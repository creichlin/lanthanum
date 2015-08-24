package ch.kerbtier.lanthanum;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

public class Triangle3f {
  private Vec3f a, b, c;
  
  public Triangle3f(Vec3f a, Vec3f b, Vec3f c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public Vec3f getA() {
    return a;
  }

  public Vec3f getB() {
    return b;
  }

  public Vec3f getC() {
    return c;
  }

  public float getCircumference() {
    return b.sub(a).length() + b.sub(c).length() + c.sub(a).length();
  }
  
  
  
  public boolean equals(Object other) {
    if (other instanceof Triangle3f) {
      Triangle3f o = (Triangle3f)other;
      return o.a.equals(a) && o.b.equals(b) && o.c.equals(c);
    }
    return false;
  }
  
  public int hashCode() {
    return a.hashCode() + b.hashCode() * 19 + c.hashCode() * 1237;
  }

  public Set<Vec3f> toSet() {
    HashSet<Vec3f> set = new HashSet<Vec3f>(3);
    set.add(a);
    set.add(b);
    set.add(c);
    return set;
  }

  public float getAngleOfCommonEdgeDeg(Triangle3f other) {
    Set<Vec3f> n = other.toSet();
    Set<Vec3f> o = this.toSet();
    Set<Vec3f> all = Sets.union(n, o);
    Set<Vec3f> edge = Sets.intersection(n, o);
    Set<Vec3f> free = Sets.difference(all, edge);
    
    if(all.size() != 4 || edge.size() != 2) {
      throw new RuntimeException("no common edge");
    }
    
    Vec3f nEdge = Sets.intersection(n, edge).iterator().next();
    Vec3f nSingle = Sets.intersection(n, free).iterator().next();
    
    Vec3f oEdge = Sets.difference(edge, Sets.newHashSet(nEdge)).iterator().next();
    Vec3f oSingle = Sets.intersection(o, free).iterator().next();
    
    Vec3f planeNormal = nEdge.sub(oEdge);
    Vec3f nDir = nSingle.sub(nEdge).projectOnPlane(planeNormal);
    Vec3f oDir = oSingle.sub(oEdge).projectOnPlane(planeNormal);
    
    float angle = nDir.angleDeg(oDir);
    return angle;
  }

  
}
