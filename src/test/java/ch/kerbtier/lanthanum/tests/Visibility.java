package ch.kerbtier.lanthanum.tests;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import ch.kerbtier.lanthanum.Vec2i;
import ch.kerbtier.lanthanum.graph.VisibleArea;

public class Visibility {

  @Test
  public void testPatternT1() {
    checkGrid(new VisibilityGrid("t1"));
  }

  @Test
  public void testPatternT2() {
    checkGrid(new VisibilityGrid("t2"));
  }

  private void checkGrid(VisibilityGrid grid) {
    VisibleArea va = new VisibleArea(grid.getPov(), 20, grid.getChecker());
    
    Set<Vec2i> visible = va.full();
    
    Assert.assertEquals(VisibilityGrid.draw(visible), VisibilityGrid.draw(grid.getVisible()));
  }
  
  
}
