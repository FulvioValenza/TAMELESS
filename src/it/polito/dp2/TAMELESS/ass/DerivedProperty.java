package it.polito.dp2.TAMELESS.ass;

import java.util.Set;

import it.polito.dp2.TAMELESS.sol.client.EntityNature;


/**
 * An interface for operating on a Property through a remote service
 *
 */
public interface DerivedProperty {
	/**
	 * Gets the name of the property (this operation is done on the remote service)
	 * @return the name of the property
	 * @throws DestroyedException if the property represented by this interface does no longer exist	
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public String getName() throws ServiceException, DestroyedException;
	
	/**
	 * Gets the entity 1 that is currently concerning the property (this operation is done on the remote service)
	 * @return a interface for reading the information of the property
	 * @throws DestroyedException if the property represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Entity getEntity() throws DestroyedException, ServiceException;
	
	/**
	 * Give the self of the property(this operation is done locally)
	 * @return the self of the property
	 */
	public String getSelf(); 
	
	/**
	 * Gets the description of the property (this operation is done on the remote service)
	 * @return the description of the property
	 * @throws DestroyedException if the property represented by this interface does no longer exist	
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public String getDescription() throws ServiceException, DestroyedException;
	
}
