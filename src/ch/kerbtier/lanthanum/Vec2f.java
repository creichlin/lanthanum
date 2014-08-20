package ch.kerbtier.lanthanum;

public class Vec2f implements Cloneable, Comparable<Vec2f> {
  private static final int HASHSEED = 3731;
  private float x;
  private float y;

  public static final Vec2f ORIGIN = new Vec2f(0, 0);

  public Vec2f(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public Vec2f clone() throws CloneNotSupportedException {
    return new Vec2f(x, y);
  }

  public float y() {
    return y;
  }

  public float x() {
    return x;
  }

  public Vec2i toVec2i() {
    return new Vec2i((int) x, (int) y);
  }

  public Vec2f add(Vec2f s) {
    return new Vec2f(x + s.x, y + s.y);
  }

  public Vec2f sub(Vec2f s) {
    return new Vec2f(x - s.x, y - s.y);
  }

  public Vec2f sub(float ox, float oy) {
    return new Vec2f(x - ox, y - oy);
  }

  public Vec2f add(float ox, float oy) {
    return new Vec2f(x + ox, y + oy);
  }

  public Vec2f mul(float factor) {
    return new Vec2f(x * factor, y * factor);
  }

  public Vec2f mul(Vec2f factor) {
    return new Vec2f(x * factor.x(), y * factor.y());
  }

  public float length() {
    return (float) Math.sqrt(x * x + y * y);
  }

  public Vec2f div(float divisor) {
    return new Vec2f(x / divisor, y / divisor);
  }

  public boolean equals(Object other) {
    if (other instanceof Vec2f) {
      return ((Vec2f) other).x == x && ((Vec2f) other).y == y;
    }
    return false;
  }

  public Vec2f rot90() {
    return new Vec2f(y, -x);
  }

  public Vec2f rot(float rad) {
    return Mat33f.rotation(rad).mul(this);
  }

  public Vec2f rotDeg(float deg) {
    return rot((float) (deg * Math.PI / 180f));
  }

  public int hashCode() {
    return (int) (x * HASHSEED + y) * HASHSEED;
  }

  public String toString() {
    return "<Vec2f:" + x + "/" + y + ">";
  }

  public Vec2f unit() {
    float l = length();
    return new Vec2f(x / l, y / l);
  }

  public Vec3f to3f(float z) {
    return new Vec3f(x, y, z);
  }

  public float dot(Vec2f vec) {
    return x * vec.x + y * vec.y;
  }

  /**
   * gets signed angle between vectors clockwise from this to o vector
   * 
   * @param o
   * @return
   */
  public float angle(Vec2f o) {
    float perpDot = x * o.y() - y * o.x();

    return -(float) Math.atan2(perpDot, dot(o));
  }

  /**
   * compare compares the length of two vectors and also the x value.
   * 
   * length has higher priority and only in cases where the lengths are equals
   * the x value is compared.
   * 
   * this behavior is to make it consistent with equals, sorted sets might
   * otherwise not add a different vector with same length.
   */
  @Override
  public int compareTo(Vec2f o) {
    if (o == null) {
      throw new NullPointerException();
    }

    int cmp = Float.compare(length(), o.length());

    if (cmp == 0) {
      cmp = Float.compare(x, o.x);
    }

    return cmp;
  }

}
