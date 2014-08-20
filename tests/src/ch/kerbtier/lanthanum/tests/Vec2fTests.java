package ch.kerbtier.lanthanum.tests;

import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Vec2f;
import static org.testng.Assert.*;

public class Vec2fTests {
  
  @Test
  public void rot() {
    
    Vec2f v1 = new Vec2f(0, 1);
    
    Vec2f v2 = new Vec2f(0, 1);
    
    for(int cnt = 0; cnt < 180; cnt++) {
      Vec2f rotated = v2.rotDeg(cnt);
      
      float angle = v1.angle(rotated);
      
      assertEquals(angle / Math.PI * 180, cnt, 0.0001f);
    }
    for(int cnt = 180; cnt < 360; cnt++) {
      Vec2f rotated = v2.rotDeg(cnt);
      
      float angle = v1.angle(rotated);
      
      assertEquals(angle / Math.PI * 180, -360 + cnt, 0.0001f);
    }
    
  }
  
  
}
