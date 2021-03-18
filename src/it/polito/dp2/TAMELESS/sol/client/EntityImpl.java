package it.polito.dp2.TAMELESS.sol.client;

import java.math.BigInteger;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.TAMELESS.ass.DestroyedException;
import it.polito.dp2.TAMELESS.ass.Entity;
import it.polito.dp2.TAMELESS.ass.InUseException;
import it.polito.dp2.TAMELESS.ass.NotAcceptableNameException;
import it.polito.dp2.TAMELESS.ass.PropertyReader;
import it.polito.dp2.TAMELESS.ass.RelationshipReader;
import it.polito.dp2.TAMELESS.ass.ServiceException;

public class EntityImpl implements Entity{

	private String self;
	private javax.ws.rs.client.Client client;
	
	public EntityImpl(javax.ws.rs.client.Client _client, String _self) {
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
	public void destroyEntity() throws DestroyedException, ServiceException, InUseException {

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
	public EntityNature getType() throws DestroyedException, ServiceException {
		return getMySelfFromServer().getType();
	}

	@Override
	public String getSelf(){
		return self;
	}
	
	private it.polito.dp2.TAMELESS.sol.client.Entity getMySelfFromServer() throws ServiceException, DestroyedException{
		
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
		
		
		return res.readEntity(it.polito.dp2.TAMELESS.sol.client.Entity.class);
	}
	
	public Boolean updateEntity(String entityName, EntityNature entityType) throws ServiceException,BadRequestException,DestroyedException{
		
		it.polito.dp2.TAMELESS.sol.client.ObjectFactory of = new ObjectFactory();
		
		it.polito.dp2.TAMELESS.sol.client.Entity toUpdate = of.createEntity();
		toUpdate.setName(entityName);
		toUpdate.setType(entityType);
		
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
			throw new DestroyedException();
		}
		
		it.polito.dp2.TAMELESS.sol.client.Entity updated = res.readEntity(it.polito.dp2.TAMELESS.sol.client.Entity.class);
		
		
		//return true if name and type returned, so the updated one on the server are equals to the ones sent
		//return false otherwise
		return (updated.getName().equals(entityName) && updated.getType().equals(entityType)) ; 
		
		
		//EntityImpl impl = new EntityImpl(client, updated.getSelf());
		//return impl;
		//return updated;
	}

}
