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

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.janusproject.kernel.repository.UniqueAddressParticipantRepository;
import io.janusproject.kernel.space.SpaceBase;
import io.janusproject.services.distributeddata.DMap;
import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.janusproject.services.executor.ExecutorService;
import io.janusproject.services.logging.LogService;
import io.janusproject.services.network.NetworkService;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.util.SynchronizedCollection;
import io.sarl.lang.util.SynchronizedSet;
import io.sarl.util.Collections3;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.Future;
import org.eclipse.xtext.xbase.lib.Exceptions;
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
abstract class AbstractDistributedSpace extends SpaceBase {
  /**
   * Implement a scope matching a single UUID.
   * 
   * @author $Author: sgalland$
   * @version $FullVersion$
   * @mavengroupid $GroupId$
   * @mavenartifactid $ArtifactId$
   */
  @SarlSpecification("0.5")
  @SarlElementType(8)
  protected static class UUIDScope implements Scope<UUID> {
    private final UUID id;
    
    /**
     * @param id - the identifier.
     */
    public UUIDScope(final UUID id) {
      this.id = id;
    }
    
    /**
     * Replies the identifier that is matched by this scope.
     * 
     * @return the identifier.
     */
    @Pure
    public UUID getID() {
      return this.id;
    }
    
    public boolean matches(final UUID element) {
      return Objects.equal(this.id, element);
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
      UUIDScope other = (UUIDScope) obj;
      if (!java.util.Objects.equals(this.id, other.id)) {
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
      result = prime * result + java.util.Objects.hashCode(this.id);
      return result;
    }
    
    @SyntheticMember
    private final static long serialVersionUID = -1085060158L;
  }
  
  /**
   * @author $Author: sgalland$
   * @version $FullVersion$
   * @mavengroupid $GroupId$
   * @mavenartifactid $ArtifactId$
   */
  @SarlSpecification("0.5")
  @SarlElementType(8)
  private static class AsyncRunner implements Runnable {
    private final EventListener agent;
    
    private final Event event;
    
    /**
     * @param agent
     * @param event
     */
    public AsyncRunner(final EventListener agent, final Event event) {
      this.agent = agent;
      this.event = event;
    }
    
    public void run() {
      this.agent.receiveEvent(this.event);
    }
    
    @Pure
    public String toString() {
      return (((("[agent=" + this.agent) + "; event=") + this.event) + "]");
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
  
  /**
   * Name of the shared attribute that is the ID of the creator of the space.
   */
  protected final static String KEY_CREATORID = "creatorID";
  
  /**
   * Repository of the agents in the space.
   */
  protected final UniqueAddressParticipantRepository<UUID> agents;
  
  /**
   * Shared attributes in the space.
   */
  protected final DMap<String, Serializable> sharedAttributes;
  
  /**
   * The logging service.
   */
  @Inject
  protected LogService logger;
  
  @Inject
  private ExecutorService executorService;
  
  @Inject
  private NetworkService network;
  
  /**
   * @param id - the identifier of the space.
   * @param factory - the factory to be used for creating distributed data structures.
   */
  public AbstractDistributedSpace(final SpaceID id, final DistributedDataStructureService factory) {
    super(id);
    String _string = this.getSpaceID().getID().toString();
    String _plus = (_string + "-mazespace-agents");
    UniqueAddressParticipantRepository<UUID> _uniqueAddressParticipantRepository = new UniqueAddressParticipantRepository<UUID>(_plus, factory);
    this.agents = _uniqueAddressParticipantRepository;
    String _string_1 = this.getSpaceID().getID().toString();
    String _plus_1 = (_string_1 + "-mazespace-attributes");
    this.sharedAttributes = factory.<String, Serializable>getMap(_plus_1);
  }
  
  @Pure
  public SynchronizedSet<UUID> getParticipants() {
    SynchronizedSet<UUID> _xsynchronizedexpression = null;
    synchronized (this.agents) {
      _xsynchronizedexpression = Collections3.<UUID>unmodifiableSynchronizedSet(this.agents.getParticipantIDs());
    }
    return _xsynchronizedexpression;
  }
  
  @Pure
  public UUID getCreatorID() {
    Serializable _get = this.sharedAttributes.get(AbstractDistributedSpace.KEY_CREATORID);
    return ((UUID) _get);
  }
  
  /**
   * Do the emission of the event on the local event bus.
   * 
   * @param event - the event to emit.
   * @param scope - the scope.
   * @return <code>true</code> if the event was dispatched on the local bus.
   */
  protected boolean putOnEventBus(final Event event, final UUID scope) {
    synchronized (this.agents) {
      SynchronizedCollection<EventListener> _listeners = this.agents.getListeners();
      for (final EventListener agent : _listeners) {
        UUID _iD = agent.getID();
        boolean _equals = Objects.equal(scope, _iD);
        if (_equals) {
          this.fireAsync(agent, event);
          return true;
        }
      }
      return false;
    }
  }
  
  /**
   * Do the emission of the event over the network.
   * 
   * @param event - the event to emit.
   * @param scope - the scope.
   */
  protected void putOnNetwork(final Event event, final UUID scope) {
    try {
      AbstractDistributedSpace.UUIDScope _uUIDScope = new AbstractDistributedSpace.UUIDScope(scope);
      this.network.publish(_uUIDScope, event);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        this.logger.error(
          "CANNOT_NOTIFY_OVER_NETWORK", scope, event, e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Send the event to the given listener asyncronously.
   * 
   * @param agent - the listener to notify.
   * @param event - the event to send.
   */
  protected Future<?> fireAsync(final EventListener agent, final Event event) {
    AbstractDistributedSpace.AsyncRunner _asyncRunner = new AbstractDistributedSpace.AsyncRunner(agent, event);
    return this.executorService.submit(_asyncRunner);
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
