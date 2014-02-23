import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Tests for the MeetingImpl class
 */

public class MeetingImplTest {

	private Meeting meeting;

	@Before
	public void setup() {
		Contact contact = new ContactImpl("Contact");
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(contact);

		Calendar date = new GregorianCalendar(2013, 01, 01);

		meeting = new MeetingImpl(contacts, date);
	}

	@Test
	public void getIdTest() {
		Contact contact1 = new ContactImpl("Contact1");
		Contact contact2 = new ContactImpl("Contact2");

		Set<Contact> contacts1 = new HashSet<Contact>();
		contacts1.add(contact1);
		
		Meeting meeting1 = new MeetingImpl(contacts1, date);

		Set<Contact> contacts2 = new HashSet<Contact>();
		contacts2.add(contact2);

		Meeting meeting2 = new MeetingImpl(contacts2, date);

		int expected = 1;
		int actual = meeting2.getId() - meeting1.getId();

		assertEquals(expected, actual);
	}

	@Test
	public void getDateTest() {
		//TODO
	}

	@Test
	public void getContactsTest() {
		//TODO
	}

}