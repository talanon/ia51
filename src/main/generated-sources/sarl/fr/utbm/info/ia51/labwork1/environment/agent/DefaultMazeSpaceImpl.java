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

import fr.utbm.info.ia51.labwork1.environment.agent.AbstractDistributedSpace;
import fr.utbm.info.ia51.labwork1.environment.agent.Action;
import fr.utbm.info.ia51.labwork1.environment.agent.MazeSpace;
import fr.utbm.info.ia51.labwork1.environment.agent.Perception;
import fr.utbm.info.ia51.labwork1.environment.maze.Direction;
import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Abstract implementation of a distributed space.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
class DefaultMazeSpaceImpl extends AbstractDistributedSpace implements MazeSpace {
  private EventListener environmentAgent;
  
  /**
   * @param id - the identifier of the space.
   * @param factory - the factory to be used for creating distributed data structures.
   * @param environmentAgent - the reference to the agent listener which is managing the environment,
   * or <code>null</code> if the current instance of the space is not directly linked to the
   * environment agent.
   */
  public DefaultMazeSpaceImpl(final SpaceID id, final DistributedDataStructureService factory, final EventListener environmentAgent) {
    super(id, factory);
    this.environmentAgent = environmentAgent;
  }
  
  public void destroy() {
    if ((this.environmentAgent != null)) {
      this.environmentAgent = null;
      this.sharedAttributes.remove(AbstractDistributedSpace.KEY_CREATORID);
    }
  }
  
  public void spawnBody(final EventListener binder) {
    synchronized (this.agents) {
      this.agents.registerParticipant(binder.getID(), binder);
    }
  }
  
  public void killBody(final EventListener binder) {
    synchronized (this.agents) {
      this.agents.unregisterParticipant(binder);
    }
  }
  
  public void notifyPerception(final Perception perception) {
    UUID id = perception.bodyId;
    boolean _putOnEventBus = this.putOnEventBus(perception, id);
    boolean _not = (!_putOnEventBus);
    if (_not) {
      this.putOnNetwork(perception, id);
    }
  }
  
  public void influence(final int influenceTime, final UUID emitter, final Direction influence) {
    Action event = new Action(influenceTime, influence);
    SpaceID _iD = this.getSpaceID();
    Address _address = new Address(_iD, emitter);
    event.setSource(_address);
    if ((this.environmentAgent != null)) {
      this.fireAsync(this.environmentAgent, event);
    } else {
      this.putOnNetwork(event, this.getCreatorID());
    }
  }
  
  public void eventReceived(final SpaceID space, final Scope<?> scope, final Event event) {
    if ((scope instanceof AbstractDistributedSpace.UUIDScope)) {
      UUID id = ((AbstractDistributedSpace.UUIDScope)scope).getID();
      this.putOnEventBus(event, id);
    } else {
      this.logger.error(
        "INVALID_SCOPE", scope, event);
    }
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
