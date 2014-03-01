import java.util.*;
/**
* A meeting that was held in the past
*
* It includes your notes about what happened and what was agreed
*/
public class PastMeetingImpl extends MeetingImpl {

	private String notes;

	/**
	 * Constructor
	 *
	 * creates a Meeting and includes notes
	 */
	public PastMeetingImpl(int id, Set<Contact> contacts, Calendar date, String notes) {
		super(id, contacts, date);
		this.notes = notes;
	}

	/**
	* Returns the notes from the meeting
	*
	* If there are no notes, the empty string is returned
	*
	* @return the notes from the meeting
	*/
	public String getNotes() {
		return this.notes;
	}
}
