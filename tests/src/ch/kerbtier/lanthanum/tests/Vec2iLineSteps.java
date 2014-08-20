package ch.kerbtier.lanthanum.tests;

import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Line2i;
import ch.kerbtier.lanthanum.Vec2i;

public class Vec2iLineSteps {

  @Test
  public void line1() {
    
    
    for(Vec2i target: new Vec2i(0, 0).iterateCircle(10)) {
      if(target.length() >= 10) {
      String sb = Util.drawGrid(new Line2i(-10, -10, 11, 11), new Vec2i(0, 0).lineTo(target, true));
      System.out.println(sb);
      }
      
    }
    
    
  }
}
