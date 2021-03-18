package it.polito.dp2.TAMELESS.ass;

import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import it.polito.dp2.TAMELESS.sol.client.BasicProperty1EntityName;
import it.polito.dp2.TAMELESS.sol.client.EntityNature;


/**
 * An interface for operating on a Property1Entity  through a remote service
 *
 */
public interface Property1Entity extends Property {

	/**
	 * Update the property(this operation is done on the remote service)
	 * @param the property name
	 * @param the entity 1
	 * @return true if property correctly updated, false otherwise
	 * @throws DestroyedException if the property represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 * @throws BadRequestException if the request sent to the server contain mistake in the structure
	 */
	public Boolean updateProperty(BasicProperty1EntityName propertyName, Entity entity) throws ServiceException,BadRequestException,NotFoundException;
	
}
