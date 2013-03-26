package proxy;

import implementation.NotebookXML;
import implementation.XmlSecondary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;



import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import dino.api.NotebookNotFoundException;


public class ClientProxy {
	
	private static Logger logger = Logger.getLogger(ClientProxy.class);
	
	private static final String xmlType = "text/xml";
	
	public static int processRequest(String urlString, String method, byte[] input, HttpServletResponse response) {
		logger.debug("In process Request ....");
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoInput(true);
			if (input != null) {
				connection.setDoOutput(true);
				connection.addRequestProperty("Content-Length", String.valueOf(input.length));
				OutputStream os = connection.getOutputStream();
				os.write(input);
			}
			else {
				if (method.equalsIgnoreCase("POST")) {
					connection.setDoOutput(true);
					connection.addRequestProperty("Content-Length", "0");
					OutputStream os = connection.getOutputStream();
					os.write("".getBytes());
				}
			}
			
			if (response != null) {
				byte[] buffer = new byte[1024];
				InputStream ips = connection.getInputStream();
				PrintWriter pw = response.getWriter();
				while (true) {
					int len = ips.read(buffer, 0, buffer.length);
					if (len == -1) {
						break;
					}
					
					pw.write(new String(buffer, 0, len));
					logger.debug(new String(buffer, 0, len));
				}
			}
			return connection.getResponseCode();
		}
		catch (MalformedURLException e) {
			logger.error(e);
		} catch (ProtocolException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return 500;
	}
	
	public static Response getNotebookByID(String notebookId, String primaryUrlString, HttpServletResponse response) throws NotFoundException, NotebookNotFoundException {
		StringBuffer urlString = new StringBuffer(primaryUrlString);
		if (urlString.charAt(urlString.length()-1) != '/') {
			urlString.append('/');
		}
		urlString.append("notebook/");
		urlString.append(notebookId);
		
		int rc= processRequest(urlString.toString(), "GET", null, response);
		return Response.status(rc).build();
	}
	
	public static Response sync(String secondaryServer, boolean delete, String notebookId, NotebookXML notebook, HttpServletResponse response) {
		logger.debug("Inside Sync for ... " + notebook.getId());
		
		try {
			byte[] input = null;
	
			if (!delete) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(notebook);
				input = baos.toByteArray();
			}
			StringBuffer urlString = new StringBuffer(getUrlString(secondaryServer, "notebook"));
			urlString.append("/sync/");
			urlString.append(notebookId);
			int rc = processRequest(urlString.toString(), "POST", input, response);
			return Response.status(rc).build();

		} catch (IOException e) {
			logger.error(e);
		}
		return Response.serverError().build();
	}

	public static Response deleteNotebook(String secondaryServer, String id) {
		logger.debug("Inside delete for ... " + id);
		
		Client client = Client.create();
		WebResource resource = client.resource(secondaryServer);
		resource.path("notebook/"+id).delete();
		return Response.ok().build();
	}
	
	public static Response getNoteByNoteid(String notebookId, String noteId, String primaryNotebookUrl, HttpServletResponse response) {
		StringBuffer urlString = new StringBuffer(getUrlString(primaryNotebookUrl, "notes"));
		urlString.append("/");
		urlString.append(notebookId);
		urlString.append("/");
		urlString.append(noteId);
		
		int rc= processRequest(urlString.toString(), "GET", null, response);
		return Response.status(rc).build();
	}

	public static void updateSecondaryForNotebook(String nbid, String primaryNotebookUrl, String secondaryUrl) {
		logger.debug("Update Secondary for " + nbid);
		XmlSecondary secondary = new XmlSecondary();
		secondary.setUrl(secondaryUrl);
		secondary.setDelete("NO");
		
		Client client = Client.create();
		WebResource resource = client.resource(primaryNotebookUrl);
		resource.path("notebook/secondary/" + nbid).entity(secondary).type(xmlType).post();
	}

	public static void deleteSecondaryForNotebook(String nbid, String primaryNotebookUrl, String secondaryUrl) {
		logger.debug("delete Secondary for " + nbid);
		XmlSecondary secondary = new XmlSecondary();
		secondary.setUrl(secondaryUrl);
		secondary.setDelete("YES");
		
		Client client = Client.create();
		WebResource resource = client.resource(primaryNotebookUrl);
		resource.path("notebook/secondary/" + nbid).entity(secondary).type(xmlType).post();
	}

	public static String getUrlString(String baseUrl, String path) {
		StringBuffer urlString = new StringBuffer(baseUrl);
		if (urlString.charAt(urlString.length()-1) != '/') {
			urlString.append('/');
		}
		urlString.append(path);
		return urlString.toString();
	}

}
