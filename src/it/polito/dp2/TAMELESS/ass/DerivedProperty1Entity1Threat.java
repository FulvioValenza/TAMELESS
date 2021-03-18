package it.polito.dp2.TAMELESS.ass;

import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import it.polito.dp2.TAMELESS.sol.client.BasicProperty1Entity1ThreatName;


/**
 * An interface for operating on a DerivedProperty1Entity1Threat through a remote service
 *
 */
public interface DerivedProperty1Entity1Threat extends DerivedProperty {

	
	/**
	 * Gets the threat that is currently concerning the property (this operation is done on the remote service)
	 * @return a interface for reading the information of the threat
	 * @throws DestroyedException if the property represented by this interface does no longer exist
	 * @throws ServiceException if the operation could not be completed because of a problem with the remote service
	 */
	public Threat getThreat() throws DestroyedException, ServiceException;

	
}
