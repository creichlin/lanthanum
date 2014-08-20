package ch.kerbtier.lanthanum;

public class Triangle3f {
  private Vec3f a, b, c;
  
  public Triangle3f(Vec3f a, Vec3f b, Vec3f c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public Vec3f getA() {
    return a;
  }

  public Vec3f getB() {
    return b;
  }

  public Vec3f getC() {
    return c;
  }
  
  
}
