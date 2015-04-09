package ch.kerbtier.lanthanum.tests;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.kerbtier.lanthanum.Vec2i;
import ch.kerbtier.lanthanum.graph.Blocked;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VisibilityGrid {

  private List<String> lines;

  public VisibilityGrid(String key) {
    Gson gson = new Gson();
    Reader reader = new InputStreamReader(VisibilityGrid.class.getResourceAsStream("visibilityPatterns.json"),
        Charset.forName("UTF-8"));

    Type collectionType = new TypeToken<Map<String, Map<String, List<String>>>>() {
    }.getType();

    Map<String, Map<String, List<String>>> values = gson.fromJson(reader, collectionType);

    lines = values.get(key).get("blocks");
  }

  public Vec2i getPov() {
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        String token = line.substring(x, x + 1);
        if (token.equals("X")) {
          return new Vec2i(x, y);
        }
      }
    }
    throw new RuntimeException("no pov found");
  }

  public Set<Vec2i> getVisible() {
    Set<Vec2i> visibles = new HashSet<Vec2i>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        String token = line.substring(x, x + 1);
        if (token.equals("O")) {
          visibles.add(new Vec2i(x, y));
        }
      }
    }
    visibles.add(getPov());
    return visibles;
  }

  public Blocked getChecker() {
    return new Blocked() {

      @Override
      public boolean blocked(Vec2i vec) {

        try {
          String token = lines.get(vec.y()).substring(vec.x(), vec.x() + 1);
          if (token.equals("#")) {
            return true;
          }
        } catch (Exception e) {
          return true;
        }

        return false;
      }
    };
  }
  
  public static String draw(Set<Vec2i> vecs) {
    List<String> lines = new ArrayList<String>();
    Vec2i max = Vec2i.ORIGIN;
    
    for(Vec2i v: vecs) {
      max = max.independentMax(v);
    }
    for(int y = 0; y < max.y() + 1; y++) {
      String line = "";
      for(int x = 0; x < max.x() + 1; x++) {
        if(vecs.contains(new Vec2i(x, y))) {
          line += "O";
        } else {
          line += ".";
        }
      }
      lines.add(line);
    }
    
    return "\n" + Joiner.on("\n").join(lines);
  }

}
