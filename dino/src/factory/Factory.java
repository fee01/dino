package factory;

import javax.servlet.ServletConfig;
import utilities.util;

public class Factory implements DirectoryManagerFactory {
	
	public static final String defaultFactoryClass = "factory.LocalFactory";
	public static final String factoryClassParameter = "factory.class";
	
	private ServletConfig config;
	private static DirectoryManagerFactory myFactory;
	
	public Factory(ServletConfig config) {
		this.config = config;
	}
	
	private synchronized void init() {
		if( myFactory == null ) {
			String factoryClassName = config.getInitParameter(factoryClassParameter);
			System.out.println("factoryClassName="+factoryClassName);
			if(util.isMissing(factoryClassName) || factoryClassName.length() < 1) factoryClassName = defaultFactoryClass;
			System.out.println("factoryClassName="+factoryClassName);
			try {
			Class<?> factClass = Class.forName(factoryClassName);
				Object fact = factClass.newInstance();
				if( fact instanceof DirectoryManagerFactory ) {
					myFactory = (DirectoryManagerFactory) fact;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public DirectoryManager getDirectoryManager() {
		init();
		return myFactory.getDirectoryManager();
	}
}
