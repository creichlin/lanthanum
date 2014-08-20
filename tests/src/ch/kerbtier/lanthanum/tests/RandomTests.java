package ch.kerbtier.lanthanum.tests;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Random;

public class RandomTests {
  @Test
  public void testRandomDoIncrease1() {

    Random random = Random.get14(10);

    int max = 10;

    for (int cnt = 0; cnt < 11; cnt++) {
      float total = 0;

      for (int r = 0; r < 1000000; r++) {
        if (random.doIncrease(max, cnt)) {
          total += 1;
        }
      }

      int probability = Math.round(total / 100); // in 0..10'000

      int expected = (10 - cnt) * 1000;

      assertEquals(probability, expected);
    }

  }

  @Test
  public void testRandomDoIncreaseMin() {
    Random random = Random.get14(10);
    int total = 0;
    for (int r = 0; r < 1000000; r++) {
      if (random.doIncrease(2, 0)) {
        total += 1;
      }
    }
    assertEquals(total, 1000000);
  }

  @Test
  public void testRandomDoIncreaseMid() {
    Random random = Random.get14(10);
    int total = 0;
    for (int r = 0; r < 1000000; r++) {
      if (random.doIncrease(2, 1)) {
        total += 1;
      }
    }
    assertEquals(Math.round(total / 1000.0), 500000 / 1000);
  }
  
  @Test
  public void testRandomDoIncreaseMax() {
    Random random = Random.get14(10);
    int total = 0;
    for (int r = 0; r < 1000000; r++) {
      if (random.doIncrease(2, 2)) {
        total += 1;
      }
    }
    assertEquals(total, 0);
  }
}
