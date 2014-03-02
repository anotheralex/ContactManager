import java.util.Calendar;
import java.util.Set;
import java.util.*;

/**
* A class to represent meetings
*
* Meetings have unique IDs, scheduled date and a list of participating contacts
*/
public class MeetingImpl implements Meeting, PastMeeting, FutureMeeting {

	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	private String notes;

	public MeetingImpl(int id, Set<Contact> contacts, Calendar date) {
		this.id = id;
		this.contacts = contacts;
		this.date = date;
		this.notes = "";
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
		return this.contacts;
	}

	/**
	 * Returns the notes from the meeting.
	 *
	 * If there are no notes, the empty string is returned.
	 *
	 * @return the notes from the meeting.
	 * */
	public String getNotes() {
		return this.notes;
	}
}
