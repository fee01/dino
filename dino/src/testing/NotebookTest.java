package testing;

import static org.junit.Assert.*;

import implementation.Note;
import implementation.Notebook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;



/**
 * @author  JFee
 */
public class NotebookTest
{
	private ArrayList<Note> notes;
	private Notebook book;
	private static int i = 0;
	private static ArrayList<Note> createNotes()
	{
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
			n.setId(i);
		
		}
		
		
		return temp;
		
	}

	@Before
	public void setUp() throws Exception
	{
		notes = NotebookTest.createNotes();
		book = new Notebook();
		
	}

	@Test
	public void test()
	{
		book.setId("1");
		book.setNotes(notes);
		book.setTitle("FIRST");
		
		assertEquals("1", book.getId());
		assertEquals("FIRST", book.getTitle());
		assertEquals(1, book.getNotes().get(0).getId());
		
	}

}
