package ch.kerbtier.lanthanum;

public class Vec2f implements Cloneable {
  private static final int HASHSEED = 3731;
  private float x;
  private float y;

  public static final Vec2f ORIGIN = new Vec2f(0, 0);

  public Vec2f(float x, float y) {
    this.x = x;
    this.y = y;
  }
  
  public boolean equals(Object other) {
    if (other instanceof Vec2i) {
      return ((Vec2f) other).x == x && ((Vec2f) other).y == y;
    }
    return false;
  }
  
  public int hashCode() {
    return (int)(x * HASHSEED + y) * HASHSEED;
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
    return new Vec2i((int)x, (int)y);
  }

  public Vec2f add(Vec2f s) {
    return new Vec2f(x + s.x, y + s.y);
  }
  
  public Vec2f sub(Vec2f s) {
    return new Vec2f(x - s.x, y - s.y);
  }

  public Vec2f add(float ox, float oy) {
    return new Vec2f(x + ox, y + oy);
  }
}
