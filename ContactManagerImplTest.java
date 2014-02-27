import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Tests for the ContactManagerImpl class
 */

public class ContactManagerImplTest {

	private ContactManager cm;

	@Before
	public void setup() {
		cm = new ContactManagerImpl();
		String name = "Test name";
		String notes = "Test notes";
		cm.addNewContact(name, notes);
		cm.flush();
	}

}
