package ch.kerbtier.lanthanum.octree;

import ch.kerbtier.lanthanum.Vec3f;

public interface NodeInfo<T> {
  float getRadius(T node);
  Vec3f getPosition(T node);
}
