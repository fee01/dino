package proxy;
/*
import implementation.Notebook;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import service.ConfigSource;
import utilities.EJBLocator;
*/
public class Responder extends Thread implements Runnable
{
	/*
	private Context context;
	Socket responder;
	public Responder()
	{
		context = ZMQ.context(1);
		responder = context.socket (ZMQ.REP);
	    responder.connect(ConfigSource.getHostNameAndPort(null, null) + "/sync/");
	}
	
	public void run()
	{
		super.run();
		while(!Thread.currentThread().isInterrupted())
		{
			String string = responder.recvStr(0);
			//EJBLocator.getInstance().getDirectoryService().getDatabase().add("100", new Notebook());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responder.send("testing");
		}
		
	}	*/


}
