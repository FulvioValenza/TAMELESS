package it.polito.dp2.TAMELESS.sol.service;

import java.lang.System;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.Map.Entry;

//import org.apache.commons.lang3.StringEscapeUtils;
import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;


import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import it.polito.dp2.TAMELESS.sol.db.*;

import it.polito.dp2.TAMELESS.sol.service.jaxb.*;
import it.polito.dp2.TAMELESS.sol.service.util.ResourceUtils;


public class SystemService {
	
	private DB db = Neo4jDB.getNeo4jDB();
	//private DB db = InMemoryDB.getInMemoryDB();
	private ResourceUtils rutil;

	public SystemService(UriInfo uriInfo) {
		rutil = new ResourceUtils((uriInfo.getBaseUriBuilder()));
	}

	Logger l = Logger.getLogger("aaa");
	private String sanitizeName(String name, boolean checkIsEmpty){
		
		
		if(checkIsEmpty && name.equals(""))
			throw new BadRequestException("null name");
		
		if(! name.matches("[\\w]+")){
			//l.severe("inside");
			name = name.replaceAll("[^\\w]", "");
		}
		return name;
		
		
	} 
	
	public Entity createEntity(Entity e) throws Exception {
		
		try{
			//l.severe(e.getName());
			
			e.setName(sanitizeName(e.getName(), true));
	
			//l.severe(e.getName());
			
			//BigInteger id = n4jDb.createItem(item);
			BigInteger id = db.createEntity(e);
			if (id==null)
				throw new Exception("Null id");
			
			//.completeEntity() add the various URI to the Entity
			rutil.completeEntity(e, id);
			return e;
		} catch (ConflictInOperationException cioe) {
			throw new ConflictServiceException();
		}
		
	}
	
	public Entities getEntities(String name, BigInteger page) throws Exception {
		//l.severe(name);
		
		name = sanitizeName(name, false);
		
		//l.severe(name);
		
		EntityPage entityPage = db.getEntities(name,page);

		Entities entities = new Entities();
		List<Entity> list = entities.getEntity();
		
		for( Entry<BigInteger,Entity> entry : entityPage.getMap().entrySet() ) {
			Entity e = entry.getValue();
			rutil.completeEntity(e , entry.getKey());
			list.add(e);
		}
		entities.setTotalPages(entityPage.getTotalPages());
		entities.setPage(page);
		return entities;
	}
	
	public Threat createThreat(Threat t) throws Exception {
		
		try{
			t.setName(sanitizeName(t.getName(), true));
			
			//BigInteger id = n4jDb.createItem(item);
			BigInteger id = db.createThreat(t);
			if (id==null)
				throw new Exception("Null id");
			
			//.completeEntity() add the various URI to the Entity
			rutil.completeThreat(t, id);
			return t;
		} catch (ConflictInOperationException cioe) {
			throw new ConflictServiceException();
		}
		
	}
	
	public Threats getThreats(String name, BigInteger page) throws Exception {
		
		name = sanitizeName(name, false);
		
		ThreatPage threatPage = db.getThreats(name,page);

		Threats threats = new Threats();
		List<Threat> list = threats.getThreat();
		
		for( Entry<BigInteger,Threat> entry : threatPage.getMap().entrySet() ) {
			Threat t = entry.getValue();
			rutil.completeThreat(t , entry.getKey());
			list.add(t);
		}
		threats.setTotalPages(threatPage.getTotalPages());
		threats.setPage(page);
		return threats;
	}
	
	public Relation2Entities createRelation2Entities(Relation2Entities r2e) throws Exception {
		
		//BigInteger id = n4jDb.createItem(item);
		BigInteger id = db.createRelation2Entities(r2e);
		if (id==null)
			throw new Exception("Null id");
		
		//.completeRelation2Entities() add the various URI to the Entity
		rutil.completeRelation2Entities(r2e, id);
		return r2e;
		
	}
	
	public Relations2Entities getRelations2Entities(BigInteger page) throws Exception {
		
		Relation2EntitiesPage relation2EntitiesPage = db.getRelations2Entities(page);

		Relations2Entities relations2Entities = new Relations2Entities();
		List<Relation2Entities> list = relations2Entities.getRelation2Entities();
		
		for( Entry<BigInteger,Relation2Entities> entry : relation2EntitiesPage.getMap().entrySet() ) {
			Relation2Entities r2e = entry.getValue();
			rutil.completeRelation2Entities(r2e , entry.getKey());
			list.add(r2e);
		}
		relations2Entities.setTotalPages(relation2EntitiesPage.getTotalPages());
		relations2Entities.setPage(page);
		return relations2Entities;
		
	}
	
	
	public Relation3Entities createRelation3Entities(Relation3Entities r3e) throws Exception {
		
		//BigInteger id = n4jDb.createItem(item);
		BigInteger id = db.createRelation3Entities(r3e);
		if (id==null)
			throw new Exception("Null id");
		
		//.completeRelation2Entities() add the various URI to the Entity
		rutil.completeRelation3Entities(r3e, id);
		return r3e;
		
	}
	
	public Relations3Entities getRelations3Entities(BigInteger page) throws Exception {
		
		Relation3EntitiesPage relation3EntitiesPage = db.getRelations3Entities(page);

		Relations3Entities relations3Entities = new Relations3Entities();
		List<Relation3Entities> list = relations3Entities.getRelation3Entities();
		
		for( Entry<BigInteger,Relation3Entities> entry : relation3EntitiesPage.getMap().entrySet() ) {
			Relation3Entities r3e = entry.getValue();
			rutil.completeRelation3Entities(r3e , entry.getKey());
			list.add(r3e);
		}
		relations3Entities.setTotalPages(relation3EntitiesPage.getTotalPages());
		relations3Entities.setPage(page);
		return relations3Entities;
		
	}
	
	public Relation2Entities1Threat createRelation2Entities1Threat(Relation2Entities1Threat r2e1t) throws Exception {
		
		//BigInteger id = n4jDb.createItem(item);
		BigInteger id = db.createRelation2Entities1Threat(r2e1t);
		if (id==null)
			throw new Exception("Null id");
		
		//.completeRelation2Entities() add the various URI to the Entity
		rutil.completeRelation2Entities1Threat(r2e1t, id);
		return r2e1t;
		
	}
	
	public Relations2Entities1Threat getRelations2Entities1Threat(BigInteger page) throws Exception {
		
		Relation2Entities1ThreatPage relation2Entities1ThreatPage = db.getRelations2Entities1Threat(page);

		Relations2Entities1Threat relations2Entities1Threat = new Relations2Entities1Threat();
		List<Relation2Entities1Threat> list = relations2Entities1Threat.getRelation2Entities1Threat();
		
		for( Entry<BigInteger,Relation2Entities1Threat> entry : relation2Entities1ThreatPage.getMap().entrySet() ) {
			Relation2Entities1Threat r2e1t = entry.getValue();
			rutil.completeRelation2Entities1Threat(r2e1t , entry.getKey());
			list.add(r2e1t);
		}
		relations2Entities1Threat.setTotalPages(relation2Entities1ThreatPage.getTotalPages());
		relations2Entities1Threat.setPage(page);
		return relations2Entities1Threat;
		
	}
	
	public Relation1Entity1Threat createRelation1Entity1Threat(Relation1Entity1Threat r1e1t) throws Exception {
		
		//BigInteger id = n4jDb.createItem(item);
		BigInteger id = db.createRelation1Entity1Threat(r1e1t);
		if (id==null)
			throw new Exception("Null id");
		
		//.completeRelation2Entities() add the various URI to the Entity
		rutil.completeRelation1Entity1Threat(r1e1t, id);
		return r1e1t;
		
	}
	
	public Relations1Entity1Threat getRelations1Entity1Threat(BigInteger page) throws Exception {
		
		Relation1Entity1ThreatPage relation1Entity1ThreatPage = db.getRelations1Entity1Threat(page);

		Relations1Entity1Threat relations1Entity1Threat = new Relations1Entity1Threat();
		List<Relation1Entity1Threat> list = relations1Entity1Threat.getRelation1Entity1Threat();
		
		for( Entry<BigInteger,Relation1Entity1Threat> entry : relation1Entity1ThreatPage.getMap().entrySet() ) {
			Relation1Entity1Threat r1e1t = entry.getValue();
			rutil.completeRelation1Entity1Threat(r1e1t , entry.getKey());
			list.add(r1e1t);
		}
		relations1Entity1Threat.setTotalPages(relation1Entity1ThreatPage.getTotalPages());
		relations1Entity1Threat.setPage(page);
		return relations1Entity1Threat;
		
	}
	
	public Property1Entity createProperty1Entity(Property1Entity p1e) throws Exception {
		
		//BigInteger id = n4jDb.createItem(item);
		BigInteger id = db.createProperty1Entity(p1e);
		if (id==null)
			throw new Exception("Null id");
		
		//.completeRelation2Entities() add the various URI to the Entity
		rutil.completeProperty1Entity(p1e, id);
		return p1e;
		
	}
	
	public Properties1Entity getProperties1Entity(BigInteger page) throws Exception {
		
		Property1EntityPage property1EntityPage = db.getProperties1Entity(page);

		Properties1Entity properties1Entity = new Properties1Entity();
		List<Property1Entity> list = properties1Entity.getProperty1Entity();
		
		for( Entry<BigInteger,Property1Entity> entry : property1EntityPage.getMap().entrySet() ) {
			Property1Entity p1e = entry.getValue();
			rutil.completeProperty1Entity(p1e , entry.getKey());
			list.add(p1e);
		}
		properties1Entity.setTotalPages(property1EntityPage.getTotalPages());
		properties1Entity.setPage(page);
		return properties1Entity;
		
	}
	
	public Property1Entity1Threat createProperty1Entity1Threat(Property1Entity1Threat p1e1t) throws Exception {
		
		//BigInteger id = n4jDb.createItem(item);
		BigInteger id = db.createProperty1Entity1Threat(p1e1t);
		if (id==null)
			throw new Exception("Null id");
		
		//.completeRelation2Entities() add the various URI to the Entity
		rutil.completeProperty1Entity1Threat(p1e1t, id);
		return p1e1t;
		
	}
	
	public Properties1Entity1Threat getProperties1Entity1Threat(BigInteger page) throws Exception {
		
		Property1Entity1ThreatPage property1Entity1ThreatPage = db.getProperties1Entity1Threat(page);

		Properties1Entity1Threat properties1Entity1Threat = new Properties1Entity1Threat();
		List<Property1Entity1Threat> list = properties1Entity1Threat.getProperty1Entity1Threat();
		
		for( Entry<BigInteger,Property1Entity1Threat> entry : property1Entity1ThreatPage.getMap().entrySet() ) {
			Property1Entity1Threat p1e1t = entry.getValue();
			rutil.completeProperty1Entity1Threat(p1e1t , entry.getKey());
			list.add(p1e1t);
		}
		properties1Entity1Threat.setTotalPages(property1Entity1ThreatPage.getTotalPages());
		properties1Entity1Threat.setPage(page);
		return properties1Entity1Threat;
		
	}
	
	//GET BY ID SECTION
	
	public Entity getEntity(BigInteger id) throws Exception {
		Entity ret = db.getEntity(id);
		if ( ret != null)
			rutil.completeEntity(ret, id);
		return ret;
	}
	
	public Threat getThreat(BigInteger id) throws Exception {
		Threat ret = db.getThreat(id);
		if ( ret != null)
			rutil.completeThreat(ret, id);
		return ret;
	}
	
	public Relation2Entities getRelation2Entities(BigInteger id) throws Exception {
		Relation2Entities ret = db.getRelation2Entities(id);
		if ( ret != null)
			rutil.completeRelation2Entities(ret, id);
		return ret;
	}
	
	public Relation3Entities getRelation3Entities(BigInteger id) throws Exception {
		Relation3Entities ret = db.getRelation3Entities(id);
		if ( ret != null)
			rutil.completeRelation3Entities(ret, id);
		return ret;
	}
	
	public Relation2Entities1Threat getRelation2Entities1Threat(BigInteger id) throws Exception {
		Relation2Entities1Threat ret = db.getRelation2Entities1Threat(id);
		if ( ret != null)
			rutil.completeRelation2Entities1Threat(ret, id);
		return ret;
	}
	
	public Relation1Entity1Threat getRelation1Entity1Threat(BigInteger id) throws Exception {
		Relation1Entity1Threat ret = db.getRelation1Entity1Threat(id);
		if ( ret != null)
			rutil.completeRelation1Entity1Threat(ret, id);
		return ret;
	}
	
	public Property1Entity getProperty1Entity(BigInteger id) throws Exception {
		Property1Entity ret = db.getProperty1Entity(id);
		if ( ret != null )
			rutil.completeProperty1Entity(ret, id);
		return ret;
	}
	
	public Property1Entity1Threat getProperty1Entity1Threat(BigInteger id) throws Exception {
		Property1Entity1Threat ret = db.getProperty1Entity1Threat(id);
		if ( ret != null)
			rutil.completeProperty1Entity1Threat(ret, id);
		return ret;
	}
	
	//DELETE section
	
	public BigInteger deleteEntity(BigInteger id) throws ConflictServiceException, Exception {
		try {
			Entity elemToDelete = getEntity(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteEntityOrThreat(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (ConflictInOperationException e) {
			throw new ConflictServiceException();
		}
	}
	
	public BigInteger deleteThreat(BigInteger id) throws ConflictServiceException, Exception {
		try {
			Threat elemToDelete = getThreat(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteEntityOrThreat(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (ConflictInOperationException e) {
			throw new ConflictServiceException();
		}
	}
	
	public BigInteger deleteRelation2Entities(BigInteger id) throws Exception {
		try {
			//this getRelation2Entities is particularly useful in the case of relation(tameless meaning)
			//and properties. The code used to allow the deletion of rel and prop
			//is in cypher and potentially allow to remove every node! Using 
			//getRelation2Entities we are checking that the id sent is associated to a relationships or properties
			Relation2Entities elemToDelete = getRelation2Entities(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteRelOrProp(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public BigInteger deleteRelation3Entities(BigInteger id) throws Exception {
		try {
			Relation3Entities elemToDelete = getRelation3Entities(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteRelOrProp(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public BigInteger deleteRelation2Entities1Threat(BigInteger id) throws Exception {
		try {
			Relation2Entities1Threat elemToDelete = getRelation2Entities1Threat(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteRelOrProp(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public BigInteger deleteRelation1Entity1Threat(BigInteger id) throws Exception {
		try {
			Relation1Entity1Threat elemToDelete = getRelation1Entity1Threat(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteRelOrProp(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public BigInteger deleteProperty1Entity(BigInteger id) throws Exception {
		try {
			Property1Entity elemToDelete = getProperty1Entity(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteRelOrProp(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public BigInteger deleteProperty1Entity1Threat(BigInteger id) throws Exception {
		try {
			Property1Entity1Threat elemToDelete = getProperty1Entity1Threat(id);
			
			if(elemToDelete==null)
				return null;
			
			BigInteger deletedId = db.deleteRelOrProp(id);
			
			if(deletedId==null)
				return null;

			return id;
			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	//PUT section
	
	public Entity updateEntity(BigInteger id, Entity toUpdate) throws Exception {
		
		try{
			//Stessa tecnica usata per delete per essere sicuri di recuperare un nodo associato a un entity
			Entity elemToUpdate = getEntity(id);
			
			if(elemToUpdate==null)
				return null;
			
			//l.severe(toUpdate.getName());
			toUpdate.setName(sanitizeName(toUpdate.getName(), true));
			//l.severe(toUpdate.getName());
			
			Entity ret = db.updateEntity(id, toUpdate);
			if (ret!=null) {
				rutil.completeEntity(toUpdate, id);
				return toUpdate;
			} else
				return null;
		} catch (ConflictInOperationException cioe) {
			throw new ConflictServiceException();
		}
		
	}
	
	public Threat updateThreat(BigInteger id, Threat toUpdate) throws Exception {
		
		try{
			Threat elemToUpdate = getThreat(id);
			
			if(elemToUpdate==null)
				return null;
			
			toUpdate.setName(sanitizeName(toUpdate.getName(), true));
			
			Threat ret = db.updateThreat(id, toUpdate);
			if (ret!=null) {
				rutil.completeThreat(toUpdate, id);
				return toUpdate;
			} else
				return null;
		} catch (ConflictInOperationException cioe) {
			throw new ConflictServiceException();
		}
	}
	
	public Relation2Entities updateRelation2Entities(BigInteger id, Relation2Entities toUpdate) throws Exception {
		
		Relation2Entities elemToUpdate = getRelation2Entities(id);
		
		if(elemToUpdate==null)
			return null;
		
		Relation2Entities ret = db.updateRelation2Entities(id, toUpdate);
		if (ret!=null) {
			rutil.completeRelation2Entities(toUpdate, id);
			return toUpdate;
		} else
			return null;
	}
	
	public Relation3Entities updateRelation3Entities(BigInteger id, Relation3Entities toUpdate) throws Exception {
		
		Relation3Entities elemToUpdate = getRelation3Entities(id);
		
		if(elemToUpdate==null)
			return null;
		
		Relation3Entities ret = db.updateRelation3Entities(id, toUpdate);
		if (ret!=null) {
			rutil.completeRelation3Entities(toUpdate, id);
			return toUpdate;
		} else
			return null;
	}
	
	public Relation2Entities1Threat updateRelation2Entities1Threat(BigInteger id, Relation2Entities1Threat toUpdate) throws Exception {
		
		Relation2Entities1Threat elemToUpdate = getRelation2Entities1Threat(id);
		
		if(elemToUpdate==null)
			return null;
		
		Relation2Entities1Threat ret = db.updateRelation2Entities1Threat(id, toUpdate);
		if (ret!=null) {
			rutil.completeRelation2Entities1Threat(toUpdate, id);
			return toUpdate;
		} else
			return null;
	}
	
	public Relation1Entity1Threat updateRelation1Entity1Threat(BigInteger id, Relation1Entity1Threat toUpdate) throws Exception {
		
		Relation1Entity1Threat elemToUpdate = getRelation1Entity1Threat(id);
		
		if(elemToUpdate==null)
			return null;
		
		Relation1Entity1Threat ret = db.updateRelation1Entity1Threat(id, toUpdate);
		if (ret!=null) {
			rutil.completeRelation1Entity1Threat(toUpdate, id);
			return toUpdate;
		} else
			return null;
	}
	
	public Property1Entity updateProperty1Entity(BigInteger id, Property1Entity toUpdate) throws Exception {
		
		Property1Entity elemToUpdate = getProperty1Entity(id);
		
		if(elemToUpdate==null)
			return null;
		
		Property1Entity ret = db.updateProperty1Entity(id, toUpdate);
		if (ret!=null) {
			rutil.completeProperty1Entity(toUpdate, id);
			return toUpdate;
		} else
			return null;
	}
	
	public Property1Entity1Threat updateProperty1Entity1Threat(BigInteger id, Property1Entity1Threat toUpdate) throws Exception {
		
		Property1Entity1Threat elemToUpdate = getProperty1Entity1Threat(id);
		
		if(elemToUpdate==null)
			return null;
		
		Property1Entity1Threat ret = db.updateProperty1Entity1Threat(id, toUpdate);
		if (ret!=null) {
			rutil.completeProperty1Entity1Threat(toUpdate, id);
			return toUpdate;
		} else
			return null;
	}
	
	public void computeResults() throws Exception{
		db.computeResults();	
	}
	
	public void deleteAllDerivedProperty() throws Exception {
		try {
			
			db.deleteAllDerivedProperty();
			

			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	

	//GET of derived Properties
	
	public DerivedProperties1Entity1Threat getDerivedProperties1Entity1Threat(BigInteger page, DerivedProperty1Entity1ThreatName name, String entitySelf, String threatSelf) throws Exception {
		
		DerivedProperty1Entity1ThreatPage derivedProperty1Entity1ThreatPage = db.getDerivedProperties1Entity1Threat(page, name, entitySelf, threatSelf);

		DerivedProperties1Entity1Threat derivedProperties1Entity1Threat = new DerivedProperties1Entity1Threat();
		List<DerivedProperty1Entity1Threat> list = derivedProperties1Entity1Threat.getDerivedProperty1Entity1Threat();
		
		for( Entry<BigInteger,DerivedProperty1Entity1Threat> entry : derivedProperty1Entity1ThreatPage.getMap().entrySet() ) {
			DerivedProperty1Entity1Threat dp1e1t = entry.getValue();
			rutil.completeDerivedProperty1Entity1Threat(dp1e1t , entry.getKey(), name);
			list.add(dp1e1t);
		}
		derivedProperties1Entity1Threat.setTotalPages(derivedProperty1Entity1ThreatPage.getTotalPages());
		derivedProperties1Entity1Threat.setPage(page);
		return derivedProperties1Entity1Threat;
		
	}
	
	public DerivedProperties1Entity getDerivedProperties1Entity(BigInteger page, DerivedProperty1EntityName name, String entitySelf) throws Exception {
		
		DerivedProperty1EntityPage derivedProperty1EntityPage = db.getDerivedProperties1Entity(page, name, entitySelf);

		DerivedProperties1Entity derivedProperties1Entity = new DerivedProperties1Entity();
		List<DerivedProperty1Entity> list = derivedProperties1Entity.getDerivedProperty1Entity();
		
		for( Entry<BigInteger,DerivedProperty1Entity> entry : derivedProperty1EntityPage.getMap().entrySet() ) {
			DerivedProperty1Entity dp1e = entry.getValue();
			rutil.completeDerivedProperty1Entity(dp1e , entry.getKey(), name);
			list.add(dp1e);
		}
		derivedProperties1Entity.setTotalPages(derivedProperty1EntityPage.getTotalPages());
		derivedProperties1Entity.setPage(page);
		return derivedProperties1Entity;
		
	}
	
	public DerivedProperty1Entity1Threat getDerivedProperty1Entity1Threat(BigInteger id, DerivedProperty1Entity1ThreatName name) throws Exception {
		DerivedProperty1Entity1Threat ret = db.getDerivedProperty1Entity1Threat(id,name);
		if ( ret != null)
			rutil.completeDerivedProperty1Entity1Threat(ret, id, name);
		return ret;
	}
	
	public DerivedProperty1Entity getDerivedProperty1Entity(BigInteger id, DerivedProperty1EntityName name) throws Exception {
		DerivedProperty1Entity ret = db.getDerivedProperty1Entity(id,name);
		if ( ret != null)
			rutil.completeDerivedProperty1Entity(ret, id, name);
		return ret;
	}

}
