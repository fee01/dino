package factory;

import implementation.Directory;

import javax.servlet.ServletConfig;
import utilities.util;

public class Factory implements DirectoryFactory 
{
	
	public static final String defaultFactoryClass = "factory.LocalFactory";
	public static final String factoryClassParameter = "factory.class";
	
	private ServletConfig config;
	private static DirectoryFactory myFactory;
	
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
				if( fact instanceof DirectoryFactory ) {
					myFactory = (DirectoryFactory) fact;
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

	
	public Directory getDirectory()
	{
		init();
		return myFactory.getDirectory();
	}

	
}
