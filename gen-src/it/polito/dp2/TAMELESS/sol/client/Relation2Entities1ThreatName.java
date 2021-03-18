
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Relation2Entities1ThreatName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Relation2Entities1ThreatName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="protect"/>
 *     &lt;enumeration value="monitor"/>
 *     &lt;enumeration value="mend"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Relation2Entities1ThreatName")
@XmlEnum
public enum Relation2Entities1ThreatName {

    @XmlEnumValue("protect")
    PROTECT("protect"),
    @XmlEnumValue("monitor")
    MONITOR("monitor"),
    @XmlEnumValue("mend")
    MEND("mend");
    private final String value;

    Relation2Entities1ThreatName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Relation2Entities1ThreatName fromValue(String v) {
        for (Relation2Entities1ThreatName c: Relation2Entities1ThreatName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
