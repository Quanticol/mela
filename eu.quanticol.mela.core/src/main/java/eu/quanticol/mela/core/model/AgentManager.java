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
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import eu.quanticol.mela.core.simulator.Transition;

/**
 * 
 * 
 *
 */
public class AgentManager {
	
	public ArrayList<Agent> agents = new ArrayList<>(); 
	public HashMap<String,Agent> directory = new HashMap<>();


	/**
	 * Get names of all agents.
	 * 
	 * @return
	 */
	public Set<String> getAgentsNames() {
		return directory.keySet();
	}
    
    public Agent addAgent(String name) {
    	if (directory.containsKey(name)) {
    		throw new IllegalArgumentException("Duplicated agent name!");
    	}
    	Agent agent = new Agent( agents.size(), name);
    	directory.put(name, agent);
    	agents.add(agent);
    	return agent;
    }    

	public int size() {
		return agents.size();
	}
	
	public int agentIndex (String name){
		Agent toCheck = directory.get(name);
		int index = agents.indexOf(toCheck);
		return index;
	}

	/**
	 * @param a: index agent
	 * @param l: index location
	 * @param current: current state of the system
	 * @param locationManager: spatial structure
	 * @return:  transition enabled by the agent in the location
	 */
	public Collection<? extends Transition> apply(int a, int l, State current, LocationManager locationManager) {
		return agents.get(a).apply(l,current,locationManager);
	}

}
