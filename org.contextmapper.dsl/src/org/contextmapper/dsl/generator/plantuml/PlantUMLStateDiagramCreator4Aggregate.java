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
package org.contextmapper.dsl.generator.plantuml;

import java.util.Set;
import java.util.stream.Collectors;

import org.contextmapper.dsl.contextMappingDSL.Aggregate;
import org.contextmapper.tactic.dsl.tacticdsl.StateTransition;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Sets;

public class PlantUMLStateDiagramCreator4Aggregate extends AbstractPlantUMLStateDiagramCreator<Aggregate> implements PlantUMLDiagramCreator<Aggregate> {

	@Override
	protected void printDiagramContent(Aggregate aggregate) {
		for (String state : collectStates(aggregate)) {
			printState(state);
		}
		for (StateTransition transition : EcoreUtil2.eAllOfType(aggregate, StateTransition.class))
			printTransition(transition);
	}

	private Set<String> collectStates(Aggregate aggregate) {
		Set<String> states = Sets.newHashSet();
		for (StateTransition transition : EcoreUtil2.eAllOfType(aggregate, StateTransition.class)) {
			if (transition.getFrom() != null && !transition.getFrom().isEmpty())
				states.addAll(transition.getFrom().stream().map(s -> s.getName()).collect(Collectors.toSet()));
			states.addAll(transition.getTarget().getTo().stream().map(s -> s.getName()).collect(Collectors.toSet()));
		}
		return states;
	}

}
