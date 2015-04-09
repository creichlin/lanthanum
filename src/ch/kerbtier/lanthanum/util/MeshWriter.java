package ch.kerbtier.lanthanum.util;

import java.util.Map;

import ch.kerbtier.lanthanum.IndicedMesh3f;
import ch.kerbtier.lanthanum.Vec3f;

public class MeshWriter {

  public static String writePov(IndicedMesh3f mesh) {
    StringBuilder data = new StringBuilder();

    data.append("mesh2{ vertex_vectors {" + mesh.vertexCount() + ",\n");
    for (Vec3f v : mesh) {
      data.append("<" + v.x() + ", " + v.y() + ", " + v.z() + ">,\n");
    }

    data.deleteCharAt(data.length() - 1);

    data.append("} face_indices { " + mesh.size() + ", \n");

    for (int cnt = 0; cnt < mesh.size(); cnt += 1) {
      data.append("<" + mesh.getIndex1(cnt) + ", " + mesh.getIndex2(cnt) + ", " + mesh.getIndex3(cnt) + ">\n");
    }

    data.deleteCharAt(data.length() - 1);

    data.append("}}");
    return data.toString();
  }

  public static String writeObj(IndicedMesh3f mesh) {
    StringBuilder data = new StringBuilder();

    for (Vec3f v : mesh) {
      data.append("v " + v.x() + " " + v.y() + " " + v.z() + "\n");
    }

    for (int cnt = 0; cnt < mesh.size(); cnt += 1) {
      data.append("f " + (mesh.getIndex1(cnt) + 1) + " " + (mesh.getIndex2(cnt) + 1) + " " + (mesh.getIndex3(cnt) + 1)
          + "\n");
    }

    return data.toString();
  }

  public static String writePly(Map<IndicedMesh3f, Vec3f> meshes) {
    int totalVertices = 0;
    int totalFaces = 0;

    for (IndicedMesh3f im : meshes.keySet()) {
      totalVertices += im.vertexCount();
      totalFaces += im.size();
    }

    StringBuilder data = new StringBuilder();
    data.append("ply\n");
    data.append("format ascii 1.0\n");

    data.append("element vertex " + totalVertices + "\n");
    data.append("property float x\n");
    data.append("property float y\n");
    data.append("property float z\n");
    data.append("property uchar red\n");
    data.append("property uchar green\n");
    data.append("property uchar blue\n");

    data.append("element face " + totalFaces + "\n");
    data.append("property list uchar int vertex_indices\n");
    data.append("end_header\n");

    for (IndicedMesh3f mesh : meshes.keySet()) {
      Vec3f color = meshes.get(mesh);
      color = color.mul(255);
      for (Vec3f v : mesh) {
        data.append(v.x() + " " + v.y() + " " + v.z() + " " + (int)color.x() + " " + (int)color.y() + " " + (int)color.z() + "\n");
      }
    }

    
    int index = 0;

    for (IndicedMesh3f mesh : meshes.keySet()) {
      for (int cnt = 0; cnt < mesh.size(); cnt += 1) {
        data.append("3 " + (index + mesh.getIndex1(cnt)) + " " + (index + mesh.getIndex2(cnt)) + " " + (index + mesh.getIndex3(cnt)) + "\n");

      }
      index += mesh.vertexCount();
    }

    return data.toString();
  }

  public static String writePly(IndicedMesh3f mesh) {
    StringBuilder data = new StringBuilder();
    data.append("ply\n");
    data.append("format ascii 1.0\n");

    data.append("element vertex " + mesh.vertexCount() + "\n");
    data.append("property float x\n");
    data.append("property float y\n");
    data.append("property float z\n");

    data.append("element face " + mesh.size() + "\n");
    data.append("property list uchar int vertex_indices\n");
    data.append("end_header\n");

    for (Vec3f v : mesh) {
      data.append(v.x() + " " + v.y() + " " + v.z() + "\n");
    }

    for (int cnt = 0; cnt < mesh.size(); cnt += 1) {
      data.append("3 " + mesh.getIndex1(cnt) + " " + mesh.getIndex2(cnt) + " " + mesh.getIndex3(cnt) + "\n");
    }

    return data.toString();
  }
}
