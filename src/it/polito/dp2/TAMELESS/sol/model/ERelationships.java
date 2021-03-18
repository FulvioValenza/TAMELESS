package it.polito.dp2.TAMELESS.sol.model;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.polito.dp2.TAMELESS.sol.service.jaxb.Relationships;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "relations2Entities",
 "relations3Entities",
 "relations2Entities1Threat",
 "relations1Entity1Threat",
 "self"
})
@XmlRootElement(name = "relationships")
public class ERelationships extends Relationships{

	//transient means that this field must not be stored during serialization
	transient private UriBuilder root;
	
	public ERelationships(){
		super();
	}
	
	public ERelationships(UriBuilder root) {
		this.root = root;
	}
	
	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getRelations2Entities() {
		return root.clone().path("/relations2Entities").toTemplate();
	}

	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getRelations3Entities() {
		return root.clone().path("/relations3Entities").toTemplate();
	}

	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getRelations2Entities1Threat() {
		return root.clone().path("/relations2Entities1Threat").toTemplate();
	}

	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getRelations1Entity1Threat() {
		return root.clone().path("/relations1Entity1Threat").toTemplate();
	}

	@Override
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
	public String getSelf() {
		return root.toTemplate();
	}
}
