package ch.kerbtier.lanthanum.graph;

import java.util.List;

import ch.kerbtier.lanthanum.Vec2i;

public interface FetchNeighbours {
  List<Neighbour> fetch(Vec2i current);
  
  public class Neighbour {
    private Vec2i position;
    private int distance;

    public Neighbour(Vec2i position) {
      this.position = position;
      this.distance = 1;
    }
    
    public Neighbour(Vec2i position, int distance) {
      this.position = position;
      this.distance = distance;
    }

    public Vec2i getPosition() {
      return position;
    }

    public int getDistance() {
      return distance;
    }
  }
}
