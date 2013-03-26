package service;

import resource.PrimaryResource;
import utilities.EJBLocator;

import implementation.NotebookList;
import implementation.NotebookXML;


import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

import dino.api.Directory;
import dino.api.Notebook;




@Singleton
@Path("/")
public class PrimarySource implements PrimaryResource {
	private static final Logger logger = Logger.getLogger(PrimarySource.class);

	//@Override
	public Response getAllNotebooks() {
		logger.debug("In get all notebook service");
		
		Directory directory = EJBLocator.getInstance().getDirectoryService();
		List<Notebook> all = directory.getAllNotebooks();

		logger.debug("Received notebooks from EJB " + all.size());
		
		ArrayList<NotebookXML> allXmlNotebook = new ArrayList<NotebookXML>();
		for (Notebook notebook : all) {
			allXmlNotebook.add(new NotebookXML(notebook));
		}
		
		NotebookList notebookList = new NotebookList();
		notebookList.setNotebooks(allXmlNotebook);
		return Response.ok(notebookList, "text/xml").build();
	}

	//@Override
	public Response deleteAllNotebooks() {
		logger.debug("In delete all remote notebook");
		try {
			EJBLocator.getInstance().getDirectoryService().deleteNotebook("delete_all_notebook");
		}
		catch (Exception e) {
			logger.error("Delete All?", e);
		}
		return null;
	}

}
