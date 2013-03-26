package testing;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Before;
import org.junit.Test;

import db.NotebookServerDatabase;
import dino.api.NotebookAlreadyExistsException;



import implementation.NotebookXML;
import implementation.NotebookList;
import service.NotebookSource;

public class NbSourceTest
{
	NotebookSource src;
	NotebookServerDatabase db;

	@Before
	public void setUp() throws Exception
	{
		src = new NotebookSource();
		db = NotebookServerDatabase.getDatabase();
	}

	@Test
	public void test() throws NotebookAlreadyExistsException
	{
		
		
		NotebookList list = (NotebookList) src.getAllNoteBooksFromServer().getEntity();
		assertTrue(list.getNotebooks().isEmpty());
		NotebookXML temp = new NotebookXML();
		temp.setTitle("Title1");
		
		//create notebooks
		NotebookXML book1 = new NotebookXML();
		NotebookXML book2 = new NotebookXML();
		NotebookXML book3 = new NotebookXML();
		
		book1.setTitle("first");
		book1.setId("nb-1");
		
		book2.setTitle("second");
		book2.setId("nb-2");
		
		book3.setTitle("third");
		book3.setId("nb-3");
		
		//add to notebookserverdb
		db.add(book1.getId(), book1);
		db.add(book2.getId(), book2);
		db.add(book3.getId(), book3);
		
		//verify the findlocalNotebook works 
		assertEquals(book1, src.findLocalNotebook("nb-1"));
		assertEquals(book2, src.findLocalNotebook("nb-2"));
		assertNull(src.findLocalNotebook("NB-6"));
		
		
		
	}

}
