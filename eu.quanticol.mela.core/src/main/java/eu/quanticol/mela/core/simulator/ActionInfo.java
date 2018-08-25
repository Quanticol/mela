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

/**
 *
 */
public class ActionInfo {
	
	private String name;
//	private String type;
//	private ArrayList<Integer> LocationActive;
//	private ArrayList<Integer> LocationPassive;
//	
//		
	
	public ActionInfo(String name){
	this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
//	public ActionInfo(String name, String type, ArrayList<Integer> LocationActive, ArrayList<Integer> LocationPassive){
//		this.name = name;
//		this.type = type;
//		this.LocationActive = LocationActive;
//		this.LocationPassive = LocationPassive;
//	}
//	
//	
//	public String printOut(LocationManager lm) {
//		String toReturn = name + " " + type; 
//		for (int i=0; i < LocationActive.size(); i++){
//			toReturn += lm.getLocationName(LocationActive.get(i));
//		}
//		for (int i=0; i < LocationPassive.size(); i++){
//			toReturn += lm.getLocationName(LocationPassive.get(i));
//		}
//		return toReturn;
//	}

	

}
