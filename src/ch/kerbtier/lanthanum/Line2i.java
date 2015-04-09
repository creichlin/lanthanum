package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class Line2i {

  private Vec2i start;
  private Vec2i end;

  public Line2i(Vec2i start, Vec2i end) {
    this.start = start;
    this.end = end;
  }

  public Line2i(int x, int y, int ex, int ey) {
    start = new Vec2i(x, y);
    end = new Vec2i(ex, ey);
  }

  public final Vec2i start() {
    return start;
  }

  public final Vec2i end() {
    return end;
  }

  public final int sx() {
    return start.x();
  }

  public final int sy() {
    return start.y();
  }

  public final int ex() {
    return end.x();
  }

  public final int ey() {
    return end.y();
  }

  public boolean rectContains(Vec2i point) {
    return point.x() >= start.x() && point.x() < end.x()
        && point.y() >= start.y() && point.y() < end.y();
  }

  /**
   * returns the vector from startpoint to endpoint
   * 
   * @return
   */

  public Vec2i toVector() {
    return end.sub(start);
  }

  public Vec2i clamp(Vec2i vec) {
    int nx = vec.x();
    int ny = vec.y();

    if (nx < start.x()) {
      nx = start.x();
    }
    if (nx > end.x()) {
      nx = end.x();
    }

    if (ny < start.y()) {
      ny = start.y();
    }
    if (ny > end.y()) {
      ny = end.y();
    }

    return new Vec2i(nx, ny);
  }


  public Line2i translate(Vec2i t) {
    return new Line2i(start.add(t), end.add(t));
  }

  public Line2i expandBy(int i) {
    return new Line2i(start.x() - i, start.y() - i, ex() + i, ey() + i);
  }

  public Line2i expandBy(int horizontal, int vertical) {
    return new Line2i(start.x() - horizontal, start.y() - vertical, ex() + horizontal, ey() + vertical);
  }

  public Iterable<Vec2i> iterateRect() {
    return toVector().iterateRectAt(start);
  }
  
  public String toString() {
    return "<Line2i:" + sx() + "/" + sy() + " " + ex() + "/" + ey() + ">";
  }

  public Iterable<Vec2i> iterateBorder() {
    List<Vec2i> i = new ArrayList<Vec2i>();
    for(int x = start.x(); x < end.x(); x++) {
      i.add(new Vec2i(x, start.y()));
      i.add(new Vec2i(x, end.y() - 1));
    }
    for(int y = start.y() + 1; y < end.y() - 1; y++) {
      i.add(new Vec2i(start.x(), y));
      i.add(new Vec2i(end.x() - 1, y));
    }
    return i;
  }

  public List<Vec2i> getCorners() {
    List<Vec2i> corners = new ArrayList<Vec2i>();
    corners.add(start);
    corners.add(new Vec2i(end.x() - 1, start.y()));
    corners.add(end.sub(Vec2i.VEC1));
    corners.add(new Vec2i(start.x(), end.y() - 1));
    return corners;
  }

}
