/*******************************************************************************
 * MELA: Modelling in Ecology with Location Attributes
 * Copyright (C) 2018 
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package eu.quanticol.mela.core.model;

import java.util.LinkedList;

/**
 *
 */
public class UpdateItem {

	private final double prob;	
	private final LinkedList<AgentVariation> variations;

	public UpdateItem(double prob, LinkedList<AgentVariation> variations) {
		super();
		this.prob = prob;
		this.variations = variations;
	}

	public double getProb() {
		return prob;
	}

	public LinkedList<AgentVariation> getVariations() {
		return variations;
	}


}
