
package it.polito.dp2.TAMELESS.sol.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BasicProperty1EntityName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BasicProperty1EntityName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="malfunctioned"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BasicProperty1EntityName")
@XmlEnum
public enum BasicProperty1EntityName {

    @XmlEnumValue("malfunctioned")
    MALFUNCTIONED("malfunctioned");
    private final String value;

    BasicProperty1EntityName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BasicProperty1EntityName fromValue(String v) {
        for (BasicProperty1EntityName c: BasicProperty1EntityName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
