package testing;

import static org.junit.Assert.*;

import implementation.Note;
import implementation.Notebook;
import implementation.NotebookList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;





/**
 * @author  JFee
 */
public class NotebookListTest
{
	
	private NotebookList list = new NotebookList();
	private static int i = 0;
	private static int j = 0;
	
	private List<Notebook> notebooks;
	
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
			n.setId("" + i);
			//System.out.println(n.getId() + " " + n.getContent());
		}
		
		
		
		
		return temp;
		
	}
	
	private static List<Notebook> createBooks()
	{
		Notebook one = new Notebook();
		Notebook two = new Notebook();
		Notebook three = new Notebook();
		Notebook four = new Notebook();
		Notebook five = new Notebook();
		List<Notebook> temp = new ArrayList<Notebook>();
		temp.add(one);
		temp.add(two);
		temp.add(three);
		temp.add(four);
		temp.add(five);
		
		
		for(Notebook nb: temp)
		{
			j++;
			String id = j + "";
			nb.setId(id);
			nb.setTitle("The title of Notebook " + id);
			nb.setNotes(NotebookListTest.createNotes());
			
			//System.out.println(nb.getId() + " " + nb.getTitle());
			
			//for(Note n: nb.getNotes())
			//{
			//	System.out.println("  " + n.getId() + " " + n.getContent());
			//}
			
			
		}
		
		
		return temp;
		
	}
	

	@Before
	public void setUp() throws Exception
	{
		notebooks = NotebookListTest.createBooks();
		
	}

	@Test
	public void test()
	{
		list.setNotebooks(notebooks);
		assertEquals(list.getNotebooks(), notebooks);
		assertEquals("1", list.getNotebooks().get(0).getId());
		
	}

}
