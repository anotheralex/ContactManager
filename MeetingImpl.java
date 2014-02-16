import java.util.Calendar;
import java.util.Set;

/**
* A class to represent meetings
*
* Meetings have unique IDs, scheduled date and a list of participating contacts
*/
public interface Meeting {

	private int Id;
	private Calender date;
	private Set<Contact> contacts;
	private Contact user;

	// counter to give out unique IDs
	private static int count = 0;

	public Meeting(Set<Contact> contacts, Calendar date) {
		this.id = count;
		count++;

		this.contacts = new HashSet<Contact>();
		this.contacts = contacts.clone();
		this.contacts.add(this.user);

		this.date = date;
	}

	/**
	* Returns the id of the meeting
	*
	* @return the id of the meeting
	*/
	public int getId() {
		return this.id;
	}
	
	/**
	* Return the date of the meeting
	*
	* @return the date of the meeting
	*/
	public Calendar getDate() {
		return this.date;
	}
	
	/**
	* Return the details of people that attended the meeting
	*
	* The list contains a minimum of one contact (if there were
	* just two people: the user and the contact) and may contain an
	* arbitraty number of them
	*
	* @return the details of people that attended the meeting.
	*/
	public Set<Contact> getContacts() {
		Set<Contact> attendees = new HashSet<Contact>();
		attendees = this.contacts.clone();
		attendees.remove(user);
		return attendees;
	}
}
