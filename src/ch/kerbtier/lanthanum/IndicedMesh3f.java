package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IndicedMesh3f implements Iterable<Vec3f> {

  private List<Vec3f> vertices = new ArrayList<Vec3f>();
  private Map<Vec3f, Integer> indices = new HashMap<Vec3f, Integer>();
  private List<Integer> triangles = new ArrayList<Integer>();
  
  public void add(Vec3f a, Vec3f b, Vec3f c) {
    int i1 = add(a);
    int i2 = add(b);
    int i3 = add(c);
    
    triangles.add(i1);
    triangles.add(i2);
    triangles.add(i3);
  }

  public void add(Triangle3f t1) {
    add(t1.getA(), t1.getB(), t1.getC());
  }

  public void add(IndicedMesh3f nm) {
    int nt = nm.size();
    
    for(int cnt = 0; cnt < nt; cnt++) {
      add(nm.getVertice1(cnt), nm.getVertice2(cnt), nm.getVertice3(cnt));
    }
  }

  
  private int add(Vec3f v) {
    if(!indices.keySet().contains(v)) {
      vertices.add(v);
      indices.put(v, vertices.size() - 1);
    }
    
    return indices.get(v);
  }
  
  public int size() {
    return triangles.size() / 3;
  }
  
  public Vec3f getVertice1(int triangle) {
    return vertices.get(getIndex1(triangle));
  }
  
  public Vec3f getVertice2(int triangle) {
    return vertices.get(getIndex2(triangle));
  }
  
  public Vec3f getVertice3(int triangle) {
    return vertices.get(getIndex3(triangle));
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
