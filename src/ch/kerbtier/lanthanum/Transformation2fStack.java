package ch.kerbtier.lanthanum;

import java.util.Stack;

public class Transformation2fStack {
  private Stack<Transformation2f> stack = new Stack<Transformation2f>();
  private Stack<Mat33f> combined = new Stack<Mat33f>();

  public Transformation2fStack() {
    // push an identity matrix on top
    combined.push(new Mat33f());
  }
  
  public Transformation2f peek() {
    return stack.peek();
  }

  public Transformation2f pop() {
    combined.pop();
    return stack.pop();
  }

  public Transformation2f push(Transformation2f item) {
    combined.push(item.getMatrix().mul(combined.peek()));
    return stack.push(item);
  }

  public Vec2f transform(Vec2f in) {
    return combined.peek().mul(in);
  }
  
  public Mat33f matrix() {
    return combined.peek();
  }
}
