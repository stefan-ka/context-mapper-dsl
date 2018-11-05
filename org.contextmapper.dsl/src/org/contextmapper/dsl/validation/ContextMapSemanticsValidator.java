package org.contextmapper.dsl.validation;

import static org.contextmapper.dsl.contextMappingDSL.BoundedContextType.TEAM;
import static org.contextmapper.dsl.contextMappingDSL.ContextMapType.ORGANIZATIONAL;
import static org.contextmapper.dsl.contextMappingDSL.ContextMapType.SYSTEM_LANDSCAPE;
import static org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLPackage.Literals.CONTEXT_MAP__BOUNDED_CONTEXTS;
import static org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLPackage.Literals.SYMMETRIC_RELATIONSHIP__PARTICIPANT1;
import static org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLPackage.Literals.SYMMETRIC_RELATIONSHIP__PARTICIPANT2;
import static org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLPackage.Literals.UPSTREAM_DOWNSTREAM_RELATIONSHIP__DOWNSTREAM;
import static org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLPackage.Literals.UPSTREAM_DOWNSTREAM_RELATIONSHIP__UPSTREAM;
import static org.contextmapper.dsl.validation.ValidationMessages.ORGANIZATIONAL_MAP_CONTEXT_IS_NOT_TYPE_TEAM;
import static org.contextmapper.dsl.validation.ValidationMessages.RELATIONSHIP_CONTEXT_NOT_ON_MAP_ERROR_MESSAGE;
import static org.contextmapper.dsl.validation.ValidationMessages.SYSTEM_LANDSCAPE_MAP_CONTAINS_TEAM;

import org.contextmapper.dsl.contextMappingDSL.BoundedContext;
import org.contextmapper.dsl.contextMappingDSL.ContextMap;
import org.contextmapper.dsl.contextMappingDSL.Relationship;
import org.contextmapper.dsl.contextMappingDSL.SymmetricRelationship;
import org.contextmapper.dsl.contextMappingDSL.UpstreamDownstreamRelationship;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

public class ContextMapSemanticsValidator extends AbstractDeclarativeValidator {

	@Override
	public void register(EValidatorRegistrar registrar) {
		// not needed for classes used as ComposedCheck
	}

	@Check
	public void validateThatRelationshipContextArePartOfMap(final ContextMap map) {
		for (Relationship relationship : map.getRelationships()) {
			BoundedContext context1 = null;
			BoundedContext context2 = null;
			EReference attributeRelContext1 = null;
			EReference attributeRelContext2 = null;
			if (relationship instanceof SymmetricRelationship) {
				context1 = ((SymmetricRelationship) relationship).getParticipant1();
				context2 = ((SymmetricRelationship) relationship).getParticipant2();
				attributeRelContext1 = SYMMETRIC_RELATIONSHIP__PARTICIPANT1;
				attributeRelContext2 = SYMMETRIC_RELATIONSHIP__PARTICIPANT2;
			} else if (relationship instanceof UpstreamDownstreamRelationship) {
				context1 = ((UpstreamDownstreamRelationship) relationship).getUpstream().getContext();
				context2 = ((UpstreamDownstreamRelationship) relationship).getDownstream().getContext();
				attributeRelContext1 = UPSTREAM_DOWNSTREAM_RELATIONSHIP__UPSTREAM;
				attributeRelContext2 = UPSTREAM_DOWNSTREAM_RELATIONSHIP__DOWNSTREAM;
			}

			if (context1 != null && !isContextPartOfMap(map, context1))
				error(String.format(RELATIONSHIP_CONTEXT_NOT_ON_MAP_ERROR_MESSAGE, context1.getName()), relationship,
						attributeRelContext1);
			if (context2 != null && !isContextPartOfMap(map, context2))
				error(String.format(RELATIONSHIP_CONTEXT_NOT_ON_MAP_ERROR_MESSAGE, context2.getName()), relationship,
						attributeRelContext2);
		}
	}

	@Check
	public void validateBoundedContextTypes(final ContextMap map) {
		if (ORGANIZATIONAL.equals(map.getType())) {
			for (BoundedContext bc : map.getBoundedContexts()) {
				if (!TEAM.equals(bc.getType()))
					error(String.format(ORGANIZATIONAL_MAP_CONTEXT_IS_NOT_TYPE_TEAM, bc.getType()), map,
							CONTEXT_MAP__BOUNDED_CONTEXTS);
			}
		} else if (SYSTEM_LANDSCAPE.equals(map.getType())) {
			for (BoundedContext bc : map.getBoundedContexts()) {
				if (TEAM.equals(bc.getType()))
					error(String.format(SYSTEM_LANDSCAPE_MAP_CONTAINS_TEAM), map, CONTEXT_MAP__BOUNDED_CONTEXTS);
			}
		}
	}

	private boolean isContextPartOfMap(ContextMap map, BoundedContext context) {
		return map.getBoundedContexts().contains(context);
	}
}
