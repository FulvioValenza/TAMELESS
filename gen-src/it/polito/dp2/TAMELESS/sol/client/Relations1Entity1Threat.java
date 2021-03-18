
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
 *         &lt;element name="relation1Entity1Threat" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="self" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *                   &lt;element name="entity1Id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                   &lt;element name="threatId" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="relationName" use="required" type="{}Relation1Entity1ThreatName" />
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
    "relation1Entity1Threat"
})
@XmlRootElement(name = "relations1Entity1Threat")
public class Relations1Entity1Threat {

    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger totalPages;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger page;
    @XmlSchemaType(name = "anyURI")
    protected String next;
    @XmlElement(nillable = true)
    protected List<Relations1Entity1Threat.Relation1Entity1Threat> relation1Entity1Threat;

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
     * Gets the value of the relation1Entity1Threat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relation1Entity1Threat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelation1Entity1Threat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Relations1Entity1Threat.Relation1Entity1Threat }
     * 
     * 
     */
    public List<Relations1Entity1Threat.Relation1Entity1Threat> getRelation1Entity1Threat() {
        if (relation1Entity1Threat == null) {
            relation1Entity1Threat = new ArrayList<Relations1Entity1Threat.Relation1Entity1Threat>();
        }
        return this.relation1Entity1Threat;
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
    public static class Relation1Entity1Threat {

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

}
