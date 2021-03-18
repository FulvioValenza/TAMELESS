package it.polito.dp2.TAMELESS.sol.db;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.TAMELESS.sol.neo4j.jaxb.*;
import it.polito.dp2.TAMELESS.sol.service.jaxb.EntityNature;

//import it.polito.dp2.BIB.sol3.service.SearchScope;

public class MyNeo4jClient {
	Client client;
	WebTarget target;
	String uri = "http://localhost:7474/db";
	private ObjectFactory of = new ObjectFactory();

	public MyNeo4jClient() {
		client = ClientBuilder.newClient();		
		target = client.target(uri).path("data");
		
		createConstraints();
	}
	
	//these operation should be done in constructor but since it is better not to
	//throw exception during construction, I use a separated method
	private void createConstraints(){
		//Logger l = Logger.getLogger(MyNeo4jClient.class.getName());
		//l.log(Level.SEVERE,"inizio create constraints");
		it.polito.dp2.TAMELESS.sol.neo4j.jaxb.ObjectFactory of = new it.polito.dp2.TAMELESS.sol.neo4j.jaxb.ObjectFactory();
		UniquenessBody ub = of.createUniquenessBody();
		ub.getPropertyKeys().add("name");
		
		//l.log(Level.SEVERE,"dopo creazione uniqueness body");
		
		Response res = target.path("schema").path("constraint")
				.path(TypeOfData.ENTITY.name()).path("uniqueness")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.json(ub));
		
		//l.log(Level.SEVERE,"dopo constraints ENTITY");
		
		//status = 409 if label already exists!
		/*if(res.getStatus() != 200 && res.getStatus() != 409)
			//throw new Neo4jClientException();*/
		
		res = target.path("schema").path("constraint")
				.path(TypeOfData.THREAT.name()).path("uniqueness")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.json(ub));
		
		//l.log(Level.SEVERE,"dopo constraints THREAT");
		/*if(res.getStatus() != 200 && res.getStatus() != 409)
			throw new Neo4jClientException();*/
	}
	
	
	public void close() {
		client.close();
	}
	
	public NodeType createNode(String name, String entityNature, TypeOfData tod) throws Neo4jClientException, ConflictInOperationException {
		
		//Logger l = Logger.getLogger(MyNeo4jClient.class.getName());
		//l.log(Level.SEVERE,"Inizio createNode");
		
		StringBuffer s = new StringBuffer("CREATE(" + name +":"+tod.toString()+"{name:")
				.append('"').append(name).append('"');
		if(entityNature != null && tod.equals(TypeOfData.ENTITY))
			s.append(",type:").append('"').append(entityNature).append('"');
		s.append("}) return " + name);
		
		//l.log(Level.SEVERE,"Dopo la creazione stringa per creare il nodo : " + s.toString());
		
		ObjectFactory of = new ObjectFactory();
		CypherRequest request = of.createCypherRequest(); 		//request contains a list of statements
		Statements stat = of.createStatements(); 	//stat contains the statement(cioè l'istruzione da eseguire)
																//e ResultDataContents:"REST" che permette di ottenere la risposta a questa
																//cypher request nel formato Rest
		stat.setStatement(s.toString());
		stat.getResultDataContents().add("REST");
		request.getStatements().add(stat);
		
		//l.log(Level.SEVERE,"Dopo la creazione CypherRequest e statements");
		
		try {
			CypherResponse response = target.path("transaction").path("commit")		//response contiene una lista di results(viene creato un elemento results
																					//per ogni statement che mando quindi in questo caso ho solo 1 result!) e una
																					//lista di errori
				  .request(MediaType.APPLICATION_JSON_TYPE)
				  .post(Entity.json(request), CypherResponse.class);

			//List<NodeType> nodes = new ArrayList<NodeType>();
			
			//l.log(Level.SEVERE,"Dopo esecuzione transazione");
			
			
			//N.B. se violo la constraints del nome univoco per entity e threats
			//lo status della risposta è sempre 200 OK ma in getErrors ho l'errore
			if (response.getErrors().size()!=0 || response.getResults()==null){
				//Logger l = Logger.getLogger("aa");
				//l.severe("Here1");
				if(response.getErrors().size()==1){
					//l.severe(response.getErrors().get(0).getCode());
					if(response.getErrors().get(0).getCode().contains("Neo.ClientError.Schema.ConstraintValidationFailed")){
						//l.severe("Here3");
						throw new ConflictInOperationException();
					}
				}
				throw new Neo4jClientException("Error in node creation");
			}
			
			//l.log(Level.SEVERE,"Dopo controllo errori in risposta");
			
			Results results = response.getResults().get(0); //devo avere 1 solo results perchè ho 1 solo statement
			//l.log(Level.SEVERE,"Dopo recupero results dalla response");
			
			if (results!=null){
				//l.log(Level.SEVERE,"Results diverso da null");
				List<NodeType> nodes = new ArrayList<NodeType>();
				for (Results.Data r : results.getData()) {
					//l.log(Level.SEVERE,"Dentro al ciclo for");
					nodes.addAll(r.getRest());
				}
				//NodeType node = results.getData().get(0).getRest().get(0);
				//l.log(Level.SEVERE,"Nodes ottenuto - Size : " + nodes.size());
				return nodes.get(0);
			}
			else
				throw new WebApplicationException();
		} catch (WebApplicationException|ProcessingException|IndexOutOfBoundsException e) {
			throw new Neo4jClientException(e);
		}
	}

	public List<NodeType> searchNode(String name, TypeOfData tod) throws Neo4jClientException {
		
		//Logger l = Logger.getLogger(MyNeo4jClient.class.getName());
		
		StringBuffer s = new StringBuffer("MATCH(n:" + tod.toString() +") WHERE ");
		s.append("n.name CONTAINS \"").append(name).append('"').append(' ');
		s.append("RETURN n");
		
		//l.log(Level.SEVERE, "Dopo creazione query " + s.toString());
		
		CypherRequest request = new CypherRequest();
		Statements stat = new Statements();
		stat.setStatement(s.toString());
		stat.getResultDataContents().add("REST");
		request.getStatements().add(stat);
		
		//l.log(Level.SEVERE, "Dopo creazione request");
		
		try {
			CypherResponse response = target.path("transaction").path("commit")
				  .request(MediaType.APPLICATION_JSON_TYPE)
				  .post(Entity.json(request), CypherResponse.class);
			
			//l.log(Level.SEVERE, "Dopo response");
			
			List<NodeType> nodes = new ArrayList<NodeType>();
			if (response.getErrors().size()!=0 || response.getResults()==null)
				throw new Neo4jClientException("Error in search");
			
			//l.log(Level.SEVERE, "Dopo controllo errore");
			
			Results results = response.getResults().get(0);
			if (results!=null)
			for (Results.Data r:results.getData()) {
				//l.log(Level.SEVERE, "Dentro al ciclo for");
				nodes.addAll(r.getRest());
			}
			return nodes;
		} catch (WebApplicationException|ProcessingException e) {
			throw new Neo4jClientException(e);
		}
	}

	public NodeType getNode(BigInteger id) throws Neo4jClientException {
		try {
			NodeType node = target.path("node").path(id.toString())
				  .request(MediaType.APPLICATION_JSON_TYPE)
				  .get(NodeType.class);
			return node;
		} catch (NotFoundException e1) {
			return null;
		} catch (WebApplicationException|ProcessingException e2) {
			throw new Neo4jClientException(e2);
		}
	}
	
	public Data updateNode(BigInteger id, String name, String entityNature, TypeOfData tod) throws Neo4jClientException, ConflictInOperationException {
		try {
			
			ObjectFactory of = new ObjectFactory();
			Data data = of.createData();
			
			data.setName(name);
			if(entityNature != null && tod.equals(TypeOfData.ENTITY))
				data.setType(entityNature);
			
			Response response = target.path("node").path(id.toString()).path("properties")
				  .request(MediaType.APPLICATION_JSON_TYPE)
				  .put(Entity.json(data));
			int status = response.getStatus();
			if (status==204)
				return data;
			else if (status==404)
				return null;
			else if (status==409)
				throw new ConflictInOperationException();
			else
				throw new Neo4jClientException("Neo4j put not successful: "+response.getStatusInfo());
		} catch (ProcessingException e) {
			throw new Neo4jClientException(e);
		}	
	}
	

	public BigInteger deleteNode(BigInteger id) throws Neo4jClientException, ConflictInOperationException {
			Response response = target.path("node").path(id.toString())
				  .request()
				  .delete();
			int status = response.getStatus();
			if (status==204)
				return id;
			else if (status==404)
				return null;
			else if (status==409) //case in which I am trying to remove node that are part of relationships(neo4j meaning)
				throw new ConflictInOperationException();
			else
				throw new Neo4jClientException("Neo4j get not successful: "+response.getStatusInfo());
	}

	public RelationshipType createRelationship(BigInteger id, BigInteger tid, TypeOfRelationship tor) throws Neo4jClientException, BadRequestInOperationException {
		WebTarget t1 = target.path("node").path(id.toString());
		WebTarget t2 = target.path("node").path(tid.toString());
		ObjectFactory of = new ObjectFactory();
		RelationshipType relationship = of.createRelationshipType();
		relationship.setTo(t2.getUri().toString());
		relationship.setType(tor.toString());
		try {
			t1
			  .path("/relationships")
		 	  .request(MediaType.APPLICATION_JSON_TYPE)
		 	  .post(Entity.json(relationship));
			return relationship;
		} catch (NotFoundException e1) {
			return null;
		} catch (BadRequestException e2) {
			throw new BadRequestInOperationException();
		} catch (WebApplicationException|ProcessingException|IllegalArgumentException e) {
			throw new Neo4jClientException(e);
		}
	}
	
	public RelationshipResponseType createRelationshipReturnResponse(BigInteger id, BigInteger tid, TypeOfRelationship tor) throws Neo4jClientException, BadRequestInOperationException {
		WebTarget t1 = target.path("node").path(id.toString());
		WebTarget t2 = target.path("node").path(tid.toString());
		ObjectFactory of = new ObjectFactory();
		RelationshipType relationship = of.createRelationshipType();
		relationship.setTo(t2.getUri().toString());
		relationship.setType(tor.toString());
		try {
			RelationshipResponseType ret = t1
			  .path("/relationships")
		 	  .request(MediaType.APPLICATION_JSON_TYPE)
		 	  .post(Entity.json(relationship), RelationshipResponseType.class);
			return ret;
		} catch (NotFoundException e1) {
			return null;
		} catch (BadRequestException e2) {
			throw new BadRequestInOperationException();
		} catch (WebApplicationException|ProcessingException|IllegalArgumentException e) {
			throw new Neo4jClientException(e);
		}
	}

	public boolean deleteRelationship(BigInteger id, BigInteger tid) throws Neo4jClientException {
		URI uri = getRelationship(id,tid);
		if (uri==null)
			return false;
		else {
			Response response = client.target(uri).request().delete();
			int status = response.getStatus();
			if (status==204)
				return true;
			else if (status==404)
				return false;
			else
				throw new Neo4jClientException("Neo4j delete not successful: "+response.getStatusInfo());
		}
	}
	
	public boolean deleteRelationshipGivenURI(URI uri) throws Neo4jClientException {
		
		if (uri==null)
			return false;
		else {
			Response response = client.target(uri).request().delete();
			int status = response.getStatus();
			if (status==204)
				return true;
			else if (status==404)
				return false;
			else
				throw new Neo4jClientException("Neo4j delete not successful: "+response.getStatusInfo());
		}
	}

	public URI getRelationship(BigInteger id, BigInteger tid) throws Neo4jClientException {
		String s = "MATCH(n)-[r]->(m) WHERE id(n)="+id.toString()+" AND id(m)="+tid.toString()+" RETURN r";
		
		CypherRequest request = new CypherRequest();
		Statements stat = new Statements();
		stat.setStatement(s.toString());
		stat.getResultDataContents().add("REST");
		request.getStatements().add(stat);
		
		try {
			CypherResponse response = target.path("transaction").path("commit")
				  .request(MediaType.APPLICATION_JSON_TYPE)
				  .post(Entity.json(request), CypherResponse.class);
			if (response.getErrors().size()!=0 || response.getResults()==null)
				throw new Neo4jClientException("Error in search");
			Results results = response.getResults().get(0);
			if (results.getData().size()==0)
				return null;
			Results.Data data = results.getData().get(0);
			URI uri = new URI(data.getRest().get(0).getSelf());
			return uri;
		} catch (WebApplicationException|ProcessingException|URISyntaxException e) {
			throw new Neo4jClientException(e);
		}
		
	}
	
	//where node is the node of neo4j associated to the concept of relationships and properties of TAMELESS
	public BigInteger deleteNodeAndRelationships(BigInteger id) throws Neo4jClientException {
		
		NodeType ret = this.getNode(id); //relationships not found - 404
		if(ret == null)
			return null;
		
		String s = "MATCH (n) WHERE ID(n) = " + id.toString() + " DETACH DELETE n" ;
		
		CypherRequest request = new CypherRequest();
		Statements stat = new Statements();
		stat.setStatement(s.toString());
		stat.getResultDataContents().add("REST");
		request.getStatements().add(stat);
		
		
		try {
			CypherResponse response = target.path("transaction").path("commit")
				  .request(MediaType.APPLICATION_JSON_TYPE)
				  .post(Entity.json(request), CypherResponse.class);
			if (response.getErrors().size()!=0 || response.getResults()==null)
				throw new Neo4jClientException("Error in search");
			Results results = response.getResults().get(0);
			if (results.getData().size()==0)
				return id;
			else
				throw new Neo4jClientException();
		} catch (WebApplicationException|ProcessingException e) {
			throw new Neo4jClientException(e);
		}
		
		/*Response response = target.path("node").path(id.toString())
				  .request()
				  .delete();
			int status = response.getStatus();
			if (status==204)
				return id;
			else if (status==404)
				return null;
			else if (status==409) //case in which I am trying to remove node that are part of relationships(neo4j meaning)
				throw new ConflictInOperationException();
			else
				throw new Neo4jClientException("Neo4j get not successful: "+response.getStatusInfo());
		
		*/
	}
	
	public String getEndNodeOfRelationshipsGivenType(BigInteger srcId,TypeOfRelationship tor) throws Neo4jClientException {
		try {
			//Logger l = Logger.getLogger(MyNeo4jClient.class.getName());
			//l.log(Level.SEVERE,"prima relationshipREsponse");
			List<RelationshipResponseType> rr = target.path("node").path(srcId.toString()).path("relationships").path("out").path(tor.toString())
										  .request(MediaType.APPLICATION_JSON_TYPE)
										  .get(new GenericType<List<RelationshipResponseType>>() {});
			//l.log(Level.SEVERE,"dopo relationshipREsponse");
			
			String end = rr.get(0).getEnd();
			
			String retIdString = end.substring(end.lastIndexOf("/") + 1);
			//BigInteger retId = BigInteger.valueOf(Integer.parseInt(IdString));
			
			return retIdString;
		} catch (NotFoundException e1) {
			return null;
		} catch (WebApplicationException|ProcessingException e2) {
			throw new Neo4jClientException(e2);
		}
	}
	
	public List<NodeType> searchNodeGiven2RelationshipsEndName(String name, TypeOfData tod, String entityName, String threatName) throws Neo4jClientException {
		
		//Logger l = Logger.getLogger(MyNeo4jClient.class.getName());
		
		StringBuffer s = new StringBuffer("MATCH");
		if(threatName != null)
			s.append("(t:THREAT{name:\"" + threatName + "\"})<-[:THREAT]-");
		s.append("(n:" + tod.toString() + " {name:\"" + name + "\"})");
		if(entityName != null)
			s.append("-[:ENTITY]->(e:ENTITY{name:\"" + entityName + "\"})");		
		
		s.append(' ');
		s.append("RETURN n");
		
		//l.log(Level.SEVERE, "Dopo creazione query " + s.toString());
		
		CypherRequest request = new CypherRequest();
		Statements stat = new Statements();
		stat.setStatement(s.toString());
		stat.getResultDataContents().add("REST");
		request.getStatements().add(stat);
		
		//l.log(Level.SEVERE, "Dopo creazione request");
		
		try {
			CypherResponse response = target.path("transaction").path("commit")
				  .request(MediaType.APPLICATION_JSON_TYPE)
				  .post(Entity.json(request), CypherResponse.class);
			
			//l.log(Level.SEVERE, "Dopo response");
			
			List<NodeType> nodes = new ArrayList<NodeType>();
			if (response.getErrors().size()!=0 || response.getResults()==null)
				throw new Neo4jClientException("Error in search");
			
			//l.log(Level.SEVERE, "Dopo controllo errore");
			
			Results results = response.getResults().get(0);
			if (results!=null)
			for (Results.Data r:results.getData()) {
				//l.log(Level.SEVERE, "Dentro al ciclo for");
				nodes.addAll(r.getRest());
			}
			return nodes;
		} catch (WebApplicationException|ProcessingException e) {
			throw new Neo4jClientException(e);
		}
	}

}
