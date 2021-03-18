
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
 *         &lt;element name="property1Entity1Threat" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="self" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *                   &lt;element name="entityId" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                   &lt;element name="threatId" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="propertyName" use="required" type="{}BasicProperty1Entity1ThreatName" />
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
    "property1Entity1Threat"
})
@XmlRootElement(name = "properties1Entity1Threat")
public class Properties1Entity1Threat {

    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger totalPages;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger page;
    @XmlSchemaType(name = "anyURI")
    protected String next;
    @XmlElement(nillable = true)
    protected List<Properties1Entity1Threat.Property1Entity1Threat> property1Entity1Threat;

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
     * Gets the value of the property1Entity1Threat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property1Entity1Threat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty1Entity1Threat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Properties1Entity1Threat.Property1Entity1Threat }
     * 
     * 
     */
    public List<Properties1Entity1Threat.Property1Entity1Threat> getProperty1Entity1Threat() {
        if (property1Entity1Threat == null) {
            property1Entity1Threat = new ArrayList<Properties1Entity1Threat.Property1Entity1Threat>();
        }
        return this.property1Entity1Threat;
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
     *         &lt;element name="entityId" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
     *         &lt;element name="threatId" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
     *       &lt;/sequence>
     *       &lt;attribute name="propertyName" use="required" type="{}BasicProperty1Entity1ThreatName" />
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
        "entityId",
        "threatId"
    })
    public static class Property1Entity1Threat {

        @XmlSchemaType(name = "anyURI")
        protected String self;
        @XmlElement(required = true)
        @XmlSchemaType(name = "anyURI")
        protected String entityId;
        @XmlElement(required = true)
        @XmlSchemaType(name = "anyURI")
        protected String threatId;
        @XmlAttribute(name = "propertyName", required = true)
        protected BasicProperty1Entity1ThreatName propertyName;

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
         * Gets the value of the entityId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEntityId() {
            return entityId;
        }

        /**
         * Sets the value of the entityId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEntityId(String value) {
            this.entityId = value;
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
         * Gets the value of the propertyName property.
         * 
         * @return
         *     possible object is
         *     {@link BasicProperty1Entity1ThreatName }
         *     
         */
        public BasicProperty1Entity1ThreatName getPropertyName() {
            return propertyName;
        }

        /**
         * Sets the value of the propertyName property.
         * 
         * @param value
         *     allowed object is
         *     {@link BasicProperty1Entity1ThreatName }
         *     
         */
        public void setPropertyName(BasicProperty1Entity1ThreatName value) {
            this.propertyName = value;
        }

    }

}
