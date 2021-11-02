// Yingyun(Alice) Miao
// 2/25/19
// CSE143
// TA: Chin Yeoh
// Assignment #6
//
// This class called AnagramSolver uses a dictionary to 
// find all combinations of words that have the same letters
// as a given phrase that the user will input.
 
import java.util.*;
 
public class AnagramSolver {
   private List<String> dictionaryCopy;
   private Map<String, LetterInventory> inventories;
 
   // post: takes in a List dictionary, which is a List of Strings
   //       that is a nonempty collection of nonempty sequences
   //       of letters and contains no duplicates, makes a copy
   //       of the dictionary, converts dictionary words into
   //       inventories and stores them in the inventories
   public AnagramSolver(List<String> dictionary) {
      dictionaryCopy = dictionary;
      inventories = new HashMap<String, LetterInventory>();
      for (String s : dictionary) {
         inventories.put(s, new LetterInventory(s));
      }
   }
 
   // pre: the maximum number of words is greater than or equal
   //      to 0, otherwise throw an IllegalArgumentException.
   // post: takes in a String text and an integer max, prints
   //       out all combinations of words from the dictionary
   //       that are anagrams of text and that include at most max
   //       words. When max is 0, it means it will print out anagrams
   //       with an unlimited size        
   public void print(String text, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      LetterInventory phrase = new LetterInventory(text);
      List<String> reducedDictionary = new ArrayList<String>();
      List<String> anagrams = new ArrayList<String>();
      for (String key : dictionaryCopy) {
         LetterInventory result = phrase.subtract(inventories.get(key));
         if (result != null) {
            reducedDictionary.add(key);
         }
      }
      print(phrase, reducedDictionary, anagrams, max);
   }
 
   // a helper method that takes in a LetterInventory phrase, a List
   // reducedDictionary, a List anagrams, and an integer max to keep 
   // searching for max number of anagrams of the reducedDictionary,
   // and then prints out all the anagrams.
   private void print(LetterInventory phrase, List<String> reducedDictionary,
                        List<String> anagrams, int max) {
      if (phrase.size() == 0) {
         System.out.println(anagrams);
      } else if (max > anagrams.size() || max == 0) {
         for (String s : reducedDictionary) {
            LetterInventory result = phrase.subtract(inventories.get(s));
            if (result != null) {
               anagrams.add(s);
               print(result, reducedDictionary, anagrams, max);
               anagrams.remove(anagrams.size() - 1);
            }
         }
      }
   }
}