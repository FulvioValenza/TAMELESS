package it.polito.dp2.TAMELESS.ass;

import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import it.polito.dp2.TAMELESS.sol.client.EntityNature;


/**
 * An interface for operating on a threat through a remote service
 *
 */
public interface Threat {
	/**
	 * Gets the name of the threat (this is a local operation, the name is retrieved locally)
	 * @return the name of the threat
	 * @throws DestroyedException if the threat represented by this interface does no longer exist
	 */
	public String getName() throws ServiceException, DestroyedException;
	
	/**
	 * Gets the relations that are currently concerning the threat (this operation is done on the remote service)
	 * @return a set of interfaces for reading the information of the relationships
	 * @throws DestroyedException if the threat represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Set<RelationshipReader> getRelationships() throws DestroyedException, ServiceException;
	
	
	/**
	 * Gets the properties that are currently concerning the threat (this operation is done on the remote service)
	 * @return a set of interfaces for reading the information of the properties
	 * @throws DestroyedException if the threat represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Set<PropertyReader> getProperties() throws DestroyedException, ServiceException;
	
	
	/**
	 * Destroys the entity (this operation is done on the remote service)
	 * @throws DestroyedException if the threat represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 * @throws InUseException if the threat is used for a relationship or a property
	 */
	public void destroyThreat() throws DestroyedException, ServiceException, InUseException;
	
	/**
	 * Give the self of the threat(this operation is done locally)
	 * @return the self of the threat
	 */
	public String getSelf();
	
	
	/**
	 * Update the threat(this operation is done on the remote service)
	 * @param the threat name
	 * @return true if threat correctly updated, false otherwise
	 * @throws DestroyedException if the threat represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 * @throws BadRequestException if the request sent to the server contain mistake in the structure
	 */
	public Boolean updateThreat(String threatName) throws ServiceException,BadRequestException,NotFoundException;
	
}
