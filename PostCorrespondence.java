import java.io.*;
import java.util.*;

public class PostCorrespondence {
	
	public static void main(String[] args) throws IOException {
		
		// declare variable
		int maxQSize = 0; 	// maximum size of queue
		int maxStates = 0;	// maximum number of states
		int numStates = 0; 	// counter of total states
		int flag = 0;		// a flag indicate whether printing out the search states
		int numDomino;	// number of Dominos
		Set<Domino> dominoSet = new HashSet<Domino>(); // set to store all the non repeated dominos
		
		// check if file is provided
		if(args.length == 0) {
            System.out.println("File name not specified.");
            System.exit(1);
        }
		
		// read file
		File file = new File(args[0]); 
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr); 
		
		// parsing file
		String s; 
		for(int i = 0; i < 4; i++) {
			s = br.readLine();
			switch(i) {
			case 0:
				maxQSize = Integer.parseInt(s); 	// parsing the first number for frontier
			case 1:
				maxStates = Integer.parseInt(s); 	// parsing the second number for maxState
			case 2:
				flag = Integer.parseInt(s);			// parsing the third number for flag
			case 3:
				numDomino = Integer.parseInt(s);	// parsing the fourth number for numDomino
			
			}
		}
		while ((s = br.readLine()) != null) { 		// parsing the rest of the lines for dominos
		     StringTokenizer st = new StringTokenizer(s, " ");		// split string with space into tokens
		     String num = st.nextToken();							// domino number
		     String topDomino = st.nextToken();						// top string of domino
		     String bottomDomino = st.nextToken();					// bottom string of domino
		     Domino d = new Domino(topDomino, bottomDomino, num);	// construct domino 
		     dominoSet.add(d);										// add domino into Set
		}
		
		// <----------------------------------- BFS ----------------------------------->
		
		Queue<State> frontier = new LinkedList<State>();				// for BFS searching
		HashMap<String, State> explored = new HashMap<String, State>();	// for visited node
																		// key: diff, value: state
		System.out.println("\n=========== START BFS ============\n");
		
		// add initial state to frontier
		for(Domino domino : dominoSet) {
			if(domino.isValidDomino()) {
				LinkedList<Domino> dList = new LinkedList<Domino>();
				dList.add(domino);
				State initialState = new State(dList);
				frontier.add(initialState);
			}
		}
		
		// fill out frontier
		while(!frontier.isEmpty() && frontier.size() < maxQSize && numStates <= maxStates) {
			State currState = frontier.poll();
			LinkedList<Domino> dList = currState.getDominoList();
			for(Domino d : dominoSet) {
				dList.add(d);
				State newState = new State(dList);
				
				// if goal state is reached
				if(newState.isGoalState()) {
					System.out.println("======= GOAL STATE REACHED =======");
					System.out.println(newState.getState());
					System.out.println("----------------------------------");
					System.out.print("Domino Sequence: ");
					String domSeq = "";
					for(Domino ansDomino : newState.getDominoList()) {
						domSeq += ansDomino.getDominoNum() + ", ";
					}
					domSeq = domSeq.substring(0, domSeq.length() - 2);
					System.out.println(domSeq);
					System.out.println("========= SOLUTION FOUND =========");
					System.out.println();
					System.exit(0);
				}
				
				if(newState.isValidState() && !explored.containsKey(newState.getDiff())) {
					LinkedList<Domino> tempList = new LinkedList<Domino>();
					tempList.addAll(dList);
					State tempState = new State(tempList);
					
					frontier.add(tempState);
					explored.put(tempState.getDiff(), tempState);
					
					numStates ++;
					
					if(flag == 1) {
						System.out.println(tempState.getState());
						System.out.println("----------------------------------");
						System.out.print("Domino Sequence: ");
						String domSeq = "";
						for(Domino ansDomino : newState.getDominoList()) {
							domSeq += ansDomino.getDominoNum() + ", ";
						}
						domSeq = domSeq.substring(0, domSeq.length() - 2);
						System.out.println(domSeq);
						System.out.println();
					}
				}
				dList.removeLast();
			} // end for loop
		} // end while loop
		
		// <--------------------------- ITERATIVE DEEPENING --------------------------->
		
		Stack<State> idStateStack = new Stack<State>(); 	// for DFS searching
		explored = new HashMap<String, State>();			// reset explored
		
		System.out.println("\n== STARTING ITERATIVE DEEPENING ==\n");
		
		numStates = 0; // reset counter of total States
		while(!frontier.isEmpty()) {
			idStateStack.push(frontier.poll());
			
			while(!idStateStack.isEmpty() && numStates <= maxStates) {
				State currState = idStateStack.pop();
				LinkedList<Domino> dList = currState.getDominoList();
				System.out.println("TRYING DFS ON DOMINO SEQUENCE: ");
				String tryDomSeq = "";
				for(Domino ansDomino : currState.getDominoList()) {
					tryDomSeq += ansDomino.getDominoNum() + ", ";
				}
				tryDomSeq = tryDomSeq.substring(0, tryDomSeq.length() - 2);
				System.out.println(tryDomSeq);
				System.out.println("----------------------------------");
				System.out.println(currState.getState());
				System.out.println();
				
				for(Domino d: dominoSet) {
					dList.add(d);
					State newState = new State(dList);
					
					// if goal state is reached
					if(newState.isGoalState()) {
						System.out.println("======= GOAL STATE REACHED =======");
						System.out.println(newState.getState());
						System.out.println("----------------------------------");
						System.out.print("Domino Sequence: ");
						String domSeq = "";
						for(Domino ansDomino : newState.getDominoList()) {
							domSeq += ansDomino.getDominoNum() + ", ";
						}
						domSeq = domSeq.substring(0, domSeq.length() - 2);
						System.out.println(domSeq);
						System.out.println("========= SOLUTION FOUND =========");
						System.out.println();
						System.exit(0);
					}
					
					if(newState.isValidState() && !explored.containsKey(newState.getDiff())) {
						LinkedList<Domino> tempList = new LinkedList<Domino>();
						tempList.addAll(dList);
						State tempState = new State(tempList);
						
						idStateStack.push(tempState);
						explored.put(tempState.getDiff(), tempState);
						
						numStates ++;
						
						if(flag == 1) {
							System.out.println(tempState.getState());
							System.out.println("----------------------------------");
							System.out.print("Domino Sequence: ");
							String domSeq = "";
							for(Domino ansDomino : newState.getDominoList()) {
								domSeq += ansDomino.getDominoNum() + ", ";
							}
							domSeq = domSeq.substring(0, domSeq.length() - 2);
							System.out.println(domSeq);
							System.out.println();
						}
					}
					dList.removeLast();
				} // end for loop
			} // end inner while loop
		} // end outer while loop
		
		// <------------------------------ END SEARCHING ------------------------------>
		
		System.out.println("\n======== NO SOLUTION FOUND =======\n");
		System.exit(1);
		
	} // end main method
} // end class PostCorrespondence
