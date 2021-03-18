package it.polito.dp2.TAMELESS.sol.client;

import java.math.BigInteger;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.TAMELESS.ass.DestroyedException;
import it.polito.dp2.TAMELESS.ass.Threat;
import it.polito.dp2.TAMELESS.ass.InUseException;
import it.polito.dp2.TAMELESS.ass.PropertyReader;
import it.polito.dp2.TAMELESS.ass.RelationshipReader;
import it.polito.dp2.TAMELESS.ass.ServiceException;

public class ThreatImpl implements Threat{

	private String self;
	private javax.ws.rs.client.Client client;
	
	public ThreatImpl(javax.ws.rs.client.Client _client, String _self) {
		this.self = _self;
		this.client = _client;
	}
	
	@Override
	public String getName() throws ServiceException, DestroyedException {
		return getMySelfFromServer().getName();
	}

	@Override
	public Set<RelationshipReader> getRelationships() throws DestroyedException, ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PropertyReader> getProperties() throws DestroyedException, ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyThreat() throws DestroyedException, ServiceException, InUseException {

		Response res = client.target(self)
				.request()
				.delete();
		
		if(res.getStatus()==500)
			throw new ServiceException();
		else if(res.getStatus()==404)
			throw new DestroyedException();
		else if(res.getStatus()==409)
			throw new InUseException();
				
	}
	
	@Override
	public String getSelf() {
		return self;
	}
	
	private it.polito.dp2.TAMELESS.sol.client.Threat getMySelfFromServer() throws ServiceException, DestroyedException{
		
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
		
		
		
		return res.readEntity(it.polito.dp2.TAMELESS.sol.client.Threat.class);
	}
	
	public Boolean updateThreat(String threatName) throws ServiceException,BadRequestException,NotFoundException{
		
		it.polito.dp2.TAMELESS.sol.client.ObjectFactory of = new ObjectFactory();
		
		it.polito.dp2.TAMELESS.sol.client.Threat toUpdate = of.createThreat();
		toUpdate.setName(threatName);
		
		Response res = client.target(self)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.put(javax.ws.rs.client.Entity.xml(toUpdate));
		
		if(res.getStatus()==500 || res.getStatus() == 409){
			throw new ServiceException();
		}
		if(res.getStatus()==400){
			throw new BadRequestException();
		}
		if(res.getStatus()==404){
			throw new NotFoundException();
		}

		it.polito.dp2.TAMELESS.sol.client.Threat updated = res.readEntity(it.polito.dp2.TAMELESS.sol.client.Threat.class);
		
		return updated.getName().equals(threatName);
	}

}
