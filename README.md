ContactManager
==============

A simple command line contact manager backend written in Java.

The interface is implemented as follows:
- ContactManagerImpl imlpements ContactManager
- ContactImpl implements Contact
- MeetingImpl implements Meeting, PastMeeting and FutureMeeting

All meetings have a Notes field. In the case of FutureMeetings and, at instantiation, Meetings, these are empty.

Known issues:
-------------
The interface lacks a setter method for meeting notes. To get around this, notes are passed as a parameter when instantiating a new PastMeeting object. For the case of adding notes to a PastMeeting object, I have resorted to copying the object's state so that a new object can be instantiated and populated at construction with notes, before destroying the notes-free original object.

The current implementation uses a kludgy CSV variant with two delimiters. This is necessary to allow for Set\<Contact\> objects to be recreated from stored data. A different approach, still using CSV, could use an relational database-style association between meeting ID and contact ID so that meeting attendees groups can be reconstructed in a more straightforward way. Otherwise XML or JSON could be used as a more appropriate storage method.

There are no tests for the flush() method in ContactManager. Rather this has been tested by with a simple launch() method to show that export and import of data works.

The implementation of ContactManager uses a few wasteful approaches using maps for checking whether objects exist in the contact manager. One important part of refactoring would be to create new private methods to move some of this duplicated code out of the interface methods.
