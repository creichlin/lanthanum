package ch.kerbtier.lanthanum.layouter;

public interface RectangularAdapterFactory<T> {
  
  RectangularAdapter<T> create(T instance);
}
