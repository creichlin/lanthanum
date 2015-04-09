package ch.kerbtier.lanthanum.layouter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.kerbtier.lanthanum.Vec2i;

public class Rectangular<T> {
  
  private List<RectangularAdapter<T>> elements = new ArrayList<RectangularAdapter<T>>();
  
  private Map<RectangularAdapter<T>, Vec2i> positions = new HashMap<RectangularAdapter<T>, Vec2i>();
  private Vec2i size;
  
  private int width;

  public Rectangular(int width, List<T> elements, RectangularAdapterFactory<T> adapterFactory) {
    this.width = width;
    
    for(T element: elements) {
      this.elements.add(adapterFactory.create(element));
    }
    
    layout();
  }

  private void layout() {
    Collections.shuffle(elements);
    
    Vec2i position = new Vec2i(0, 0);
    
    int lineHeight = 0;
    int maxWidth = 0;
    
    for(RectangularAdapter<T> element: elements) {
      Vec2i size = element.getSize();
      
      if(size.x() > width) {
        throw new RuntimeException("cannot fit element with width " + size.x() + "into area with width " + width);
      }
      
      if(position.x() + size.x() > width) { // new line
        position = new Vec2i(0, position.y() + lineHeight);
        lineHeight = size.y();
      } else if(size.y() > lineHeight) {
        lineHeight = size.y();
      }
      
      positions.put(element, position);
      
      position = position.add(new Vec2i(size.x(), 0));
      if(position.x() > maxWidth) {
        maxWidth = position.x();
      }
    }
    
    size = new Vec2i(maxWidth, position.y() +  lineHeight);
    
  }
  
  public Vec2i getSize() {
    return size;
  }
  
  public void inform(RectangularListener<T> listener) {
    for(RectangularAdapter<T> adapter: elements) {
      listener.inform(adapter.getSubject(), positions.get(adapter));
    }
  }
}
