package ch.kerbtier.lanthanum.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import ch.kerbtier.lanthanum.ProbabilityPicker;
import ch.kerbtier.lanthanum.Random;

public class ProbabilityPickerTest {
  @Test
  public void test1() {

    Random random = Random.get14(0);

    ProbabilityPicker<Float> pb1 = new ProbabilityPicker<Float>();

    pb1.add(0.3f, 0.3f);
    pb1.add(3f, 3f);
    pb1.add(1f, 1f);

    Map<Float, Integer> count = new HashMap<Float, Integer>();

    for (int cnt = 0; cnt < 10000000; cnt++) {
      Float k = pb1.get(random);
      count.put(k, count.containsKey(k) ? count.get(k) + 1: 1);
    }
    
    for(Float k: count.keySet()) {
      int key = Math.round(k * 1000);
      int val = Math.round(count.get(k) * pb1.getTotal() / 10000.0f);
      assertEquals(val, key);
    }

  }
}
