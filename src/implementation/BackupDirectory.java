package implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import utilities.NoteBookException;
import utilities.NotebookAlreadyExistsException;
import utilities.NotebookNotFoundException;

public class BackupDirectory
{
private String urlBase = "http:localhost:8080/dino/secondary";
	
	//Singleton Pattern used
	public final static BackupDirectory INSTANCE = new BackupDirectory(); 
	protected BackupDirectory(){};
	
	//the directory backed by HashMap
	private Map<String, Notebook> db = new ConcurrentHashMap<String, Notebook>();
	
	
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

	public Notebook getNotebook(String id) throws NotebookNotFoundException
	{
		
		if(this.findById(id) == null)
		{
			throw new NotebookNotFoundException();		
		}
		return this.findById(id);
	}

	public String createNotebook(Notebook nb)
			throws NotebookAlreadyExistsException
	{
		//check if id is already associated with a notebook
		if(this.findById(nb.getId()) != null)
		{
			throw new NotebookAlreadyExistsException("Notebook already in directory");
		}
		
		//check if title already is associated with a notebook
		if(this.findByTitle(nb.getTitle()) != null)
		{
			throw new NotebookAlreadyExistsException("Notebook already in directory");
		}
		
		
		
		nb.setSecondaryURL(urlBase + "/" + nb.getId() + "/");
		
		db.put(nb.getId(), nb);
		return nb.getId();
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
	
	
	public static BackupDirectory getDatabase()
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
	
	
	public void updateBackupDatabase(String nbId, Notebook nb) throws NoteBookException
	{
		if(db.containsKey(nbId))
		{
			db.put(nbId, nb);
		}
		else
		{
			throw new NotebookNotFoundException("Could not update notebook, no Notebook was found.");
		}
		
	}


}
