package com.meetsl.androidaop;

public class MySampleProxiedImpl implements MySampleProxied {

  /**
   * Print some fake String
   */
  public void printSomethingCool() {
    sleep(20);
    System.out.println("This is really cool...Dynamic Proxies rock!!!");
  }

  /**
   * Test method for sleeping.
   *
   * @param millis Amount of time to sleep.
   */
  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}