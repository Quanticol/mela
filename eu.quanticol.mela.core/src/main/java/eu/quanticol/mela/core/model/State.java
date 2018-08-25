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

import java.util.List;
import java.util.function.BiFunction;
/**
 *
 */
public class State {   
 
	private final int agents;
	private final int locations;
	private final int[][] state;  
	
    private State(int agents, int locations, int[][] state){
    	this.agents = agents;
    	this.locations = locations;
    	this.state = state;
    }   

    /**
     * @param agents: number of agents
     * @param locations: number of locations
     * @param agentAllocationFunction: initial state of the system
     */
    public State(int agents, int locations, BiFunction<Integer, Integer, Integer> agentAllocationFunction) {
    	this.agents = agents;
    	this.locations = locations;
    	this.state = new int[agents][locations];
    	for( int i=0 ; i < agents ; i++ ) {
        	for( int j=0 ; j < locations ; j++ ) {
        		this.state[i][j] = agentAllocationFunction.apply(i, j);
        	}    		
    	}
	}

	/**
	 * @param agent: index agent
	 * @param location: index location
	 * @return: number of the required agent in the given location
	 */
	public int get(int agent, int location) {
    	return this.state[agent][location];
    }
    
    /**
     * @param updates: lit of agents' variation
     * @return new state of the system, after the variation have been applied
     */
    public State apply( List<AgentVariation> updates ) {
    	int[][] newState = copyState();
    	for (AgentVariation av : updates) {
    		
    		//System.out.println(av.getAgentIndex() + " " + av.getLocationIndex() + " " + av.getVariation());
    		
    		newState[av.getAgentIndex() ][av.getLocationIndex()] += av.getVariation();
		}
    	return new State(this.agents,this.locations,newState);
    }

	/**
	 * @return copy of the current state of the system
	 */
	private int[][] copyState() {
		int[][] newState = new int[agents][locations];
		for( int i=0 ; i<agents ; i++ ) {
			for( int j=0 ; j<locations ; j++ ) {
				newState[i][j] = state[i][j];
			}
		}
		return newState;
	}
	
	public int numberLocations(){
		return locations;
	}

	public int getAgents() {
		return agents;
	}

	public int getLocations() {
		return locations;
	}

	public int[][] getState() {
		return state;
	}

    
}