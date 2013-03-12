package factory;

import implementation.Directory;

import javax.naming.InitialContext;



/**
 * @author Don
 *
 */
public class EJBFactory implements DirectoryManagerFactory {
	
	public EJBFactory() {
		super();
	}
	
	public DirectoryManager getDirectoryManager() {
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
