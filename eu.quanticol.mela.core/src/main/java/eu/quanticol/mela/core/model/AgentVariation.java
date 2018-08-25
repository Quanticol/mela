package eu.quanticol.mela.core.model;

public class AgentVariation {
	
	private final int agentIndex;
	private final int locationIndex;
	private final int variation;
	
	public AgentVariation( int agentIndex , int locationIndex, int variation ) {
		this.agentIndex = agentIndex;
		this.locationIndex = locationIndex;
		this.variation = variation;
	}

	public int getAgentIndex() {
		return this.agentIndex;
	}

	public int getLocationIndex() {
		return this.locationIndex;
	}

	public int getVariation() {
		return this.variation;
	}

}
