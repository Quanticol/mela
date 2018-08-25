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

import eu.quanticol.mela.core.model.Model;
import eu.quanticol.mela.core.model.State;
import eu.quanticol.mela.core.simulator.Trajectory.TrajectoryStep;

/**
 * @author ludovicaluisavissat
 *
 */
public class DataAndMeta implements DataHandler {
	
	static String outputData = "./Output/Data";
	static String outputMeta = "./Output/Meta";

	@Override
	public void start(int iterations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Trajectory t, int currentIteration, Model m) {
		PrintWriter writer_data = null;
		PrintWriter writer_meta = null;
		try {
			writer_data = new PrintWriter(outputData+ "_" + currentIteration + ".txt", "UTF-8");
			writer_meta = new PrintWriter(outputMeta+ "_" + currentIteration + ".txt", "UTF-8");		    
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		for (TrajectoryStep trStep : t.getData()){	
	    double time = trStep.getTime();
		State toWrite = trStep.getState();
		writer_data.print(time + " ");	
        System.out.println(time);
    	for( int i=0 ; i < toWrite.getAgents() ; i++ ) {
        	for( int j=0 ; j < toWrite.getLocations() ; j++ ) {
        		writer_data.print(toWrite.getState()[i][j] + " ");
        	}    		
    	}
    	writer_meta.println(trStep.getActionInfo()); 	
    	writer_data.println("");
		}
		writer_data.close();
		writer_meta.close();
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

}
