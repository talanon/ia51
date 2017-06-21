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
import fr.utbm.info.ia51.labwork1.environment.agent.Action;
import fr.utbm.info.ia51.labwork1.environment.agent.Controller;
import fr.utbm.info.ia51.labwork1.environment.agent.DefaultMazeFrontEndSkill;
import fr.utbm.info.ia51.labwork1.environment.agent.DefaultMazeManagerSkill;
import fr.utbm.info.ia51.labwork1.environment.agent.EnvironmentEvent;
import fr.utbm.info.ia51.labwork1.environment.agent.EnvironmentListener;
import fr.utbm.info.ia51.labwork1.environment.agent.MazeChangeQuery;
import fr.utbm.info.ia51.labwork1.environment.agent.MazeFrontEnd;
import fr.utbm.info.ia51.labwork1.environment.agent.MazeManager;
import fr.utbm.info.ia51.labwork1.environment.agent.PacManIsDead;
import fr.utbm.info.ia51.labwork1.environment.agent.Player;
import fr.utbm.info.ia51.labwork1.environment.agent.RunBeginingOfStep;
import fr.utbm.info.ia51.labwork1.environment.agent.RunEndOfStep;
import fr.utbm.info.ia51.labwork1.environment.maze.AgentBody;
import fr.utbm.info.ia51.labwork1.environment.maze.Direction;
import fr.utbm.info.ia51.labwork1.environment.maze.DriverBody;
import fr.utbm.info.ia51.labwork1.environment.maze.GovBody;
import fr.utbm.info.ia51.labwork1.environment.maze.CityObject;
import fr.utbm.info.ia51.labwork1.players.Ghost;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Skill;
import io.sarl.lang.util.ClearableReference;
import io.sarl.util.Scopes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Definition of the agent that is managing the environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(16)
@SuppressWarnings("all")
public class Environment extends Agent {
  private ArrayList<EnvironmentListener> listeners = CollectionLiterals.<EnvironmentListener>newArrayList();
  
  private Player player;
  
  private Controller controller;
  
  private int time = 0;
  
  private final LinkedList<MazeChangeQuery> actions = CollectionLiterals.<MazeChangeQuery>newLinkedList();
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    Integer width = ((Integer) _get);
    Object _get_1 = occurrence.parameters[1];
    Integer height = ((Integer) _get_1);
    Object _get_2 = occurrence.parameters[2];
    Integer nbGhosts = ((Integer) _get_2);
    Object _get_3 = occurrence.parameters[3];
    Integer perceptionDistance = ((Integer) _get_3);
    int _size = ((List<Object>)Conversions.doWrapArray(occurrence.parameters)).size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(4, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Object o = occurrence.parameters[(i).intValue()];
        if ((o instanceof EnvironmentListener)) {
          this.listeners.add(((EnvironmentListener)o));
        }
      }
    }
    DefaultMazeManagerSkill _defaultMazeManagerSkill = new DefaultMazeManagerSkill((width).intValue(), (height).intValue());
    this.<DefaultMazeManagerSkill>setSkill(_defaultMazeManagerSkill, MazeManager.class);
    DefaultMazeFrontEndSkill _defaultMazeFrontEndSkill = new DefaultMazeFrontEndSkill();
    this.<DefaultMazeFrontEndSkill>setSkill(_defaultMazeFrontEndSkill, MazeFrontEnd.class);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    EventSpace _defaultSpace = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultSpace();
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    Address _defaultAddress = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultAddress();
    Controller _controller = new Controller(_defaultSpace, _defaultAddress);
    this.controller = _controller;
    MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
    GovBody pacmanBody = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.createPacman();
    UUID _agentId = pacmanBody.getAgentId();
    Logger _logger = Logger.getLogger(this.getID().toString());
    Player _player = new Player(_agentId, _logger);
    this.player = _player;
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, (nbGhosts).intValue(), true);
    for (final Integer i_1 : _doubleDotLessThan_1) {
      {
        MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
        DriverBody ghostBody = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.createGhost((perceptionDistance).intValue());
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(Ghost.class, ghostBody.getAgentId(), _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.getDefaultContext());
      }
    }
    this.firePlayerBinding();
    this.fireControllerBinding();
    this.fireEnvironmentChange();
  }
  
  protected void fireEnvironmentChange() {
    TreeMap<Point2i, CityObject> objects = new TreeMap<Point2i, CityObject>();
    MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
    List<CityObject> _pacmanObjects = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getPacmanObjects();
    for (final CityObject obj : _pacmanObjects) {
      objects.put(obj.getPosition(), obj);
    }
    UUID _iD = this.getID();
    MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
    int _mazeWidth = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.getMazeWidth();
    MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2 = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
    int _mazeHeight = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2.getMazeHeight();
    Map<Point2i, CityObject> _unmodifiableMap = Collections.<Point2i, CityObject>unmodifiableMap(objects);
    EnvironmentEvent event = new EnvironmentEvent(_iD, this.time, _mazeWidth, _mazeHeight, _unmodifiableMap);
    for (final EnvironmentListener listener : this.listeners) {
      listener.environmentChanged(event);
    }
  }
  
  protected void firePlayerBinding() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.bindPlayer(this.player);
    }
  }
  
  protected void firePlayerUnbinding() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.unbindPlayer(this.player);
    }
  }
  
  protected void fireControllerBinding() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.bindController(this.controller);
    }
  }
  
  protected void fireControllerUnbinding() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.unbindController(this.controller);
    }
  }
  
  protected void fireGameOver() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.gameOver();
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
    this.firePlayerUnbinding();
    this.fireControllerUnbinding();
    this.<MazeManager>clearSkill(MazeManager.class);
    this.<MazeFrontEnd>clearSkill(MazeFrontEnd.class);
  }
  
  @SyntheticMember
  private void $behaviorUnit$Action$2(final Action occurrence) {
    synchronized (this) {
      UUID _uUID = occurrence.getSource().getUUID();
      MazeChangeQuery _mazeChangeQuery = new MazeChangeQuery(_uUID, occurrence.direction);
      this.actions.add(_mazeChangeQuery);
      int _size = this.actions.size();
      MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
      int _bodyCount = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getBodyCount();
      int _minus = (_bodyCount - 1);
      boolean _greaterEqualsThan = (_size >= _minus);
      if (_greaterEqualsThan) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        RunEndOfStep _runEndOfStep = new RunEndOfStep();
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_runEndOfStep, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultAddress()));
      }
    }
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$Action$2(final Action it, final Action occurrence) {
    return (occurrence.time >= it.time);
  }
  
  @SyntheticMember
  private void $behaviorUnit$RunEndOfStep$3(final RunEndOfStep occurrence) {
    synchronized (this) {
      Direction avatarDirection = this.player.getDirection();
      UUID _bodyId = this.player.getBodyId();
      MazeChangeQuery avatarAction = new MazeChangeQuery(_bodyId, avatarDirection);
      this.actions.add(avatarAction);
      MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
      _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getSuperPowerAccessor(this.player.getBodyId()).decreaseSuperPower();
      MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
      boolean _applyActions = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.applyActions(this.actions);
      if (_applyActions) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        PacManIsDead _pacManIsDead = new PacManIsDead();
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_pacManIsDead);
        this.fireGameOver();
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
      }
      this.time++;
      this.fireEnvironmentChange();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      RunBeginingOfStep _runBeginingOfStep = new RunBeginingOfStep();
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_runBeginingOfStep);
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$RunBeginingOfStep$4(final RunBeginingOfStep occurrence) {
    synchronized (this) {
      this.actions.clear();
      MazeManager _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$castSkill(MazeManager.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = this.$getSkill(MazeManager.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
      Set<Map.Entry<AgentBody, List<CityObject>>> _entrySet = _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getPerceptions().entrySet();
      for (final Map.Entry<AgentBody, List<CityObject>> e : _entrySet) {
        MazeFrontEnd _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND$CALLER = this.$castSkill(MazeFrontEnd.class, (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND.get() == null) ? (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND = this.$getSkill(MazeFrontEnd.class)) : this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND);
        _$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND$CALLER.sendPerception(this.time, e.getKey().getAgentId(), e.getValue(), e.getKey().getPosition());
      }
    }
  }
  
  @Extension
  @ImportedCapacityFeature(MazeManager.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(MazeManager.class, ($0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || $0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) ? ($0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = $0$getSkill(MazeManager.class)) : $0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER)", imported = MazeManager.class)
  private MazeManager $CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER() {
    if (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) {
      this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER = $getSkill(MazeManager.class);
    }
    return $castSkill(MazeManager.class, this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEMANAGER);
  }
  
  @Extension
  @ImportedCapacityFeature(MazeFrontEnd.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(MazeFrontEnd.class, ($0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND == null || $0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND.get() == null) ? ($0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND = $0$getSkill(MazeFrontEnd.class)) : $0$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND)", imported = MazeFrontEnd.class)
  private MazeFrontEnd $CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND$CALLER() {
    if (this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND == null || this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND.get() == null) {
      this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND = $getSkill(MazeFrontEnd.class);
    }
    return $castSkill(MazeFrontEnd.class, this.$CAPACITY_USE$FR_UTBM_INFO_IA51_LABWORK1_ENVIRONMENT_AGENT_MAZEFRONTEND);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(DefaultContextInteractions.class, ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $0$getSkill(DefaultContextInteractions.class)) : $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS)", imported = DefaultContextInteractions.class)
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Lifecycle.class, ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $0$getSkill(Lifecycle.class)) : $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE)", imported = Lifecycle.class)
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$RunEndOfStep(final RunEndOfStep occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$RunEndOfStep$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Action(final Action occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$Action$2(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Action$2(occurrence));
    }
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$RunBeginingOfStep(final RunBeginingOfStep occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$RunBeginingOfStep$4(occurrence));
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
    Environment other = (Environment) obj;
    if (other.time != this.time)
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
    return result;
  }
  
  @SyntheticMember
  public Environment(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Environment(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
}
