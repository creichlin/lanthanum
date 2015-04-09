package ch.kerbtier.lanthanum.octree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.kerbtier.lanthanum.Vec3f;

/*      
 *      Y 
 *      ^
 *      |
 *      4                  5
 *      +----------------- +
 *     /|                 /|
 *    /                  / |
 *   /  |               /  |
 * 7/                 6/   |
 * +------------------+    |
 * |    |             |    |
 * |                  |    |
 * |   0+ - - - - - - |- - +1 ---> X
 * |                  |   /
 * |  /               |  /
 * |                  | /
 * |/                 |/
 * +------------------+
 * 3                  2
 * Z
 * 
 * @author creichlin
 *
 * @param <T>
 */

public class OcTreeNode<T> {

  private static final Vec3f[] centers = new Vec3f[] { new Vec3f(-1, -1, -1), new Vec3f(1, -1, -1),
      new Vec3f(1, -1, 1), new Vec3f(-1, -1, 1), new Vec3f(-1, 1, -1), new Vec3f(1, 1, -1), new Vec3f(1, 1, 1),
      new Vec3f(-1, 1, 1) };

  private OcTree<T> tree;
  private Vec3f center;
  private float size;
  private List<T> nodes = new ArrayList<T>();
  private List<OcTreeNode<T>> children = null;

  public OcTreeNode(OcTree<T> tree, Vec3f center, float size) {
    this.center = center;
    this.size = size;
    this.tree = tree;
  }
  
  public OcTreeNode<T> findQuadrant(Vec3f pos) {
    if(pos.x() > center.x()) {
      if(pos.y() > center.y()) {
        if(pos.z() > center.z()) { // 6
          return children.get(6);
        } else { // 5
          return children.get(5);
        }
      } else {
        if(pos.z() > center.z()) { // 2
          return children.get(2);
        } else { // 1
          return children.get(1);
        }
      }
    } else {
      if(pos.y() > center.y()) {
        if(pos.z() > center.z()) { // 7
          return children.get(7);
        } else { // 4
          return children.get(4);
        }
      } else {
        if(pos.z() > center.z()) { // 3
          return children.get(3);
        } else { // 0
          return children.get(0);
        }
      }
    }
  }


  public void add(T node) {
    if (children == null && nodes.size() < 4) { // if there are not many nodes
                                                 // and not split yet, just add
                                                 // them to this
      nodes.add(node);
    } else if (children != null) { // its split, so place it correctly

      placeNode(node);
    } else { // split this block and distribute nodes to children
      children = new ArrayList<OcTreeNode<T>>();
      for (Vec3f dir : centers) {
        Vec3f npos = this.center.add(dir.mul(size / 4));
        children.add(new OcTreeNode<>(tree, npos, size / 2));
      }

      List<T> tmp = nodes;
      nodes = new ArrayList<T>();

      for (T n : tmp) {
        placeNode(n);
      }
    }
  }

  private void placeNode(T node) {
    for (OcTreeNode<T> child : children) {
      if (child.fits(node)) {
        child.add(node);
        return;
      }
    }
    // does fit nowhere, so add it here
    nodes.add(node);
  }

  private boolean fits(T node) {
    Vec3f pos = tree.getNodeInfo().getPosition(node);
    float radius = tree.getNodeInfo().getRadius(node);

    float d = size / 2 - radius;

    if (d < 0) {
      return false;
    }

    Vec3f dv = new Vec3f(d, d, d);

    Vec3f min = center.sub(dv);
    Vec3f max = center.add(dv);
    
    
    boolean inside = pos.x() > min.x() && pos.x() < max.x() && pos.y() > min.y() && pos.y() < max.y()
        && pos.z() > min.z() && pos.z() < max.z();
        
    return inside;
  }

  public String toStr(int i) {
    StringBuilder sb = new StringBuilder();
    for(int cnt = 0; cnt < i; cnt++) {
      sb.append("  ");
    }
    
    sb.append("- [" + nodes.size() + "]" + size + ": " + center + "\n");
    
    if(children != null) {
      for(OcTreeNode<T> tn: children) {
        sb.append(tn.toStr(i + 1));
      }
    }
    
    return sb.toString();
  }

  public Collection<T> getNodes() {
    return nodes;
  }

  public boolean isSplit() {
    return children != null;
  }

  public boolean hasNodes() {
    return nodes.size() > 0;
  }
}
