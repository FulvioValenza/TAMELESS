package it.polito.dp2.TAMELESS.sol.resources;

import it.polito.dp2.TAMELESS.sol.model.EProperties;
import it.polito.dp2.TAMELESS.sol.model.ERelationships;
import it.polito.dp2.TAMELESS.sol.model.EResults;
import it.polito.dp2.TAMELESS.sol.model.ESystem;
import it.polito.dp2.TAMELESS.sol.service.ConflictServiceException;
import it.polito.dp2.TAMELESS.sol.service.SystemService;
import it.polito.dp2.TAMELESS.sol.service.jaxb.*;
import it.polito.dp2.TAMELESS.sol.service.jaxb.System;

/*
import it.polito.dp2.TAMELESS.sol.model.EBiblio;
import it.polito.dp2.TAMELESS.sol.service.BadRequestServiceException;
import it.polito.dp2.TAMELESS.sol.service.BiblioService;
import it.polito.dp2.TAMELESS.sol.service.ConflictServiceException;
import it.polito.dp2.TAMELESS.sol.service.SearchScope;
*/
import java.math.BigInteger;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/system")
@Api(value = "/system") // serve per swagger
public class SystemResource {
	
	public UriInfo uriInfo;
	
	private SystemService service;
	//private	static final Logger log = Logger.getLogger(SystemResource.class.getName());
	
	public SystemResource(@Context UriInfo uriInfo){	
		this.uriInfo = uriInfo;
		this.service = new SystemService(uriInfo);
	}
	
	@GET
	@ApiOperation(value = "getSystem", notes = "read main resource")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=System.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public ESystem getSystem() {
		// .getAbsolutePathBuilder allow to get the absolute path of the uriInfo in form of UriBuilder
		return new ESystem(uriInfo.getAbsolutePathBuilder());
	}
	
	@POST
	@Path("/entities")
    @ApiOperation(value = "createEntities", notes = "create a new Entity", response=Entity.class
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Entity.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 409, message = "Conflict (entity name already used or not available)")
    		})
	//mando entity da inserire
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	//produco entity inserita
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createEntity(Entity e) {
		try {
			
			Entity returnEntity = service.createEntity(e);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			return Response.created(new URI(returnEntity.getSelf())).entity(returnEntity).build();
			//return e.getType().toString();
		} catch (ConflictServiceException cse) {
			throw new ClientErrorException(409);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException(e1);
		}
	}
	
	@GET
	@Path("/entities")
    @ApiOperation(value = "getEntities", notes = "search entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Entities.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Entities getEntities(
			@ApiParam("The name to be used for the search") @QueryParam("name") @DefaultValue("") String name,
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page
			) {
		try {
			return service.getEntities(name, BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@POST
	@Path("/threats")
    @ApiOperation(value = "createThreats", notes = "create a new Threat", response=Threat.class
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 409, message = "Conflict (threat name already used or not available)")
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createThreat(Threat t) {
		try {
			Threat returnThreat = service.createThreat(t);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			
			return Response.created(new URI(returnThreat.getSelf())).entity(returnThreat).build();
			//return e.getType().toString();
		} catch (ConflictServiceException cse) {
			throw new ClientErrorException(409);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException();
		}
	}
	
	@GET
	@Path("/threats")
    @ApiOperation(value = "getThreats", notes = "search threats"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Threats.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Threats getThreats(
			@ApiParam("The name to be used for the search") @QueryParam("name") @DefaultValue("") String name,
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page
			) {
		try {
			return service.getThreats(name, BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@POST
	@Path("/relationships/relations2Entities")
    @ApiOperation(value = "createRelation2Entities", notes = "create a new relations between two entities", response=Relation2Entities.class
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Relation2Entities.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createRelation2Entities(Relation2Entities r2e) {
		try {
			Relation2Entities returnRelation2Entities = service.createRelation2Entities(r2e);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			
			return Response.created(new URI(returnRelation2Entities.getSelf())).entity(returnRelation2Entities).build();
			//return e.getType().toString();
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Path("/relationships/relations2Entities")
    @ApiOperation(value = "getRelation2Entities", notes = "get relations with 2 entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relations2Entities.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relations2Entities getRelation2Entities(
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page
			) {
		try {
			// I do not allow here to find relation given an entity or threat id because it is allowed by entity and threat resource 
			return service.getRelations2Entities(BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@POST
	@Path("/relationships/relations3Entities")
    @ApiOperation(value = "createRelation3Entities", notes = "create a new relations between three entities", response=Relation3Entities.class
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Relation3Entities.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createRelation3Entities(Relation3Entities r3e) {
		try {
			Relation3Entities returnRelation3Entities = service.createRelation3Entities(r3e);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			
			return Response.created(new URI(returnRelation3Entities.getSelf())).entity(returnRelation3Entities).build();
			//return e.getType().toString();
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Path("/relationships/relations3Entities")
    @ApiOperation(value = "getRelation3Entities", notes = "get relations with 3 entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relations3Entities.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relations3Entities getRelation3Entities(
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page
			) {
		try {
			//I do not allow here to find relation given an entity or threat id because it is allowed by entity and threat resource 
			return service.getRelations3Entities(BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@POST
	@Path("/relationships/relations2Entities1Threat")
    @ApiOperation(value = "createRelation2Entities1Threat", notes = "create a new relations between two entities and 1 threat", response=Relation2Entities1Threat.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Relation2Entities1Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createRelation2Entities1Threat(Relation2Entities1Threat r2e1t) {
		try {
			Relation2Entities1Threat returnRelation2Entities1Threat = service.createRelation2Entities1Threat(r2e1t);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			
			return Response.created(new URI(returnRelation2Entities1Threat.getSelf())).entity(returnRelation2Entities1Threat).build();
			//return e.getType().toString();
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Path("/relationships/relations2Entities1Threat")
    @ApiOperation(value = "getRelation2Entities1Threat", notes = "get relations with 2 entities and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relations2Entities1Threat.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relations2Entities1Threat getRelation2Entities1Threat(
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try {
			//I do not allow here to find relation given an entity or threat id because it is allowed by entity and threat resource 
			return service.getRelations2Entities1Threat(BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@POST
	@Path("/relationships/relations1Entity1Threat")
    @ApiOperation(value = "createRelation1Entity1Threat", notes = "create a new relations between 1 entity and 1 threat", response=Relation1Entity1Threat.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Relation1Entity1Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createRelation1Entity1Threat(Relation1Entity1Threat r1e1t) {
		try {
			Relation1Entity1Threat returnRelation1Entity1Threat = service.createRelation1Entity1Threat(r1e1t);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			
			return Response.created(new URI(returnRelation1Entity1Threat.getSelf())).entity(returnRelation1Entity1Threat).build();
			//return e.getType().toString();
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Path("/relationships/relations1Entity1Threat")
    @ApiOperation(value = "getRelation1Entity1Threat", notes = "get relations with 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relations1Entity1Threat.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relations1Entity1Threat getRelation1Entity1Threat(
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try {
			//I do not allow here to find relation given an entity or threat id because it is allowed by entity and threat resource 
			return service.getRelations1Entity1Threat(BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	
	@POST
	@Path("/properties/properties1Entity")
    @ApiOperation(value = "createProperty1Entity", notes = "create a new property associated to 1 entity", response=Property1Entity.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Property1Entity.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createProperty1Entity(Property1Entity p1e) {
		try {
			Property1Entity returnProperty1Entity = service.createProperty1Entity(p1e);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			
			return Response.created(new URI(returnProperty1Entity.getSelf())).entity(returnProperty1Entity).build();
			//return e.getType().toString();
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Path("/properties/properties1Entity")
    @ApiOperation(value = "getProperty1Entity", notes = "get properties associated to 1 entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Properties1Entity.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Properties1Entity getProperties1Entity(
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try {
			//I do not allow here to find relation given an entity or threat id because it is allowed by entity and threat resource 
			return service.getProperties1Entity(BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@POST
	@Path("/properties/properties1Entity1Threat")
    @ApiOperation(value = "createProperty1Entity1Threat", notes = "create a new property associated to 1 entity and 1 threat", response=Property1Entity1Threat.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "OK", response=Property1Entity1Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createProperty1Entity1Threat(Property1Entity1Threat p1e1t) {
		try {
			Property1Entity1Threat returnProperty1Entity1Threat = service.createProperty1Entity1Threat(p1e1t);

			/*
			 * Response.created(URI uri) create a response builder that set in the location header of the response uri.
			 * The location header allows to set the url of the resource to which the response is linked
			 * .entity(elem) set elem as entity of the response (cioè nel body)
			 * .build() costruisce l'oggetto response a partire dai parametri settatin nel builder-
			 */
			
			return Response.created(new URI(returnProperty1Entity1Threat.getSelf())).entity(returnProperty1Entity1Threat).build();
			//return e.getType().toString();
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e1) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Path("/properties/properties1Entity1Threat")
    @ApiOperation(value = "getProperty1Entity1Threat", notes = "get properties associated to 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Properties1Entity1Threat.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Properties1Entity1Threat getProperties1Entity1Threat(
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try {
			//I do not allow here to find relation given an entity or threat id because it is allowed by entity and threat resource 
			return service.getProperties1Entity1Threat(BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	//GET BY ID SECTION
	
	@GET
	@Path("/entities/{id}")
    @ApiOperation(value = "getEntity", notes = "read a single Entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Entity.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Entity getEntity(
			@ApiParam("The id of the Entity") @PathParam("id") BigInteger id) {
		Entity elemToRetrieve;
		try {
			elemToRetrieve = service.getEntity(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/threats/{id}")
    @ApiOperation(value = "getThreat", notes = "read a single Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Threat.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Threat getThreat(
			@ApiParam("The id of the Threat") @PathParam("id") BigInteger id) {
		Threat elemToRetrieve;
		try {
			elemToRetrieve = service.getThreat(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/relationships/relations2Entities/{id}")
    @ApiOperation(value = "getRelation2Entities", notes = "read a single Relation2Entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation2Entities.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation2Entities getRelation2Entities(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		Relation2Entities elemToRetrieve;
		try {
			elemToRetrieve = service.getRelation2Entities(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/relationships/relations3Entities/{id}")
    @ApiOperation(value = "getRelation3Entities", notes = "read a single Relation3Entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation3Entities.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation3Entities getRelation3Entities(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		Relation3Entities elemToRetrieve;
		try {
			elemToRetrieve = service.getRelation3Entities(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/relationships/relations2Entities1Threat/{id}")
    @ApiOperation(value = "getRelation2Entities1Threat", notes = "read a single Relation2Entities1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation2Entities1Threat.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation2Entities1Threat getRelation2Entities1Threat(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		Relation2Entities1Threat elemToRetrieve;
		try {
			elemToRetrieve = service.getRelation2Entities1Threat(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/relationships/relations1Entity1Threat/{id}")
    @ApiOperation(value = "getRelation1Entity1Threat", notes = "read a single Relation1Entity1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation1Entity1Threat.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation1Entity1Threat getRelation1Entity1Threat(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		Relation1Entity1Threat elemToRetrieve;
		try {
			elemToRetrieve = service.getRelation1Entity1Threat(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/properties/properties1Entity/{id}")
    @ApiOperation(value = "getProperty1Entity", notes = "read a single Property1Entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Property1Entity.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Property1Entity getProperty1Entity(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		Property1Entity elemToRetrieve;
		try {
			elemToRetrieve = service.getProperty1Entity(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/properties/properties1Entity1Threat/{id}")
    @ApiOperation(value = "getProperty1Entity1Threat", notes = "read a single Property1Entity1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Property1Entity1Threat.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Property1Entity1Threat getProperty1Entity1Threat(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		Property1Entity1Threat elemToRetrieve;
		try {
			elemToRetrieve = service.getProperty1Entity1Threat(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	//DELETE section
	
	@DELETE
	@Path("/entities/{id}")
    @ApiOperation(value = "deleteEntity", notes = "delete a single entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 409, message = "Conflict (entity is in relationship or has property)"),
    		})
	public void deleteEntity(
			@ApiParam("The id of the Entity") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteEntity(id);
		} catch (ConflictServiceException e) {
			throw new ClientErrorException(409);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	@DELETE
	@Path("/threats/{id}")
    @ApiOperation(value = "deleteThreat", notes = "delete a single threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 409, message = "Conflict (threat is in relationship or has property)"),
    		})
	public void deleteThreat(
			@ApiParam("The id of the Threat") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteThreat(id);
		} catch (ConflictServiceException e) {
			throw new ClientErrorException(409);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	@DELETE
	@Path("/relationships/relations2Entities/{id}")
    @ApiOperation(value = "deleteRelation2Entities", notes = "delete a single Relation2Entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found")
    		})
	public void deleteRelation2Entities(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteRelation2Entities(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	@DELETE
	@Path("/relationships/relations3Entities/{id}")
    @ApiOperation(value = "deleteRelation3Entities", notes = "delete a single Relation3Entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found")
    		})
	public void deleteRelation3Entities(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteRelation3Entities(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	@DELETE
	@Path("/relationships/relations2Entities1Threat/{id}")
    @ApiOperation(value = "deleteRelation2Entities1Threat", notes = "delete a single Relation2Entities1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found")
    		})
	public void deleteRelation2Entities1Threat(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteRelation2Entities1Threat(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	@DELETE
	@Path("/relationships/relations1Entity1Threat/{id}")
    @ApiOperation(value = "deleteRelation1Entity1Threat", notes = "delete a single Relation1Entity1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found")
    		})
	public void deleteRelation1Entity1Threat(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteRelation1Entity1Threat(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	@DELETE
	@Path("/properties/properties1Entity/{id}")
    @ApiOperation(value = "deleteProperty1Entity", notes = "delete a single Property1Entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found")
    		})
	public void deleteProperty1Entity(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteProperty1Entity(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	@DELETE
	@Path("/properties/properties1Entity1Threat/{id}")
    @ApiOperation(value = "deleteProperty1Entity1Threat", notes = "delete a single Property1Entity1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content"),
    		@ApiResponse(code = 404, message = "Not Found")
    		})
	public void deleteProperty1Entity1Threat(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		BigInteger ret;
		try {
			ret = service.deleteProperty1Entity1Threat(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (ret==null)
			throw new NotFoundException();
	}
	
	//PUT section
	
	@PUT
	@Path("/entities/{id}")
    @ApiOperation(value = "updateEntity", notes = "update a single entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Entity.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 409, message = "Conflict (entity name already used or not available)")
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Entity updateEntity(
			@ApiParam("The id of the entity") @PathParam("id") BigInteger id,
			Entity toUpdate) {
		
		Entity updated;
		try {
			updated = service.updateEntity(id, toUpdate);
		} catch (ConflictServiceException cse) {
			throw new ClientErrorException(409);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	@PUT
	@Path("/threats/{id}")
    @ApiOperation(value = "updateThreat", notes = "update a single Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 409, message = "Conflict (threat name already used or not available)")
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Threat updateThreat(
			@ApiParam("The id of the Threat") @PathParam("id") BigInteger id,
			Threat toUpdate) {
		
		Threat updated;
		try {
			updated = service.updateThreat(id, toUpdate);
		} catch (ConflictServiceException cse) {
			throw new ClientErrorException(409);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	
	@PUT
	@Path("/relationships/relations2Entities/{id}")
    @ApiOperation(value = "updateRelation2Entities", notes = "update a single Relation2Entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation2Entities.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation2Entities updateRelation2Entities(
			@ApiParam("The id of the relation") @PathParam("id") BigInteger id,
			Relation2Entities toUpdate) {
		
		Relation2Entities updated;
		try {
			updated = service.updateRelation2Entities(id, toUpdate);
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	@PUT
	@Path("/relationships/relations3Entities/{id}")
    @ApiOperation(value = "updateRelation3Entities", notes = "update a single Relation3Entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation3Entities.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation3Entities updateRelation3Entities(
			@ApiParam("The id of the relation") @PathParam("id") BigInteger id,
			Relation3Entities toUpdate) {
		
		Relation3Entities updated;
		try {
			updated = service.updateRelation3Entities(id, toUpdate);
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	@PUT
	@Path("/relationships/relations2Entities1Threat/{id}")
    @ApiOperation(value = "updateRelation2Entities1Threat", notes = "update a single Relation2Entities1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation2Entities1Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation2Entities1Threat updateRelation2Entities1Threat(
			@ApiParam("The id of the relation") @PathParam("id") BigInteger id,
			Relation2Entities1Threat toUpdate) {
		
		Relation2Entities1Threat updated;
		try {
			updated = service.updateRelation2Entities1Threat(id, toUpdate);
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	@PUT
	@Path("/relationships/relations1Entity1Threat/{id}")
    @ApiOperation(value = "updateRelation1Entity1Threat", notes = "update a single Relation1Entity1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation1Entity1Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation1Entity1Threat updateRelation1Entity1Threat(
			@ApiParam("The id of the relation") @PathParam("id") BigInteger id,
			Relation1Entity1Threat toUpdate) {
		
		Relation1Entity1Threat updated;
		try {
			updated = service.updateRelation1Entity1Threat(id, toUpdate);
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	@PUT
	@Path("/properties/properties1Entity/{id}")
    @ApiOperation(value = "updateProperty1Entity", notes = "update a single Property1Entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Property1Entity.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Property1Entity updateProperty1Entity(
			@ApiParam("The id of the property") @PathParam("id") BigInteger id,
			Property1Entity toUpdate) {
		
		Property1Entity updated;
		try {
			updated = service.updateProperty1Entity(id, toUpdate);
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	@PUT
	@Path("/properties/properties1Entity1Threat/{id}")
    @ApiOperation(value = "updateProperty1Entity1Threat", notes = "update a single Property1Entity1Threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Property1Entity1Threat.class),
    		@ApiResponse(code = 400, message = "Bad Request"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Property1Entity1Threat updateProperty1Entity1Threat(
			@ApiParam("The id of the property") @PathParam("id") BigInteger id,
			Property1Entity1Threat toUpdate) {
		
		Property1Entity1Threat updated;
		try {
			updated = service.updateProperty1Entity1Threat(id, toUpdate);
		} catch(BadRequestException bre){
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (updated==null)
			throw new NotFoundException();
		return updated;
	}
	
	@PUT
	@Path("/results")
    @ApiOperation(value = "putResults", notes = "obtain results"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content")
    		//@ApiResponse(code = 200, message = "OK",response=String.class)
    		})
	public void computeResults() {
		try {
			service.computeResults();
			//return "ciao";
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	

	@GET
	@Path("/javaRuntimePath")
    @ApiOperation(value = "javaRuntimePath", notes = "obtain java runtime library path"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK",response=String.class)
    		})
	public String getJavaRuntimeLibraryPath() {
		try {
			String path = java.lang.System.getProperty("java.library.path"); 
			return path;
			//return "ciao";
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@DELETE
	@Path("/results")
    @ApiOperation(value = "deleteAllDerivedProperty", notes = "delete all the derived property"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 204, message = "No content")
    		})
	public void deleteAllDerivedProperty() {
		try {
			service.deleteAllDerivedProperty();
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
	}
	
	//GET of derived Properties
	
	@GET
	@Path("/results/canbeCompromised")
    @ApiOperation(value = "getDerivedCompromisedProperties", notes = "get derived properties associated to 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperties1Entity1Threat.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperties1Entity1Threat getDerivedCompromisedProperties(
			@ApiParam("The self of the entity to be used for the search") @QueryParam("entitySelf") @DefaultValue("") String entitySelf,
			@ApiParam("The self of the threat to be used for the search") @QueryParam("threatSelf") @DefaultValue("") String threatSelf,
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try { 
			//Logger l = Logger.getLogger("aaa");
			//l.severe(entitySelf);
			return service.getDerivedProperties1Entity1Threat(BigInteger.valueOf(page), DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED, 
					entitySelf, threatSelf);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	
	@GET
	@Path("/results/canbeMalfunctioning")
    @ApiOperation(value = "getDerivedMalfunctioningProperties", notes = "get derived properties associated to 1 entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperties1Entity.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperties1Entity getDerivedMalfunctioningProperties(
			@ApiParam("The self of the entity to be used for the search") @QueryParam("entitySelf") @DefaultValue("") String entitySelf,
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try { 
		
			
			return service.getDerivedProperties1Entity(BigInteger.valueOf(page), DerivedProperty1EntityName.CANBE_MALFUNCTIONING,
					entitySelf);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@GET
	@Path("/results/canbeVulnerable")
    @ApiOperation(value = "getDerivedVulnerableProperties", notes = "get derived properties associated to 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperties1Entity1Threat.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperties1Entity1Threat getDerivedVulnerableProperties(
		@ApiParam("The self of the entity to be used for the search") @QueryParam("entitySelf") @DefaultValue("") String entitySelf,
		@ApiParam("The self of the threat to be used for the search") @QueryParam("threatSelf") @DefaultValue("") String threatSelf,
		@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try { 
			//Logger l = Logger.getLogger("aaa");
			//l.severe(entitySelf);
			return service.getDerivedProperties1Entity1Threat(BigInteger.valueOf(page), DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE, 
					entitySelf, threatSelf);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@GET
	@Path("/results/canbeDetected")
    @ApiOperation(value = "getDerivedDetectedProperties", notes = "get derived properties associated to 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperties1Entity1Threat.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperties1Entity1Threat getDerivedDetectedProperties(
		@ApiParam("The self of the entity to be used for the search") @QueryParam("entitySelf") @DefaultValue("") String entitySelf,
		@ApiParam("The self of the threat to be used for the search") @QueryParam("threatSelf") @DefaultValue("") String threatSelf,
		@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try { 
			//Logger l = Logger.getLogger("aaa");
			//l.severe(entitySelf);
			return service.getDerivedProperties1Entity1Threat(BigInteger.valueOf(page), DerivedProperty1Entity1ThreatName.CANBE_DETECTED, 
					entitySelf, threatSelf);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
		
	}
	
	@GET
	@Path("/results/canbeRestored")
    @ApiOperation(value = "getDerivedRestoredProperties", notes = "get derived properties associated to 1 entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperties1Entity.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperties1Entity getDerivedRestoredProperties(		
		@ApiParam("The self of the entity to be used for the search") @QueryParam("entitySelf") @DefaultValue("") String entitySelf,
		@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try { 
		
			
			return service.getDerivedProperties1Entity(BigInteger.valueOf(page), DerivedProperty1EntityName.CANBE_RESTORED,
					entitySelf);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@GET
	@Path("/results/canbeFixed")
    @ApiOperation(value = "getDerivedFixedProperties", notes = "get derived properties associated to 1 entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperties1Entity.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperties1Entity getDerivedFixedProperties(
		@ApiParam("The self of the entity to be used for the search") @QueryParam("entitySelf") @DefaultValue("") String entitySelf,
		@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page) {
		try { 
		
			
			return service.getDerivedProperties1Entity(BigInteger.valueOf(page), DerivedProperty1EntityName.CANBE_FIXED,
					entitySelf);
		} catch (BadRequestException bre) {
			throw bre;
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
		
	}
	
	
	
	@GET
	@Path("/results")
	@ApiOperation(value = "getResults", notes = "read results resource")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Results.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public EResults getResults() {
		// .getAbsolutePathBuilder allow to get the absolute path of the uriInfo in form of UriBuilder
		/*Logger l = Logger.getLogger(SystemResource.class.getName());
		l.log(Level.INFO, uriInfo.getAbsolutePathBuilder().toTemplate());*/
		//NB poichè viene creata una nuova istanza di SystemResource per ogni richiesta,
		//uriInfo in questo caso è già associato a system/results
		return new EResults(uriInfo.getAbsolutePathBuilder());
	}
	
	@GET
	@Path("/relationships")
	@ApiOperation(value = "getRelationships", notes = "read relationships resource")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relationships.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public ERelationships getRelationships() {

		return new ERelationships(uriInfo.getAbsolutePathBuilder());
	}
	
	@GET
	@Path("/properties")
	@ApiOperation(value = "getProperties", notes = "read properties resource")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Properties.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public EProperties getProperties() {

		return new EProperties(uriInfo.getAbsolutePathBuilder());
	}
	
	//DERIVED PROPERTIES GET BY ID

	
	@GET
	@Path("/results/canbeCompromised/{id}")
    @ApiOperation(value = "getDerivedCompromisedProperty", notes = "get a single derived property associated to 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperty1Entity1Threat.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperty1Entity1Threat getDerivedCompromisedProperty(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		DerivedProperty1Entity1Threat elemToRetrieve;
		try {
			elemToRetrieve = service.getDerivedProperty1Entity1Threat(id,DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	
	@GET
	@Path("/results/canbeMalfunctioning/{id}")
    @ApiOperation(value = "getDerivedMalfunctioningProperty", notes = "get a single derived property associated to 1 entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperty1Entity.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperty1Entity getDerivedMalfunctioningProperty(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		DerivedProperty1Entity elemToRetrieve;
		try {
			elemToRetrieve = service.getDerivedProperty1Entity(id,DerivedProperty1EntityName.CANBE_MALFUNCTIONING);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	
	@GET
	@Path("/results/canbeVulnerable/{id}")
    @ApiOperation(value = "getDerivedVulnerableProperty", notes = "get a single derived property associated to 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperty1Entity1Threat.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperty1Entity1Threat getDerivedVulnerableProperty(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		DerivedProperty1Entity1Threat elemToRetrieve;
		try {
			elemToRetrieve = service.getDerivedProperty1Entity1Threat(id,DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}

	@GET
	@Path("/results/canbeDetected/{id}")
    @ApiOperation(value = "getDerivedDetectedProperty", notes = "get a single derived property associated to 1 entity and 1 threat"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperty1Entity1Threat.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperty1Entity1Threat getDerivedDetectedProperty(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		DerivedProperty1Entity1Threat elemToRetrieve;
		try {
			elemToRetrieve = service.getDerivedProperty1Entity1Threat(id,DerivedProperty1Entity1ThreatName.CANBE_DETECTED);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/results/canbeRestored/{id}")
    @ApiOperation(value = "getDerivedRestoredProperty", notes = "get a single derived property associated to 1 entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperty1Entity.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperty1Entity getDerivedRestoredProperty(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		DerivedProperty1Entity elemToRetrieve;
		try {
			elemToRetrieve = service.getDerivedProperty1Entity(id,DerivedProperty1EntityName.CANBE_RESTORED);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	@GET
	@Path("/results/canbeFixed/{id}")
    @ApiOperation(value = "getDerivedFixedProperty", notes = "get a single derived property associated to 1 entity"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=DerivedProperty1Entity.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DerivedProperty1Entity getDerivedFixedProperty(
			@ApiParam("The id of the Property") @PathParam("id") BigInteger id) {
		DerivedProperty1Entity elemToRetrieve;
		try {
			elemToRetrieve = service.getDerivedProperty1Entity(id,DerivedProperty1EntityName.CANBE_FIXED);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}
	
	
	//GET RELATION GIVEN ENTITY ID
	
	/*
	@GET
	@Path("/relationships/relations2Entities")
    @ApiOperation(value = "getRelation2Entities", notes = "get relations with 2 entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relations2Entities.class),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relations2Entities getRelation2Entities(
			@ApiParam("The page of results to be read") @QueryParam("page") @DefaultValue("1") int page
			) {
		try { 
			// I do not allow here to find relation given an entity or threat id because it is allowed by entity and threat resource 
			return service.getRelations2Entities(BigInteger.valueOf(page));
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@GET
	@Path("/relationships/relations2Entities/{id}")
    @ApiOperation(value = "getRelation2Entities", notes = "read a single Relation2Entities"
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response=Relation2Entities.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Relation2Entities getRelation2Entities(
			@ApiParam("The id of the Relation") @PathParam("id") BigInteger id) {
		Relation2Entities elemToRetrieve;
		try {
			elemToRetrieve = service.getRelation2Entities(id);
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		if (elemToRetrieve==null)
			throw new NotFoundException();
		return elemToRetrieve;
	}*/
}


