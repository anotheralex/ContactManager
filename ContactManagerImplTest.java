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

		ContactManager cm = new ContactManagerImpl();

	}

	@Test(expected = NullPointerException.class)
	public void addNewContactTestNullName() {
		String name = null;
		String notes = "Notes";
		cm.addNewContact(name, notes);
	}

	@Test(expected = NullPointerException.class)
	public void addNewContactTestNullNotes() {
		String name = "Name";
		String notes = null;
		cm.addNewContact(name, notes);
	}

	@Test
	public void getContactsById() {
		cm.addNewContact("Name 1", "Notes");
		cm.addNewContact("Name 2", "Notes");
		cm.addNewContact("Name 3", "Notes");
		cm.addNewContact("Name 4", "Notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(1, 2);

		int expected = 2;
		int actual = contacts.size();
		assertEquals(expected, actual);
	}


}
