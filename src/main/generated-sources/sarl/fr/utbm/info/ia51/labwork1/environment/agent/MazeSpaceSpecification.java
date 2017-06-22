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

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.utbm.info.ia51.labwork1.environment.agent.DefaultMazeSpaceImpl;
import fr.utbm.info.ia51.labwork1.environment.agent.MazeSpace;
import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.core.SpaceSpecification;
import org.arakhne.afc.vmutil.locale.Locale;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Space that is representing the Jaak environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class MazeSpaceSpecification implements SpaceSpecification<MazeSpace> {
  @Inject
  private DistributedDataStructureService dataStructureService;
  
  @Inject
  private Injector injector;
  
  public MazeSpaceSpecification() {
  }
  
  public MazeSpace create(final SpaceID id, final Object... params) {
    if (((params.length >= 1) && (params[0] instanceof EventListener))) {
      Object _get = params[0];
      return this.createSpace(id, this.dataStructureService, ((EventListener) _get));
    }
    String _string = Locale.getString(MazeSpaceSpecification.class, "NO_EVENT_LISTENER");
    throw new IllegalArgumentException(_string);
  }
  
  /**
   * Creates a {@link Space} that respects this specification.
   * 
   * @param spaceId - the {@link SpaceID} for the newly created {@link Space}
   * @param factory - the factory to be used for creating distributed data structures.
   * @param environmentAgent - the reference to the agent listener which is managing the environment,
   * or <code>null</code> if the current instance of the space is not directly linked to the
   * environment agent.
   * @return an instance of {@link Space}
   */
  public MazeSpace createSpace(final SpaceID spaceId, final DistributedDataStructureService factory, final EventListener environmentAgent) {
    MazeSpace space = new DefaultMazeSpaceImpl(spaceId, factory, environmentAgent);
    this.injector.injectMembers(space);
    return space;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
}
