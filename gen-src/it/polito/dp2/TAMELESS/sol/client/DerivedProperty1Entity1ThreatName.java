
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DerivedProperty1Entity1ThreatName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DerivedProperty1Entity1ThreatName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="canbeCompromised"/>
 *     &lt;enumeration value="canbeVulnerable"/>
 *     &lt;enumeration value="canbeDetected"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DerivedProperty1Entity1ThreatName")
@XmlEnum
public enum DerivedProperty1Entity1ThreatName {

    @XmlEnumValue("canbeCompromised")
    CANBE_COMPROMISED("canbeCompromised"),
    @XmlEnumValue("canbeVulnerable")
    CANBE_VULNERABLE("canbeVulnerable"),
    @XmlEnumValue("canbeDetected")
    CANBE_DETECTED("canbeDetected");
    private final String value;

    DerivedProperty1Entity1ThreatName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DerivedProperty1Entity1ThreatName fromValue(String v) {
        for (DerivedProperty1Entity1ThreatName c: DerivedProperty1Entity1ThreatName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
