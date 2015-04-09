package ch.kerbtier.lanthanum;

public class Line3f {
  private Vec3f v1;
  private Vec3f v2;
  
  public Line3f(Vec3f v1, Vec3f v2) {
    this.v1 = v1;
    this.v2 = v2;
  }

  public Vec3f getV1() {
    return v1;
  }

  public Vec3f getV2() {
    return v2;
  }

  public float length() {
    return v2.sub(v1).length();
  }
}
