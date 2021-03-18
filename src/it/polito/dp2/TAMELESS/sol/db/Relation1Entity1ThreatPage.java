package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import it.polito.dp2.TAMELESS.sol.service.jaxb.Relation1Entity1Threat;

public class Relation1Entity1ThreatPage {
	private Map<BigInteger,Relation1Entity1Threat> map;
	private BigInteger totalPages=BigInteger.ONE;
	
	public BigInteger getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(BigInteger totalPages) {
		this.totalPages = totalPages;
	}
	public Map<BigInteger, Relation1Entity1Threat> getMap() {
		if (map==null)
			map = new HashMap<BigInteger, Relation1Entity1Threat>();
		return map;
	}
}
