package ch.kerbtier.lanthanum.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import ch.kerbtier.lanthanum.Mat33f;
import ch.kerbtier.lanthanum.Mat44f;

public class MatrixMultiplications {
  
  @Test
  public void mat33fIdentity() {
    Mat33f m1 = new Mat33f();
    Mat33f m2 = new Mat33f();
    Mat33f res = m1.mul(m2);
    
    assertEquals(res, new Mat33f());
  }

  @Test
  public void mat33fSet1() {
    Mat33f m1 = new Mat33f(1, 2, 3, 4, 5, 6, 7, 8, 9);
    Mat33f m2 = new Mat33f(1, 1, 1, 2, 2, 2, 3, 3, 3);
    Mat33f res = m1.mul(m2);
    
    assertEquals(res, new Mat33f(14, 14, 14, 32, 32, 32, 50, 50, 50));
  }


  @Test
  public void mat44fSet1() {
    Mat44f m1 = new Mat44f(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
    Mat44f m2 = new Mat44f(2, 4, 8, 16, 16, 32, 64, 128, 3, 5, 9, 17, 1, 2, 3, 4);
    Mat44f res = m1.mul(m2);
    
    assertEquals(res, new Mat44f(47, 91, 175, 339, 135, 263, 511, 999, 223, 435, 847, 1659, 311, 607, 1183, 2319));
  }
}
