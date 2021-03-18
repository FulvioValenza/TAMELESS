
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
@XmlRootElement(name = "property1Entity1Threat")
public class Property1Entity1Threat {

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
