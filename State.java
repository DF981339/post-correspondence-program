import java.util.*;

public class State {		// node of graph
	private String diff;	// the trailing of the domino
	private LinkedList<Domino> dominoList;
	private String topString;
	private String bottomString;
	private Domino domino;
	
	// State constructor
	State(LinkedList<Domino> dList) {
	    this.dominoList = dList;
	    
	    for(Domino d : dominoList) {
	    	this.topString += d.getTop();
	    	this.bottomString += d.getBottom();
	    }
	    
		// find the trailing of domino
	    if(dominoList.size() == 0 || this.topString == this.bottomString ) {
	    	// no trailing when top and bottom are equal
	    	this.diff = "";
	    }
	    else if(this.topString.length() > this.bottomString.length()) {
	    	// find trailing when top is longer
	    	this.diff = "+" + this.topString.replace(this.bottomString, "");
	    }
	    else {
	    	// find trailing when bottom is longer
	    	this.diff = "-" + this.bottomString.replace(this.topString, ""); 
	    }
	    
	} // end State constructor
	
	// check if current state is valid
	public boolean isValidState() {
		if (topString.startsWith(bottomString) || bottomString.startsWith(topString)) { 
            return true; 
        } 
		else {
			return false;
		}
	}
	
	// check if current state is goal state
	public boolean isGoalState() {
		if(dominoList.size() < 1) {
			return false;
		}
		if(topString.length() != bottomString.length()) {
			return false;
		}
		return this.isValidState();
	}
	
	// return list of domino
	public LinkedList<Domino> getDominoList(){
		return dominoList;
	}
	
	public String getDiff() {
		return diff;
	}
	
	// human reading domino sequence
	public String getState() {
		String topCombination = "";
		String bottomCombination = "";
		String stateString = "";
		
		for(Domino d : dominoList) {
			topCombination += " | ";
			topCombination += d.getTop();
		}
		
		for(Domino d : dominoList) {
			bottomCombination += " | ";
			bottomCombination += d.getBottom();
		}
		
		topCombination = topCombination.substring(3);
		bottomCombination = bottomCombination.substring(3);
		
		stateString = topCombination + "\n" + bottomCombination;
		
		return stateString;
	}
	
} // end State class
