
package it.polito.dp2.TAMELESS.sol.client;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="totalPages" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="next" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="relation3Entities" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="self" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *                   &lt;element name="entity1Id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                   &lt;element name="entity2Id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                   &lt;element name="entity3Id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="relationName" use="required" type="{}Relation3EntitiesName" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "totalPages",
    "page",
    "next",
    "relation3Entities"
})
@XmlRootElement(name = "relations3Entities")
public class Relations3Entities {

    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger totalPages;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger page;
    @XmlSchemaType(name = "anyURI")
    protected String next;
    @XmlElement(nillable = true)
    protected List<Relations3Entities.Relation3Entities> relation3Entities;

    /**
     * Gets the value of the totalPages property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the value of the totalPages property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalPages(BigInteger value) {
        this.totalPages = value;
    }

    /**
     * Gets the value of the page property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPage(BigInteger value) {
        this.page = value;
    }

    /**
     * Gets the value of the next property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNext() {
        return next;
    }

    /**
     * Sets the value of the next property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNext(String value) {
        this.next = value;
    }

    /**
     * Gets the value of the relation3Entities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relation3Entities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelation3Entities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Relations3Entities.Relation3Entities }
     * 
     * 
     */
    public List<Relations3Entities.Relation3Entities> getRelation3Entities() {
        if (relation3Entities == null) {
            relation3Entities = new ArrayList<Relations3Entities.Relation3Entities>();
        }
        return this.relation3Entities;
    }


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
    public static class Relation3Entities {

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

}
