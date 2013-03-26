package db;

import implementation.NotebookXML;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


import dino.api.NotebookAlreadyExistsException;
import dino.api.NotebookNotFoundException;



public class NotebookServerDatabase extends Database {

	/**
	 * 
	 */
	private static final long serialVersionUID = -496003595288916161L;
	
	private static Logger logger = Logger.getLogger(NotebookServerDatabase.class);
	private static final NotebookServerDatabase self = new NotebookServerDatabase();

	private static long noteId = 1;
	private static Object noteIdLockObject = new Object();

	public NotebookServerDatabase() {
	}

	public static NotebookServerDatabase getDatabase() {
		return self;
	}

	public NotebookXML findById(String id) 
	{
		logger.debug("In NotebookServerDatabase find notebook by ID");
		NotebookXML nb = (NotebookXML) super.find(id.toUpperCase());
		if (nb != null) {
			logger.debug("returning notebook id is: " + nb.getId());
		}
		else {
			logger.debug("returning notebook id is: null");
		}
		return nb;
	}
	
	public NotebookXML findByTitle(String title) {
		logger.debug("In find by title");
		@SuppressWarnings("unchecked")
		List<NotebookXML> list = (List<NotebookXML>) getAllDisconnected();
		Iterator<NotebookXML> it = list.iterator();
		NotebookXML nb;
		while (it.hasNext()) {
			nb = it.next();
			if (nb.getTitle().equalsIgnoreCase(title)) {
				return nb;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<NotebookXML> findAll() {
		return (List<NotebookXML>) getAllDisconnected();
	}
	
	public void update(String id, NotebookXML noteBook) throws NotebookNotFoundException {
		logger.debug("In notebook update");
		if (findById(id) == null) {
			throw new NotebookNotFoundException();
		}
		super.update(id.toUpperCase(), noteBook);
	}

	public void add(String id, NotebookXML noteBook) throws NotebookAlreadyExistsException {
		logger.debug("In notebook add");
		id = id.toUpperCase();
		if (db.containsKey(id)) {
			throw new NotebookAlreadyExistsException(id);
		}
		super.update(id, noteBook);
	}
	
	public String getNextId() {
		long id = 0;
		synchronized (noteIdLockObject) {
			id = noteId++;
		}
		return String.valueOf(id);
	}

	public void remove(String id) throws NotebookNotFoundException {
		logger.debug("In NotebookServerDatabase remove notebook by ID:" + id);
		logger.debug("NotebookServerDatabase searching for Notebook:" + id);
		if (findById(id) == null) {
			logger.debug("NotebookServerDatabase Notebook:" + id + " not found");
			throw new NotebookNotFoundException();
		}
		logger.debug("NotebookServerDatabase Notebook:" + id + " found.  Preparing to delete from db.");
		super.remove(id.toUpperCase());
		logger.debug("NotebookServerDatabase db.remove operation performed on Notebook:" + id);
	}

}