package testing;

import static org.junit.Assert.*;



import java.util.List;



import org.junit.Before;
import org.junit.Test;

import utilities.EJBLocator;

import com.sun.faces.config.DbfFactory;

import db.NotebookServerDatabase;
import dino.api.BadAddressException;
import dino.api.Directory;
import dino.api.Notebook;
import dino.api.NotebookAlreadyExistsException;
import dino.api.NotebookNotFoundException;

import implementation.NotebookXML;



/**
 * @author  JFee
 */
public class DirectoryTest
{
	Directory d = EJBLocator.getInstance().getDirectoryService();
	NotebookServerDatabase db = NotebookServerDatabase.getDatabase();

	@Before
	public void setUp() throws Exception
	{
		
	}

	@Test
	public void testFindsCopiesCreates()
	{
		/*
		boolean found = false;
		try
		{
			d.createNotebook("One", "1 URL");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
		} catch (BadAddressException e) {
			// TODO Auto-generated catch block
			found = false;
		}
		
		assertFalse(found);
		
		assertTrue(db.findById("1") != null);
		assertEquals("One", db.findById("1").getTitle());
		
		List<Notebook> list = d.getAllNotebooks();
		assertEquals(1, list.size());
		assertEquals("1", list.get(0).getId());
		assertEquals("1 URL", list.get(0).getPrimaryNotebookUrl());
		
		assertNull(db.findById("ITSNull"));
		NotebookXML one = db.findById("1");
		assertNotNull(one);
		assertEquals(one.getTitle(), "One");
		
		try
		{
			d.createNotebook("One", "1 URL");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
		} catch (BadAddressException e) {
			// TODO Auto-generated catch block
			found = false;
		}
		
		assertTrue(found);
		
		found = false;
		try
		{
			d.createNotebook("One", "NotTheSame");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
		} catch (BadAddressException e) {
			// TODO Auto-generated catch block
			found = false;
		}
		
		assertTrue(found);
	
		found = false;
		Notebook otherOne = null;
		try 
		{
			otherOne = d.getNotebook("1");
		}
		catch (Exception e) 
		{
		
			found = true;
		}
		assertFalse(found);
		assertNotNull(otherOne);
		
		
		found = false;
		
		try
		{
			d.deleteNotebook("This should not work");
		}
		catch(NotebookNotFoundException e)
		{
			found = true;
		}
		
		assertTrue(found);
		
		
		found = false;
		try
		{
			d.deleteNotebook("1");
		}
		catch(NotebookNotFoundException e)
		{
			found = true;
		}
		
		
		assertFalse(found);
		assertTrue(d.getAllNotebooks().isEmpty());
		
		found = false;
		try
		{
			d.createNotebook("1", "1 URL");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
		} catch (BadAddressException e) {
			// TODO Auto-generated catch block
			found = false;
		}
		
		assertFalse(found);
		
		found = false;
		try
		{
			d.createNotebook("2", "2 URL");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
		} catch (BadAddressException e) {
			// TODO Auto-generated catch block
			found = false;
		}
		
		assertFalse(found);
		
		found = false;
		try
		{
			d.createNotebook("3", "3 URL");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
		} catch (BadAddressException e) {
			// TODO Auto-generated catch block
			found = false;
		}
		
		assertFalse(found);
		
		assertEquals(3, d.getAllNotebooks().size());
		
		*/
		
	}
	

}
