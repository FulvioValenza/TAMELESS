package it.polito.dp2.TAMELESS.sol.client;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.management.relation.Relation;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.TAMELESS.ass.Client;
import it.polito.dp2.TAMELESS.ass.DestroyedException;
import it.polito.dp2.TAMELESS.ass.ServiceException;
import it.polito.dp2.TAMELESS.ass.NotAcceptableNameException;

/*
import it.polito.dp2.BIB.ass3.Bookshelf;
import it.polito.dp2.BIB.ass3.ItemReader;
*/

import it.polito.dp2.TAMELESS.sol.client.*;


public class ClientFactoryImpl implements Client {
	
	private javax.ws.rs.client.Client client;
	private WebTarget target;
	private static Set<it.polito.dp2.TAMELESS.ass.Relation2Entities> r2eSet;
	private static Set<it.polito.dp2.TAMELESS.ass.Relation3Entities> r3eSet;
	private static Set<it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat> r2e1tSet;
	private static Set<it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat> r1e1tSet;
	private static Set<it.polito.dp2.TAMELESS.ass.Property1Entity> p1eSet;
	private static Set<it.polito.dp2.TAMELESS.ass.Property1Entity1Threat> p1e1tSet;
	
	static ObjectFactory of;
	static String uri = "http://localhost:8080/TAMELESS/rest";
	static String urlProperty = "it.polito.dp2.TAMELESS.ass.URL";
	static String portProperty = "it.polito.dp2.TAMELESS.ass.PORT";
	static String elemAlreadyCreatedProperty = "elemAlreadyCreated";  
	static String safeExampleProperty = "safeExample";
	
	public ClientFactoryImpl(URI uri) {
		this.uri = uri.toString();
		
		client = ClientBuilder.newClient();
		// .path("aaa") create a new web target with the original uri and "/aaa" at the end
		target = client.target(uri).path("system");
		
		of = new ObjectFactory();
		
		r2eSet = new LinkedHashSet<>();
		r3eSet = new LinkedHashSet<>();
		r2e1tSet = new LinkedHashSet<>();
		r1e1tSet = new LinkedHashSet<>();
		p1eSet = new LinkedHashSet<>();
		p1e1tSet = new LinkedHashSet<>();
		System.out.println(target);
	}
	
	static ClientFactoryImpl mainClient;
	public static void main(String[] args) {
		//System.setProperty("it.polito.dp2.BIB.BibReaderFactory", "it.polito.dp2.BIB.Random.BibReaderFactoryImpl");
		String customUri = System.getProperty(urlProperty);
		String customPort = System.getProperty(portProperty);
		Boolean elemAlreadyCreated = System.getProperty(elemAlreadyCreatedProperty)
				.compareTo("true") == 0 ? true : false;
		
		Boolean safeExample = System.getProperty(safeExampleProperty)
				.compareTo("true") == 0 ? true : false;
		
		System.out.println("customUri : " + customUri);
		System.out.println("customPort : " + customPort);
		System.out.println("elemAlreadyCreated : " + elemAlreadyCreated);
		
		
		if (customUri != null)
			uri = customUri;
		

		
		try {
			
			mainClient = new ClientFactoryImpl(new URI(uri));
			
			//it.polito.dp2.TAMELESS.ass.Entity website = mainClient.createEntity("website",EntityNature.CYBER);
			//it.polito.dp2.TAMELESS.ass.Entity tmp = mainClient.createEntity("website",EntityNature.CYBER);
			
			//it.polito.dp2.TAMELESS.ass.Threat dos = mainClient.createThreat("dos");
			//it.polito.dp2.TAMELESS.ass.Threat tmp = mainClient.createThreat("dos");
			
			if( ! elemAlreadyCreated ){		//se la property è false, allora 
											//devo creare gli elementi sul server
				
				
				
				if(! safeExample){
					it.polito.dp2.TAMELESS.ass.Entity website = mainClient.createEntity("website",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity air_conditioned = mainClient.createEntity("air_conditioned",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity fanbox_airConditioned = mainClient.createEntity("fanbox_airConditioned",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity servers = mainClient.createEntity("servers",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity attacker = mainClient.createEntity("attacker",EntityNature.HUMAN);
					it.polito.dp2.TAMELESS.ass.Entity biometricAuth = mainClient.createEntity("biometricAuth",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity room = mainClient.createEntity("room",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity path = mainClient.createEntity("path",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity path2 = mainClient.createEntity("path2",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity internet = mainClient.createEntity("internet",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity firewall = mainClient.createEntity("firewall",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity antivirus = mainClient.createEntity("antivirus",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity antimalwer = mainClient.createEntity("antimalwer",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity ids = mainClient.createEntity("ids",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity tcpConnection = mainClient.createEntity("tcpConnection",EntityNature.CYBER);
					
					
					printEntities("");
	
	
					it.polito.dp2.TAMELESS.ass.Threat dos = mainClient.createThreat("dos");
					it.polito.dp2.TAMELESS.ass.Threat virus = mainClient.createThreat("virus");
					it.polito.dp2.TAMELESS.ass.Threat malwer = mainClient.createThreat("malwer");
					it.polito.dp2.TAMELESS.ass.Threat intrusion = mainClient.createThreat("intrusion");
					it.polito.dp2.TAMELESS.ass.Threat unauthorizedAccess = mainClient.createThreat("unauthorizedAccess");
					it.polito.dp2.TAMELESS.ass.Threat physicalAttack = mainClient.createThreat("physicalAttack");
					it.polito.dp2.TAMELESS.ass.Threat maliciousPurpose = mainClient.createThreat("maliciousPurpose");
					
					printThreats("");
					
	
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTAIN, servers, website));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTAIN, room, servers));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTAIN, room, air_conditioned));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.DEPEND, air_conditioned, fanbox_airConditioned));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.DEPEND, servers, air_conditioned));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.DEPEND, website, servers));
					
					printRelations2Entities("");
					
					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, tcpConnection, internet, servers));
					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, path, attacker, fanbox_airConditioned));
					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, path2, attacker, room));
					
					printRelations3Entities("");
		
					
					r2e1tSet.add(mainClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT, biometricAuth, path2, unauthorizedAccess));
					r2e1tSet.add(mainClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT, firewall, tcpConnection, dos));
					r2e1tSet.add(mainClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT, ids, tcpConnection, intrusion));
					r2e1tSet.add(mainClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT, antivirus, servers, virus));
					r2e1tSet.add(mainClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT, antimalwer, servers, malwer));
					
					printRelations2Entities1Threat("");
		
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, room, physicalAttack));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, attacker, physicalAttack));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, internet, dos));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, internet, virus));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, internet, intrusion));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, internet, malwer));
					//r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.POTENTIALLY_VULNERABLE, internet, malwer));
					
					printRelations1Entity1Threat("");
					
					/*
					p1eSet.add(mainClient.createProperty1Entity(BasicProperty1EntityName.MALFUNCTIONED, room));
					
					printProperties1Entity("");
					*/
				
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, servers, virus));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, servers, dos));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, servers, malwer));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, website, virus));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, website, dos));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, website, malwer));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, air_conditioned, physicalAttack));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, fanbox_airConditioned, physicalAttack));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED, attacker, maliciousPurpose));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED, internet, maliciousPurpose));
					
					
					printProperties1Entity1Threat("");
				}
				else{
					//it.polito.dp2.TAMELESS.ass.Entity employee = mainClient.createEntity("employee__",EntityNature.HUMAN);
					it.polito.dp2.TAMELESS.ass.Entity employee = mainClient.createEntity("employee",EntityNature.HUMAN);
					it.polito.dp2.TAMELESS.ass.Entity attacker = mainClient.createEntity("attacker",EntityNature.HUMAN);
					it.polito.dp2.TAMELESS.ass.Entity cleaningStuff = mainClient.createEntity("cleaningStuff",EntityNature.HUMAN);
					it.polito.dp2.TAMELESS.ass.Entity pc_employee = mainClient.createEntity("pc_employee",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity clientMail_pc = mainClient.createEntity("clientMail_pc",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity serverMail = mainClient.createEntity("serverMail",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity phishingMail = mainClient.createEntity("phishingMail",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity employee_data = mainClient.createEntity("employee_data",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity logicalAssociation = mainClient.createEntity("logicalAssociation",EntityNature.CYBER);
					it.polito.dp2.TAMELESS.ass.Entity pin_safeLock = mainClient.createEntity("pin_safeLock",EntityNature.PHYSICAL);
					//it.polito.dp2.TAMELESS.ass.Entity doorLock = mainClient.createEntity("doorLock__",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity doorLock = mainClient.createEntity("doorLock",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity safeLock = mainClient.createEntity("safeLock",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity key_doorLock = mainClient.createEntity("key_doorLock",EntityNature.PHYSICAL);
					//it.polito.dp2.TAMELESS.ass.Entity safe = mainClient.createEntity("safe__",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity safe = mainClient.createEntity("safe",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity path = mainClient.createEntity("path",EntityNature.PHYSICAL);
					it.polito.dp2.TAMELESS.ass.Entity friendship = mainClient.createEntity("friendship",EntityNature.HUMAN);
					
					
					printEntities("");
	
	
					it.polito.dp2.TAMELESS.ass.Threat phishingAttack = mainClient.createThreat("phishingAttack");
					it.polito.dp2.TAMELESS.ass.Threat stealingInformation = mainClient.createThreat("stealingInformation");
					it.polito.dp2.TAMELESS.ass.Threat derivationFromAssociation = mainClient.createThreat("derivationFromAssociation");
					it.polito.dp2.TAMELESS.ass.Threat maliciousPurpose = mainClient.createThreat("maliciousPurpose");
					it.polito.dp2.TAMELESS.ass.Threat openBykey = mainClient.createThreat("openBykey");
					it.polito.dp2.TAMELESS.ass.Threat corruption = mainClient.createThreat("corruption");
					it.polito.dp2.TAMELESS.ass.Threat copy = mainClient.createThreat("copy");
					it.polito.dp2.TAMELESS.ass.Threat unauthorizedAccess = mainClient.createThreat("unauthorizedAccess");
					it.polito.dp2.TAMELESS.ass.Threat attackerPurpose = mainClient.createThreat("attackerPurpose");
					
					
					printThreats("");

					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, path, attacker, safe));
					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, friendship, attacker, cleaningStuff));
					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, logicalAssociation, employee_data, pin_safeLock));
					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, pc_employee, clientMail_pc, employee));
					r3eSet.add(mainClient.createRelation3Entities(Relation3EntitiesName.CONNECT, serverMail, phishingMail, clientMail_pc));
					
					printRelations3Entities("");
	
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTROL, employee, employee_data));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTROL, attacker, phishingMail));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTROL, key_doorLock, doorLock));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTROL, cleaningStuff, key_doorLock));
					r2eSet.add(mainClient.createRelation2Entities(Relation2EntitiesName.CONTROL, pin_safeLock, safeLock));
					
					
					printRelations2Entities("");
					

		
					
					r2e1tSet.add(mainClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT, doorLock, path, unauthorizedAccess));
					r2e1tSet.add(mainClient.createRelation2Entities1Threat(Relation2Entities1ThreatName.PROTECT, pin_safeLock, safe, unauthorizedAccess));
					
					printRelations2Entities1Threat("");
		
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, attacker, corruption));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, cleaningStuff, copy));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, attacker, unauthorizedAccess));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, key_doorLock, openBykey));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, phishingMail, phishingAttack));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, employee_data, derivationFromAssociation));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, clientMail_pc, stealingInformation));
					r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.SPREAD, employee, stealingInformation));
					//r1e1tSet.add(mainClient.createRelation1Entity1Threat(Relation1Entity1ThreatName.POTENTIALLY_VULNERABLE, internet, malwer));
					
					printRelations1Entity1Threat("");
					
				
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, cleaningStuff, corruption));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, key_doorLock, copy));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, doorLock, openBykey));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, clientMail_pc, phishingAttack));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, employee, stealingInformation));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, employee_data, stealingInformation));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, pin_safeLock, derivationFromAssociation));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.VULNERABLE, safe, unauthorizedAccess));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED, attacker, maliciousPurpose));
					p1e1tSet.add(mainClient.createProperty1Entity1Threat(BasicProperty1Entity1ThreatName.COMPROMISED, phishingMail, attackerPurpose));
					
					
					printProperties1Entity1Threat("");

					
				}
			}
			
			/*
			//GET BY ID section
			
			System.out.println("Entity");
			Entity e = mainClient.getEntity((BigInteger.ZERO));
			printEntity(e);
			//e = mainClient.getEntity((BigInteger.TEN).add(BigInteger.TEN));
			//printEntity(e);
			
			System.out.println("Threat");
			Threat t = mainClient.getThreat((BigInteger.ZERO));
			printThreat(t);
			//t = mainClient.getThreat((BigInteger.TEN).add(BigInteger.TEN));
			//printThreat(t);
			
			System.out.println("Relation2Entities");
			Relation2Entities r2e = mainClient.getRelation2Entities(BigInteger.ZERO);
			printRelation2Entities(r2e);
			//r2e = mainClient.getRelation2Entities((BigInteger.TEN).add(BigInteger.TEN));
			//printRelation2Entities(r2e);
			
			System.out.println("Relation3Entities");
			Relation3Entities r3e = mainClient.getRelation3Entities(BigInteger.valueOf(8));
			printRelation3Entities(r3e);
			//r3e = mainClient.getRelation3Entities((BigInteger.TEN).add(BigInteger.TEN));
			//printRelation3Entities(r3e);
			
			System.out.println("Relation2Entities1Threat");
			Relation2Entities1Threat r2e1t = mainClient.getRelation2Entities1Threat(BigInteger.valueOf(12));
			printRelation2Entities1Threat(r2e1t);
			//r2e1t = mainClient.getRelation2Entities1Threat((BigInteger.TEN).add(BigInteger.TEN));
			//printRelation2Entities1Threat(r2e1t);
			
			System.out.println("Relation1Entity1Threat");
			Relation1Entity1Threat r1e1t = mainClient.getRelation1Entity1Threat(BigInteger.valueOf(14));
			printRelation1Entity1Threat(r1e1t);
			//r1e1t = mainClient.getRelation1Entity1Threat((BigInteger.TEN).add(BigInteger.TEN));
			//printRelation1Entity1Threat(r1e1t);
			
			/*
			System.out.println("Property1Entity");
			Property1Entity p1e = mainClient.getProperty1Entity(BigInteger.ZERO);
			printProperty1Entity(p1e);
			//p1e = mainClient.getProperty1Entity((BigInteger.TEN).add(BigInteger.TEN));
			//printProperty1Entity(p1e);
			*
			
			System.out.println("Property1Entity1Threat");
			Property1Entity1Threat p1e1t = mainClient.getProperty1Entity1Threat(BigInteger.ONE);
			printProperty1Entity1Threat(p1e1t);
			//p1e1t = mainClient.getProperty1Entity1Threat((BigInteger.TEN).add(BigInteger.TEN));
			//printProperty1Entity1Threat(p1e1t);
			*/
			
			/*
			//DELETE section
			
			mainClient.printEntities("");
			mainClient.deleteEntity(BigInteger.ZERO);
			mainClient.printEntities("");
			
			mainClient.printThreats("");
			mainClient.deleteThreat(BigInteger.ZERO);
			mainClient.printThreats("");
			
			mainClient.printRelations2Entities("");
			mainClient.deleteRelation2Entities(BigInteger.ZERO);
			mainClient.printRelations2Entities("");
						
			mainClient.printRelations3Entities("");
			mainClient.deleteRelation3Entities(BigInteger.valueOf(8));
			mainClient.printRelations3Entities("");
			
			mainClient.printRelations2Entities1Threat("");
			mainClient.deleteRelation2Entities1Threat(BigInteger.valueOf(12));
			mainClient.printRelations2Entities1Threat("");
			
			mainClient.printRelations1Entity1Threat("");
			mainClient.deleteRelation1Entity1Threat(BigInteger.valueOf(14));
			mainClient.printRelations1Entity1Threat("");			

			/*
			mainClient.printProperties1Entity("");
			mainClient.deleteProperty1Entity(BigInteger.ZERO);
			mainClient.printProperties1Entity("");
			*
			mainClient.printProperties1Entity1Threat("");
			mainClient.deleteProperty1Entity1Threat(BigInteger.ONE);
			mainClient.printProperties1Entity1Threat("");
			*/

			/*
			//PUT section
			
			mainClient.printEntities("");
			mainClient.updateEntity(BigInteger.valueOf(200),"Pippo",EntityNature.PHYSICAL);
			mainClient.printEntities("");
					
			mainClient.printThreats("");
			mainClient.updateThreat(BigInteger.ZERO,"Pluto");
			mainClient.printThreats("");
			
			mainClient.printRelations2Entities("");
			mainClient.updateRelation2Entities(BigInteger.ZERO,Relation2EntitiesName.CHECK,"room","air_conditioned");
			mainClient.printRelations2Entities("");
					
			mainClient.printRelations3Entities("");
			mainClient.updateRelation3Entities(BigInteger.valueOf(8), Relation3EntitiesName.CONNECT,"room","air_conditioned","path2");
			mainClient.printRelations3Entities("");
			
			mainClient.printRelations2Entities1Threat("");
			mainClient.updateRelation2Entities1Threat(BigInteger.valueOf(12), Relation2Entities1ThreatName.MEND,"room","air_conditioned","dos");
			mainClient.printRelations2Entities1Threat("");
			
			mainClient.printRelations1Entity1Threat("");
			mainClient.updateRelation1Entity1Threat(BigInteger.valueOf(14),Relation1Entity1ThreatName.POTENTIALLY_VULNERABLE,"room","dos");
			mainClient.printRelations1Entity1Threat("");			
			
			/*
			mainClient.printProperties1Entity("");
			mainClient.updateProperty1Entity(BigInteger.ZERO,BasicProperty1EntityName.MALFUNCTIONED,"room");
			mainClient.printProperties1Entity("");
			*
			mainClient.printProperties1Entity1Threat("");
			mainClient.updateProperty1Entity1Threat(BigInteger.ONE,BasicProperty1Entity1ThreatName.COMPROMISED,"room","dos");
			mainClient.printProperties1Entity1Threat("");
			*/
			
			//GET HATEOAS
			
			//getSystem
			
			//getRelationships
			
			//getProperties
			
			//getResults
			
			//a questo punto ottengo le derivedProperties
			
			if(mainClient.computeResults() == true){
				System.out.println("Results correctly computed");
				
				mainClient.printDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED, null, null);
				
				mainClient.printDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_MALFUNCTIONING, null);
				
				mainClient.printDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE, null, null);
				
				mainClient.printDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName.CANBE_DETECTED, null, null);
				
				mainClient.printDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_RESTORED, null);
				
				mainClient.printDerivedProperties1Entity(DerivedProperty1EntityName.CANBE_FIXED, null);
				
			}
			
			
		} catch (URISyntaxException | ServiceException e) {
			e.printStackTrace();
		} catch(NotFoundException nfe){
			nfe.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();			
		}
		
	}
	
	private static void printBlankLine() {
		System.out.println(" ");
	}

	
	private static void printLine(char c) {
		System.out.println(makeLine(c));
	}
	
	private static StringBuffer makeLine(char c) {
		StringBuffer line = new StringBuffer(132);
		
		for (int i = 0; i < 132; ++i) {
			line.append(c);
		}
		return line;
	}
	
	private void assertStatusEquals201(int status) throws ServiceException{
		//System.out.println(status);
		if(status!=201){
			throw new ServiceException();
		}
	}
	
	public it.polito.dp2.TAMELESS.ass.Entity createEntity(String entityName, EntityNature entityType) throws ServiceException{
		
		Entity toCreate = of.createEntity();
		toCreate.setName(entityName);
		toCreate.setType(entityType);
		
		Response res = target.path("entities")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		

		assertStatusEquals201(res.getStatus());
		
		
		Entity created = res.readEntity(Entity.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		EntityImpl impl = new EntityImpl(client, created.getSelf());
		return impl;
	}
	
	public Set<it.polito.dp2.TAMELESS.ass.Entity> getEntities(String name) throws ServiceException{
		
		Response res = target.path("entities")
				.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Entity> ret = new LinkedHashSet<>();
		
		Entities es = res.readEntity(Entities.class);
		
		for(Entities.Entity e : es.getEntity()){
			Entity eRet = of.createEntity();
			eRet.setName(e.getName());
			eRet.setProperties(e.getProperties());
			eRet.setRelations(e.getRelations());
			eRet.setSelf(e.getSelf());
			eRet.setType(e.getType());
			
			EntityImpl impl = new EntityImpl(client,e.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printEntities(String name) throws ServiceException, DestroyedException {
		Set<it.polito.dp2.TAMELESS.ass.Entity> set = mainClient.getEntities(name);
		System.out.println("Entities returned: "+set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Entity e : set) {
			printEntity(e);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printEntity(it.polito.dp2.TAMELESS.ass.Entity e) throws ServiceException,DestroyedException{
		System.out.println("Name : " + e.getName());
		System.out.print("Type : " + e.getType().value() + "\n");
		if (e.getSelf()!=null)
			System.out.println("Self : "+ e.getSelf());
		if (e.getRelationships()!=null)
			System.out.println("Relations : "+ e.getRelationships());			
		if (e.getProperties()!=null)
			System.out.println("Properties : "+ e.getProperties());
	}
	
	
	public it.polito.dp2.TAMELESS.ass.Threat createThreat(String threatName) throws ServiceException{
		
		Threat toCreate = of.createThreat();
		toCreate.setName(threatName);
		
		Response res = target.path("threats")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		
		assertStatusEquals201(res.getStatus());
		
		Threat created = res.readEntity(Threat.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		ThreatImpl impl = new ThreatImpl(client, created.getSelf());
		return impl;
	}
	
	public Set<it.polito.dp2.TAMELESS.ass.Threat> getThreats(String name) throws ServiceException{
		
		Response res = target.path("threats")
				.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Threat> ret = new LinkedHashSet<>();
		
		Threats ts = res.readEntity(Threats.class);
		
		for(Threats.Threat t : ts.getThreat()){
			
			Threat tRet = of.createThreat();
			tRet.setName(t.getName());
			tRet.setProperties(t.getProperties());
			tRet.setRelations(t.getRelations());
			tRet.setSelf(t.getSelf());
			
			ThreatImpl impl = new ThreatImpl(client, t.getSelf());
			ret.add(impl);
		
		}
		
		return ret;
	}
	
	private static void printThreats(String name) throws ServiceException, DestroyedException {
		Set<it.polito.dp2.TAMELESS.ass.Threat> set = mainClient.getThreats(name);
		System.out.println("Threats returned: "+set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Threat t : set) {
			printThreat(t);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printThreat(it.polito.dp2.TAMELESS.ass.Threat t) throws ServiceException, DestroyedException{
		System.out.println("Name : " + t.getName());
		if (t.getSelf()!=null)
			System.out.println("Self : "+ t.getSelf());
		if (t.getRelationships()!=null)
			System.out.println("Relations : "+ t.getRelationships());			
		if (t.getProperties()!=null)
			System.out.println("Properties : "+ t.getProperties());
	}
	
	
	public it.polito.dp2.TAMELESS.ass.Relation2Entities createRelation2Entities(Relation2EntitiesName relationName,it.polito.dp2.TAMELESS.ass.Entity entity1, it.polito.dp2.TAMELESS.ass.Entity entity2) throws DestroyedException, ServiceException{

		Relation2Entities toCreate = of.createRelation2Entities();
		toCreate.setRelationName(relationName);
		toCreate.setEntity1Id(entity1.getSelf());
		toCreate.setEntity2Id(entity2.getSelf());
		
		Response res = target.path("/relationships/relations2Entities")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		
		assertStatusEquals201(res.getStatus());

		Relation2Entities created = res.readEntity(Relation2Entities.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		Relation2EntitiesImpl impl = new Relation2EntitiesImpl(client, created.getSelf());
		return impl;
	}
	
	
	public Set<it.polito.dp2.TAMELESS.ass.Relation2Entities> getRelations2Entities(String name) throws ServiceException{
		
		Response res = target.path("/relationships/relations2Entities")
				//.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Relation2Entities> ret = new LinkedHashSet<>();
		
		Relations2Entities list = res.readEntity(Relations2Entities.class);
		
		for(Relations2Entities.Relation2Entities elem : list.getRelation2Entities()){
			
			Relation2Entities toAddElem = of.createRelation2Entities();
			toAddElem.setRelationName(elem.getRelationName());
			toAddElem.setEntity1Id(elem.getEntity1Id());
			toAddElem.setEntity2Id(elem.getEntity2Id());
			toAddElem.setSelf(elem.getSelf());
			
			//ret.add(toAddElem);
			
			Relation2EntitiesImpl impl = new Relation2EntitiesImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printRelations2Entities(String name) throws ServiceException, DestroyedException {
		Set<it.polito.dp2.TAMELESS.ass.Relation2Entities> set = mainClient.getRelations2Entities(name);
		System.out.println("Relations of two entities returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Relation2Entities elem : set) {
			printRelation2Entities(elem);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printRelation2Entities(it.polito.dp2.TAMELESS.ass.Relation2Entities elem) throws DestroyedException, ServiceException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity1 : " + elem.getEntity1().getName());
		System.out.println("entity2 : " + elem.getEntity2().getName());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
	public it.polito.dp2.TAMELESS.ass.Relation3Entities createRelation3Entities(Relation3EntitiesName relationName,it.polito.dp2.TAMELESS.ass.Entity entity1,it.polito.dp2.TAMELESS.ass.Entity entity2, it.polito.dp2.TAMELESS.ass.Entity entity3) throws ServiceException, DestroyedException{
			
		Relation3Entities toCreate = of.createRelation3Entities();
		toCreate.setRelationName(relationName);
		toCreate.setEntity1Id(entity1.getSelf());
		toCreate.setEntity2Id(entity2.getSelf());
		toCreate.setEntity3Id(entity3.getSelf());
		
		Response res = target.path("/relationships/relations3Entities")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		
		assertStatusEquals201(res.getStatus());
		
		Relation3Entities created = res.readEntity(Relation3Entities.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		Relation3EntitiesImpl impl = new Relation3EntitiesImpl(client, created.getSelf());
		return impl;
	}
	
	
	public Set<it.polito.dp2.TAMELESS.ass.Relation3Entities> getRelations3Entities(String name) throws ServiceException{
		
		Response res = target.path("/relationships/relations3Entities")
				//.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Relation3Entities> ret = new LinkedHashSet<>();
		
		Relations3Entities list = res.readEntity(Relations3Entities.class);
		
		for(Relations3Entities.Relation3Entities elem : list.getRelation3Entities()){
			
			Relation3Entities toAddElem = of.createRelation3Entities();
			toAddElem.setRelationName(elem.getRelationName());
			toAddElem.setEntity1Id(elem.getEntity1Id());
			toAddElem.setEntity2Id(elem.getEntity2Id());
			toAddElem.setEntity3Id(elem.getEntity3Id());
			toAddElem.setSelf(elem.getSelf());
			
			Relation3EntitiesImpl impl = new Relation3EntitiesImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printRelations3Entities(String name) throws ServiceException, DestroyedException {
		Set<it.polito.dp2.TAMELESS.ass.Relation3Entities> set = mainClient.getRelations3Entities(name);
		System.out.println("Relations of three entities returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Relation3Entities elem : set) {
			printRelation3Entities(elem);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printRelation3Entities(it.polito.dp2.TAMELESS.ass.Relation3Entities elem) throws ServiceException, DestroyedException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity1 : " + elem.getEntity1().getName());
		System.out.println("entity2 : " + elem.getEntity2().getName());
		System.out.println("entity3 : " + elem.getEntity3().getName());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
	public it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat createRelation2Entities1Threat(Relation2Entities1ThreatName relationName,it.polito.dp2.TAMELESS.ass.Entity entity1,it.polito.dp2.TAMELESS.ass.Entity entity2, it.polito.dp2.TAMELESS.ass.Threat threat) throws ServiceException{
	
		
		Relation2Entities1Threat toCreate = of.createRelation2Entities1Threat();
		toCreate.setRelationName(relationName);
		toCreate.setEntity1Id(entity1.getSelf());
		toCreate.setEntity2Id(entity2.getSelf());
		toCreate.setThreatId(threat.getSelf());
		
		Response res = target.path("/relationships/relations2Entities1Threat")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		

		assertStatusEquals201(res.getStatus());
		
		Relation2Entities1Threat created = res.readEntity(Relation2Entities1Threat.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		Relation2Entities1ThreatImpl impl = new Relation2Entities1ThreatImpl(client, created.getSelf());
		return impl;
	}
	
	
	public Set<it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat> getRelations2Entities1Threat(String name) throws ServiceException{
		
		Response res = target.path("/relationships/relations2Entities1Threat")
				//.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat> ret = new LinkedHashSet<>();
		
		Relations2Entities1Threat list = res.readEntity(Relations2Entities1Threat.class);
		
		for(Relations2Entities1Threat.Relation2Entities1Threat elem : list.getRelation2Entities1Threat()){
			
			Relation2Entities1Threat toAddElem = of.createRelation2Entities1Threat();
			toAddElem.setRelationName(elem.getRelationName());
			toAddElem.setEntity1Id(elem.getEntity1Id());
			toAddElem.setEntity2Id(elem.getEntity2Id());
			toAddElem.setThreatId(elem.getThreatId());
			toAddElem.setSelf(elem.getSelf());
			
			//ret.add(toAddElem);
			
			Relation2Entities1ThreatImpl impl = new Relation2Entities1ThreatImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printRelations2Entities1Threat(String name) throws ServiceException, DestroyedException {
		Set<it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat> set = mainClient.getRelations2Entities1Threat(name);
		System.out.println("Relations of two entities and 1 threat returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat elem : set) {
			printRelation2Entities1Threat(elem);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printRelation2Entities1Threat(it.polito.dp2.TAMELESS.ass.Relation2Entities1Threat elem) throws ServiceException, DestroyedException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity1 : " + elem.getEntity1().getName());
		System.out.println("entity2 : " + elem.getEntity2().getName());
		System.out.println("threat : " + elem.getThreat().getName());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
	public it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat createRelation1Entity1Threat(Relation1Entity1ThreatName relationName,it.polito.dp2.TAMELESS.ass.Entity entity1, it.polito.dp2.TAMELESS.ass.Threat threat) throws ServiceException{
		
		
		Relation1Entity1Threat toCreate = of.createRelation1Entity1Threat();
		toCreate.setRelationName(relationName);
		toCreate.setEntity1Id(entity1.getSelf());
		toCreate.setThreatId(threat.getSelf());
		
		Response res = target.path("/relationships/relations1Entity1Threat")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		
		assertStatusEquals201(res.getStatus());
		
		Relation1Entity1Threat created = res.readEntity(Relation1Entity1Threat.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		Relation1Entity1ThreatImpl impl = new Relation1Entity1ThreatImpl(client, created.getSelf());
		return impl;
	}
	
	
	public Set<it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat> getRelations1Entity1Threat(String name) throws ServiceException{
		
		Response res = target.path("/relationships/relations1Entity1Threat")
				//.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat> ret = new LinkedHashSet<>();
		
		Relations1Entity1Threat list = res.readEntity(Relations1Entity1Threat.class);
		
		for(Relations1Entity1Threat.Relation1Entity1Threat elem : list.getRelation1Entity1Threat()){
			
			
			Relation1Entity1Threat toAddElem = of.createRelation1Entity1Threat();
			toAddElem.setRelationName(elem.getRelationName());
			toAddElem.setEntity1Id(elem.getEntity1Id());
			toAddElem.setThreatId(elem.getThreatId());
			toAddElem.setSelf(elem.getSelf());
			
			//ret.add(toAddElem);
			
			Relation1Entity1ThreatImpl impl = new Relation1Entity1ThreatImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printRelations1Entity1Threat(String name) throws ServiceException, DestroyedException {
		Set<it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat> set = mainClient.getRelations1Entity1Threat(name);
		System.out.println("Relations of one entities and 1 threat returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat elem : set) {
			printRelation1Entity1Threat(elem);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/	
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printRelation1Entity1Threat(it.polito.dp2.TAMELESS.ass.Relation1Entity1Threat elem) throws ServiceException, DestroyedException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity1 : " + elem.getEntity1().getName());
		System.out.println("threat : " + elem.getThreat().getName());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
	public it.polito.dp2.TAMELESS.ass.Property1Entity createProperty1Entity(BasicProperty1EntityName propertyName, it.polito.dp2.TAMELESS.ass.Entity entity) throws ServiceException{
		
		
		Property1Entity toCreate = of.createProperty1Entity();
		toCreate.setPropertyName(propertyName);
		toCreate.setEntityId(entity.getSelf());
		
		Response res = target.path("/properties/properties1Entity")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		
		assertStatusEquals201(res.getStatus());
		
		Property1Entity created = res.readEntity(Property1Entity.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		Property1EntityImpl impl = new Property1EntityImpl(client, created.getSelf());
		return impl;
	}
	
	
	public Set<it.polito.dp2.TAMELESS.ass.Property1Entity> getProperties1Entity(String name) throws ServiceException{
		
		Response res = target.path("/properties/properties1Entity")
				//.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Property1Entity> ret = new LinkedHashSet<>();
		
		Properties1Entity list = res.readEntity(Properties1Entity.class);
		
		for(Properties1Entity.Property1Entity elem : list.getProperty1Entity()){
			
			
			Property1Entity toAddElem = of.createProperty1Entity();
			
			toAddElem.setPropertyName(elem.getPropertyName());
			toAddElem.setEntityId(elem.getEntityId());
			toAddElem.setSelf(elem.getSelf());
			
			//ret.add(toAddElem);
			
			Property1EntityImpl impl = new Property1EntityImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printProperties1Entity(String name) throws ServiceException, DestroyedException {
		
		Set<it.polito.dp2.TAMELESS.ass.Property1Entity> set = mainClient.getProperties1Entity(name);
		System.out.println("Properties of one entity returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Property1Entity elem : set) {
			printProperty1Entity(elem);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printProperty1Entity(it.polito.dp2.TAMELESS.ass.Property1Entity elem) throws ServiceException, DestroyedException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity : " + elem.getEntity().getName());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
	public it.polito.dp2.TAMELESS.ass.Property1Entity1Threat createProperty1Entity1Threat(BasicProperty1Entity1ThreatName propertyName, it.polito.dp2.TAMELESS.ass.Entity entity, it.polito.dp2.TAMELESS.ass.Threat threat) throws ServiceException{
		
		
		Property1Entity1Threat toCreate = of.createProperty1Entity1Threat();
		toCreate.setPropertyName(propertyName);
		toCreate.setEntityId(entity.getSelf());
		toCreate.setThreatId(threat.getSelf());
		
		Response res = target.path("/properties/properties1Entity1Threat")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.post(javax.ws.rs.client.Entity.xml(toCreate));
		
		assertStatusEquals201(res.getStatus());
		
		Property1Entity1Threat created = res.readEntity(Property1Entity1Threat.class);
		//System.out.println("createEntity() return : " + created.getName() + " and " + created.getSelf());
		Property1Entity1ThreatImpl impl = new Property1Entity1ThreatImpl(client, created.getSelf());
		return impl;
	}
	
	
	public Set<it.polito.dp2.TAMELESS.ass.Property1Entity1Threat> getProperties1Entity1Threat(String name) throws ServiceException{
		
		Response res = target.path("/properties/properties1Entity1Threat")
				//.queryParam("name", name)
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.Property1Entity1Threat> ret = new LinkedHashSet<>();
		
		Properties1Entity1Threat list = res.readEntity(Properties1Entity1Threat.class);
		
		for(Properties1Entity1Threat.Property1Entity1Threat elem : list.getProperty1Entity1Threat()){
			
			
			Property1Entity1Threat toAddElem = of.createProperty1Entity1Threat();
			
			toAddElem.setPropertyName(elem.getPropertyName());
			toAddElem.setEntityId(elem.getEntityId());
			toAddElem.setThreatId(elem.getThreatId());
			toAddElem.setSelf(elem.getSelf());
			
			//ret.add(toAddElem);
			
			Property1Entity1ThreatImpl impl = new Property1Entity1ThreatImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printProperties1Entity1Threat(String name) throws ServiceException, DestroyedException {
		
		Set<it.polito.dp2.TAMELESS.ass.Property1Entity1Threat> set = mainClient.getProperties1Entity1Threat(name);
		System.out.println("Properties of one entity and 1 threat returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.Property1Entity1Threat elem : set) {
			printProperty1Entity1Threat(elem);
			/*String[] authors = item.getAuthors();
			System.out.print(authors[0]);
			for (int i=1; i<authors.length; i++)
				System.out.print(", "+authors[i]);
			System.out.println(";");
			
			Set<ItemReader> citingItems = item.getCitingItems();
			System.out.println("Cited by "+citingItems.size()+" items:");
			for (ItemReader citing: citingItems) {
				System.out.println("- "+citing.getTitle());
			}*/	
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printProperty1Entity1Threat(it.polito.dp2.TAMELESS.ass.Property1Entity1Threat elem) throws ServiceException, DestroyedException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity : " + elem.getEntity().getName());
		System.out.println("threat : " + elem.getThreat().getName());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
	//GET BY ID section
	
	/*public Entity getEntity(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("entities")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		return res.readEntity(Entity.class);
	}
	
	public Threat getThreat(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("threats")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		
		return res.readEntity(Threat.class);
	}
	
	public Relation2Entities getRelation2Entities(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("relationships/relations2Entities")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		return res.readEntity(Relation2Entities.class);
	}
	
	public Relation3Entities getRelation3Entities(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("relationships/relations3Entities")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		return res.readEntity(Relation3Entities.class);
	}
	
	public Relation2Entities1Threat getRelation2Entities1Threat(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("relationships/relations2Entities1Threat")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		return res.readEntity(Relation2Entities1Threat.class);
	}
	
	public Relation1Entity1Threat getRelation1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("relationships/relations1Entity1Threat")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		return res.readEntity(Relation1Entity1Threat.class);
	}
	
	public Property1Entity getProperty1Entity(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("properties/properties1Entity")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException(res);
		}
		
		
		return res.readEntity(Property1Entity.class);
	}
	
	public Property1Entity1Threat getProperty1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException{
		
		Response res = target.path("properties/properties1Entity1Threat")
				.path(id.toString())
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		
		return res.readEntity(Property1Entity1Threat.class);
	}*/
	
	//DELETE section
	
	/*public void deleteEntity(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException{
		
		Response res = target.path("entities")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}
	
	public void deleteThreat(BigInteger id) throws ServiceException, NotFoundException , ClientErrorException{
		
		Response res = target.path("threats")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}
	
	public void deleteRelation2Entities(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException{
		
		Response res = target.path("relationships/relations2Entities")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}
	
	public void deleteRelation3Entities(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException{
		
		Response res = target.path("relationships/relations3Entities")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}
	
	public void deleteRelation2Entities1Threat(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException{
		
		Response res = target.path("relationships/relations2Entities1Threat")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}
	
	public void deleteRelation1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException{
		
		Response res = target.path("relationships/relations1Entity1Threat")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}
	
	public void deleteProperty1Entity(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException{
		
		Response res = target.path("properties/properties1Entity")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException(res);
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}
	
	public void deleteProperty1Entity1Threat(BigInteger id) throws ServiceException, NotFoundException, ClientErrorException{
		
		Response res = target.path("properties/properties1Entity1Threat")
				.path(id.toString())
				.request()
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		if(res.getStatus()==404){
			throw new NotFoundException();
		}
		
		if(res.getStatus()==409)
			throw new ClientErrorException(409);
		
	}*/
	
	//PUT section
	
	/*public Entity updateEntity(BigInteger id, String entityName, EntityNature entityType) throws ServiceException,BadRequestException,NotFoundException{
		
		Entity toUpdate = of.createEntity();
		toUpdate.setName(entityName);
		toUpdate.setType(entityType);
		
		Response res = target.path("entities")
				.path(id.toString())
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
		
		
		Entity updated = res.readEntity(Entity.class);
		return updated;
	}
	
	public Threat updateThreat(BigInteger id, String threatName) throws ServiceException,BadRequestException,NotFoundException{
		
		Threat toUpdate = of.createThreat();
		toUpdate.setName(threatName);
		
		Response res = target.path("threats")
				.path(id.toString())
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

		Threat updated = res.readEntity(Threat.class);
		return updated;
	}
	
		
	public Relation2Entities updateRelation2Entities(BigInteger id, Relation2EntitiesName relationName,String entity1Name,String entity2Name) throws ServiceException,BadRequestException,NotFoundException{
		

		
		Relation2Entities toUpdate = of.createRelation2Entities();
		toUpdate.setRelationName(relationName);
		toUpdate.setEntity1Id(entityName_EntityMap.get(entity1Name).getSelf());
		toUpdate.setEntity2Id(entityName_EntityMap.get(entity2Name).getSelf());
		
		Response res = target.path("/relationships/relations2Entities")
				.path(id.toString())
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

		Relation2Entities updated = res.readEntity(Relation2Entities.class);
		return updated;
	}*
	
	public Relation3Entities updateRelation3Entities(BigInteger id, Relation3EntitiesName relationName,String entity1Name,String entity2Name, String entity3Name) throws ServiceException,BadRequestException,NotFoundException{
		
		Relation3Entities toUpdate = of.createRelation3Entities();
		toUpdate.setRelationName(relationName);
		toUpdate.setEntity1Id(entityName_EntityMap.get(entity1Name).getSelf());
		toUpdate.setEntity2Id(entityName_EntityMap.get(entity2Name).getSelf());
		toUpdate.setEntity3Id(entityName_EntityMap.get(entity3Name).getSelf());
		
		Response res = target.path("/relationships/relations3Entities")
				.path(id.toString())
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

		Relation3Entities updated = res.readEntity(Relation3Entities.class);
		return updated;
	}
	
		
	public Relation2Entities1Threat updateRelation2Entities1Threat(BigInteger id, Relation2Entities1ThreatName relationName,String entity1Name,String entity2Name, String threatName) throws ServiceException,BadRequestException,NotFoundException{
		

		
		Relation2Entities1Threat toUpdate = of.createRelation2Entities1Threat();
		toUpdate.setRelationName(relationName);
		toUpdate.setEntity1Id(entityName_EntityMap.get(entity1Name).getSelf());
		toUpdate.setEntity2Id(entityName_EntityMap.get(entity2Name).getSelf());
		toUpdate.setThreatId(threatName_ThreatMap.get(threatName).getSelf());
		
		Response res = target.path("/relationships/relations2Entities1Threat")
				.path(id.toString())
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

		Relation2Entities1Threat updated = res.readEntity(Relation2Entities1Threat.class);
		return updated;
	}
	
		
	public Relation1Entity1Threat updateRelation1Entity1Threat(BigInteger id, Relation1Entity1ThreatName relationName,String entity1Name, String threatName) throws ServiceException,BadRequestException,NotFoundException{
		
		
		Relation1Entity1Threat toUpdate = of.createRelation1Entity1Threat();
		toUpdate.setRelationName(relationName);
		toUpdate.setEntity1Id(entityName_EntityMap.get(entity1Name).getSelf());
		toUpdate.setThreatId(threatName_ThreatMap.get(threatName).getSelf());
		
		Response res = target.path("/relationships/relations1Entity1Threat")
				.path(id.toString())
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

		Relation1Entity1Threat updated = res.readEntity(Relation1Entity1Threat.class);
		return updated;
	}
	
		
	public Property1Entity updateProperty1Entity(BigInteger id, BasicProperty1EntityName propertyName, String entityName) throws ServiceException,BadRequestException,NotFoundException{
	
		
		Property1Entity toUpdate = of.createProperty1Entity();
		toUpdate.setPropertyName(propertyName);
		toUpdate.setEntityId(entityName_EntityMap.get(entityName).getSelf());
		
		Response res = target.path("/properties/properties1Entity")
				.path(id.toString())
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

		Property1Entity updated = res.readEntity(Property1Entity.class);
		return updated;
	}
	
		
	public Property1Entity1Threat updateProperty1Entity1Threat(BigInteger id, BasicProperty1Entity1ThreatName propertyName, String entityName, String threatName) throws ServiceException,BadRequestException,NotFoundException{
		
		
		
		Property1Entity1Threat toUpdate = of.createProperty1Entity1Threat();
		toUpdate.setPropertyName(propertyName);
		toUpdate.setEntityId(entityName_EntityMap.get(entityName).getSelf());
		toUpdate.setThreatId(threatName_ThreatMap.get(threatName).getSelf());
		
		Response res = target.path("/properties/properties1Entity1Threat")
				.path(id.toString())
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

		Property1Entity1Threat updated = res.readEntity(Property1Entity1Threat.class);
		return updated;
	}*/
	
	public Boolean computeResults() throws ServiceException{
		
		Response res = target.path("/results")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.put(javax.ws.rs.client.Entity.xml(""));
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}

		Boolean resultsCorrectlyComputed = res.getStatus() == 204 || res.getStatus() == 200;
		return resultsCorrectlyComputed;
	}
	
	public void deleteAllDerivedProperties() throws ServiceException{
		
		Response res = target.path("/results")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.delete();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
	}
	
	public Set<it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity> getDerivedProperties1Entity(DerivedProperty1EntityName name, it.polito.dp2.TAMELESS.ass.Entity e) throws ServiceException, NotAcceptableNameException{
		

		WebTarget wt = null;
		
		if(name.equals(DerivedProperty1EntityName.CANBE_MALFUNCTIONING) == true)
			wt = target.path("/results/canbeMalfunctioning");
		else if(name.equals(DerivedProperty1EntityName.CANBE_RESTORED) == true)
			wt = target.path("/results/canbeRestored");
		else if(name.equals(DerivedProperty1EntityName.CANBE_FIXED) == true)
			wt = target.path("/results/canbeFixed");
		else
			throw new NotAcceptableNameException();
		
		if(e!=null){
			wt =  wt.queryParam("entitySelf", e.getSelf());
		}
		
		Response res = wt
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		
		Set<it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity> ret = new LinkedHashSet<>();
		
		DerivedProperties1Entity list = res.readEntity(DerivedProperties1Entity.class);
		
		for(DerivedProperties1Entity.DerivedProperty1Entity elem : list.getDerivedProperty1Entity()){
			
			
			DerivedProperty1Entity toAddElem = of.createDerivedProperty1Entity();
			
			toAddElem.setPropertyName(elem.getPropertyName());
			toAddElem.setEntityId(elem.getEntityId());
			toAddElem.setDescription(elem.getDescription());
			toAddElem.setSelf(elem.getSelf());
			
			//ret.add(toAddElem);
			DerivedProperty1EntityImpl impl = new DerivedProperty1EntityImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	
	private static void printDerivedProperties1Entity(DerivedProperty1EntityName name, it.polito.dp2.TAMELESS.ass.Entity e) throws ServiceException, DestroyedException, NotAcceptableNameException{
		
		Set<it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity> set = mainClient.getDerivedProperties1Entity(name, e);
		System.out.println("Derived Properties " + name + " returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity elem : set) {
			printDerivedProperty1Entity(elem);
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printDerivedProperty1Entity(it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity elem) throws ServiceException, DestroyedException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity : " + elem.getEntity().getName());
		System.out.println("description : " + elem.getDescription());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
	public Set<it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity1Threat> getDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName name,it.polito.dp2.TAMELESS.ass.Entity e, it.polito.dp2.TAMELESS.ass.Threat t) throws ServiceException, NotAcceptableNameException{
		

		WebTarget wt = null;
		
		if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED) == true)
			wt = target.path("/results/canbeCompromised");
		else if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE) == true)
			wt = target.path("/results/canbeVulnerable");
		else if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_DETECTED) == true)
			wt = target.path("/results/canbeDetected");
		else
			throw new NotAcceptableNameException();
		
		if(e!=null){
			wt =  wt.queryParam("entitySelf", e.getSelf());
		}
		
		if(t!=null){
			wt =  wt.queryParam("threatSelf", t.getSelf());
		}

		Response res = wt
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get();
		
		if(res.getStatus()==500){
			throw new ServiceException();
		}
		
		Set<it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity1Threat> ret = new LinkedHashSet<>();
		
		DerivedProperties1Entity1Threat list = res.readEntity(DerivedProperties1Entity1Threat.class);
		
		for(DerivedProperties1Entity1Threat.DerivedProperty1Entity1Threat elem : list.getDerivedProperty1Entity1Threat()){
			
			
			DerivedProperty1Entity1Threat toAddElem = of.createDerivedProperty1Entity1Threat();
			
			toAddElem.setPropertyName(elem.getPropertyName());
			toAddElem.setEntityId(elem.getEntityId());
			toAddElem.setThreatId(elem.getThreatId());
			toAddElem.setDescription(elem.getDescription());
			toAddElem.setSelf(elem.getSelf());
			
			//ret.add(toAddElem);
			DerivedProperty1Entity1ThreatImpl impl = new DerivedProperty1Entity1ThreatImpl(client, elem.getSelf());
			ret.add(impl);
		}
		
		return ret;
	}
	
	private static void printDerivedProperties1Entity1Threat(DerivedProperty1Entity1ThreatName name, it.polito.dp2.TAMELESS.ass.Entity e, it.polito.dp2.TAMELESS.ass.Threat t) throws ServiceException, NotAcceptableNameException, DestroyedException{
		
		Set<it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity1Threat> set = mainClient.getDerivedProperties1Entity1Threat(name, e, t);
		System.out.println("Derived Properties " + name + " returned: " + set.size());
		
		// For each entity print related data
		for (it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity1Threat elem : set) {
			printDerivedProperty1Entity1Threat(elem);
			printLine('-');

		}
		printBlankLine();
	}
	
	private static void printDerivedProperty1Entity1Threat(it.polito.dp2.TAMELESS.ass.DerivedProperty1Entity1Threat elem) throws ServiceException, DestroyedException{
		System.out.println("Name : " + elem.getName());
		System.out.println("entity : " + elem.getEntity().getName());
		System.out.println("threat : " + elem.getThreat().getName());
		System.out.println("description : " + elem.getDescription());
		if (elem.getSelf()!=null)
			System.out.println("Self : "+ elem.getSelf());
	}
	
}



