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

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentContext;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Constants related to the SARL spaces between the environment agent and the other agents.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
final class MazeSpaceUtils {
  private MazeSpaceUtils() {
  }
  
  /**
   * Replies the identifier of the physic space in the given context.
   * 
   * @param context - the context to consider.
   * @return the id of the Jaak space.
   */
  @Pure
  public static UUID getSpaceIDInContext(final AgentContext context) {
    String _string = context.getID().toString();
    return UUID.nameUUIDFromBytes((_string + "!!!JaakPhysicSpace").getBytes());
  }
}
