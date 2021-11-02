// Yingyun(Alice) Miao
// 1/13/19
// CSE143
// TA: Chin Yeoh
// Assignment #1
//
// The class called LetterInventory is used to keep track of 
// an inventory of letters of the alphabet. It keeps count of
// all the letters.

public class LetterInventory{
   private int[] elementData; // list of integers
   private int size;          // the total number of letters
   
   //class constant stands for 26 letters
   public static final int DEFAULT_CAPACITY = 26; 
   
   // post: constructs a list of default capacity, takes in a string data,
   //       records the number of each letter in data. It ignores the case
   //       of the letters and anything that is not an alphabetic character
   public LetterInventory(String data){
      elementData = new int[DEFAULT_CAPACITY];
      data = data.toLowerCase();
      for (int i = 0; i < data.length(); i++) {
         char ch = data.charAt(i);
         if (ch >= 'a' && ch <= 'z') {
            elementData[ch - 'a']++;
            size++;
         }
      }
   }
 
   //post: returns the total number of counts in the inventory
   public int size() {
      return size;
   }
 
   //post: returns ture if the list has 0 counts, false otherwise
   public boolean isEmpty() {
      return size == 0;
   }
 
   // pre: takes in a character letter, throws an 
   //      IllegalArgumentException if the letter is nonalphabetic
   // post: returns the integer at the desired index (represents
   //       the character letter that is case-insensitive) in the list
   public int get(char letter) {
      letter = checkLetter(letter);
      return elementData[letter - 'a'];
   }
 
   // pre: takes in a character letter, an integer value, throws an
   //      IllegalArgumentException if the letter is nonalphabetic.
   //      The integer value that is taken in is greater or equal to 
   //      0, otherwise throws an IllegalArgumentException if the value
   //      is less than 0
   // post: sets the count for the given letter that is case-insensitive
   //       to the given value in the list 
   public void set(char letter, int value) {
      letter = checkLetter(letter);
      if (value < 0) {
         throw new IllegalArgumentException();
      }
      size += value - elementData[letter - 'a'];
      elementData[letter - 'a'] = value;
   }
 
   // post: returns a String representation of the 
   //       inventory with the letters all in lowercase
   //       and in sorted order and surrounded by square
   //       brackets
   public String toString() {
      String result = "[";
      for (int i = 0; i < DEFAULT_CAPACITY; i++) {
         for (int j = 0; j < elementData[i]; j++) {
            result += (char)(i + 'a');
         }
      }
      result += "]";
      return result;
   }
 
   // post: constructs and returns a new LetterInventory object
   //       that represents the sum of this letter inventory and 
   //       the other letterInventory
   public LetterInventory add (LetterInventory other) {
      LetterInventory sum = new LetterInventory("");
      for (int i = 0; i < DEFAULT_CAPACITY; i++) {
         int newValue = this.elementData[i] + other.elementData[i];
         sum.set((char) ('a' + i), newValue);
      }
      return sum;
   }
 
   // pre: this letter inventory has a bigger size than the other given 
   //      LetterInventory, otherwise return null
   // post: constructs and returns a new LetterInventory object that
   //       represents the result of subtracting the other inventory
   //       from this inventory
   public LetterInventory subtract (LetterInventory other) {
      LetterInventory difference = new LetterInventory("");
      for (int i = 0; i < DEFAULT_CAPACITY; i++) {
         int newValue = this.elementData[i] - other.elementData[i];
         if (newValue < 0) { 
            return null;
         }
         difference.set((char) ('a' + i), newValue);
      }
      return difference;
   } 
 
   // post: throws an IllegalArgumentException if the given letter
   //       is not a legal letter of the current list, otherwise
   //       return the lower-cased letter
   private char checkLetter(char letter) {
      letter = Character.toLowerCase(letter);
      if (letter < 'a' || letter > 'z') {
         throw new IllegalArgumentException();
      }
      return letter;
   } 
}