package testing;

import static org.junit.Assert.*;

import implementation.Note;
import implementation.NotebookXML;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import utilities.BadRequest;



/**
 * @author  JFee
 */
public class NotebookTest
{
	private ArrayList<Note> notes;
	private NotebookXML book;
	private static int i = 0;
	private static ArrayList<Note> createNotes()
	{
		i = 0;
		Note one = new Note();
		Note two = new Note();
		Note three = new Note();
		Note four = new Note();
		Note five = new Note();
		ArrayList<Note> temp = new ArrayList<Note>();
		temp.add(one);
		temp.add(two);
		temp.add(three);
		temp.add(four);
		temp.add(five);
		
		
		for(Note n: temp)
		{
			i++;
			n.setContent(i + " note content");
			n.setId("" + i);
			//System.out.println(n.getId());
		}
		
		
		return temp;
		
	}

	@Before
	public void setUp() throws Exception
	{
		notes = NotebookTest.createNotes();
		book = new NotebookXML();
		
	}

	@Test
	public void test()
	{
		book.setId("1");
		book.setNotes(notes);
		book.setTitle("FIRST");
		
		assertEquals("1", book.getId());
		assertEquals("FIRST", book.getTitle());
		assertEquals("1", book.getNotes().get(0).getId());
		
		//new notes not set b/c they are null so old remain
		notes = null;
		book.setNotes(notes);
		assertEquals(5, book.getNotes().size());
		
		//now this should have reset the noteIdsetter and notes list to default
		notes = new ArrayList<Note>();
		book.setNotes(notes);
		assertTrue(book.getNotes().isEmpty());
		
		notes = NotebookTest.createNotes();
		notes.remove(0);
		assertEquals("2",  notes.get(0).getId());
		
		/*even though the note has an id of 2 in the notes(list) 
		when this notebook is set with these notes it gets a new id 
		for this notebook*/
		
		book.setNotes(notes);
		assertEquals(4,  book.getNotes().size());
		assertEquals("1", notes.get(0).getId());
		
		//add a note make sure id set correctly and added to list of notes
		Note tempNote = new Note();
		tempNote.setContent("TempContent");
		book.addNote(tempNote);
		assertEquals(5,  book.getNotes().size());
		assertEquals("5", book.getNotes().get(4).getId());
		
		//add empty content note make sure it doesn't work
		tempNote = new Note();
		book.addNote(tempNote);
		assertEquals(5,  book.getNotes().size());
		assertEquals("5", book.getNotes().get(4).getId());
		
		tempNote.setContent("              ");
		book.addNote(tempNote);
		assertEquals(5,  book.getNotes().size());
		assertEquals("5",  book.getNotes().get(4).getId());
		
		//should not delete anything
		Note nTemp = new Note();
		
		
		assertFalse(book.deleteNote(nTemp));
		
		
		assertEquals(5, book.getNotes().size());
		
		
		
		//should delete and move noteIdSetter back 1
		//note at index 3 = NOTE with id 4
		
		notes = NotebookTest.createNotes();
		book.setNotes(notes);
		Note foundNote = book.getNotes().get(3);
		assertEquals("4", foundNote.getId());


		assertTrue(book.deleteNote(foundNote));
		
		assertEquals(4,  book.getNotes().size());
		
		
		for(Note n: book.getNotes())
		{
			System.out.println(n.getId() + " " + n.getContent());
		}
		
		assertTrue(book.deleteNote("2"));
		
		
		assertEquals(3, book.getNotes().size());
		
		assertFalse(book.deleteNote("7"));
		
		
		foundNote.setId("6");
		
		assertFalse(book.deleteNote(foundNote));
		
		
		for(Note n: book.getNotes())
		{
			System.out.println(n.getId() + " " + n.getContent());
		}
		
		
		
		//test title can't start with whitespace
		//book = new NotebookXML();
		System.out.println(book.getId());
		book.setTitle(" Title");
		assertEquals("FIRST", book.getTitle());
		
		
		
		
	}

}
