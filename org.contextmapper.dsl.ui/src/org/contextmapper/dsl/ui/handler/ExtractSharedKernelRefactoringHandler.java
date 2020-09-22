/*
 * Copyright 2019 The Context Mapper Project Team
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
package org.contextmapper.dsl.ui.handler;

import org.contextmapper.dsl.cml.CMLResource;
import org.contextmapper.dsl.contextMappingDSL.SharedKernel;
import org.contextmapper.dsl.refactoring.ExtractSharedKernelRefactoring;
import org.contextmapper.dsl.refactoring.SemanticCMLRefactoring;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.ecore.EObject;

public class ExtractSharedKernelRefactoringHandler extends AbstractRefactoringHandler {

	@Override
	protected void executeRefactoring(CMLResource resource, ExecutionEvent event) {
		SharedKernel sk = (SharedKernel) getSelectedElement();
		SemanticCMLRefactoring ar = new ExtractSharedKernelRefactoring(sk.getParticipant1().getName(), sk.getParticipant2().getName());
		ar.refactor(resource);
		ar.persistChanges(serializer);
	}

	@Override
	public boolean isEnabled() {
		EObject obj = getSelectedElement();

		if (obj == null || !super.isEnabled())
			return false;

		// only allowed on Shared Kernel relationship
		if (!(obj instanceof SharedKernel))
			return false;

		return true;
	}

}
