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
import fr.utbm.info.ia51.framework.math.Vector2i;
import fr.utbm.info.ia51.labwork1.environment.maze.AgentBody;
import fr.utbm.info.ia51.labwork1.environment.maze.CityObject;
import fr.utbm.info.ia51.labwork1.environment.maze.PillObject;
import fr.utbm.info.ia51.labwork1.environment.maze.WallObject;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Define the maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class Maze {
  /**
   * @author $Author: sgalland$
   * @version $FullVersion$
   * @mavengroupid $GroupId$
   * @mavenartifactid $ArtifactId$
   */
  @SarlSpecification("0.5")
  @SarlElementType(8)
  private static class PrimWall {
    public final Point2i passage;
    
    public final Point2i corridor;
    
    public final Point2i passageCandidate1;
    
    public final Point2i passageCandidate2;
    
    public final Point2i passageCandidate3;
    
    public PrimWall(final int passageX, final int passageY, final int corridorX, final int corridorY, final int passageCandidateX1, final int passageCandidateY1, final int passageCandidateX2, final int passageCandidateY2, final int passageCandidateX3, final int passageCandidateY3) {
      Point2i _point2i = new Point2i(passageX, passageY);
      this.passage = _point2i;
      Point2i _point2i_1 = new Point2i(corridorX, corridorY);
      this.corridor = _point2i_1;
      Point2i _point2i_2 = new Point2i(passageCandidateX1, passageCandidateY1);
      this.passageCandidate1 = _point2i_2;
      Point2i _point2i_3 = new Point2i(passageCandidateX2, passageCandidateY2);
      this.passageCandidate2 = _point2i_3;
      Point2i _point2i_4 = new Point2i(passageCandidateX3, passageCandidateY3);
      this.passageCandidate3 = _point2i_4;
    }
    
    @Pure
    public String toString() {
      return ((("passage=" + this.passage) + "|corridor=") + this.corridor);
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
   * Max number of super pills in the world.
   */
  private final static int MAX_SUPER_PILLS = 5;
  
  /**
   * Matrix of the objects.
   */
  private final CityObject[][] grid;
  
  private final TreeMap<UUID, AgentBody> bodies = new TreeMap<UUID, AgentBody>();
  
  /**
   * Width of the world.
   */
  private final int width;
  
  /**
   * Height of the world.
   */
  private final int height;
  
  /**
   * Random generator.
   */
  private final Random random = new Random();
  
  /**
   * @param width is the width of the world.
   * @param height is the height of the world.
   */
  public Maze(final int width, final int height) {
    this.width = width;
    this.height = height;
    Object _newInstance = Array.newInstance(CityObject.class, width, height);
    this.grid = ((CityObject[][]) _newInstance);
    this.buildWorld(this.width, this.height);
  }
  
  /**
   * Build the maze and fill the spaces with pills.
   * 
   * @param width width of the maze.
   * @param height height of the maze.
   */
  private void buildWorld(final int width, final int height) {
    this.buildMaze();
    for (int i = 0; (i < width); i++) {
      for (int j = 0; (j < height); j++) {
        CityObject _get = this.grid[i][j];
        boolean _tripleEquals = (_get == null);
        if (_tripleEquals) {
          PillObject _pillObject = new PillObject(i, j, this, false);
          this.grid[i][j] = _pillObject;
        }
      }
    }
    for (int i = 0; (i < Maze.MAX_SUPER_PILLS); i++) {
      {
        int x = this.random.nextInt(width);
        int y = this.random.nextInt(height);
        while ((!this.canMoveInside(x, y))) {
          {
            x = this.random.nextInt(width);
            y = this.random.nextInt(height);
          }
        }
        PillObject _pillObject = new PillObject(x, y, this, true);
        this.grid[x][y] = _pillObject;
      }
    }
  }
  
  /**
   * Build the maze with the Prim's algorithm.
   */
  private void buildMaze() {
    for (int i = 0; (i < this.width); i++) {
      for (int j = 0; (j < this.height); j++) {
        WallObject _wallObject = new WallObject(i, j, this);
        this.grid[i][j] = _wallObject;
      }
    }
    ArrayList<Maze.PrimWall> walls = new ArrayList<Maze.PrimWall>();
    int x = this.random.nextInt(this.width);
    int y = this.random.nextInt(this.height);
    this.grid[x][y] = null;
    Maze.PrimWall _primWall = new Maze.PrimWall(x, (y - 1), x, (y - 2), (x - 1), (y - 2), x, (y - 3), (x + 1), (y - 2));
    walls.add(_primWall);
    Maze.PrimWall _primWall_1 = new Maze.PrimWall(x, (y + 1), x, (y + 2), (x - 1), (y + 2), x, (y + 3), (x + 1), (y + 2));
    walls.add(_primWall_1);
    Maze.PrimWall _primWall_2 = new Maze.PrimWall((x - 1), y, (x - 2), y, (x - 2), (y + 1), (x - 3), y, (x - 2), (y - 1));
    walls.add(_primWall_2);
    Maze.PrimWall _primWall_3 = new Maze.PrimWall((x + 1), y, (x + 2), y, (x + 2), (y - 1), (x + 3), y, (x + 2), (y + 1));
    walls.add(_primWall_3);
    while ((!walls.isEmpty())) {
      {
        Maze.PrimWall diggeableWall = walls.remove(this.random.nextInt(walls.size()));
        if (((((((diggeableWall.corridor.getX() >= 0) && (diggeableWall.corridor.getX() < this.width)) && (diggeableWall.corridor.getY() >= 0)) && (diggeableWall.corridor.getY() < this.height)) && (this.grid[diggeableWall.corridor.getX()][diggeableWall.corridor.getY()] instanceof WallObject)) && (this.grid[diggeableWall.passage.getX()][diggeableWall.passage.getY()] instanceof WallObject))) {
          this.grid[diggeableWall.passage.getX()][diggeableWall.passage.getY()] = null;
          this.grid[diggeableWall.corridor.getX()][diggeableWall.corridor.getY()] = null;
          this.addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate1);
          this.addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate2);
          this.addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate3);
        }
      }
    }
  }
  
  private void addWallCandidate(final List<Maze.PrimWall> walls, final Point2i corridor, final Point2i candidate) {
    final Vector2i v = candidate.operator_minus(corridor);
    final Vector2i r = v.clone();
    r.perpendicularize();
    int _x = corridor.getX();
    int _x_1 = v.getX();
    int _plus = (_x + _x_1);
    int _y = corridor.getY();
    int _y_1 = v.getY();
    int _plus_1 = (_y + _y_1);
    int _x_2 = corridor.getX();
    int _x_3 = v.getX();
    int _multiply = (2 * _x_3);
    int _plus_2 = (_x_2 + _multiply);
    int _y_2 = corridor.getY();
    int _y_3 = v.getY();
    int _multiply_1 = (2 * _y_3);
    int _plus_3 = (_y_2 + _multiply_1);
    int _x_4 = corridor.getX();
    int _x_5 = v.getX();
    int _multiply_2 = (3 * _x_5);
    int _plus_4 = (_x_4 + _multiply_2);
    int _y_4 = corridor.getY();
    int _y_5 = v.getY();
    int _multiply_3 = (3 * _y_5);
    int _plus_5 = (_y_4 + _multiply_3);
    int _x_6 = corridor.getX();
    int _x_7 = v.getX();
    int _multiply_4 = (2 * _x_7);
    int _plus_6 = (_x_6 + _multiply_4);
    int _x_8 = r.getX();
    int _plus_7 = (_plus_6 + _x_8);
    int _y_6 = corridor.getY();
    int _y_7 = v.getY();
    int _multiply_5 = (2 * _y_7);
    int _plus_8 = (_y_6 + _multiply_5);
    int _y_8 = r.getY();
    int _plus_9 = (_plus_8 + _y_8);
    int _x_9 = corridor.getX();
    int _x_10 = v.getX();
    int _multiply_6 = (2 * _x_10);
    int _plus_10 = (_x_9 + _multiply_6);
    int _x_11 = r.getX();
    int _minus = (_plus_10 - _x_11);
    int _y_9 = corridor.getY();
    int _y_10 = v.getY();
    int _multiply_7 = (2 * _y_10);
    int _plus_11 = (_y_9 + _multiply_7);
    int _y_11 = r.getY();
    int _minus_1 = (_plus_11 - _y_11);
    Maze.PrimWall pw = new Maze.PrimWall(_plus, _plus_1, _plus_2, _plus_3, _plus_4, _plus_5, _plus_7, _plus_9, _minus, _minus_1);
    walls.add(pw);
  }
  
  /**
   * Replies if the cell at the given coordinates can receive a body.
   * 
   * @param x
   * @param y
   * @return <code>true</code> if the cell is empty or has a pickable object; <code>false</code>
   * otherwise.
   */
  @Pure
  public synchronized boolean canMoveInside(final int x, final int y) {
    return (((((x >= 0) && (y >= 0)) && (x < this.width)) && (y < this.height)) && (this.grid[x][y] == null));
  }
  
  /**
   * Create a body of the given type.
   * 
   * @param bodyType the type of the body.
   * @param agentId the identifier of the agent that will be linked to the body.
   * @param perceptionDistance the distance of perception.
   * @return the body.
   * @throws Exception if it is impossible to retrieve the body constructor or to create the instance.
   */
  public <T extends AgentBody> T createBody(final Class<T> bodyType, final UUID agentId, final int perceptionDistance) {
    try {
      int x = this.random.nextInt(this.width);
      int y = this.random.nextInt(this.height);
      while ((!this.canMoveInside(x, y))) {
        {
          x = this.random.nextInt(this.width);
          y = this.random.nextInt(this.height);
        }
      }
      UUID id = agentId;
      if ((id == null)) {
        id = UUID.randomUUID();
      }
      Constructor<T> cons = bodyType.getDeclaredConstructor(int.class, int.class, Maze.class, UUID.class, int.class);
      T body = cons.newInstance(Integer.valueOf(x), Integer.valueOf(y), this, id, Integer.valueOf(perceptionDistance));
      this.grid[x][y] = body;
      final T _converted_body = (T)body;
      this.bodies.put(id, _converted_body);
      return body;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Replies the number of bodies in the maze.
   * 
   * @return the number of bodies.
   */
  @Pure
  public int getBodyCount() {
    return this.bodies.size();
  }
  
  /**
   * Replies the object at the given position.
   * 
   * @param x
   * @param y
   * @return the object or <code>null</code>.
   */
  @Pure
  public CityObject getObjectAt(final int x, final int y) {
    if (((((x >= 0) && (x < this.width)) && (y >= 0)) && (y < this.height))) {
      return this.grid[x][y];
    }
    return new WallObject(x, y, this);
  }
  
  /**
   * Set the object at the given position.
   * 
   * @param x
   * @param y
   * @param obj the object to put at the position
   * @return the object in the cell before the change.
   */
  public CityObject setObjectAt(final int x, final int y, final CityObject obj) {
    if (((((x >= 0) && (x < this.width)) && (y >= 0)) && (y < this.height))) {
      CityObject old = this.grid[x][y];
      this.grid[x][y] = obj;
      if ((obj != null)) {
        obj.setPosition(x, y);
      }
      return old;
    }
    return null;
  }
  
  /**
   * Set the object at the given position.
   * 
   * @param position
   * @param obj the object to put at the position
   * @return the object in the cell before the change.
   */
  public CityObject setObjectAt(final Point2i position, final CityObject obj) {
    return this.setObjectAt(position.getX(), position.getY(), obj);
  }
  
  /**
   * Replies the agent bodies.
   * 
   * @return the agent bodies.
   */
  @Pure
  public Collection<AgentBody> getAgentBodies() {
    return Collections.<UUID, AgentBody>unmodifiableMap(this.bodies).values();
  }
  
  /**
   * Replies the agent body.
   * 
   * @param id
   * @return the body.
   */
  @Pure
  public AgentBody getAgentBody(final UUID id) {
    return this.bodies.get(id);
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
    Maze other = (Maze) obj;
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
