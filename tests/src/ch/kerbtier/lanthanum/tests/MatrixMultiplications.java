package ch.kerbtier.lanthanum.tests;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import ch.kerbtier.lanthanum.Mat33f;

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
}
