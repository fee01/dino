package implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import factory.DirectoryManager;

import utilities.NotebookAlreadyExistsException;
import utilities.NotebookNotFoundException;


/**
 * @author  JFee
 */
public class Directory implements DirectoryManager 
{
	
	//Singleton Pattern used
	//public final static DirectoryManager INSTANCE = new Directory(); 
	//private Directory(){};
	
	
	
	//the directory backed by HashMap
	private Map<String, Notebook> db = new ConcurrentHashMap<String, Notebook>();
	
	private int IdSetter = 0;
	
	public Directory(Map<String, Notebook> db)
	{
		this.db = db;
	}

	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#findById(java.lang.String)
	 */
	@Override
	public Notebook findById(String id)
	{
		return db.get(id);	
	}
	
	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#findByTitle(java.lang.String)
	 */
	@Override
	public Notebook findByTitle(String title)
	{
		for(Notebook n: this.getAllNotebooks())
		{			
			if(n.getTitle().equalsIgnoreCase(title))
			{
				return n;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#getAllNotebooks()
	 */
	@Override
	public List<Notebook> getAllNotebooks()
	{
		List<Notebook> all = new ArrayList<Notebook>(db.values());
		return all;
	}

	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#getNotebook(java.lang.String)
	 */
	@Override
	public Notebook getNotebook(String id)
	{
		return this.findById(id);
	}

	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#createNotebook(java.lang.String, java.lang.String)
	 */
	@Override
	public String createNotebook(String title, String primaryURL) throws NotebookAlreadyExistsException			
	{
		//check if id is already associated with a notebook
		if(this.findById(primaryURL) != null)
		{
			throw new NotebookAlreadyExistsException("Notebook already in directory");
		}
		
		//check if title already is associated with a notebook
		if(this.findByTitle(title) != null)
		{
			throw new NotebookAlreadyExistsException("Notebook already in directory");
		}
		
		
		String id = this.setID();
		Notebook nb = new Notebook();
		nb.setTitle(title);
		nb.setId(id);
		nb.setPrimaryURL(primaryURL);
		
		db.put(id, nb);
		return id;
	}
	
	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#createNotebook(java.lang.String)
	 */
	@Override
	public String createNotebook(String title)
			throws NotebookAlreadyExistsException
	{
		
		//check if title already is associated with a notebook
		if(this.findByTitle(title) != null)
		{
			throw new NotebookAlreadyExistsException("Notebook already in directory");
		}
		
		
		String id = this.setID();
		Notebook nb = new Notebook();
		nb.setTitle(title);
		nb.setId(id);
		nb.setPrimaryURL(title);
		
		db.put(id, nb);
		return id;
	}
	
	
	
	private String setID()
	{
		IdSetter++;
		return "" + IdSetter;
	}
	


	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#deleteNotebook(java.lang.String)
	 */
	@Override
	public void deleteNotebook(String id)
			throws NotebookNotFoundException
	{
		Notebook found = this.findById(id);
		if(found == null)
		{
			throw new NotebookNotFoundException("Notebook does not exist in directory");
		}
		
		db.remove(found.getId());

	}
	
	public static DirectoryManager getDatabase()
	{
		return null;
		//return INSTANCE;
	}
	
	
	//returning a copy of the DB for testing purposes
	/* (non-Javadoc)
	 * @see implementation.DirectoryManager#getCopyDB()
	 */
	@Override
	public Map<String, Notebook> getCopyDB()
	{
		Map<String, Notebook> copy = new HashMap<String, Notebook>();
		copy.putAll(this.db);
		
		return copy;
	}

}
