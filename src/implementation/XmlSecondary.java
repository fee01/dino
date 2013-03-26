package implementation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="secondary")
public class XmlSecondary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7097375823758741450L;
	private String url;
	private String delete;

	@XmlElement(name="url")
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	
	@XmlElement(name="delete")
	public String getDelete() {return delete;}
	public void setDelete(String delete) {this.delete = delete;}

}
