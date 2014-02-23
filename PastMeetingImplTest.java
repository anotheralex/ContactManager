import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Tests for the PastMeetingImpl class
 */

public class PastMeetingImplTest {

	private PastMeeting meeting;
	private Set<Contact> contacts;
	private Contact contact;
	private Calendar date;
	private String notes

	@Before
	public void setup() {
		contact = new ContactImpl("Contact");
		contacts = new HashSet<Contact>();
		contacts.add(contact);

		date = new GregorianCalendar(2013, 01, 01);

		notes = "Notes";

		meeting = new PastMeetingImpl(contacts, date, notes);
	}

	@Test
	public void getNotesTest() {
		String expected = notes;
		String actual = meeting.getNotes();
		assertEquals(expected, actual);
	}
}
