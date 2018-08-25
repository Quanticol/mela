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
/**
 * 
 */
package eu.quanticol.mela.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;


/**
 * 
 *
 */
public class AllActionInfo {
	
	public String agentPerformingActive;
	public String agentPerformingPassive;
	public String type;
	public String rateName;
	public String probName;
//	public Update updateActive;
//	public Update updatePassive;
	public String symbolActive;
	public ArrayList<String> updateArrayActive = new ArrayList<String>();
	public String symbolPassive;
	public ArrayList<String> updateArrayPassive = new ArrayList<String>();	
	public BiFunction<Integer,LocationManager,List<Integer>> infSet;
	public Predicate<Integer> envPredicate;
		
	
	public void setAgentPerformingActive(String agentPerformingActive) {
		this.agentPerformingActive = agentPerformingActive;
	}
	public void setAgentPerformingPassive(String agentPerformingPassive) {
		this.agentPerformingPassive = agentPerformingPassive;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public void setProbName(String probName) {
		this.probName = probName;
	}
	
	public void setSymbolActive(String symbolActive) {
		this.symbolActive = symbolActive;
	}
	public void setUpdateArrayActive(ArrayList<String> updateArrayActive) {
		this.updateArrayActive = updateArrayActive;
	}
	public void setSymbolPassive(String symbolPassive) {
		this.symbolPassive = symbolPassive;
	}
	public void setUpdateArrayPassive(ArrayList<String> updateArrayPassive) {
		this.updateArrayPassive = updateArrayPassive;
	}
	
	//depending on the influence set
	//local
	public void setInfSetLocal() {
		this.infSet = ( (x,y) -> 
		createList(x)
	);
	}
	
	//neighbouring locations at distance d
	public void setInfSetNeigh(Integer d) {
		this.infSet = ( (x,y) -> 
		y.allTheIndexDistance(d, x)
	);	
    }	
		
	//all the locations	
	public void setInfSetAll() {
		this.infSet = ( (x,y) -> 
		y.allTheIndex()
	);	
    }		
	public void setEnvPredicate(Predicate<Integer> envPredicate) {
		this.envPredicate = envPredicate;
	}	
	
	public String getAgentPerformingActive() {
		return agentPerformingActive;
	}
	public String getAgentPerformingPassive() {
		return agentPerformingPassive;
	}
	public String getType() {
		return type;
	}
	public String getRateName() {
		return rateName;
	}
	public String getProbName() {
		return probName;
	}
	public String getSymbolActive() {
		return symbolActive;
	}
	public ArrayList<String> getUpdateArrayActive() {
		return updateArrayActive;
	}
	public String getSymbolPassive() {
		return symbolPassive;
	}
	public ArrayList<String> getUpdateArrayPassive() {
		return updateArrayPassive;
	}
	public BiFunction<Integer, LocationManager, List<Integer>> getInfSet() {
		return infSet;
	}
	public Predicate<Integer> getEnvPredicate() {
		return envPredicate;
	}
	
	public static ArrayList<Integer> createList (int x){
		ArrayList<Integer> newList = new ArrayList<Integer>();
		newList.add(x);
		return newList;
	}
	@Override
	public String toString() {
		return "AllActionInfo [agentPerformingActive=" + agentPerformingActive + ", agentPerformingPassive="
				+ agentPerformingPassive + ", type=" + type + ", rateName=" + rateName + ", probName=" + probName + "]";
	}



}
