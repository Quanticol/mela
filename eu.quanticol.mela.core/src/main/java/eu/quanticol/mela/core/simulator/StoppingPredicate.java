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
 * @author ludovicaluisavissat
 *
 */
public interface StoppingPredicate  {

	
	/**
	 * @param s: current state of the system
	 * @param steps: number of steps
	 * @param time: time elapsed
	 * @return: if the simulation can continue or has to stop
	 */
	public boolean continueSimulation( State s , int steps , double time );

}
