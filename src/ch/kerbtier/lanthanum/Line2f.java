package ch.kerbtier.lanthanum;

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

  public Vec2f size() {
    return end.sub(start);
  }
}
