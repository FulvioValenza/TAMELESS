//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.01 at 04:03:41 PM CEST 
//


package it.polito.dp2.TAMELESS.sol.service.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="self" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="entity1Id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="threatId" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *       &lt;attribute name="relationName" use="required" type="{}Relation1Entity1ThreatName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "self",
    "entity1Id",
    "threatId"
})
@XmlRootElement(name = "relation1Entity1Threat")
public class Relation1Entity1Threat {

    @XmlSchemaType(name = "anyURI")
    protected String self;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String entity1Id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String threatId;
    @XmlAttribute(name = "relationName", required = true)
    protected Relation1Entity1ThreatName relationName;

    /**
     * Gets the value of the self property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelf() {
        return self;
    }

    /**
     * Sets the value of the self property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelf(String value) {
        this.self = value;
    }

    /**
     * Gets the value of the entity1Id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity1Id() {
        return entity1Id;
    }

    /**
     * Sets the value of the entity1Id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity1Id(String value) {
        this.entity1Id = value;
    }

    /**
     * Gets the value of the threatId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThreatId() {
        return threatId;
    }

    /**
     * Sets the value of the threatId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThreatId(String value) {
        this.threatId = value;
    }

    /**
     * Gets the value of the relationName property.
     * 
     * @return
     *     possible object is
     *     {@link Relation1Entity1ThreatName }
     *     
     */
    public Relation1Entity1ThreatName getRelationName() {
        return relationName;
    }

    /**
     * Sets the value of the relationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Relation1Entity1ThreatName }
     *     
     */
    public void setRelationName(Relation1Entity1ThreatName value) {
        this.relationName = value;
    }

}
