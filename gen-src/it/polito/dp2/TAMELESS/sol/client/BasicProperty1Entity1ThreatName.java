
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BasicProperty1Entity1ThreatName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BasicProperty1Entity1ThreatName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="compromised"/>
 *     &lt;enumeration value="vulnerable"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BasicProperty1Entity1ThreatName")
@XmlEnum
public enum BasicProperty1Entity1ThreatName {

    @XmlEnumValue("compromised")
    COMPROMISED("compromised"),
    @XmlEnumValue("vulnerable")
    VULNERABLE("vulnerable");
    private final String value;

    BasicProperty1Entity1ThreatName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BasicProperty1Entity1ThreatName fromValue(String v) {
        for (BasicProperty1Entity1ThreatName c: BasicProperty1Entity1ThreatName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
