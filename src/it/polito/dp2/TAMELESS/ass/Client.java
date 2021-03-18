/**
 * 
 */
package it.polito.dp2.TAMELESS.ass;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.TAMELESS.sol.client.*;

/**
 * An interface for interacting with the Biblio Service
 *
 */
public interface Client{
	
	public Entity createEntity(String entityName, EntityNature entityType) throws ServiceException;
	
	public Set<Entity> getEntities(String name) throws ServiceException;
	
	public Threat createThreat(String threatName) throws ServiceException;
	
	public Set<Threat> getThreats(String name) throws ServiceException;
		
	public Relation2Entities createRelation2Entities(Relation2EntitiesName relationName,
			Entity entity1, Entity entity2) throws DestroyedException,ServiceException;
	
	public Set<Relation2Entities> getRelations2Entities(String name) throws ServiceException;
		
	public Relation3Entities createRelation3Entities(Relation3EntitiesName relationName,
			Entity entity1,Entity entity2, Entity entity3) throws DestroyedException, ServiceException;
		
	public Set<Relation3Entities> getRelations3Entities(String name) throws ServiceException;
		
	public Relation2Entities1Threat createRelation2Entities1Threat(Relation2Entities1ThreatName relationName,
			Entity entity1,Entity entity2, Threat threat) throws ServiceException;
	
	public Set<Relation2Entities1Threat> getRelations2Entities1Threat(String name) throws ServiceException;
		
	public Relation1Entity1Threat createRelation1Entity1Threat(Relation1Entity1ThreatName relationName,
			Entity entity1, Threat threat) throws ServiceException;
	
	public Set<Relation1Entity1Threat> getRelations1Entity1Threat(String name) throws ServiceException;
		
	public Property1Entity createProperty1Entity(BasicProperty1EntityName propertyName, Entity entity) 
			throws ServiceException;
	
	public Set<Property1Entity> getProperties1Entity(String name) throws ServiceException;
		
	public Property1Entity1Threat createProperty1Entity1Threat(BasicProperty1Entity1ThreatName propertyName, 
			Entity entity, Threat threat) throws ServiceException;
	
	
	public Set<Property1Entity1Threat> getProperties1Entity1Threat(String name) throws ServiceException;
	
	//GET BY ID section
	
	//public it.polito.dp2.TAMELESS.sol.client.Entity getEntity(BigInteger id) throws ServiceException, NotFoundException ;
	
	//public it.polito.dp2.TAMELESS.sol.client.Threat getThreat(BigInteger id) throws ServiceException, NotFoundException;
	
	//public it.polito.dp2.TAMELESS.sol.client.Relation2Entities getRelation2Entities(BigInteger id) throws ServiceException, NotFoundException;
	
	//public Relation3Entities getRelation3Entities(BigInteger id) throws ServiceException, NotFoundException;
	
	//public Relation2Entities1Threat getRelation2Entities1Threat(BigInteger id) throws ServiceException, NotFoundException ;
	
	//public Relation1Entity1Threat getRelation1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException ;
	
	//public Property1Entity getProperty1Entity(BigInteger id) throws ServiceException, NotFoundException ;
	
	//public Property1Entity1Threat getProperty1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException;	

	//DELETE section
	
	//public void deleteEntity(BigInteger id) throws ServiceException, NotFoundException, EntityInException;
	
	//public void deleteThreat(BigInteger id) throws ServiceException, NotFoundException , ClientErrorException;
	
	//public void deleteRelation2Entities(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException;
	
	//public void deleteRelation3Entities(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException;
	
	//public void deleteRelation2Entities1Threat(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException;
	
	//public void deleteRelation1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException;
	
	//public void deleteProperty1Entity(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException;
	
	//public void deleteProperty1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException;
	
	//PUT section
	
	//public it.polito.dp2.TAMELESS.sol.client.Entity updateEntity(BigInteger id, String entityName, EntityNature entityType) 
			//throws ServiceException,BadRequestException,NotFoundException;
	
	//public it.polito.dp2.TAMELESS.sol.client.Threat updateThreat(BigInteger id, String threatName) throws ServiceException,BadRequestException,NotFoundException;	
		
	//public it.polito.dp2.TAMELESS.sol.client.Relation2Entities updateRelation2Entities(BigInteger id, Relation2EntitiesName relationName,
			//String entity1Name,String entity2Name) throws ServiceException,BadRequestException,NotFoundException;
	
	//public it.polito.dp2.TAMELESS.sol.client.Relation3Entities updateRelation3Entities(BigInteger id, Relation3EntitiesName relationName,
			//String entity1Name,String entity2Name, String entity3Name) 
					//throws ServiceException,BadRequestException,NotFoundException;	
		
	//public it.polito.dp2.TAMELESS.sol.client.Relation2Entities1Threat updateRelation2Entities1Threat(BigInteger id, Relation2Entities1ThreatName relationName,
			//String entity1Name,String entity2Name, String threatName) 
					//throws ServiceException,BadRequestException,NotFoundException;		
	
	//public it.polito.dp2.TAMELESS.sol.client.Relation1Entity1Threat updateRelation1Entity1Threat(BigInteger id, Relation1Entity1ThreatName relationName,
			//String entity1Name, String threatName) throws ServiceException,BadRequestException,NotFoundException;		
	
	//public it.polito.dp2.TAMELESS.sol.client.Property1Entity updateProperty1Entity(BigInteger id, BasicProperty1EntityName propertyName, 
			//String entityName) throws ServiceException,BadRequestException,NotFoundException;	
		
	//public it.polito.dp2.TAMELESS.sol.client.Property1Entity1Threat updateProperty1Entity1Threat(BigInteger id, 
			//BasicProperty1Entity1ThreatName propertyName, String entityName, String threatName) 
					//throws ServiceException,BadRequestException,NotFoundException;
	
	
	//Derived Properties
	
	public Boolean computeResults() throws ServiceException;
	
	public void deleteAllDerivedProperties() throws ServiceException;
	
	public Set<DerivedProperty1Entity> getDerivedProperties1Entity(DerivedProperty1EntityName name, Entity e) 
			throws ServiceException, NotAcceptableNameException;
		
	public Set<DerivedProperty1Entity1Threat> getDerivedProperties1Entity1Threat
		(DerivedProperty1Entity1ThreatName name, Entity e, Threat t) throws ServiceException, NotAcceptableNameException;
}
