package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class Vec2i implements Cloneable {
  private static final int HASHSEED = 3731;
  private int x;
  private int y;

  public static final Vec2i ORIGIN = new Vec2i(0, 0);
  public static final Vec2i UNIT_X = new Vec2i(1, 0);
  public static final Vec2i UNIT_Y = new Vec2i(0, 1);
  public static final Vec2i VEC1 = new Vec2i(1, 1);

  public Vec2i() {
    this.x = 0;
    this.y = 0;
  }

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
  
  public int getX() {
    return x;
  }
  
  public int getY() {
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

  public Vec2i mul(Vec2i factor) {
    return new Vec2i(x * factor.x, y * factor.y);
  }

  public Iterable<Vec2i> iterateCircle(int radius) {
    return iterateCircle(radius, false);
  }

  public Iterable<Vec2i> iterateCircle(int radius, boolean noFill) {
    List<Vec2i> positions = new ArrayList<Vec2i>();
    for (int cy = -radius; cy <= radius; cy++) {
      for (int cx = -radius; cx <= radius; cx++) {
        int dist = cx * cx + cy * cy;

        if (dist <= radius * radius) {
          if (!noFill || dist > (radius - 1) * (radius - 1)) {
            positions.add(new Vec2i(x + cx, y + cy));
          }
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

  @Deprecated
  public Iterable<Vec2i> iterateRect(Vec2i size) {
    List<Vec2i> positions = new ArrayList<Vec2i>();
    for (int ly = 0; ly < size.y(); ly++) {
      for (int lx = 0; lx < size.x(); lx++) {
        positions.add(new Vec2i(x + lx, y + ly));
      }
    }
    return positions;
  }

  public Iterable<Vec2i> iterateRectAt(Vec2i pos) {
    List<Vec2i> positions = new ArrayList<Vec2i>();
    for (int ly = 0; ly < y; ly++) {
      for (int lx = 0; lx < x; lx++) {
        positions.add(new Vec2i(pos.x + lx, pos.y + ly));
      }
    }
    return positions;
  }

  public Iterable<Vec2i> iterateCross(int radius) {
    List<Vec2i> positions = new ArrayList<Vec2i>();
    for (int cnt = -radius; cnt <= radius; cnt++) {
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
    return new Vec2i((int) (x / unit), (int) (y / unit));
  }

  public Vec2f toVec2f() {
    return new Vec2f(x, y);
  }

  public int index(int width) {
    return y * width + x;
  }

  public int rectArea() {
    return y * x;
  }

  public List<Vec2i> getDiamond(int size) {
    List<Vec2i> vecs = new ArrayList<Vec2i>();
    Vec2i left = add(new Vec2i(-1, -1).mul(size));
    Vec2i leftTop = left.add(new Vec2i(1, -1).mul(size));
    left = leftTop;
    for (int cnty = 0; cnty < size * 2 + 1; cnty++) {
      Vec2i curx = leftTop.add(new Vec2i(-1, 1).mul(cnty));
      for (int cntx = 0; cntx < size * 4 + 1; cntx++) {
        vecs.add(curx);
        if (cntx % 2 == 0) {
          curx = curx.add(new Vec2i(1, 0));
        } else {
          curx = curx.add(new Vec2i(0, 1));
        }
      }
    }
    return vecs;
  }

  public List<Vec2i> getDiamond(int x, int y) {
    List<Vec2i> vecs = new ArrayList<Vec2i>();
    Vec2i left = add(new Vec2i(-1, -1).mul(x));
    Vec2i leftTop = left.add(new Vec2i(1, -1).mul(y));
    left = leftTop;
    for (int cnty = 0; cnty < y * 2 + 1; cnty++) {
      Vec2i curx = leftTop.add(new Vec2i(-1, 1).mul(cnty));
      for (int cntx = 0; cntx < x * 4 + 1; cntx++) {
        vecs.add(curx);
        if (cntx % 2 == 0) {
          curx = curx.add(new Vec2i(1, 0));
        } else {
          curx = curx.add(new Vec2i(0, 1));
        }
      }
    }
    return vecs;
  }

  /**
   * this algorithm calculates fat lines. they are properly mirrored and used
   * for a quite good visibility algorithm
   * 
   * @param position
   * @param exclusive
   * @return
   */
  public List<Vec2i> lineToBf(Vec2i position, boolean exclusive) {
    List<Vec2i> list = new ArrayList<Vec2i>();
    Vec2i delta = position.sub(this);
    for (int cnt = 0; cnt < 100; cnt++) {

      Vec2f n = this.toVec2f().add(delta.toVec2f().mul(cnt / 99.0f));

      Vec2i x = n.round();

      if (!list.contains(x)) {
        list.add(x);
      }
    }

    if (exclusive) {
      list.remove(this);
      list.remove(position);
    }

    return list;
  }

  /**
   * algorithm produces wrongly mirrirred lines... weird.
   * 
   * @param position
   * @param exclusive
   * @return
   */
  public List<Vec2i> lineTo(Vec2i position, boolean exclusive) {
    List<Vec2i> line = new ArrayList<Vec2i>();

    Vec2i a = this;
    Vec2i b = position;

    Vec2i delta = b.sub(a);

    Vec2i deltaAbs = delta.abs();

    if (deltaAbs.x >= deltaAbs.y) { // iterate along x-axis

      if (a.x > b.x) {
        Vec2i temp = a;
        a = b;
        b = temp;
        delta = delta.mul(-1);
      }
      for (int x = a.x + 1; x < b.x; x++) {
        int y = roundAbsolute((x - a.x) * delta.y / (float) delta.x) + a.y;
        line.add(new Vec2i(x, y));
      }

    } else { // iterate along y-axis
      if (a.y > b.y) {
        Vec2i temp = a;
        a = b;
        b = temp;
        delta = delta.mul(-1);
      }

      for (int y = a.y + 1; y < b.y; y++) {
        int x = roundAbsolute((y - a.y) * delta.x / (float) delta.y) + a.x;
        line.add(new Vec2i(x, y));

      }
    }

    return line;
  }

  private Vec2i abs() {
    return new Vec2i(Math.abs(x), Math.abs(y));
  }

  public float length() {
    return (float) Math.sqrt(x * x + y * y);
  }

  public float length(Vec2i other) {
    Vec2i diff = other.sub(this);
    return (float) Math.sqrt(diff.x * diff.x + diff.y * diff.y);
  }

  public float shortestLength(List<Vec2i> vecs) {
    float shortest = Float.MAX_VALUE;

    for (Vec2i o : vecs) {
      if (length(o) < shortest) {
        shortest = length(o);
      }
    }

    return shortest;
  }

  public Vec2i independentMax(Vec2i vec) {
    return new Vec2i(Math.max(x, vec.x), Math.max(y, vec.y));
  }

  private static int roundAbsolute(float i) {
    if (i < 0) {
      return -Math.round(-i);
    }
    return Math.round(i);
  }

  public Vec2i fromIndex(int cnt) {
    return new Vec2i(cnt % x, cnt / x);
  }

  public int getArea() {
    return x * y;
  }

  public Vec2i min(Vec2i vec) {
    return new Vec2i(Math.min(x, vec.x()), Math.min(y, vec.y()));
  }

  public Vec2i max(Vec2i vec) {
    return new Vec2i(Math.max(x, vec.x()), Math.max(y, vec.y()));
  }

}
