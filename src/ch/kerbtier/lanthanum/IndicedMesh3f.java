package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IndicedMesh3f implements Iterable<Vec3f> {

  private List<Vec3f> vertices = new ArrayList<Vec3f>();
  private List<Integer> triangles = new ArrayList<Integer>();
  
  public void add(Vec3f a, Vec3f b, Vec3f c) {
    int i1 = add(a);
    int i2 = add(b);
    int i3 = add(c);
    
    triangles.add(i1);
    triangles.add(i2);
    triangles.add(i3);
  }
  
  private int add(Vec3f v) {
    if(!vertices.contains(v)) {
      vertices.add(v);
    }
    
    return vertices.indexOf(v);
  }
  
  public int size() {
    return triangles.size() / 3;
  }
  
  public int getIndex1(int triangle) {
    return triangles.get(triangle * 3);
  }
  
  public int getIndex2(int triangle) {
    return triangles.get(triangle * 3 + 1);
  }

  public int getIndex3(int triangle) {
    return triangles.get(triangle * 3 + 2);
  }

  @Override
  public Iterator<Vec3f> iterator() {
    return vertices.iterator();
  }

  public int vertexCount() {
    return vertices.size();
  }
}
