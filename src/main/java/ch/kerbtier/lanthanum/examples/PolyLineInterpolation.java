package ch.kerbtier.lanthanum.examples;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ch.kerbtier.lanthanum.PolyLine2f;
import ch.kerbtier.lanthanum.Vec2f;

public class PolyLineInterpolation {
  public static void main(String[] args) {
    PolyLine2f line = new PolyLine2f();
    line.push(new Vec2f(0, 0));
    line.push(new Vec2f(2, 4));
    line.push(new Vec2f(3, 3));
    line.push(new Vec2f(2, 6));
    line.push(new Vec2f(5, 4));
    line.push(new Vec2f(6, 1));
    line.push(new Vec2f(9, 8));

    BufferedImage bi = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = bi.createGraphics();

    drawPoly(line, g, Color.RED);
    // drawPoly(line.subDivide(0.1f, 5), g, Color.GREEN);
    
    drawPoly(line.subDivide(0.1f, 10).sampleAlong(new Vec2f(0.066f, 0)), g, Color.YELLOW);

    try {
      ImageIO.write(bi, "png", new File("polyLineInterpolation.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void drawPoly(PolyLine2f line, Graphics2D g, Color c) {
    g.setColor(c);
    Vec2f from = null;
    for (Vec2f v : line) {

      if (from != null) {
        g.drawLine((int) (from.x() * 100), (int) (from.y() * 100),
            (int) (v.x() * 100), (int) (v.y() * 100));
      }

      from = v;
    }
  }
}
