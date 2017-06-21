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

import fr.utbm.info.ia51.labwork1.environment.agent.MazeChangeQuery;
import fr.utbm.info.ia51.labwork1.environment.agent.MazeManager;
import fr.utbm.info.ia51.labwork1.environment.maze.AgentBody;
import fr.utbm.info.ia51.labwork1.environment.maze.GhostBody;
import fr.utbm.info.ia51.labwork1.environment.maze.Maze;
import fr.utbm.info.ia51.labwork1.environment.maze.PacmanBody;
import fr.utbm.info.ia51.labwork1.environment.maze.PacmanObject;
import fr.utbm.info.ia51.labwork1.environment.maze.SuperPowerAccessor;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Skill;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Skill for managing a maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(19)
@SuppressWarnings("all")
public class DefaultMazeManagerSkill extends Skill implements MazeManager {
  private final Random random = new Random();
  
  private final int width;
  
  private final int height;
  
  private Maze maze;
  
  public DefaultMazeManagerSkill(final int width, final int height) {
    this.width = width;
    this.height = height;
  }
  
  public void install() {
    Maze _maze = new Maze(this.width, this.height);
    this.maze = _maze;
  }
  
  public void uninstall() {
    this.maze = null;
  }
  
  @Pure
  public int getBodyCount() {
    return this.maze.getBodyCount();
  }
  
  @Inline(value = "false", constantExpression = true)
  public boolean applyActions(final List<MazeChangeQuery> actions) {
    return false;
  }
  
  @Pure
  public Map<AgentBody, List<PacmanObject>> getPerceptions() {
    TreeMap<AgentBody, List<PacmanObject>> perceptions = new TreeMap<AgentBody, List<PacmanObject>>();
    return perceptions;
  }
  
  @Pure
  public int getMazeHeight() {
    return this.height;
  }
  
  @Pure
  public int getMazeWidth() {
    return this.width;
  }
  
  @Pure
  public List<PacmanObject> getPacmanObjects() {
    ArrayList<PacmanObject> list = CollectionLiterals.<PacmanObject>newArrayList();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, this.width, true);
    for (final Integer i : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, this.height, true);
      for (final Integer j : _doubleDotLessThan_1) {
        {
          PacmanObject o = this.maze.getObjectAt((i).intValue(), (j).intValue());
          if ((o != null)) {
            list.add(o);
          }
        }
      }
    }
    return list;
  }
  
  @Pure
  public AgentBody getAgentBody(final UUID id) {
    return this.maze.getAgentBody(id);
  }
  
  @Pure
  public SuperPowerAccessor getSuperPowerAccessor(final UUID id) {
    return this.maze.getSuperPowerAccessorFor(id);
  }
  
  public GhostBody createGhost(final int perceptionDistance) {
    return this.maze.<GhostBody>createBody(GhostBody.class, null, perceptionDistance);
  }
  
  public PacmanBody createPacman() {
    return this.maze.<PacmanBody>createBody(PacmanBody.class, null, 0);
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
    DefaultMazeManagerSkill other = (DefaultMazeManagerSkill) obj;
    if (other.width != this.width)
      return false;
    if (other.height != this.height)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.width;
    result = prime * result + this.height;
    return result;
  }
}
