package ch.kerbtier.lanthanum.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestDistance<T> {
  private Map<T, T> comingFrom = new HashMap<T, T>();
  private Map<T, Integer> distances = new HashMap<T, Integer>();

  public int get(T element) {
    if (!distances.containsKey(element)) {
      return -1;
    }
    return distances.get(element);
  }

  public void setFirst(T first) {
    distances.put(first, 0);
  }

  public void update(T from, T to) {
    update(from, to, 1);
  }
  
  public void update(T from, T to, int dist) {
    int distance = distances.get(from) + dist;

    boolean update = false;

    if (distances.containsKey(to)) {
      if (distance < distances.get(to)) {
        update = true;
      }
    } else {
      update = true;
    }

    if (update) {
      distances.put(to, distance);
      comingFrom.put(to, from);
    }
  }

  public T previous(T current) {
    return comingFrom.get(current);
  }

  public T closestFrom(Collection<T> queue) {
    int lowest = Integer.MAX_VALUE;
    T selected = null;
    for (T p : queue) {
      if (distances.get(p) < lowest) {
        lowest = distances.get(p);
        selected = p;
      }
    }
    return selected;
  }

  public List<T> pathTo(T target) {
    List<T> positions = new ArrayList<T>();
    T current = target;

    while (current != null) {
      positions.add(0, current);
      current = previous(current);
    }

    return positions;
  }
}
