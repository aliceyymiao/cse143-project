// Yingyun(Alice) Miao
// 1/21/19
// CSE143
// TA: Chin Yeoh
// Assignment #2
//
// The class called Guitar37 builds on the GuitarString
// class to keep track of a musical instrument with 37
// strings.

public class Guitar37 implements Guitar {
   private GuitarString[] strings; // creates a list of guitar strings
   private int currentTime; // the number of times of tic
   
   // class constant representing keyboard layout
   public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

   // constructs the strings and initialize the current time,
   // each string has a different frequency
   public Guitar37() {
      strings = new GuitarString[KEYBOARD.length()];
      currentTime = 0;
      for (int i = 0; i < KEYBOARD.length(); i++) {
         double frequency = 440.0 * Math.pow(2, (i - 24) / 12.0);
         strings[i] = new GuitarString(frequency);
      }
   }
   
   // takes in the integer pitch, if the corresponding index of the 
   // given pitch is greater than or equal to 0 and less than the length
   // of the keyboard, play the corresponding note that the
   // pitch represents; otherwise, ignore if the pitch is out of scale
   public void playNote(int pitch) {
      int index = pitch + 24;
      if (index >= 0 && index < KEYBOARD.length()) {
         strings[index].pluck();
      }
   } 
   
   // takes in the character key, return true if the key is within the 
   // KEYBOARD, return false otherwise
   public boolean hasString(char key) {
      return KEYBOARD.indexOf(key) != -1;
   }
   
   // takes in the character key, play the corresponding note that the
   // key represents, throw an IllegalArgumentException if out of scale
   public void pluck(char key) {
      if (KEYBOARD.indexOf(key) == -1) {
         throw new IllegalArgumentException();
      }
      strings[KEYBOARD.indexOf(key)].pluck();
   }
   
   // returns the sum of the current samples
   public double sample() {
      double sum = 0.0;
      for (int i = 0; i < KEYBOARD.length(); i++) {
         sum += strings[i].sample();
      }
      return sum;
   }
   
   // advances the time forward one tic
   public void tic() {
      for (int i = 0; i < KEYBOARD.length(); i++) {
         strings[i].tic();
      }
      currentTime++;
   }
   
   // returns the number of times of tic
   public int time() {
      return currentTime;
   }
}