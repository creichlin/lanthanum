package ch.kerbtier.lanthanum.layouter;

import ch.kerbtier.lanthanum.Vec2i;

public interface RectangularAdapter<T> {
  Vec2i getSize();

  T getSubject();
}
