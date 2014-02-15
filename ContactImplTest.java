/**
 * JUnit 1.4 unit tests for ContactImpl class
 */
import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest {

	@Test
	public void getIdTest() {

		Contact zero = new ContactImpl("zero");
		Contact one = new ContactImpl("one");
		Contact two = new ContactImpl("two");

		// make sure that IDs are unique and increment
		int actual = two.getId() - zero.getId();
		int expected = 2;
		assertEquals(expected, actual);
	}

	@Test
	public void getNameTest() {

		String name = "Test";
		Contact test = new ContactImpl(name);

		String expected = name;
		String actual = test.getName();
		assertEquals(expected, actual);
	}

	@Test
	public void getNotesTest() {
		String name = "Tony Est";
		String notes = "Tony notes";

		Contact test = new ContactImpl(name, notes);
		String expected = notes;
		String actual = test.getNotes();
		assertEquals(expected, actual);
	}

	@Test
	public void getNotesNullTest() {
		Contact test = new ContactImpl("Test");

		String expected = "";
		String actual = test.getNotes();
		assertEquals(expected, actual);
	}

	@Test
	public void addNotesTest() {
		Contact test = new ContactImpl("test");
		String notes = "some notes";
		test.addNotes(notes);

		String expected = notes;
		String actual = test.getNotes();
		assertEquals(expected, actual);
	}

}
