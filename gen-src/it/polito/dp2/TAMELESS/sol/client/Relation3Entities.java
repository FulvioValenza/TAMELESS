
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
 *         &lt;element name="entity3Id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *       &lt;attribute name="relationName" use="required" type="{}Relation3EntitiesName" />
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
    "entity3Id"
})
@XmlRootElement(name = "relation3Entities")
public class Relation3Entities {

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
    protected String entity3Id;
    @XmlAttribute(name = "relationName", required = true)
    protected Relation3EntitiesName relationName;

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
     * Gets the value of the entity3Id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity3Id() {
        return entity3Id;
    }

    /**
     * Sets the value of the entity3Id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity3Id(String value) {
        this.entity3Id = value;
    }

    /**
     * Gets the value of the relationName property.
     * 
     * @return
     *     possible object is
     *     {@link Relation3EntitiesName }
     *     
     */
    public Relation3EntitiesName getRelationName() {
        return relationName;
    }

    /**
     * Sets the value of the relationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Relation3EntitiesName }
     *     
     */
    public void setRelationName(Relation3EntitiesName value) {
        this.relationName = value;
    }

}
