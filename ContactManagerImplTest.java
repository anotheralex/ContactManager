import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Calendar;
import java.io.*;

/**
 * Tests for the ContactManagerImpl class
 */

public class ContactManagerImplTest {

	private ContactManager cm;
	private static final String FILENAME = "contacts.txt";

	@Before
	public void setup() {
		/*
		File file = new File(FILENAME);
		try {
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/

		//ContactManager cm = new ContactManagerImpl();

	}

	@Test(expected = NullPointerException.class)
	public void addNewContactTestNullName() {
		ContactManager cm = new ContactManagerImpl();
		String name = null;
		String notes = "Notes";
		cm.addNewContact(name, notes);
	}

	@Test(expected = NullPointerException.class)
	public void addNewContactTestNullNotes() {
		ContactManager cm = new ContactManagerImpl();
		String name = "Name";
		String notes = null;
		cm.addNewContact(name, notes);
	}

	@Test
	public void getContactsTestById() {
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		
		contacts = cm.getContacts(0);
		int expected = 1;
		int actual = contacts.size();
		assertEquals(expected, actual);
		
		contacts = cm.getContacts(0, 1);
		expected = 2;
		actual = contacts.size();
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getContactsTestUnknownId() {
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		
		contacts = cm.getContacts(3);
	}

	@Test(expected = NullPointerException.class)
	public void getContactsTestNullName() {
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		cm.addNewContact("nam1", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		String name = null;
		contacts = cm.getContacts(name);
	}

	@Test
	public void getContactsTestByName() {
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		cm.addNewContact("nam1", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts("name");

		int expected = 2;
		int actual = contacts.size();
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addFutureMeetingTestFutureDate() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		cm.addFutureMeeting(contacts, past);
	}

	@Test
	public void addFutureMeetingTestNormal() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar future1 = Calendar.getInstance();
		future1.set(2015, 1, 1);

		Calendar future2 = Calendar.getInstance();
		future2.set(2016, 1, 1);
		
		int i = cm.addFutureMeeting(contacts, future1);
		int j = cm.addFutureMeeting(contacts, future2);

		assertEquals(0, i);
		assertEquals(1, j);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPastMeetingTestMeetingIsInFuture() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		// add a future meeting and get its ID
		int i = cm.addFutureMeeting(contacts, future);

		PastMeeting pm = cm.getPastMeeting(i);
	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingNullNotes() {

		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = null;
		cm.addNewPastMeeting(contacts, past, notes);

	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingNullDate() {

		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar past = null;

		String notes = "notes";
		cm.addNewPastMeeting(contacts, past, notes);

	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingNullContacts() {
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = null;

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = "notes";
		cm.addNewPastMeeting(contacts, past, notes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNewPastMeetingNoAttendees() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		// create empty set of attendees
		Set<Contact> attendees = new HashSet<>();

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = "notes";
		cm.addNewPastMeeting(attendees, past, notes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNewPastMeetingUnknownAttendee() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		// create empty set of attendees
		Set<Contact> attendees = new HashSet<>();
		attendees = cm.getContacts(0);

		Contact unknown = new ContactImpl(99, "Unknown", "notes");
		attendees.add(unknown);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = "notes";

		cm.addNewPastMeeting(attendees, past, notes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addMeetingNotesTestNoMeetingWithId() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
	
		// try to add notes to non-existant meeting
		cm.addMeetingNotes(1, "text");
	}

	@Test(expected = IllegalStateException.class)
	public void addMeetingNotesMeetingInFuture() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		cm.addFutureMeeting(contacts, future);
		cm.addMeetingNotes(0, "text");
	}

	@Test(expected = NullPointerException.class)
	public void addMeetingNotesNullNotes() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		cm.addNewPastMeeting(contacts, past, "");
		cm.addMeetingNotes(0, null);
	}


}
