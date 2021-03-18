package it.polito.dp2.TAMELESS.sol.tests;

import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

/*
import it.polito.dp2.BIB.*;
import it.polito.dp2.BIB.ass3.Bookshelf;
//import it.polito.dp2.BIB.ass3.Client;
//import it.polito.dp2.BIB.ass3.ClientFactory;
import it.polito.dp2.BIB.ass3.DestroyedBookshelfException;
import it.polito.dp2.BIB.ass3.ItemReader;
//import it.polito.dp2.BIB.ass3.ServiceException;
import it.polito.dp2.BIB.ass3.TooManyItemsException;
import it.polito.dp2.BIB.ass3.UnknownItemException;
import it.polito.dp2.BIB.ass3.admClient.AdminClient;
import it.polito.dp2.BIB.ass3.admClient.BookType;
import it.polito.dp2.BIB.ass3.admClient.Items.Item;
*/

import it.polito.dp2.TAMELESS.ass.Client;
import it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity;
import it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity1Threat;
import it.polito.dp2.TAMELESS.ass.DestroyedException;
import it.polito.dp2.TAMELESS.ass.Entity;
import it.polito.dp2.TAMELESS.ass.InUseException;
import it.polito.dp2.TAMELESS.ass.Property1Entity;
import it.polito.dp2.TAMELESS.ass.Property1Entity1Threat;
import it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat;
import it.polito.dp2.TAMELESS.ass.ServiceException;
import it.polito.dp2.TAMELESS.ass.Threat;
import it.polito.dp2.TAMELESS.ass.NotAcceptableNameException;
import it.polito.dp2.TAMELESS.ass.Relation2Entities;
import it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat;
import it.polito.dp2.TAMELESS.ass.Relation3Entities;
import it.polito.dp2.TAMELESS.sol.client.BasicProperty1Entity1ThreatName;
import it.polito.dp2.TAMELESS.sol.client.BasicProperty1EntityName;
import it.polito.dp2.TAMELESS.sol.client.ClientFactory;
import it.polito.dp2.TAMELESS.sol.client.DerivedProperty1EntityName;
import it.polito.dp2.TAMELESS.sol.client.EntityNature;
import it.polito.dp2.TAMELESS.sol.client.Relation1Entity1ThreatName;
import it.polito.dp2.TAMELESS.sol.client.Relation2Entities1ThreatName;
import it.polito.dp2.TAMELESS.sol.client.Relation2EntitiesName;
import it.polito.dp2.TAMELESS.sol.client.Relation3EntitiesName;
import it.polito.dp2.TAMELESS.sol.client.DerivedProperty1Entity1ThreatName;
//It is from BIB but I need it
import it.polito.dp2.BIB.FactoryConfigurationError;

public class TamTests {
	
	private static Client testClient;	                // Client under test
	
	// prepare the environment
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create reference data generator
		//System.setProperty("it.polito.dp2.BIB.BibReaderFactory", "it.polito.dp2.BIB.Random.BibReaderFactoryImpl");
		//referenceBibReader = BibReaderFactory.newInstance().newBibReader();
		
		try {
			testClient = ClientFactory.newInstance().newClient();
			//adminClient = new AdminClient();
		} catch (FactoryConfigurationError fce) {
			fce.printStackTrace();
		}
		//assertNotNull("Internal tester error during test setup: null reference", referenceBibReader);
		//assertNotNull("Internal tester error during test setup: null reference", adminClient);
		assertNotNull("Could not run test: the implementation under test generated a null Client", testClient);
	}

	@Before
	public void setUp() throws Exception {

	}
	
	//NB Anche se le varie test create sono tutte praticamente identiche, NON faccio una funzione
	//helpCreate() la quale esegue il corpo della funzione e tramite degli switch decide 
	//se gestire Entity, Threat, etc... . Le motivazioni sono principalmente 2: per prima
	//cosa, se usassi gli switch in sostanza non dovrei ricopiare solamente le varie
	//assert perchè comunque le operazioni di create e destroy sarebbero diverse
	//Come seconda cosa, creando i vari elementi dentro i vari case degli switch, dovrei
	//trovare il modo per avere questi oggetti anche al di fuori degli switch o utilizzando
	//tutti object(perderei tutti i controlli aggiuntivi fatti sui singoli tipi) o creando 
	//a inizio funzione tutti i possibili tipi(aggiungendo così molto codice)
	//Per ottimizzare nel miglior modo possibile creerò una funzione solo per fare
	//gli assert!
	
	private void checkReturnedSetSizeAndNull(Set set, int correctSize){
		assertNotNull("The client under test returned a null set", set);
		assertEquals("Wrong number of entities ",correctSize,set.size());
	}

	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that entities are created and destroyed correctly and that getEntities returns consistent sets
	public final void testCreateEntities() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreateEntities");
		Entity first = testClient.createEntity("first", EntityNature.HUMAN);
		Entity second = testClient.createEntity("second", EntityNature.HUMAN);
		Entity toRemove = testClient.createEntity("toRemove", EntityNature.HUMAN);

		Set<? extends Entity> set = testClient.getEntities(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		set = testClient.getEntities("fi");
		checkReturnedSetSizeAndNull(set, 1);
		
		toRemove.destroyEntity();
		set = testClient.getEntities("");
		checkReturnedSetSizeAndNull(set, 2);
		
		first.destroyEntity();
		second.destroyEntity();
		
		set = testClient.getEntities("");
		checkReturnedSetSizeAndNull(set, 0);
		
	}
	
	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that threats are created and destroyed correctly and that getThreats returns consistent sets
	public final void testCreateThreats() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreateThreats");
		Threat first = testClient.createThreat("first");
		Threat second = testClient.createThreat("second");
		Threat toRemove = testClient.createThreat("toRemove");

		Set<? extends Threat> set = testClient.getThreats(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		set = testClient.getThreats("fi"); 
		checkReturnedSetSizeAndNull(set, 1);
		
		toRemove.destroyThreat();
		set = testClient.getThreats("");
		checkReturnedSetSizeAndNull(set, 2);
		
		first.destroyThreat();
		second.destroyThreat();
		
		set = testClient.getThreats("");
		checkReturnedSetSizeAndNull(set, 0);
		
	}
	
	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that Relation2Entities are created and destroyed correctly and that getRelations2Entities returns consistent sets
	public final void testCreateRelation2Entities() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreateRelation2Entities");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Relation2Entities first = testClient.createRelation2Entities(Relation2EntitiesName.CONTAIN,e1,e2);
		Relation2Entities second = testClient.createRelation2Entities(Relation2EntitiesName.CHECK,e2,e1);
		Relation2Entities toRemove = testClient.createRelation2Entities(Relation2EntitiesName.DEPEND,e3,e1);

		Set<? extends Relation2Entities> set = testClient.getRelations2Entities(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		toRemove.destroyRelation();
		set = testClient.getRelations2Entities("");		
		checkReturnedSetSizeAndNull(set, 2);

		
		first.destroyRelation();
		second.destroyRelation();
		
		set = testClient.getRelations2Entities("");
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
	}
	
	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that Relation3Entities are created and destroyed correctly and that getRelations3Entities returns consistent sets
	public final void testCreateRelation3Entities() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreateRelation3Entities");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Relation3Entities first = testClient.createRelation3Entities(Relation3EntitiesName.CONNECT,e1,e2,e3);
		Relation3Entities second = testClient.createRelation3Entities(Relation3EntitiesName.CONNECT,e2,e1,e3);
		Relation3Entities toRemove = testClient.createRelation3Entities(Relation3EntitiesName.CONNECT,e3,e1,e2);

		Set<? extends Relation3Entities> set = testClient.getRelations3Entities(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		toRemove.destroyRelation();
		set = testClient.getRelations3Entities("");		
		checkReturnedSetSizeAndNull(set, 2);

		
		first.destroyRelation();
		second.destroyRelation();
		
		set = testClient.getRelations3Entities("");
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
	}
	
	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that Relation2Entities1Threat are created and destroyed correctly and that getRelations2Entities1Threat returns consistent sets
	public final void testCreateRelation2Entities1Threat() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreateRelation2Entities1Threat");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Threat t1 = testClient.createThreat("t1");
		Threat t2 = testClient.createThreat("t2");
		
		Relation2Entities1Threat first = testClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.MONITOR,e1,e2,t1);
		Relation2Entities1Threat second = testClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT,e2,e3,t2);
		Relation2Entities1Threat toRemove = testClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.MEND,e3,e1,t1);

		Set<? extends Relation2Entities1Threat> set = testClient.getRelations2Entities1Threat(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		toRemove.destroyRelation();
		set = testClient.getRelations2Entities1Threat("");		
		checkReturnedSetSizeAndNull(set, 2);

		
		first.destroyRelation();
		second.destroyRelation();
		
		set = testClient.getRelations2Entities1Threat("");
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
		
		t1.destroyThreat();
		t2.destroyThreat();
	}
	
	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that Relation1Entity1Threat are created and destroyed correctly and that getRelations1Entity1Threat returns consistent sets
	public final void testCreateRelation1Entity1Threat() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreateRelation1Entity1Threat");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Threat t1 = testClient.createThreat("t1");
		Threat t2 = testClient.createThreat("t2");
		
		Relation1Entity1Threat first = testClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.POTENTIALLY_VULNERABLE,e1,t1);
		Relation1Entity1Threat second = testClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD,e2,t2);
		Relation1Entity1Threat toRemove = testClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD,e3,t1);

		Set<? extends Relation1Entity1Threat> set = testClient.getRelations1Entity1Threat(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		toRemove.destroyRelation();
		set = testClient.getRelations1Entity1Threat("");		
		checkReturnedSetSizeAndNull(set, 2);

		
		first.destroyRelation();
		second.destroyRelation();
		
		set = testClient.getRelations1Entity1Threat("");
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
		
		t1.destroyThreat();
		t2.destroyThreat();
	}
	
	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that Property1Entity are created and destroyed correctly and that getProperties1Entity returns consistent sets
	public final void testCreateProperty1Entity() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreatePropertyEntity");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Property1Entity first = testClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED,e1);
		Property1Entity second = testClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED,e2);
		Property1Entity toRemove = testClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED,e3);

		Set<? extends Property1Entity> set = testClient.getProperties1Entity(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		toRemove.destroyProperty();
		set = testClient.getProperties1Entity("");		
		checkReturnedSetSizeAndNull(set, 2);

		
		first.destroyProperty();
		second.destroyProperty();
		
		set = testClient.getProperties1Entity("");
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();

	}
	
	
	//TEST controllato con wireshark
	//TEST of POST, GET, DELETE
	@Test
	// Check that Property1Entity1Threat are created and destroyed correctly and that getProperties1Entity1Threat returns consistent sets
	public final void testCreateProperty1Entity1Threat() throws ServiceException, DestroyedException, InUseException  {
		System.out.println("DEBUG: starting testCreatePropertyEntity1Threat");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Threat t1 = testClient.createThreat("t1");
		Threat t2 = testClient.createThreat("t2");
		
		Property1Entity1Threat first = testClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED,e1,t1);
		Property1Entity1Threat second = testClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE,e2,t2);
		Property1Entity1Threat toRemove = testClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE,e3,t1);

		Set<? extends Property1Entity1Threat> set = testClient.getProperties1Entity1Threat(""); 
		checkReturnedSetSizeAndNull(set, 3);
		
		toRemove.destroyProperty();
		set = testClient.getProperties1Entity1Threat("");		
		checkReturnedSetSizeAndNull(set, 2);

		
		first.destroyProperty();
		second.destroyProperty();
		
		set = testClient.getProperties1Entity1Threat("");
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
		
		t1.destroyThreat();
		t2.destroyThreat();
	}

	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that entities are updated correctly by comparing the entity returned by update and retrieved with get by id indirectly exploited 
	public final void testUpdateEntity() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateEntities");
		Entity first = testClient.createEntity("first", EntityNature.HUMAN);
		
		//N.b. Potrebbe capitare che un altro client interagisca col server andando a 
		//modificare la risorsa first facendo fallire i test, qui sotto. Siccome nei 
		//test della create, questo problema non è stato considerato, faccio così anche
		//nel caso dell'update
		
		assertNotNull("The element created is null", first);
		assertEquals("first",first.getName());
		assertEquals(EntityNature.HUMAN, first.getType());
		
		Boolean correctlyUpdated = first.updateEntity("second", EntityNature.CYBER);
		
		assertTrue("The update was not correct", correctlyUpdated);
		assertEquals("second",first.getName());
		assertEquals(EntityNature.CYBER, first.getType());
		
		first.destroyEntity();
		
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that threats are updated correctly by comparing the returned by the update and the one retrieved with get by id indirectly 
	public final void testUpdateThreat() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateThreat");
		Threat first = testClient.createThreat("first");
		
		//N.b. Potrebbe capitare che un altro client interagisca col server andando a 
		//modificare la risorsa first facendo fallire i test, qui sotto. Siccome nei 
		//test della create, questo problema non è stato considerato, faccio così anche
		//nel caso dell'update
		
		assertNotNull("The element created is null", first);
		assertEquals("first",first.getName());
		
		Boolean correctlyUpdated = first.updateThreat("second");
		
		assertTrue("The update was not correct", correctlyUpdated);
		assertEquals("second",first.getName());
		
		first.destroyThreat();
		
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that relation are updated correctly by comparing the relation returned by update and retrieved with get by id indirectly 
	public final void testUpdateRelation2Entities() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateRelation2Entities");
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Relation2Entities first = testClient.createRelation2Entities(Relation2EntitiesName.CONTAIN,e1,e2);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation2EntitiesName.CONTAIN.toString(),first.getName());
		
		assertEquals(e1.getSelf(), first.getEntity1().getSelf());
		assertEquals("e1",first.getEntity1().getName());
		assertEquals(EntityNature.HUMAN, first.getEntity1().getType());

		assertEquals(e2.getSelf(), first.getEntity2().getSelf());
		assertEquals("e2",first.getEntity2().getName());
		assertEquals(EntityNature.CYBER, first.getEntity2().getType());
		
		
		Boolean correctlyUpdated = first.updateRelation(Relation2EntitiesName.DEPEND, e2, e3);
		
		assertTrue("The update was not correct", correctlyUpdated);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation2EntitiesName.DEPEND.toString(),first.getName());
		
		assertEquals(e2.getSelf(), first.getEntity1().getSelf());
		assertEquals("e2",first.getEntity1().getName());
		assertEquals(EntityNature.CYBER, first.getEntity1().getType());

		assertEquals(e3.getSelf(), first.getEntity2().getSelf());
		assertEquals("e3",first.getEntity2().getName());
		assertEquals(EntityNature.PHYSICAL, first.getEntity2().getType());
		
		first.destroyRelation();
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
		
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that relation are updated correctly by comparing the relation returned by update and retrieved with get by id indirectly 
	public final void testUpdateRelation3Entities() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateRelation2Entities");
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		Entity e4 = testClient.createEntity("e4", EntityNature.HUMAN);
		Entity e5 = testClient.createEntity("e5", EntityNature.CYBER);
	
		
		Relation3Entities first = testClient.createRelation3Entities(Relation3EntitiesName.CONNECT,e1,e2,e3);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation3EntitiesName.CONNECT.toString(),first.getName());
		
		assertEquals(e1.getSelf(), first.getEntity1().getSelf());
		assertEquals("e1",first.getEntity1().getName());
		assertEquals(EntityNature.HUMAN, first.getEntity1().getType());

		assertEquals(e2.getSelf(), first.getEntity2().getSelf());
		assertEquals("e2",first.getEntity2().getName());
		assertEquals(EntityNature.CYBER, first.getEntity2().getType());
		
		assertEquals(e3.getSelf(), first.getEntity3().getSelf());
		assertEquals("e3",first.getEntity3().getName());
		assertEquals(EntityNature.PHYSICAL, first.getEntity3().getType());

		
		Boolean correctlyUpdated = first.updateRelation(Relation3EntitiesName.CONNECT,e3,e4,e5);
		
		assertTrue("The update was not correct", correctlyUpdated);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation3EntitiesName.CONNECT.toString(),first.getName());
		
		assertEquals(e3.getSelf(), first.getEntity1().getSelf());
		assertEquals("e3",first.getEntity1().getName());
		assertEquals(EntityNature.PHYSICAL, first.getEntity1().getType());
		
		assertEquals(e4.getSelf(), first.getEntity2().getSelf());
		assertEquals("e4",first.getEntity2().getName());
		assertEquals(EntityNature.HUMAN, first.getEntity2().getType());
		
		assertEquals(e5.getSelf(), first.getEntity3().getSelf());
		assertEquals("e5",first.getEntity3().getName());
		assertEquals(EntityNature.CYBER, first.getEntity3().getType());

		first.destroyRelation();
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
		e4.destroyEntity();
		e5.destroyEntity();
		
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that relation are updated correctly by comparing the relation returned by update and retrieved with get by id indirectly 
	public final void testUpdateRelation2Entities1Threat() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateRelation2Entities1Threat");
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		Entity e3 = testClient.createEntity("e3", EntityNature.PHYSICAL);
		
		Threat t1 = testClient.createThreat("t1");
		Threat t2 = testClient.createThreat("t2");
		
		Relation2Entities1Threat first = testClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.MEND,e1,e2,t1);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation2Entities1ThreatName.MEND.toString(),first.getName());
		
		assertEquals(e1.getSelf(), first.getEntity1().getSelf());
		assertEquals("e1",first.getEntity1().getName());
		assertEquals(EntityNature.HUMAN, first.getEntity1().getType());

		assertEquals(e2.getSelf(), first.getEntity2().getSelf());
		assertEquals("e2",first.getEntity2().getName());
		assertEquals(EntityNature.CYBER, first.getEntity2().getType());

		assertEquals(t1.getSelf(), first.getThreat().getSelf());
		assertEquals("t1",first.getThreat().getName());
		
		Boolean correctlyUpdated = first.updateRelation(Relation2Entities1ThreatName.MONITOR, e2, e3, t2);
		
		assertTrue("The update was not correct", correctlyUpdated);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation2Entities1ThreatName.MONITOR.toString(),first.getName());
		
		assertEquals(e2.getSelf(), first.getEntity1().getSelf());
		assertEquals("e2",first.getEntity1().getName());
		assertEquals(EntityNature.CYBER, first.getEntity1().getType());

		assertEquals(e3.getSelf(), first.getEntity2().getSelf());
		assertEquals("e3",first.getEntity2().getName());
		assertEquals(EntityNature.PHYSICAL, first.getEntity2().getType());
		
		assertEquals(t2.getSelf(), first.getThreat().getSelf());
		assertEquals("t2",first.getThreat().getName());
		
		first.destroyRelation();
		
		e1.destroyEntity();
		e2.destroyEntity();
		e3.destroyEntity();
		
		t1.destroyThreat();
		t2.destroyThreat();
		
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that relation are updated correctly by comparing the relation returned by update and retrieved with get by id indirectly 
	public final void testUpdateRelation1Entity1Threat() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateRelation1Entity1Threat");
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		
		Threat t1 = testClient.createThreat("t1");
		Threat t2 = testClient.createThreat("t2");
		
		Relation1Entity1Threat first = testClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.POTENTIALLY_VULNERABLE,e1,t1);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation1Entity1ThreatName.POTENTIALLY_VULNERABLE.toString(),first.getName());
		
		assertEquals(e1.getSelf(), first.getEntity1().getSelf());
		assertEquals("e1",first.getEntity1().getName());
		assertEquals(EntityNature.HUMAN, first.getEntity1().getType());

		assertEquals(t1.getSelf(), first.getThreat().getSelf());
		assertEquals("t1",first.getThreat().getName());
		
		Boolean correctlyUpdated = first.updateRelation(Relation1Entity1ThreatName.SPREAD, e2, t2);
		
		assertTrue("The update was not correct", correctlyUpdated);
		
		assertNotNull("The element created is null", first);
		assertEquals(Relation1Entity1ThreatName.SPREAD.toString(),first.getName());
		
		assertEquals(e2.getSelf(), first.getEntity1().getSelf());
		assertEquals("e2",first.getEntity1().getName());
		assertEquals(EntityNature.CYBER, first.getEntity1().getType());
		
		assertEquals(t2.getSelf(), first.getThreat().getSelf());
		assertEquals("t2",first.getThreat().getName());
		
		first.destroyRelation();
		
		e1.destroyEntity();
		e2.destroyEntity();
		
		t1.destroyThreat();
		t2.destroyThreat();
		
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that property are updated correctly by comparing the property returned by update and retrieved with get by id indirectly 
	public final void testUpdateProperty1Entity() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateProperty1Entity");
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		
		Property1Entity first = testClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED,e1);
		
		assertNotNull("The element created is null", first);
		assertEquals(BasicProperty1EntityName.MALFUNCTIONED.toString(),first.getName());
		
		assertEquals(e1.getSelf(), first.getEntity().getSelf());
		assertEquals("e1",first.getEntity().getName());
		assertEquals(EntityNature.HUMAN, first.getEntity().getType());
		
		Boolean correctlyUpdated = first.updateProperty(BasicProperty1EntityName.MALFUNCTIONED, e2);
		
		assertTrue("The update was not correct", correctlyUpdated);
		
		assertNotNull("The element created is null", first);
		assertEquals(BasicProperty1EntityName.MALFUNCTIONED.toString(),first.getName());
		
		assertEquals(e2.getSelf(), first.getEntity().getSelf());
		assertEquals("e2",first.getEntity().getName());
		assertEquals(EntityNature.CYBER, first.getEntity().getType());
		
		first.destroyProperty();
		
		e1.destroyEntity();
		e2.destroyEntity();
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET BY ID
	@Test
	// Check that property are updated correctly by comparing the property returned by update and retrieved with get by id indirectly 
	public final void testUpdateProperty1Entity1Threat() throws ServiceException, DestroyedException, InUseException  {
		
		System.out.println("DEBUG: starting testUpdateProperty1Entity1Threat");
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		
		Threat t1 = testClient.createThreat("t1");
		Threat t2 = testClient.createThreat("t2");
		
		Property1Entity1Threat first = testClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED,e1,t1);
		
		assertNotNull("The element created is null", first);
		assertEquals(BasicProperty1Entity1ThreatName.COMPROMISED.toString(),first.getName());
		
		assertEquals(e1.getSelf(), first.getEntity().getSelf());
		assertEquals("e1",first.getEntity().getName());
		assertEquals(EntityNature.HUMAN, first.getEntity().getType());

		assertEquals(t1.getSelf(), first.getThreat().getSelf());
		assertEquals("t1",first.getThreat().getName());
		
		Boolean correctlyUpdated = first.updateProperty(BasicProperty1Entity1ThreatName.VULNERABLE, e2, t2);
		
		assertTrue("The update was not correct", correctlyUpdated);
		
		assertNotNull("The element created is null", first);
		assertEquals(BasicProperty1Entity1ThreatName.VULNERABLE.toString(),first.getName());
		
		assertEquals(e2.getSelf(), first.getEntity().getSelf());
		assertEquals("e2",first.getEntity().getName());
		assertEquals(EntityNature.CYBER, first.getEntity().getType());
		
		assertEquals(t2.getSelf(), first.getThreat().getSelf());
		assertEquals("t2",first.getThreat().getName());
		
		first.destroyProperty();
		
		e1.destroyEntity();
		e2.destroyEntity();
		
		t1.destroyThreat();
		t2.destroyThreat();
		
	}
	
	//TEST controllato con wireshark
	//TEST of PUT, GET, DELETE
	@Test
	// Check that DerivedProperty1Entity are created correctly and that getProperties1Entity returns consistent sets
	public final void testCreateDerivedProperty1Entity() throws ServiceException, DestroyedException, InUseException, NotAcceptableNameException  {
		System.out.println("DEBUG: starting testCreateDerivedProperty1Entity");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		
		Property1Entity first = testClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED,e1);
		Property1Entity second = testClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED,e2);
		Property1Entity third = testClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED,e1);

		Boolean correctlyCreated = testClient.computeResults();
		
		assertTrue("Error during the creation of the derived properties",correctlyCreated);

		Set<? extends DerivedProperty1Entity> set = testClient.getDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING, null); 
		checkReturnedSetSizeAndNull(set, 3);

		set = testClient.getDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING,e1);
		checkReturnedSetSizeAndNull(set, 2);
		
		set = testClient.getDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING,e2);
		checkReturnedSetSizeAndNull(set, 1);
		
		first.destroyProperty();
		second.destroyProperty();
		
		
		set = testClient.getDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING, null);
		checkReturnedSetSizeAndNull(set, 1);
		
		third.destroyProperty();
		
		testClient.deleteAllDerivedProperties();
		set = testClient.getDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING, null);
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();
		
		/*try{
			e1.destroyEntity();
			fail("Entity destroyed even if it should not be");
			e2.destroyEntity();
		} catch (InUseException IUe){
			testClient.deleteAllDerivedProperties();
			e1.destroyEntity();
			e2.destroyEntity();
		}*/


	}

	//TEST controllato con wireshark
	//TEST of PUT, GET, DELETE
	@Test
	// Check that DerivedProperty1Entity1Threat are created correctly and that getProperties1Entity1Threat returns consistent sets
	public final void testCreateDerivedProperty1Entity1Threat() throws ServiceException, DestroyedException, InUseException, NotAcceptableNameException  {
		System.out.println("DEBUG: starting testCreateDerivedProperty1Entity1Threat");
		
		Entity e1 = testClient.createEntity("e1", EntityNature.HUMAN);
		Entity e2 = testClient.createEntity("e2", EntityNature.CYBER);
		
		Threat t1 = testClient.createThreat("t1");
		Threat t2 = testClient.createThreat("t2");
		
		Property1Entity1Threat first = testClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED,e1,t1);
		Property1Entity1Threat second = testClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED,e2,t2);
		Property1Entity1Threat third = testClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED,e1,t2);

		Boolean correctlyCreated = testClient.computeResults();
		
		assertTrue("Error during the creation of the derived properties",correctlyCreated);

		Set<? extends DerivedProperty1Entity1Threat> set = testClient.getDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED,null,null); 
		checkReturnedSetSizeAndNull(set, 3);

		set = testClient.getDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED,e1,null);
		checkReturnedSetSizeAndNull(set, 2);
		
		set = testClient.getDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED,null,t1);
		checkReturnedSetSizeAndNull(set, 1);
		
		set = testClient.getDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED,e2,t1);
		checkReturnedSetSizeAndNull(set, 0);			
		
		first.destroyProperty();
		second.destroyProperty();
		
		set = testClient.getDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED,null,null);
		checkReturnedSetSizeAndNull(set, 1);

		third.destroyProperty();
		
		testClient.deleteAllDerivedProperties();
		set = testClient.getDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED,null,null);
		checkReturnedSetSizeAndNull(set, 0);
		
		e1.destroyEntity();
		e2.destroyEntity();

		t1.destroyThreat();
		t2.destroyThreat();

		
		/*try{
			e1.destroyEntity();
			fail("Entity destroyed even if it should not be");
			e2.destroyEntity();
		} catch (InUseException IUe){
			testClient.deleteAllDerivedProperties();
			e1.destroyEntity();
			e2.destroyEntity();
		}*/


	}

	
	/*
	@Test
	// Check that removeItem() correctly manages the removal of an item from the bookshelf
	public final void testGetItems() throws ServiceException, DestroyedBookshelfException, UnknownItemException  {
		System.out.println("DEBUG: starting testGetItems");
		Set<ItemReader> items = testClient.getItems("", 0, 3000);
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", items);
		
		Set<it.polito.dp2.BIB.ItemReader> ref_items = referenceBibReader.getItems("", 0, 3000);
		
		assertEquals("getItems returned a wrong number of items ",ref_items.size(),items.size());
	}
	
	@Test
	// Check that removeItem() correctly manages the removal of an item from the bookshelf
	public final void testBookShelfOperations() throws ServiceException, DestroyedBookshelfException, UnknownItemException, TooManyItemsException  {
		System.out.println("DEBUG: starting testBookShelfOperations");
		String testBookShelf="Biography";
		
		Bookshelf bookshelf = testClient.createBookshelf(testBookShelf);
		assertNotNull("The createBookshelf of the client under test generated a null Bookshelf", bookshelf);
			
		int numberOfBooksInBookShelf = addItemsBookshelf(testBookShelf, bookshelf);
		System.out.println("DEBUG: testBookShelfOperations() "+numberOfBooksInBookShelf+" number of new items added to the "+testBookShelf);
		
		if (numberOfBooksInBookShelf>0) {
			// remove first item
			Set<ItemReader> items = bookshelf.getItems();
			assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", items);
			ItemReader firstItem = bookshelf.getItems().iterator().next();
			assertNotNull("The getItems of the implementation under test returned a null ItemReader", firstItem);
			bookshelf.removeItem(firstItem);
			numberOfBooksInBookShelf--;
			Set<ItemReader> intemsAfterRemove = bookshelf.getItems();
			assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", intemsAfterRemove);
			int actual = intemsAfterRemove.size();
			
			// check that the number of items in the bookshelf matches after removing an item
			assertEquals("Wrong number of books in the bookshelf "+testBookShelf +" after removing an item ",numberOfBooksInBookShelf,actual);		
		}
		
		bookshelf.destroyBookshelf();
	}

	// add all book items to the current bookshelf up to limit and check number of items
	private int addItemsBookshelf(String testBookShelf, Bookshelf bookshelf)
			throws ServiceException, DestroyedBookshelfException, UnknownItemException, TooManyItemsException {
		Set<ItemReader> items = testClient.getItems("", 0, 3000);
		
		
		TreeSet<ItemReader> sortedItems = new TreeSet<ItemReader>(new ItemReaderComparator());
		sortedItems.addAll(items);
		
		// limit the number of items to be added to the bookshelf
		int limit = 20;
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", items);
				
		int numberOfBooksInBookShelf=0;
		for (ItemReader item: sortedItems) {
			if(numberOfBooksInBookShelf>=limit) break;
				bookshelf.addItem(item);
				numberOfBooksInBookShelf++;
		}
		
		Set<ItemReader> itemsAfterAdd = bookshelf.getItems();
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", itemsAfterAdd);
		int actual = itemsAfterAdd.size();
		
		// check the number of items in bookshelf has been increased as expected
		assertEquals("Wrong number of books in the bookshelf "+testBookShelf +" after adding "+ numberOfBooksInBookShelf +" items ",numberOfBooksInBookShelf,actual);
		return numberOfBooksInBookShelf;
	}
	
	@Test 
	// Check that the removal of an item from the database correctly removes it from all bookshelves
	public final void testRemoveItemFromBibliography() throws ServiceException, DestroyedBookshelfException, UnknownItemException, DatatypeConfigurationException, TooManyItemsException {
		System.out.println("DEBUG: starting testRemoveItemFromBibliography");
		// create three different bookshelves
		Bookshelf first = testClient.createBookshelf("Non-Fiction Science");
		Bookshelf second = testClient.createBookshelf("Non-Fiction History");
		Bookshelf third = testClient.createBookshelf("Non-Fiction Math");
		
		// upload a new book item to the server and update the item with returned data
		Item item = buildItem();
		item = adminClient.createItem(item);
		
		Set<ItemReader> addedItemSet = testClient.getItems("The Way of Z", 1990, 1999);
		assertNotNull("The getItems of the implementation under test generated a null set of added ItemReader", addedItemSet);
		assertTrue("The getItems of the implementation under test generated an empty set of ItemReader", addedItemSet.iterator().hasNext());
		
		
		// retrieve the only (expected) item from the set   
		ItemReader addedItem = addedItemSet.iterator().next();
		
		// add the new book item to all bookshelves
		first.addItem(addedItem);
		second.addItem(addedItem);
		third.addItem(addedItem);
		
		Set<ItemReader> firstItems = first.getItems();
		Set<ItemReader> secondItems = second.getItems();
		Set<ItemReader> thirdItems = third.getItems();
		
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", firstItems);
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", secondItems);
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", thirdItems);
		
		// check that the number of items in the bookshelves equal to one after adding an item
		assertEquals("Wrong number of books in the bookshelf Non-Fiction Science",1,first.getItems().size());
		assertEquals("Wrong number of books in the bookshelf Non-Fiction History",1,second.getItems().size());
		assertEquals("Wrong number of books in the bookshelf Non-Fiction Math",1,third.getItems().size());
		
		// delete the new added book item from the server
		adminClient.removeItem(item);

		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", firstItems);
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", secondItems);
		assertNotNull("The getItems of the implementation under test generated a null set of ItemReader", thirdItems);

		// check that the number of items in the bookshelves equal to 0 after removing the added item
		assertEquals("Wrong number of books in the bookshelf Non-Fiction Science",0,first.getItems().size());
		assertEquals("Wrong number of books in the bookshelf Non-Fiction History",0,second.getItems().size());
		assertEquals("Wrong number of books in the bookshelf Non-Fiction Math",0,third.getItems().size());
		
		first.destroyBookshelf();
		second.destroyBookshelf();
		third.destroyBookshelf();
		
	}
	
	// generate a new book item
	private Item buildItem() {
		Item itemToSend = new Item();
		itemToSend.setTitle("The Way of Z");
		itemToSend.setSubtitle("Practica1 Programming with Formal Methods");
		itemToSend.getAuthor().add("J. Yusupov");
		BookType book = new BookType();
		book.setISBN("0131411553");
		book.setPublisher("Addison Wesley");
		itemToSend.setBook(book);
		try {
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			calendar.setYear(1996);
			book.setYear(calendar);
		} catch (DatatypeConfigurationException e) {
			assertTrue("Unexpected internal error",false);
		}
		return itemToSend;
	}

	@Test
	// Check that the getItems() method of the bookshelf correctly updates the number of reads
	public final void testNumberOfReads() throws ServiceException, DestroyedBookshelfException, UnknownItemException, TooManyItemsException{
		System.out.println("DEBUG: starting testNumberOfReads");
		String testBookShelf="Phantasy";
		
		Bookshelf bookshelf = testClient.createBookshelf(testBookShelf);
		assertNotNull("The createBookshelf of the implementation under testNumberOfReads generated a null Bookshelf", bookshelf);
		
		addItemsBookshelf(testBookShelf, bookshelf);
		int beforeNumberOfReads = bookshelf.getNumberOfReads();
		
		// reading contents of the bookshelf must increment the number of reads
		bookshelf.getItems();
		int afterNumberOfReads = bookshelf.getNumberOfReads();
		
		assertTrue("Wrong number of reads in the bookshelf "+testBookShelf ,beforeNumberOfReads<afterNumberOfReads);
		
		bookshelf.destroyBookshelf();
	}
		
	
	@Test(expected=DestroyedBookshelfException.class)
	//Check if adding an item to the destroyed bookshelf correctly managed
	public final void testNonExistingBookshelf() throws ServiceException, DestroyedBookshelfException, UnknownItemException, TooManyItemsException{
		System.out.println("DEBUG: starting testNonExistingBookshelf");
		// create a new bookshelf and destroy it
		String testBookShelf = "Memoir";
		Bookshelf bookshelf = testClient.createBookshelf(testBookShelf);
		assertNotNull("The createBookshelf of the implementation under testNumberOfReads generated a null Bookshelf", bookshelf);

		bookshelf.destroyBookshelf();
		
		// retrieve items from the server in order to add to the bookshelf
		Set<ItemReader> items = testClient.getItems("", 0, 3000);
		assertTrue("Server did not return any item to add " , items!=null && !items.isEmpty());
		
		// check that addItem() method of the destroyed bookshelf throws the expected exception
		for (ItemReader itemReader : items) {
			bookshelf.addItem(itemReader);
		}
		
	}	
	
	
	class ItemReaderComparator implements Comparator<ItemReader> {
		public int compare(ItemReader f0, ItemReader f1) {
			if (f0 == f1)
				return 0;
			if (f0 == null)
				return -1;
			if (f1 == null)
				return 1;
			String title0 = f0.getTitle();
			String title1 = f1.getTitle();
			if (title0 == title1)
				return 0;
			if (title0 == null)
				return -1;
			if (title1 == null)
				return 1;
			return title0.compareTo(title1);
		}
	}

	*/

}

