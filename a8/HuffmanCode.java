// Yingyun(Alice) Miao
// 3/9/19
// CSE143
// TA: Chin Yeoh
// Assignment #8
//
// The class called HuffmanCode compresses text files using Huffman
// coding based on the frequency of characters. It envolves structures
// called priority queues and binary trees, and encodes as well as decodes
// the files.
 
import java.util.*;
import java.io.*;

public class HuffmanCode{
   private HuffmanNode overallRoot;
   
   // takes in an integer array 'frequencies', the indices of
   // the array parameter represent corresponding ASCII character
   // values, the values of the array parameter represent the
   // counts of the corresponding characters, characters corresponding
   // to frequencies of 0 are ignored. Constructs and initializes
   // the HuffmanCode with the given 'frequencies' number of
   // occurrences of the character
   public HuffmanCode(int[] frequencies) {
      Queue<HuffmanNode> sorted = new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < frequencies.length; i++) {
         if (frequencies[i] > 0) {
            sorted.add(new HuffmanNode(i, frequencies[i]));
         }
      }
      while (sorted.size() > 1) {
         HuffmanNode left = sorted.remove();
         HuffmanNode right = sorted.remove();
         sorted.add(new HuffmanNode(-1, left.frequency + right.frequency, 
                                    left, right));
      }
      overallRoot = sorted.remove();
   }
   
   // takes in a Scanner input, which is a previously
   // constructed code from a .code file that contains
   // the frequencies of the characters and their representing
   // codes, initialize a new HuffmanCode. Assume the Scanner
   // is not null and always contians data encoded in legal,
   // valid standard format
   public HuffmanCode(Scanner input) {
      overallRoot = new HuffmanNode(-1, 0);
      while(input.hasNextLine()) {
         int n = Integer.parseInt(input.nextLine());
         HuffmanNode root = overallRoot;
         String code = input.nextLine();
         for (int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '0') {
               if(root.left == null) {
                  root.left = new HuffmanNode(n, 0);
               }
               root = root.left;
            } else {
               if (root.right == null) {
                  root.right = new HuffmanNode(n, 0);
               }
               root = root.right;
            }
         }
      }
   }
   
   // takes in a PrintStream output, stores the current
   // huffman codes to the given output stream in standard 
   // format
   public void save(PrintStream output) {
      save(output, overallRoot, "");
   }
   
   // a helper method that takes in a PrintStream output,
   // a HuffmanNode root, and a String binary, saves the 
   // current huffman codes with the counts of the characters
   // and their representing codes into the output file
   private void save(PrintStream output, HuffmanNode root, String binary) {
      if (root != null) {
         if (root.left == null && root.right == null) {
            output.println(root.character);
            output.println(binary);
         } else {
            save(output, root.left, binary + "0");
            save(output, root.right, binary + "1");
         }
      }
   }
   
   // takes in a BitInputStream input and a PrintStream output,
   // reads individual bits from the input stream and writes the 
   // corresponding characters to the output. It would stop reading when
   // the BitInputStream is empty. Assume the input stream contains a 
   // legal encoding of characters for the tree's huffman code
   public void translate(BitInputStream input, PrintStream output) {
      while (input.hasNextBit()) {
         HuffmanNode root = overallRoot;
         while (root.left != null) {
            int bit = input.nextBit();
            if (bit == 0) {
               root = root.left;
            } else {
               root = root.right;
            }
         }
         if (root.right == null && root.left == null) {
            output.write(root.character);
         }
      }
   }
   
   // The class called HuffmanNode implements the comparable interface
   // for the priority queue and stores a single node of a binary tree
   // with the representing number of the character and its frequency.  
   private static class HuffmanNode implements Comparable<HuffmanNode>{
      public int character;
      public int frequency;
      public HuffmanNode left;
      public HuffmanNode right;
      
      // takes in an integer character and a integer frequency,
      // constructs a leaf node with the given ASCII value and 
      // frequency
      public HuffmanNode(int character, int frequency) {
   		this(character, frequency, null, null);
      }
      
      // takes in an integer character, an integer frequency,
      // a HuffmanNode left and a HuffmanNode right, constructs
      // a branch node with the given character, frequency, left
      // subtree and right subtree
   	public HuffmanNode(int character, int frequency,
   	                   HuffmanNode left, HuffmanNode right) {
         this.character = character;
         this.frequency = frequency;
         this.left = left;
         this.right = right;
      }
      
      // takes in a HuffmanNode other, returns the difference between 
      // the frequencies of this HuffmanNode and other HuffmanNode and
      // sorts the characters with frequency
      public int compareTo(HuffmanNode other) {
         return frequency - other.frequency;
      }
   }
}