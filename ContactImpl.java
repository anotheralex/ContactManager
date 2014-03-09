/**
* A contact is a person we are making business with or may do in the future
*
* Contacts have an ID (unique), a name (probably unique, but maybe
* not), and notes that the user may want to save about them
*/
public class ContactImpl implements Contact {

	private int id;
	private String name;
	private String notes;

	// static field to generate and ensure unique IDs
	private static int count = 0;

	/**
	 * Constructor
	 *
	 * @param name contact name
	 */
	public ContactImpl(String name) {
		this.id = count;
		count++;
		this.name = name;
		this.notes = null;
	}

	/**
	 * Constructor
	 *
	 * @param name contact name
	 * @param notes notes about contact
	 */
	public ContactImpl(String name, String notes) {
		this.id = count;
		count++;
		this.name = name;
		this.notes = notes;
	}

	/**
	 * Constructor
	 *
	 * @param id contact id
	 * @param name contact name
	 * @param notes notes about contact
	 */
	public ContactImpl(int id, String name, String notes) {
		this.id = id;
		this.name = name;
		this.notes = notes;
	}


	/**
	* Returns the ID of the contact
	*
	* @return the ID of the contact
	*/
	public int getId() {
		return this.id;
	}
	
	/**
	* Returns the name of the contact
	*
	* @return the name of the contact
	*/
	public String getName() {
		return this.name;
	}
	
	/**
	* Returns our notes about the contact, if any
	*
	* If we have not written anything about the contact, the empty
	* string is returned
	*
	* @return a string with notes about the contact, maybe empty.
	*/
	public String getNotes() {
		if (this.notes == null) {
			return "";
		}
		return this.notes;
	}
	
	/**
	* Add notes about the contact
	*
	* @param note the notes to be added
	*/
	public void addNotes(String notes) {
		this.notes = notes;
	}
}
