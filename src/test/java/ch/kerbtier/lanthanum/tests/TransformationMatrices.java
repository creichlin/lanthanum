package ch.kerbtier.lanthanum.tests;

import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Mat33f;
import ch.kerbtier.lanthanum.Vec2f;
import static org.testng.Assert.*;

public class TransformationMatrices {
  
  @Test
  public void scale1() {
    Mat33f scale22 = Mat33f.scale(2, 2);
    Vec2f unit = new Vec2f(1, 1);
    assertEquals(scale22.mul(unit), new Vec2f(2, 2));
  }
  
  @Test
  public void scale2() {
    Mat33f scale22 = Mat33f.scale(-1, 3);
    Vec2f unit = new Vec2f(-0.45f, 0.2f);
    assertEquals(scale22.mul(unit), new Vec2f(0.45f, 0.6f));
  }
  
  @Test
  public void translate() {
    Mat33f trans = Mat33f.translation(4, 2);
    Vec2f unit = new Vec2f(1, 1);
    assertEquals(trans.mul(unit), new Vec2f(5, 3));
  }
  
  @Test
  public void rotate1() {
    Mat33f trans = Mat33f.rotation((float)(Math.PI / 4));
    Vec2f unit = new Vec2f(1, 1);
    assertEquals(trans.mul(unit), new Vec2f(1.4142135f, 0));
  }
  
  @Test
  public void rotate2() {
    Mat33f trans = Mat33f.rotation((float)(Math.PI / 2));
    Vec2f unit = new Vec2f(1, 1);
    assertEquals(trans.mul(unit), new Vec2f(0.99999994f, -1));
  }
  
  @Test
  public void chainTransformations() {
    Mat33f scale = Mat33f.scale(2, 2);
    Mat33f rotate = Mat33f.rotation((float)(Math.PI / 5));
    Mat33f translate =Mat33f.translation(3,  2);
    
    Vec2f bh = scale.mul(new Vec2f(1, 1));
    bh = rotate.mul(bh);
    bh = translate.mul(bh);
    
    Mat33f combined = translate.mul(rotate).mul(scale);
    
    assertEquals(combined.mul(new Vec2f(1, 1)), bh);
  }
  
  
}
