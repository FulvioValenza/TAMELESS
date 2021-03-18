package it.polito.dp2.TAMELESS.ass;

import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import it.polito.dp2.TAMELESS.sol.client.EntityNature;
import it.polito.dp2.TAMELESS.sol.client.Relation2EntitiesName;


/**
 * An interface for operating on a Relation2Entities through a remote service
 *
 */
public interface Relation2Entities extends Relation {

	
	/**
	 * Gets the entity 2 that is currently concerning the relation (this operation is done on the remote service)
	 * @return a interface for reading the information of the entity
	 * @throws DestroyedException if the relation represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Entity getEntity2() throws DestroyedException, ServiceException;
	
	/**
	 * Update the relation(this operation is done on the remote service)
	 * @param the relation name
	 * @param the entity 1
	 * @param the entity 2
	 * @return true if relation correctly updated, false otherwise
	 * @throws DestroyedException if the relation represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 * @throws BadRequestException if the request sent to the server contain mistake in the structure
	 */
	public Boolean updateRelation(Relation2EntitiesName relationName, Entity entity1,Entity entity2) throws ServiceException,BadRequestException,NotFoundException;
	

	
}
