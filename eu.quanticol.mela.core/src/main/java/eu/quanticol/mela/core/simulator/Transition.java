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
package eu.quanticol.mela.core.simulator;

import java.util.List;

import eu.quanticol.mela.core.model.AgentVariation;
import eu.quanticol.mela.core.model.State;

/**
 * @author ludovicaluisavissat
 *
 */
public class Transition {
	
	private final double rate;
	private final List<AgentVariation> v;
	private final String actionInfo;
	
	public Transition(double rate, List<AgentVariation> v, String actionInfo) {
		super();
		this.rate = rate;
		this.v = v;
		this.actionInfo = actionInfo;
	}

	public double getRate() {
		return rate;
	}

	public List<AgentVariation> getVariation() {
		return v;
	}

	public String getInfo() {
		return actionInfo;
	}
	
	public State apply(State current) {
		State newState = current.apply(v);
		return newState;
	}

}
