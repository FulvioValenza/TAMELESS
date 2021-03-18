package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import it.polito.dp2.TAMELESS.sol.service.jaxb.Relation3Entities;

public class Relation3EntitiesPage {
	private Map<BigInteger,Relation3Entities> map;
	private BigInteger totalPages=BigInteger.ONE;
	
	public BigInteger getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(BigInteger totalPages) {
		this.totalPages = totalPages;
	}
	public Map<BigInteger, Relation3Entities> getMap() {
		if (map==null)
			map = new HashMap<BigInteger, Relation3Entities>();
		return map;
	}
}
