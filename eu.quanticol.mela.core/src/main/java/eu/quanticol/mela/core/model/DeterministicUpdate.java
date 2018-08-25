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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 */
public class DeterministicUpdate implements Update {
	
	private final ArrayList<Integer> nextAgents;
	private final int src;
	
	public DeterministicUpdate( int src , ArrayList<Integer> nextAgents ) {
		this.src = src;
		this.nextAgents= nextAgents;
	}
	
	/**
	 * return the list of updateItems
	 * as variations, it removes the current agent, it adds the new one(s), in the same location
	 * Change of state 1: add one new agents
     * Change of state 2: add two new agents
     * Demographic +: add two agent of the same type (birth action as (alpha, r).(P(l)||P(l)))
     * Demographic -: no variations (we already removed the existing one, nextAgents will be empty)
	 * this update has probability 1.0
	 *
	 */
	@Override
	public List<UpdateItem> computeUpdateItems(LocationManager locationManager, int location) {
		LinkedList<UpdateItem> toReturn = new LinkedList<>();
		LinkedList<AgentVariation> variations = new LinkedList<>();
		variations.add(new AgentVariation(src, location, -1));
		for (int idx : nextAgents) {
			variations.add(new AgentVariation(idx, location, +1));
		}
		toReturn.add(new UpdateItem(1.0, variations));
		return toReturn;
	}

	@Override
	public int getIndexAgent(){
		return src;
	}
	
}
