package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class Random {
  private int seed;
  private int factor;
  private int summand;
  private int modulo;

  protected Random() {

  }

  public Random(int seed, int factor, int summand, int modulo) {
    this.seed = seed;
    this.factor = factor;
    this.summand = summand;
    this.modulo = modulo;
  }

  /**
   * returns sometimes true, for sure if current == 0 and for sure not if
   * current == max and with linear probability in between.
   * 
   * @param max
   * @param current
   * @return
   */
  public boolean doIncrease(int max, int current) {
    int rand = nextInt(max) + 1;
    return rand > current;
  }

  public static void main(String[] args) {
    Random rand = get14(0);

    if (isCompleteLoop(rand)) {
      System.out.println("complete!");
    } else {
      System.out.println("incomplete!");
    }

    System.out.println(rand.modulo + " " + rand.factor + " " + rand.summand);

    for (int cnt = 0; cnt < 100; cnt++) {
      System.out.println(rand.nextInt());
    }

    for (int cnt = 0; cnt < 100; cnt++) {
      System.out.println(rand.nextInt(10));
    }

    for (int cnt = 0; cnt < 100; cnt++) {
      System.out.println(rand.nextInt(-10));
    }

  }

  public static Random get12(int seed) {
    return new Random(seed, 14205, 16293, 8192);
  }

  public static Random get14(int seed) {
    return new Random(seed, 38417, 64923, 32768);
  }

  public static Random get10(int seed) {
    return new Random(seed, 3305, 2877, 2048);
  }

  public static Random getComplete(int exp) {
    if (exp > 14) {
      throw new RuntimeException("exponents greater then 14 will result in negative numbers...");
    }
    int modulo = 2 << exp;

    for (int cnt = 0; cnt < 100000; cnt++) {
      Random random = new Random(0, (int) (Math.random() * modulo) + modulo, (int) (Math.random() * modulo) + modulo,
          modulo);
      if (isCompleteLoop(random)) {
        return random;
      }
    }
    return null;
  }

  private static boolean isCompleteLoop(Random random) {
    List<Integer> used = new ArrayList<Integer>();

    while (!used.contains(random.nextInt())) {
      used.add(random.getSeed());
    }

    int dist = used.size() - used.lastIndexOf(random.getSeed());
    return dist == random.modulo;
  }

  public int nextInt() {
    seed = (factor * seed + summand) % modulo;
    return seed;
  }

  public int getSeed() {
    return seed;
  }

  public int nextInt(int to) {
    if (to > modulo) {
      throw new RuntimeException("random to valu cannot be bigger then randoms modulo");
    }

    return nextInt() * to / modulo;
  }

  public float nextFloat() {
    return nextInt() / (float) modulo;
  }

  public float nextFloat(float total) {
    return nextInt() * total / modulo;
  }
}
