package ch.kerbtier.lanthanum;

import java.util.EnumSet;

public class EnumSetSerializers {
  
  public static <T extends Enum<T>, U extends Enum<?>> byte toByte(EnumSet<T> set, Class<U> type) {
    byte b = 0;
    
    if(type.getEnumConstants().length > 8) {
      throw new RuntimeException("enum set doesn't fit in one byte");
    }

    for(Enum<?> e: type.getEnumConstants()) {
      if(set.contains(e)) {
        b |= 1 << e.ordinal();
      }
    }

    return b;
  }
  
  public static <E extends Enum<E>> EnumSet<E> toSet(byte b, Class<E> type) {
    E[] enums = type.getEnumConstants();
    EnumSet<E> enumSet = EnumSet.noneOf(type);
    
    for(int bit = 0; bit < 8; bit++) {
      if((b & 1 << bit) > 0) {
        enumSet.add(enums[bit]);
      }
    }

    return enumSet;
  }

  public static <T extends Enum<T>, U extends Enum<?>> String toString(EnumSet<T> set, Class<U> type) {
    StringBuilder sb = new StringBuilder();
    
    for(Enum<?> e: type.getEnumConstants()) {
      if(set.contains(e)) {
        sb.append(e.toString() + ",");
      }
    }

    if(sb.length() > 0) {
    return sb.substring(0, sb.length() - 1);
    }
    return "";
  }
  
  public static <E extends Enum<E>> EnumSet<E> toSet(String in, Class<E> type) {
    EnumSet<E> enumSet = EnumSet.noneOf(type);
    
    for(String key: in.split(",")) {
      enumSet.add(Enum.valueOf(type, key));
    }

    return enumSet;
  }
}
