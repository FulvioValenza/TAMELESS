package it.polito.dp2.TAMELESS.sol.model;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.polito.dp2.TAMELESS.sol.service.jaxb.System;


//impongo ordine alle varie proprietà che altrimenti sono convertite in ordine casuale
@XmlType(name = "", propOrder = {
    "entities",
    "threats",
    "relationships",
    "properties",
    "results",
    "self"
})
//imporsto l'elemento radice XML, senza questo, viene ritornato un JSON
@XmlRootElement(name = "system")
public class ESystem extends System{
	
	//transient means that this field must not be stored during serialization
	transient private UriBuilder root;
	
	public ESystem(){
		super();
	}
	
	public ESystem(UriBuilder root) {
		this.root = root;
	}

	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getEntities() {
		/* UriBuilder utility class for building URI
		 * .clone() return a copy of the uriBuilder
		 * .path() return a new UriBuilder with "entities" added to the end of the URI(it doesn't matter "/" if present or not )
		 * .toTemplate return the URI string of the URIBuilder
		 */
		
		return root.clone().path("/entities").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getThreats() {
		return root.clone().path("threats").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getRelationships() {
		return root.clone().path("relationships").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getProperties() {		
		return root.clone().path("properties").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getResults() {		
		return root.clone().path("results").toTemplate();
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getSelf() {
		return root.toTemplate();
	}

}