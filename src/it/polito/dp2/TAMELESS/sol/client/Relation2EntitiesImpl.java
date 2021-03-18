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
import it.polito.dp2.TAMELESS.ass.Relation2Entities;
import it.polito.dp2.TAMELESS.ass.RelationshipReader;
import it.polito.dp2.TAMELESS.ass.ServiceException;

public class Relation2EntitiesImpl implements Relation2Entities{

	private String self;
	private javax.ws.rs.client.Client client;
	
	public Relation2EntitiesImpl(javax.ws.rs.client.Client _client, String _self) {
		this.self = _self;
		this.client = _client;
	}
	
	@Override
	public String getName() throws DestroyedException, ServiceException {
		return getMySelfFromServer().getRelationName().toString();
	}

	@Override
	public Entity getEntity1() throws DestroyedException, ServiceException {
		
		Entity ret = new EntityImpl(client, getMySelfFromServer().getEntity1Id());
		
		return ret;
	}

	@Override
	public Entity getEntity2() throws DestroyedException, ServiceException {
		
		Entity ret = new EntityImpl(client, getMySelfFromServer().getEntity2Id());
		
		return ret;
	}

	@Override
	public void destroyRelation() throws DestroyedException, ServiceException {

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
	
	
	private it.polito.dp2.TAMELESS.sol.client.Relation2Entities getMySelfFromServer() throws ServiceException, DestroyedException{
		
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
		
		
		return res.readEntity(it.polito.dp2.TAMELESS.sol.client.Relation2Entities.class);
	}
	
	public Boolean updateRelation(Relation2EntitiesName relationName, Entity entity1,Entity entity2) throws ServiceException,BadRequestException,NotFoundException{
		
		it.polito.dp2.TAMELESS.sol.client.ObjectFactory of = new ObjectFactory();
		
		it.polito.dp2.TAMELESS.sol.client.Relation2Entities toUpdate = of.createRelation2Entities();
		toUpdate.setRelationName(relationName);
		toUpdate.setEntity1Id(entity1.getSelf());
		toUpdate.setEntity2Id(entity2.getSelf());
		
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

		it.polito.dp2.TAMELESS.sol.client.Relation2Entities updated = res.readEntity(it.polito.dp2.TAMELESS.sol.client.Relation2Entities.class);
		return (updated.getRelationName().equals(relationName) && updated.getEntity1Id().equals(entity1.getSelf()) && updated.getEntity2Id().equals(entity2.getSelf()));
	}

}
