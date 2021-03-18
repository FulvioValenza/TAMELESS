
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Relation2EntitiesName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Relation2EntitiesName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="contain"/>
 *     &lt;enumeration value="control"/>
 *     &lt;enumeration value="depend"/>
 *     &lt;enumeration value="check"/>
 *     &lt;enumeration value="replicate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Relation2EntitiesName")
@XmlEnum
public enum Relation2EntitiesName {

    @XmlEnumValue("contain")
    CONTAIN("contain"),
    @XmlEnumValue("control")
    CONTROL("control"),
    @XmlEnumValue("depend")
    DEPEND("depend"),
    @XmlEnumValue("check")
    CHECK("check"),
    @XmlEnumValue("replicate")
    REPLICATE("replicate");
    private final String value;

    Relation2EntitiesName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Relation2EntitiesName fromValue(String v) {
        for (Relation2EntitiesName c: Relation2EntitiesName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
