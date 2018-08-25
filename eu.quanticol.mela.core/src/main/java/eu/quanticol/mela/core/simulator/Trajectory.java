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

import java.util.LinkedList;

import eu.quanticol.mela.core.model.State;

/**
 *
 */
public class Trajectory {

	private final LinkedList<TrajectoryStep> data;
	private double currentTime;
	
	
	public Trajectory(double time, State state) {
		this.data = new LinkedList<>();
		this.currentTime = time;
		this.add(null,state);
	}

	/**
	 * @param info: info regarding the performed action
	 * @param dt: time elapsed
	 * @param state: current state of the system
	 */
	public void add(String info, double dt, State state) {
		this.currentTime += dt;
		this.add(info,state);
	}

	/**
	 * @param info: info regarding the performed action
	 * @param state: current state of the system
	 */
	private void add(String info, State state) {
		this.data.add(new TrajectoryStep(info, currentTime, state));
	}

	public double getTime() {
		return currentTime;
	}
	
	public class TrajectoryStep {
		
		private String info;		
		private double time;
		private State s;

		public TrajectoryStep(String info, double time, State s) {
			super();
			this.info = info;
			this.time = time;
			this.s = s;
		}

		public String getActionInfo() {
			return info;
		}

		public double getTime() {
			return time;
		}

		public State getState() {
			return s;
		}


	}

	public LinkedList<TrajectoryStep> getData() {
		return data;
	}

	public double getCurrentTime() {
		return currentTime;
	}

}
