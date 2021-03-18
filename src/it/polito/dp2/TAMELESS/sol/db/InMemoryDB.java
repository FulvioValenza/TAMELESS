package it.polito.dp2.TAMELESS.sol.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import java.util.logging.Logger;

import javax.ws.rs.InternalServerErrorException;

import it.polito.dp2.TAMELESS.sol.service.jaxb.*;
import it.polito.dp2.TAMELESS.sol.service.util.*;

public class InMemoryDB implements DB {

	private static DB inMemoryDB = new InMemoryDB();
	private List<String> toThrow;
	private Map<BigInteger, Entity> em; //entityMap
	private Map<BigInteger, Threat> tm; //threatMap
	private Map<BigInteger, Relation2Entities> r2em;
	private Map<BigInteger, Relation3Entities> r3em;
	private Map<BigInteger, Relation2Entities1Threat> r2e1tm;
	private Map<BigInteger, Relation1Entity1Threat> r1e1tm;
	private Map<BigInteger, Property1Entity> p1em;
	private Map<BigInteger, Property1Entity1Threat> p1e1tm;
	private Map<BigInteger, DerivedProperty1Entity1Threat> compromisedMap;
	private Map<BigInteger, DerivedProperty1Entity> malfunctioningMap;
	private Map<BigInteger, DerivedProperty1Entity1Threat> vulnerableMap;
	private Map<BigInteger, DerivedProperty1Entity1Threat> detectedMap;
	private Map<BigInteger, DerivedProperty1Entity> restoredMap;
	private Map<BigInteger, DerivedProperty1Entity> fixedMap;
	private BigInteger entityId;
	private BigInteger threatId;
	private BigInteger relationId;
	private BigInteger propertyId;
	private BigInteger derivedPropertyId;
	
	private InMemoryDB(){
		toThrow = new ArrayList<>();
		toThrow.add("a1");
		toThrow.add("b1");
		toThrow.add("a2");
		toThrow.add("b2");
		toThrow.add("c2");
		toThrow.add("a3");
		toThrow.add("b3");
		toThrow.add("a4");
		toThrow.add("b4");
		toThrow.add("a5");
		toThrow.add("b5");
		toThrow.add("a6");
		toThrow.add("b6");
		toThrow.add("c6");
		toThrow.add("a7");
		toThrow.add("b7");
		toThrow.add("a8");
		toThrow.add("a9");
		toThrow.add("b9");
		toThrow.add("c9");
		toThrow.add("a10");
		toThrow.add("b10");
		em = new ConcurrentHashMap<>();
		tm = new ConcurrentHashMap<>();
		r2em = new ConcurrentHashMap<>();
		r3em = new ConcurrentHashMap<>();
		r2e1tm = new ConcurrentHashMap<>();
		r1e1tm = new ConcurrentHashMap<>();
		p1em = new ConcurrentHashMap<>();
		p1e1tm = new ConcurrentHashMap<>();
		compromisedMap = new ConcurrentHashMap<>();
		malfunctioningMap = new ConcurrentHashMap<>();
		vulnerableMap = new ConcurrentHashMap<>();
		detectedMap = new ConcurrentHashMap<>();
		restoredMap = new ConcurrentHashMap<>();
		fixedMap = new ConcurrentHashMap<>();
		entityId = BigInteger.ZERO;
		threatId = BigInteger.ZERO;
		relationId = BigInteger.ZERO;
		propertyId = BigInteger.ZERO;
		derivedPropertyId = BigInteger.ZERO;
	}
	
	public static DB getInMemoryDB() {
		return inMemoryDB;
	}
	
	private BigInteger getIdFromURL(String URL){
		int lastSlashIndex = URL.lastIndexOf("/");
		String stringId = URL.substring(lastSlashIndex + 1);
		return BigInteger.valueOf(Integer.parseInt(stringId));
	}
	
	private String getEntityNameFromURL(String URL) throws Exception{ 
		BigInteger id = getIdFromURL(URL);
    	Entity ret = em.get(id);
    	if(ret != null)
    		return ret.getName();
    	else
    		throw new Exception();
	}
	
	private String getThreatNameFromURL(String URL) throws Exception{ 
		BigInteger id = getIdFromURL(URL);
    	Threat ret = tm.get(id);
    	if(ret != null)
    		return ret.getName();
    	else
    		throw new Exception();
	}
	
	//NB non posso ottenere self perchè è generato in automatico dal service
	private String getSelfGivenEntityName(String name){
		/*
		for(Entry<BigInteger,Entity> elem : em.entrySet()){
			if(elem.getValue().getName().equals(name) == true){
				return elem.getValue();
			}
		}
		throw new Exception();
		*/
		if(! toThrow.contains(name))
			return name;
		else
			return null;
	}
	
	//NB non posso ottenere self perchè è generato in automatico dal service
	private String getSelfGivenThreatName(String name){
		/*
		for(Entry<BigInteger,Entity> elem : em.entrySet()){
			if(elem.getValue().getName().equals(name) == true){
				return elem.getValue();
			}
		}
		throw new Exception();
		*/
		if(! toThrow.contains(name))
			return name;
		else
			return null;
	}
	
	private String createPrologFile() throws Exception{
	    String toWrite = "";
	    for(Entry<BigInteger,Entity> elem : em.entrySet()){
		    toWrite += "e(" + elem.getValue().getName() + ").\n";
	    }
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Threat> elem : tm.entrySet()){
		    toWrite += "t(" + elem.getValue().getName() + ").\n";
	    }
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation2Entities> elem : r2em.entrySet()){
	    	String entity1Name = getEntityNameFromURL(elem.getValue().getEntity1Id());
	    	String entity2Name = getEntityNameFromURL(elem.getValue().getEntity2Id());
		  
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + entity2Name + ").\n";
	    }	
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation3Entities> elem : r3em.entrySet()){
	    	String entity1Name = getEntityNameFromURL(elem.getValue().getEntity1Id());
	    	String entity2Name = getEntityNameFromURL(elem.getValue().getEntity2Id());
	    	String entity3Name = getEntityNameFromURL(elem.getValue().getEntity3Id());
		  
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + entity2Name + ", " + entity3Name +").\n";
	    }	
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation2Entities1Threat> elem : r2e1tm.entrySet()){
	    	String entity1Name = getEntityNameFromURL(elem.getValue().getEntity1Id());
	    	String entity2Name = getEntityNameFromURL(elem.getValue().getEntity2Id());
	    	String threatName = getThreatNameFromURL(elem.getValue().getThreatId());
	    	
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + entity2Name + ", " + threatName + ").\n";
	    }
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation1Entity1Threat> elem : r1e1tm.entrySet()){
	    	String entity1Name = getEntityNameFromURL(elem.getValue().getEntity1Id());
	    	String threatName = getThreatNameFromURL(elem.getValue().getThreatId());
	    	
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + threatName + ").\n";
	    }		    

	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Property1Entity> elem : p1em.entrySet()){
	    	String entityName = getEntityNameFromURL(elem.getValue().getEntityId());
	    	
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += "assMalfun(" + entityName + ").\n";
	    }		
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Property1Entity1Threat> elem : p1e1tm.entrySet()){
	    	String entityName = getEntityNameFromURL(elem.getValue().getEntityId());
	    	String threatName = getThreatNameFromURL(elem.getValue().getThreatId());
	    	
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	String propName = "";
	    	if(elem.getValue().getPropertyName().equals(BasicProperty1Entity1ThreatName.COMPROMISED))
	    		propName = "assComp";
	    	else //caso Vulnerable
	    		propName = "assVul";
	    		
	    	
	    	toWrite +=  propName + "(" + entityName + ", " + threatName + ").\n";
	    }	
	    
	    return toWrite;
	}
	
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createEntity(it.polito.dp2.TAMELESS.sol.service.jaxb.Entity)
	 */	
	@Override
	public synchronized BigInteger createEntity(Entity entity) throws Exception {
		// TODO block insertion of entity with same name
		BigInteger assignedId = entityId;
		em.put(entityId, entity);
		entityId = entityId.add(BigInteger.ONE);
		return assignedId;
	}
	
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getEntities(java.lang.String, java.math.BigInteger)
	 */
	@Override
	public EntityPage getEntities(String name, BigInteger page) throws Exception {
		//TODO: manage pages
		
		//N.B. neo4j if name is an empty string, returns all the element 
		EntityPage entityPage = new EntityPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Entity> map = entityPage.getMap();
			if(name.compareTo("") == 0)
				map.putAll(em);
			else{
				for (Entry<BigInteger,Entity> e : em.entrySet()) {
					//map.put(node.getMetadata().getId(),fromData(node.getData()));
					if( e.getValue().getName().toLowerCase().contains(name.toLowerCase()) ){
						map.put(e.getKey(), e.getValue());
					}	
				}
			}
			
	
			entityPage.setTotalPages(BigInteger.ONE);
		}		
		return entityPage;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createThreat(it.polito.dp2.TAMELESS.sol.service.jaxb.Threat)
	 */	
	@Override
	public synchronized BigInteger createThreat(Threat threat) throws Exception {
		
		// TODO block insertion of threat with same name
		
		BigInteger assignedId = threatId;
		tm.put(threatId, threat);
		threatId = threatId.add(BigInteger.ONE);
		return assignedId;
	}

	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getThreats(java.lang.String, java.math.BigInteger)
	 */
	@Override
	public ThreatPage getThreats(String name, BigInteger page) throws Exception {
		//TODO: manage pages
		ThreatPage threatPage = new ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Threat> map = threatPage.getMap();
			if(name.compareTo("") == 0)
				map.putAll(tm);
			else{
				for (Entry<BigInteger,Threat> t : tm.entrySet()) {
					//map.put(node.getMetadata().getId(),fromData(node.getData()));
					if( t.getValue().getName().toLowerCase().contains(name.toLowerCase()) ){
						map.put(t.getKey(), t.getValue());
					}	
				}
			}
			
	
			threatPage.setTotalPages(BigInteger.ONE);
		}		
		return threatPage;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation2Entity(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities)
	 */
	@Override
	public synchronized BigInteger createRelation2Entities(Relation2Entities r2e) throws Exception {
		
		// TODO check the existence of both entities
		
		BigInteger assignedId = relationId;
		r2em.put(relationId, r2e);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations2Entities(java.math.BigInteger)
	 */
	@Override
	public Relation2EntitiesPage getRelations2Entities(BigInteger page) throws Exception {
		//TODO: manage pages
		Relation2EntitiesPage r2ePage = new Relation2EntitiesPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Relation2Entities> map = r2ePage.getMap();

			map.putAll(r2em);			
	
			r2ePage.setTotalPages(BigInteger.ONE);
		}		
		return r2ePage;
	}

	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation3Entity(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation3Entities)
	 */
	@Override
	public synchronized BigInteger createRelation3Entities(Relation3Entities r3e) throws Exception {
		
		// TODO check the existence of both entities
		
		BigInteger assignedId = relationId;
		r3em.put(relationId, r3e);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations3Entities(java.math.BigInteger)
	 */
	@Override
	public Relation3EntitiesPage getRelations3Entities(BigInteger page) throws Exception {
		//TODO: manage pages
		Relation3EntitiesPage r3ePage = new Relation3EntitiesPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Relation3Entities> map = r3ePage.getMap();

			map.putAll(r3em);			
	
			r3ePage.setTotalPages(BigInteger.ONE);
		}		
		return r3ePage;
	}
	
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation2Entity1Threat(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities1Threat)
	 */
	@Override
	public synchronized BigInteger createRelation2Entities1Threat(Relation2Entities1Threat r2e1t) throws Exception {
		
		// TODO check the existence of both entities
		
		BigInteger assignedId = relationId;
		r2e1tm.put(relationId, r2e1t);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations2Entities1Threat(java.math.BigInteger)
	 */
	@Override
	public Relation2Entities1ThreatPage getRelations2Entities1Threat(BigInteger page) throws Exception {
		//TODO: manage pages
		Relation2Entities1ThreatPage r2e1tPage = new Relation2Entities1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Relation2Entities1Threat> map = r2e1tPage.getMap();

			map.putAll(r2e1tm);			
	
			r2e1tPage.setTotalPages(BigInteger.ONE);
		}		
		return r2e1tPage;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation1Entity1Threat(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation1Entity1Threat)
	 */
	@Override
	public synchronized BigInteger createRelation1Entity1Threat(Relation1Entity1Threat r1e1t) throws Exception {
		
		// TODO check the existence of both entities
		
		BigInteger assignedId = relationId;
		r1e1tm.put(relationId, r1e1t);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public Relation1Entity1ThreatPage getRelations1Entity1Threat(BigInteger page) throws Exception {
		//TODO: manage pages
		Relation1Entity1ThreatPage r1e1tPage = new Relation1Entity1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Relation1Entity1Threat> map = r1e1tPage.getMap();

			map.putAll(r1e1tm);			
	
			r1e1tPage.setTotalPages(BigInteger.ONE);
		}		
		return r1e1tPage;
	}

	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createProperty1Entity(it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity)
	 */
	@Override
	public synchronized BigInteger createProperty1Entity(Property1Entity p1e) throws Exception {
		
		// TODO check the existence of both entities
		
		BigInteger assignedId = propertyId;
		p1em.put(propertyId, p1e);
		propertyId = propertyId.add(BigInteger.ONE);
		return assignedId;
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity(java.math.BigInteger)
	 */
	@Override
	public Property1EntityPage getProperties1Entity(BigInteger page) throws Exception {
		//TODO: manage pages
		Property1EntityPage p1ePage = new Property1EntityPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Property1Entity> map = p1ePage.getMap();

			map.putAll(p1em);			
	
			p1ePage.setTotalPages(BigInteger.ONE);
		}		
		return p1ePage;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createProperty1Entity1Threat(it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity1Threat)
	 */
	@Override
	public synchronized BigInteger createProperty1Entity1Threat(Property1Entity1Threat p1e1t) throws Exception {
		
		// TODO check the existence of both entities
		
		BigInteger assignedId = propertyId;
		p1e1tm.put(propertyId, p1e1t);
		propertyId = propertyId.add(BigInteger.ONE);
		return assignedId;
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public Property1Entity1ThreatPage getProperties1Entity1Threat(BigInteger page) throws Exception {
		//TODO: manage pages
		Property1Entity1ThreatPage p1e1tPage = new Property1Entity1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,Property1Entity1Threat> map = p1e1tPage.getMap();

			map.putAll(p1e1tm);			
	
			p1e1tPage.setTotalPages(BigInteger.ONE);
		}		
		return p1e1tPage;
	}
	
	//GET BY ID SECTION
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getEntity(java.math.BigInteger)
	 */
	public Entity getEntity(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return em.get(id);
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getThreat(java.math.BigInteger)
	 */
	public Threat getThreat(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return tm.get(id);
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation2Entiities(java.math.BigInteger)
	 */
	public Relation2Entities getRelation2Entities(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return r2em.get(id);
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation3Entiities(java.math.BigInteger)
	 */
	public Relation3Entities getRelation3Entities(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return r3em.get(id);
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation2Entiities1Threat(java.math.BigInteger)
	 */
	public Relation2Entities1Threat getRelation2Entities1Threat(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return r2e1tm.get(id);
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation1Entity1Threat(java.math.BigInteger)
	 */
	public Relation1Entity1Threat getRelation1Entity1Threat(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return r1e1tm.get(id);
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperty1Entity(java.math.BigInteger)
	 */
	public Property1Entity getProperty1Entity(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return p1em.get(id);
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperty1Entity1Threat(java.math.BigInteger)
	 */
	public Property1Entity1Threat getProperty1Entity1Threat(BigInteger id) throws Exception{
		 
		//.get() return null if id does not exist as key, otherwise, the element
		return p1e1tm.get(id);
	}
	
	//DELETE section
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteEntity(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteEntity(BigInteger id) throws ConflictInOperationException, Exception{
		// TODO check the presence in the various relations and properties
		
		Entity toDelete = em.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteThreat(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteThreat(BigInteger id) throws ConflictInOperationException, Exception{
		
		// TODO check the presence in the various relations and properties
		
		Threat toDelete = tm.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteRelation2Entities(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteRelation2Entities(BigInteger id) throws Exception{
		
		Relation2Entities toDelete = r2em.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteRelation3Entities(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteRelation3Entities(BigInteger id) throws Exception{
		
		Relation3Entities toDelete = r3em.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteRelation2Entities1Threat(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteRelation2Entities1Threat(BigInteger id) throws Exception{
		
		Relation2Entities1Threat toDelete = r2e1tm.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteRelation1Entity1Threat(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteRelation1Entity1Threat(BigInteger id) throws Exception{
		
		Relation1Entity1Threat toDelete = r1e1tm.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}

	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteProperty1Entity(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteProperty1Entity(BigInteger id) throws Exception{
		
		Property1Entity toDelete = p1em.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteProperty1Entity1Threat(java.math.BigInteger)
	 */
	public synchronized BigInteger deleteProperty1Entity1Threat(BigInteger id) throws Exception{
		
		Property1Entity1Threat toDelete = p1e1tm.remove(id);
		if(toDelete == null)
			return null;
		else
			return id;
	}
	
	//PUT section
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateEntity(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Entity)
	 */
	@Override
	public synchronized Entity updateEntity(BigInteger id, Entity toUpdate) throws Exception {
		
		
		if ( ! em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}

	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateThreat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Threat)
	 */
	@Override
	public synchronized Threat updateThreat(BigInteger id, Threat toUpdate) throws Exception {
		
		
		if ( ! tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation2Entities(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities)
	 */
	@Override
	public synchronized Relation2Entities updateRelation2Entities(BigInteger id, Relation2Entities toUpdate) throws Exception {
		
		
		if ( ! r2em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r2em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}
	
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation3Entities(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation3Entities)
	 */
	@Override
	public synchronized Relation3Entities updateRelation3Entities(BigInteger id, Relation3Entities toUpdate) throws Exception {
		
		
		if ( ! r3em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r3em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation2Entities1Threat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities1Threat)
	 */
	@Override
	public synchronized Relation2Entities1Threat updateRelation2Entities1Threat(BigInteger id, Relation2Entities1Threat toUpdate) throws Exception {
		
		
		if ( ! r2e1tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r2e1tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation1Entity1Threat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation1Entity1Threat)
	 */
	@Override
	public synchronized Relation1Entity1Threat updateRelation1Entity1Threat(BigInteger id, Relation1Entity1Threat toUpdate) throws Exception {
		
		
		if ( ! r1e1tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r1e1tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateProperty1Entity(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity)
	 */
	@Override
	public synchronized Property1Entity updateProperty1Entity(BigInteger id, Property1Entity toUpdate) throws Exception {
		
		
		if ( ! p1em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		p1em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateProperty1Entity1Threat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity1Threat)
	 */
	@Override
	public synchronized Property1Entity1Threat updateProperty1Entity1Threat(BigInteger id, Property1Entity1Threat toUpdate) throws Exception {
		
		
		if ( ! p1e1tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		p1e1tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
	}

	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#computeResults()
	 */
	@Override
	public synchronized void computeResults() throws Exception {
		
		//PrologException: error(existence_error(procedure, '/'(control, 2)), context('/'(canbeComp, 2), _0))
		// la funzione control/2 non è stata trovata. E' stata richiamata in una canBeComp/2
		compromisedMap.clear();
		malfunctioningMap.clear();
		vulnerableMap.clear();
		detectedMap.clear();
		restoredMap.clear();
		fixedMap.clear();
		derivedPropertyId = BigInteger.ZERO;
		
		try{

			File file = new File("lib/system.p");

		    // if file doesnt exists, then create it
		    if (!file.exists()) {
		        file.createNewFile();
		    }
		    
		    FileWriter fw = new FileWriter(file.getAbsoluteFile()); //append=false
		    BufferedWriter bw = new BufferedWriter(fw);
	
		    String toWrite = createPrologFile();
		    
		    bw.write(toWrite);
		    bw.close();
		    //return toWrite;
		    
			//!!!!!!!
			//Query 1 - Introduce rules in prolog engine
			//!!!!!!!
			Query q1 = 
				    new Query( 
					"consult", //dovrebbe essere built-in query type(cioè non lo trovo nel file ma è definito da prolog)
					new Term[] {new Atom("lib/rule.p")} 
				    );
			//!!!!!!!
			//Query 2 - Introduce system in prolog engine
			//!!!!!!!
			Query q2 = 
				    new Query( 
					"consult", //dovrebbe essere built-in query type(cioè non lo trovo nel file ma è definito da prolog)
					new Term[] {new Atom("lib/system.p")} 
				    );
			
			
			Logger logger = Logger.getLogger(InMemoryDB.class.getName());
	        // log messages using log(Level level, String msg) 
			
			if(q1.hasSolution() && q2.hasSolution()){ 	//if rules and system have been 
														//read correctly
				deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED, compromisedMap);						
				
				deriveProperty1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING, malfunctioningMap);
				
				deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE, vulnerableMap);
				
				deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_DETECTED, detectedMap);
				
				deriveProperty1Entity(DerivedProperty1EntityName.CANBE_RESTORED, restoredMap);
				
				deriveProperty1Entity(DerivedProperty1EntityName.CANBE_FIXED, fixedMap);

				//return "OK";
			}
			else
				throw new InternalServerErrorException();
		}
		catch(Exception e){
			throw new InternalServerErrorException();
		}

		//pongo le 6 query salvando per ognuna il risultato in delle mappe
		//setto variabile systemUpdated=false	(i vari post, put e delete sopra appena vengono eseguiti, 
		//										settano systemUpdated=true e così le get delle derivedProperties
		//										ritornano errore)
		
		
		//System.out.println("Query 1");
		//JPL.setNativeLibraryDir("/usr/lib/swi-prolog/lib/i686-linux");
		//<!--<jvmarg value="-Djava.library.path=/usr/lib/swi-prolog/lib/i686-linux" />-->
		//<!--<property name="java.library.path" value="${lib.dir}/jpl.jar"/>-->
		//<!--<property name="java.library.path" value="/usr/lib/swi-prolog/lib/i686-linux"/>-->
		
		 /*String currentDir = System.getProperty("user.dir"); -> /home/dp2/Desktop/TAMELESS
		 return currentDir;*/
		
		/*String tmp = System.getProperty("java.library.path"); 
		return tmp;*/
		//return JPL.version_string();
	}
	
	private void deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName name,Map<BigInteger,DerivedProperty1Entity1Threat> m) throws Exception{
		
		Logger logger = Logger.getLogger(InMemoryDB.class.getName());
		
		Variable Ent = new Variable("E");
		Variable Thr = new Variable("T");
		
		String queryName = "";
		if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED))
			queryName = "canbeComp";
		else if (name.equals(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE))
			queryName = "canbeVul";
		else if (name.equals(DerivedProperty1Entity1ThreatName.CANBE_DETECTED))
			queryName = "canbeDet";
		else 
			throw new Exception();
		
		logger.log(Level.SEVERE,"Inizio query " + queryName );
		
		Query query = 
		  new Query( 
		      queryName, 
		      //new Term[] {new Atom("attacker"),new Atom("maliciousPurpose")}
		      new Term[]{Ent,Thr} 
		  );
			
		logger.log(Level.SEVERE,"Dopo query " + queryName );
		
		java.util.Map<String,Term>[] solutions = query.allSolutions(); 
		
		logger.log(Level.SEVERE,"Dopo .allSolutions -> length = " + solutions.length);
		
		for ( int i=0 ; i < solutions.length ; i++ ) { 
			
			String entitySelf = getSelfGivenEntityName(solutions[i].get("E").toString());
			String threatSelf = getSelfGivenThreatName(solutions[i].get("T").toString());
			
			if(entitySelf != null && threatSelf != null){ //se threatself e entityself non sono a1, a2,...
				
				DerivedProperty1Entity1Threat tmp = new DerivedProperty1Entity1Threat();
				tmp.setPropertyName(name); 
				
				tmp.setEntityId(entitySelf);
				tmp.setThreatId(threatSelf);
				
				m.put(derivedPropertyId, tmp);
				derivedPropertyId = derivedPropertyId.add(BigInteger.ONE);
				
				logger.log(Level.SEVERE, " E = "+ solutions[i].get("E") + ", T = " + solutions[i].get("T") + "\n");
				//ret += " E = "+ solutions[i].get("E") + ", T = " + solutions[i].get("T") + "\n" ;
				//System.out.println( "X = " + solutions[i].get("X")); 
			}
		}
		//return ret;
		logger.log(Level.SEVERE,"Fine " + queryName);	
	}
	
	private void deriveProperty1Entity(DerivedProperty1EntityName name,Map<BigInteger,DerivedProperty1Entity> m) throws Exception{
		
		Logger logger = Logger.getLogger(InMemoryDB.class.getName());
		
		Variable Ent = new Variable("E");
		
		String queryName = "";
		if(name.equals(DerivedProperty1EntityName.CANBE_MALFUNCTIONING))
			queryName = "canbeMalfun";
		else if(name.equals(DerivedProperty1EntityName.CANBE_RESTORED))
			queryName = "canbeRest";
		else if(name.equals(DerivedProperty1EntityName.CANBE_FIXED))
			queryName = "canbeFix";
		else 
			throw new Exception();
		
		logger.log(Level.SEVERE,"Inizio query " + queryName );
		
		Query query = 
		  new Query( 
		      queryName, 
		      //new Term[] {new Atom("attacker"),new Atom("maliciousPurpose")}
		      new Term[]{Ent} 
		  );
			
		logger.log(Level.SEVERE,"Dopo query " + queryName );
		
		java.util.Map<String,Term>[] solutions = query.allSolutions(); 
		
		logger.log(Level.SEVERE,"Dopo .allSolutions -> length = " + solutions.length);
		
		for ( int i=0 ; i < solutions.length ; i++ ) { 
			
			String entitySelf = getSelfGivenEntityName(solutions[i].get("E").toString());
			
			if(entitySelf != null){ //se entityself non sono a1, a2,...
				
				DerivedProperty1Entity tmp = new DerivedProperty1Entity();
				tmp.setPropertyName(name); 
				
				tmp.setEntityId(entitySelf);
				
				m.put(derivedPropertyId, tmp);
				derivedPropertyId = derivedPropertyId.add(BigInteger.ONE);
				
				logger.log(Level.SEVERE, " E = "+ solutions[i].get("E") + "\n");
				//ret += " E = "+ solutions[i].get("E") + ", T = " + solutions[i].get("T") + "\n" ;
				//System.out.println( "X = " + solutions[i].get("X")); 
			}
		}
		//return ret;
		logger.log(Level.SEVERE,"Fine " + queryName);	
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public DerivedProperty1Entity1ThreatPage getDerivedProperties1Entity1Threat(BigInteger page, 
			DerivedProperty1Entity1ThreatName name, String entitySelf, String threatSelf) throws Exception {
		//TODO: manage pages
		DerivedProperty1Entity1ThreatPage dp1e1tPage = new DerivedProperty1Entity1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,DerivedProperty1Entity1Threat> map = dp1e1tPage.getMap();

			if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED) == true)
				map.putAll(compromisedMap);		
			else if	(name.equals(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE) == true)
				map.putAll(vulnerableMap);
			else if	(name.equals(DerivedProperty1Entity1ThreatName.CANBE_DETECTED) == true){
				/*DerivedProperty1Entity1Threat tmp = new DerivedProperty1Entity1Threat();
				tmp.setPropertyName(DerivedProperty1Entity1ThreatName.CANBE_DETECTED);
				tmp.setThreatId("detected");
				tmp.setEntityId("detected");
				detectedMap.put(derivedPropertyId,tmp);*/
				map.putAll(detectedMap);
			}
			else 
				throw new Exception();
	
			dp1e1tPage.setTotalPages(BigInteger.ONE);
		}		
		return dp1e1tPage;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public DerivedProperty1EntityPage getDerivedProperties1Entity(BigInteger page, DerivedProperty1EntityName name, 
			String entitySelf) throws Exception {
		//TODO: manage pages
		DerivedProperty1EntityPage dp1ePage = new DerivedProperty1EntityPage();
		if (page.equals(BigInteger.ONE)) {
			//List<NodeType> nodes = client.searchNode(scope, keyword, beforeInclusive, afterInclusive);
			
			Map<BigInteger,DerivedProperty1Entity> map = dp1ePage.getMap();

			if(name.equals(DerivedProperty1EntityName.CANBE_MALFUNCTIONING) == true)
				map.putAll(malfunctioningMap);		
			else if	(name.equals(DerivedProperty1EntityName.CANBE_RESTORED) == true){
				/*DerivedProperty1Entity tmp = new DerivedProperty1Entity();
				tmp.setPropertyName(DerivedProperty1EntityName.CANBE_RESTORED);
				tmp.setEntityId("restored");
				restoredMap.put(derivedPropertyId,tmp);*/
				map.putAll(restoredMap);
			} else if	(name.equals(DerivedProperty1EntityName.CANBE_FIXED) == true){
				/*DerivedProperty1Entity tmp = new DerivedProperty1Entity();
				tmp.setPropertyName(DerivedProperty1EntityName.CANBE_FIXED);
				tmp.setEntityId("fixed");
				fixedMap.put(derivedPropertyId,tmp);*/
				map.putAll(fixedMap);
			}
			else 
				throw new Exception();
	
			dp1ePage.setTotalPages(BigInteger.ONE);
		}		
		return dp1ePage;
	}

	@Override
	public DerivedProperty1Entity1Threat getDerivedProperty1Entity1Threat(BigInteger id,
			DerivedProperty1Entity1ThreatName name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DerivedProperty1Entity getDerivedProperty1Entity(BigInteger id, DerivedProperty1EntityName name)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllDerivedProperty() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigInteger deleteEntityOrThreat(BigInteger id) throws ConflictInOperationException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger deleteRelOrProp(BigInteger id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}

