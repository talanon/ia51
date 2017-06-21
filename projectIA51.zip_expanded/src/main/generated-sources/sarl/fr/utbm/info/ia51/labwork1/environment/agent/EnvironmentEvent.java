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
import java.util.EventObject;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Event that describes a change in the environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class EnvironmentEvent extends EventObject {
  private final int time;
  
  private final int width;
  
  private final int height;
  
  private final Map<Point2i, PacmanObject> objects;
  
  public EnvironmentEvent(final UUID source, final int time, final int width, final int height, final Map<Point2i, PacmanObject> objects) {
    super(source);
    this.time = time;
    this.width = width;
    this.height = height;
    this.objects = objects;
  }
  
  @Pure
  public int getTime() {
    return this.time;
  }
  
  @Pure
  public int getWidth() {
    return this.width;
  }
  
  @Pure
  public int getHeight() {
    return this.height;
  }
  
  @Pure
  public Map<Point2i, PacmanObject> getObjects() {
    return this.objects;
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
    EnvironmentEvent other = (EnvironmentEvent) obj;
    if (other.time != this.time)
      return false;
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
    result = prime * result + this.time;
    result = prime * result + this.width;
    result = prime * result + this.height;
    return result;
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 3335358385L;
}
