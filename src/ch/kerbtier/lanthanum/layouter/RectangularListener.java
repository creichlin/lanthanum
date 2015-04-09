package ch.kerbtier.lanthanum.layouter;

import ch.kerbtier.lanthanum.Vec2i;

public interface RectangularListener<T> {
  
  void inform(T element, Vec2i position);

}
