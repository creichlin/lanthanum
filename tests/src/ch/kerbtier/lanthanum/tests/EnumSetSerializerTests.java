package ch.kerbtier.lanthanum.tests;

import java.util.EnumSet;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.EnumSetSerializers;

public class EnumSetSerializerTests {

  @Test
  public void toByteTest() {
    EnumSet<E6> es = EnumSet.of(E6.A, E6.C, E6.F);
    
    byte b = EnumSetSerializers.toByte(es, E6.class);
    
    assertEquals(b, 0b100101);
  }

  @Test
  public void fromByteTest() {
    EnumSet<E6> copy = EnumSetSerializers.fromByte((byte)0b100101, E6.class);
    
    assertEquals(copy, EnumSet.of(E6.A, E6.C, E6.F));
  }

  @Test
  public void toByteBigEnum() {
    EnumSet<E8> es = EnumSet.of(E8.A, E8.C, E8.F, E8.H);
    
    byte b = EnumSetSerializers.toByte(es, E8.class);
    
    assertEquals(b, (byte)0b10100101);
  }

  @Test
  public void fromByteBigEnum() {
    EnumSet<E8> copy = EnumSetSerializers.fromByte((byte)0b10100101, E8.class);
    
    assertEquals(copy, EnumSet.of(E8.A, E8.C, E8.F, E8.H));
  }

  @Test
  public void toChar() {
    EnumSet<E10> es = EnumSet.of(E10.A, E10.C, E10.F, E10.H, E10.J);
    
    char c = EnumSetSerializers.toChar(es, E10.class);
    
    assertEquals(c, (char)0b1010100101);
  }

  @Test
  public void fromChar() {
    EnumSet<E10> copy = EnumSetSerializers.fromChar((char)0b1010100101, E10.class);
    
    assertEquals(copy, EnumSet.of(E10.A, E10.C, E10.F, E10.H, E10.J));
  }

  enum E6 {
    A, B, C, D, E, F
  }

  enum E8 {
    A, B, C, D, E, F, G, H
  }

  enum E10 {
    A, B, C, D, E, F, G, H, I, J
  }

}
