import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.*;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
* A class to manage your contacts and meetings
*/
public class ContactManagerImpl implements ContactManager {

	private Set<Contact> contacts;
	private Set<Meeting> meetings;
	private String filename = "contacts.txt";

	/*
	 * Persistent IDs for contacts and meetings
	 * These variables serve as the IDs for the next created instance
	 * They are restored to their previous values when loading previous data
	 */
	private int contactId;
	private int meetingId;

	// format for storing and readings dates and times
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Constructor
	 *
	 * Creates main data structures and resets main IDs
	 *
	 * With no parameters any stored data will be ignored.
	 * Useful for testing purposes.
	 */
	public ContactManagerImpl() {
		this.contacts = new HashSet<Contact>();
		this.meetings = new HashSet<Meeting>();
		this.contactId = 0;
		this.meetingId = 0;
	}

	public ContactManagerImpl(String str) {
		this.contacts = new HashSet<Contact>();
		this.meetings = new HashSet<Meeting>();
	
		/* Check if a file named FILENAME exists
		 * If it does load data
		 * If it does not set base contactId and meetingId
		 */
		File file = new File(filename);
		if (file.exists()) {
			this.load(file);
		} else {
			this.contactId = 0;
			this.meetingId = 0;
		}
	}

	/**
	 * Recover contactId, meetingId and rebuild contact and meeting lists
	 *
	 * @param file File object to search for data
	 */
	private void load(File file) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			String line;
			String[] fields;

			while ((line = in.readLine()) != null) {
				// use | as delimeter to allow commas in notes
				fields = line.split("\\|");

				/*
				 * This method of importing is hopelessly complicated
				 * Better to use a better data structure or import/export
				 * method. This is working for now and will not be changed
				 */
				switch (fields[0]) {
					case "contactId":
						this.contactId = Integer.parseInt(fields[1]);
						break;
					case "meetingId":
						this.meetingId = Integer.parseInt(fields[1]);
						break;
					case "contact":
						Contact contact = new ContactImpl(
								Integer.parseInt(fields[1]),
								fields[2],
								fields[3]);
						this.contacts.add(contact);
						break;
					case "meeting":
						Set<Contact> attendees = new HashSet<Contact>();
						// create array of IDs of attendees at meeting
						String[] strIds = fields[4].split(",");

						/*
						 * Set of known contacts already created above
						 * Create contactId->contact map for known contacts
						 * Very inefficient but works for now
						 */
						HashMap<Integer, Contact> contactIdMap = new HashMap<>();
						for (Contact knownContact : this.contacts) {
							contactIdMap.put(knownContact.getId(), knownContact);
						}

						/*
						 * Populate Set<Contact>() with meeting attendees
						 * Iterate to strIds.length - 1 to avoid
						 * the empty string created by the trailing comma
						 * during flush()ing
						 */
						for (int i = 0; i < strIds.length; i++) {
							if (!contactIdMap.containsKey(Integer.parseInt(strIds[i]))) {
								throw new IllegalArgumentException("Error parsing id " + strIds[i]);
							} else {
								attendees.add(contactIdMap.get(Integer.parseInt(strIds[i])));
							}
						}

						// create a Calendar object to store parsed date
						Calendar date = Calendar.getInstance();
						date.setTime(dateFormat.parse(fields[2]));
						
						/*
						 * Instantiate a new PastMeeting object
						 * using PastMeeting to have access to 
						 * constructor allowing notes
						 * Cast as Meeting object for storage in container
						 */
						PastMeeting meeting = new MeetingImpl(
								Integer.parseInt(fields[1]),
								attendees,
								date,
								fields[3]);
						this.meetings.add((Meeting) meeting);
						break;
				}
	
			}
		} catch (IOException ex) {
				ex.printStackTrace();
		} catch (ParseException ex) {
				ex.printStackTrace();
		} finally {
			closeReader(in);
		}

	}

	/**
	 * Closes a Reader object
	 *
	 * @param reader Reader object to close
	 */
	private static void closeReader(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/*
		 * When run from main, use the constructor that makes use
		 * of stored data
		 */
		ContactManagerImpl cm = new ContactManagerImpl("contacts.txt");
		cm.launch();
	}

	/*
	 * Simple code to test some of the functionality of the class.
	 * Most important for checkign that data export and import
	 * are working properly.
	 */
	public void launch() {
		System.out.println("Welcome to Contact Manager");

		this.addNewContact("Contact 1", "Notes 1");
		this.addNewContact("Contact 2", "Notes 2");
		this.addNewContact("Contact 3", "Notes 3");
		
		try {
			Calendar date1 = Calendar.getInstance();
			date1.setTime(dateFormat.parse("2000-01-01 12:00:00"));
			this.addNewPastMeeting(this.contacts, date1, "Past notes 1");

			Calendar date2 = Calendar.getInstance();
			date2.setTime(dateFormat.parse("2001-01-01 12:00:00"));
			this.addNewPastMeeting(this.contacts, date2, "Past notes 1");
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		this.flush();

	}

	/**
	* Add a new meeting to be held in the future
	*
	* @param contacts a list of contacts that will participate in the meeting
	* @param date the date on which the meeting will take place
	* @return the ID for the meeting
	* @throws IllegalArgumentException if the meeting is set for a time in the past,
	* 	or if any contact is unknown / non-existent
	*/
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (date.before(Calendar.getInstance())) {
			throw new IllegalArgumentException("Error. Date is in the past.");
		} else {
			FutureMeeting meeting = new MeetingImpl(this.meetingId, contacts, date);
			this.meetings.add((Meeting) meeting);
			this.meetingId++;
			return meeting.getId();
		}
	}
	
	/**
	* Returns the PAST meeting with the requested ID, or null if there is none
	*
	* @param id the ID for the meeting
	* @return the meeting with the requested ID, or null if there is none
	* @throws IllegalArgumentException if there is a meeting with that ID happening in the future
	*/
	public PastMeeting getPastMeeting(int id) {
		for (Meeting meeting : this.meetings) {
			if (meeting.getId() == id) {
				if (meeting.getDate().after(Calendar.getInstance())) {
					throw new IllegalArgumentException("Meeting " + id + " is not a past meeting.");
				} else {
					return (PastMeeting) meeting;
				}
			}
		}
		return null;
	}
	
	/**
	* Returns the FUTURE meeting with the requested ID, or null if there is none
	*
	* @param id the ID for the meeting
	* @return the meeting with the requested ID, or null if there is none
	* @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	*/
	public FutureMeeting getFutureMeeting(int id) {
		for (Meeting meeting : this.meetings) {
			if (meeting.getId() == id) {
				if (meeting.getDate().before(Calendar.getInstance())) {
					throw new IllegalArgumentException("Meeting " + id + " happened in the past");
				} else {
					return (FutureMeeting) meeting;
				}
			}
		}
		return null;
	}
	
	/**
	* Returns the meeting with the requested ID, or null if there is none
	*
	* @param id the ID for the meeting
	* @return the meeting with the requested ID, or null if there is none
	*/
	public Meeting getMeeting(int id) {
		for (Meeting meeting : this.meetings) {
			if (meeting.getId() == id) {
				return meeting;
			}
		}
		return null;
	}
	
	/**
	* Returns the list of future meetings scheduled with this contact
	*
	* If there are no meetings, the returned list will be empty. Otherwise,
	* the list will be chronologically sorted and will not contain any
	* duplicates
	*
	* @param contact one of the user’s contacts
	* @return the list of future meeting(s) scheduled with this contact (maybe empty)
	* @throws IllegalArgumentException if the contact does not exist
	*/
	public List<Meeting> getFutureMeetingList(Contact contact) {
		List<Meeting> result = new ArrayList<>();
		
		if (!this.contacts.contains(contact)) {
			throw new IllegalArgumentException("Contact does not exist.");
		} else {
			for (Meeting meeting : this.meetings) {
				if (meeting.getContacts().contains(contact) &&
					meeting.getDate().after(Calendar.getInstance())) {
						result.add(meeting);
				}
			}
		}
		return result;
	}
	
	/**
	* Returns the list of meetings that are scheduled for, or that took
	* place on, the specified date
	*
	* If there are no meetings, the returned list will be empty. Otherwise,
	* the list will be chronologically sorted and will not contain any
	* duplicates
	*
	* @param date the date
	* @return the list of meetings
	*/
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO list is not sorted chronologically whatever that means
		List<Meeting> result = new ArrayList<>();
		for (Meeting meeting : this.meetings) {
			if (meeting.getDate().equals(date)) {
				result.add(meeting);
			}
		}
		return result;
	}

	/**
	* Returns the list of past meetings in which this contact has participated
	*
	* If there are no meetings, the returned list will be empty. Otherwise,
	* the list will be chronologically sorted and will not contain any
	* duplicates
	*
	* @param contact one of the user’s contacts
	* @return the list of future meeting(s) scheduled with this contact (maybe empty)
	* @throws IllegalArgumentException if the contact does not exist
	*/
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO list is not sorted chronologically whatever that means
		List<PastMeeting> result = new ArrayList<>();

		if (!this.contacts.contains(contact)) {
			throw new IllegalArgumentException("Contact does not exist.");
		} else {
			for (Meeting meeting : this.meetings) {
				if (meeting.getContacts().contains(contact) &&
					meeting.getDate().before(Calendar.getInstance())) {
						result.add((PastMeeting) meeting);
				}
			}
		}
		return result;
	}
	
	/**
	* Create a new record for a meeting that took place in the past
	*
	* @param contacts a list of participants
	* @param date the date on which the meeting took place
	* @param text messages to be added about the meeting
	* @throws IllegalArgumentException if the list of contacts is
	* 	empty, or any of the contacts does not exist
	* @throws NullPointerException if any of the arguments is null
	*/
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		if (contacts == null || date == null || text == null) {
			throw new NullPointerException();
		} else if (contacts.isEmpty()) {
			throw new IllegalArgumentException("No attendees given");
		} else {
			for (Contact contact : contacts) {
				if (!this.contacts.contains(contact)) {
					throw new IllegalArgumentException();
				}
			}

			Meeting meeting = new MeetingImpl(this.meetingId, contacts, date, text);
			this.meetings.add(meeting);
			this.meetingId++;
		}
	}
	
	/**
	* Add notes to a meeting
	*
	* This method is used when a future meeting takes place, and is
	* then converted to a past meeting (with notes)
	*
	* It can be also used to add notes to a past meeting at a later date
	*
	* @param id the ID of the meeting
	* @param text messages to be added about the meeting
	* @throws IllegalArgumentException if the meeting does not exist
	* @throws IllegalStateException if the meeting is set for a date in the future
	* @throws NullPointerException if the notes are null
	*/
	public void addMeetingNotes(int id, String text) {
		/*
		 * this waste memory and won't scale well
		 * should instead override equals() and
		 * create a new private method for confirming inclusion
		 * based on id
		 */
		// create meetingId->meeting map to check for existence
		Map<Integer, Meeting> meetingIdMap = new HashMap<>();
		for (Meeting meeting : this.meetings) {
			meetingIdMap.put(meeting.getId(), meeting);
		}

		if (!meetingIdMap.containsKey(id)) {
			throw new IllegalArgumentException();
		} else if (meetingIdMap.get(id).getDate().after(Calendar.getInstance())) {
			throw new IllegalStateException();
		} else if (text == null) {
			throw new NullPointerException();
		} else {
			/*
			 * Ideally want meetingIdMap.get(id).addNotes(text);
			 * No setter method for notes in interface so
			 * create a new meeting and copy old information
			 * but add the new notes, then add the new meeting and
			 * destroy the old meeting
			 */
			Meeting updated  = new MeetingImpl(id,
					meetingIdMap.get(id).getContacts(),
					meetingIdMap.get(id).getDate(),
					text);
			this.meetings.add(updated);
			this.meetings.remove(meetingIdMap.get(id));
		}
	}
	
	/**
	* Create a new contact with the specified name and notes
	*
	* @param name the name of the contact
	* @param notes notes to be added about the contact
	* @throws NullPointerException if the name or the notes are null
	*/
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) {
			throw new NullPointerException();
		} else {
			Contact newContact = new ContactImpl(contactId, name, notes);
			this.contacts.add(newContact);
			this.contactId++;
		}
	}
	
	/**
	* Returns a list containing the contacts that correspond to the IDs
	*
	* @param ids an arbitrary number of contact IDs
	* @return a list containing the contacts that correspond to the IDs
	* @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
	*/
	public Set<Contact> getContacts(int... ids) {

		Set<Contact> result = new HashSet<>();

		// create a map contactId->contact for all known contacts
		Map<Integer, Contact> contactIdMap = new HashMap<>();
		for (Contact contact : this.contacts) {
			contactIdMap.put(contact.getId(), contact);
		}

		for (int i = 0; i < ids.length; i++) {
			if (!contactIdMap.containsKey(ids[i])) {
				throw new IllegalArgumentException();
			} else {
				result.add(contactIdMap.get(ids[i]));
			}
		}

		return result;
	}
	
	/**
	* Returns a list with the contacts whose name contains that string
	*
	* @param name the string to search for
	* @return a list with the contacts whose name contains that string
	* @throws NullPointerException if the parameter is null
	*/
	public Set<Contact> getContacts(String name) {
		Set<Contact> result = new HashSet<>();

		if (name == null) {
			throw new NullPointerException();
		} else {
			for (Contact contact : this.contacts) {
				if (contact.getName().toLowerCase().contains(name.toLowerCase())) {
					result.add(contact);
				}
			}
		}
		return result;
	}
	
	/**
	* Save all data to disk.
	*
	* This method must be executed when the program is
	* closed and when/if the user requests it
	*/
	public void flush() {
		File file = new File(filename);
		PrintWriter out = null;
		String output;

		try {
			out = new PrintWriter(file);

			/*
			 * contactId format: "contactId"|contactId
			 * meetingId format: "meetingId"|meetingId
			 * contact format: "contact"|id|name|notes
			 * meeting format: "meeting"|id|date|notes|attendees
			 *		where attendees: contact,contact,...,contact
			 * use | delimeter to avoid problems with commas in notes
			 */
			// 
			output = "contactId" + "|" + this.contactId + "\n";
			out.write(output);

			output = "meetingId" + "|" + this.meetingId + "\n";
			out.write(output);

			for (Contact contact : this.contacts) {
				output = "contact" + "|" +
					contact.getId() + "|" +
					contact.getName() + "|" +
					contact.getNotes() + "\n";
				out.write(output);
			}

			for (Meeting meeting : this.meetings) {
				// downcast to PastMeeting to access getNotes() method
				PastMeeting pastMeeting = (PastMeeting) meeting;

				output = "meeting" + "|" +
					pastMeeting.getId() + "|" +
					dateFormat.format(pastMeeting.getDate().getTime()) + "|" +
					pastMeeting.getNotes() + "|";
				for (Contact contact : pastMeeting.getContacts()) {
					// trailing comma dealt with at data load
					output = output + contact.getId() + ",";
				}
				output += "\n";
				out.write(output);
			}

		} catch (FileNotFoundException ex) {
			System.out.println("Could not write to file " + file + ".");
		} finally {
			out.close();
		}
	}

}
