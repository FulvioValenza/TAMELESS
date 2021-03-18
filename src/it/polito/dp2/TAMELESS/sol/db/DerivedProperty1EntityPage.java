package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import it.polito.dp2.TAMELESS.sol.service.jaxb.DerivedProperty1Entity;

public class DerivedProperty1EntityPage {
	private Map<BigInteger,DerivedProperty1Entity> map;
	private BigInteger totalPages=BigInteger.ONE;
	
	public BigInteger getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(BigInteger totalPages) {
		this.totalPages = totalPages;
	}
	public Map<BigInteger, DerivedProperty1Entity> getMap() {
		if (map==null)
			map = new HashMap<BigInteger, DerivedProperty1Entity>();
		return map;
	}
}
