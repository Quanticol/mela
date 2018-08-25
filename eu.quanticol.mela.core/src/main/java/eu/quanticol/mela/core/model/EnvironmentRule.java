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
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import eu.quanticol.mela.core.simulator.Transition;

/**
 *
 */
public class EnvironmentRule implements Rule {
	
	private final String info;
	
	//TODO: Check if this field is really needed.
	private final int agentIndex;
	
	private final AgentStep step;
	private final double rate;
	private final Predicate<Integer> environmentSet;
	private final double effectProb;

	/**
	 * info: information about the action, for metadata
	 * agentIndex: index of active agent
	 * step: index and update for the agent
	 * rate: rate of the action
	 * environmentSet: to evaluate if the passive agent is in a location under the environment influence
	 * effectProb: probability of the action to be effective
	 */
	public EnvironmentRule(String info, int agentIndex, AgentStep step, double rate,
			Predicate<Integer> environmentSet, double effectProb) {
		super();
		this.info = info;
		this.agentIndex = agentIndex;
		this.step = step;
		this.rate = rate;
		this.environmentSet = environmentSet;
		this.effectProb = effectProb;
	}

	@Override
	/**
	 * this methods returns the set of enabled transitions, for passive agents which are located in the locations
	 * influenced by the environment.
	 * If we do not have passive agents, this method will not be called (the method getTransitions() in the model checks the presence of the agents)
	 */
	public Collection<? extends Transition> apply(int l, State current, LocationManager locationManager) {
		LinkedList<Transition> toReturn = new LinkedList<>();
		if (environmentSet.test(l)) {
			List<UpdateItem> updateItems = step.computeUpdateItems(locationManager, l);
			for (UpdateItem updateItem : updateItems) {
				List<AgentVariation> variations = updateItem.getVariations();
				//TODO: Built here the appropriate  ActionInfo
				int passAgentIndex = step.getAgentIndex();
				String infoRule = info;
				toReturn.add(new Transition(rate*current.get(passAgentIndex, l)*updateItem.getProb()*effectProb, variations, infoRule));
			}
			//TODO: Built here the appropriate  ActionInfo
			int passAgentIndex = step.getAgentIndex();
			String infoRule = "No change";
			toReturn.add(new Transition(rate*current.get(passAgentIndex, l)*(1-effectProb), new LinkedList<>(), infoRule));
		}
		return toReturn;
	}
	
	
	public static Predicate<Integer> createPredicate( ArrayList<Integer> locations ) {
		return l -> locations.contains(l);
	}
	
	public static Predicate<Integer> allPredicate( ) {
		return l -> true;
	}
	

    public static void createAddEnvRule(HashMap<String, AllActionInfo> map, String nameAction, AgentManager am, HashMap<String, Double> parameters){
    	Update updatePassive = null; 
    	String info = nameAction + " " + map.get(nameAction).getType();
	     String agentName = map.get(nameAction).getAgentPerformingActive();
	     int agentIndex = am.agentIndex(agentName);
    	 String passAgentName = map.get(nameAction).getAgentPerformingPassive();
         int agentIndexPass = am.agentIndex(passAgentName);
         if (map.get(nameAction).symbolPassive == "|>") {
			    MovementUpdate newMove = new MovementUpdate(agentIndex);
			    updatePassive = newMove;
	       }else{
	    	ArrayList<Integer> updateArray = new ArrayList<Integer>(); 
	    	for (int i=0; i< map.get(nameAction).updateArrayPassive.size(); i++){
	    		String name = map.get(nameAction).updateArrayPassive.get(i);
	    		updateArray.add(am.agentIndex(name));
	    	    }
	    	    updatePassive = new DeterministicUpdate(agentIndex, updateArray);	     	    		    	
	       } 
         AgentStep passiveStep = new AgentStep(agentIndexPass, updatePassive);
         Predicate<Integer> environmentSet =  map.get(nameAction).getEnvPredicate();
         if (parameters.get(map.get(nameAction).getRateName()) == null) { 
	    	  throw new Error("Parameter " + map.get(nameAction).getRateName() + " is not defined.");
	     }	    	   
	     if (parameters.get(map.get(nameAction).getProbName()) == null) { 
	    	  throw new Error("Parameter " + map.get(nameAction).getProbName() + " is not defined.");
	     }
	     String rateName = map.get(nameAction).getRateName();
	     double rate = parameters.get(rateName);
         String passProbName = map.get(nameAction).getProbName();
         double passProb = parameters.get(passProbName);
         EnvironmentRule newEnv = new EnvironmentRule(info, agentIndex, passiveStep, rate, environmentSet, passProb);
         am.directory.get(passAgentName).addRule(newEnv); 
    }

	/**
	 * @return the agentIndex
	 */
	public int getAgentIndex() {
		return agentIndex;
	}




}
