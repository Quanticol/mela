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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import eu.quanticol.mela.core.model.Model;
import eu.quanticol.mela.core.simulator.Trajectory.TrajectoryStep;

/**
 *
 */
public class DataAction implements DataHandler {
	
	private ArrayList<String> listActions;
	private ArrayList<Integer> ActionsCount;
	static String outputAcCount = "./Output/ActionCount";

	@Override
	public void start(int iterations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Trajectory t, int currentIteration, Model m) {
		PrintWriter writer_ac = null;
		try {
			writer_ac = new PrintWriter(outputAcCount+ "_" + currentIteration + ".txt", "UTF-8");		    
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		for (int i=0; i < listActions.size(); i++){
			ActionsCount.add(0);
		}
		for (TrajectoryStep trStep : t.getData()){	
	    double time = trStep.getTime();
		String toCheck = trStep.getActionInfo().substring(0, trStep.getActionInfo().indexOf(' '));				
		writer_ac.print(time + " ");	
    	for( int i=0 ; i < listActions.size() ; i++ ) {
    		if (listActions.get(i) == toCheck){
    			int newValue = ActionsCount.get(i) + 1;
    			ActionsCount.set(i, newValue);
        	} 
        	writer_ac.print(ActionsCount.get(i) + " ");
    	}	
    	writer_ac.println("");
		}
		writer_ac.close();
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	
	public void setActions(ArrayList<String> namesAc){
		this.listActions = namesAc;
	}
	
}
