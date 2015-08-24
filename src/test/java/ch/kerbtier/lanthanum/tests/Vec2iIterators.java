package ch.kerbtier.lanthanum.tests;

import java.util.Collection;

import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Line2i;
import ch.kerbtier.lanthanum.Vec2i;

public class Vec2iIterators {

  @Test
  public void diamond1() {

    Vec2i center = new Vec2i(0, 0);

    Collection<Vec2i> points = center.getDiamond(3, 3);

    String sb = Util.drawGrid(new Line2i(-10, -10, 11, 11), points);
    
    System.out.println(sb.toString());

  }


}
