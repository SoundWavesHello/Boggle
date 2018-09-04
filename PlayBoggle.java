/*
 * 		Author: Stephen Majercik
 * 		Edited by Kevin Lane
 * 		7 November 2016
 * 
 * Creates a Boggle game and initiates play.
 * 
 */

import java.util.Scanner;

public class PlayBoggle {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);	
		
		System.out.println("Welcome to Boggle!");
		
		System.out.println("Please enter the dictionary"
				+ " filename without the \".txt\" extension: ");
		String dictFile = scan.nextLine();
		
		Dictionary dict = new Dictionary(dictFile);
		
		System.out.println("Dictionary size is " + dict.getSize());
		
		System.out.println();
		
		System.out.println("Please enter the board file name"
				+ " without the \".txt\" extension: ");
		
		String filename = scan.nextLine();
		
		
		Boggle boggleBoard = new Boggle(filename, dict);
		boggleBoard.play();
		
		scan.close();

	}

}