package testing;

import static org.junit.Assert.*;
import implementation.Note;
import implementation.NotebookXML;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import db.NotebookServerDatabase;

public class NotebookServerDBTest
{
	private NotebookServerDatabase nbServerDB;
	
	private NotebookXML book1;
	private NotebookXML book2;
	private NotebookXML book3;
	
	private List<NotebookXML> list;
	
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
		nbServerDB = new NotebookServerDatabase();
		book1 = new NotebookXML();
		book2 = new NotebookXML();
		book3 = new NotebookXML();
		
		book1.setTitle("first");
		book1.setId("nb-1");
		
		book2.setTitle("second");
		book2.setId("nb-2");
		
		book3.setTitle("third");
		book3.setId("nb-3");
		
	}

	@Test
	public void test()
	{
		assertEquals(book1.getId(), "NB-1");
		boolean caught = false;
		try
		{
			nbServerDB.add("nb-1", book1);
			nbServerDB.add("nb-2", book2);
			nbServerDB.add("nb-3", book3);
			
		}
		catch (Exception e)
		{
			caught = true;
		}
		
		assertFalse(caught);
		
		list = nbServerDB.findAll();
		assertEquals(3,  list.size());
		assertEquals("NB-1", list.get(0).getId());
		assertEquals("first", list.get(0).getTitle());
		
		caught = false;
		try
		{
			nbServerDB.remove("nb-1");
		}
		catch (Exception e)
		{
			caught = true;
		}
		
		assertFalse(caught);
		
		//get the updated list of db
		list = nbServerDB.findAll();
		assertEquals(2, list.size());
		assertEquals("second", list.get(0).getTitle());
		
		NotebookXML bookNew2 = new NotebookXML();
		bookNew2.setId("NB-2");
		bookNew2.setTitle("new second");
		caught = false;
		try
		{
			nbServerDB.update("nb-2", bookNew2);
		}
		catch (Exception e)
		{
			caught = true;
		}
		
		assertFalse(caught);
		
		list = nbServerDB.findAll();
		assertEquals("NB-2",  list.get(0).getId());
		assertEquals("NB-2", bookNew2.getId());
		
		assertEquals(book3, nbServerDB.findById("nb-3"));
		
		
		
		
	}

}
