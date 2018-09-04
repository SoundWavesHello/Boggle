/*
 * 
 * 		Author: Kevin Lane
 * 		Lab 7
 * 		Last Modified: 11/7/16
 * 
 * 		This class contains methods to do the following:
 * 
 * 		1) Construct a Boggle class object with
 * 			no parameters
 * 
 * 		2) Construct a Boggle class object when
 * 			given the name of the file to read into
 * 			a boggle board and the dictionary
 * 
 * 		3) Play a game of boggle against the
 * 			computer (and lose tragically)
 * 
 * 		4) Check whether or not the user's word
 * 			can be played and if it cannot be
 * 			played, explain why
 * 
 * 		5) Cycle through each character on the
 * 			boggle board to find all possible
 * 			words on the board
 * 
 * 		6) Find any possible words starting
 * 			from coordinates on the board
 * 
 * 		7) Read a file into the boggle board
 * 
 * 		8) Print out the boggle board
 * 
 * 		9) Print out words found by the user
 * 
 * 		10) Print out words found by the computer
 * 			and mark whether or not it 
 * 		
 * 		11) Calculate the total score of words
 *			from a given list
 * 
 */


// import utilities

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;


public class Boggle {

	
	// create constants
	
	public static final int DIMENSION = 4;
	public static final char BLANK = ' ';
	
	// create variables
	
	private ArrayList<String> computerWords = new ArrayList<String>();
	private ArrayList<String> userWords = new ArrayList<String>();
	private Dictionary realWords;
	private char[][] board = new char[DIMENSION][DIMENSION];
	
	
	// Purpose: constructs a Boggle object
	// Parameters: none
	// Return: none
	
	public Boggle(){
		realWords = new Dictionary();
	}
	
	
	// Purpose: constructs a Boggle object
	// Parameters: text file name for the puzzle
	// Return: none
	
	public Boggle(String boardFile, Dictionary dict){
		realWords = dict;
		readBoard(boardFile);
	}
	
	
	// Purpose: Allows the user to play a game of 
	//			boggle against a computer
	// Parameters: none
	// Return: none
	
	// Purpose: allows the user to play a game of boggle
	// Parameters: none
	// Return: none
	
	public void play(){
		// print out the board for the player to see
		printBoard();
		
		// create an ArrayList of all possible words
		// on the board
		solve();
		Collections.sort(computerWords);
					
		
		// create a new Scanner type object 
		// to get user input
		Scanner scan = new Scanner(System.in);
		
		// prompt user
		System.out.println("Would you like to play a game? (y/n) ");
		
		String answer = scan.nextLine();
		
		// if they'd like to play
		if (answer.equalsIgnoreCase("y")){
			
			// allow them to enter words
			System.out.println("Enter as many words as you would like,"
					+ " one to a line.");
			System.out.println("Enter an exclamation point when you are done.");
			
			String word = "";
			
			// give them a way to exit the loop
			while (!word.equals("!")){
				word = scan.nextLine();
				
				if(word.equals("!")){
					break;
				}
				
				// check if the word entered
				// is valid and gives a 
				// reason why if it isn't
				checkUser(word);
				
			}
			
			// get some white space
			System.out.println();
			System.out.println();
			
			// print out the user's words
			printUserWords();
			
			// get some white space
			System.out.println();
			
			
			// print out the score
			System.out.println("Score: " + calculateScore(userWords));
			
		}
		
		// if the user enters a letter other than y or n
		else if (!answer.equalsIgnoreCase("n")){
			System.out.println("Invalid input; goodbye");
			scan.close();
			return;
		}
		
		// if the user did not want to play,
		// then only the computer's results
		// will be shown, but if they did want
		// to play, this will be shown after
		// their own input in the conditional above
		
		// get some whitespace
		System.out.println();
		
		// print out the computer's words
		printComputerWords();
		
		System.out.println();
		
		// print out the score
		System.out.println("Score: " + calculateScore(computerWords));
		
		// close the Scanner so eclipse
		// doesn't get angry
		scan.close();
		
	}
	
	
	// Purpose: check if the user's word is valid;
	//			if it isn't explain why
	// Parameters: the word to check
	// Return: none
	
	// Purpose: check if the user's word is valid
	//			and give a reason if it is invalid
	// Parameters: the word you're checking
	// Return: none
	
	public void checkUser(String word){
		
		// check to see if it's actually a word
		if (realWords.binarySearch(word)){
			
			// check to see if word is long enough
			if (word.length() >= 3){
				
				// check to see if the word is on the board
				if (computerWords.contains(word)){
					
					// check to see if word already used
					if (userWords.contains(word)){
						System.out.println("Invalid word: "
								+ "already used");
					}
					
					// if it is a word on the board that was not
					// used, add it to the userWords list
					else{
						System.out.println("Nice job!");
						userWords.add(word);
					}
				}
				
				else{
					System.out.println("Invalid word: "
							+ "not on the board");
				}
			}
			
			else {
				System.out.println("Invalid word: "
						+ "not long enough");
			}
			
		}
		
		else{
			System.out.println("Invalid word: "
					+ "not in dictionary");
		}
	}
	
	
	// Purpose: find all the words in the 
	//			boggle board
	// Parameters: none
	// Return: none
	
	// Purpose: goes through the current boggle 
	//			puzzle to solve it
	// Parameters: none
	// Return: none
	
	public void solve(){
		for(int i = 0; i < DIMENSION; i ++){
			for(int j = 0; j < DIMENSION; j ++){
				findWords(i, j, "");
			}
		}
	}
	
	
	// Purpose: find all possible words starting at
	//			a given indice
	// Parameters: the current coordinates and the 
	//			current string
	// Return: none

	// Purpose: any possible words that start
	//			from one space
	// Parameters: the x and y coordinates and
	//				the current String
	// Return: none
	
	public void findWords(int x, int y, String currString){
		
		// check to see if the current word is real, 3
		// or more characters, and if it already has been found
		// if it is, append it to the list of words
		
		if(realWords.binarySearch(currString) && 
				!computerWords.contains(currString)
				&& currString.length() >= 3){
			computerWords.add(currString);
		}
		
		// stop if it reaches somewhere outside of the array
		
		if (x < 0 || x >= DIMENSION || y < 0 
				|| y >= DIMENSION){
			return;
		}
		
		// if there is nothing at the array
		
		if (board[x][y] == BLANK){
			return;
		}
		
		// checks to see if there is anything with 
		// this prefix; if there isn't, then there's
		// no point in continuing this recursive
		// branch
		
		if(!realWords.findPrefix(currString)){
			return;
		}
		
		// set the current string equal to itself
		// plus the current character
		// if the character is 'q', add 'u' to
		// the current string as well
		
		if(board[x][y] == 'q'){
			currString += board[x][y] + 'u';
		}
		else{
			currString += board[x][y];
		}
		
		
		// erase that character on the board to prevent
		// it from going backwards
		
		char temp = board[x][y];
		board[x][y] = BLANK;
		
		// move in all 8 directions from the current coordinates
		
		for (int xOff = -1; xOff <= 1; xOff ++){
			for(int yOff = -1; yOff <= 1; yOff ++){
				if (xOff != 0 || yOff != 0){
					findWords(x + xOff, y + yOff, currString);
				}
			}
		}
		
		// reset the characters on the board
		
		board[x][y] = temp;
	}
	
	
	// Purpose: read a text file into a two
	//			dimensional array that is the 
	//			boggle board
	// Parameters: the text file name
	// Return: none

	// Purpose: read a text file into a two
	//			dimensional array that is
	//			the Boggle board
	// Parameters: the text file name
	// Return:	none
	
	public void readBoard(String textFile){
		try {
			
			// the board files are found in a folder
			// called Files/boards-and-solutions
			String boardFileName = "Files/boards-and-solutions/" 
			+ textFile + ".txt";
			
			// create a Scanner object to go through the file
			Scanner scan = new Scanner(new File(boardFileName));
			
			// iterate through the lines in the file
			for (int i = 0; i < DIMENSION; i++) {
				
				// if the scanner has another item
				if (scan.hasNext()){
					
					String line = scan.next();
					
					// iterate through the characters on
					// each line
					for (int j = 0; j < DIMENSION; j ++){
						
						// add characters to the array
						board[i][j] = line.charAt(j);
					}
					
				}
			}
			
			scan.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
			

	}

	
	// Purpose: print out the board
	// Parameters: none
	// Return: none

	// Purpose: print out the board
	// Parameters: none
	// Return: none
	
	public void printBoard(){
		for(int i = 0; i < DIMENSION; i ++){
			System.out.println(Arrays.toString(board[i]));
		}
	}
	
	
	// Purpose: print out words found by
	// 			the user
	// Parameters: none
	// Return: none

	// Purpose: print out words the user found
	// Parameters: none
	// Return: none
	
	public void printUserWords(){
		System.out.println("Human words:");
		System.out.println("=============");
		
		// alphabetize words
		Collections.sort(userWords);
		
		// print out the words found by the user
		for (int i = 0; i < userWords.size(); i ++){
			System.out.println(userWords.get(i));
		}
	}
	
	
	// Purpose: print out words found by
	//			the computer and mark whether
	//			or not it was already found by
	//			the user
	// Parameters: none
	// Return: none

	// Purpose: print out words found by the computer
	// 			and whether or not the user already
	//			found those words
	// Parameters: none
	// Return: none
	
	public void printComputerWords(){
		System.out.println("Computer words:");
		System.out.println("================");
		
		// print out words found by computer
		for (int i = 0; i < computerWords.size(); i ++){
			
			// if word was found by human, denote that
			if (userWords.contains(computerWords.get(i))){
				System.out.printf("%-12s%-24s\n", computerWords.get(i),
						"disallowed: found by human");
				
				// clear it from the list so it doesn't count
				// towards the total score
				computerWords.remove(i);
			}
			
			// if word wasn't found by human, just print it out
			else{
				System.out.println(computerWords.get(i));
			}
		}
	}
	
	
	// Purpose: calculate the total score of all the words
	//			in a given ArrayList of Strings
	// Parameters: the ArrayList of Strings to calculate
	//			the score of
	// Return: the total score

	// Purpose: calculate the score of the words in
	//			an ArrayList of Strings
	// Parameters: the ArrayList of Strings to calculate
	//			the score of
	// Return: the score
	
	public int calculateScore(ArrayList<String> array){
		
		// create score counter
		int score = 0;
		
		// iterate through items in array
		for (int i = 0; i < array.size(); i ++){
			int length = array.get(i).length();
			
			// check length of word and
			// add an appropriate number
			// to the score
			
			if (3 == length || length == 4){
				score ++;
			}
			else if (5 == length){
				score += 2;
			}
			else if (6 == length){
				score += 3;
			}
			else if (7 == length){
				score += 5;
			}
			else if (8 <= length){
				score += 11; 
			}
		}
		
		return score;
	}
}
