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
	private Set<Contact> contacts;
	private Contact contact;
	private Calendar date;

	@Before
	public void setup() {
		contact = new ContactImpl("Contact");
		contacts = new HashSet<Contact>();
		contacts.add(contact);

		date = new GregorianCalendar(2013, 01, 01);

		meeting = new MeetingImpl(contacts, date);
	}

	@Test
	public void getIdTest() {

		Calendar date = new GregorianCalendar(2013, 01, 01);

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
		Calendar actual = meeting.getDate();
		Calendar expected = date;
		assertEquals(expected, actual);
	}

	@Test
	public void getContactsTest() {

		Calendar date = new GregorianCalendar(2013, 01, 01);

		Contact contact1 = new ContactImpl("Contact1");
		Contact contact2 = new ContactImpl("Contact2");

		Set<Contact> contacts1 = new HashSet<Contact>();
		contacts1.add(contact1);
		contacts1.add(contact2);
		
		Meeting meeting1 = new MeetingImpl(contacts1, date);

		Set<Contact> actual = meeting1.getContacts();

		Set<Contact> expected = contacts1;

		assertEquals(expected, actual);

	}

}
