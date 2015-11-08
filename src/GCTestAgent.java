/**
 * Created by chetan on 8/11/15.
 */


import java.util.ArrayList;
import java.util.Date;

public class GCTestAgent implements GCTestAgentMBean, Runnable {
 ArrayList<Object> leakingMap = new ArrayList<Object>();
 volatile double val = 10;

 @Override
 public void newThread(String threadName) {
  Thread newThread = new Thread(this);
  newThread.setName(threadName);
  newThread.start();
 }

 @Override
 public void newCollectableObject(int size) {
     //NAMED COLLECTABLE AS IT WILL BE GC VERY SOON because not assign to any variable
     //The other is
  createObject(size);
 }

 private Object createObject(int size) {
  ArrayList<String> list = new ArrayList<String>();
  for (int i = 0; i < size; i++) {
   list.add( (new Date()).toString() + " " +  i);
  }
  return list;
 }

 @Override
 public void newLeakedObject(int size) {
     //HERE LEAKING MAP IS A ARRAYLIST WHOSE purpose is that to hold the refrence so to avoid gc

     //this is what happens in caches where it is saved
     leakingMap.add(createObject(size));
 }

 @Override
 public void run() {
  for (;;) {
   System.out.println(Thread.currentThread().getName());
   try {
    Thread.sleep(10000);
   } catch (InterruptedException e) {
    e.printStackTrace();
   }
  }
 }

 @Override
 public void clearLeaked() {
  leakingMap.clear();
 }

 @Override
 public void cpuIntensiveOperation(int iterations) {
  int[] myArrayToBeSorted = new int[] {4,2,6,7,2,1,6};
  for (int i = 0; i < iterations; i++) {
   for (int j = 0; j < myArrayToBeSorted.length - 1; j++) {
    myArrayToBeSorted[j] = myArrayToBeSorted[j] + myArrayToBeSorted[j + 1];
   }
  }
 }

}



