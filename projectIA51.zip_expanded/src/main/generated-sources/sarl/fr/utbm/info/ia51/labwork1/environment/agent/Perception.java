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
package fr.utbm.info.ia51.labwork1.environment.agent;

import fr.utbm.info.ia51.framework.math.Point2i;
import fr.utbm.info.ia51.labwork1.environment.maze.PacmanObject;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Perception for an agent.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(13)
@SuppressWarnings("all")
public class Perception extends Event {
  public final int time;
  
  public final List<PacmanObject> objects;
  
  public final Point2i position;
  
  public final UUID bodyId;
  
  public Perception(final int time, final UUID bodyID, final List<PacmanObject> objects, final Point2i position) {
    this.time = time;
    this.bodyId = bodyID;
    this.objects = objects;
    this.position = position;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Perception other = (Perception) obj;
    if (other.time != this.time)
      return false;
    if (!Objects.equals(this.bodyId, other.bodyId)) {
      return false;
    }
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.time;
    result = prime * result + Objects.hashCode(this.bodyId);
    return result;
  }
  
  /**
   * Returns a String representation of the Perception event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("time  = ").append(this.time);
    result.append("objects  = ").append(this.objects);
    result.append("position  = ").append(this.position);
    result.append("bodyId  = ").append(this.bodyId);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 809217616L;
}
