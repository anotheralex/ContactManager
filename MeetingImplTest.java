import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Calendar;

/**
 * Tests for the MeetingImpl class
 */

public class MeetingImplTest {

	private Meeting meeting;
	private Set<Contact> contacts;
	private Contact contact;
	private Calendar date;
	private int id;

	@Before
	public void setup() {
		contact = new ContactImpl(0, "Contact", "Notes");
		contacts = new HashSet<Contact>();
		contacts.add(contact);

		//date = new GregorianCalendar(2013, 01, 01);
		date = Calendar.getInstance();
		id = 0;

		meeting = new MeetingImpl(id , contacts, date);
	}

	@Test
	public void getIdTest() {
		Meeting m = new MeetingImpl(1, contacts, date);

		int expected = 1;
		int actual = m.getId();

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
		Meeting m = new MeetingImpl(0, contacts, date);

		Set<Contact> actual = m.getContacts();
		Set<Contact> expected = contacts;

		assertEquals(expected, actual);
	}
}
