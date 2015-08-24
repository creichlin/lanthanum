package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PolyLine3f implements Iterable<Vec3f> {
  private List<Vec3f> points = new ArrayList<>();

  public PolyLine3f() {

  }

  public void push(Vec3f point) {
    points.add(point);
  }

  @Override
  public Iterator<Vec3f> iterator() {
    return points.iterator();
  }

  public int size() {
    return points.size();
  }

  public Vec3f get(int cnt) {
    return points.get(cnt);
  }

  public PolyLine3f translate(Vec3f t) {
    PolyLine3f pl = new PolyLine3f();

    for (Vec3f v : this) {
      pl.push(v.add(t));
    }

    return pl;
  }

  public PolyLine3f mul(float f) {
    PolyLine3f pl = new PolyLine3f();

    for(Vec3f p: points) {
      pl.push(p.mul(f));
    }
    
    return pl;
  }
  
  
  public PolyLine3f addArithmetic(Vec3f add) {
    PolyLine3f pl = new PolyLine3f();

    for(Vec3f p: points) {
      pl.push(p.add(add));
    }
    
    return pl;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    sb.append("<PolyLine3f: ");
    
    for(Vec3f p: points) {
      sb.append(p.x() + "/" + p.y() + "/" + p.z() + " ");
    }
    
    sb.deleteCharAt(sb.length() - 1);
    sb.append(">");
    
    return sb.toString();
  }

}
