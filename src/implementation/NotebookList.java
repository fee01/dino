package implementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;





/**
 * List of Notebooks
 * @author   JFee
 */
@XmlRootElement (name="notebook-list")
public class NotebookList implements Serializable
{
	private static final long serialVersionUID = 6477831092880918053L;
	private List<NotebookXML> notebooks;
	
	public NotebookList() {
		super();
	}

	@XmlElement (name="notebook")
	public List<NotebookXML> getNotebooks() {return notebooks;}
	public void setNotebooks(List<NotebookXML> notebooks) {this.notebooks = notebooks;}
	

}
