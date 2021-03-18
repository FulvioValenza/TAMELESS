package it.polito.dp2.TAMELESS.sol.client;

import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.TAMELESS.ass.DestroyedException;
import it.polito.dp2.TAMELESS.ass.Entity;
import it.polito.dp2.TAMELESS.ass.EntityReader;
import it.polito.dp2.TAMELESS.ass.InUseException;
import it.polito.dp2.TAMELESS.ass.PropertyReader;
import it.polito.dp2.TAMELESS.ass.Property1Entity;
import it.polito.dp2.TAMELESS.ass.RelationshipReader;
import it.polito.dp2.TAMELESS.ass.ServiceException;
import it.polito.dp2.TAMELESS.ass.Threat;
import it.polito.dp2.TAMELESS.ass.ThreatReader;
import it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity1Threat;

public class DerivedProperty1Entity1ThreatImpl implements DerivedProperty1Entity1Threat{

	private String self;
	private javax.ws.rs.client.Client client;
	
	public DerivedProperty1Entity1ThreatImpl(javax.ws.rs.client.Client _client, String _self) {
		this.self = _self;
		this.client = _client;
	}
	
	@Override
	public String getName() throws DestroyedException, ServiceException {
		return getMySelfFromServer().getPropertyName().toString();
	}

	@Override
	public Entity getEntity() throws DestroyedException, ServiceException {
		
		Entity ret = new EntityImpl(client, getMySelfFromServer().getEntityId());
		
		return ret;
		
	}
	
	@Override
	public Threat getThreat() throws DestroyedException, ServiceException {

		Threat ret = new ThreatImpl(client, getMySelfFromServer().getThreatId());
		
		return ret;
		
	}
	
	@Override
	public String getSelf(){
		return self;
	}
	
	
	private it.polito.dp2.TAMELESS.sol.client.DerivedProperty1Entity1Threat getMySelfFromServer() throws ServiceException, DestroyedException{
		
		Response res = client.target(self)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		return res.readEntity(it.polito.dp2.TAMELESS.sol.client.DerivedProperty1Entity1Threat.class);
	}
	
	@Override
	public String getDescription() throws DestroyedException, ServiceException {
		String ret = getMySelfFromServer().getDescription();
		
		if(ret == null)
			return "";
		else 
			return ret;
	}

}
