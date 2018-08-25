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
import java.util.List;
import java.util.Set;

/**
 * 
 *
 */
public class MovementUpdate implements Update {
	
	private final int src;
	
	public MovementUpdate( int src ) {
		this.src = src;
	}	

	/**
	 * The updateItems which are built have a probability associated which is calculated as 1/n, where n is the number of neighbouring locations
	 * The agent state (src) is the same, in this case the index of the location is the one that changes
	 *
	 */
	@Override
	public List<UpdateItem> computeUpdateItems(LocationManager locationManager, int location) {
		LinkedList<UpdateItem> toReturn = new LinkedList<>();
		Set<Integer> nextTo = locationManager.nextTo(location);
		double prob = 1.0/nextTo.size();
		for (Integer l2 : nextTo) {
			toReturn.add(new UpdateItem(prob, getVariation(location,l2)));
		}
		return toReturn;
	}
	
// for random movement on the graph (privacy network)	
	
//	Set<Integer> allLoc = locationManager.allTheIndex(); //(current one to exclude?)
//	double prob = 1.0/allLoc.size();
//	for (Integer l2 : allLoc) {
//		toReturn.add(new UpdateItem(prob, getVariation(location,l2)));
//	}

	private LinkedList<AgentVariation> getVariation(int startLocation, int endLocation) {
		LinkedList<AgentVariation> variations = new LinkedList<>();
		variations.add(new AgentVariation(src, startLocation, -1));
		variations.add(new AgentVariation(src, endLocation, 1));			
		return variations;
	}
	
	@Override
	public int getIndexAgent(){
		return src;
	}

}
