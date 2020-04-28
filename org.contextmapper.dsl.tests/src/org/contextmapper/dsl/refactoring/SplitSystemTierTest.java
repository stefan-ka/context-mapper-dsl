/*
 * Copyright 2020 The Context Mapper Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.contextmapper.dsl.refactoring;

import static org.contextmapper.dsl.refactoring.ContextSplittingIntegrationType.ACL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.contextmapper.dsl.cml.CMLResourceContainer;
import org.contextmapper.dsl.contextMappingDSL.BoundedContext;
import org.contextmapper.dsl.contextMappingDSL.ContextMappingModel;
import org.contextmapper.dsl.contextMappingDSL.DownstreamRole;
import org.contextmapper.dsl.contextMappingDSL.SculptorModule;
import org.contextmapper.dsl.contextMappingDSL.SymmetricRelationship;
import org.contextmapper.dsl.contextMappingDSL.UpstreamDownstreamRelationship;
import org.contextmapper.dsl.contextMappingDSL.UpstreamRole;
import org.contextmapper.dsl.refactoring.SplitSystemTier.SplitBoundedContextRelationshipType;
import org.contextmapper.dsl.refactoring.exception.RefactoringInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class SplitSystemTierTest extends AbstractRefactoringTest {

	@ParameterizedTest
	@ValueSource(strings = { "SomeContextThatDoesNotExist" })
	@NullSource
	public void canCheckIfBoundedContextNameExists(String inputBoundedContextName) throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-1-input.cml");
		SplitSystemTier ar = new SplitSystemTier(inputBoundedContextName, "NewName4ExistingTier", "NewTierName");

		// when, then
		assertThrows(RefactoringInputException.class, () -> {
			ar.doRefactor(input);
		});
	}

	@Test
	public void canCheckThatInputBCIsSystem() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-2-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");

		// when, then
		assertThrows(RefactoringInputException.class, () -> {
			ar.doRefactor(input);
		});
	}

	@Test
	public void canCheckThatName4ExistingContextNotAlreadyExists() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-3-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");

		// when, then
		assertThrows(RefactoringInputException.class, () -> {
			ar.doRefactor(input);
		});
	}

	@Test
	public void canCheckThatName4NewTierNotAlreadyExists() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-4-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");

		// when, then
		assertThrows(RefactoringInputException.class, () -> {
			ar.doRefactor(input);
		});
	}

	@Test
	public void canSplitTier() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-1-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");

		// when
		ar.doRefactor(input);

		// then
		ContextMappingModel model = reloadResource(input).getContextMappingModel();
		assertEquals(2, model.getBoundedContexts().size());
		Set<String> contextNames = model.getBoundedContexts().stream().map(bc -> bc.getName()).collect(Collectors.toSet());
		assertTrue(contextNames.contains("TestBackendLogic"));
		assertTrue(contextNames.contains("TestBackendDatabase"));
	}

	@Test
	public void canCreateUpstreamDownstreamRelationship() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-1-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");

		// when
		ar.doRefactor(input);

		// then
		ContextMappingModel model = reloadResource(input).getContextMappingModel();
		assertNotNull(model.getMap());
		assertEquals(1, model.getMap().getRelationships().size());
		UpstreamDownstreamRelationship relationship = (UpstreamDownstreamRelationship) model.getMap().getRelationships().get(0);
		assertEquals("TestBackendDatabase", relationship.getUpstream().getName());
		assertEquals("TestBackendLogic", relationship.getDownstream().getName());
		assertTrue(relationship.getUpstreamRoles().contains(UpstreamRole.PUBLISHED_LANGUAGE));
		assertTrue(relationship.getDownstreamRoles().contains(DownstreamRole.CONFORMIST));
	}

	@Test
	public void canSwitchUpstreamDownstreamRoleInNewRelationship() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-5-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");
		ar.setRelationshipType(SplitBoundedContextRelationshipType.EXISTING_CONTEXT_BECOMES_UPSTREAM);

		// when
		ar.doRefactor(input);

		// then
		ContextMappingModel model = reloadResource(input).getContextMappingModel();
		assertNotNull(model.getMap());
		assertEquals(1, model.getMap().getRelationships().size());
		UpstreamDownstreamRelationship relationship = (UpstreamDownstreamRelationship) model.getMap().getRelationships().get(0);
		assertEquals("TestBackendLogic", relationship.getUpstream().getName());
		assertEquals("TestBackendDatabase", relationship.getDownstream().getName());
		assertTrue(relationship.getUpstreamRoles().contains(UpstreamRole.PUBLISHED_LANGUAGE));
		assertTrue(relationship.getDownstreamRoles().contains(DownstreamRole.CONFORMIST));
	}

	@Test
	public void canSwitchIntegrationTypeToACL() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-5-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");
		ar.setIntegrationType(ACL);

		// when
		ar.doRefactor(input);

		// then
		ContextMappingModel model = reloadResource(input).getContextMappingModel();
		assertNotNull(model.getMap());
		assertEquals(1, model.getMap().getRelationships().size());
		UpstreamDownstreamRelationship relationship = (UpstreamDownstreamRelationship) model.getMap().getRelationships().get(0);
		assertEquals("TestBackendDatabase", relationship.getUpstream().getName());
		assertEquals("TestBackendLogic", relationship.getDownstream().getName());
		assertTrue(relationship.getUpstreamRoles().contains(UpstreamRole.PUBLISHED_LANGUAGE));
		assertTrue(relationship.getDownstreamRoles().contains(DownstreamRole.ANTICORRUPTION_LAYER));
	}

	@Test
	public void canCopyDomainModel() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-6-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");
		ar.copyDomainModel(true);

		// when
		ar.doRefactor(input);

		// then
		ContextMappingModel model = reloadResource(input).getContextMappingModel();
		BoundedContext copiedBC = model.getBoundedContexts().stream().filter(bc -> bc.getName().equals("TestBackendDatabase")).findFirst().get();
		assertNotNull(copiedBC);
		assertEquals(1, copiedBC.getAggregates().size());
		assertEquals("TestAggregate_TestBackendDatabase", copiedBC.getAggregates().get(0).getName());
		assertEquals(1, copiedBC.getModules().size());
		assertEquals("TestModule_TestBackendDatabase", copiedBC.getModules().get(0).getName());
		assertEquals(1, copiedBC.getAggregates().get(0).getDomainObjects().size());
		assertEquals("TestEntity_TestBackendDatabase", copiedBC.getAggregates().get(0).getDomainObjects().get(0).getName());
		SculptorModule module = copiedBC.getModules().get(0);
		assertEquals(1, module.getAggregates().size());
		assertEquals(1, module.getAggregates().get(0).getDomainObjects().size());
		assertEquals("TestAggregateInModule_TestBackendDatabase", module.getAggregates().get(0).getName());
		assertEquals("TestEntityInModule_TestBackendDatabase", module.getAggregates().get(0).getDomainObjects().get(0).getName());
	}

	@Test
	public void canDefineImplementationTechnologies() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-6-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");
		ar.setNewTierImplementationTechnology("Oracle DB");
		ar.setNewRelationshipImplementationTechnology("JDBC");

		// when
		ar.doRefactor(input);

		// then
		ContextMappingModel model = reloadResource(input).getContextMappingModel();
		BoundedContext newTier = model.getBoundedContexts().stream().filter(bc -> bc.getName().equals("TestBackendDatabase")).findFirst().get();
		UpstreamDownstreamRelationship newRelationship = (UpstreamDownstreamRelationship) model.getMap().getRelationships().get(0);
		assertEquals("Oracle DB", newTier.getImplementationTechnology());
		assertEquals("JDBC", newRelationship.getImplementationTechnology());
	}

	@Test
	public void canGetRelationshipTypeLabel() {
		// given
		SplitBoundedContextRelationshipType upstream = SplitBoundedContextRelationshipType.EXISTING_CONTEXT_BECOMES_UPSTREAM;
		SplitBoundedContextRelationshipType downstream = SplitBoundedContextRelationshipType.EXISTING_CONTEXT_BECOMES_DOWNSTREAM;

		// when
		String upstreamLabel = upstream.getLabel();
		String downstreamLabel = downstream.getLabel();

		// then
		assertEquals("Upstream", upstreamLabel);
		assertEquals("Downstream", downstreamLabel);
	}

	@ParameterizedTest
	@CsvSource({ "Upstream, EXISTING_CONTEXT_BECOMES_UPSTREAM", "Downstream, EXISTING_CONTEXT_BECOMES_DOWNSTREAM" })
	public void canGetRelationshipTypeByLabel(String inputLabel, String expectedType) {
		// given
		String label = inputLabel;

		// when
		SplitBoundedContextRelationshipType type = SplitBoundedContextRelationshipType.byLabel(label);

		// then
		assertEquals(SplitBoundedContextRelationshipType.valueOf(expectedType), type);
	}

	@Test
	public void relationshipTypeCanHandleNullValue() {
		// given
		String label = null;

		// when
		SplitBoundedContextRelationshipType type = SplitBoundedContextRelationshipType.byLabel(label);

		// then
		assertEquals(SplitBoundedContextRelationshipType.EXISTING_CONTEXT_BECOMES_UPSTREAM, type);
	}

	@Test
	public void canRenameContextInRelationships() throws IOException {
		// given
		CMLResourceContainer input = getResourceCopyOfTestCML("split-system-tier-test-7-input.cml");
		SplitSystemTier ar = new SplitSystemTier("TestBackend", "TestBackendLogic", "TestBackendDatabase");

		// when
		ar.doRefactor(input);

		// then
		ContextMappingModel model = reloadResource(input).getContextMappingModel();
		assertEquals(3, model.getBoundedContexts().size());
		assertTrue(getBoundedContextNames(model.getBoundedContexts()).contains("TestBackendLogic"));
		assertFalse(getBoundedContextNames(model.getBoundedContexts()).contains("TestBackend"));

		Set<String> namesOnContextMap = getBoundedContextNames(model.getMap().getBoundedContexts());
		assertTrue(namesOnContextMap.contains("TestBackendLogic"));
		assertFalse(namesOnContextMap.contains("TestBackend"));

		UpstreamDownstreamRelationship upDownRel1 = model.getMap().getRelationships().stream().filter(r -> r.getName().equals("upDownTestRel1"))
				.map(r -> (UpstreamDownstreamRelationship) r).findFirst().get();
		UpstreamDownstreamRelationship upDownRel2 = model.getMap().getRelationships().stream().filter(r -> r.getName().equals("upDownTestRel2"))
				.map(r -> (UpstreamDownstreamRelationship) r).findFirst().get();
		SymmetricRelationship symRel1 = model.getMap().getRelationships().stream().filter(r -> r.getName().equals("symTestRel1")).map(r -> (SymmetricRelationship) r).findFirst()
				.get();
		SymmetricRelationship symRel2 = model.getMap().getRelationships().stream().filter(r -> r.getName().equals("symTestRel2")).map(r -> (SymmetricRelationship) r).findFirst()
				.get();
		assertEquals("TestBackendLogic", upDownRel1.getUpstream().getName());
		assertEquals("TestBackendLogic", upDownRel2.getDownstream().getName());
		assertEquals("TestBackendLogic", symRel1.getParticipant1().getName());
		assertEquals("TestBackendLogic", symRel2.getParticipant2().getName());
	}

	private Set<String> getBoundedContextNames(List<BoundedContext> contexts) {
		return contexts.stream().map(bc -> bc.getName()).collect(Collectors.toSet());
	}

}
