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
package org.contextmapper.dsl.quickfixes;

import org.contextmapper.dsl.contextMappingDSL.Feature;
import org.contextmapper.dsl.contextMappingDSL.UserRequirement;
import org.contextmapper.dsl.exception.ContextMapperApplicationException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;

public class SplitStoryByVerb implements CMLQuickFix<Feature> {

	@Override
	public void applyQuickfix(Feature contextObject) {
		if (!(contextObject.eContainer() instanceof UserRequirement))
			throw new ContextMapperApplicationException("Cannot apply quickfix, as the provided feature is not embedded into to a user story or use case.");

		UserRequirement story = (UserRequirement) contextObject.eContainer();
		Feature newFeature = EcoreUtil2.copy(contextObject);
		newFeature.setVerb("\"{verb}\"");
		story.getFeatures().add(newFeature);
	}

	@Override
	public void applyQuickfix2EObject(EObject contextObject) {
		if (contextObject instanceof Feature)
			applyQuickfix((Feature) contextObject);
		else
			throw new ContextMapperApplicationException("Cannot apply quickfix, as the given context object is no user story feature.");
	}

	@Override
	public String getName() {
		return "Split Story by Verb/Operation";
	}

	@Override
	public String getDescription() {
		return "Splits a feature in a user story or use case by the verb/operation.";
	}

}
