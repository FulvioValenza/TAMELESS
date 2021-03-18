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
import it.polito.dp2.TAMELESS.ass.Property1Entity1Threat;
import it.polito.dp2.TAMELESS.ass.RelationshipReader;
import it.polito.dp2.TAMELESS.ass.ServiceException;
import it.polito.dp2.TAMELESS.ass.ThreatReader;
import it.polito.dp2.TAMELESS.ass.Threat;

public class Property1Entity1ThreatImpl implements Property1Entity1Threat{

	private String self;
	private javax.ws.rs.client.Client client;
	
	public Property1Entity1ThreatImpl(javax.ws.rs.client.Client _client, String _self) {
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
	public void destroyProperty() throws DestroyedException, ServiceException {

		Response res = client.target(self)
				.request()
				.delete();
		
		if(res.getStatus()==500)
			throw new ServiceException();
		else if(res.getStatus()==404)
			throw new DestroyedException();

				
	}
	
	@Override
	public String getSelf(){
		return self;
	}
	
	
	private it.polito.dp2.TAMELESS.sol.client.Property1Entity1Threat getMySelfFromServer() throws ServiceException, DestroyedException{
		
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
		
		
		return res.readEntity(it.polito.dp2.TAMELESS.sol.client.Property1Entity1Threat.class);
	}
	
	public Boolean updateProperty(BasicProperty1Entity1ThreatName propertyName, Entity entity, Threat threat) throws ServiceException,BadRequestException,NotFoundException{
		
		it.polito.dp2.TAMELESS.sol.client.ObjectFactory of = new ObjectFactory();
		
		it.polito.dp2.TAMELESS.sol.client.Property1Entity1Threat toUpdate = of.createProperty1Entity1Threat();
		toUpdate.setPropertyName(propertyName);
		toUpdate.setEntityId(entity.getSelf());
		toUpdate.setThreatId(threat.getSelf());
		
		Response res = client.target(self)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.put(javax.ws.rs.client.Entity.xml(toUpdate));
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		if(res.getStatus()==400){
			throw new BadRequestException();
		}
		if(res.getStatus()==404){
			throw new NotFoundException();
		}

		it.polito.dp2.TAMELESS.sol.client.Property1Entity1Threat updated = res.readEntity(it.polito.dp2.TAMELESS.sol.client.Property1Entity1Threat.class);
		
		return (updated.getPropertyName().equals(propertyName) && updated.getEntityId().equals(entity.getSelf()) && 
				updated.getThreatId().equals(threat.getSelf())) ; 		
	}

}
