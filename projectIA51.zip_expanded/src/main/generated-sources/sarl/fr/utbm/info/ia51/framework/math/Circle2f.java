/**
 * $Id$
 * 
 * Copyright (c) 2011-17 Stephane GALLAND <stephane.galland@utbm.fr>.
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
package fr.utbm.info.ia51.framework.math;

import com.google.common.base.Objects;
import fr.utbm.info.ia51.framework.math.MotionHull2f;
import fr.utbm.info.ia51.framework.math.Point2f;
import fr.utbm.info.ia51.framework.math.Rectangle2f;
import fr.utbm.info.ia51.framework.math.Shape2f;
import fr.utbm.info.ia51.framework.math.Tuple2f;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * 2D Rectangle with floating-point values.
 * 
 * <p>This code is coped from <a href="http://www.arakhne.org/afc">Arakhne Foundation Classes</a>.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class Circle2f extends Shape2f<Circle2f> {
  private final Point2f center = new Point2f();
  
  private float radius;
  
  public Circle2f() {
  }
  
  /**
   * @param center
   * @param radius
   */
  public Circle2f(final Point2f center, final float radius) {
    this.center.set(center);
    this.radius = radius;
  }
  
  /**
   * @param x
   * @param y
   * @param radius
   */
  public Circle2f(final float x, final float y, final float radius) {
    this.center.set(x, y);
    this.radius = radius;
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if ((obj instanceof Circle2f)) {
      return (Objects.equal(this.center, ((Circle2f)obj).center) && (this.radius == ((Circle2f)obj).radius));
    }
    return false;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return Objects.hashCode(this.center, Float.valueOf(this.radius));
  }
  
  /**
   * Replies a copy of the center point of this circle.
   * 
   * @return the center point.
   */
  @Pure
  public Point2f getCenter() {
    return this.center.clone();
  }
  
  /**
   * Replies the radius of the circle.
   * 
   * @return the radius.
   */
  @Pure
  public float getRadius() {
    return this.radius;
  }
  
  @Override
  @Pure
  public String toString() {
    String _string = this.center.toString();
    String _plus = ("[" + _string);
    String _plus_1 = (_plus + "-");
    String _plus_2 = (_plus_1 + Float.valueOf(this.radius));
    return (_plus_2 + "]");
  }
  
  /**
   * Replies if an intersection exists between this rectangle and the given shape.
   * 
   * @param s - the shape.
   * @return <code>true</code> if an intersection exists.
   */
  public boolean intersects(final Shape2f<?> s) {
    if ((s instanceof Circle2f)) {
      float x = (((Circle2f)s).center.x - this.center.x);
      float y = (((Circle2f)s).center.y - this.center.y);
      return (((x * x) + (y * y)) < ((this.radius + ((Circle2f)s).radius) * (this.radius + ((Circle2f)s).radius)));
    }
    if ((s instanceof Rectangle2f)) {
      return ((Rectangle2f)s).intersects(this);
    }
    if ((s instanceof MotionHull2f)) {
      return ((MotionHull2f)s).intersects(this);
    }
    throw new IllegalArgumentException();
  }
  
  @Override
  public Circle2f translate(final Tuple2f<?> vector) {
    return new Circle2f(
      (this.center.x + vector.x), 
      (this.center.y + vector.y), 
      this.radius);
  }
  
  @Override
  @Pure
  public Rectangle2f getBounds() {
    return new Rectangle2f(
      (this.center.x - this.radius), 
      (this.center.y - this.radius), 
      (this.center.x + this.radius), 
      (this.center.y + this.radius));
  }
  
  @Override
  @Pure
  public float getMaxDemiSize() {
    return this.radius;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public Circle2f clone() {
    try {
      return (Circle2f) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2591779525L;
}
