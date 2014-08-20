package ch.kerbtier.lanthanum.graph;

import java.util.HashSet;
import java.util.Set;

import ch.kerbtier.lanthanum.Vec2i;
import ch.kerbtier.lanthanum.graph.FetchNeighbours.Neighbour;

public class Flood {

  private Set<Vec2i> queue = new HashSet<Vec2i>();
  private Set<Vec2i> checked = new HashSet<Vec2i>();

  private FetchNeighbours fetcher;
  private Vec2i pos;

  public Flood(FetchNeighbours fetcher, Vec2i pos) {
    this.fetcher = fetcher;
    this.pos = pos;
  }
  
  public Set<Vec2i> get() {
    queue.add(pos);

    while (queue.size() > 0) {
      Vec2i current = queue.iterator().next();
      queue.remove(current);
      checked.add(current);
      
      for (Neighbour neighbour : fetcher.fetch(current)) {
        if (!checked.contains(neighbour)) {
          if (!queue.contains(neighbour)) {
            queue.add(neighbour.getPosition());
          }
        }
      }
    }
    return checked;
  }
}
