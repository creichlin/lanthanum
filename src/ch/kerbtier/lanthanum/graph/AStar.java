package ch.kerbtier.lanthanum.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.kerbtier.lanthanum.Vec2i;
import ch.kerbtier.lanthanum.graph.FetchNeighbours.Neighbour;

public class AStar {

  private ShortestDistance<Vec2i> distances = new ShortestDistance<Vec2i>();
  private Set<Vec2i> queue = new HashSet<Vec2i>();
  private Set<Vec2i> checked = new HashSet<Vec2i>();

  private FetchNeighbours fetcher;
  private Vec2i pos;
  private Vec2i target;
  
  private List<Vec2i> cachedResult = null;
  private boolean resultIsNull = false;

  public AStar(FetchNeighbours fetcher, Vec2i pos, Vec2i target) {
    this.fetcher = fetcher;
    this.pos = pos;
    this.target = target;
  }

  /**
   * gets path to target. start and end point included. if there is no path, return null.
   * @return
   */
  public List<Vec2i> get() {

    if(cachedResult != null) {
      return cachedResult;
    }
    
    if(resultIsNull) {
      return null;
    }
    
    
    queue.add(pos);

    distances.setFirst(pos);

    while (queue.size() > 0) {

      Vec2i current = distances.closestFrom(queue);
      queue.remove(current);
      checked.add(current);

      if (current.equals(target)) {
        cachedResult = construct();
        return cachedResult;
      }

      for (Neighbour neighbour : fetcher.fetch(current)) {
        if (!checked.contains(neighbour.getPosition())) {
          if (!queue.contains(neighbour.getPosition())) {
            queue.add(neighbour.getPosition());
          }

          distances.update(current, neighbour.getPosition(), neighbour.getDistance());
        }
      }
    }
    resultIsNull = true;
    return null; // target not reachable
  }

  private List<Vec2i> construct() {
    return distances.pathTo(target);
  }

  /*
   * returns the distance from the center, using calculated distance map if no
   * distance calculated, return -1
   */
  public int distTo(Vec2i pos) {
    return distances.get(pos);
  }
}
