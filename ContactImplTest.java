/**
 * JUnit 1.4 unit tests for ContactImpl class
 */

import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest {

	private ContactImpl contact;

	@Before
	public void setup() {
		String name = "test name";
		String notes = "test notes"
		Contact contact = new ContactImpl(name, notes);
	}

	@Test
	public void getIdTest() {
		int actual = contact.getId();
		int expected = 0;
		assertEquals(expected, actual);
	}

}
