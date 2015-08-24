package ch.kerbtier.lanthanum.tests;

import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Triangle3f;
import ch.kerbtier.lanthanum.Vec3f;
import static org.testng.Assert.*;

public class Triangle3fTests {

  @Test
  public void angleBetweenCommonEdgeTriangles1() {
    Vec3f p1 = new Vec3f(0, 0, 0);
    Vec3f p2 = new Vec3f(1, 0, 0);
    Vec3f p3 = new Vec3f(1, 1, 0);
    Vec3f p4 = new Vec3f(0, 1, 0);
    
    Triangle3f t1 = new Triangle3f(p1, p2, p3);
    Triangle3f t2 = new Triangle3f(p3, p4, p2);
    Triangle3f t3 = new Triangle3f(p3, p4, p1);
    
    assertEquals(t1.getAngleOfCommonEdgeDeg(t2), 0, 0.00001f);
    assertEquals(t1.getAngleOfCommonEdgeDeg(t3), 180, 0.00001f);
    
  }

  @Test
  public void angleBetweenCommonEdgeTriangles2() {
    Vec3f p1 = new Vec3f(0, 0, 0);
    Vec3f p2 = new Vec3f(1, 0, 0);
    Vec3f p3 = new Vec3f(1, 1, 0);
    Vec3f p4 = new Vec3f(0, 1, 0);
    
    Triangle3f t1 = new Triangle3f(p3, p2, p1);
    Triangle3f t2 = new Triangle3f(p3, p4, p2);
    Triangle3f t3 = new Triangle3f(p3, p4, p1);
    
    assertEquals(t1.getAngleOfCommonEdgeDeg(t2), 0, 0.00001f);
    assertEquals(t1.getAngleOfCommonEdgeDeg(t3), 180, 0.00001f);
    
  }
}
