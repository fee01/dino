package testing;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Before;
import org.junit.Test;

import factory.DirectoryManager;


import implementation.Directory;
import implementation.Notebook;
import implementation.NotebookList;
import service.NotebookSource;

public class NbSourceTest
{
	NotebookSource src;
	DirectoryManager db;

	@Before
	public void setUp() throws Exception
	{
		//src = new NotebookSource();
		db = Directory.getDatabase();
	}

	@Test
	public void test()
	{
		
		
		
	}

}
