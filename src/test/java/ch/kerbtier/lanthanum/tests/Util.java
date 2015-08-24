package ch.kerbtier.lanthanum.tests;

import java.util.Collection;

import ch.kerbtier.lanthanum.Line2i;
import ch.kerbtier.lanthanum.Vec2i;

public class Util {
  public static String drawGrid(Line2i rect, Collection<Vec2i> points) {
    StringBuilder sb = new StringBuilder();

    for (int cnty = rect.sy(); cnty < rect.ey(); cnty++) {
      for (int cntx = rect.sx(); cntx < rect.ex(); cntx++) {
        if (cntx == 0 && cnty == 0) {
          sb.append("X");
        } else {
          if (points.contains(new Vec2i(cntx, cnty))) {
            sb.append("*");
          } else {
            sb.append(".");
          }
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

}
