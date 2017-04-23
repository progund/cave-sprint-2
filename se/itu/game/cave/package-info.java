/**
 * <p>
 * Provides the classes necessary to implement the version 1
 * of the Cave game.
 * </p>
 * <p>
 * In this version, we have:
 * <ul>
 * <li>{@link se.itu.game.cave.Thing} - A class representing a Thing in a Room or in the Player's inventory</li>
 * <li>{@link se.itu.game.cave.Room} - A class representing a Room with a List of Things (if any)
 * and a maximum of four exits to other Room's</li>
 * <li>{@link se.itu.game.cave.Player} - The player of the Game</li>
 * </ul>
 * </p>
 * <p>
 * The Player can pick up or drop a Thing in its current Room, give us its inventory
 * (List of Thing references) and go in a {@link se.itu.game.cave.Room.Direction Direction}.
 * </p>
 * <p>
 * A {@link se.itu.game.cave.Room} has a maximum of four exits to other Rooms in the directions
 * {@link se.itu.game.cave.Room.Direction#NORTH North}, 
 * {@link se.itu.game.cave.Room.Direction#SOUTH South}, 
 * {@link se.itu.game.cave.Room.Direction#EAST East}, and, 
 * {@link se.itu.game.cave.Room.Direction#WEST West}, and it can have zero or more Things which can be
 * taken by the {@link se.itu.game.cave.Player} or added to the Room by the Player.
 * </p>
 * <p>
 * There are no rules implemented for taking Things in this version.
 * </p>
 */
package se.itu.game.cave;
