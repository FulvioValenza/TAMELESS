
package it.polito.dp2.TAMELESS.sol.client;

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
 *         &lt;element name="entity2Id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="threatId" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *       &lt;attribute name="relationName" use="required" type="{}Relation2Entities1ThreatName" />
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
    "entity2Id",
    "threatId"
})
@XmlRootElement(name = "relation2Entities1Threat")
public class Relation2Entities1Threat {

    @XmlSchemaType(name = "anyURI")
    protected String self;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String entity1Id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String entity2Id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String threatId;
    @XmlAttribute(name = "relationName", required = true)
    protected Relation2Entities1ThreatName relationName;

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
     * Gets the value of the entity2Id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity2Id() {
        return entity2Id;
    }

    /**
     * Sets the value of the entity2Id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity2Id(String value) {
        this.entity2Id = value;
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
     *     {@link Relation2Entities1ThreatName }
     *     
     */
    public Relation2Entities1ThreatName getRelationName() {
        return relationName;
    }

    /**
     * Sets the value of the relationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Relation2Entities1ThreatName }
     *     
     */
    public void setRelationName(Relation2Entities1ThreatName value) {
        this.relationName = value;
    }

}
