package factory;

import implementation.Directory;


public class LocalFactory implements DirectoryFactory 
{

	private Directory d;
	
	public LocalFactory()
	{
		super();
	}
	
	
	public Directory getDirectory() 
	{
		d = Directory.getDatabase();
		return d;
	}
	
}
