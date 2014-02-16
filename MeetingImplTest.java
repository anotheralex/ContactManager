import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests for the MeetingImpl class
 */

public class MeetingImplTest {

	private Meeting meeting;

	@Before
	public void setup() {
		Contact user = new Contact("Test1");
		Contact contact = new Contact("Contact");
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(user);
		contacts.add(contact);

		Calendar date = Calendar.getInstance();

		meeting = new Meeting(contacts, date);
	}

	@Test
	public void getIdTest() {
		//TODO
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
