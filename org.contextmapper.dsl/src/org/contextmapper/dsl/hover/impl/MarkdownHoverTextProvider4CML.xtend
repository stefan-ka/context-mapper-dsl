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
package org.contextmapper.dsl.hover.impl

import org.contextmapper.dsl.hover.impl.AbstractCMLHoverTextProvider
import org.contextmapper.dsl.hover.CMLHoverTextProvider

class MarkdownHoverTextProvider4CML extends AbstractCMLHoverTextProvider implements CMLHoverTextProvider {

	override protected registerHoverTexts() {
		registerHoverText("BoundedContext", '''
		**Bounded Context**: A description of a boundary (typically a subsystem, or the work 
		of a particular team) within which a particular model is defined and applicable.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("ContextMap", '''
		**Context Map**: A model describing bounded contexts 
		and especially their relationships. Brandolini provides a very good introduction into context mapping here:
		[https://www.infoq.com/articles/ddd-contextmapping/](https://www.infoq.com/articles/ddd-contextmapping/)
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("U", '''
		**U**pstream in the upstream-downstream relationship.
		
		**upstream-downstream**: A relationship between two bounded contexts 
		in which the “upstream” contexts’s actions affect project success of the “downstream” context, but 
		the actions of the downstream do not significantly affect projects upstream. (e.g. If two cities 
		are along the same river, the upstream city’s pollution primarily affects the downstream city.) 
		The upstream team may succeed independently of the fate of the downstream team.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("D", '''
		**D**ownstream in the upstream-downstream relationship.
		
		**upstream-downstream**: A relationship between two bounded contexts 
		in which the “upstream” contexts’s actions affect project success of the “downstream” context, but 
		the actions of the downstream do not significantly affect projects upstream. (e.g. If two cities 
		are along the same river, the upstream city’s pollution primarily affects the downstream city.) 
		The upstream team may succeed independently of the fate of the downstream team.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("S", '''
		**S**upplier in the customer-supplier relationship.
		
		**customer-supplier**:<br/>Describes an upstream-downstream relationship 
		where one bounded context is customer and the other supplier which work closely together. 
		The supplier prioritizes the implementation with respect to the customers requirements.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("C", '''
		**C**ustomer in the customer-supplier relationship.
		
		**customer-supplier**:<br/>Describes an upstream-downstream relationship 
		where one bounded context is customer and the other supplier which work closely together. 
		The supplier prioritizes the implementation with respect to the customers requirements.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("OHS", '''
		**Open Host Service (OHS)**: Describes a role of a 
		bounded context which is providing certain functionality needed by many other contexts. 
		Because of the broad usage, a public API is provided.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("PL", '''
		**Published Language (PL)**: The published language describes 
		the shared knowledge two bounded contexts need for their interaction. Typically defined by the 
		upstream providing an Open Host Service.
		
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("ACL", '''
		**Anticorruption Layer (ACL)**: Describes a mechanism used 
		by downstreams in order to protect themselves from changes of the upstream.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("CF", '''
		**Conformist (CF)**: Describes a role of a bounded context in 
		an upstream-downstream relationship. Since there is no influence on the upstream, the downstream 
		team has to deal with what they get and "conform" to it.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("Aggregate", '''
		**Aggregate**: An Aggregate is a cluster of domain 
		objects (such as Entities, Value Objects, etc.) which is kept consistent with respect to specific 
		invariants and typically also represents a boundary regarding transactions.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("Domain", '''
		**Domain**: The domain describes the world within your 
		organization is working. This is not a pattern in the original DDD book of Evans, but still a very 
		important term related to the patterns Subdomain and Bounded Context as described by 
		[Vernon](https://www.amazon.de/Implementing-Domain-Driven-Design-Vaughn-Vernon/dp/0321834577)
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("Subdomain", '''
		**Subdomain (Core, Supporting, Generic)**: A subdomain 
		is a part of the domain. Regarding subdomains we differentiate between Core Domains, Supporting 
		Subdomains and Generic Subdomains. A bounded context implements parts of one or multiple subdomains.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("P", '''
		**Partnership (P)**: The partnership pattern describes an 
		intimate relationship between two bounded contexts. Their domain models somehow relate and have 
		to be evolved together.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');

		registerHoverText("SK", '''
		**Shared Kernel (SK)**: Desribes an intimate relationship 
		between two bounded contexts which share a common part of the domain model and manage it as a 
		common library.
		
		Find all DDD pattern descriptions in the DDD reference under 
		[http://domainlanguage.com/ddd/reference/](http://domainlanguage.com/ddd/reference/)''');
	}

}
