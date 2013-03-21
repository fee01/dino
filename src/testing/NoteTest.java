package testing;

import static org.junit.Assert.*;
import implementation.Note;

import org.junit.Before;
import org.junit.Test;



/**
 * @author  JFee
 */
public class NoteTest
{

	private Note note;

	@Before
	public void setUp() throws Exception
	{
		note = new Note();
	}

	@Test
	public void test()
	{
		String content = "This is a note";
		note.setContent(content);
		note.setId("" + 1);
		
		assertEquals("This is a note", note.getContent());
		assertEquals("1", note.getId());
	}

}
