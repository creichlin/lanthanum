package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class Vec2i implements Cloneable {
  private static final int HASHSEED = 3731;
  private int x;
  private int y;

  public static final Vec2i ORIGIN = new Vec2i(0, 0);

  public Vec2i(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public boolean equals(Object other) {
    if (other instanceof Vec2i) {
      return ((Vec2i) other).x == x && ((Vec2i) other).y == y;
    }
    return false;
  }
  
  public int hashCode() {
    return x * HASHSEED + y;
  }

  @Override
  public Vec2i clone() throws CloneNotSupportedException {
    return new Vec2i(x, y);
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  public Vec2i add(Vec2i pos) {
    return new Vec2i(x + pos.x, y + pos.y);
  }

  /*
   * inside, exclusive end
   */
  public boolean isInside(int sx, int sy, int ex, int ey) {
    if (x < sx || y < sy || x >= ex || y >= ey) {
      return false;
    }
    return true;
  }

  public Vec2i divide(int i) {
    return new Vec2i(x / i, y / i);
  }

  public Vec2i mul(int factor) {
    return new Vec2i(x * factor, y * factor);
  }

  public Iterable<Vec2i> iterateCircle(int radius) {
    List<Vec2i> positions = new ArrayList<Vec2i>();
    for (int cy = -radius; cy <= radius; cy++) {
      for (int cx = -radius; cx <= radius; cx++) {
        int dist = cx * cx + cy * cy;

        if (dist <= radius * radius) {
          positions.add(new Vec2i(x + cx, y + cy));
        }
      }
    }
    return positions;
  }

  public Iterable<Vec2i> iterateRect() {
    List<Vec2i> positions = new ArrayList<Vec2i>();
    for (int ly = 0; ly < y; ly++) {
      for (int lx = 0; lx < x; lx++) {
        positions.add(new Vec2i(lx, ly));
      }
    }
    return positions;
  }
  
  public Iterable<Vec2i> iterateCross(int radius) {
    List<Vec2i> positions = new ArrayList<Vec2i>();
    for(int cnt = -radius; cnt <= radius; cnt++) {
      positions.add(new Vec2i(x, y + cnt));
      positions.add(new Vec2i(x + cnt, y));
    }
    return positions;
  }

  public String toString() {
    return "<Vec2i:" + x + "/" + y + ">";
  }

  public Vec2i invert() {
    return new Vec2i(-x, -y);
  }

  public Vec2i add(int x2, int y2) {
    return new Vec2i(x + x2, y + y2);
  }

  public Vec2i sub(Vec2i from) {
    return new Vec2i(x - from.x, y - from.y);
  }

  public Vec2i rot90() {
    return new Vec2i(y, -x);
  }

  public Vec2i divide(float unit) {
    return new Vec2i((int)(x / unit), (int)(y / unit));
  }

  public Vec2f toVec2f() {
    return new Vec2f(x, y);
  }

}
