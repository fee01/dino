package implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import utilities.NotebookAlreadyExistsException;
import utilities.NotebookNotFoundException;


/**
 * @author  JFee
 */
public class Directory 
{	
	//Singleton Pattern used
	public final static Directory INSTANCE = new Directory(); 
	private Directory(){};
	
	//the directory backed by HashMap
	private Map<String, Notebook> db = new ConcurrentHashMap<String, Notebook>();
	
	private int IdSetter = 0;

	/**
	 * Finds a notebook in the directory by its ID
	 * @param id the wanted notebook's ID 
	 * @return the found Notebook or null otherwise
	 */
	public Notebook findById(String id)
	{
		return db.get(id);	
	}
	
	/**
	 * Find a notebook by its unique title
	 * @param title title of notebook to find
	 * @return the wanted notebook or null otherwise
	 */
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

	public List<Notebook> getAllNotebooks()
	{
		List<Notebook> all = new ArrayList<Notebook>(db.values());
		return all;
	}

	public Notebook getNotebook(String id)
	{
		return this.findById(id);
	}

	public String createNotebook(String title, String primaryURL)
			throws NotebookAlreadyExistsException
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
	
	public static Directory getDatabase()
	{
		return INSTANCE;
	}
	
	
	//returning a copy of the DB for testing purposes
	public Map<String, Notebook> getCopyDB()
	{
		Map<String, Notebook> copy = new HashMap<String, Notebook>();
		copy.putAll(this.db);
		
		return copy;
	}

}
