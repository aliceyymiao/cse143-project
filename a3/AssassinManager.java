// Yingyun(Alice) Miao
// 1/28/19
// CSE143
// TA: Chin Yeoh
// Assignment #3
//
// This class allows a client to manage a game of 
// assasin. It keeps track of who is stalking whom
// and the history of who killed whom.
 
import java.util.*;

public class AssassinManager{
   private AssassinNode frontKillRing; 
   private AssassinNode frontGraveyard; 

   // pre: the name list is not empty, otherwise throw
   //      IllegalArgumentException
   // post: takes in a list of names, initialize the 
   //       kill ring with the order of the names in
   //       the list, which represents people still alive
   public AssassinManager(List<String> names) {
      if (names.isEmpty()) {
         throw new IllegalArgumentException();
      }
      frontGraveyard = null;
      for(int i = names.size() - 1; i >= 0; i--) {
         frontKillRing = new AssassinNode(names.get(i), frontKillRing);
      }
   }    
   
   // prints out who is stalking whom. If there is only one person in
   // the ring, report that the person is stalking themselves
   public void printKillRing() {
      AssassinNode prev = frontKillRing;
      AssassinNode current = frontKillRing.next;
      while (current != null) {
         System.out.println("    " + prev.name + " is stalking " + current.name);
         prev = current;
         current = current.next;
      }
      System.out.println("    " + prev.name + " is stalking " + frontKillRing.name);    
   }
   
   // prints out names in reverse kill order. Do not print
   // output if the graveyard is empty
   public void printGraveyard() {
      AssassinNode current = frontGraveyard;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }
   
   // a helper method that takes in an AssassinNode list front  
   // and a string name that is case insensitive, returns true if 
   // the name is in the AssassinNode list; otherwise return false
   private boolean containsName(AssassinNode front, String name) {
      AssassinNode current = front;
      while(current != null) {
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // takes in a String name that is case insensitive, 
   // return true if the given name is in the current
   // kill ring and return false otherwise
   public boolean killRingContains(String name) {
      return containsName(frontKillRing, name);
   }
   
   // takes in a String name that is case insensitive, 
   // return true if the given name is in the current
   // graveyard and return false otherwise
   public boolean graveyardContains(String name) {
      return containsName(frontGraveyard, name);
   }
   
   // return true if the kill ring contains one person or
   // the game is over; return false otherwise
   public boolean gameOver() {
      return frontKillRing.next == null;
   }
   
   // return the name left in the kill ring when the 
   // game is over; otherwise return null
   public String winner() {
      if (frontKillRing.next == null) {
         return frontKillRing.name;
      } else {
         return null;
      }
   }
   
   // pre: takes in the String name. The kill ring contains the given 
   //      name and the game is not over. Otherwise throw 
   //      IllegalArgumentException when the kill ring doesn't contain
   //      the name or throw IllegalStateException when the game is over
   // post: records the killing of the person with the given name that 
   //       ignores case, transferring the person from the kill ring
   //       to the graveyard
   public void kill(String name) {
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      } else if (gameOver()) {
         throw new IllegalStateException();
      }
      AssassinNode current = frontKillRing;
      AssassinNode temp = frontKillRing;   
      while (current.next != null
             && !current.next.name.equalsIgnoreCase(name)) {
         current = current.next;
      }
      if (!frontKillRing.name.equalsIgnoreCase(name)) {
         temp = current.next;
         current.next = current.next.next;
      } else {
         frontKillRing = frontKillRing.next;
      }
      temp.killer = current.name;
      temp.next = frontGraveyard;
      frontGraveyard = temp;
   }
}