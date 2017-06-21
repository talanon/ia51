/**
 * $Id$
 * 
 * Copyright (c) 2015-17 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.info.ia51.labwork1.environment.maze;

import fr.utbm.info.ia51.framework.math.Point2i;
import fr.utbm.info.ia51.labwork1.environment.maze.Maze;
import fr.utbm.info.ia51.labwork1.environment.maze.CityObject;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Pill in the PacMan environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class PillObject extends CityObject {
  private final boolean isSuperPill;
  
  public PillObject(final int x, final int y, final Maze maze, final boolean isSuperPill) {
    super(x, y, maze);
    this.isSuperPill = isSuperPill;
  }
  
  public PillObject(final Point2i position, final Maze maze, final boolean isSuperPill) {
    super(position, maze);
    this.isSuperPill = isSuperPill;
  }
  
  @Pure
  public boolean equals(final Object obj) {
    boolean _xblockexpression = false;
    {
      if ((obj == this)) {
        return true;
      }
      boolean _equals = super.equals(obj);
      boolean _not = (!_equals);
      if (_not) {
        return false;
      }
      PillObject o = ((PillObject) obj);
      boolean _isSuperPill = o.isSuperPill();
      boolean _isSuperPill_1 = this.isSuperPill();
      _xblockexpression = (_isSuperPill == _isSuperPill_1);
    }
    return _xblockexpression;
  }
  
  @Pure
  public int hashCode() {
    return Objects.hash(Integer.valueOf(super.hashCode()), Boolean.valueOf(this.isSuperPill));
  }
  
  /**
   * Replies the pill has the super power.
   */
  @Pure
  public boolean isSuperPill() {
    return this.isSuperPill;
  }
  
  @Pure
  @Inline(value = "false", constantExpression = true)
  public final boolean isOccluder() {
    return false;
  }
  
  @Pure
  @Inline(value = "true", constantExpression = true)
  public boolean isPickable() {
    return true;
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 1838564891L;
}
