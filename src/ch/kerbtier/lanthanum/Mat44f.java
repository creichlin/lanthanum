package ch.kerbtier.lanthanum;

import java.util.Arrays;

public class Mat44f {
  private float[] data = new float[16];

  public Mat44f (float...floats) {
    Arrays.fill(data, 0);
    int length = Math.min(data.length, floats.length);
    for(int cnt = 0; cnt < length; cnt++) {
      data[cnt] = floats[cnt];
    }
  }

  public Mat44f () {
    Arrays.fill(data, 0);
    data[0] = 1;
    data[5] = 1;
    data[10] = 1;
    data[15] = 1;
  }
  
  public static Mat44f translation(Vec3f t) {
    return translation(t.x(), t.y(), t.z());
  }
  
  public static Mat44f translation(float x, float y, float z) {
    Mat44f nm = new Mat44f();
    nm.data[3] = x;
    nm.data[7] = y;
    nm.data[11] = z;
    return nm;
  }
  
  public static Mat44f rotation(Vec2f axis, float alpha) {
    throw new UnsupportedOperationException();
  }
  
  public static Mat44f rotationXDeg(float alpha) {
    return rotationX((float)(alpha * Math.PI / 180));
  }

  public static Mat44f rotationYDeg(float alpha) {
    return rotationY((float)(alpha * Math.PI / 180));
  }

  public static Mat44f rotationZDeg(float alpha) {
    return rotationZ((float)(alpha * Math.PI / 180));
  }


  public static Mat44f rotationX(float alpha) {
    Mat44f nm = new Mat44f();
    
    float cos = (float)Math.cos(alpha);
    float sin = (float)Math.sin(alpha);
    
    nm.data[5] = cos;
    nm.data[6] = -sin;
    nm.data[8] = sin;
    nm.data[9] = cos;
    
    return nm;
  }

  public static Mat44f rotationY(float alpha) {
    Mat44f nm = new Mat44f();
    
    float cos = (float)Math.cos(alpha);
    float sin = (float)Math.sin(alpha);
    
    nm.data[0] = cos;
    nm.data[2] = sin;
    nm.data[8] = -sin;
    nm.data[10] = cos;
    
    return nm;
  }

  public static Mat44f rotationZ(float alpha) {
    Mat44f nm = new Mat44f();
    
    float cos = (float)Math.cos(alpha);
    float sin = (float)Math.sin(alpha);
    
    nm.data[0] = cos;
    nm.data[1] = -sin;
    nm.data[4] = sin;
    nm.data[5] = cos;
    
    return nm;
  }

  public static Mat44f scale(Vec3f s) {
    return scale(s.x(), s.y(), s.z());
  }
  
  public static Mat44f scale(float x, float y, float z) {
    Mat44f nm = new Mat44f();
    nm.data[0] = x;
    nm.data[5] = y;
    nm.data[10] = z;
    return nm;
  }
  
  
  public Mat44f mul(Mat44f o) {
    Mat44f n = new Mat44f();
    
    n.data[0] = data[0] * o.data[0] + data[1] * o.data[4] + data[2] * o.data[8] + data[3] * o.data[12];
    n.data[1] = data[0] * o.data[1] + data[1] * o.data[5] + data[2] * o.data[9] + data[3] * o.data[13];
    n.data[2] = data[0] * o.data[2] + data[1] * o.data[6] + data[2] * o.data[10] + data[3] * o.data[14];
    n.data[3] = data[0] * o.data[3] + data[1] * o.data[7] + data[2] * o.data[11] + data[3] * o.data[15];
    
    n.data[4] = data[4] * o.data[0] + data[5] * o.data[4] + data[6] * o.data[8] + data[7] * o.data[12];
    n.data[5] = data[4] * o.data[1] + data[5] * o.data[5] + data[6] * o.data[9] + data[7] * o.data[13];
    n.data[6] = data[4] * o.data[2] + data[5] * o.data[6] + data[6] * o.data[10] + data[7] * o.data[14];
    n.data[7] = data[4] * o.data[3] + data[5] * o.data[7] + data[6] * o.data[11] + data[7] * o.data[15];
    
    n.data[8] = data[8] * o.data[0] + data[9] * o.data[4] + data[10] * o.data[8] + data[11] * o.data[12];
    n.data[9] = data[8] * o.data[1] + data[9] * o.data[5] + data[10] * o.data[9] + data[11] * o.data[13];
    n.data[10] = data[8] * o.data[2] + data[9] * o.data[6] + data[10] * o.data[10] + data[11] * o.data[14];
    n.data[11] = data[8] * o.data[3] + data[9] * o.data[7] + data[10] * o.data[11] + data[11] * o.data[15];
    
    n.data[12] = data[12] * o.data[0] + data[13] * o.data[4] + data[14] * o.data[8] + data[15] * o.data[12];
    n.data[13] = data[12] * o.data[1] + data[13] * o.data[5] + data[14] * o.data[9] + data[15] * o.data[13];
    n.data[14] = data[12] * o.data[2] + data[13] * o.data[6] + data[14] * o.data[10] + data[15] * o.data[14];
    n.data[15] = data[12] * o.data[3] + data[13] * o.data[7] + data[14] * o.data[11] + data[15] * o.data[15];
    
    return n;
  }
  
  // does an affine transformation, 4th component is assumed 1
  public Vec3f mul(Vec3f in) {
    return new Vec3f(data[0] * in.x() + data[1] * in.y() + data[2] * in.z() + data[3],
                      data[4] * in.x() + data[5] * in.y() + data[6] * in.z() + data[7],
                      data[8] * in.x() + data[9] * in.y() + data[10] * in.z() + data[11]
                     );
  }

  
  public int hashCode() {
    int tot = 0;
    for(int cnt = 0; cnt < data.length; cnt++) {
      tot = tot * 137 + (int)(data[cnt] * 4321);
    }
    return tot;
  }

  public boolean equals(Object o) {
    if(o instanceof Mat44f) {
      return Arrays.equals(data, ((Mat44f)o).data);
    }
    return false;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    
    for(float v: data) {
      sb.append(v + ", ");
    }
    
    sb.delete(sb.length() - 2, sb.length());
    
    return "<Mat4x4:" + sb + ">";
  }

  public float get(int i) {
    return data[i];
  }
}
