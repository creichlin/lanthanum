package ch.kerbtier.lanthanum;

import java.util.Arrays;

public class Mat33f {
  private float[] data = new float[9];
  
  public Mat33f(float i, float j, float k, float l, float m, float n, float o, float p, float q){
    data[0] = i;
    data[1] = j;
    data[2] = k;
    data[3] = l;
    data[4] = m;
    data[5] = n;
    data[6] = o;
    data[7] = p;
    data[8] = q;
  }
  public Mat33f () {
    Arrays.fill(data, 0);
    data[0] = 1;
    data[4] = 1;
    data[8] = 1;
  }
  
  public static Mat33f translation(Vec2f t) {
    return translation(t.x(), t.y());
  }
  
  public static Mat33f translation(float x, float y) {
    Mat33f nm = new Mat33f();
    nm.data[2] = x;
    nm.data[5] = y;
    return nm;
  }

  public static Mat33f rotation(float a) {
    Mat33f nm = new Mat33f();
    float cos = (float)Math.cos(a);
    float sin = (float)Math.sin(a);
    
    nm.data[0] = cos;
    nm.data[1] = sin;
    nm.data[3] = -sin;
    nm.data[4] = cos;
    return nm;
  }

  public static Mat33f scale(Vec2f s) {
    return scale(s.x(), s.y());
  }
  
  public static Mat33f scale(float x, float y) {
    Mat33f nm = new Mat33f();
    nm.data[0] = x;
    nm.data[4] = y;
    return nm;
  }
  
  
  public Mat33f mul(Mat33f o) {
    Mat33f n = new Mat33f();
    
    n.data[0] = data[0] * o.data[0] + data[1] * o.data[3] + data[2] * o.data[6];
    n.data[1] = data[0] * o.data[1] + data[1] * o.data[4] + data[2] * o.data[7];
    n.data[2] = data[0] * o.data[2] + data[1] * o.data[5] + data[2] * o.data[8];
    
    n.data[3] = data[3] * o.data[0] + data[4] * o.data[3] + data[5] * o.data[6];
    n.data[4] = data[3] * o.data[1] + data[4] * o.data[4] + data[5] * o.data[7];
    n.data[5] = data[3] * o.data[2] + data[4] * o.data[5] + data[5] * o.data[8];
    
    n.data[6] = data[6] * o.data[0] + data[7] * o.data[3] + data[8] * o.data[6];
    n.data[7] = data[6] * o.data[1] + data[7] * o.data[4] + data[8] * o.data[7];
    n.data[8] = data[6] * o.data[2] + data[7] * o.data[5] + data[8] * o.data[8];
    
    
    return n;
  }
  
  // does an affine transformation, z component is assumed 1
  public Vec2f mul(Vec2f in) {
    return new Vec2f(data[0] * in.x() + data[1] * in.y() + data[2], data[3] * in.x() + data[4] * in.y() + data[5]);
  }

  
  public int hashCode() {
    int tot = 0;
    for(int cnt = 0; cnt < data.length; cnt++) {
      tot = tot * 137 + (int)(data[cnt] * 4321);
    }
    return tot;
  }

  public boolean equals(Object o) {
    if(o instanceof Mat33f) {
      return Arrays.equals(data, ((Mat33f)o).data);
    }
    return false;
  }

  public String toString(){
    return "<Mat3x3:" + data[0] + " " + data[1] + " " + data[2] + "/" + 
        data[3] + " " + data[4] + " " + data[5] + "/" + 
        data[6] + " " + data[7] + " " + data[8] + ">";
  }
  
  public float determinant() {
    
    float d1 = data[4] * data[8] - data[5] * data[7];
    float d2 = data[3] * data[8] - data[5] * data[6];
    float d3 = data[3] * data[7] - data[4] * data[6];
    
    float det = data[0] * d1 - data[1] * d2 + data[2] * d3;

    return det;
  }

}