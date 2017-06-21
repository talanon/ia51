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
import fr.utbm.info.ia51.framework.math.Circle2f;
import fr.utbm.info.ia51.framework.math.MathUtil;
import fr.utbm.info.ia51.framework.math.MotionHull2f;
import fr.utbm.info.ia51.framework.math.Point2f;
import fr.utbm.info.ia51.framework.math.Shape2f;
import fr.utbm.info.ia51.framework.math.Tuple2f;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * 2D Rectangle with floating-point values.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class Rectangle2f extends Shape2f<Rectangle2f> {
  private final Point2f lower = new Point2f();
  
  private final Point2f upper = new Point2f();
  
  public Rectangle2f() {
  }
  
  /**
   * @param p1
   * @param p2
   */
  public Rectangle2f(final Point2f p1, final Point2f p2) {
    this.lower.set(
      Math.min(p1.x, p2.x), 
      Math.min(p1.y, p2.y));
    this.upper.set(
      Math.max(p1.x, p2.x), 
      Math.max(p1.y, p2.y));
  }
  
  /**
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   */
  public Rectangle2f(final float x1, final float y1, final float x2, final float y2) {
    this.lower.set(
      Math.min(x1, x2), 
      Math.min(y1, y2));
    this.upper.set(
      Math.max(x1, x2), 
      Math.max(y1, y2));
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if ((obj instanceof Rectangle2f)) {
      return (Objects.equal(this.lower, ((Rectangle2f)obj).lower) && Objects.equal(this.upper, ((Rectangle2f)obj).upper));
    }
    return false;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return Objects.hashCode(this.lower, this.upper);
  }
  
  /**
   * Replies a copy of the lower point of this rectangle.
   * 
   * @return the lower point.
   */
  @Pure
  public Point2f getLower() {
    return this.lower.clone();
  }
  
  /**
   * Replies a copy of the upper point of this rectangle.
   * 
   * @return the upper point.
   */
  @Pure
  public Point2f getUpper() {
    return this.upper.clone();
  }
  
  @Pure
  public String toString() {
    String _string = this.lower.toString();
    String _plus = ("[" + _string);
    String _plus_1 = (_plus + "-");
    String _string_1 = this.upper.toString();
    String _plus_2 = (_plus_1 + _string_1);
    return (_plus_2 + "]");
  }
  
  /**
   * Replies if an intersection exists between this rectangle and the given shape.
   * 
   * @param s - the shape.
   * @return <code>true</code> if an intersection exists.
   */
  public boolean intersects(final Shape2f<?> s) {
    if ((s instanceof Rectangle2f)) {
      return (this.intersects(this.lower.x, this.upper.x, ((Rectangle2f)s).lower.x, ((Rectangle2f)s).upper.x) && this.intersects(this.lower.y, this.upper.y, ((Rectangle2f)s).lower.y, ((Rectangle2f)s).upper.y));
    }
    if ((s instanceof Circle2f)) {
      Point2f center = ((Circle2f)s).getCenter();
      float x = MathUtil.clamp(center.x, this.lower.x, this.upper.x);
      float y = MathUtil.clamp(center.y, this.lower.y, this.upper.y);
      float _x = x;
      x = (_x - center.x);
      float _y = y;
      y = (_y - center.y);
      float radius = ((Circle2f)s).getRadius();
      return (((x * x) + (y * y)) < (radius * radius));
    }
    if ((s instanceof MotionHull2f)) {
      return ((MotionHull2f)s).intersects(this);
    }
    throw new IllegalArgumentException();
  }
  
  private boolean intersects(final float a1, final float a2, final float b1, final float b2) {
    return ((a2 > b1) && (b2 > a1));
  }
  
  /**
   * Replies the center point of the rectangle.
   * 
   * @return the center point.
   */
  @Pure
  public Point2f getCenter() {
    return new Point2f(
      ((this.lower.x + this.upper.x) / 2f), 
      ((this.lower.y + this.upper.y) / 2f));
  }
  
  @Override
  public Rectangle2f translate(final Tuple2f<?> vector) {
    return new Rectangle2f(
      (this.lower.x + vector.x), 
      (this.lower.y + vector.y), 
      (this.upper.x + vector.x), 
      (this.upper.y + vector.y));
  }
  
  @Override
  @Pure
  public Rectangle2f getBounds() {
    return this.clone();
  }
  
  /**
   * Replies the width of the rectangle.
   * 
   * @return the width.
   */
  @Pure
  public float getWidth() {
    return (this.upper.x - this.lower.x);
  }
  
  /**
   * Replies the height of the rectangle.
   * 
   * @return the height.
   */
  @Pure
  public float getHeight() {
    return (this.upper.y - this.lower.y);
  }
  
  @Override
  @Pure
  public float getMaxDemiSize() {
    float _max = Math.max(this.getWidth(), this.getHeight());
    return (_max / 2f);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public Rectangle2f clone() {
    try {
      return (Rectangle2f) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 7202589241L;
}
