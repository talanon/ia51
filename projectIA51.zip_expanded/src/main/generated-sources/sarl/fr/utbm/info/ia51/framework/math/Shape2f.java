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

import fr.utbm.info.ia51.framework.math.Rectangle2f;
import fr.utbm.info.ia51.framework.math.Tuple2f;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.io.Serializable;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Abstract implementation of a shape.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public abstract class Shape2f<S extends Shape2f<S>> implements Serializable, Cloneable {
  public Shape2f() {
  }
  
  @Override
  @Pure
  public S clone() {
    try {
      Object _clone = super.clone();
      return ((S) _clone);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Replies if an intersection exists between this rectangle and the given rectangle.
   * 
   * @param r - the rectangle.
   * @return <code>true</code> if an intersection exists.
   */
  public abstract boolean intersects(final Shape2f<?> r);
  
  /**
   * Create a clone of this shape with a translation.
   * 
   * @param vector the translation.
   * @return the clone.
   */
  public abstract S translate(final Tuple2f<?> vector);
  
  /**
   * Replies the bounds for this shape.
   * 
   * @return the bounds of the shape.
   */
  public abstract Rectangle2f getBounds();
  
  /**
   * Replies the biggest demi-size of the shape.
   * 
   * @return the biggest demi-size of the shape.
   */
  public abstract float getMaxDemiSize();
  
  @SyntheticMember
  private final static long serialVersionUID = -1583342247L;
}
