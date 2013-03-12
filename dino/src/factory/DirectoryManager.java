package factory;

import implementation.Notebook;

import java.util.List;
import java.util.Map;

import utilities.NotebookAlreadyExistsException;
import utilities.NotebookNotFoundException;

public interface DirectoryManager {

	/**
	 * Finds a notebook in the directory by its ID
	 * @param id the wanted notebook's ID 
	 * @return the found Notebook or null otherwise
	 */
	 Notebook findById(String id);

	/**
	 * Find a notebook by its unique title
	 * @param title title of notebook to find
	 * @return the wanted notebook or null otherwise
	 */
	Notebook findByTitle(String title);
	List<Notebook> getAllNotebooks();
	Notebook getNotebook(String id);
	String createNotebook(String title, String primaryURL) throws NotebookAlreadyExistsException;
			
	String createNotebook(String title) throws NotebookAlreadyExistsException;
	void deleteNotebook(String id) throws NotebookNotFoundException;			

	//returning a copy of the DB for testing purposes
	Map<String, Notebook> getCopyDB();

}