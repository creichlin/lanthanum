package ch.kerbtier.lanthanum.tests;

import java.util.EnumSet;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.EnumSetSerializers;

public class EnumSetSerializerTests {

  @Test
  public void toByteTest() {
    EnumSet<E1> es = EnumSet.of(E1.A, E1.C, E1.F);
    
    byte b = EnumSetSerializers.toByte(es, E1.class);
    
    assertEquals(b, 0b100101);
  }

  @Test
  public void fromByteTest() {
    EnumSet<E1> es = EnumSet.of(E1.A, E1.C, E1.F);
    
    byte b = EnumSetSerializers.toByte(es, E1.class);
    
    EnumSet<E1> copy = EnumSetSerializers.toSet(b, E1.class);
    
    assertEquals(copy, es);
  }

  enum E1 {
    A, B, C, D, E, F
  }

}
