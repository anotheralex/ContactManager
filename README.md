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
The interface lacks a setter method for meeting notes. To get around this, notes are passed as a paramter when instantiating a new PastMeeting. For the case of adding notes to a PastMeeting object, I have resorted to copying the object's state so that a new object can be instantiated and populated at construction with notes, before destroying the notes-free original object.

The current implementation uses a kludgey CSV variant with two delimiters. This is necessary to allow for Set<Contact> objects to be recreated from stored data. A different still using CSV could use an association class between meeting ID and contact ID so that meeting attendees groups can be reconstructed in a more straightforward way.

There are no tests for the flush() method in ContactManager. Rather this has been tested by with a simple launch() method to show that export and import of data works.
