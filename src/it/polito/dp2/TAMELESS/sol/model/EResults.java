package it.polito.dp2.TAMELESS.sol.model;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.polito.dp2.TAMELESS.sol.service.jaxb.Results;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "canbeCompromised",
 "canbeMalfunctioning",
 "canbeVulnerable",
 "canbeDetected",
 "canbeRestored",
 "canbeFixed",
 "self"
})
@XmlRootElement(name = "results")
public class EResults extends Results{

	//transient means that this field must not be stored during serialization
	transient private UriBuilder root;
	
	public EResults(){
		super();
	}
	
	public EResults(UriBuilder root) {
		this.root = root;
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getCanbeCompromised() {
		return root.clone().path("/canbeCompromised").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getCanbeMalfunctioning() {
		return root.clone().path("/canbeMalfunctioning").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getCanbeVulnerable() {
		return root.clone().path("/canbeVulnerable").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getCanbeDetected() {
		return root.clone().path("/canbeDetected").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getCanbeRestored() {
		return root.clone().path("/canbeRestored").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getCanbeFixed() {
		return root.clone().path("/canbeFixed").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getSelf() {
		return root.toTemplate();
	}

}
