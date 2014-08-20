package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class QuadStripe3f {

  protected List<Vec3f> points = new ArrayList<Vec3f>();

  public void append(Vec3f p1, Vec3f p2) {
    points.add(p1);
    points.add(p2);
  }

  public List<Triangle3f> toTriangles() {

    List<Triangle3f> triangles = new ArrayList<Triangle3f>();

    for (int cnt = 2; cnt < points.size(); cnt += 2) {
      Vec3f pa = points.get(cnt - 2);
      Vec3f pb = points.get(cnt);
      Vec3f pc = points.get(cnt + 1);
      Vec3f pd = points.get(cnt - 1);
      
      triangles.add(new Triangle3f(pa, pb, pc));
      triangles.add(new Triangle3f(pc, pd, pa));
    }

    return triangles;
  }

  /**
   * 
   * 1---c---3---f---5
   * |       |       |
   * a       d       |
   * |       |       |
   * 0---b---2---e---4
   * 
   * @return
   */
  public QuadStripe2f flatten() {
    
    if(points.size() < 4) {
      return null;
    }

    float distfz = points.get(0).length();

    Vec3f a = points.get(1).sub(points.get(0));
    
    QuadStripe2f quad2 = new QuadStripe2f();
    
    Vec2f p0 = new Vec2f(distfz, 0);
    Vec2f p1 = new Vec2f(distfz, a.length());
    
    quad2.append(p0, p1);
    
    for(int cnt = 2; cnt < points.size(); cnt += 2) {
      Vec3f d = points.get(cnt - 1).sub(points.get(cnt - 2));
      Vec3f e = points.get(cnt).sub(points.get(cnt - 2));
      Vec3f f = points.get(cnt + 1).sub(points.get(cnt - 1));
      
      Vec2f dd = quad2.get(cnt - 1).sub(quad2.get(cnt - 2));
      
      Vec2f p4 = dd.rot(d.angle(e)).unit().mul(e.length());
      Vec2f p5 = dd.rot(d.angle(f)).unit().mul(f.length());
      
      p4 = p4.add(quad2.get(cnt - 2));
      p5 = p5.add(quad2.get(cnt - 1));
      
      quad2.append(p4,  p5);
    }
    
    return quad2;
  }
}
