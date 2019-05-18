/*
 * Copyright 2018 The Context Mapper Project Team
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

import java.util.stream.Collectors;

import org.contextmapper.dsl.contextMappingDSL.BoundedContext;
import org.contextmapper.dsl.refactoring.MergeBoundedContextsRefactoring;
import org.contextmapper.dsl.ui.handler.wizard.MergeBoundedContextsContext;
import org.contextmapper.dsl.ui.handler.wizard.MergeBoundedContextsRefactoringWizard;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class MergeBoundedContextsRefactoringHandler extends AbstractRefactoringWithUserInputHandler {

	@Override
	public boolean isEnabled() {
		EObject obj = getSelectedElement();

		if (obj == null || !super.isEnabled())
			return false;

		// only allowed on bounded contexts
		if (!(obj instanceof BoundedContext))
			return false;

		return true;
	}

	@Override
	protected void executeRefactoring(Resource resource, ExecutionEvent event) {
		BoundedContext bc = (BoundedContext) getSelectedElement();

		MergeBoundedContextsContext refactoringContext = new MergeBoundedContextsContext(bc.getName(),
				getCurrentContextMappingModel().getBoundedContexts().stream().map(b -> b.getName()).collect(Collectors.toList()));

		new WizardDialog(HandlerUtil.getActiveShell(event), new MergeBoundedContextsRefactoringWizard(refactoringContext, executionContext -> {
			return finishRefactoring(new MergeBoundedContextsRefactoring(executionContext.getSelectedBoundedContext1(), executionContext.getSelectedBoundedContext2()), resource,
					event);
		})).open();
	}

}
