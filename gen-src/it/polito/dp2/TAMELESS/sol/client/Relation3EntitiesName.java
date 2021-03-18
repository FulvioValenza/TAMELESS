
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Relation3EntitiesName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Relation3EntitiesName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="connect"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Relation3EntitiesName")
@XmlEnum
public enum Relation3EntitiesName {

    @XmlEnumValue("connect")
    CONNECT("connect");
    private final String value;

    Relation3EntitiesName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Relation3EntitiesName fromValue(String v) {
        for (Relation3EntitiesName c: Relation3EntitiesName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
