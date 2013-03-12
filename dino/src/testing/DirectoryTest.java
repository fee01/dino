package testing;

import static org.junit.Assert.*;

import factory.DirectoryManager;
import implementation.Directory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;



import org.junit.Before;
import org.junit.Test;

import implementation.Notebook;

import utilities.NotebookAlreadyExistsException;
import utilities.NotebookNotFoundException;



/**
 * @author  JFee
 */
public class DirectoryTest
{
	DirectoryManager d = null;

	@Before
	public void setUp() throws Exception
	{
		d = new Directory(new ConcurrentHashMap<String, Notebook>());
	}

	@Test
	public void testFindsCopiesCreates()
	{
		boolean found = false;
		try
		{
			d.createNotebook("One", "1 URL");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
		}
		
		assertFalse(found);
		
		assertTrue(d.getCopyDB().containsKey("1"));
		assertEquals("One", d.getCopyDB().get("1").getTitle());
		
		List<Notebook> list = d.getAllNotebooks();
		assertEquals(1, list.size());
		assertEquals("1", list.get(0).getId());
		assertEquals("1 URL", list.get(0).getPrimaryURL());
		
		assertNull(d.findById("ITSNull"));
		Notebook one = d.findById("1");
		assertNotNull(one);
		assertEquals(one.getTitle(), "One");
		
		try
		{
			d.createNotebook("One", "1 URL");
		}
		catch(NotebookAlreadyExistsException e)
		{
			found = true;
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
		}
		
		assertTrue(found);
	
		Notebook otherOne = d.getNotebook("1");
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
		
	}

}
