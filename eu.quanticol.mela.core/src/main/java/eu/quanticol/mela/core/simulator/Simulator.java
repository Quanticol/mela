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

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import eu.quanticol.mela.core.model.Model;
import eu.quanticol.mela.core.model.State;

/**
 *
 */
public class Simulator {
	
	private Random random;
	public static String nameAction;

	public Simulator( Random random ) {
		this.random = random;
	}	
	
	
	/**
	 * @param m: model (agents, locations, parameters, initial conditions)
	 * @param p: information to run the simulations
	 */
	public void simulate( Model m , Parameters p ) {
		int iterations = p.getSimulationRuns();
		LinkedList<DataHandler> handler = p.getDataHandlerList();
		for (DataHandler datahandler : handler){
			datahandler.start(iterations);
		}
		for( int i=0 ; i<iterations ; i++ ) {
			Trajectory t = computeTrajectory( m , p.getStopPredicate() );
			for (DataHandler datahandler : handler){
				datahandler.add(t, i, m);
			}
		}
		for (DataHandler datahandler : handler){
			datahandler.commit();
		}
	}
	
	/**
	 * @param m: model  (agents, locations, parameters, initial conditions)
	 * @param stopPredicate: condition to stop the simulation
	 * @return Trajectory, the output of a simulation (time, current state of the system, after each action is performed)
	 * 
	 * WHAT IT DOES:
	 * 
	 */
	public Trajectory computeTrajectory(Model m, StoppingPredicate stopPredicate) {
		State current = m.getInitialState();
		int steps = 0;
		Trajectory t = new Trajectory(0.0,current);
		boolean flag = true;
		while (flag && stopPredicate.continueSimulation(current, steps, t.getTime())) {
			List<Transition> enabled = m.getTransitions( current );	
			double totalRate = getTotalRate(enabled);	
			if (totalRate != 0) {
				double u1 = randomValue();
				double u2 = randomValue();
				Transition transition = select( enabled , totalRate*u1 );
				double dt = (1/totalRate)*Math.log(1/u2);
				//System.out.println(transition.getInfo());
				current = transition.apply(current);
				t.add(transition.getInfo(),dt,current);
				steps++;	
				nameAction = transition.getInfo().substring(0, transition.getInfo().indexOf(' '));				
			} else {
				flag = false;
			}			
		}
		return t;
	}

	/**
	 * @param enabled: list of possible transitions
	 * @param d: random value generated
	 * @return: the select Transition, given the random value
	 * it iterates in the list, until the counter results bigger than d, which means we found the action and its associated interval
	 */
	private Transition select(List<Transition> enabled, double d) {
		double counter = 0.0;
		for (Transition transition : enabled) {
			counter += transition.getRate();
			if (counter > d) {
				return transition;
			}
		}
		return null;
	}

	private double randomValue() {
		return random.nextDouble();
	}

	/**
	 * @param enabled: list of possible transitions
	 * @return total rate (sum of all the transition rates)
	 */
	private double getTotalRate(List<Transition> enabled) {
		double counter = 0.0;
		for (Transition transition : enabled) {
			counter += transition.getRate();
		}
		return counter;
	}

	/**
	 * @param modelFile: directory of MELA model
	 * @param parametersFile: directory of parameters file
	 */
	public void simulate( File modelFile , File parametersFile ) {
		//TODO fix this once we have the parser
		//MELAParser parser = MELAParser.getInstance();
		//simulate(parser.parseModel(modelFile),parser.parseParameters(parametersFile));
	}
	
	public static String getNameAction(){
		return nameAction;
	}

}
