package ch.kerbtier.lanthanum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PolyLine2f implements Iterable<Vec2f>, Comparable<PolyLine2f> {
  private List<Vec2f> points = new ArrayList<Vec2f>();

  public PolyLine2f() {

  }

  public void push(Vec2f point) {
    points.add(point);
  }

  public void push(Line2f line) {
    points.add(line.start());
    points.add(line.end());
  }

  public List<Line2f> closedLines() {
    List<Line2f> lines = new ArrayList<Line2f>();

    for (int cnt = 1; cnt < points.size(); cnt++) {
      lines.add(new Line2f(points.get(cnt - 1), points.get(cnt)));
    }
    lines.add(new Line2f(points.get(points.size() - 1), points.get(0)));
    return lines;
  }

  public PolyLine2f subDivide(float dfac, int times) {

    PolyLine2f np = new PolyLine2f();

    for (int cnt = 0; cnt < points.size(); cnt++) {
      Vec2f point1 = points.get(cnt);

      np.push(point1);

      if (cnt == 0) { // first point
        Vec2f point2 = points.get(cnt + 1);
        Vec2f dir2 = points.get(cnt + 2).sub(point2).mul(-dfac);

        Vec2f target = point1.mul(0.5f).add(point2.add(dir2).mul(0.5f));
        np.push(target);
      } else if (cnt == points.size() - 2) { // second last point
        Vec2f point2 = points.get(cnt + 1);
        Vec2f dir1 = points.get(cnt - 1).sub(point1).mul(-dfac);

        Vec2f target = point1.add(dir1).mul(0.5f).add(point2.mul(0.5f));
        np.push(target);
      } else if (cnt == points.size() - 1) { // last point

      } else {
        Vec2f point2 = points.get(cnt + 1);
        Vec2f dir1 = points.get(cnt - 1).sub(point1).mul(-dfac);
        Vec2f dir2 = points.get(cnt + 2).sub(point2).mul(-dfac);

        Vec2f target = point1.add(dir1).mul(0.5f)
            .add(point2.add(dir2).mul(0.5f));
        np.push(target);
      }
    }

    if (times > 1) {
      return np.subDivide(dfac, times - 1);
    }

    return np;

  }

  public PolyLine2f sampleAlong(Vec2f v) {
    PolyLine2f np = new PolyLine2f();

    Vec2f ortho = v.rot90();
    Line2f orthoLine = new Line2f(new Vec2f(0, 0), ortho);

    float smallestDist = Float.MAX_VALUE;
    for (Vec2f point : points) {
      Line2f l = new Line2f(point, point.sub(v));
      Vec2f intersection = orthoLine.intersection(l);
      Line2f line = new Line2f(intersection, point);
      float dist = line.toVector().length();
      if (dist < smallestDist) {
        smallestDist = dist;
      }
    }

    smallestDist = smallestDist / v.length();
    smallestDist = (float) Math.ceil(smallestDist);

    Line2f start = orthoLine.translate(v.mul(smallestDist));

    Vec2f intersection = intersection(start);

    int idx = 1;
    while (intersection != null) {
      np.push(intersection);
      Line2f il = start.translate(v.mul(idx));
      intersection = intersection(il);
      idx++;
    }

    return np;
  }


  @Override
  public Iterator<Vec2f> iterator() {
    return points.iterator();
  }

  public int size() {
    return points.size();
  }

  public Vec2f get(int cnt) {
    return points.get(cnt);
  }

  public Line2f boundingBox() {
    Vec2f first = points.get(0);
    float minx = first.x();
    float miny = first.y();
    float maxx = first.x();
    float maxy = first.y();

    for (Vec2f v : points) {
      if (v.x() < minx) {
        minx = v.x();
      }

      if (v.x() > maxx) {
        maxx = v.x();
      }

      if (v.y() < miny) {
        miny = v.y();
      }

      if (v.y() > maxy) {
        maxy = v.y();
      }
    }

    return new Line2f(new Vec2f(minx, miny), new Vec2f(maxx, maxy));
  }

  public PolyLine2f translate(Vec2f t) {
    PolyLine2f pl = new PolyLine2f();

    for (Vec2f v : this) {
      pl.push(v.add(t));
    }

    return pl;
  }

  public float getMaxDelta(PolyLine2f next) {
    float ymin1 = getMinX();
    float ymin2 = next.getMinX();
    float ymin = ymin1 > ymin2 ? ymin1 : ymin2;

    float ymax1 = getMaxX();
    float ymax2 = next.getMaxX();
    float ymax = ymax1 < ymax2 ? ymax1 : ymax2;

    
    float maxDiff = -Float.MAX_VALUE;

    for (float cnt = ymin; cnt < ymax; cnt += 0.01f) {
      float b = maxYAt(cnt);
      float t = next.minYAt(cnt);

      float diff = b - t;
      
      if (diff > maxDiff) {
        maxDiff = diff;
      }
    }

    return maxDiff;
  }

  public float maxYAt(float x) {

    float maxY = -Float.MAX_VALUE;

    Line2f cross = new Line2f(new Vec2f(x, 0), new Vec2f(x, 1));

    for(Line2f segment: closedLines()) {
      Vec2f intersection = segment.intersectionSegmentWithLine(cross);
      if(intersection != null) {
        if(intersection.y() > maxY) {
          maxY = intersection.y();
        }
      }
    }

    return maxY;
  }

  public float minYAt(float x) {

    float minY = Float.MAX_VALUE;

    Line2f cross = new Line2f(new Vec2f(x, 0), new Vec2f(x, 1));

    for(Line2f segment: closedLines()) {
      Vec2f intersection = segment.intersectionSegmentWithLine(cross);
      if(intersection != null) {
        if(intersection.y() < minY) {
          minY = intersection.y();
        }
      }
    }

    return minY;
  }

  public float getMinY() {
    float miny = Float.MAX_VALUE;
    for (Vec2f v : points) {
      if (v.y() < miny) {
        miny = v.y();
      }
    }
    return miny;
  }

  public float getMaxY() {
    float maxy = -Float.MAX_VALUE;
    for (Vec2f v : points) {
      if (v.y() > maxy) {
        maxy = v.y();
      }
    }
    return maxy;
  }

  public float getMinX() {
    float minx = Float.MAX_VALUE;
    for (Vec2f v : points) {
      if (v.x() < minx) {
        minx = v.x();
      }
    }
    return minx;
  }

  public float getMaxX() {
    float maxx = -Float.MAX_VALUE;
    for (Vec2f v : points) {
      if (v.x() > maxx) {
        maxx = v.x();
      }
    }
    return maxx;
  }

  public List<Vec2f> getCorners(float deg) {
    
    List<Vec2f> is = new ArrayList<Vec2f>();
    
    float a = (float)(deg /180 * Math.PI);
    
    List<Line2f> lines = closedLines();
    
    for(int cnt = 0; cnt < lines.size(); cnt++) {
      Line2f l1 = cnt == 0 ? lines.get(lines.size() - 1) : lines.get(cnt - 1);
      Line2f l2 = lines.get(cnt);
      
      float angle = Math.abs(l1.angle(l2));
      
      if(angle >= a) {
        is.add(l1.intersection(l2));
      }
      
    }
    
    return is;
  }

  public PolyLine2f mul(float f) {
    PolyLine2f pl = new PolyLine2f();

    for(Vec2f p: points) {
      pl.push(p.mul(f));
    }
    
    return pl;
  }

  public PolyLine2f rotate(float angle) {
    PolyLine2f pl = new PolyLine2f();

    for(Vec2f p: points) {
      pl.push(p.rot(angle));
    }
    
    return pl;
  }

  public Set<Vec2f> intersections(Line2f line) {
    Set<Vec2f> intersections = new HashSet<Vec2f>();

    for(Line2f seg: closedLines()) {
      Vec2f intersection = seg.intersectionSegmentWithLine(line);
      if(intersection != null) {
        intersections.add(intersection);
      }
    }
    return intersections;
  }
  
  
  public Vec2f intersection(Line2f line) {
    for(Line2f seg: closedLines()) {
      Vec2f intersection = seg.intersectionSegmentWithLine(line);
      if(intersection != null) {
        return intersection;
      }
    }
    return null;
  }

  public PolyLine2f add(Vec2f add) {
    PolyLine2f pl = new PolyLine2f();

    for(Vec2f p: points) {
      pl.push(p.add(add));
    }
    
    return pl;
  }

  public Vec2f average() {
    Vec2f tot = new Vec2f(0, 0);
    int cnt = 0;
    
    for(Vec2f p: points) {
      tot = tot.add(p);
      cnt++;
    }
    
    return tot.div(cnt);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    sb.append("<PolyLine2f: ");
    
    for(Vec2f p: points) {
      sb.append(p.x() + "/" + p.y() + " ");
    }
    
    sb.deleteCharAt(sb.length() - 1);
    sb.append(">");
    
    return sb.toString();
  }

  /**
   * compares the averages (centers) of all vertices
   */
  @Override
  public int compareTo(PolyLine2f other) {
    if(other == null) {
      throw new NullPointerException();
    }

    return average().compareTo(other.average());
  }
}
