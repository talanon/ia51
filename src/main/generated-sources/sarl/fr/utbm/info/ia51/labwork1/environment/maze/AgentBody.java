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
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Abstract element for all the agent bodies.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public abstract class AgentBody extends CityObject implements Comparable<AgentBody> {
  private final UUID agentId;
  
  private final int perceptionDistance;
  
  public AgentBody(final int x, final int y, final Maze maze, final UUID agentId, final int perceptionDistance) {
    super(x, y, maze);
    this.agentId = agentId;
    this.perceptionDistance = perceptionDistance;
  }
  
  public AgentBody(final Point2i position, final Maze maze, final UUID agentId, final int perceptionDistance) {
    super(position, maze);
    this.agentId = agentId;
    this.perceptionDistance = perceptionDistance;
  }
  
  /**
   * Replies the distance of perception of the body.
   * 
   * @return the distance.
   */
  @Pure
  public int getPerceptionDistance() {
    return this.perceptionDistance;
  }
  
  @Pure
  public final UUID getAgentId() {
    return this.agentId;
  }
  
  @Pure
  @Inline(value = "false", constantExpression = true)
  public final boolean isOccluder() {
    return false;
  }
  
  public int compareTo(final AgentBody o) {
    if ((o == this)) {
      return 0;
    }
    if ((o == null)) {
      return Integer.MAX_VALUE;
    }
    return this.agentId.compareTo(o.agentId);
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
    AgentBody other = (AgentBody) obj;
    if (!Objects.equals(this.agentId, other.agentId)) {
      return false;
    }
    if (other.perceptionDistance != this.perceptionDistance)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.agentId);
    result = prime * result + this.perceptionDistance;
    return result;
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -3346683719L;
}
