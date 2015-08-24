package ch.kerbtier.lanthanum.tests;

import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Mat33f;
import static org.testng.Assert.*;

public class MatrixDeterminants {
  @Test
  public void test33() {
    Mat33f m = new Mat33f(5, 2, 6, -3, -4, 5, 1, 2, 3);
    assertEquals(m.determinant(), -94.0f);
  }
}
