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
  
  public Vec3f cross(Vec3f b) {
    Vec3f a = this;
    
    float nx = a.y * b.z - a.z * b.y;
    float ny = a.z * b.x - a.x * b.z;
    float nz = a.x * b.y - a.y * b.x;
    return new Vec3f(nx, ny, nz);
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
  
  private Vec3f normalize() {
    return this.div(this.length());
  }

  public float angleDeg(Vec3f b) {
    return (float)(angle(b) * 180 / Math.PI);
  }

  public float angle(Vec3f b) {
    float dls = dot(b) / (length() * b.length());
    if (dls < -1f)
      dls = -1f;
    else if (dls > 1.0f)
      dls = 1.0f;

    return (float) Math.acos(dls);
  }
  
  /**
   * gives the closest point on the line vector.
   * 
   * always return a position on the line, if plane orthogonal to line trough this does not intersect line, the close line ending is returned.
   * 
   * @param start
   * @param direction
   * @return
   */
  public Vec3f closestPointOn(Vec3f start, Vec3f line) {
    float length = line.length();
    Vec3f v = line.unit();
    
    float factor = (this.sub(start)).dot(v);
    
    if(factor > length) {
      factor = length;
    } else if(factor < 0) {
      factor = 0;
    }
    return start.add(v.mul(factor));
  }
  
  
  public Vec3f projectOnPlane(Vec3f planeNormal) {
    Vec3f x = this;
    Vec3f n = planeNormal;
    
    float d = x.dot(n) / n.length();
    
    Vec3f nNormal = n.normalize();
    
    Vec3f p = nNormal.mul(d);
        
    return x.sub(p);
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
    return "<Vec3f:" + x + "/" + y + "/" + z + ">";
  }

  public Vec3f unit() {
    float l = length();
    return new Vec3f(x / l, y / l, z / l);
  }

  /**
   * both vectors are projected onto the plane, defined by this vector as normal vectors.
   * 
   * the angle between those two projections is returned. it uses normal direction to calculate the sign of the angle.
   * 
   * 
   * @param a
   * @param b
   */
  public float angleOfProjectionsDeg(Vec3f a, Vec3f b) {
    Vec3f pa = a.projectOnPlane(this);
    Vec3f pb = b.projectOnPlane(this);
    
    float angle = (float)(pa.angle(pb) * 180 / Math.PI);

    if(Float.isNaN(angle)) {
      angle = 0;
    }
    
    
    Vec3f cross = pa.mul(pb);
    if(dot(cross) < 0) {
      angle *= -1;
    }
    
    return angle;
  }

  /*
   * does such a division make any sense?
   */
  public Vec3f div(Vec3f o) {
    return new Vec3f(x / o.x, y / o.y, z / o.z);
  }

  public Vec3f min(Vec3f o) {
    return new Vec3f(Math.min(x,  o.x), Math.min(y,  o.y), Math.min(z, o.z));
  }

  public Vec3f max(Vec3f o) {
    return new Vec3f(Math.max(x,  o.x), Math.max(y,  o.y), Math.max(z, o.z));
  }
}
