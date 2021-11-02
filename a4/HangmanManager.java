// Yingyun(Alice) Miao
// 2/4/19
// CSE143
// TA: Chin Yeoh
// Assignment #4
//
// The class called HangmanManager keeps track of the state of a game
// of hangman. It is different from the normal hangman game in which the 
// computer picks the word. This game "cheats" the player as it delays 
// picking a word to keep its options open.
 
import java.util.*;
 
public class HangmanManager {
   private int guessesLeft;
   private Set<Character> guessed;
   private Set<String> setOfWords;
   private String pattern;
 
   // Pre: length larger than or equal to 1 and max bigger than 
   //      or equal to 0, throw an IllegalArgumentException otherwise
   // Post: takes in a Collection dictionary, an integer length, and
   //       an integer max that represents the maximum number of wrong 
   //       guesses the player is allowed to make, initialize the state 
   //       of the game. The set of words initially contain all words 
   //       from the dictionary of the given length
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      guessesLeft = max;
      guessed = new TreeSet<Character>();
      setOfWords = new TreeSet<String>();
      for (String word : dictionary) {
         if (word.length() == length) {
            setOfWords.add(word);
         }
      }
      pattern = "-";
      for (int i = 1; i < length; i++) {
         pattern += " -";
      }
   }
 
   // returns the current set of words being 
   // considered by the hangman manager
   public Set<String> words() {
      return setOfWords;
   }
 
   // returns the number of guesses left
   public int guessesLeft() {
      return guessesLeft;
   }
 
   // returns the current set of letters that
   // have been guessed by the user
   public Set<Character> guesses() {
      return guessed;
   }
 
   // Pre: the set of words is not empty, throw an
   //      IllegalStateException otherwise
   // Post: return the current pattern to be displayed
   //       for the hangman game taking into account
   //       guesses that have been made, the dashes
   //       represent the unguessed letters
   public String pattern() {
      if (setOfWords.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
 
   // Pre: the number of guesses left is at least 1 and the set
   //      of words is not empty. Otherwise throw an IllegalStateException
   //      Also, the character being guessed should not be guessed 
   //      previously. Otherwise throw an IllegalArgumentException
   // Post: return the number of occurrences of the guessed letter
   //       in the new pattern and update the number of guesses left
   public int record(char guess) {
      if (guessesLeft < 1 || setOfWords.isEmpty()) {
         throw new IllegalStateException();
      }
      if (guessed.contains(guess)) {
         throw new IllegalArgumentException();
      }
      guessed.add(guess);
      comparePattern(getOrganized(guess));
      int count = 0;
      for (int i = 0; i < pattern.length(); i++) {
         if (guess == pattern.charAt(i)) {
            count++;
         }
      }
      if (count == 0) {
         guessesLeft--;
      }
      return count;    
   }  
 
   // compare the user's guessed letter with the possible words inventory, if 
   // the words do not contain the letter, remain the original state as dashes,
   // otherwise reveal the right letter in corresponding spots. Also, classify
   // words to certain pattern's inventory.
   private Map<String, Set<String>> getOrganized(char guess) {
      Map<String, Set<String>> patternedWords = new TreeMap<String, Set<String>>();
      for (String word: setOfWords) {
         String newPattern = pattern;
         for (int i = 0; i < word.length(); i++) {
            if (guess == word.charAt(i)) {
               newPattern = newPattern.substring(0, 2 * i) + guess
                            + newPattern.substring(2 * i + 1);  
            }
         }
         if (!patternedWords.containsKey(newPattern)) {
            patternedWords.put(newPattern, new TreeSet<String>());
         }
         patternedWords.get(newPattern).add(word);
      }
      return patternedWords;
   }
 
   // takes in the diagram of all the patterns with their corresponding
   // set of words, compare the pattern of the words in the inventory that
   // matches best with user's guess, and then choose that group of words
   // as the answer set.
   private void comparePattern (Map<String, Set<String>> patternedWords) {
      int max = 0;
      for (String mode : patternedWords.keySet()) {
         if (patternedWords.get(mode).size() > max) {
            max = patternedWords.get(mode).size();
            pattern = mode;
            setOfWords = patternedWords.get(mode);
         }
      }
   }
}