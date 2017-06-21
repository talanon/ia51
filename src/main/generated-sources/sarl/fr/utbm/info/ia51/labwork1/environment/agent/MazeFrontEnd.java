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
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import java.util.List;
import java.util.UUID;

/**
 * Capacity to be the front-end for the maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@FunctionalInterface
@SarlSpecification("0.5")
@SarlElementType(17)
@SuppressWarnings("all")
public interface MazeFrontEnd extends Capacity {
  public abstract void sendPerception(final int time, final UUID bodyID, final List<PacmanObject> objects, final Point2i position);
  
  public static class ContextAwareCapacityWrapper<C extends MazeFrontEnd> extends Capacity.ContextAwareCapacityWrapper<C> implements MazeFrontEnd {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void sendPerception(final int time, final UUID bodyID, final List<PacmanObject> objects, final Point2i position) {
      try {
        ensureCallerInLocalThread();
        this.capacity.sendPerception(time, bodyID, objects, position);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
