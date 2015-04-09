package ch.kerbtier.lanthanum.graph;

import java.util.HashSet;
import java.util.Set;

import ch.kerbtier.lanthanum.Vec2i;

public class VisibleArea {

  private Vec2i start;
  private Blocked check;
  private int dist;
  
  public VisibleArea(Vec2i start, int dist, Blocked check) {
    this.start = start;
    this.dist = dist;
    this.check = check;
  }

  

  public Set<Vec2i> full() {
    Set<Vec2i> visible = new HashSet<Vec2i>();
    
    for(Vec2i v: start.iterateCircle(dist)) {
      if(!check.blocked(v)) {
        
        boolean blocked = false;
        for (Vec2i p : start.lineToBf(v, true)) {
          if (check.blocked(p)) {
            blocked = true;
            break;
          }
        }
        if (!blocked) {
          visible.add(v);
        }
      }
    }
    
    return visible;
  }
}
