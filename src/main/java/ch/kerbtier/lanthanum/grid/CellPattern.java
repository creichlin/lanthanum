package ch.kerbtier.lanthanum.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.kerbtier.lanthanum.EnumSetSerializers;
import ch.kerbtier.lanthanum.Vec2i;
import ch.kerbtier.lanthanum.grid.CellPattern.Neighbour;
import static ch.kerbtier.lanthanum.grid.CellPattern.Neighbour.*;
import static ch.kerbtier.lanthanum.grid.CellPattern.Corner.*;

public class CellPattern {
  private EnumSet<Neighbour> neighbours;

  public CellPattern(Vec2i position, IsValidAt validator) {
    neighbours = EnumSet.noneOf(Neighbour.class);
    for (Neighbour n : Neighbour.values()) {
      if (validator.isValid(position.add(n.getDirection()))) {
        neighbours.add(n);
      }
    }
  }

  private CellPattern(EnumSet<Neighbour> neighbours) {
    this.neighbours = neighbours;
  }
  
  public CellPattern(int bitset) {
    neighbours = EnumSetSerializers.fromByte((byte) bitset, Neighbour.class);
  }

  public Corner getTopLeftCorner() {
    return doCorner(TOP_LEFT, LEFT, TOP);
  }

  public Corner getTopRightCorner() {
    return doCorner(TOP_RIGHT, RIGHT, TOP);
  }

  public Corner getBottomLeftCorner() {
    return doCorner(BOTTOM_LEFT, LEFT, BOTTOM);
  }

  public Corner getBottomRightCorner() {
    return doCorner(BOTTOM_RIGHT, RIGHT, BOTTOM);
  }

  private Corner doCorner(Neighbour edge, Neighbour horizontal, Neighbour vertical) {
    Corner corner = OPEN;
    if (hasAllOf(horizontal, vertical)) {
      corner = CLOSED;
    } else if (hasAllOf(horizontal)) {
      corner = VERTICAL;
    } else if (hasAllOf(vertical)) {
      corner = HORIZONTAL;
    } else if (hasAllOf(edge)) {
      corner = EDGE;
    }

    return corner;
  }
  
  public BufferedImage getGrayImage() {
    BufferedImage bi = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
    
    Graphics2D g = bi.createGraphics();
    
    g.setColor(Color.black);
    g.fillRect(0, 0, 3, 3);
    
    
    g.setColor(Color.white);
    
    if(neighbours.contains(TOP_LEFT)) {
      g.fillRect(0, 0, 1, 1);
    }
    if(neighbours.contains(TOP)) {
      g.fillRect(1, 0, 1, 1);
    }
    if(neighbours.contains(TOP_RIGHT)) {
      g.fillRect(2, 0, 1, 1);
    }
    if(neighbours.contains(LEFT)) {
      g.fillRect(0, 1, 1, 1);
    }
    if(neighbours.contains(RIGHT)) {
      g.fillRect(2, 1, 1, 1);
    }
    if(neighbours.contains(BOTTOM_LEFT)) {
      g.fillRect(0, 2, 1, 1);
    }
    if(neighbours.contains(BOTTOM)) {
      g.fillRect(1, 2, 1, 1);
    }
    if(neighbours.contains(BOTTOM_RIGHT)) {
      g.fillRect(2, 2, 1, 1);
    }
    
    return bi;
  }
  
  
  /**
   * returns the id that represents the complete pattern given
   * @return
   */
  public int getId() {
    return EnumSetSerializers.toChar(neighbours, Neighbour.class);
  }
  
  /**
   * returns the id unique to the corner pattern. there are only 47 unique possibilities
   * @return
   */
  public int getCornerId() {
    int id = getTopLeftCorner().ordinal();
    id = id * 6 +  getTopRightCorner().ordinal();
    id = id * 6 +  getBottomLeftCorner().ordinal();
    id = id * 6 +  getBottomRightCorner().ordinal();
    return id;
  }
  
  public static List<CellPattern> getAllPatterns() {
    List<CellPattern> patterns = new ArrayList<CellPattern>();
    for(int cnt = 0; cnt < 256; cnt++) {
      patterns.add(new CellPattern(cnt));
    }
    return patterns;
  }
  
  public static List<CellPattern> getAllCornerPatterns() {
    List<CellPattern> patterns = new ArrayList<CellPattern>();
    Set<Integer> added = new HashSet<Integer>();
    for(int cnt = 0; cnt < 256; cnt++) {
      CellPattern cp = new CellPattern(cnt);
      if(!added.contains(cp.getCornerId())) {
        patterns.add(cp);
        added.add(cp.getCornerId());
      }
    }
    return patterns;
  }
  

  private boolean hasAllOf(Neighbour... ngs) {
    for (Neighbour n : ngs) {
      if (!neighbours.contains(n)) {
        return false;
      }
    }
    return true;
  }
  
  public String toString() {
    return neighbours.toString();
  }

  public enum Neighbour {
    TOP_LEFT(-1, -1), TOP(0, -1), TOP_RIGHT(1, -1), LEFT(-1, 0), RIGHT(1, 0), BOTTOM_LEFT(-1, 1), BOTTOM(0, 1), BOTTOM_RIGHT(
        1, 1);

    private final Vec2i direction;

    private Neighbour(int x, int y) {
      direction = new Vec2i(x, y);
    }

    public Vec2i getDirection() {
      return direction;
    }
  }

  public enum Corner {
    CLOSED, HORIZONTAL, VERTICAL, OPEN, EDGE
  }

  public CellPattern remove(Neighbour... neighbours) {
    EnumSet<Neighbour> nn = this.neighbours.clone();
    nn.removeAll(Arrays.asList(neighbours));
    return new CellPattern(nn);
  }

  public CellPattern add(Neighbour... neighbours) {
    EnumSet<Neighbour> nn = this.neighbours.clone();
    nn.addAll(Arrays.asList(neighbours));
    return new CellPattern(nn);
  }

  public boolean is(Neighbour... neighbours) {
    return this.neighbours.containsAll(Arrays.asList(neighbours));
  }
}
