
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Relation1Entity1ThreatName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Relation1Entity1ThreatName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="spread"/>
 *     &lt;enumeration value="potentiallyVulnerable"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Relation1Entity1ThreatName")
@XmlEnum
public enum Relation1Entity1ThreatName {

    @XmlEnumValue("spread")
    SPREAD("spread"),
    @XmlEnumValue("potentiallyVulnerable")
    POTENTIALLY_VULNERABLE("potentiallyVulnerable");
    private final String value;

    Relation1Entity1ThreatName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Relation1Entity1ThreatName fromValue(String v) {
        for (Relation1Entity1ThreatName c: Relation1Entity1ThreatName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
