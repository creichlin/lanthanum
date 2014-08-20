package ch.kerbtier.lanthanum;


public class Vec3f implements Cloneable {
  private static final int HASHSEED = 3731;
  private float x;
  private float y;
  private float z;

  public static final Vec3f ORIGIN = new Vec3f(0, 0, 0);

  public Vec3f(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  @Override
  public Vec3f clone() throws CloneNotSupportedException {
    return new Vec3f(x, y, z);
  }

  public float y() {
    return y;
  }

  public float x() {
    return x;
  }

  public float z() {
    return z;
  }

  public Vec3f add(Vec3f s) {
    return new Vec3f(x + s.x, y + s.y, z + s.z);
  }
  
  public Vec3f sub(Vec3f s) {
    return new Vec3f(x - s.x, y - s.y, z - s.z);
  }

  public Vec3f add(float ox, float oy, float oz) {
    return new Vec3f(x + ox, y + oy, z + oz);
  }

  public Vec3f mul(float factor) {
    return new Vec3f(x * factor, y * factor, z * factor);
  }
  
  public Vec3f mul(Vec3f f) {
    return new Vec3f(x * f.x, y * f.y, z * f.z);
  }

  
  public float length() {
    return (float)Math.sqrt(x * x + y * y + z * z);
  }

  public Vec3f div(float divisor) {
    return new Vec3f(x / divisor, y / divisor, z / divisor);
  }
  
  public float dot(Vec3f vec) {
    return x * vec.x + y * vec.y + z * vec.z;
  }

  public float angle(Vec3f b) {
    float dls = dot(b) / (length() * b.length());
    if (dls < -1f)
      dls = -1f;
    else if (dls > 1.0f)
      dls = 1.0f;

    return (float) Math.acos(dls);
  }
  
  public boolean equals(Object other) {
    if (other instanceof Vec3f) {
      return ((Vec3f) other).x == x && ((Vec3f) other).y == y && ((Vec3f)other).z == z;
    }
    return false;
  }
  
  public int hashCode() {
    return (int)((x * HASHSEED + y) * HASHSEED + z) * HASHSEED;
  }
  
  public String toString() {
    return "<Vec2f:" + x + "/" + y + "/" + z + ">";
  }

  public Vec3f unit() {
    float l = length();
    return new Vec3f(x / l, y / l, z / l);
  }
}
