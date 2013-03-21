package factory;

import implementation.Directory;

import javax.naming.InitialContext;



/**
 * @author Don
 *
 */
public class EJBFactory implements DirectoryFactory 
{
	
	public EJBFactory() 
	{
		super();
	}
	
	public Directory getDirectory() 
	{
		Directory mgr = null;
		try {
			InitialContext ic = new InitialContext();
			Object o = ic.lookup("implementation.Directory");
			mgr = (Directory) o;
		} catch(Exception e) {
			System.err.println("Exception while looking up backend: " + e);
		}
		return mgr;
	}

	

}
