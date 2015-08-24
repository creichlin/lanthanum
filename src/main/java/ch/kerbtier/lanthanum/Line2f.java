package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.List;

public class Line2f {

  private Vec2f start;
  private Vec2f end;

  public Line2f(Vec2f start, Vec2f end) {
    this.start = start;
    this.end = end;
  }

  public Line2f(float x, float y, float ex, float ey) {
    start = new Vec2f(x, y);
    end = new Vec2f(ex, ey);
  }

  public final Vec2f start() {
    return start;
  }

  public final Vec2f end() {
    return end;
  }

  public final float sx() {
    return start.x();
  }

  public final float sy() {
    return start.y();
  }

  public final float ex() {
    return end.x();
  }

  public final float ey() {
    return end.y();
  }

  public boolean rectContains(Vec2f point) {
    return point.x() > start.x() && point.x() < end.x() && point.y() > start.y() && point.y() < end.y();
  }

  /**
   * returns the vector from startpoint to endpoint
   * 
   * @return
   */

  public Vec2f toVector() {
    return end.sub(start);
  }

  public Vec2f clamp(Vec2f vec) {
    float nx = vec.x();
    float ny = vec.y();

    if (nx < start.x()) {
      nx = start.x();
    }
    if (nx > end.x()) {
      nx = end.x();
    }

    if (ny < start.y()) {
      ny = start.y();
    }
    if (ny > end.y()) {
      ny = end.y();
    }

    return new Vec2f(nx, ny);
  }

  /**
   * gets intersection of lines, also if lines meet not on lines, use
   * intersects() to check if intersection is on lines
   * 
   * @param o
   * @return
   */
  public Vec2f intersection(Line2f o) {
    Vec2f d1 = toVector();
    Vec2f d2 = o.toVector();
    return cr_intersection(start.x(), start.y(), d1.x(), d1.y(), o.start.x(), o.start.y(), d2.x(), d2.y());
  }

  private Vec2f cr_intersection(float s1x, float s1y, float d1x, float d1y, float s2x, float s2y, float d2x, float d2y) {
    float det = d1x * d2y - d1y * d2x;
    if (det == 0) {
      return null;
    }
    float a = ((s1y - s2y) * d2x - d2y * s1x + d2y * s2x) / det;
    return new Vec2f(d1x, d1y).mul(a).add(s1x, s1y);
  }

  /**
   * intersection of linesegment (this) with line
   * 
   * @param line
   * @return
   */
  public Vec2f intersectionSegmentWithLine(Line2f line) {
    Vec2f d1 = toVector();
    Vec2f d2 = line.toVector();
    return cr_intersection_seg_line(start.x(), start.y(), d1.x(), d1.y(), line.start.x(), line.start.y(), d2.x(),
        d2.y());
  }

  private Vec2f cr_intersection_seg_line(float s1x, float s1y, float d1x, float d1y, float s2x, float s2y, float d2x,
      float d2y) {
    float det = d1x * d2y - d1y * d2x;
    if (det == 0) {
      return null;
    }
    float a = ((s1y - s2y) * d2x - d2y * s1x + d2y * s2x) / det;
    if (a >= 0 && a <= 1) {
      return new Vec2f(d1x, d1y).mul(a).add(s1x, s1y);
    }
    return null;
  }

  public List<Vec2f> interpolateByStep(float step) {

    float total = toVector().length();

    return interpolate((int) (total / step));
  }

  private List<Vec2f> interpolate(int i) {
    List<Vec2f> steps = new ArrayList<Vec2f>();

    Vec2f delta = toVector().div(i);

    for (int cnt = 0; cnt < i; cnt++) {
      steps.add(start.add(delta.mul(cnt)));
    }

    return steps;
  }

  public Line2f translate(Vec2f t) {
    return new Line2f(start.add(t), end.add(t));
  }

  public float angle(Line2f t) {
    return toVector().angle(t.toVector());
  }

  public float factorTo(Vec2f target) {
    Vec2f norm = toVector();
    Vec2f tv = target.sub(start);

    float fac = tv.length() / norm.length();

    if (Math.abs(norm.angle(tv)) < Math.PI / 2) {
      return fac;
    }
    return -fac;
  }

  public Line2f unit() {
    return new Line2f(start, end.sub(start).unit().add(start));
  }

  public Line2f center() {
    Vec2f half = end.sub(start).mul(0.5f);
    return new Line2f(start.sub(half), start.add(half));
  }

  public Line2f merge(Line2f o) {
    PolyLine2f pl = new PolyLine2f();
    pl.push(this);
    pl.push(o);
    return pl.boundingBox();
  }

  /**
   * gets the closest distance to the point. this line interpreted as infinitely long
   * @param p
   * @return
   */
  public float distanceTo(Vec2f p) {
    Vec2f v = start;
    Vec2f w = end;

    float l2 = v.sub(w).lengthSquared();
    float t = p.sub(v).dot(w.sub(v)) / l2;

    Vec2f pro = v.add(w.sub(v).mul(t));
    return pro.distance(p);
  }

}
