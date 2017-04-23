package se.itu.game.cave.init;

import se.itu.game.cave.Thing;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>Utility class which keeps tracks of the Things in the game.
 * </p>
 * <p>
 * In order to check if e.g. the Bird is in the Player's inventory,
 * you could do <code>player.inventory().contains(Things.get("Bird"));</code>
 * </p>
 */
public class Things {

  private static Map<String, Thing> things = new HashMap<>();

  /* Package private - only e.g. CaveInitializer can add things */
  static void add(String name, Thing thing) {
    things.put(name, thing);
  }

  /**
   * Returns a reference to the given thing.
   * @param name - The string corresponding to the Thing's name
   * @return A reference to the thing corresponding to the given name, or null
   * if no such Thing exists.
   */
  public static Thing get(String name) {
    return things.get(name);
  }
  
}
