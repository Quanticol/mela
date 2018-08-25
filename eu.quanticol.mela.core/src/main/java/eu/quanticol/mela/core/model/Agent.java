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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import eu.quanticol.mela.core.simulator.Transition;

/**
 * 
 * 
 * 
 *
 */
public class Agent {
	
	private final String name;
	private final int index;
	private final LinkedList<Rule> ruleList = new LinkedList<Rule>();

	/**
	 * 
	 * Create a new Agent with a given index and name.
	 *  
	 * @param index Agent index
	 * @param name Agent name
	 */
	public Agent(int index, String name) {
		this.index = index;
		this.name = name;
	}
	
	/**
	 * 
	 * Return agent name.
	 * 
	 * @return agent name.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * Return agent index
	 * 
	 * @return agent index.
	 */
	public int getIndex(){
		return index;
	}
	
	/**
	 * 
	 * Add a rule to the agent. 
	 * 
	 * @param r
	 */
	public void addRule(Rule r){
		ruleList.add(r);
	}
	
	/**
	 * Returns agent rules.
	 * 
	 * @return the agent rules.
	 */
	public List<Rule> getRuleList() {
		return this.ruleList;
	}
	
	/**
	 * 
	 * Compute all the transitions enabled when an agent is located at l.
	 * 
	 * @param l: current agent location
	 * @param current: current state of the system
	 * @param locationManager: spatial structure
	 * @return: list of enabled transitions
	 */
	public Collection<? extends Transition> apply(int l, State current, LocationManager locationManager) {
		LinkedList<Transition> toReturn = new LinkedList<>();
		for (Rule r : ruleList) {
			toReturn.addAll(r.apply(l,current,locationManager));
		}
		return toReturn;
	}

}
