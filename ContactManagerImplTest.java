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


}
