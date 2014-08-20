package ch.kerbtier.lanthanum;

public class Transformation2f {
  private Vec2f scale;
  private Vec2f translation;
  private float rotation;
  
  private Mat33f matrix = null;
  
  public Vec2f getScale() {
    return scale;
  }

  public void setScale(Vec2f scale) {
    matrix = null;
    this.scale = scale;
  }

  public Vec2f getTranslation() {
    return translation;
  }

  public void setTranslation(Vec2f translation) {
    matrix = null;
    this.translation = translation;
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float rotation) {
    matrix = null;
    this.rotation = rotation;
  }
  
  public Mat33f getMatrix() {
    if(matrix == null) {
      Mat33f temp = Mat33f.translation(translation);
      temp = temp.mul(Mat33f.rotation(rotation));
      matrix = temp.mul(Mat33f.scale(scale));
    }
    return matrix;
  }
}
