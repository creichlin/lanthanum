package ch.kerbtier.lanthanum;

import java.math.BigInteger;

public class Vec2bi implements Cloneable {
  private static final int HASHSEED = 3731;
  private BigInteger x = BigInteger.ZERO;
  private BigInteger y = BigInteger.ZERO;

  public static final Vec2bi ORIGIN = new Vec2bi(0, 0);
  public static final Vec2bi UNIT_X = new Vec2bi(1, 0);
  public static final Vec2bi UNIT_Y = new Vec2bi(0, 1);
  public static final Vec2bi VEC1 = new Vec2bi(1, 1);

  public Vec2bi() {

  }

  public Vec2bi(long x, long y) {
    this.x = BigInteger.valueOf(x);
    this.y = BigInteger.valueOf(y);
  }

  public Vec2bi(BigInteger x, BigInteger y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Vec2bi) {
      return ((Vec2bi) other).x.equals(x) && ((Vec2bi) other).y.equals(y);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return x.hashCode() * HASHSEED + y.hashCode();
  }

  public BigInteger x() {
    return x;
  }

  public BigInteger y() {
    return y;
  }
  
  public BigInteger getX() {
    return x;
  }
  
  public BigInteger getY() {
    return y;
  }

  @Override
  public String toString() {
    return "<Vec2bi:" + x + "/" + y + ">";
  }


}
