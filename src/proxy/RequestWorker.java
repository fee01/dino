package proxy;
/*
import org.apache.log4j.Logger;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import utilities.NoteBookException;*/



public class RequestWorker extends Thread implements Runnable{
	/*
	private static final Logger logger = Logger.getLogger(RequestWorker.class);
	private Context context;
	private Socket requester;
	
	public RequestWorker()
	{
		context = ZMQ.context(1);
		requester = context.socket(ZMQ.REQ);
		requester.connect("tcp://localhost:8080/sync");
		
	}
	
	@Override
	public void run()
	{
		super.run();
		for(int i = 0; i < 10; i++)
		{
			requester.send("Hello", 0);
			String reply = requester.recvStr(0);
			logger.debug(reply);
			
		}
	}*/

}
