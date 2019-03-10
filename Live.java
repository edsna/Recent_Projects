import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.util.Queue; 
import java.util.Arrays;
import java.lang.Integer;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
//import org.apache.commons.lang3.ArrayUtils;
/*
 @Author: Edson ZAndamela
 @Date: February 9th 2019
 @Version: 1.0
*/


public class Live{
	
	static int[] GoalState = new int[9];
	static int[] boardArr = new int[9];	//Board Array-stor inpt
	static int[] Goal1 = {1,2,3,4,5,6,7,8,0}; 
	static int[] Goal2 = {1,2,3,8,0,4,7,6,5};
	static int currentState;
	static boolean goalTest;
	static boolean left = false;
	static boolean right = false;
	static boolean bottom = false;
	static boolean up = false;
	static final int lft = 1;
	static final int rgt = 2;
	static final int u = 3;
	static final int d = 4;
	static final int curr = 0;
	static int totalNumMovesBFS = 0;
	static int totalNodesBFS = 0;
	static int totalNumMovesA = 0;
	static int totalNodesAOOP = 0;
	static int totalNodesAManhattan = 0;
	

	//Shuffle input Array
			//PrintState(boardArr);
				  // Implementing Fisher–Yates shuffle
	  static void shuffleArray(int[] ar){
	    // If running on Java 6 or older, use `new Random()` on RHS here
		    Random rnd = ThreadLocalRandom.current();
		    for (int i = ar.length - 1; i > 0; i--){
		    	int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		    }
	  }

	//Finds Empty tile "0"
	public static int EmptyTile(int[] boardArr){
		for(int e = 0; e < boardArr.length; e++){
			if(boardArr[e] == 0){
				currentState = e;
			}
		}
		return currentState;
	}
	//Prints Initial state
	public static void PrintInitialState(int[] boardArr){
		int n = boardArr.length;
		int[] part1 = Arrays.copyOfRange(boardArr, 0, (n + 1)/3);
		int[] part2 = Arrays.copyOfRange(boardArr, (n - 6), n -3);
		int[] part3 = Arrays.copyOfRange(boardArr, (n - 3), n);
		System.out.print("Initial State is: ");
		System.out.println();
		System.out.println("\t" + "\t" + Arrays.toString(part1));
		System.out.println("\t" + "\t" + Arrays.toString(part2));
		System.out.println("\t" + "\t" + Arrays.toString(part3));
		System.out.println();
	}
	//Prints current state
	public static void PrintState(int[] boardArr){
		int n = boardArr.length;
		int[] part1 = Arrays.copyOfRange(boardArr, 0, (n + 1)/3);
		int[] part2 = Arrays.copyOfRange(boardArr, (n - 6), n -3);
		int[] part3 = Arrays.copyOfRange(boardArr, (n - 3), n);
		System.out.print("Current State is: ");
		System.out.println();
		System.out.println("\t" + "\t" + Arrays.toString(part1));
		System.out.println("\t" + "\t" + Arrays.toString(part2));
		System.out.println("\t" + "\t" + Arrays.toString(part3));
		System.out.println();
	}
	//Pringts Goal State
	public static void PrintGoalState(int[] GoalState){
		System.out.print("Goal State is: ");
		int n = GoalState.length;
		int[] part1 = Arrays.copyOfRange(GoalState, 0, (n + 1)/3);
		int[] part2 = Arrays.copyOfRange(GoalState, (n - 6), n -3);
		int[] part3 = Arrays.copyOfRange(GoalState, (n - 3), n);
		System.out.println();
		System.out.println("\t" + "\t" + Arrays.toString(part1));
		System.out.println("\t" + "\t" + Arrays.toString(part2));
		System.out.println("\t" + "\t" + Arrays.toString(part3));
		System.out.println();
	}
	
	//Swaps elements
	public static final <T> void swap (int[] a, int i, int j){
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	//Check if its possible to move empty tile up
	public static boolean moveUp(int[] boardArr){
		EmptyTile(boardArr);
		if(currentState == 3 || currentState == 4 || currentState == 5 || currentState == 6 || currentState == 7 || currentState == 8){
			up = true;
		}else{
			up = false;
		}
		return up;
	}
	//Moves empty tile up
	public static int[] slideUp(int[] boardArr){
		if((moveUp(boardArr)) == true){				//if can move up
			swap(boardArr, currentState, currentState-3);	//move or swap
		}
		return boardArr;		
	}
	//Checks of its possible to move empty tile to bottom
	public static boolean moveBot(int[] boardArr){
		EmptyTile(boardArr);
		if(currentState == 0 || currentState == 1 || currentState == 2 || currentState == 3 || currentState == 4 || currentState == 5){
			bottom = true;
		}else{
			bottom = false;
		}
		return bottom ;
	}
	//Moves empty tile to the bottom
	public static int[] slideBot(int[] boardArr){
		if((moveBot(boardArr)) == true){
			swap(boardArr, currentState, currentState+3);
		}
		return boardArr;
	}
	//Checks if its possible to move empty tile to the right
	public static boolean moveRight(int[] boardArr){
		EmptyTile(boardArr);
		if(currentState == 0 || currentState == 1 || currentState == 3 || currentState == 4 || currentState == 6 || currentState == 7){
			right = true;
		}else{
			right = false;
		}
		return right;
	}
	//Move emtpy tile to the right
	public static int[] slideRight(int[] boardArr){
		if((moveRight(boardArr)) == true){
			swap(boardArr, currentState, currentState+1);
		}
		return boardArr;
	}
	//Checks if its possible to move empty tile to the left
	public static boolean moveLeft(int[] boardArr){
		//static boolean left = false;
		EmptyTile(boardArr);
		if(currentState == 1 || currentState == 2 || currentState == 4 || currentState == 5 || currentState == 7 || currentState == 8){
			left = true;
		}else{
			left = false;
		}
		return left;
	}
	//Move empty tile to the left
	public static int[] slideLeft(int[] boardArr){
		if((moveLeft(boardArr)) == true){
			swap(boardArr, currentState, currentState-1);
		}
		return boardArr;
	}
	//Checks current VS Goal State
	public static boolean stateChecker( int[] boardArr, int[] GoalState){
		//First print states
		//PrintState(boardArr);
		//PrintGoalState(GoalState);
		//Compare states
		boolean arrComp = Arrays.equals(boardArr, GoalState);
			return arrComp;
	}
	//Returns the list of Possible Actions per state
	public static ArrayList<ArrayList<Integer>> Actions(int[] boardArr, ArrayList<Integer> acc){ //list of actions 
	//initialize list to store valid actions
        ArrayList<ArrayList<Integer>> actionsList = new ArrayList<ArrayList <Integer>>();
		EmptyTile(boardArr);		//returns location of zeroeth element
			if(moveLeft(boardArr) == true){
			ArrayList<Integer> inner = new ArrayList<Integer>();
				inner.addAll(acc);
				inner.add(lft);
				actionsList.add(inner);
			}
			if(moveRight(boardArr) == true){
			ArrayList<Integer> inner = new ArrayList<Integer>();
				inner.addAll(acc);
				inner.add(rgt);
				actionsList.add(inner);
			}
			if(moveUp(boardArr) == true){
			ArrayList<Integer> inner = new ArrayList<Integer>();
				inner.addAll(acc);
				inner.add(u);
				actionsList.add(inner);	
			}
			if(moveBot(boardArr) == true){
			ArrayList<Integer> inner = new ArrayList<Integer>();
				inner.addAll(acc);
				inner.add(d);
				actionsList.add(inner);
			}
			return actionsList;	//returns list of valid current actions from empty tile.
			//already checks if its valid
	}
	//Takes and executes actions (Might need to take boardArr as well) ArrayList<ArrayList<Integer>> actionsList //ArrayList Actions(boardArr, new ArrayList<Integer>())
	public static int[] executeActions(int[] boardArr, ArrayList<Integer> actionss){
		//May need to create an Array of strings to store all sequence of executed actions
		int[] newBoard = boardArr.clone();
			for(int c = 0; c < actionss.size(); c++){
				if((actionss.get(c)) == 1){
					slideLeft(newBoard);
					//May need to add action into array
				}else if((actionss.get(c)) == 2){
					slideRight(newBoard);
				}else if((actionss.get(c)) == 3){
					slideUp(newBoard);
				}else if((actionss.get(c)) == 4){
					slideBot(newBoard);
				}  
			}
			//System.out.println("ArrayList has: " + actionss);
		return newBoard;
	}
	//BFS
/*
	public static void breathFirst(){
		int totalMoves = 0;
		int totalSearchTrees = 0;
		while(!Q.isEmpty()){						//while queue is not empty
    				//EmptyTile(boardArr);					//find empty tile
    				Q.remove(Actions(boardArr, new ArrayList<Integer>()));	//remove from queue
    				//Actions(boardArr, new ArrayList<Integer>());		//return actionsL
				if(stateChecker(boardArr, GoalState)){			//if its equal to goal state
					executeActions(boardArr, actionsList);		//return board with executed actions
				}else{
					actionsList = Q.addAll(goalTest);		//Add actions to queue
				}
    			}
    			PrintState(boardArr);
}
*/


	static 	ArrayList<Integer> convertToArray(int[] boardInt){
		ArrayList<Integer> boardArrayList = new ArrayList<>(boardInt.length);
		for(int i =0;i<boardInt.length; i++){
			boardArrayList.add(boardInt[i]);
		}
		return boardArrayList;
	}
	static 	int[] convertToInt(ArrayList<Integer> boardInt){
		int[] boardArrayList = new int[boardInt.size()];
		for(int i =0;i<boardInt.size(); i++){
			boardArrayList[i] = boardInt.get(i);
		}
		return boardArrayList;
	}


	
	
	public static ArrayList<Integer> BreathFirstSearch(int[] boardArr, int[] GoalState){
		int totalMoves = 0;
		int totalSearchTrees = 0;
		//int[] final test = new int[9];
		Queue<ArrayList<Integer>> Q = new LinkedList<ArrayList<Integer>>();
		Q.add(new ArrayList<Integer>());
		totalNodesBFS +=1;
		HashSet<ArrayList<Integer>> hashSet = new HashSet<>();
		hashSet.add(convertToArray(boardArr));
		//Iterator<String> i = hashSet.iterator();
		while(!Q.isEmpty()){	//while queue is not empty
			ArrayList<Integer> Moves = Q.poll();	//remove from queue
			ArrayList<Integer> result = convertToArray(executeActions(boardArr, Moves));
			//boolean exist = hashSet.contains(result);
			//PrintState(boardArr);
			hashSet.add(result);
			if(stateChecker(convertToInt(result), GoalState)){
				return Moves;
			}else{
				ArrayList<ArrayList<Integer>> actionsList = Actions(convertToInt(result), Moves);
				for(int i = 0; i < actionsList.size(); i++ ){
					ArrayList<Integer> Moves2 = actionsList.get(i);
					ArrayList<Integer> result2 = convertToArray(executeActions(boardArr, Moves2));
					System.out.println("Moves has: " + Moves);
					
					//System.out.println("Moves 2 has: " + Moves2);
					//System.out.println("Result 2 has: " + result2);
					
					if(!hashSet.contains(result2)){
						Q.add(Moves2);
						totalNodesBFS +=1;
					}
					else{
						//System.out.println("Collision");	
					}	
				}				
			}
			//System.out.println("Elements of queue inside loop are: "+Q)		
    		}
  			//System.out.println("Actions are: " + );
    			return new ArrayList<Integer>();
	}

	public static int heuristic1(int[] boardArr, int[] GoalState){
		int distance = 0;
		for(int i = 0; i < boardArr.length; i++){
			if(boardArr[i] != GoalState[i]) {
				distance++;
			}
		}
		return distance;
	}
	public static int manhatanDist(int[] boardArr, int[] GoalState){
	int vertDist = 0;
	int horizDist = 0;
	int result = 0;
	List<int[]> GoalStateList = Arrays.asList(GoalState);
		for(int j = 0; j < boardArr.length; j++){
				vertDist = Math.abs(GoalStateList.indexOf(boardArr[j]) - j) / 3;
				horizDist = Math.abs(GoalStateList.indexOf(boardArr[j]) - j) % 3;
				result += horizDist + vertDist;	
		}
		return result;
	}
	//takes arralist int and the arrayylist integer goes all pQueue compares each element of the return cost of first - second parameter
	//cost = length of moves + heuristic costs of moves
	public static class compareActionsManhattan implements Comparator<ArrayList<Integer>>{
		public int compare(ArrayList<Integer> m1, ArrayList<Integer> m2){
			int m2Cost = m2.size() + manhatanDist(executeActions(boardArr, m2) , GoalState);
			int m1Cost = m1.size() + manhatanDist(executeActions(boardArr, m1) , GoalState); 
			return m1Cost - m2Cost;
		}
	}
	
	public static class compareActionsOOP implements Comparator<ArrayList<Integer>>{
		public int compare(ArrayList<Integer> m1, ArrayList<Integer> m2){
			int m2Cost = m2.size() + heuristic1(executeActions(boardArr, m2) , GoalState);
			int m1Cost = m1.size() + heuristic1(executeActions(boardArr, m1) , GoalState);
			return m1Cost - m2Cost;
		}
	}

	public static ArrayList<Integer> aStarOOP(int[] boardArr, int[] GoalState){

		PriorityQueue<ArrayList<Integer>> pQueue = new PriorityQueue<ArrayList<Integer>>(1 , new compareActionsOOP());
		pQueue.add(new ArrayList<Integer>());

		HashSet<ArrayList<Integer>> hashSet = new HashSet<>();
		hashSet.add(convertToArray(boardArr));
		totalNodesAOOP+=1;
		//Iterator<String> i = hashSet.iterator();
		while(!pQueue.isEmpty()){	//while queue is not empty
			ArrayList<Integer> Moves  = pQueue.poll();
			ArrayList<Integer> result = convertToArray(executeActions(boardArr, Moves));
			//boolean exist = hashSet.contains(result);
			//PrintState(boardArr);
			hashSet.add(result);
			if(stateChecker(convertToInt(result), GoalState)){
				return Moves;
			}else{
				ArrayList<ArrayList<Integer>> actionsList = Actions(convertToInt(result), Moves);
				for(int i = 0; i < actionsList.size(); i++ ){
					ArrayList<Integer> Moves2 = actionsList.get(i);
					ArrayList<Integer> result2 = convertToArray(executeActions(boardArr, Moves2));
					System.out.println("Moves has A*: " + Moves);
					//System.out.println("Moves 2 has: " + Moves2);
					//System.out.println("Result 2 has: " + result2);
					
					if(!hashSet.contains(result2)){
						pQueue.add(Moves2);
						totalNodesAOOP+=1;
					}
					else{
						//System.out.println("Collision");	
					}	
				}				
			}
			//System.out.println("Elements of queue inside loop are: "+Q)		
    		}
		return new ArrayList<Integer>();
    		
	}//End of A*

	public static ArrayList<Integer> aStarManhattan(int[] boardArr, int[] GoalState){

		PriorityQueue<ArrayList<Integer>> pQueue = new PriorityQueue<ArrayList<Integer>>(1 , new compareActionsManhattan());
		pQueue.add(new ArrayList<Integer>());
		totalNodesAManhattan += 1;

		HashSet<ArrayList<Integer>> hashSet = new HashSet<>();
		hashSet.add(convertToArray(boardArr));
		//Iterator<String> i = hashSet.iterator();
		while(!pQueue.isEmpty()){	//while queue is not empty
			ArrayList<Integer> Moves  = pQueue.poll();
			ArrayList<Integer> result = convertToArray(executeActions(boardArr, Moves));
			//boolean exist = hashSet.contains(result);
			//PrintState(boardArr);
			hashSet.add(result);
			if(stateChecker(convertToInt(result), GoalState)){
				return Moves;
			}else{
				ArrayList<ArrayList<Integer>> actionsList = Actions(convertToInt(result), Moves);
				for(int i = 0; i < actionsList.size(); i++ ){
					ArrayList<Integer> Moves2 = actionsList.get(i);
					ArrayList<Integer> result2 = convertToArray(executeActions(boardArr, Moves2));
					System.out.println("Moves has A*: " + Moves);
					//System.out.println("Moves 2 has: " + Moves2);
					//System.out.println("Result 2 has: " + result2);
					
					if(!hashSet.contains(result2)){
						pQueue.add(Moves2);
						totalNodesAManhattan += 1;

					}
					else{
						//System.out.println("Collision");	
					}	
				}				
			}
			//System.out.println("Elements of queue inside loop are: "+Q)		
    		}
		return new ArrayList<Integer>();
    		
	}//End of A*
	

	//funct (board, list of actions){for each action{move board}return most recent or last updated board}
	//Functional Programming
	
	public static void main(String[]args){
		ArrayList<ArrayList<Integer>> actionsList;
		//Reading Input from external file

		File file = new File("data.in");
				 try {	
				 	//int[] boardArr = new int[9];	
					Scanner Sc = new Scanner(file);
					for(int i = 0; i < boardArr.length; i++){
						if(Sc.hasNext()){
							boardArr[i] = Sc.nextInt();
						}
					}
		      }
		      catch (ArithmeticException e) { 
		      }
		      catch (Exception e) {
		      			boardArr[0] = 0;
		      			boardArr[1] = 1;
		      			boardArr[2] = 2;
		      			boardArr[3] = 3;
		      			boardArr[4] = 4;
		      			boardArr[5] = 5;
		      			boardArr[6] = 6;
		      			boardArr[7] = 7;
		      			boardArr[8] = 8;
			  		//boardArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8};
					      		shuffleArray(boardArr);
							for(int i = 0; i < boardArr.length; i++){
								System.out.print(boardArr[i] + " ");
							}
								System.out.println();
		      	}//End of catch		
			//Working on Parity Gerator
			int tracker = 0;
			for(int k = 0; k < boardArr.length; k++){
				for(int w = k+1; w < boardArr.length; w++){
					if((boardArr[k] < boardArr[w]) && (boardArr[k] != 0)){
						tracker++;
					}
				}
			}
			System.out.println("Parity = " + tracker);
			if((tracker%2) == 0){
				GoalState = Goal1;
			}else{
				GoalState = Goal2;
			}
						
			//PrintGoalState(GoalState);
			
			//EmptyTile(boardArr);
			System.out.println("Emtpy tile is at location: " + EmptyTile(boardArr));
			//System.out.println("Emtpy tile in Goal board is at location: " + EmptyTile(GoalState));
			//moveUp(boardArr);
			//moveLeft(boardArr);
			//moveBot(boardArr);
			//moveRight(boardArr);
			//moveRight(boardArr);
			PrintInitialState(boardArr);
			PrintState(boardArr);
			PrintGoalState(GoalState);
			//System.out.println("Emtpy tile is at location: " + EmptyTile(boardArr));
			//stateChecker(currentState, boardArr, GoalState);
			actionsList = Actions(boardArr, new ArrayList<Integer>());
        		//System.out.println("List of valid ations is: " + actionsList); 
			//Implementing Queue
			Queue<ArrayList<Integer>> Q = new LinkedList<ArrayList<Integer>>();
        		Q.addAll(actionsList);		//Add actions' elements into queue
        		 // Display contents of the queue. 
    			//System.out.println("Elements of queue: "+Q);
    			//PrintState(boardArr);
    			//BreathFirstSearch();
    			//System.out.println("********Working on BFS*******");
			//breathFirst();
			//Q.poll();
			//System.out.println("Queue now has: "+Q);

    			
    		//	List<E> result = new ArrayList<E>();
    			
    			//ArrayList<Integer> finaly = BreathFirstSearch(boardArr);
    			//PrintState(boardArr);
    				ArrayList<Integer> end = BreathFirstSearch(boardArr, GoalState);
    			//PrintState(boardArr);
    				System.out.println("BFS has: " + end);
        		System.out.println("Total number of explored nodes BFS is: " + totalNodesBFS);
    			//ArrayList<ArrayList<Integer>> actionsList
    			//System.out.println("After slideUp: "+ slideUp(boardArr));
    			
    			 //while queue is not empty
    			 //System.out.println("Index is: " + findIndex(GoalState));
   			//PrintInitialState(boardArr);
			//PrintState(boardArr);
			//PrintGoalState(GoalState);
			//System.out.println("Moves222 has: " + Moves);
			    	System.out.println("********Working on A********");
			ArrayList<Integer> oopResult = aStarOOP(boardArr,GoalState);


			ArrayList<Integer> manhattanResult= aStarManhattan(boardArr,GoalState);
			System.out.println("Total number of explored nodes A* is: " + totalNodesAOOP);
			//PrintState(boardArr);

			System.out.println(oopResult);
			System.out.println(manhattanResult);		
	}//End of MAIN


}//End of Live

		/*
		for (int i = 0; i < mainArrayList.size(); i++) {

			for (int k = 0; k < mainArrayList.get(i).size(); k++) {

			System.out.print(" " + mainArrayList.get(i).get(k));

if((actionsList.get(a)) == 1){
				System.out.println("Yeahhh Left Success!!!");
			}
}
		*/
		//actionsList.get(list_index)
		//System.out.println("ArrayList has: " + actionsList);
