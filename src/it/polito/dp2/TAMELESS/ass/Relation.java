package it.polito.dp2.TAMELESS.ass;

import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import it.polito.dp2.TAMELESS.sol.client.EntityNature;
import it.polito.dp2.TAMELESS.sol.client.Relation2EntitiesName;


/**
 * An interface for operating on a Relation through a remote service
 *
 */
public interface Relation{
	/**
	 * Gets the name of the relation (this is a local operation, the name is retrieved locally)
	 * @return the name of the relation
	 * @throws DestroyedException if the relation represented by this interface does no longer exist	
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public String getName() throws ServiceException, DestroyedException;
	
	/**
	 * Gets the entity 1 that is currently concerning the relation (this operation is done on the remote service)
	 * @return a interface for reading the information of the entity
	 * @throws DestroyedException if the relation represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Entity getEntity1() throws DestroyedException, ServiceException;
	
	/**
	 * Destroys the relation (this operation is done on the remote service)
	 * @throws DestroyedException if the relation represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public void destroyRelation() throws DestroyedException, ServiceException;
	
	/**
	 * Give the self of the relation(this operation is done locally)
	 * @return the self of the relation
	 */
	public String getSelf(); 
	
}
