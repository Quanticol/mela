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

import eu.quanticol.mela.core.model.Model;
import eu.quanticol.mela.core.model.State;

/**
 *
 */
public class Population implements StoppingPredicate {
	
	private String agentName;
	private double timeBound;
	private Model m;
	
	public boolean continueSimulation( State s , int steps , double time ){
		boolean toReturn = ( agentExist(agentName, s) && (timeBoundOver(time)));
		return toReturn;
		
	}
	
	public boolean agentExist (String agentName, State s){
		int agentIndex = m.getAgentManager().agentIndex(agentName);
		for (int i=0; i < s.numberLocations(); i++){
			if (s.get(agentIndex, i) != 0){
				return true;				
			}
		}
		return false;
	}
	
	public void setPopulationName(String name){
		this.agentName = name;
	}
	
	public void setTimeBound(double time){
		this.timeBound = time;
	}
	
	public boolean timeBoundOver (double timeToCheck){
		boolean check = timeToCheck < timeBound;
		return check;
	}

	public void setModel(Model m) {
		this.m = m;		
	}
	

}



