// Yingyun(Alice) Miao
// 1/21/19
// CSE143
// TA: Chin Yeoh
// Assignment #2
//
// The class called GuitarString is used to model a 
// vibrating guitar string of a given frequency.

import java.util.*;

public class GuitarString {
   // creates Queue called ringBuffer to represent a guitar string
   private Queue <Double> ringBuffer;
   
   // class constant represents the energy decay factor 0.996
   public static final double DECAY_FACTOR = 0.996;
   
   // takes in a given frequency, constructs a guitar string
   // of this frequency and creates a ring buffer of the 
   // desired capacity N. The ring buffer is filled with 0's.
   // If the frequency is less than or equal to 0 or if the 
   // resulting size of ring buffer is less than 2, throw an 
   // IllegalArgumentException
   public GuitarString(double frequency) {
      int capacityN = (int)Math.round(StdAudio.SAMPLE_RATE / frequency);
      ringBuffer = new LinkedList <Double> ();
      for (int i = 0; i < capacityN; i++) {
         ringBuffer.add(0.0);
      }
      if (frequency <= 0.0 || ringBuffer.size() < 2) {
         throw new IllegalArgumentException();
      }
   }
   
   // pre: takes in an init array of doubles, constructs a guitar string
   //      if the array has fewer than two elements, throw an 
   //      IllegalArgumentException.
   // post: initializes the contents of the ring buffer to the values
   //       in the array.
   public GuitarString(double[] init) {
      int capacityN = init.length;
      ringBuffer = new LinkedList <Double> ();
      if (capacityN < 2) {
         throw new IllegalArgumentException();
      }
      for (double i : init) {
         ringBuffer.add(i);
      }
   }

   // replace the N elements in the ring buffer with
   // N random values between -0.5 inclusive and +0.5
   // exclusive
   public void pluck(){
      Random rand = new Random();
      for (int i = 0; i < ringBuffer.size(); i++) {
         double r = rand.nextDouble() - 0.5;
         ringBuffer.remove();
         ringBuffer.add(r);
      }
   }

   // delete the sample at the front of the ring buffer and 
   // add to the end of it the average of the first two elements
   // multiplied by the energy decay factor (0.996).
   public void tic() {
      double firstSample = ringBuffer.remove();
      double secondSample = ringBuffer.peek();
      double addSample = DECAY_FACTOR * (firstSample + secondSample) / 2;
      ringBuffer.add(addSample);
   }
   
   // return the value at the front of the ring buffer
   public double sample() {
      return ringBuffer.peek();
   }
} 