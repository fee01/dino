package utilities;

import implementation.NotebookXML;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import org.apache.log4j.Logger;


import dino.api.Directory;

//import dino.api.Directory;
//import implementation.Directory;
/**
 *Derived direct from dino
 * senthil ramasamy
 * https://bitbucket.org/senthil/team2/overview
 *
 */
public class EJBLocator {
	
	private static final Logger logger = Logger.getLogger(EJBLocator.class);
	private static EJBLocator self = new EJBLocator();
    private static Map<String,Object> cacheMap = new HashMap<String,Object>();
   
    private static final String directory_jndi_name = "dino.api.Directory";
    private static final String message_queue_name = "syncmessages";
     
    private static String jndiHost = "localhost";
    private static String jndiPort = null;

    private EJBLocator() {
        super();
    }

    public Object lookup(String jndiName) {
    	String cacheName = jndiHost + jndiName;
        Object object =cacheMap.get(cacheName);
        if(object==null) {
            try {
            	InitialContext context;
            	
                Properties props = new Properties();
    			props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
                if( jndiPort != null ) {
                    props.setProperty("org.omg.CORBA.ORBInitialPort", jndiPort);
                }
                props.setProperty("org.omg.CORBA.ORBInitialHost", jndiHost);
                
                context = new InitialContext(props);
                NamingEnumeration<NameClassPair> list = context.list("");
                String myJndiName = jndiName;
                while (list.hasMoreElements()) {
                	NameClassPair ncp = list.next();
                	myJndiName = ncp.getName();
                	if (myJndiName.indexOf(jndiName) != -1) {
                		logger.debug("Matching JNDI Name found : " + myJndiName);
                		break;
                	}
                }
                object = context.lookup(myJndiName);
                cacheMap.put(cacheName,object);
            }catch(Exception ex){
            	logger.fatal("Error finding EJB", ex);
                return null;
            } 
        }
        
        return object;    	

    }

    public static EJBLocator getInstance() {
        return self;
    }
    
    public Directory getDirectoryService() {
    	Directory directory = (Directory) lookup(directory_jndi_name);
        return directory;
    }

    public static void setJndiHostPort(String hostport) {
    	StringTokenizer tokenizer = new StringTokenizer(hostport, ":");
    	int cnt = tokenizer.countTokens();
   		jndiHost = tokenizer.nextToken();
   		if (cnt > 1) {
   			jndiPort = tokenizer.nextToken();
   		}
   		return;
    }

	public static String getJndiHost() {
		return jndiHost;
	}

	public static void setJndiHost(String jndiHost) {
		EJBLocator.jndiHost = jndiHost;
	}

	public static String getJndiPort() {
		return jndiPort;
	}

	public static void setJndiPort(String jndiPort) {
		EJBLocator.jndiPort = jndiPort;
	}

	public void sendQueue(String secondaryServer, NotebookXML xmlNotebook) { 
		logger.debug("Sending Sync Messages to " + secondaryServer);
			try {
				
				InitialContext context;
				Properties props = new Properties();
				if (jndiPort != null) {
					props.setProperty("org.omg.CORBA.ORBInitialPort", jndiPort);
				}
				props.setProperty("org.omg.CORBA.ORBInitialHost", secondaryServer);
				context = new InitialContext(props);
				Queue queue  = (Queue) context.lookup(message_queue_name);
				QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
				QueueConnection queueConnection = factory.createQueueConnection();
				QueueSession session = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
				ObjectMessage objectMessage = session.createObjectMessage(xmlNotebook);
				QueueSender sender = session.createSender(queue);
				sender.send(objectMessage);
				logger.debug("Message " + xmlNotebook.getTitle() + " sent successfully.");

			} catch (Exception e) {
				logger.error("JMS Error", e);
			}
	}
    
}
