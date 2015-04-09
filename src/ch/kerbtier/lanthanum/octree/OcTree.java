package ch.kerbtier.lanthanum.octree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.kerbtier.lanthanum.Vec3f;

public class OcTree<T> {
  private NodeInfo<T> nodeInfo;
  private OcTreeNode<T> root;
  private Vec3f center;
  private float size;

  public OcTree(NodeInfo<T> nodeInfo, Vec3f center, float size) {
    this.nodeInfo = nodeInfo;
    this.center = center;
    this.size = size;

    root = new OcTreeNode<T>(this, center, size);
  }

  public void add(T node) {
    root.add(node);
  }

  public NodeInfo<T> getNodeInfo() {
    return nodeInfo;
  }

  public String toString() {
    return root.toStr(0);
  }

  public List<Collection<T>> nodesAt(Vec3f position) {
    OcTreeNode<T> current = root;

    List<Collection<T>> result = new ArrayList<Collection<T>>();
    if (current.hasNodes()) {
      result.add(current.getNodes());
    }
    while (current.isSplit()) {
      current = current.findQuadrant(position);
      if (current.hasNodes()) {
        result.add(current.getNodes());
      }
    }

    return result;
  }
}
