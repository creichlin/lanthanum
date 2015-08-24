package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class QuadStripe2f {

  protected List<Vec2f> points = new ArrayList<Vec2f>();

  public void append(Vec2f p1, Vec2f p2) {
    points.add(p1);
    points.add(p2);
  }

  public Vec2f get(int i) {
    return points.get(i);
  }

  public PolyLine2f polyLine() {
    PolyLine2f pl = new PolyLine2f();

    for(int cnt = 0; cnt < points.size(); cnt += 2) {
      pl.push(points.get(cnt));
    }

    for(int cnt = points.size() - 1; cnt > 0; cnt -= 2) {
      pl.push(points.get(cnt));
    }
    
    return pl;
  }

}
