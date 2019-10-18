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
package org.contextmapper.dsl.generator.mdsl.model;

import java.util.List;

import com.google.common.collect.Lists;

public class EndpointContract {

	private String name;
	private String servesAs;
	private boolean servesAsPatternMatched = false;
	private List<EndpointOperation> operations = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EndpointOperation> getOperations() {
		return operations;
	}

	public void addOperation(EndpointOperation operation) {
		this.operations.add(operation);
	}
	
	public void setServesAs(String servesAs) {
		this.servesAs = servesAs;
	}
	
	public String getServesAs() {
		return servesAs;
	}
	
	public void setServesAsPatternMatched(boolean servesAsPatternMatched) {
		this.servesAsPatternMatched = servesAsPatternMatched;
	}
	
	public boolean isServesAsPatternMatched() {
		return servesAsPatternMatched;
	}

}
