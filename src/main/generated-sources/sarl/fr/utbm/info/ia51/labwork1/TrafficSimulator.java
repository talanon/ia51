/**
 * $Id$
 * 
 * Copyright (c) 2014-15 Stephane GALLAND <stephane.galland@utbm.fr>.
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
package fr.utbm.info.ia51.labwork1;

import fr.utbm.info.ia51.labwork1.environment.agent.Environment;
import fr.utbm.info.ia51.labwork1.ui.CityGUI;
import io.janusproject.Boot;
import io.janusproject.util.LoggerCreator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.logging.Level;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Launcher of the simulation framework.
 * 
 * This launcher needs the {@link http://www.janusproject.io Janus platform}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.5")
@SarlElementType(8)
@SuppressWarnings("all")
public class TrafficSimulator {
  /**
   * Width of the world (in number of cells).
   */
  public final static int WIDTH = 25;
  
  /**
   * Height of the world (in number of cells).
   */
  public final static int HEIGHT = 25;
  
  /**
   * Number of drivers at the start-up.
   */
  public final static int NB_CARS = 5;
  
  /**
   * Percpetion distance for the agents.
   */
  public final static int PERCEPTION_DISTANCE = 5;
  
  /**
   * The UI will force the environment agent to wait for it.
   */
  public final static int WAITING_DURATION = 500;
  
  public static void main(final String[] args) {
    try {
      Boot.setOffline(true);
      Boot.setVerboseLevel(LoggerCreator.toInt(Level.INFO));
      Boot.showJanusLogo();
      CityGUI ui = new CityGUI(TrafficSimulator.WAITING_DURATION);
      Boot.startJanus(
        null, 
        Environment.class, 
        Integer.valueOf(TrafficSimulator.WIDTH), 
        Integer.valueOf(TrafficSimulator.HEIGHT), 
        Integer.valueOf(TrafficSimulator.NB_CARS), 
        Integer.valueOf(TrafficSimulator.PERCEPTION_DISTANCE), ui);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SyntheticMember
  public TrafficSimulator() {
    super();
  }
}
