package it.polito.dp2.TAMELESS.ass;

import java.util.Comparator;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import it.polito.dp2.TAMELESS.sol.client.EntityNature;


/**
 * An interface for operating on a entity through a remote service
 *
 */
public interface Entity {
	/**
	 * Gets the name of the entity (this is a remote operation)
	 * @return the name of the entity
	 * @throws DestroyedException if the entity represented by this interface does no longer exist
	 * 	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public String getName() throws ServiceException, DestroyedException;
	
	/**
	 * Gets the relations that are currently concerning the entity (this operation is done on the remote service)
	 * @return a set of interfaces for reading the information of the relationships
	 * @throws DestroyedException if the entity represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Set<RelationshipReader> getRelationships() throws DestroyedException, ServiceException;
	
	
	/**
	 * Gets the properties that are currently concerning the entity (this operation is done on the remote service)
	 * @return a set of interfaces for reading the information of the properties
	 * @throws DestroyedException if the entity represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Set<PropertyReader> getProperties() throws DestroyedException, ServiceException;
	
	
	/**
	 * Destroys the entity (this operation is done on the remote service)
	 * @throws DestroyedException if the entity represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 * @throws InUseException if the entity is used for a relationship or a property
	 */
	public void destroyEntity() throws DestroyedException, ServiceException, InUseException;
	
	/**
	 * Give the type of the entity(this operation is done on the remote service)
	 * @return the type of the entity
	 * @throws DestroyedException if the entity represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public EntityNature getType() throws DestroyedException, ServiceException;
	
	/**
	 * Give the self of the entity(this operation is done locally)
	 * @return the self of the entity
	 */
	public String getSelf(); 	//non lancia eccezioni perchè è eseguito in locale e se uso self
								//per accedere all'elemento che è distrutto sarà il getter a lanciare l'eccezione
	
	/**
	 * Update the entity(this operation is done on the remote service)
	 * @param the entity name
	 * @param the entity type
	 * @return true if entity correctly updated, false otherwise
	 * @throws DestroyedException if the entity represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 * @throws BadRequestException if the request sent to the server contain mistake in the structure
	 */
	public Boolean updateEntity(String entityName, EntityNature entityType) 
			throws ServiceException,BadRequestException,DestroyedException;
}
