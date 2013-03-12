package factory;

import implementation.Directory;
import implementation.Notebook;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Stateless;



@Stateless
public class DirectoryManagerBean extends Directory implements DirectoryManager {
	private static Map<String, Notebook> db = new ConcurrentHashMap<String, Notebook>();
	
	public DirectoryManagerBean() {
		super(db);
	}

}