package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
/*
import it.polito.dp2.BIB.sol3.service.SearchScope;
import it.polito.dp2.BIB.sol3.service.jaxb.Citation;
import it.polito.dp2.BIB.sol3.service.jaxb.Item;
*/

import it.polito.dp2.TAMELESS.sol.service.jaxb.*;

/**
 * An interface to interact with a DB of entities and threats objects
 *
 */
public interface DB {

	/**
	 * Create a new entity in the DB using the given Entity information
	 * @param entity the entity object with the information about the entity to be created
	 * @return an integer id assigned to the created entity
	 * @throws NullPointerException if entity is null
	 * @throws Exception if the entity cannot be created in the DB for other reasons
	 */
	BigInteger createEntity(Entity entity) throws Exception;
	
	/**
	 * Perform a query on the DB to search entities
	 * @param name the name for selecting entities.
	 * @param page the number of the results page to get
	 * @return an EntityPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	EntityPage getEntities(String name, BigInteger page) throws Exception;

	
	/**
	 * Create a new threat in the DB using the given Threat information
	 * @param threat the threat object with the information about the threat to be created
	 * @return an integer id assigned to the created threat
	 * @throws NullPointerException if threat is null
	 * @throws Exception if the threat cannot be created in the DB for other reasons
	 */
	BigInteger createThreat(Threat threat) throws Exception;
	
	/**
	 * Perform a query on the DB to search threats 
	 * @param name the name for selecting threats.
	 * @param page the number of the results page to get
	 * @return a ThreatPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	ThreatPage getThreats(String name, BigInteger page) throws Exception;
	
	/**
	 * Create a new Relation2Entities in the DB using the given information
	 * @param Relation2Entity the Relation2Entities object with the information about the relation to be created
	 * @return an integer id assigned to the created relation
	 * @throws NullPointerException if r2e is null
	 * @throws Exception if the relation cannot be created in the DB for other reasons
	 */
	BigInteger createRelation2Entities(Relation2Entities r2e) throws Exception;
	
	/**
	 * Perform a query on the DB to get the relation with 2 entities
	 * @param page the number of the results page to get
	 * @return a Relation2EntitiesPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	Relation2EntitiesPage getRelations2Entities(BigInteger page) throws Exception;
	
	/**
	 * Create a new Relation3Entities in the DB using the given information
	 * @param Relation3Entity the Relation3Entities object with the information about the relation to be created
	 * @return an integer id assigned to the created relation
	 * @throws NullPointerException if r3e is null
	 * @throws Exception if the relation cannot be created in the DB for other reasons
	 */
	BigInteger createRelation3Entities(Relation3Entities r3e) throws Exception;
	
	/**
	 * Perform a query on the DB to get the relation with 3 entities
	 * @param page the number of the results page to get
	 * @return a Relation3EntitiesPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	Relation3EntitiesPage getRelations3Entities(BigInteger page) throws Exception;
	
	/**
	 * Create a new Relation2Entities1Threat in the DB using the given information
	 * @param Relation2Entity1Threat the Relation2Entities1Threat object with the information about the relation to be created
	 * @return an integer id assigned to the created relation
	 * @throws NullPointerException if r2e1t is null
	 * @throws Exception if the relation cannot be created in the DB for other reasons
	 */
	BigInteger createRelation2Entities1Threat(Relation2Entities1Threat r2e1t) throws Exception;
	
	/**
	 * Perform a query on the DB to get the relation with 2 entities and 1 threat
	 * @param page the number of the results page to get
	 * @return a Relation2Entities1ThreatPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	Relation2Entities1ThreatPage getRelations2Entities1Threat(BigInteger page) throws Exception;
	
	/**
	 * Create a new Relation1Entity1Threat in the DB using the given information
	 * @param Relation1Entity1Threat the Relation1Entity1Threat object with the information about the relation to be created
	 * @return an integer id assigned to the created relation
	 * @throws NullPointerException if r1e1t is null
	 * @throws Exception if the relation cannot be created in the DB for other reasons
	 */
	BigInteger createRelation1Entity1Threat(Relation1Entity1Threat r1e1t) throws Exception;
	
	/**
	 * Perform a query on the DB to get the relation with 1 entity and 1 threat
	 * @param page the number of the results page to get
	 * @return a Relation1Entity1ThreatPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	Relation1Entity1ThreatPage getRelations1Entity1Threat(BigInteger page) throws Exception;
	
	/**
	 * Create a new Property1Entity in the DB using the given information
	 * @param Property1Entity the Property1Entity object with the information about the relation to be created
	 * @return an integer id assigned to the created relation
	 * @throws NullPointerException if p1e is null
	 * @throws Exception if the relation cannot be created in the DB for other reasons
	 */
	BigInteger createProperty1Entity(Property1Entity p1e) throws Exception;
	
	/**
	 * Perform a query on the DB to get the property with 1 entity
	 * @param page the number of the results page to get
	 * @return a Property1EntityPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	Property1EntityPage getProperties1Entity(BigInteger page) throws Exception;
	
	/**
	 * Create a new Property1Entity1Threat in the DB using the given information
	 * @param Property1Entity1Threat the Property1Entity1Threat object with the information about the relation to be created
	 * @return an integer id assigned to the created relation
	 * @throws NullPointerException if p1e1t is null
	 * @throws Exception if the relation cannot be created in the DB for other reasons
	 */
	BigInteger createProperty1Entity1Threat(Property1Entity1Threat p1e1t) throws Exception;
	
	/**
	 * Perform a query on the DB to get the property with 1 entity and 1 threat
	 * @param page the number of the results page to get
	 * @return a Property1Entity1ThreatPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	Property1Entity1ThreatPage getProperties1Entity1Threat(BigInteger page) throws Exception;
	
	//GET BY ID SECTION
	
	/**
	 * Perform a query on the DB to obtain the information related to the entity with a given id
	 * @param id the integer id of the entity
	 * @return an Entity object describing the entity with the given id, or null if an entity 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Entity getEntity(BigInteger id) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the threat with a given id
	 * @param id the integer id of the threat
	 * @return a Threat object describing the threat with the given id, or null if a threat 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Threat getThreat(BigInteger id) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the relation with a given id
	 * @param id the integer id of the relation
	 * @return a Relation2Entities object describing the relation with the given id, or null if a relation 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation2Entities getRelation2Entities(BigInteger id) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the relation with a given id
	 * @param id the integer id of the relation
	 * @return a Relation3Entities object describing the relation with the given id, or null if a relation 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation3Entities getRelation3Entities(BigInteger id) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the relation with a given id
	 * @param id the integer id of the relation
	 * @return a Relation2Entities1Threat object describing the relation with the given id, or null if a relation 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation2Entities1Threat getRelation2Entities1Threat(BigInteger id) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the relation with a given id
	 * @param id the integer id of the relation
	 * @return a Relation1Entity1Threat object describing the relation with the given id, or null if a relation 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation1Entity1Threat getRelation1Entity1Threat(BigInteger id) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the property with a given id
	 * @param id the integer id of the property
	 * @return a Property1Entity object describing the property with the given id, or null if a property 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Property1Entity getProperty1Entity(BigInteger id) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the property with a given id
	 * @param id the integer id of the property
	 * @return a Property1Entity1Threat object describing the property with the given id, or null if a property 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Property1Entity1Threat getProperty1Entity1Threat(BigInteger id) throws Exception;
	
	//DELETE section
	
	/**
	 * Remove an entity from the DB 
	 * @param id the integer id of the entity or threat
	 * @return the same integer id of the entity or threat upon successful removal
	 * or null if an entity or threat with the given id is not available in the DB
	 * @throws ConflictInOperationException if the Entity or threat is used for some properties or relation
	 * @throws Exception if the operation cannot be completed for other reasons
	 */
	BigInteger deleteEntityOrThreat(BigInteger id) throws ConflictInOperationException, Exception;
	
	
	/**
	 * Remove a Relation or Properties from the DB 
	 * @param id the integer id of the relation or properties 
	 * @return the same integer id of the relation or properties upon successful removal
	 * or null if a relation or properties with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed for other reasons
	 */
	BigInteger deleteRelOrProp(BigInteger id) throws Exception;
	
	//PUT section
	
	/**
	 * Update an entity in the DB using the given entity information
	 * @param id the integer id of the entity to update
	 * @param toUpdate the entity object with the information about the entity to be updated
	 * @return  an updated Entity object, or null if an entity 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Entity updateEntity(BigInteger id, Entity toUpdate) throws Exception;
	
	/**
	 * Update a threat in the DB using the given Threat information
	 * @param id the integer id of the threat to update
	 * @param toUpdate the threat object with the information about the threat to be updated
	 * @return  an updated Threat object, or null if a threat 
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Threat updateThreat(BigInteger id, Threat toUpdate) throws Exception;
	
	/**
	 * Update a relation in the DB using the given relation information
	 * @param id the integer id of the relation to update
	 * @param toUpdate the Relation2Entities object with the information about the relation to be updated
	 * @return  an updated Relation2Entities object, or null if a relation
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation2Entities updateRelation2Entities(BigInteger id, Relation2Entities toUpdate) throws Exception;
	
	/**
	 * Update a relation in the DB using the given relation information
	 * @param id the integer id of the relation to update
	 * @param toUpdate the Relation3Entities object with the information about the relation to be updated
	 * @return  an updated Relation3Entities object, or null if a relation
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation3Entities updateRelation3Entities(BigInteger id, Relation3Entities toUpdate) throws Exception;
	
	/**
	 * Update a relation in the DB using the given relation information
	 * @param id the integer id of the relation to update
	 * @param toUpdate the Relation2Entities1Threat object with the information about the relation to be updated
	 * @return  an updated Relation2Entities1Threat object, or null if a relation
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation2Entities1Threat updateRelation2Entities1Threat(BigInteger id, Relation2Entities1Threat toUpdate) throws Exception;
	
	/**
	 * Update a relation in the DB using the given relation information
	 * @param id the integer id of the relation to update
	 * @param toUpdate the Relation1Entity1Threat object with the information about the relation to be updated
	 * @return  an updated Relation1Entity1Threat object, or null if a relation
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Relation1Entity1Threat updateRelation1Entity1Threat(BigInteger id, Relation1Entity1Threat toUpdate) throws Exception;
	
	/**
	 * Update a property in the DB using the given relation information
	 * @param id the integer id of the property to update
	 * @param toUpdate the Property1Entity object with the information about the property to be updated
	 * @return  an updated Property1Entity object, or null if a property
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Property1Entity updateProperty1Entity(BigInteger id, Property1Entity toUpdate) throws Exception;
	
	/**
	 * Update a property in the DB using the given relation information
	 * @param id the integer id of the property to update
	 * @param toUpdate the Property1Entity1Threat object with the information about the property to be updated
	 * @return  an updated Property1Entity1Threat object, or null if a property
	 *  with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	Property1Entity1Threat updateProperty1Entity1Threat(BigInteger id, Property1Entity1Threat toUpdate) throws Exception;
	
	/**
	 * Update or create the derivedProperty in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	void computeResults() throws Exception;	
	

	/**
	 * Delete all the derivedProperty in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	void deleteAllDerivedProperty() throws Exception;
	
	//GET of derived Properties
	
	/**
	 * Perform a query on the DB to get the derived property with 1 entity and 1 threat divided by property name
	 * @param page the number of the results page to get
	 * @param name the name of the derived property to retrieve
	 * @param entitySelf the self of the entity to which the derived properties is linked
	 * @param threatSelf the self of the threat to which the derived properties is linked
	 * @return a DerivedProperty1Entity1ThreatPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	DerivedProperty1Entity1ThreatPage getDerivedProperties1Entity1Threat(BigInteger page, DerivedProperty1Entity1ThreatName name, String entitySelf, String threatSelf) throws Exception;
	
	/**
	 * Perform a query on the DB to get the derived property with 1 entity divided by property name
	 * @param page the number of the results page to get
	 * @param name the name of the derived property to retrieve
	 * @param entitySelf the self of the entity to which the derived properties is linked
	 * @return a DerivedProperty1EntityPage object that contains the search results (one page)
	 * @throws Exception if the operation cannot be completed
	 */
	DerivedProperty1EntityPage getDerivedProperties1Entity(BigInteger page, DerivedProperty1EntityName name, String entitySelf) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the derived property with a given id
	 * @param id the integer id of the derived property
	 * @param the name of the derived property
	 * @return a DerivedProperty1Entity1Threat object describing the derived property with the given id, 
	 * 	or null if a property with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	DerivedProperty1Entity1Threat getDerivedProperty1Entity1Threat(BigInteger id, DerivedProperty1Entity1ThreatName name) throws Exception;
	
	/**
	 * Perform a query on the DB to obtain the information related to the derived property with a given id
	 * @param id the integer id of the derived property
	 * @param the name of the derived property
	 * @return a DerivedProperty1Entity object describing the derived property with the given id, 
	 * 	or null if a property with the given id is not available in the DB
	 * @throws Exception if the operation cannot be completed
	 */
	DerivedProperty1Entity getDerivedProperty1Entity(BigInteger id, DerivedProperty1EntityName name) throws Exception;
	
}


