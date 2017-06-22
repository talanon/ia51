package fr.utbm.info.ia51.labwork1.environment.agent;

import fr.utbm.info.ia51.labwork1.environment.agent.EnvironmentEvent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import java.util.EventListener;

/**
 * Space that is representing the Jaak environment.
 */
@SarlSpecification("0.5")
@SarlElementType(9)
@SuppressWarnings("all")
public interface EnvironmentListener extends EventListener {
  /**
   * Invoked when the environment has changed.
   * 
   * @param event
   */
  public abstract void environmentChanged(final EnvironmentEvent event);
  
  /**
   * Invoked when the game is over.
   */
  public abstract void gameOver();
}
