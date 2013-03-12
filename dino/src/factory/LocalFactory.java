package factory;

import implementation.Directory;
import implementation.Notebook;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalFactory implements DirectoryManagerFactory {

	private Map<String, Notebook> db = new ConcurrentHashMap<String, Notebook>();
	
	public LocalFactory()
	{
		super();
	}
	
	@Override
	public Directory getDirectoryManager() {
		// TODO Auto-generated method stub
		return new Directory(db);
	}
	
}
