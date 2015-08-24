package ch.kerbtier.lanthanum.graph;

import java.util.HashSet;
import java.util.Set;

import ch.kerbtier.lanthanum.Vec2i;
import ch.kerbtier.lanthanum.graph.FetchNeighbours.Neighbour;

public class FloodSD {

  private Set<Vec2i> queue = new HashSet<Vec2i>();
  private Set<Vec2i> checked = new HashSet<Vec2i>();

  private ShortestDistance<Vec2i> distances = new ShortestDistance<Vec2i>();
  
  private FetchNeighbours fetcher;
  private Vec2i pos;

  public FloodSD(FetchNeighbours fetcher, Vec2i pos) {
    this.fetcher = fetcher;
    this.pos = pos;
  }
  

  public Set<Vec2i> get() {
    queue.add(pos);

    distances.setFirst(pos);

    while (queue.size() > 0) {

      Vec2i current = distances.closestFrom(queue);
      queue.remove(current);
      checked.add(current);
      
      for (Neighbour neighbour : fetcher.fetch(current)) {
        if (!checked.contains(neighbour)) {
          if (!queue.contains(neighbour)) {
            queue.add(neighbour.getPosition());
          }
          
          distances.update(current, neighbour.getPosition(), neighbour.getDistance());
        }
      }
    }
    return checked;
  }

  /*
   * returns the distance from the center, using calculated distance map
   * if no distance calculated, return -1
   */
  public int distTo(Vec2i pos) {
    return distances.get(pos);
  }
}
