package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.Map;

import it.polito.dp2.TAMELESS.sol.neo4j.jaxb.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import java.util.logging.Logger;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import it.polito.dp2.TAMELESS.sol.service.jaxb.*;
import it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory;

public class Neo4jDB implements DB {
	private static DB neo4jDB = new Neo4jDB();
	private MyNeo4jClient client;
	private Logger l = Logger.getLogger(Neo4jDB.class.getName());
	private List<String> toThrow;
	private static final String entityPrefix = "http://localhost:8080/TAMELESS/rest/system/entities";
	private static final String entityPrefixSecured = "https://localhost:8443/TAMELESS/rest/system/entities";
	
	private static final String threatPrefix = "http://localhost:8080/TAMELESS/rest/system/threats";
	private static final String threatPrefixSecured = "https://localhost:8443/TAMELESS/rest/system/threats";

	//DO NOT ACCESS TO IT DIRECTLY!!!!!!!!
	private Boolean derivedPropertiesUpdated = false;

	private void setDerPropAsUpdated() {
		this.derivedPropertiesUpdated = true;
	}
	
	private void setDerPropAsToBeUpdated(){
		this.derivedPropertiesUpdated = false;
	}
	
	private Boolean areDerivedPropertiesUpdated(){
		return derivedPropertiesUpdated;
	}


	private Neo4jDB() {
		client = new MyNeo4jClient();
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
		toThrow.add("a11");
		toThrow.add("b11");
		toThrow.add("a12");
		toThrow.add("a13");
		toThrow.add("b13");
		toThrow.add("a14");
		toThrow.add("b14");
		toThrow.add("a15");
		toThrow.add("b15");
		toThrow.add("a16");
		toThrow.add("a17");
		/*toThrow.add("a18");
		toThrow.add("b18");
		toThrow.add("a19");
		toThrow.add("a20");
		toThrow.add("b20");
		toThrow.add("a21");
		toThrow.add("b21");
		toThrow.add("a22");
		toThrow.add("a23");*/
		
	}
	
	public static DB getNeo4jDB() {
		return neo4jDB;
	}
	
	private String getEntityNameFromId(String id) throws Exception{
		
		BigInteger bigIntId = BigInteger.valueOf(Integer.parseInt(id));
    	
		String ret = this.getEntity(bigIntId).getName();
    	
    	if(ret != null)
    		return ret;
    	else
    		throw new Exception();
	}
	
	private String getThreatNameFromId(String id) throws Exception{ 

		BigInteger bigIntId = BigInteger.valueOf(Integer.parseInt(id));
    	
		String ret = this.getThreat(bigIntId).getName();
		
    	if(ret != null)
    		return ret;
    	else
    		throw new Exception();
	}
	
	//NB non posso ottenere self perchè è generato in automatico dal service
	private String getIdGivenEntityName(String name) throws Exception{

		if(toThrow.contains(name))
			return null;
		
		Map<BigInteger, Entity> map = this.getEntities(name, BigInteger.ONE).getMap();
		
		l.severe(name);
		
		/*if(map.size() == 1){
		
			Entry<BigInteger,Entity> e = map.entrySet().iterator().next();
			if(e.getValue().getName().equals(name))
				return e.getKey().toString();
			
		}*/
		for(Entry<BigInteger, Entity> e : map.entrySet()){
			if(e.getValue().getName().equals(name))
				return e.getKey().toString();
		}
		
		
		
		throw new Exception();
		
	}
	
	//NB non posso ottenere self perchè è generato in automatico dal service
	private String getIdGivenThreatName(String name) throws Exception{

		
		if(toThrow.contains(name))
			return null;
		
		Map<BigInteger, Threat> map = this.getThreats(name, BigInteger.ONE).getMap();
		
		
		/*if(map.size() == 1){
			
			Entry<BigInteger,Threat> e = map.entrySet().iterator().next();
			if(e.getValue().getName().equals(name))
				return e.getKey().toString();
			
		}*/
		
		for(Entry<BigInteger, Threat> e : map.entrySet()){
			if(e.getValue().getName().equals(name))
				return e.getKey().toString();
		}
		
		throw new Exception();
	}
	
	private String createPrologFile() throws Exception{
	    String toWrite = "";
	    for(Entry<BigInteger,Entity> elem : this.getEntities("", BigInteger.ONE).getMap().entrySet()){
		    toWrite += "e(" + elem.getValue().getName() + ").\n";
	    }
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Threat> elem : this.getThreats("", BigInteger.ONE).getMap().entrySet()){
		    toWrite += "t(" + elem.getValue().getName() + ").\n";
	    }
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation2Entities> elem : this.getRelations2Entities(BigInteger.ONE).getMap().entrySet()){
	    	 
	    	String entity1Name = getEntityNameFromId(elem.getValue().getEntity1Id());
	    	String entity2Name = getEntityNameFromId(elem.getValue().getEntity2Id());
		  
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + entity2Name + ").\n";
	    }	
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation3Entities> elem : this.getRelations3Entities(BigInteger.ONE).getMap().entrySet()){
	    	String entity1Name = getEntityNameFromId(elem.getValue().getEntity1Id());
	    	String entity2Name = getEntityNameFromId(elem.getValue().getEntity2Id());
	    	String entity3Name = getEntityNameFromId(elem.getValue().getEntity3Id());
		  
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + entity2Name + ", " + entity3Name +").\n";
	    }	
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation2Entities1Threat> elem : this.getRelations2Entities1Threat(BigInteger.ONE).getMap().entrySet()){
	    	String entity1Name = getEntityNameFromId(elem.getValue().getEntity1Id());
	    	String entity2Name = getEntityNameFromId(elem.getValue().getEntity2Id());
	    	String threatName = getThreatNameFromId(elem.getValue().getThreatId());
	    	
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + entity2Name + ", " + threatName + ").\n";
	    }
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Relation1Entity1Threat> elem : this.getRelations1Entity1Threat(BigInteger.ONE).getMap().entrySet()){
	    	String entity1Name = getEntityNameFromId(elem.getValue().getEntity1Id());
	    	String threatName = getThreatNameFromId(elem.getValue().getThreatId());
	    	
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += elem.getValue().getRelationName().toString().toLowerCase() + 
	    				"(" + entity1Name + ", " + threatName + ").\n";
	    }		    

	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Property1Entity> elem : this.getProperties1Entity(BigInteger.ONE).getMap().entrySet()){
	    	String entityName = getEntityNameFromId(elem.getValue().getEntityId());
	    	
	    	//toWrite += getIdFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	//toWrite += getEntityNameFromURL(elem.getValue().getEntity1Id()) + "\n";
	    	toWrite += "assMalfun(" + entityName + ").\n";
	    }		
	    
	    toWrite += "\n\n";
	    
	    for(Entry<BigInteger,Property1Entity1Threat> elem : this.getProperties1Entity1Threat(BigInteger.ONE).getMap().entrySet()){
	    	String entityName = getEntityNameFromId(elem.getValue().getEntityId());
	    	String threatName = getThreatNameFromId(elem.getValue().getThreatId());
	    	
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
	
	private void assertNameNotInToThrowList(String name) throws ConflictInOperationException{
		if(toThrow.contains(name))
			throw new ConflictInOperationException();
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createEntity(it.polito.dp2.TAMELESS.sol.service.jaxb.Entity)
	 */	
	@Override
	public synchronized BigInteger createEntity(Entity entity) throws Exception {
		
		assertNameNotInToThrowList(entity.getName());
		
		NodeType node = client.createNode(entity.getName(),entity.getType().name(),TypeOfData.ENTITY);
		
		this.setDerPropAsToBeUpdated();
		
		return node.getMetadata().getId();
	
	}
	
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getEntities(java.lang.String, java.math.BigInteger)
	 */
	@Override
	public EntityPage getEntities(String name, BigInteger page) throws Exception {
		
		//N.B. neo4j if name is an empty string, returns all the element 
		EntityPage entityPage = new EntityPage();
		if (page.equals(BigInteger.ONE)) {
			
			List<NodeType> nodes = client.searchNode(name,TypeOfData.ENTITY);
			
			Map<BigInteger,Entity> map = entityPage.getMap();
						
			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),entityFromData(node.getData()));
			}		
			
			entityPage.setTotalPages(BigInteger.ONE);
		}	
		return entityPage;
	}
	
	private Entity entityFromData(Data d) throws Exception{
		
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Entity e = of.createEntity();
		
		e.setName(d.getName());
		
		String type = d.getType().toLowerCase();
		if(type.equals("human"))
			e.setType(EntityNature.HUMAN);
		else if(type.equals("cyber"))
			e.setType(EntityNature.CYBER);
		else if(type.equals("physical"))
			e.setType(EntityNature.PHYSICAL);
		else
			throw new Exception();
		
		return e;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createThreat(it.polito.dp2.TAMELESS.sol.service.jaxb.Threat)
	 */	
	@Override
	public synchronized BigInteger createThreat(Threat threat) throws Exception {
		
		assertNameNotInToThrowList(threat.getName());
		
		NodeType node = client.createNode(threat.getName(),null,TypeOfData.THREAT);
		
		this.setDerPropAsToBeUpdated();
		
		return node.getMetadata().getId();
	
	}

	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getThreats(java.lang.String, java.math.BigInteger)
	 */
	@Override
	public ThreatPage getThreats(String name, BigInteger page) throws Exception {
		ThreatPage threatPage = new ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			
			List<NodeType> nodes = client.searchNode(name,TypeOfData.THREAT);
			
			Map<BigInteger,Threat> map = threatPage.getMap();
			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),threatFromData(node.getData()));
			}	
			
	
			threatPage.setTotalPages(BigInteger.ONE);
		}		
		return threatPage;
	}
	
	private Threat threatFromData(Data d) throws Exception{
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Threat t = of.createThreat();
		t.setName(d.getName());		
		return t;
	}
	
	private BigInteger getIdFromURL(String url,TypeOfData tod) throws Exception{
		//l.severe(url);
		if(! url.contains("/"))
			return null;
		
		String idString = url.substring(url.lastIndexOf("/") + 1);
		String idPrefix = url.substring(0,url.lastIndexOf("/"));
		//l.log(Level.SEVERE,idPrefix);
		//l.log(Level.SEVERE,idString);
		if(idString.length() == 0)
			return null;
		BigInteger id = BigInteger.valueOf(Integer.parseInt(idString));
		//NodeType e1 = client.getNode(e1Id);
		if(tod.equals(TypeOfData.ENTITY)){
			Entity e = this.getEntity(id);
			//l.log(Level.SEVERE,"e == null ? " + ((e == null) ? "true" : "false"));
			//l.log(Level.SEVERE,"idPrefix different entityPrefix ? " + ((! idPrefix.equals(entityPrefix)) ? "true" : "false"));
			
			return (e == null || (! idPrefix.equals(entityPrefix) && ! idPrefix.equals(entityPrefixSecured))) ? null : id;
		}
		else if(tod.equals(TypeOfData.THREAT)){
			Threat t = this.getThreat(id);
			return (t == null || (! idPrefix.equals(threatPrefix) && ! idPrefix.equals(threatPrefixSecured))) ? null : id;
		} 
		else 
			throw new Exception();
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation2Entity(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities)
	 */
	@Override
	public synchronized BigInteger createRelation2Entities(Relation2Entities r2e) throws Exception {

		
		/*BigInteger assignedId = relationId;
		r2em.put(relationId, r2e);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;*/
		
		/*String e1IdString = r2e.getEntity1Id().substring(r2e.getEntity1Id().lastIndexOf("/") + 1);
		//String idPrefix = r2e.getEntity1Id().substring(0,r2e.getEntity1Id().lastIndexOf("/"));
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(r2e.getEntity1Id(), TypeOfData.ENTITY);
		/*
		String e2IdString = r2e.getEntity2Id().substring(r2e.getEntity2Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e2Id = BigInteger.valueOf(Integer.parseInt(e2IdString));
		//NodeType e2 = client.getNode(e2Id);
		Entity e2 = this.getEntity(e2Id);*/
		BigInteger e2Id = getIdFromURL(r2e.getEntity2Id(), TypeOfData.ENTITY);
		
		if(e1Id != null && e2Id != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(r2e.getRelationName().toString(),null,TypeOfData.RELATION_2ENTITIES);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), e1Id, TypeOfRelationship.ENTITY1);
			RelationshipType r2 = client.createRelationship(node.getMetadata().getId(), e2Id, TypeOfRelationship.ENTITY2);
			if(r1 != null && r2 != null){
				r2e.setEntity1Id(e1Id.toString());
				r2e.setEntity2Id(e2Id.toString());
				
				this.setDerPropAsToBeUpdated();
				
				return node.getMetadata().getId();
			}else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), e1Id);
				if(r2 != null)
					client.deleteRelationship(node.getMetadata().getId(), e2Id);
				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else //case in which at least one between e1 or e2 it is not associated to an existing node or the existing node is not an entity
			throw new BadRequestException();
		
	}
	
	
	/**
	 * NB. i metodi getter delle relazioni e properties devono essere synchronized perchè anche se neo4j si occupa di mantenere le proprietà ACID
	 * delle transazioni, le varie create e update fanno almeno due operazioni di cui una crea il nodo e le altre creano le varie relazioni. Siccome questo insieme
	 * di operazioni NON è stato gestito a livello di transazione in Neo4j, è necessario impedire via software di fare una get di relazioni o proprietà per cui è stato
	 * solo creato/modificato il nodo e non le relazioni(Neo4J meaning) 
	 */
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations2Entities(java.math.BigInteger)
	 */
	@Override
	public synchronized Relation2EntitiesPage getRelations2Entities(BigInteger page) throws Exception {
		Relation2EntitiesPage r2ePage = new Relation2EntitiesPage();
		if (page.equals(BigInteger.ONE)) {
			
			List<NodeType> nodes = client.searchNode("",TypeOfData.RELATION_2ENTITIES);
			
			Map<BigInteger,Relation2Entities> map = r2ePage.getMap();
			
			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),relation2EntitiesFromNode(node));
			}
			
			r2ePage.setTotalPages(BigInteger.ONE);
		}		
		return r2ePage;
		
	}
	
	private Relation2Entities relation2EntitiesFromNode(NodeType nt) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Relation2Entities ret = of.createRelation2Entities();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		if(name.equals("check"))
			ret.setRelationName(Relation2EntitiesName.CHECK);
		else if(name.equals("contain"))
			ret.setRelationName(Relation2EntitiesName.CONTAIN);
		else if(name.equals("control"))
			ret.setRelationName(Relation2EntitiesName.CONTROL);
		else if(name.equals("depend"))
			ret.setRelationName(Relation2EntitiesName.DEPEND);
		else if(name.equals("replicate"))
			ret.setRelationName(Relation2EntitiesName.REPLICATE);
		else
			throw new Exception();

		//l.log(Level.SEVERE,"Dopo assegnazione relationName");
		ret.setEntity1Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY1));
		//l.log(Level.SEVERE,"dopo set entity1");
		ret.setEntity2Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY2));
		//l.log(Level.SEVERE,"dopo set entity2");
		return ret;
	}

	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation3Entity(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation3Entities)
	 */
	@Override
	public synchronized BigInteger createRelation3Entities(Relation3Entities r3e) throws Exception {
		
		/*BigInteger assignedId = relationId;
		r3em.put(relationId, r3e);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;*/
		
		/*String e1IdString = r3e.getEntity1Id().substring(r3e.getEntity1Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(r3e.getEntity1Id(), TypeOfData.ENTITY);
		
		/*String e2IdString = r3e.getEntity2Id().substring(r3e.getEntity2Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e2Id = BigInteger.valueOf(Integer.parseInt(e2IdString));
		//NodeType e2 = client.getNode(e2Id);
		Entity e2 = this.getEntity(e2Id);*/
		BigInteger e2Id = getIdFromURL(r3e.getEntity2Id(), TypeOfData.ENTITY);
		
		/*String e3IdString = r3e.getEntity3Id().substring(r3e.getEntity3Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e3Id = BigInteger.valueOf(Integer.parseInt(e3IdString));
		//NodeType e3 = client.getNode(e3Id);
		Entity e3 = this.getEntity(e3Id);*/
		BigInteger e3Id = getIdFromURL(r3e.getEntity3Id(), TypeOfData.ENTITY);
		
		if(e1Id != null && e2Id != null && e3Id != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(r3e.getRelationName().toString(),null,TypeOfData.RELATION_3ENTITIES);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), e1Id, TypeOfRelationship.ENTITY1);
			RelationshipType r2 = client.createRelationship(node.getMetadata().getId(), e2Id, TypeOfRelationship.ENTITY2);
			RelationshipType r3 = client.createRelationship(node.getMetadata().getId(), e3Id, TypeOfRelationship.ENTITY3);
			if(r1 != null && r2 != null && r3 != null){
				r3e.setEntity1Id(e1Id.toString());
				r3e.setEntity2Id(e2Id.toString());
				r3e.setEntity3Id(e3Id.toString());
				
				this.setDerPropAsToBeUpdated();
				
				return node.getMetadata().getId();
			}else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), e1Id);
				if(r2 != null)
					client.deleteRelationship(node.getMetadata().getId(), e2Id);
				if(r3 != null)
					client.deleteRelationship(node.getMetadata().getId(), e3Id);
				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else 
			throw new BadRequestException();
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations3Entities(java.math.BigInteger)
	 */
	@Override
	public synchronized Relation3EntitiesPage getRelations3Entities(BigInteger page) throws Exception {

		Relation3EntitiesPage r3ePage = new Relation3EntitiesPage();
		if (page.equals(BigInteger.ONE)) {
			
			List<NodeType> nodes = client.searchNode("", TypeOfData.RELATION_3ENTITIES);
			
			Map<BigInteger,Relation3Entities> map = r3ePage.getMap();

			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),relation3EntitiesFromNode(node));
			}
			
			r3ePage.setTotalPages(BigInteger.ONE);
		}		
		return r3ePage;

	}
	
	private Relation3Entities relation3EntitiesFromNode(NodeType nt) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Relation3Entities ret = of.createRelation3Entities();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		if(name.equals("connect"))
			ret.setRelationName(Relation3EntitiesName.CONNECT);
		else
			throw new Exception();

		//l.log(Level.SEVERE,"Dopo assegnazione relationName");
		ret.setEntity1Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY1));
		//l.log(Level.SEVERE,"dopo set entity1");
		ret.setEntity2Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY2));
		//l.log(Level.SEVERE,"dopo set entity2");
		ret.setEntity3Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY3));
		return ret;
	}
	
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation2Entity1Threat(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities1Threat)
	 */
	@Override
	public synchronized BigInteger createRelation2Entities1Threat(Relation2Entities1Threat r2e1t) throws Exception {
		
		
		/*BigInteger assignedId = relationId;
		r2e1tm.put(relationId, r2e1t);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;*/
		
		/*String e1IdString = r2e1t.getEntity1Id().substring(r2e1t.getEntity1Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(r2e1t.getEntity1Id(), TypeOfData.ENTITY);
		
		
		/*String e2IdString = r2e1t.getEntity2Id().substring(r2e1t.getEntity2Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e2Id = BigInteger.valueOf(Integer.parseInt(e2IdString));
		//NodeType e2 = client.getNode(e2Id);
		Entity e2 = this.getEntity(e2Id);*/
		BigInteger e2Id = getIdFromURL(r2e1t.getEntity2Id(), TypeOfData.ENTITY);
		
		
		/*String tIdString = r2e1t.getThreatId().substring(r2e1t.getThreatId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger tId = BigInteger.valueOf(Integer.parseInt(tIdString));
		//NodeType t = client.getNode(tId);
		Threat t = this.getThreat(tId);*/
		BigInteger tId = getIdFromURL(r2e1t.getThreatId(), TypeOfData.THREAT);
		
		
		if(e1Id != null && e2Id != null && tId != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(r2e1t.getRelationName().toString(),null,TypeOfData.RELATION_2ENTITIES_1THREAT);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), e1Id, TypeOfRelationship.ENTITY1);
			RelationshipType r2 = client.createRelationship(node.getMetadata().getId(), e2Id, TypeOfRelationship.ENTITY2);
			RelationshipType r3 = client.createRelationship(node.getMetadata().getId(), tId, TypeOfRelationship.THREAT);
			if(r1 != null && r2 != null && r3 != null){
				r2e1t.setEntity1Id(e1Id.toString());
				r2e1t.setEntity2Id(e2Id.toString());
				r2e1t.setThreatId(tId.toString());
				
				this.setDerPropAsToBeUpdated();
				
				return node.getMetadata().getId();
			} else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), e1Id);
				if(r2 != null)
					client.deleteRelationship(node.getMetadata().getId(), e2Id);
				if(r3 != null)
					client.deleteRelationship(node.getMetadata().getId(), tId);
				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else 
			throw new BadRequestException();
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations2Entities1Threat(java.math.BigInteger)
	 */
	@Override
	public synchronized Relation2Entities1ThreatPage getRelations2Entities1Threat(BigInteger page) throws Exception {

		Relation2Entities1ThreatPage r2e1tPage = new Relation2Entities1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			
			List<NodeType> nodes = client.searchNode("", TypeOfData.RELATION_2ENTITIES_1THREAT);
			
			Map<BigInteger,Relation2Entities1Threat> map = r2e1tPage.getMap();

			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),relation2Entities1ThreatFromNode(node));
			}
			
			r2e1tPage.setTotalPages(BigInteger.ONE);
		}		
		return r2e1tPage;
		
	}
	
	
	private Relation2Entities1Threat relation2Entities1ThreatFromNode(NodeType nt) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Relation2Entities1Threat ret = of.createRelation2Entities1Threat();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		if(name.equals("mend"))
			ret.setRelationName(Relation2Entities1ThreatName.MEND);
		else if(name.equals("monitor"))
			ret.setRelationName(Relation2Entities1ThreatName.MONITOR);
		else if(name.equals("protect"))
			ret.setRelationName(Relation2Entities1ThreatName.PROTECT);
		else
			throw new Exception();

		//l.log(Level.SEVERE,"Dopo assegnazione relationName");
		ret.setEntity1Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY1));
		//l.log(Level.SEVERE,"dopo set entity1");
		ret.setEntity2Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY2));
		//l.log(Level.SEVERE,"dopo set entity2");
		ret.setThreatId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.THREAT));
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createRelation1Entity1Threat(it.polito.dp2.TAMELESS.sol.service.jaxb.Relation1Entity1Threat)
	 */
	@Override
	public synchronized BigInteger createRelation1Entity1Threat(Relation1Entity1Threat r1e1t) throws Exception {
		
		
		/*BigInteger assignedId = relationId;
		r1e1tm.put(relationId, r1e1t);
		relationId = relationId.add(BigInteger.ONE);
		return assignedId;*/
		
		/*String e1IdString = r1e1t.getEntity1Id().substring(r1e1t.getEntity1Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(r1e1t.getEntity1Id(), TypeOfData.ENTITY);
		
		/*String tIdString = r1e1t.getThreatId().substring(r1e1t.getThreatId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger tId = BigInteger.valueOf(Integer.parseInt(tIdString));
		//NodeType t = client.getNode(tId);
		Threat t = this.getThreat(tId);*/
		BigInteger tId = getIdFromURL(r1e1t.getThreatId(), TypeOfData.THREAT);
		
		if(e1Id != null && tId != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(r1e1t.getRelationName().toString(),null,TypeOfData.RELATION_1ENTITY_1THREAT);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), e1Id, TypeOfRelationship.ENTITY1);
			RelationshipType r2 = client.createRelationship(node.getMetadata().getId(), tId, TypeOfRelationship.THREAT);
			if(r1 != null && r2 != null ){
				r1e1t.setEntity1Id(e1Id.toString());
				r1e1t.setThreatId(tId.toString());
				
				this.setDerPropAsToBeUpdated();
				
				return node.getMetadata().getId();
			} else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), e1Id);
				if(r2 != null)
					client.deleteRelationship(node.getMetadata().getId(), tId);
				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else 
			throw new BadRequestException();

	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelations1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public synchronized Relation1Entity1ThreatPage getRelations1Entity1Threat(BigInteger page) throws Exception {

		Relation1Entity1ThreatPage r1e1tPage = new Relation1Entity1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			List<NodeType> nodes = client.searchNode("", TypeOfData.RELATION_1ENTITY_1THREAT);
			
			Map<BigInteger,Relation1Entity1Threat> map = r1e1tPage.getMap();

			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),relation1Entity1ThreatFromNode(node));
			}		
	
			r1e1tPage.setTotalPages(BigInteger.ONE);
		}		
		//l.log(Level.SEVERE,"End of get RelationSSSSSSSSSSSSSSSSSs");
		return r1e1tPage;
		
	}

	private Relation1Entity1Threat relation1Entity1ThreatFromNode(NodeType nt) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Relation1Entity1Threat ret = of.createRelation1Entity1Threat();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		
		if(name.equals("potentially_vulnerable"))
			ret.setRelationName(Relation1Entity1ThreatName.POTENTIALLY_VULNERABLE);
		else if(name.equals("spread"))
			ret.setRelationName(Relation1Entity1ThreatName.SPREAD);
		else
			throw new Exception();

		ret.setEntity1Id(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY1));

		ret.setThreatId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.THREAT));
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createProperty1Entity(it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity)
	 */
	@Override
	public synchronized BigInteger createProperty1Entity(Property1Entity p1e) throws Exception {
		
		/*BigInteger assignedId = propertyId;
		p1em.put(propertyId, p1e);
		propertyId = propertyId.add(BigInteger.ONE);
		return assignedId;*/
		
		/*String eIdString = p1e.getEntityId().substring(p1e.getEntityId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger eId = BigInteger.valueOf(Integer.parseInt(eIdString));
		//NodeType e = client.getNode(eId);
		Entity e = this.getEntity(eId);*/
		BigInteger eId = getIdFromURL(p1e.getEntityId(), TypeOfData.ENTITY);
		
		if(eId != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(p1e.getPropertyName().toString(),null,TypeOfData.PROPERTY_1ENTITY);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), eId, TypeOfRelationship.ENTITY);
			if(r1 != null ){
				p1e.setEntityId(eId.toString());
				
				this.setDerPropAsToBeUpdated();
				
				return node.getMetadata().getId();
			} else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), eId);

				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else 
			throw new BadRequestException();
		
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity(java.math.BigInteger)
	 */
	@Override
	public synchronized Property1EntityPage getProperties1Entity(BigInteger page) throws Exception {

		Property1EntityPage p1ePage = new Property1EntityPage();
		if (page.equals(BigInteger.ONE)) {
			
			List<NodeType> nodes = client.searchNode("", TypeOfData.PROPERTY_1ENTITY);
			
			Map<BigInteger,Property1Entity> map = p1ePage.getMap();

			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),property1EntityFromNode(node));
			}				
	
			p1ePage.setTotalPages(BigInteger.ONE);
		}		
		return p1ePage;

	}
	
	private Property1Entity property1EntityFromNode(NodeType nt) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Property1Entity ret = of.createProperty1Entity();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		
		if(name.equals("malfunctioned"))
			ret.setPropertyName(BasicProperty1EntityName.MALFUNCTIONED);
		else
			throw new Exception();

		ret.setEntityId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY));
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#createProperty1Entity1Threat(it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity1Threat)
	 */
	@Override
	public synchronized BigInteger createProperty1Entity1Threat(Property1Entity1Threat p1e1t) throws Exception {
		
		/*BigInteger assignedId = propertyId;
		p1e1tm.put(propertyId, p1e1t);
		propertyId = propertyId.add(BigInteger.ONE);
		return assignedId;*/
		
		/*String eIdString = p1e1t.getEntityId().substring(p1e1t.getEntityId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger eId = BigInteger.valueOf(Integer.parseInt(eIdString));
		//NodeType e = client.getNode(eId);
		Entity e = this.getEntity(eId);*/
		BigInteger eId = getIdFromURL(p1e1t.getEntityId(), TypeOfData.ENTITY);
		
		/*String tIdString = p1e1t.getThreatId().substring(p1e1t.getThreatId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger tId = BigInteger.valueOf(Integer.parseInt(tIdString));
		//NodeType t = client.getNode(tId);
		Threat t = this.getThreat(tId);*/
		BigInteger tId = getIdFromURL(p1e1t.getThreatId(), TypeOfData.THREAT);
		
		if(eId != null && tId != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(p1e1t.getPropertyName().toString(),null,TypeOfData.PROPERTY_1ENTITY_1THREAT);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), eId, TypeOfRelationship.ENTITY);
			RelationshipType r2 = client.createRelationship(node.getMetadata().getId(), tId, TypeOfRelationship.THREAT);
			if(r1 != null && r2 != null ){
				p1e1t.setEntityId(eId.toString());
				p1e1t.setThreatId(tId.toString());
				
				this.setDerPropAsToBeUpdated();
				
				return node.getMetadata().getId();
			} else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), eId);
				if(r2 != null)
					client.deleteRelationship(node.getMetadata().getId(), tId);
				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else 
			throw new BadRequestException();
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public synchronized Property1Entity1ThreatPage getProperties1Entity1Threat(BigInteger page) throws Exception {

		Property1Entity1ThreatPage p1e1tPage = new Property1Entity1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			
			List<NodeType> nodes = client.searchNode("",TypeOfData.PROPERTY_1ENTITY_1THREAT);
			
			Map<BigInteger,Property1Entity1Threat> map = p1e1tPage.getMap();

			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),property1Entity1ThreatFromNode(node));
			}	
			
			p1e1tPage.setTotalPages(BigInteger.ONE);
		}		
		return p1e1tPage;
		
	}
	
	private Property1Entity1Threat property1Entity1ThreatFromNode(NodeType nt) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		Property1Entity1Threat ret = of.createProperty1Entity1Threat();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		
		if(name.equals("compromised"))
			ret.setPropertyName(BasicProperty1Entity1ThreatName.COMPROMISED);
		else if(name.equals("vulnerable"))
			ret.setPropertyName(BasicProperty1Entity1ThreatName.VULNERABLE);
		else
			throw new Exception();

		ret.setEntityId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY));

		ret.setThreatId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.THREAT));
		
		return ret;
	}
	
	//GET BY ID SECTION
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getEntity(java.math.BigInteger)
	 */
	public Entity getEntity(BigInteger id) throws Exception{
		 
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.ENTITY.toString()))
			return entityFromData(ret.getData());
		
		//case in which some node with that id has been found but it is not an entity
		return null;
			

	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getThreat(java.math.BigInteger)
	 */
	public Threat getThreat(BigInteger id) throws Exception{
		
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.THREAT.toString()))
			return threatFromData(ret.getData());
		
		return null;

	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation2Entiities(java.math.BigInteger)
	 */
	public synchronized Relation2Entities getRelation2Entities(BigInteger id) throws Exception{
		
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
	
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.RELATION_2ENTITIES.toString()))
			return relation2EntitiesFromNode(ret);
		
		return null;
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation3Entiities(java.math.BigInteger)
	 */
	public synchronized Relation3Entities getRelation3Entities(BigInteger id) throws Exception{
		 
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.RELATION_3ENTITIES.toString()))
			return relation3EntitiesFromNode(ret);
		
		return null;
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation2Entiities1Threat(java.math.BigInteger)
	 */
	public synchronized Relation2Entities1Threat getRelation2Entities1Threat(BigInteger id) throws Exception{
		 
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.RELATION_2ENTITIES_1THREAT.toString()))
			return relation2Entities1ThreatFromNode(ret);
		
		return null;
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getRelation1Entity1Threat(java.math.BigInteger)
	 */
	public synchronized Relation1Entity1Threat getRelation1Entity1Threat(BigInteger id) throws Exception{
		 
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.RELATION_1ENTITY_1THREAT.toString()))
			return relation1Entity1ThreatFromNode(ret);
		
		return null;
		
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperty1Entity(java.math.BigInteger)
	 */
	public synchronized Property1Entity getProperty1Entity(BigInteger id) throws Exception{
		 	
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.PROPERTY_1ENTITY.toString()))
			return property1EntityFromNode(ret);
		
		return null;

	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperty1Entity1Threat(java.math.BigInteger)
	 */
	public synchronized Property1Entity1Threat getProperty1Entity1Threat(BigInteger id) throws Exception{
		 
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		
		if(ret.getMetadata().getLabels().size() == 1 && 
				ret.getMetadata().getLabels().contains(TypeOfData.PROPERTY_1ENTITY_1THREAT.toString()))
			return property1Entity1ThreatFromNode(ret);
		
		return null;

	}
	
	//DELETE section

	public synchronized BigInteger deleteEntityOrThreat(BigInteger id) throws Neo4jClientException, ConflictInOperationException{
		
		//return null if node not found
		//return id if node correctly deleted
		//throw ConflictInOperationException if node is used in relationships(neo4j meaning)
		//throw Neo4jClientException if other error occurs
		
		BigInteger ret = client.deleteNode(id);
		
		if(ret != null)
			this.setDerPropAsToBeUpdated();
		
		return ret;
				
	}
	
	public synchronized BigInteger deleteRelOrProp(BigInteger id) throws Neo4jClientException, ConflictInOperationException{
		
		//return null if not found
		//return id if deleted
		//throws exception otherwise
		
		BigInteger ret = client.deleteNodeAndRelationships(id);
		
		if(ret != null)
			this.setDerPropAsToBeUpdated();
		
		return ret;
	}
	
	
		
	//PUT section
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateEntity(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Entity)
	 */
	@Override
	public synchronized Entity updateEntity(BigInteger id, Entity toUpdate) throws Exception {
		
		assertNameNotInToThrowList(toUpdate.getName());
		
		if (client.updateNode(id, toUpdate.getName(),toUpdate.getType().toString(),TypeOfData.ENTITY)==null) {
			return null;
		}	
		else{
			this.setDerPropAsToBeUpdated();
			return toUpdate;
		}
			
		/*
		if ( ! em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;*/
	}

	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateThreat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Threat)
	 */
	@Override
	public synchronized Threat updateThreat(BigInteger id, Threat toUpdate) throws Exception {
		
		assertNameNotInToThrowList(toUpdate.getName());
		
		if (client.updateNode(id, toUpdate.getName(),null,TypeOfData.THREAT)==null) {
			return null;
		}	
		else{
			this.setDerPropAsToBeUpdated();
			return toUpdate;
		}
			
		
		/*if ( ! tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
		*/
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation2Entities(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities)
	 */
	@Override
	public synchronized Relation2Entities updateRelation2Entities(BigInteger id, Relation2Entities toUpdate) throws Exception {
		
		//leggo il nodo vecchio -> lo salvo per eventuale rollback ->
		//uso la get di questa classe così contiene già gli indici dei terminal node
		Relation2Entities old = getRelation2Entities(id);
		
		//leggo url delle relationships vecchie così posso cancellarle -> uso la getRelationships di MyNeo4jClient
		URI old_r1 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())));
		URI old_r2 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity2Id())));
		
		/*//ottengo i nodi utili per creare le nuove relazioni
		String e1IdString = toUpdate.getEntity1Id().substring(toUpdate.getEntity1Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(toUpdate.getEntity1Id(), TypeOfData.ENTITY);
		
		
		/*String e2IdString = toUpdate.getEntity2Id().substring(toUpdate.getEntity2Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e2Id = BigInteger.valueOf(Integer.parseInt(e2IdString));
		//NodeType e2 = client.getNode(e2Id);
		Entity e2 = this.getEntity(e2Id);*/
		BigInteger e2Id = getIdFromURL(toUpdate.getEntity2Id(), TypeOfData.ENTITY);
		
		if(e1Id != null && e2Id != null){ //both entities exists
			
			//modifico la relazione attuale cambiando il contenuto del nodo
			// -> se fallisce non faccio nulla perchè non ho ancora modificato nulla! -> la modifica del nodo è fallita, 
			//la modifica delle relazioni non è ancora stata fatta
			if (client.updateNode(id, toUpdate.getRelationName().toString(), null, TypeOfData.RELATION_2ENTITIES) == null) {
				return null;
			}	
			else{
				//NodeType node = client.createNode(r2e.getRelationName().toString(),null,TypeOfData.RELATION_2ENTITIES);
				
				//creo le relazioni nuove
				RelationshipResponseType r1 = client.createRelationshipReturnResponse(id, e1Id, TypeOfRelationship.ENTITY1);
				RelationshipResponseType r2 = client.createRelationshipReturnResponse(id, e2Id, TypeOfRelationship.ENTITY2);
				//r2 = null;
				if(r1 != null && r2 != null){
					//cancello le relazioni vecchie
					Boolean delOldR1Correct = client.deleteRelationshipGivenURI(old_r1);
					Boolean delOldR2Correct = client.deleteRelationshipGivenURI(old_r2);
					//Boolean delOldR1Correct = false;
					//Boolean delOldR2Correct = true;
					
					if (delOldR1Correct == true && delOldR2Correct == true){ //everything OK
						//operation done in order to returning the same results as create
						/*toUpdate.setEntity1Id(getIdFromURL(toUpdate.getEntity1Id()).toString());
						toUpdate.setEntity2Id(getIdFromURL(toUpdate.getEntity2Id()).toString());*/
						toUpdate.setEntity1Id(e1Id.toString());
						toUpdate.setEntity2Id(e2Id.toString());
						
						this.setDerPropAsToBeUpdated();
						
						return toUpdate;
					}else{ //case in which the delete of old relationships goes wrong

						//delete new relationships to roll back
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
						client.deleteRelationshipGivenURI(new URI(r2.getSelf()));
						
						if(delOldR1Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())), TypeOfRelationship.ENTITY1);
						}
						
						if(delOldR2Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity2Id())), TypeOfRelationship.ENTITY2);
						}
						client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_2ENTITIES);
						throw new Exception();
						
					}
				}
				else{ //case in which the creation of new relationships goes wrong
					if(r1 != null)
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
					if(r2 != null)
						client.deleteRelationshipGivenURI(new URI(r2.getSelf()));
					//client.deleteNode(node.getMetadata().getId());
					client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_2ENTITIES);
					throw new Exception();
				}		
				
			}
				
		}
		else 
			throw new BadRequestException();
		

		/*if ( ! r2em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r2em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;*/
	}
	
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation3Entities(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation3Entities)
	 */
	@Override
	public synchronized Relation3Entities updateRelation3Entities(BigInteger id, Relation3Entities toUpdate) throws Exception {
		
		//per spiegazioni vedi updateRelation2Entities();
		
		Relation3Entities old = getRelation3Entities(id);
		
		URI old_r1 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())));
		URI old_r2 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity2Id())));
		URI old_r3 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity3Id())));
		
		/*//ottengo i nodi utili per creare le nuove relazioni
		String e1IdString = toUpdate.getEntity1Id().substring(toUpdate.getEntity1Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(toUpdate.getEntity1Id(), TypeOfData.ENTITY);
		
		/*String e2IdString = toUpdate.getEntity2Id().substring(toUpdate.getEntity2Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e2Id = BigInteger.valueOf(Integer.parseInt(e2IdString));
		//NodeType e2 = client.getNode(e2Id);
		Entity e2 = this.getEntity(e2Id);*/
		BigInteger e2Id = getIdFromURL(toUpdate.getEntity2Id(), TypeOfData.ENTITY);
		
		/*String e3IdString = toUpdate.getEntity3Id().substring(toUpdate.getEntity3Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e3Id = BigInteger.valueOf(Integer.parseInt(e3IdString));
		//NodeType e3 = client.getNode(e3Id);
		Entity e3 = this.getEntity(e3Id);*/
		BigInteger e3Id = getIdFromURL(toUpdate.getEntity3Id(), TypeOfData.ENTITY);
		
		
		if(e1Id != null && e2Id != null && e3Id != null){ //both entities exists
			
			if (client.updateNode(id, toUpdate.getRelationName().toString(), null, TypeOfData.RELATION_3ENTITIES) == null) {
				return null;
			}	
			else{

				RelationshipResponseType r1 = client.createRelationshipReturnResponse(id, e1Id, TypeOfRelationship.ENTITY1);
				RelationshipResponseType r2 = client.createRelationshipReturnResponse(id, e2Id, TypeOfRelationship.ENTITY2);
				RelationshipResponseType r3 = client.createRelationshipReturnResponse(id, e3Id, TypeOfRelationship.ENTITY3);
				//r2 = null;
				if(r1 != null && r2 != null && r3 != null ){
					Boolean delOldR1Correct = client.deleteRelationshipGivenURI(old_r1);
					Boolean delOldR2Correct = client.deleteRelationshipGivenURI(old_r2);
					Boolean delOldR3Correct = client.deleteRelationshipGivenURI(old_r3);

					
					if (delOldR1Correct == true && delOldR2Correct == true && delOldR3Correct == true){ //everything OK
						//operation done in order to returning the same results as create
						toUpdate.setEntity1Id(e1Id.toString());
						toUpdate.setEntity2Id(e2Id.toString());
						toUpdate.setEntity3Id(e3Id.toString());
						
						this.setDerPropAsToBeUpdated();
						
						return toUpdate;
					}else{ //case in which the delete of old relationships goes wrong

						//delete new relationships to roll back
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
						client.deleteRelationshipGivenURI(new URI(r2.getSelf()));
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
						
						if(delOldR1Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())), TypeOfRelationship.ENTITY1);
						}
						
						if(delOldR2Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity2Id())), TypeOfRelationship.ENTITY2);
						}
						
						if(delOldR3Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity3Id())), TypeOfRelationship.ENTITY3);
						}
						client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_3ENTITIES);
						throw new Exception();
						
					}
				}
				else{ //case in which the creation of new relationships goes wrong
					if(r1 != null)
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
					if(r2 != null)
						client.deleteRelationshipGivenURI(new URI(r2.getSelf()));
					if(r3 != null)
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
					//client.deleteNode(node.getMetadata().getId());
					client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_3ENTITIES);
					throw new Exception();
				}		
				
			}
				
		}
		else 
			throw new BadRequestException();
		
		
		/*if ( ! r3em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r3em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;*/
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation2Entities1Threat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation2Entities1Threat)
	 */
	@Override
	public synchronized Relation2Entities1Threat updateRelation2Entities1Threat(BigInteger id, Relation2Entities1Threat toUpdate) throws Exception {
		
		//per spiegazioni vedi updateRelation2Entities();
		
		Relation2Entities1Threat old = getRelation2Entities1Threat(id);
		
		URI old_r1 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())));
		URI old_r2 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity2Id())));
		URI old_r3 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getThreatId())));
		
		//ottengo i nodi utili per creare le nuove relazioni
		/*String e1IdString = toUpdate.getEntity1Id().substring(toUpdate.getEntity1Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(toUpdate.getEntity1Id(), TypeOfData.ENTITY);
		
		/*String e2IdString = toUpdate.getEntity2Id().substring(toUpdate.getEntity2Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger e2Id = BigInteger.valueOf(Integer.parseInt(e2IdString));
		//NodeType e2 = client.getNode(e2Id);
		Entity e2 = this.getEntity(e2Id);*/
		BigInteger e2Id = getIdFromURL(toUpdate.getEntity2Id(), TypeOfData.ENTITY);
		
		/*String tIdString = toUpdate.getThreatId().substring(toUpdate.getThreatId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger tId = BigInteger.valueOf(Integer.parseInt(tIdString));
		//NodeType t = client.getNode(tId);
		Threat t = this.getThreat(tId);*/
		BigInteger tId = getIdFromURL(toUpdate.getThreatId(), TypeOfData.THREAT);
		
		if(e1Id != null && e2Id != null && tId != null){ //both entities exists
			
			if (client.updateNode(id, toUpdate.getRelationName().toString(), null, TypeOfData.RELATION_2ENTITIES_1THREAT) == null) {
				return null;
			}	
			else{

				RelationshipResponseType r1 = client.createRelationshipReturnResponse(id, e1Id, TypeOfRelationship.ENTITY1);
				RelationshipResponseType r2 = client.createRelationshipReturnResponse(id, e2Id, TypeOfRelationship.ENTITY2);
				RelationshipResponseType r3 = client.createRelationshipReturnResponse(id, tId, TypeOfRelationship.THREAT);
				
				//r2 = null;
				if(r1 != null && r2 != null && r3 != null){
					//cancello le relazioni vecchie
					Boolean delOldR1Correct = client.deleteRelationshipGivenURI(old_r1);
					Boolean delOldR2Correct = client.deleteRelationshipGivenURI(old_r2);
					Boolean delOldR3Correct = client.deleteRelationshipGivenURI(old_r3);
					//Boolean delOldR1Correct = false;
					//Boolean delOldR2Correct = true;
					
					if (delOldR1Correct == true && delOldR2Correct == true && delOldR3Correct == true){ //everything OK
					
						//operation done in order to returning the same results as create
						toUpdate.setEntity1Id(e1Id.toString());
						toUpdate.setEntity2Id(e2Id.toString());
						toUpdate.setThreatId(tId.toString());
						
						this.setDerPropAsToBeUpdated();
						
						return toUpdate;
					}else{ //case in which the delete of old relationships goes wrong

						//delete new relationships to roll back
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
						client.deleteRelationshipGivenURI(new URI(r2.getSelf()));
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
						
						if(delOldR1Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())), TypeOfRelationship.ENTITY1);
						}
						
						if(delOldR2Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity2Id())), TypeOfRelationship.ENTITY2);
						}
						
						if(delOldR3Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getThreatId())), TypeOfRelationship.THREAT);
						}
						
						client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_2ENTITIES_1THREAT);
						throw new Exception();
						
					}
				}
				else{ //case in which the creation of new relationships goes wrong
					if(r1 != null)
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
					if(r2 != null)
						client.deleteRelationshipGivenURI(new URI(r2.getSelf()));
					if(r3 != null)
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
					//client.deleteNode(node.getMetadata().getId());
					client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_2ENTITIES_1THREAT);
					throw new Exception();
				}		
				
			}
				
		}
		else 
			throw new BadRequestException();
		
		/*if ( ! r2e1tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r2e1tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;*/
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateRelation1Entity1Threat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Relation1Entity1Threat)
	 */
	@Override
	public synchronized Relation1Entity1Threat updateRelation1Entity1Threat(BigInteger id, Relation1Entity1Threat toUpdate) throws Exception {
		
		//per spiegazioni vedi updateRelation2Entities();
		
		Relation1Entity1Threat old = getRelation1Entity1Threat(id);
		
		URI old_r1 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())));
		URI old_r3 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getThreatId())));
		
		/*//ottengo i nodi utili per creare le nuove relazioni
		String e1IdString = toUpdate.getEntity1Id().substring(toUpdate.getEntity1Id().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger e1Id = BigInteger.valueOf(Integer.parseInt(e1IdString));
		//NodeType e1 = client.getNode(e1Id);
		Entity e1 = this.getEntity(e1Id);*/
		BigInteger e1Id = getIdFromURL(toUpdate.getEntity1Id(), TypeOfData.ENTITY);
		
		/*String tIdString = toUpdate.getThreatId().substring(toUpdate.getThreatId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger tId = BigInteger.valueOf(Integer.parseInt(tIdString));
		//NodeType t = client.getNode(tId);
		Threat t = this.getThreat(tId);*/
		BigInteger tId = getIdFromURL(toUpdate.getThreatId(), TypeOfData.THREAT);
		
		if(e1Id != null && tId != null){ //both entities exists
			
			if (client.updateNode(id, toUpdate.getRelationName().toString(), null, TypeOfData.RELATION_1ENTITY_1THREAT) == null) {
				return null;
			}	
			else{

				RelationshipResponseType r1 = client.createRelationshipReturnResponse(id, e1Id, TypeOfRelationship.ENTITY1);
				RelationshipResponseType r3 = client.createRelationshipReturnResponse(id, tId, TypeOfRelationship.THREAT);
				
				//r2 = null;
				if(r1 != null && r3 != null){
					//cancello le relazioni vecchie
					Boolean delOldR1Correct = client.deleteRelationshipGivenURI(old_r1);
					Boolean delOldR3Correct = client.deleteRelationshipGivenURI(old_r3);
					//Boolean delOldR1Correct = false;
					//Boolean delOldR2Correct = true;
					
					if (delOldR1Correct == true && delOldR3Correct == true){ //everything OK

						//operation done in order to returning the same results as create
						toUpdate.setEntity1Id(e1Id.toString());
						toUpdate.setThreatId(tId.toString());
						
						this.setDerPropAsToBeUpdated();
						
						return toUpdate;
					}else{ //case in which the delete of old relationships goes wrong

						//delete new relationships to roll back
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
						
						if(delOldR1Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntity1Id())), TypeOfRelationship.ENTITY1);
						}
						
						if(delOldR3Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getThreatId())), TypeOfRelationship.THREAT);
						}
						
						client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_1ENTITY_1THREAT);
						throw new Exception();
						
					}
				}
				else{ //case in which the creation of new relationships goes wrong
					if(r1 != null)
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
					if(r3 != null)
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
					//client.deleteNode(node.getMetadata().getId());
					client.updateNode(id, old.getRelationName().toString(), null, TypeOfData.RELATION_1ENTITY_1THREAT);
					throw new Exception();
				}		
				
			}
				
		}
		else 
			throw new BadRequestException();
		
		/*if ( ! r1e1tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		r1e1tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;
		*/
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateProperty1Entity(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity)
	 */
	@Override
	public synchronized Property1Entity updateProperty1Entity(BigInteger id, Property1Entity toUpdate) throws Exception {
		
		//per spiegazioni vedi updateRelation2Entities();
		
		Property1Entity old = getProperty1Entity(id);
		
		URI old_r1 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntityId())));
		
		/*//ottengo i nodi utili per creare le nuove relazioni
		String eIdString = toUpdate.getEntityId().substring(toUpdate.getEntityId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger eId = BigInteger.valueOf(Integer.parseInt(eIdString));
		//NodeType e = client.getNode(eId);
		Entity e = this.getEntity(eId);*/
		BigInteger eId = getIdFromURL(toUpdate.getEntityId(), TypeOfData.ENTITY);
		
		if(eId != null){ //both entities exists
			
			if (client.updateNode(id, toUpdate.getPropertyName().toString(), null, TypeOfData.PROPERTY_1ENTITY) == null) {
				return null;
			}	
			else{

				RelationshipResponseType r1 = client.createRelationshipReturnResponse(id, eId, TypeOfRelationship.ENTITY);
				
				//r2 = null;
				if(r1 != null){
					//cancello le relazioni vecchie
					Boolean delOldR1Correct = client.deleteRelationshipGivenURI(old_r1);
					//Boolean delOldR1Correct = false;
					//Boolean delOldR2Correct = true;
					
					if (delOldR1Correct == true){ //everything OK
					
						//operation done in order to returning the same results as create
						toUpdate.setEntityId(eId.toString());
						
						this.setDerPropAsToBeUpdated();
						
						return toUpdate;
					}else{ //case in which the delete of old relationships goes wrong

						//delete new relationships to roll back
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
						
						if(delOldR1Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntityId())), TypeOfRelationship.ENTITY);
						}
						
						client.updateNode(id, old.getPropertyName().toString(), null, TypeOfData.PROPERTY_1ENTITY);
						throw new Exception();
						
					}
				}
				else{ //case in which the creation of new relationships goes wrong
					if(r1 != null)
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
					//client.deleteNode(node.getMetadata().getId());
					client.updateNode(id, old.getPropertyName().toString(), null, TypeOfData.PROPERTY_1ENTITY);
					throw new Exception();
				}		
				
			}
				
		}
		else 
			throw new BadRequestException();
		
		/*if ( ! p1em.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		p1em.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;*/
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#updateProperty1Entity1Threat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.Property1Entity1Threat)
	 */
	@Override
	public synchronized Property1Entity1Threat updateProperty1Entity1Threat(BigInteger id, Property1Entity1Threat toUpdate) throws Exception {
		
		//per spiegazioni vedi updateRelation2Entities();
		
		Property1Entity1Threat old = getProperty1Entity1Threat(id);
		
		URI old_r1 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntityId())));
		URI old_r3 = client.getRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getThreatId())));
		
		//ottengo i nodi utili per creare le nuove relazioni
		/*String eIdString = toUpdate.getEntityId().substring(toUpdate.getEntityId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e1IdString);
		BigInteger eId = BigInteger.valueOf(Integer.parseInt(eIdString));
		//NodeType e = client.getNode(eId);
		Entity e = this.getEntity(eId);*/
		BigInteger eId = getIdFromURL(toUpdate.getEntityId(), TypeOfData.ENTITY);
		
		/*String tIdString = toUpdate.getThreatId().substring(toUpdate.getThreatId().lastIndexOf("/") + 1);
		//l.log(Level.SEVERE,e2IdString);
		BigInteger tId = BigInteger.valueOf(Integer.parseInt(tIdString));
		//NodeType t = client.getNode(tId);
		Threat t = this.getThreat(tId);*/
		BigInteger tId = getIdFromURL(toUpdate.getThreatId(), TypeOfData.THREAT);
		
		if(eId != null && tId != null){ //both entities exists
			
			if (client.updateNode(id, toUpdate.getPropertyName().toString(), null, TypeOfData.PROPERTY_1ENTITY_1THREAT) == null) {
				return null;
			}	
			else{

				RelationshipResponseType r1 = client.createRelationshipReturnResponse(id, eId, TypeOfRelationship.ENTITY);
				RelationshipResponseType r3 = client.createRelationshipReturnResponse(id, tId, TypeOfRelationship.THREAT);
				
				//r2 = null;
				if(r1 != null && r3 != null){
					//cancello le relazioni vecchie
					Boolean delOldR1Correct = client.deleteRelationshipGivenURI(old_r1);
					Boolean delOldR3Correct = client.deleteRelationshipGivenURI(old_r3);
					//Boolean delOldR1Correct = false;
					//Boolean delOldR2Correct = true;
					
					if (delOldR1Correct == true && delOldR3Correct == true){ //everything OK
						

						//operation done in order to returning the same results as create
						toUpdate.setEntityId(eId.toString());
						toUpdate.setThreatId(tId.toString());
						
						this.setDerPropAsToBeUpdated();
						
						return toUpdate;
					}else{ //case in which the delete of old relationships goes wrong

						//delete new relationships to roll back
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
						
						if(delOldR1Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getEntityId())), TypeOfRelationship.ENTITY);
						}
						
						if(delOldR3Correct == true){ //ho cancellato una relazione quindi devo ricrearla							
							client.createRelationship(id, BigInteger.valueOf(Integer.parseInt(old.getThreatId())), TypeOfRelationship.THREAT);
						}
						
						client.updateNode(id, old.getPropertyName().toString(), null, TypeOfData.PROPERTY_1ENTITY_1THREAT);
						throw new Exception();
						
					}
				}
				else{ //case in which the creation of new relationships goes wrong
					if(r1 != null)
						client.deleteRelationshipGivenURI(new URI(r1.getSelf()));
					if(r3 != null)
						client.deleteRelationshipGivenURI(new URI(r3.getSelf()));
					//client.deleteNode(node.getMetadata().getId());
					client.updateNode(id, old.getPropertyName().toString(), null, TypeOfData.PROPERTY_1ENTITY_1THREAT);
					throw new Exception();
				}		
				
			}
				
		}
		else 
			throw new BadRequestException();
		
		/*if ( ! p1e1tm.containsKey(id)) //if elem does not exist, nothing is updated
			return null;

		p1e1tm.put(id, toUpdate); //.put() override di pre-existent element
		return toUpdate;*/
	}
	
	
	/**
	 *	computeResultsdeve essere synchronized per evitare che più thread in contemporanea accedano al file system.p 
	 */
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#computeResults()
	 */
	@Override
	public synchronized void computeResults() throws Exception {
		//l.log(Level.SEVERE, "Inizio computeResults() in neo4jDB");
		//PrologException: error(existence_error(procedure, '/'(control, 2)), context('/'(canbeComp, 2), _0))
		// la funzione control/2 non è stata trovata. E' stata richiamata in una canBeComp/2
		
		//PrologException: error(existence_error(procedure, '/'(assComp, 2)), context('/'(canbeComp, 2), _7))

		this.deleteAllDerivedProperty();
		
		//l.log(Level.SEVERE, "Dopo le clear");
		try{

			File file = new File("lib/system.p");

			//l.log(Level.SEVERE, "Dopo apertura file");
		    // if file doesnt exists, then create it
		    if (!file.exists()) {
		        file.createNewFile();
		    }
		    
		    FileWriter fw = new FileWriter(file.getAbsoluteFile()); //append=false
		    BufferedWriter bw = new BufferedWriter(fw);
		    //l.log(Level.SEVERE, "prima createPrologFIle");
	
		    String toWrite = createPrologFile();
		    //l.log(Level.SEVERE, "Fine createPrologFile");
		    
		    bw.write(toWrite);
		    bw.close();

		    //l.log(Level.SEVERE, "Scrittura su file effettuata");
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
			
			
			//Logger logger = Logger.getLogger(InMemoryDB.class.getName());
	        // log messages using log(Level level, String msg) 
			//l.log(Level.SEVERE, "Dopo le query");
			
			if(q1.hasSolution() && q2.hasSolution()){ 	//if rules and system have been 
														//read correctly
				//Query q3Query = new Query("listing");
				//Map<String, Term>[] tmp = q3Query.allSolutions();
				/*for(Map<String,Term> help : tmp){
					l.log(Level.SEVERE, "line : \t\t" + help.toString());
				}*/
				
				
				//l.log(Level.SEVERE, "Entrambe le query hanno soluzione");
				Query q3 = new Query("set_prolog_flag",
						new Term[] {new Atom("unknown"),new Atom("warning")} 
						);
				q3.hasSolution();
				
				deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED);						
				
				deriveProperty1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING);
				
				deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE);
				
				deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_DETECTED);
				
				deriveProperty1Entity(DerivedProperty1EntityName.CANBE_RESTORED);
				
				deriveProperty1Entity(DerivedProperty1EntityName.CANBE_FIXED);
				
				Query q4 = new Query("set_prolog_flag",
						new Term[] {new Atom("unknown"),new Atom("error")} 
						);
				q4.hasSolution();

				this.setDerPropAsUpdated();
				//l.log(Level.SEVERE, "End of compute results");
			}
			else
				throw new InternalServerErrorException();
		}
		catch(Exception e){
			l.log(Level.SEVERE, "Eccezione : " + e.getMessage());
			e.printStackTrace();
			//e.printStackTrace(new PrintStream(new File("lib/tmp.txt")));
			//l.log(Level.SEVERE, e.getStackTrace().toString());
			throw new InternalServerErrorException();
		}

		//pongo le 6 query salvando per ognuna il risultato in delle mappe
		
		
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
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#deleteAllDerivedProperty()
	 */
	public synchronized void deleteAllDerivedProperty() throws Exception{
		
		//1 -> 	recupero i nodi con le labels DERIVED_PROPERTY_1ENTITY e DERIVED_PROPERTY_1ENTITY_1THREAT
		List<NodeType> listDP1E = client.searchNode("", TypeOfData.DERIVED_PROPERTY_1ENTITY);
		List<NodeType> listDP1E1T = client.searchNode("", TypeOfData.DERIVED_PROPERTY_1ENTITY_1THREAT);
		
		//2 -> 	cancello i nodi uno per uno con client.deleteNodeAndRelationships(id)
		for(NodeType n : listDP1E)
			client.deleteNodeAndRelationships(n.getMetadata().getId());
		
		for(NodeType n : listDP1E1T)
			client.deleteNodeAndRelationships(n.getMetadata().getId());

	}
	
	
	//NB anche se questo metodo non dovrebbe essere chiamato direttamente, è possibile che
	//per errore qualcuno lo faccia e siccome, crea le property sul db deve essere synchronized
	//Se il metodo è CORRETTAMENTE richiamato da computeResults(che è synchronized) il lock
	//viene semplicemente mantenuto. Se il metodo è richimato ERRONEAMENTE in maniera diretta
	//siamo sicuri che almeno verrà preso il lock!
	private synchronized void deriveProperty1Entity1Threat(DerivedProperty1Entity1ThreatName name) throws Exception{
		
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
		
		//logger.log(Level.SEVERE,"query hasSolution() " + (query.hasSolution() ? "true" : "false"));
		
		java.util.Map<String,Term>[] solutions = query.allSolutions(); 
		
		logger.log(Level.SEVERE,"Dopo .allSolutions");
		
		logger.log(Level.SEVERE,"Dopo .allSolutions -> length = " + solutions.length);
		
		for ( int i=0 ; i < solutions.length ; i++ ) { 
			
			String entityId = getIdGivenEntityName(solutions[i].get("E").toString());
			
			logger.log(Level.SEVERE,"EntityID " + entityId);
			
			String threatId = getIdGivenThreatName(solutions[i].get("T").toString());
			
			logger.log(Level.SEVERE,"ThreatID " + threatId);
			
			if(entityId != null && threatId != null){ //se threatself e entityself non sono a1, a2,...
				
				DerivedProperty1Entity1Threat tmp = new DerivedProperty1Entity1Threat();
				tmp.setPropertyName(name); 
				
				tmp.setEntityId(entityId);
				tmp.setThreatId(threatId);
				
				//m.put(derivedPropertyId, tmp);
				//derivedPropertyId = derivedPropertyId.add(BigInteger.ONE);
				this.createDerivedProperty1Entity1Threat(tmp);
				
				logger.log(Level.SEVERE, " E = "+ solutions[i].get("E") + ", T = " + solutions[i].get("T") + "\n");
				//ret += " E = "+ solutions[i].get("E") + ", T = " + solutions[i].get("T") + "\n" ;
				//System.out.println( "X = " + solutions[i].get("X")); 
			}
		}
		//return ret;
		logger.log(Level.SEVERE,"Fine " + queryName);	
	}
	
	private synchronized BigInteger createDerivedProperty1Entity1Threat(DerivedProperty1Entity1Threat dp1e1t) throws Exception {
		
		//NB diverso dal caso standard perchè qui ho direttamente l'id nel campo!
		String eIdString = dp1e1t.getEntityId();
		//l.log(Level.SEVERE,e1IdString);
		BigInteger eId = BigInteger.valueOf(Integer.parseInt(eIdString));
		NodeType e = client.getNode(eId);

		//NB diverso dal caso standard perchè qui ho direttamente l'id nel campo!
		String tIdString = dp1e1t.getThreatId();
		//l.log(Level.SEVERE,e2IdString);
		BigInteger tId = BigInteger.valueOf(Integer.parseInt(tIdString));
		NodeType t = client.getNode(tId);
		
		if(e != null && t != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(dp1e1t.getPropertyName().toString(),null,TypeOfData.DERIVED_PROPERTY_1ENTITY_1THREAT);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), eId, TypeOfRelationship.ENTITY);
			RelationshipType r2 = client.createRelationship(node.getMetadata().getId(), tId, TypeOfRelationship.THREAT);
			if(r1 != null && r2 != null )
				return node.getMetadata().getId();
			else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), eId);
				if(r2 != null)
					client.deleteRelationship(node.getMetadata().getId(), tId);
				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else 
			throw new Exception();
		
	}
	
	private synchronized void deriveProperty1Entity(DerivedProperty1EntityName name) throws Exception{
		
		Logger logger = Logger.getLogger(Neo4jDB.class.getName());
		
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
			
			String entityId = getIdGivenEntityName(solutions[i].get("E").toString());
			
			logger.log(Level.SEVERE, " EntityId " + entityId);
			
			if(entityId != null){ //se entityself non sono a1, a2,...
				
				DerivedProperty1Entity tmp = new DerivedProperty1Entity();
				tmp.setPropertyName(name); 
				
				tmp.setEntityId(entityId);
				
				//m.put(derivedPropertyId, tmp);
				//derivedPropertyId = derivedPropertyId.add(BigInteger.ONE);
				
				this.createDerivedProperty1Entity(tmp);
				
				logger.log(Level.SEVERE, " E = "+ solutions[i].get("E") + "\n");
				//ret += " E = "+ solutions[i].get("E") + ", T = " + solutions[i].get("T") + "\n" ;
				//System.out.println( "X = " + solutions[i].get("X")); 
			}
		}
		//return ret;
		logger.log(Level.SEVERE,"Fine " + queryName);	
	}
	
	private synchronized BigInteger createDerivedProperty1Entity(DerivedProperty1Entity dp1e) throws Exception {
		
		//NB diverso dal caso standard perchè qui ho direttamente l'id nel campo!
		String eIdString = dp1e.getEntityId();
		//l.log(Level.SEVERE,e1IdString);
		BigInteger eId = BigInteger.valueOf(Integer.parseInt(eIdString));
		NodeType e = client.getNode(eId);
		
		if(e != null){ //both entities exists
			//l.log(Level.SEVERE,"Both entity exists");
			NodeType node = client.createNode(dp1e.getPropertyName().toString(),null,TypeOfData.DERIVED_PROPERTY_1ENTITY);
			RelationshipType r1 = client.createRelationship(node.getMetadata().getId(), eId, TypeOfRelationship.ENTITY);
			if(r1 != null)
				return node.getMetadata().getId();
			else{ //this branch should never happen!
				
				if(r1 != null)
					client.deleteRelationship(node.getMetadata().getId(), eId);
				client.deleteNode(node.getMetadata().getId());
				throw new Exception();
			}
				
		}
		else 
			throw new Exception();
		
	}
	
	private synchronized void updateDerivedPropertiesWhenNeeded() throws Exception{
		if(! areDerivedPropertiesUpdated()) //derived properties not updated
			this.computeResults();
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public synchronized DerivedProperty1Entity1ThreatPage getDerivedProperties1Entity1Threat(BigInteger page, DerivedProperty1Entity1ThreatName name,
			String entitySelf, String threatSelf) throws Exception {
		
		updateDerivedPropertiesWhenNeeded();
		
		DerivedProperty1Entity1ThreatPage dp1e1tPage = new DerivedProperty1Entity1ThreatPage();
		if (page.equals(BigInteger.ONE)) {
			
			String entityName = null;
			String threatName = null;
			
			if( ! entitySelf.equals("") ){
				BigInteger entityId = getIdFromURL(entitySelf, TypeOfData.ENTITY);
				if(entityId == null)
					throw new BadRequestException();
				Entity e = this.getEntity(entityId);
				if(e == null) //the id sent is not associated to an entity or to an existing node
					throw new BadRequestException();
				entityName = e.getName();
			}
			
			if( ! threatSelf.equals("") ){
				BigInteger threatId = getIdFromURL(threatSelf, TypeOfData.THREAT);
				if(threatId == null)
					throw new BadRequestException();
				Threat t = this.getThreat(threatId);
				if(t == null) //the id sent is not associated to a threat or to an existing node
					throw new BadRequestException();
				threatName = t.getName();
			}
			
			
			List<NodeType> nodes = client.searchNodeGiven2RelationshipsEndName(name.toString(), TypeOfData.DERIVED_PROPERTY_1ENTITY_1THREAT, entityName, threatName);
			
			Map<BigInteger,DerivedProperty1Entity1Threat> map = dp1e1tPage.getMap();

			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),derivedProperty1Entity1ThreatFromNode(node,name));
			}	
			
			/*DerivedProperty1Entity1Threat tmp = new DerivedProperty1Entity1Threat();
			tmp.setPropertyName(DerivedProperty1Entity1ThreatName.CANBE_DETECTED);
			tmp.setThreatId(new Integer(16).toString());
			tmp.setEntityId(new Integer(1).toString());
			map.put(BigInteger.valueOf(100), tmp);*/
			
			/*if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED) == true)
				map.putAll(compromisedMap);		
			else if	(name.equals(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE) == true)
				map.putAll(vulnerableMap);
			else if	(name.equals(DerivedProperty1Entity1ThreatName.CANBE_DETECTED) == true){
				*DerivedProperty1Entity1Threat tmp = new DerivedProperty1Entity1Threat();
				tmp.setPropertyName(DerivedProperty1Entity1ThreatName.CANBE_DETECTED);
				tmp.setThreatId("detected");
				tmp.setEntityId("detected");
				detectedMap.put(derivedPropertyId,tmp);
				map.putAll(detectedMap);*
			}
			else 
				throw new Exception();*/
	
			dp1e1tPage.setTotalPages(BigInteger.ONE);
		}		
		return dp1e1tPage;

	}
	
	private DerivedProperty1Entity1Threat derivedProperty1Entity1ThreatFromNode(NodeType nt, DerivedProperty1Entity1ThreatName name) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		DerivedProperty1Entity1Threat ret = of.createDerivedProperty1Entity1Threat();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		ret.setPropertyName(name);
		
		/*String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		
		
		if(name.equals("compromised"))
			ret.setPropertyName(BasicProperty1Entity1ThreatName.COMPROMISED);
		else if(name.equals("vulnerable"))
			ret.setPropertyName(BasicProperty1Entity1ThreatName.VULNERABLE);
		else
			throw new Exception();*/

		ret.setEntityId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY));

		ret.setThreatId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.THREAT));
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getProperties1Entity1Threat(java.math.BigInteger)
	 */
	@Override
	public synchronized DerivedProperty1EntityPage getDerivedProperties1Entity(BigInteger page, DerivedProperty1EntityName name, String entitySelf) throws Exception {
		
		updateDerivedPropertiesWhenNeeded();
		
		DerivedProperty1EntityPage dp1ePage = new DerivedProperty1EntityPage();
		if (page.equals(BigInteger.ONE)) {
			
			String entityName = null;
			
			if( ! entitySelf.equals("") ){
				BigInteger entityId = getIdFromURL(entitySelf, TypeOfData.ENTITY);
				if(entityId == null)
					throw new BadRequestException();
				Entity e = this.getEntity(entityId);
				if(e == null) //the id sent is not associated to an entity or to an existing node
					throw new BadRequestException();
				entityName = e.getName();
			}			
			
			List<NodeType> nodes = client.searchNodeGiven2RelationshipsEndName(name.toString(), TypeOfData.DERIVED_PROPERTY_1ENTITY, entityName, null);
			//List<NodeType> nodes = client.searchNode(name.toString(), TypeOfData.DERIVED_PROPERTY_1ENTITY);
			
			Map<BigInteger,DerivedProperty1Entity> map = dp1ePage.getMap();

			for (NodeType node:nodes) {
				map.put(node.getMetadata().getId(),derivedProperty1EntityFromNode(node,name));
			}	
			
			/*DerivedProperty1Entity tmp = new DerivedProperty1Entity();
			tmp.setPropertyName(DerivedProperty1EntityName.CANBE_FIXED);
			tmp.setEntityId(new Integer(1).toString());
			map.put(BigInteger.valueOf(100), tmp);*/
			
			/*if(name.equals(DerivedProperty1EntityName.CANBE_MALFUNCTIONING) == true)
				map.putAll(malfunctioningMap);		
			else if	(name.equals(DerivedProperty1EntityName.CANBE_RESTORED) == true){
				*DerivedProperty1Entity tmp = new DerivedProperty1Entity();
				tmp.setPropertyName(DerivedProperty1EntityName.CANBE_RESTORED);
				tmp.setEntityId("restored");
				restoredMap.put(derivedPropertyId,tmp);*
				map.putAll(restoredMap);
			} else if	(name.equals(DerivedProperty1EntityName.CANBE_FIXED) == true){
				*DerivedProperty1Entity tmp = new DerivedProperty1Entity();
				tmp.setPropertyName(DerivedProperty1EntityName.CANBE_FIXED);
				tmp.setEntityId("fixed");
				fixedMap.put(derivedPropertyId,tmp);*
				map.putAll(fixedMap);
			}
			else 
				throw new Exception();*/
	
			dp1ePage.setTotalPages(BigInteger.ONE);
		}		
		return dp1ePage;
		
	}
	
	private DerivedProperty1Entity derivedProperty1EntityFromNode(NodeType nt, DerivedProperty1EntityName name) throws Exception{
		
		//l.log(Level.SEVERE,"Inizio relation2EntitiesFromNode");
		it.polito.dp2.TAMELESS.sol.service.jaxb.ObjectFactory of =  new ObjectFactory();
		DerivedProperty1Entity ret = of.createDerivedProperty1Entity();
		
		//l.log(Level.SEVERE,"Dopo create relation2Entities di of");

		ret.setPropertyName(name);
		
		/*String name = nt.getData().getName().toLowerCase();
		
		//l.log(Level.SEVERE,"Name della r : "+ name);
		
		
		if(name.equals("compromised"))
			ret.setPropertyName(BasicProperty1Entity1ThreatName.COMPROMISED);
		else if(name.equals("vulnerable"))
			ret.setPropertyName(BasicProperty1Entity1ThreatName.VULNERABLE);
		else
			throw new Exception();*/

		ret.setEntityId(client.getEndNodeOfRelationshipsGivenType(nt.getMetadata().getId(), TypeOfRelationship.ENTITY));
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getDerivedProperty1Entity1Threat(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.DerivedProperty1Entity1ThreatName)
	 */
	public synchronized DerivedProperty1Entity1Threat getDerivedProperty1Entity1Threat(BigInteger id, DerivedProperty1Entity1ThreatName name) throws Exception{

		updateDerivedPropertiesWhenNeeded();
		/*
		 * 	MATCH (a), (b)
			WHERE a.name = "ids" AND b.name = "dos" 
			CREATE (c:DERIVED_PROPERTY_1ENTITY_1THREAT {name:"CANBE_DETECTED"})-[pr:ENTITY]->(a), (c)-[pr1:THREAT]->(b)
			RETURN a, b, c
		 * */

		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		
		
		//NB nel caso di get all properties, recupero dal db solo le properties che hanno quel name quindi
		//posso passare in maniera fissa name alla derivedProperty1Entity1ThreatFromNode(). In questo caso invece,
		//recupero le derived Prop per id e devo anche controllare che il nome associato sia uguale a name!
		if(ret.getMetadata().getLabels().size() == 1 
				&& ret.getMetadata().getLabels().contains(TypeOfData.DERIVED_PROPERTY_1ENTITY_1THREAT.toString())
				&& ret.getData().getName().equals(name.toString()))
			return derivedProperty1Entity1ThreatFromNode(ret,name);
		
		return null;

	}
	
	/* (non-Javadoc)
	 * @see it.polito.dp2.TAMELESS.sol.db.DB#getDerivedProperty1Entity(java.math.BigInteger, it.polito.dp2.TAMELESS.sol.service.jaxb.DerivedProperty1EntityName)
	 */
	public synchronized DerivedProperty1Entity getDerivedProperty1Entity(BigInteger id, DerivedProperty1EntityName name) throws Exception{

		updateDerivedPropertiesWhenNeeded();
		/*
		 * 	MATCH (a)
			WHERE a.name = "website" 
			CREATE (c:DERIVED_PROPERTY_1ENTITY {name:"CANBE_FIXED"})-[pr:ENTITY]->(a)
			RETURN a,c
		 */
		
		NodeType ret = client.getNode(id);
		if(ret == null)
			return null;
		
		
		//NB nel caso di get all properties, recupero dal db solo le properties che hanno quel name quindi
		//posso passare in maniera fissa name alla derivedProperty1EntityFromNode(). In questo caso invece,
		//recupero le derived Prop per id e devo anche controllare che il nome associato sia uguale a name!
		if(ret.getMetadata().getLabels().size() == 1 
				&& ret.getMetadata().getLabels().contains(TypeOfData.DERIVED_PROPERTY_1ENTITY.toString())
				&& ret.getData().getName().equals(name.toString()))
			return derivedProperty1EntityFromNode(ret,name);
		
		return null;

	}
	
}
