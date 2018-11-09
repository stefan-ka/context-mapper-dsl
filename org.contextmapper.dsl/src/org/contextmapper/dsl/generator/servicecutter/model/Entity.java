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
package org.contextmapper.dsl.generator.servicecutter.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * ServiceCutter Entity input object.
 * 
 * @author Stefan Kapferer
 *
 */
public class Entity {

	private String name;
	private List<Nanoentity> nanoEntities;

	public Entity(String name) {
		this.name = name;
		this.nanoEntities = Lists.newArrayList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Nanoentity> getNanoEntities() {
		return nanoEntities;
	}

	public void addNanoEntity(Nanoentity nanoEntity) {
		this.nanoEntities.add(nanoEntity);
	}

}
