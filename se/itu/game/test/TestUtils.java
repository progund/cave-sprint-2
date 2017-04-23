package se.itu.game.test;

import se.itu.game.cave.Room;
import static se.itu.game.cave.Room.Direction.NORTH;
import static se.itu.game.cave.Room.Direction.EAST;
import static se.itu.game.cave.Room.Direction.SOUTH;
import static se.itu.game.cave.Room.Direction.WEST;
import se.itu.game.cave.Player;
import se.itu.game.cave.Thing;

import java.util.List;
import java.util.ArrayList;

public class TestUtils {

  public static final String START_ROOM_DESCR = "You are standing at the end of";
  public static final String EAST_ROOM_DESCR = "You are inside a building";
  public static final String WEST_ROOM_DESCR = "You have walked up a hill";
  public static final String NORTH_ROOM_DESCR = "You are in open forest";
  public static final String SOUTH_4_ROOM_DESCR = "You are in a small chamber";
  public static final String CAGE_ROOM_DESC = "You are crawling over cobbles";

  private static Room createRoom(String description,
                                 Room north,        
                                 Room east,
                                 Room south,        
                                 Room west) {
    List<Thing> things = new ArrayList<Thing>();
    return new Room (description,north,east,south,west, things);
  }

  public static Room getTest1Cave()  {

    Room startingRoom = createRoom("You are standing at the end of .....",
                                   null, null, null, null);
        
    Room westRoom = createRoom("You have walked up a hill ...",
                               null, null, null, null);
        
    Room northRoom = createRoom("You are in open forest ....",
                                null, null, null, null);
        
    Room southRoom = createRoom("You are in a valley ....",
                                null, null, null, null);
        
    List<Thing> things = new ArrayList<Thing>();
    things.add(new Thing("Skeleton key"));
    Room eastRoom = new Room("You are inside a building, .....",
                             null, null, null, null, things);
        
    startingRoom.setConnectingRoom(EAST, eastRoom);
    startingRoom.setConnectingRoom(WEST, westRoom);
    startingRoom.setConnectingRoom(NORTH,northRoom);
    startingRoom.setConnectingRoom(SOUTH, southRoom);
        
    eastRoom.setConnectingRoom(WEST, startingRoom);
    westRoom.setConnectingRoom(EAST, startingRoom);
    southRoom.setConnectingRoom(NORTH, startingRoom);
    northRoom.setConnectingRoom(SOUTH, startingRoom);
        
    return startingRoom;
  }
  
}
