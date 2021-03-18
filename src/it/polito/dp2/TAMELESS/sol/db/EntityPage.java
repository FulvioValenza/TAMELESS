package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import it.polito.dp2.TAMELESS.sol.service.jaxb.Entity;

public class EntityPage {
	private Map<BigInteger,Entity> map;
	private BigInteger totalPages=BigInteger.ONE;
	
	public BigInteger getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(BigInteger totalPages) {
		this.totalPages = totalPages;
	}
	public Map<BigInteger, Entity> getMap() {
		if (map==null)
			map = new HashMap<BigInteger, Entity>();
		return map;
	}
}
