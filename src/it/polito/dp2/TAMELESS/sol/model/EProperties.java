//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.03 at 03:42:48 PM CET 
//


package it.polito.dp2.TAMELESS.sol.model;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.polito.dp2.TAMELESS.sol.service.jaxb.Properties;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "properties1Entity",
    "properties1Entity1Threat",
    "self"
})
@XmlRootElement(name = "properties")
public class EProperties extends Properties{

	//transient means that this field must not be stored during serialization
	transient private UriBuilder root;
	
	public EProperties(){
		super();
	}
	
	public EProperties(UriBuilder root) {
		this.root = root;
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    public String getProperties1Entity() {
		return root.clone().path("/properties1Entity").toTemplate();
    }

	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    public String getProperties1Entity1Threat() {
		return root.clone().path("/properties1Entity1Threat").toTemplate();
    }

	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getSelf() {
		return root.toTemplate();
    }
    
}
