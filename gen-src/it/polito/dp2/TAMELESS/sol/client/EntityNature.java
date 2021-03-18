
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityNature.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityNature">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="human"/>
 *     &lt;enumeration value="cyber"/>
 *     &lt;enumeration value="physical"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityNature")
@XmlEnum
public enum EntityNature {

    @XmlEnumValue("human")
    HUMAN("human"),
    @XmlEnumValue("cyber")
    CYBER("cyber"),
    @XmlEnumValue("physical")
    PHYSICAL("physical");
    private final String value;

    EntityNature(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EntityNature fromValue(String v) {
        for (EntityNature c: EntityNature.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
