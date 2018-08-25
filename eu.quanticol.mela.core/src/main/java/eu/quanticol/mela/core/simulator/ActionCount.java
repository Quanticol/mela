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

import eu.quanticol.mela.core.model.State;

/**
 *
 *
 */
public class ActionCount implements StoppingPredicate {
	
	private String nameAction;
	private int count;
	private double timeBound;
		
	private int countAction = 0;
	
	public boolean continueSimulation( State s , int steps , double time ){
		if (Simulator.getNameAction() == nameAction ){
			countAction++;
		}
		boolean toReturn = ((countAction < count) && (timeBoundOver(time)));
		return toReturn;
		
	}
	
	public void setActionName(String name){
		this.nameAction = name;
	}
	
	public void setCount(int count){
		this.count = count;
	}

	
	public void setTimeBound(double time){
		this.timeBound = time;
	}
	
	public boolean timeBoundOver (double timeToCheck){
		boolean check = timeToCheck < timeBound;
		return check;
	}
	


}
