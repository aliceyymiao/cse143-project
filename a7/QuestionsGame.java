// Yingyun(Alice) Miao
// 3/4/19
// CSE143
// TA: Chin Yeoh
// Assignment #7
//
// This class called QuestionsGame stores the questions and answers of
// a yes/no guessing game the computer plays, and writes the tree to 
// an output file. It also asks the user a series of yes/no questions 
// and takes the new information that the user inputs from a lost game
// and uses it to replace the old incorrect answer.

import java.io.*;
import java.util.*;

public class QuestionsGame {
	private QuestionNode overallRoot;
   private Scanner console;
   
   // initializes a new QuestionsGame object with
   // a single object "computer" 
   public QuestionsGame() {
      console = new Scanner(System.in);
      overallRoot = new QuestionNode("computer");
   }
   
   // takes in a Scanner input that is linked to the
   // file, replace the current tree with a new tree 
   // with the information in the input file that is
   // assumed legal and in standard format
   public void read(Scanner input) {
      overallRoot = readHelper(input);
   }
   
   // a helper method that takes in a Scanner input that
   // is linked to the file, and replaces the current tree 
   // with a new tree with the information in the input
   // file that is assumed legal and in standard format
	private QuestionNode readHelper(Scanner input) {
		String line = input.nextLine();
		QuestionNode root = new QuestionNode(input.nextLine());
		if(line.equals("Q:")) {
			root.left = readHelper(input);
			root.right = readHelper(input);
		}
		return root;
	}
   
   // takes in a PrintStream output, writes the current
   // tree into an output file that is in standard format
	public void write(PrintStream output) {
		writeHelper(output, overallRoot);
	}
   
   // takes in a PrintStream output and a QuestionNode root,
   // writes the current tree to an output file that is in
   // a standard format
	private void writeHelper(PrintStream output, QuestionNode root) {
		if (root != null) {
         if (root.right != null) {
			   output.println("Q:");
		   } else {
			   output.println("A:");
         }
         output.println(root.text);
         writeHelper(output, root.left);
         writeHelper(output, root.right);
		}
	}
   
   // ask the user a series of yes/no questions until 
   // reaching an answer object to guess. If the computer 
   // wins the game, prints a message saying so; if fails, 
   // ask the user what object they were thinking of, a 
   // question to distinguish that object from the player's 
   // guess, and whether the player's object is yes or no 
   // answer for that question 
   public void askQuestions() {
      overallRoot = askQuestions(overallRoot);
   }
   
   // a helper method that takes in a QuestionNode root, asks
   // the user questions based on the user's answers until it 
   // reaches to an answer. If this answer is correct, the computer
   // wins; if not, the user wins and expands the tree with the
   // correct answer and new question to distinguish the object from 
   // the others.
   private QuestionNode askQuestions(QuestionNode root) {
      if (root.left == null && root.right == null) {
         return askQuestionsHelper(root);
	   } else {
         if(yesTo(root.text)) {
            root.left = askQuestions(root.left);
         } else {
            root.right = askQuestions(root.right);
         }
         return root;
      }
   } 
   
   // a helper method that takes in a QuestionNode root, which is the 
   // incorrect guess by the computer, asks for the correct answer and
   // add it as well as a new question to the current tree to distinguish 
   // the answer from the others
   private QuestionNode askQuestionsHelper(QuestionNode root) {
		if(yesTo("Would your object happen to be " + root.text + "?")) {
			System.out.println("Great, I got it right!");
      } else { 
         System.out.print("What is the name of your object? ");
         String name = console.nextLine();
         System.out.println("Please give me a yes/no question that");
         System.out.println("distinguishes between your object");
         System.out.print("and mine--> ");
         String question = console.nextLine();
         QuestionNode answerNew = new QuestionNode(name);
         QuestionNode questionNew = new QuestionNode(question);
         if (yesTo("And what is the answer for your object?")) {
            questionNew.left = answerNew;
            questionNew.right = root;
         } else {
            questionNew.left = root;
            questionNew.right = answerNew;
         }
         root = questionNew;
      }
      return root;
   }


   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
       System.out.print(prompt + " (y/n)? ");
       String response = console.nextLine().trim().toLowerCase();
       while (!response.equals("y") && !response.equals("n")) {
           System.out.println("Please answer y or n.");
           System.out.print(prompt + " (y/n)? ");
           response = console.nextLine().trim().toLowerCase();
       }
       return response.equals("y");
   }

   // This class called QuestionNode stores a single node
   // of a binary tree that has Strings of question or answer. 
   private static class QuestionNode {
      public String text;
      public QuestionNode left;
      public QuestionNode right;
      
      // takes in a String text, constructs a new leaf node
      // with the given text
      public QuestionNode(String text) {
         this(text, null, null);  
      }
      
      // takes in a String text, a QuestionNode left and a QuestionNode right,
      // constructs a branch question node with given text, left subtree and 
      // right subtree
      public QuestionNode(String text, QuestionNode left, QuestionNode right) {
         this.text = text;
         this.left = left;
         this.right = right;
      }
   }
}
