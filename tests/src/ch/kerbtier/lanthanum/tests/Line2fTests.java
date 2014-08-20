package ch.kerbtier.lanthanum.tests;

import java.util.Random;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import ch.kerbtier.lanthanum.Line2f;
import ch.kerbtier.lanthanum.Vec2f;

public class Line2fTests {

  @Test
  public void factorCalculationTests() {
    Line2f l = new Line2f(new Vec2f(1, 2), new Vec2f(4, 7));
    Random r = new Random();
    for(int cnt = 0; cnt < 100; cnt++) {
    float fac = (r.nextFloat() - 0.5f) * 24;
    
    Vec2f pos = l.start().add(l.toVector().mul(fac));
    
    
    float fcr = l.factorTo(pos);
    
    assertEquals(fcr, fac, 0.00001f);
    }
  }
  
}
