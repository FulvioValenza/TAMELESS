package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities1Threat;

public class Relation2Entities1ThreatPage {
	private Map<BigInteger,Relation2Entities1Threat> map;
	private BigInteger totalPages=BigInteger.ONE;
	
	public BigInteger getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(BigInteger totalPages) {
		this.totalPages = totalPages;
	}
	public Map<BigInteger, Relation2Entities1Threat> getMap() {
		if (map==null)
			map = new HashMap<BigInteger, Relation2Entities1Threat>();
		return map;
	}
}
