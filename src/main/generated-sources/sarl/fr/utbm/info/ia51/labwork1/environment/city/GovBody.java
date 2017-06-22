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
package fr.utbm.info.ia51.labwork1.environment.city;

import fr.utbm.info.ia51.framework.math.Point2i;
import fr.utbm.info.ia51.labwork1.environment.city.AgentBody;
import fr.utbm.info.ia51.labwork1.environment.city.City;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The body of the pacman.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class GovBody extends AgentBody {
  private final int IN_ALERT = 0;
  
  private int inAlert;
  
  public GovBody(final int x, final int y, final City city, final UUID agentId, final int perceptionDistance) {
    super(x, y, city, agentId, perceptionDistance);
  }
  
  public GovBody(final Point2i position, final City city, final UUID agentId, final int perceptionDistance) {
    super(position, city, agentId, perceptionDistance);
  }
  
  /**
   * Replies if the gov body has an urgency.
   */
  @Pure
  public boolean isInAlert() {
    return (this.inAlert > 0);
  }
  
  /**
   * Set if the gov body has an urgency.
   */
  void resetInAlert() {
    this.inAlert = this.IN_ALERT;
  }
  
  /**
   * Decrease the urgency.
   */
  void decreaseInAlert() {
    if ((this.inAlert > 0)) {
      this.inAlert--;
    }
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
    GovBody other = (GovBody) obj;
    if (other.IN_ALERT != this.IN_ALERT)
      return false;
    if (other.inAlert != this.inAlert)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.IN_ALERT;
    result = prime * result + this.inAlert;
    return result;
  }
}
