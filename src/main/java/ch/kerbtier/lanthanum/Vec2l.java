package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class Vec2l implements Cloneable {
  private static final int HASHSEED = 3731;
  private long x;
  private long y;

  public static final Vec2l ORIGIN = new Vec2l(0, 0);
  public static final Vec2l UNIT_X = new Vec2l(1, 0);
  public static final Vec2l UNIT_Y = new Vec2l(0, 1);
  public static final Vec2l VEC1 = new Vec2l(1, 1);

  public Vec2l() {
    this.x = 0;
    this.y = 0;
  }

  public Vec2l(long x, long y) {
    this.x = x;
    this.y = y;
  }

  public boolean equals(Object other) {
    if (other instanceof Vec2l) {
      return ((Vec2l) other).x == x && ((Vec2l) other).y == y;
    }
    return false;
  }

  public int hashCode() {
    return (int)(x * HASHSEED + y);
  }

  @Override
  public Vec2l clone() throws CloneNotSupportedException {
    return new Vec2l(x, y);
  }

  public long x() {
    return x;
  }

  public long y() {
    return y;
  }
  
  public long getX() {
    return x;
  }
  
  public long getY() {
    return y;
  }

  public Vec2l add(Vec2l pos) {
    return new Vec2l(x + pos.x, y + pos.y);
  }

  /*
   * inside, exclusive end
   */
  public boolean isInside(long sx, long sy, long ex, long ey) {
    if (x < sx || y < sy || x >= ex || y >= ey) {
      return false;
    }
    return true;
  }

  public Vec2l divide(long i) {
    return new Vec2l(x / i, y / i);
  }

  public Vec2l mul(long factor) {
    return new Vec2l(x * factor, y * factor);
  }

  public Vec2l mul(Vec2l factor) {
    return new Vec2l(x * factor.x, y * factor.y);
  }

  @Override
  public String toString() {
    return "<Vec2l:" + x + "/" + y + ">";
  }

  public Vec2l invert() {
    return new Vec2l(-x, -y);
  }

  public Vec2l add(long x2, long y2) {
    return new Vec2l(x + x2, y + y2);
  }

  public Vec2l sub(Vec2l from) {
    return new Vec2l(x - from.x, y - from.y);
  }

  public Vec2l rot90() {
    return new Vec2l(y, -x);
  }

  public Vec2l divide(float unit) {
    return new Vec2l((long) (x / unit), (long) (y / unit));
  }

  public Vec2f toVec2f() {
    return new Vec2f(x, y);
  }

  public long index(long width) {
    return y * width + x;
  }

  public long rectArea() {
    return y * x;
  }

  private Vec2l abs() {
    return new Vec2l(Math.abs(x), Math.abs(y));
  }

  public float length() {
    return (float) Math.sqrt(x * x + y * y);
  }

  public float length(Vec2l other) {
    Vec2l diff = other.sub(this);
    return (float) Math.sqrt(diff.x * diff.x + diff.y * diff.y);
  }

  public float shortestLength(List<Vec2l> vecs) {
    float shortest = Float.MAX_VALUE;

    for (Vec2l o : vecs) {
      if (length(o) < shortest) {
        shortest = length(o);
      }
    }

    return shortest;
  }

  public Vec2l independentMax(Vec2l vec) {
    return new Vec2l(Math.max(x, vec.x), Math.max(y, vec.y));
  }

  public Vec2l fromIndex(long cnt) {
    return new Vec2l(cnt % x, cnt / x);
  }

  public long getArea() {
    return x * y;
  }

  public Vec2l min(Vec2l vec) {
    return new Vec2l(Math.min(x, vec.x()), Math.min(y, vec.y()));
  }

  public Vec2l max(Vec2l vec) {
    return new Vec2l(Math.max(x, vec.x()), Math.max(y, vec.y()));
  }

}
