package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class ProbabilityPicker<T> {
  private float total = 0;
  private List<T> elements = new ArrayList<>();
  private List<Float> edges = new ArrayList<>();
  
  public void add(float probability, T element) {
    total += probability;
    edges.add(total);
    elements.add(element);
  }
  
  public T get(Random random) {
    float value = random.nextFloat(total);
    
    for(int cnt = 0; cnt < edges.size(); cnt++) {
      if(value < edges.get(cnt)) {
        return elements.get(cnt);
      }
    }
    assert false;
    return null;
  }

  public float getTotal() {
    return total;
  }
}
