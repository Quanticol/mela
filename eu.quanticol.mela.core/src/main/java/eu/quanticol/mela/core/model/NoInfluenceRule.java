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

import eu.quanticol.mela.core.simulator.Transition;

/**
 *
 */
public class NoInfluenceRule implements Rule {
	
	private final String info;
	private final int agentIndex;
	private final Update update;
	private final double rate;

	
	public NoInfluenceRule(String info, int agentIndex, double rate, Update update) {
		super();
		this.info = info;
		this.agentIndex = agentIndex;
		this.update = update;
		this.rate = rate;
	}	

	@Override	
	/**
	 * this method creates the list of enabled transitions given the no-influence action 
	 * the use of updateItem and probability in this case is used for movement actions
	 */
	public Collection<? extends Transition> apply(int l, State current, LocationManager locationManager) {
		LinkedList<Transition> toReturn = new LinkedList<>();
		//TODO: Built here the appropriate  ActionInfo
		List<UpdateItem> updateItems = update.computeUpdateItems(locationManager, l);
		for (UpdateItem updateItem : updateItems) {
			List<AgentVariation> variations = updateItem.getVariations();
			String infoRule = info;
			toReturn.add(new Transition(rate*current.get(agentIndex, l)*updateItem.getProb(), variations, infoRule));
		}
		return toReturn;
	}
	
	public static void createAddNoInfRule(HashMap<String, AllActionInfo> map, String nameAction, AgentManager am, HashMap<String, Double> parameters){
		   String info = nameAction + " " + map.get(nameAction).getType();
	       String agentName = map.get(nameAction).getAgentPerformingActive();
	       int agentIndex = am.agentIndex(agentName);
	       if (map.get(nameAction).symbolActive == "|>") {
			    MovementUpdate newMove = new MovementUpdate(agentIndex);
			    Update update = newMove;
			       if (parameters.get(map.get(nameAction).getRateName()) != null) { 
				       double rate = parameters.get(map.get(nameAction).getRateName()); 	       
				       NoInfluenceRule newNoInf = new NoInfluenceRule(info, agentIndex, rate, update);
				       am.directory.get(agentName).addRule(newNoInf); 
				       }
				       else {
				          throw new Error("Parameter " + map.get(nameAction).getRateName() + " is not defined.");
				       }  
	       }else{
	    	ArrayList<Integer> updateArray = new ArrayList<Integer>(); 
	    	for (int i=0; i< map.get(nameAction).updateArrayActive.size(); i++){
	    		String name = map.get(nameAction).updateArrayActive.get(i);
	    		updateArray.add(am.agentIndex(name));
	    	    }
	    	Update update = new DeterministicUpdate(agentIndex, updateArray);	     
		       if (parameters.get(map.get(nameAction).getRateName()) != null) { 
			       double rate = parameters.get(map.get(nameAction).getRateName()); 	       
			       NoInfluenceRule newNoInf = new NoInfluenceRule(info, agentIndex, rate, update);
			       am.directory.get(agentName).addRule(newNoInf); 
			       }
			       else {
			          throw new Error("Parameter " + map.get(nameAction).getRateName() + " is not defined.");
			       } 	    		    	
	       } 
	}
	
	



}
