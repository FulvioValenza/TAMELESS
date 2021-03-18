package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities;

public class Relation2EntitiesPage {
	private Map<BigInteger,Relation2Entities> map;
	private BigInteger totalPages=BigInteger.ONE;
	
	public BigInteger getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(BigInteger totalPages) {
		this.totalPages = totalPages;
	}
	public Map<BigInteger, Relation2Entities> getMap() {
		if (map==null)
			map = new HashMap<BigInteger, Relation2Entities>();
		return map;
	}
}
