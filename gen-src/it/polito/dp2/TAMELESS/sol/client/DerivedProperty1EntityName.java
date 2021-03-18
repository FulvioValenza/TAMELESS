
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DerivedProperty1EntityName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DerivedProperty1EntityName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="canbeMalfunctioning"/>
 *     &lt;enumeration value="canbeRestored"/>
 *     &lt;enumeration value="canbeFixed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DerivedProperty1EntityName")
@XmlEnum
public enum DerivedProperty1EntityName {

    @XmlEnumValue("canbeMalfunctioning")
    CANBE_MALFUNCTIONING("canbeMalfunctioning"),
    @XmlEnumValue("canbeRestored")
    CANBE_RESTORED("canbeRestored"),
    @XmlEnumValue("canbeFixed")
    CANBE_FIXED("canbeFixed");
    private final String value;

    DerivedProperty1EntityName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DerivedProperty1EntityName fromValue(String v) {
        for (DerivedProperty1EntityName c: DerivedProperty1EntityName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
