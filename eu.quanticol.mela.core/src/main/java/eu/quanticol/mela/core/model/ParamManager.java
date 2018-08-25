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

import java.util.HashMap;

/**
 *
 */
public class ParamManager {
	
	public HashMap<String, Double> paramMap = new HashMap<String, Double>();
	
	/**
	 * @param name: name parameter
	 * @param value: value parameter
	 * add a parameter and its value to the hashmap
	 */
	public void addParam(String name, double value) {
		if(ParamExist(name)) {
			System.err.println("The param " + name + " already exists!");
		}else {
			paramMap.put(name, value);
		}
	}
	
	/**
	 * @param name: name parameter
	 * @return: checks if the parameter exists already (through the name)
	 */
	public boolean ParamExist(String name) {
		if(paramMap.get(name) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public double getParamValue(String name) {
		double ret = (Double) paramMap.get(name);
		return ret;
	}
	
	public HashMap<String, Double> getParamMap() {
		return this.paramMap;
	}
	
	public void clear() {
		paramMap.clear();
	}

}
