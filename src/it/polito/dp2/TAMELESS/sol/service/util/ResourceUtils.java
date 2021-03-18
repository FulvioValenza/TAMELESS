package it.polito.dp2.TAMELESS.sol.service.util;

import java.math.BigInteger;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;

import it.polito.dp2.TAMELESS.sol.service.jaxb.*;

public class ResourceUtils {

	UriBuilder base;
	UriBuilder entities;
	UriBuilder threats;
	UriBuilder relation2Entities;
	UriBuilder relation3Entities;
	UriBuilder relation2Entities1Threat;
	UriBuilder relation1Entity1Threat;
	UriBuilder property1Entity;
	UriBuilder property1Entity1Threat;
	UriBuilder derivedProperty1Entity1ThreatCompromised;
	UriBuilder derivedProperty1EntityMalfunctioning;
	UriBuilder derivedProperty1Entity1ThreatVulnerable;
	UriBuilder derivedProperty1Entity1ThreatDetected;
	UriBuilder derivedProperty1EntityRestored;
	UriBuilder derivedProperty1EntityFixed;

	public ResourceUtils(UriBuilder base) {
		this.base = base;
		this.entities = base.clone().path("system/entities");
		this.threats = base.clone().path("system/threats");
		this.relation2Entities = base.clone().path("system/relationships/relations2Entities");
		this.relation3Entities = base.clone().path("system/relationships/relations3Entities");
		this.relation2Entities1Threat = base.clone().path("system/relationships/relations2Entities1Threat");
		this.relation1Entity1Threat = base.clone().path("system/relationships/relations1Entity1Threat");
		this.property1Entity = base.clone().path("system/properties/properties1Entity");
		this.property1Entity1Threat = base.clone().path("system/properties/properties1Entity1Threat");
		this.derivedProperty1Entity1ThreatCompromised = base.clone().path("system/results/canbeCompromised");
		this.derivedProperty1EntityMalfunctioning = base.clone().path("system/results/canbeMalfunctioning");
		this.derivedProperty1Entity1ThreatVulnerable = base.clone().path("system/results/canbeVulnerable");
		this.derivedProperty1Entity1ThreatDetected = base.clone().path("system/results/canbeDetected");
		this.derivedProperty1EntityRestored = base.clone().path("system/results/canbeRestored");
		this.derivedProperty1EntityFixed = base.clone().path("system/results/canbeFixed");
	}
	
	public void completeEntity(Entity e, BigInteger id) {
		UriBuilder selfBuilder = entities.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	e.setSelf(self.toString());
    	
    	URI relations = selfBuilder.clone().path("relations").build();
    	e.setRelations(relations.toString());
    	
    	URI properties = selfBuilder.clone().path("properties").build();
    	e.setProperties(properties.toString());
	}

	public void completeThreat(Threat t, BigInteger id) {
		UriBuilder selfBuilder = threats.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	t.setSelf(self.toString());
    	
    	URI relations = selfBuilder.clone().path("relations").build();
    	t.setRelations(relations.toString());
    	
    	URI properties = selfBuilder.clone().path("properties").build();
    	t.setProperties(properties.toString());
	}
	
	public void completeRelation2Entities(Relation2Entities r2e, BigInteger id) {
		UriBuilder selfBuilder = relation2Entities.clone().path(id.toString());
    	URI self = selfBuilder.build();
		UriBuilder e1Builder = entities.clone().path(r2e.getEntity1Id());
    	URI e1 = e1Builder.build();
		UriBuilder e2Builder = entities.clone().path(r2e.getEntity2Id());
    	URI e2 = e2Builder.build();
    	r2e.setSelf(self.toString());
    	r2e.setEntity1Id(e1.toString());
    	r2e.setEntity2Id(e2.toString());

	}
	
	public void completeRelation3Entities(Relation3Entities r3e, BigInteger id) {
		UriBuilder selfBuilder = relation3Entities.clone().path(id.toString());
    	URI self = selfBuilder.build();
		UriBuilder e1Builder = entities.clone().path(r3e.getEntity1Id());
    	URI e1 = e1Builder.build();
		UriBuilder e2Builder = entities.clone().path(r3e.getEntity2Id());
    	URI e2 = e2Builder.build();
		UriBuilder e3Builder = entities.clone().path(r3e.getEntity3Id());
    	URI e3 = e3Builder.build();
    	
    	r3e.setSelf(self.toString());
    	r3e.setEntity1Id(e1.toString());
    	r3e.setEntity2Id(e2.toString());
    	r3e.setEntity3Id(e3.toString());
    	
    	
	}
	
	public void completeRelation2Entities1Threat(Relation2Entities1Threat r2e1t, BigInteger id) {
		UriBuilder selfBuilder = relation2Entities1Threat.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	
		UriBuilder e1Builder = entities.clone().path(r2e1t.getEntity1Id());
    	URI e1 = e1Builder.build();
		UriBuilder e2Builder = entities.clone().path(r2e1t.getEntity2Id());
    	URI e2 = e2Builder.build();
		UriBuilder tBuilder = threats.clone().path(r2e1t.getThreatId());
    	URI t = tBuilder.build();
    	r2e1t.setSelf(self.toString());
    	r2e1t.setEntity1Id(e1.toString());
    	r2e1t.setEntity2Id(e2.toString());
    	r2e1t.setThreatId(t.toString());
    	
    	
	}
	
	public void completeRelation1Entity1Threat(Relation1Entity1Threat r1e1t, BigInteger id) {
		UriBuilder selfBuilder = relation1Entity1Threat.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	
		UriBuilder e1Builder = entities.clone().path(r1e1t.getEntity1Id());
    	URI e1 = e1Builder.build();
		UriBuilder tBuilder = threats.clone().path(r1e1t.getThreatId());
    	URI t = tBuilder.build();
    	
    	r1e1t.setSelf(self.toString());
    	r1e1t.setEntity1Id(e1.toString());
    	r1e1t.setThreatId(t.toString());
    
    	
    	
	}
	
	public void completeProperty1Entity(Property1Entity p1e, BigInteger id) {
		UriBuilder selfBuilder = property1Entity.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	
		UriBuilder eBuilder = entities.clone().path(p1e.getEntityId());
    	URI e = eBuilder.build();

    	p1e.setSelf(self.toString());
    	p1e.setEntityId(e.toString());
    	
    	
	}
	
	public void completeProperty1Entity1Threat(Property1Entity1Threat p1e1t, BigInteger id) {
		UriBuilder selfBuilder = property1Entity1Threat.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	
		UriBuilder eBuilder = entities.clone().path(p1e1t.getEntityId());
    	URI e = eBuilder.build();
		UriBuilder tBuilder = threats.clone().path(p1e1t.getThreatId());
    	URI t = tBuilder.build();
    	
    	p1e1t.setSelf(self.toString());
    	p1e1t.setEntityId(e.toString());
    	p1e1t.setThreatId(t.toString());
    	
    	
	}
	
	public void completeDerivedProperty1Entity1Threat(DerivedProperty1Entity1Threat dp1e1t, BigInteger id, DerivedProperty1Entity1ThreatName name) {
		
		UriBuilder selfBuilder = null;
		if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_COMPROMISED))
			selfBuilder = derivedProperty1Entity1ThreatCompromised.clone().path(id.toString());
		else if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_VULNERABLE))
			selfBuilder = derivedProperty1Entity1ThreatVulnerable.clone().path(id.toString());
		else if(name.equals(DerivedProperty1Entity1ThreatName.CANBE_DETECTED))
			selfBuilder = derivedProperty1Entity1ThreatDetected.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	
		UriBuilder eBuilder = entities.clone().path(dp1e1t.getEntityId());
    	URI e = eBuilder.build();
		UriBuilder tBuilder = threats.clone().path(dp1e1t.getThreatId());
    	URI t = tBuilder.build();
    	
    	dp1e1t.setSelf(self.toString());
    	dp1e1t.setEntityId(e.toString());
    	dp1e1t.setThreatId(t.toString());
    	
    	
	}
	
	public void completeDerivedProperty1Entity(DerivedProperty1Entity dp1e, BigInteger id, DerivedProperty1EntityName name){
		
		UriBuilder selfBuilder = null;
		if(name.equals(DerivedProperty1EntityName.CANBE_MALFUNCTIONING))
			selfBuilder = derivedProperty1EntityMalfunctioning.clone().path(id.toString());
		else if(name.equals(DerivedProperty1EntityName.CANBE_RESTORED))
			selfBuilder = derivedProperty1EntityRestored.clone().path(id.toString());
		else if(name.equals(DerivedProperty1EntityName.CANBE_FIXED))
			selfBuilder = derivedProperty1EntityFixed.clone().path(id.toString());
    	URI self = selfBuilder.build();
    	
		UriBuilder eBuilder = entities.clone().path(dp1e.getEntityId());
    	URI e = eBuilder.build();
    	
    	dp1e.setSelf(self.toString());
    	dp1e.setEntityId(e.toString());
    	
    	
	}
	
	
}



