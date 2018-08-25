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
import java.util.function.BiFunction;

import eu.quanticol.mela.core.simulator.Transition;

/**
 *
 */
public class InfluenceRule implements Rule {
	
	/**
	 * info: information for metadata
	 * rate: rate of the action
	 * passiveProb: probability of the action to be effective
	 * active: index of agent performing the action, its update
	 * passive: index of passive agent, its update
	 * influenceFunction: index of locations where the influence is felt
	 */
	private final String info;
	private final double rate;
	private final double passiveProb;
	private final AgentStep active;
	private final AgentStep passive;
	private final BiFunction<Integer,LocationManager,List<Integer>> influenceFunction;

	public InfluenceRule(String info, 
			double rate,
			AgentStep active , 
			BiFunction<Integer,LocationManager,List<Integer>> influenceFunction, 
			AgentStep passive ,
			double passiveProb ) {
		super();
		this.info = info;
		this.active = active;
		this.passive = passive;
		this.rate = rate;
		this.passiveProb = passiveProb;
		this.influenceFunction = influenceFunction;
	}	
	
	/**
	 * build the transitions with the updates for both the active and the passive, checking the presence of the passive agents
	 * it uses the method addTransitions to add both the updates
	 * it builds the two possible transitions, the effective (probability p) and not effective (probability 1-p)
	 */	
	@Override
	public Collection<? extends Transition> apply(int l, State current, LocationManager locationManager) {
		LinkedList<Transition> toReturn = new LinkedList<>();
		int activeMul = current.get(active.getAgentIndex(), l);
		List<UpdateItem> activeUpdateItems = active.computeUpdateItems(locationManager, l);
		for (Integer l2 : influenceFunction.apply(l, locationManager)) {
			int passiveMul = current.get(passive.getAgentIndex(), l2);
			if (passiveMul > 0) {
				List<UpdateItem> passiveUpdateItems = passive.computeUpdateItems(locationManager, l2);
				addTransitions( toReturn , rate*passiveProb*activeMul*passiveMul , activeUpdateItems , passiveUpdateItems );
				String infoRule = "No change";
				toReturn.add(new Transition(rate*(1-passiveProb)*activeMul*passiveMul, new LinkedList<>(), infoRule));
			}
		}
		return toReturn;
	}

	/**
	 * @param toReturn: list of enabled transitions
	 * @param rate: rate of the action
	 * @param activeUpdateItems: update of the active agent
	 * @param passiveUpdateItems: update of the passive agent
	 */
	private void addTransitions(LinkedList<Transition> toReturn, double rate, List<UpdateItem> activeUpdateItems,
			List<UpdateItem> passiveUpdateItems) {
		for (UpdateItem passiveItem : passiveUpdateItems) {
			for (UpdateItem activeItem : activeUpdateItems) {
				LinkedList<AgentVariation> variations = new LinkedList<>();
				variations.addAll(passiveItem.getVariations());
				variations.addAll(activeItem.getVariations());	
				String infoRule = info;
				toReturn.add(new Transition(rate*passiveItem.getProb()*activeItem.getProb(), variations, infoRule));
			}
		}		

	}
	
	public static void createAddInfRule(HashMap<String, AllActionInfo> map, String nameAction, AgentManager am, HashMap<String, Double> parameters){		
		Update updateActive = null;
		Update updatePassive = null;
		String info = nameAction + " " + map.get(nameAction).getType();
	       String agentName = map.get(nameAction).getAgentPerformingActive();
	       int agentIndex = am.agentIndex(agentName);	      
	       if (map.get(nameAction).symbolActive == "|>") {
			    MovementUpdate newMove = new MovementUpdate(agentIndex);
			    updateActive = newMove;
	       }else{
	    	ArrayList<Integer> updateArray = new ArrayList<Integer>(); 
	    	for (int i=0; i< map.get(nameAction).updateArrayActive.size(); i++){
	    		String name = map.get(nameAction).updateArrayActive.get(i);
	    		updateArray.add(am.agentIndex(name));
	    	    }
	    	    updateActive = new DeterministicUpdate(agentIndex, updateArray);	     	    		    	
	       } 
	       AgentStep activeStep = new AgentStep(agentIndex, updateActive);
	       BiFunction<Integer,LocationManager,List<Integer>> influenceFunction = map.get(nameAction).getInfSet();
	       String passAgentName = map.get(nameAction).getAgentPerformingPassive();
	       int agentIndexPass = am.agentIndex(passAgentName);
	       if (map.get(nameAction).symbolPassive == "|>") {
			    MovementUpdate newMove = new MovementUpdate(agentIndex);
			    updatePassive = newMove;
	       }else{
	    	ArrayList<Integer> updateArrayPass = new ArrayList<Integer>(); 
	    	for (int i=0; i< map.get(nameAction).updateArrayPassive.size(); i++){
	    		String name = map.get(nameAction).updateArrayPassive.get(i);
	    		updateArrayPass.add(am.agentIndex(name));
	    	    }
	    	    updatePassive = new DeterministicUpdate(agentIndexPass, updateArrayPass);	     	    		    	
	       } 
	       AgentStep passiveStep = new AgentStep(agentIndexPass, updatePassive);
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
	       InfluenceRule newInf = new InfluenceRule(info, rate, activeStep, influenceFunction, passiveStep, passProb);
	       am.directory.get(agentName).addRule(newInf); 
	}


}
