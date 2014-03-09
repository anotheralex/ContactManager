/**
 * JUnit 1.4 unit tests for ContactImpl class
 */
import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest {

	private Contact contact;
	private int id;
	private String name;
	private String notes;

	@Before
	public void seteup() {
		id = 0;
		name = "Name";
		notes = "Notes";
		contact = new ContactImpl(id, name, notes);
	}

	@Test
	public void getIdTest() {
		int actual = contact.getId();
		int expected = id;
		assertEquals(expected, actual);
	}

	@Test
	public void getNameTest() {
		String expected = name;
		String actual = contact.getName();
		assertEquals(expected, actual);
	}

	@Test
	public void getNotesTest() {
		String expected = notes;
		String actual = contact.getNotes();
		assertEquals(expected, actual);
	}

	@Test
	public void getNotesNullTest() {
		Contact c = new ContactImpl(1, "Name", "");

		String expected = "";
		String actual = c.getNotes();
		assertEquals(expected, actual);
	}

	@Test
	public void addNotesTest() {
		Contact c = new ContactImpl(1, "Name", "");
		String newNotes = "New Notes";

		c.addNotes(newNotes);

		String expected = newNotes;
		String actual = c.getNotes();
		assertEquals(expected, actual);
	}
}
