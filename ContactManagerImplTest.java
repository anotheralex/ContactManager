import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Calendar;
import java.io.*;

/**
 * Tests for the ContactManagerImpl class
 */

public class ContactManagerImplTest {

	private ContactManager manager;
	private static final String FILENAME = "contacts.txt";
	private Calendar pastDate;
	private Calendar futureDate;

	@Before
	public void setup() {
		// instantiate a ContactManager object, add two Contacts
		manager = new ContactManagerImpl();
		manager.addNewContact("name", "notes");
		manager.addNewContact("name", "notes");
	
		Calendar pastDate = Calendar.getInstance();
		pastDate.set(2013, 1, 1);

		Calendar futureDate = Calendar.getInstance();
		futureDate.set(2015, 1, 1);

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

	@Test(expected = IllegalArgumentException.class)
	public void addFutureMeetingTestFutureDate() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		cm.addFutureMeeting(contacts, past);
	}

	@Test
	public void addFutureMeetingTestNormal() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar future1 = Calendar.getInstance();
		future1.set(2015, 1, 1);

		Calendar future2 = Calendar.getInstance();
		future2.set(2016, 1, 1);
		
		int i = cm.addFutureMeeting(contacts, future1);
		int j = cm.addFutureMeeting(contacts, future2);

		assertEquals(0, i);
		assertEquals(1, j);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPastMeetingTestMeetingIsInFuture() {
		Set<Contact> contacts = manager.getContacts(0, 1);

		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		// add a future meeting and get its ID
		int i = manager.addFutureMeeting(contacts, future);

		PastMeeting pm = manager.getPastMeeting(i);
	}

	@Test
	public void getPastMeetingTestCheckClass() {
		Set<Contact> contacts = manager.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = "notes";

		// add a past meeting and get its ID
		manager.addNewPastMeeting(contacts, past, notes);

		Object obj = manager.getPastMeeting(0);
		assertTrue(obj instanceof PastMeeting);
	}

	@Test
	public void getPastMeetingTestCheckReturnContents() {
		Set<Contact> contacts = manager.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String expected = "notes";

		// add a past meeting and get its ID
		manager.addNewPastMeeting(contacts, past, expected);

		String actual = manager.getPastMeeting(0).getNotes();
		assertEquals(expected, actual);
	}

	@Test
	public void getPastMeetingTestUknownReturnsNull() {
		PastMeeting pm = manager.getPastMeeting(0);
		assertNull(pm);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getFutureMeetingTestMeetingIsInPast() {
		Set<Contact> contacts = manager.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);
		String text = "notes";

		// add a past meeting and get its ID
		manager.addNewPastMeeting(contacts, past, text);
		FutureMeeting fm = manager.getFutureMeeting(0);
	}

	@Test
	public void getFutureMeetingTestCheckClass() {
		Set<Contact> contacts = manager.getContacts(0, 1);

		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		// add a past meeting and get its ID
		manager.addFutureMeeting(contacts, future);

		Object obj = manager.getFutureMeeting(0);
		assertTrue(obj instanceof FutureMeeting);
	}

	@Test
	public void getFutureMeetingTestUknownReturnsNull() {
		FutureMeeting fm = manager.getFutureMeeting(0);
		assertNull(fm);
	}

	@Test
	public void getMeetingTestCheckClass() {
		Set<Contact> contacts = manager.getContacts(0, 1);

		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		manager.addFutureMeeting(contacts, future);

		Object obj = manager.getMeeting(0);
		assertTrue(obj instanceof Meeting);
	}

	@Test
	public void getMeetingTestUknownReturnsNull() {
		Meeting meeting = manager.getMeeting(0);
		assertNull(meeting);
	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingNullNotes() {

		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = null;
		cm.addNewPastMeeting(contacts, past, notes);

	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingNullDate() {

		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar past = null;

		String notes = "notes";
		cm.addNewPastMeeting(contacts, past, notes);

	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingNullContacts() {
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = null;

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = "notes";
		cm.addNewPastMeeting(contacts, past, notes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNewPastMeetingNoAttendees() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		// create empty set of attendees
		Set<Contact> attendees = new HashSet<>();

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = "notes";
		cm.addNewPastMeeting(attendees, past, notes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNewPastMeetingUnknownAttendee() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		// create empty set of attendees
		Set<Contact> attendees = new HashSet<>();
		attendees = cm.getContacts(0);

		Contact unknown = new ContactImpl(99, "Unknown", "notes");
		attendees.add(unknown);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		String notes = "notes";

		cm.addNewPastMeeting(attendees, past, notes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addMeetingNotesTestNoMeetingWithId() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
	
		// try to add notes to non-existant meeting
		cm.addMeetingNotes(1, "text");
	}

	@Test(expected = IllegalStateException.class)
	public void addMeetingNotesMeetingInFuture() {
		// instantiate a ContactManager object, add Contacts
		ContactManager cm = new ContactManagerImpl();
		cm.addNewContact("name", "notes");
		cm.addNewContact("name", "notes");
		
		Set<Contact> contacts = new HashSet<>();
		contacts = cm.getContacts(0, 1);

		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		cm.addFutureMeeting(contacts, future);
		cm.addMeetingNotes(0, "text");
	}

	@Test(expected = NullPointerException.class)
	public void addMeetingNotesNullNotes() {
		Set<Contact> contacts = manager.getContacts(0, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		manager.addNewPastMeeting(contacts, past, "");
		manager.addMeetingNotes(0, null);
	}

	@Test
	public void addMeetingNotesTest() {
		Set<Contact> contacts = manager.getContacts(0, 1);
		
		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		manager.addNewPastMeeting(contacts, past, "");

		String actual = "notes added later";
		manager.addMeetingNotes(0, actual);
		
		PastMeeting pm = manager.getPastMeeting(0);
		String expected = pm.getNotes();
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFutureMeetingListTestUnknownContact() {
		Contact unknown = new ContactImpl(99, "Unknown", "notes");
		List<Meeting> lm = manager.getFutureMeetingList(unknown);
	}

	@Test
	public void getFutureMeetingListNoMeetingsWithContact() {
		Set<Contact> contacts = manager.getContacts(0, 1);
		Object[] contactsArray = contacts.toArray();
		Contact known = (Contact)contactsArray[0];

		List<Meeting> lm = manager.getFutureMeetingList(known);
		assertTrue(lm.isEmpty());
	}

	/*
	 * TODO test for chronological ordering
	 */
	@Test
	public void getFutureMeetingListKnownContactCorrectNumMeetings() {
		Set<Contact> contacts = manager.getContacts(0, 1);
		Object[] contactsArray = contacts.toArray();
		Contact known = (Contact)contactsArray[0];

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		Calendar future1 = Calendar.getInstance();
		future1.set(2015, 1, 1);

		Calendar future2 = Calendar.getInstance();
		future2.set(2016, 1, 1);

		manager.addFutureMeeting(contacts, future1);
		manager.addFutureMeeting(contacts, future2);
		manager.addNewPastMeeting(contacts, past, "old");

		List<Meeting> lm = manager.getFutureMeetingList(known);

		int expected = 2;
		int actual = lm.size();
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPastMeetingListTestUnknownContact() {
		Contact unknown = new ContactImpl(99, "Unknown", "notes");
		List<PastMeeting> pm = manager.getPastMeetingList(unknown);
	}

	@Test
	public void getPastMeetingListNoMeetingsWithContact() {
		Set<Contact> contacts = manager.getContacts(0, 1);
		Object[] contactsArray = contacts.toArray();
		Contact known = (Contact)contactsArray[0];

		List<PastMeeting> pm = manager.getPastMeetingList(known);
		assertTrue(pm.isEmpty());
	}

	/*
	 * TODO test for chronological ordering
	 */
	@Test
	public void getPastMeetingListKnownContactCorrectNumMeetings() {
		Set<Contact> contacts = manager.getContacts(0, 1);
		Object[] contactsArray = contacts.toArray();
		Contact known = (Contact)contactsArray[0];

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		Calendar future1 = Calendar.getInstance();
		future1.set(2015, 1, 1);

		Calendar future2 = Calendar.getInstance();
		future2.set(2016, 1, 1);

		manager.addFutureMeeting(contacts, future1);
		manager.addFutureMeeting(contacts, future2);
		manager.addNewPastMeeting(contacts, past, "old");

		List<PastMeeting> pm = manager.getPastMeetingList(known);

		int expected = 1;
		int actual = pm.size();
		assertEquals(expected, actual);
	}

	@Test
	public void getFutureMeetingListTestMeetingsInFuture() {
		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		manager.addFutureMeeting(manager.getContacts(0), future);
		manager.addFutureMeeting(manager.getContacts(1), future);
		manager.addFutureMeeting(manager.getContacts(0, 1), future);

		List<Meeting> lm = manager.getFutureMeetingList(future);
		int expected = 3;
		int actual = lm.size();
		assertEquals(expected, actual);
	}

	@Test
	public void getFutureMeetingListTestMeetingsInPast() {
		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);
		String notes = "notes";

		manager.addFutureMeeting(manager.getContacts(0), future);
		manager.addNewPastMeeting(manager.getContacts(0), past, notes);
		manager.addNewPastMeeting(manager.getContacts(1), past, notes);
		manager.addNewPastMeeting(manager.getContacts(0, 1), past, notes);

		List<Meeting> lm = manager.getFutureMeetingList(past);
		int expected = 3;
		int actual = lm.size();
		assertEquals(expected, actual);
	}

	@Test
	public void getFutureMeetingListTestNoMeetingsOnDate() {
		Calendar future = Calendar.getInstance();
		future.set(2015, 1, 1);

		Calendar past = Calendar.getInstance();
		past.set(2013, 1, 1);

		Calendar date = Calendar.getInstance();
		date.set(2014, 1, 1);

		String notes = "notes";

		manager.addFutureMeeting(manager.getContacts(0), future);
		manager.addNewPastMeeting(manager.getContacts(0), past, notes);
		manager.addNewPastMeeting(manager.getContacts(1), past, notes);
		manager.addNewPastMeeting(manager.getContacts(0, 1), past, notes);

		List<Meeting> lm = manager.getFutureMeetingList(date);
		assertTrue(lm.isEmpty());
	}


}
