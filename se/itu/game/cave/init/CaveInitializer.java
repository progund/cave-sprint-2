package se.itu.game.cave.init;

import se.itu.game.cave.Room;
import se.itu.game.cave.Thing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Initializes the cave of rooms from the database. This class
   also contains some convenience methods for looking up rooms by ID.
 */
public class CaveInitializer {

  private static final String EVERYTHING_QUERY =
    "SELECT cave.roomid, north, south, east, west, line, linenr, thing " +
    "FROM cave LEFT JOIN lines " +
    "ON cave.roomid=lines.roomid "+
    "LEFT JOIN things ON cave.roomid=things.roomid;";
  private DbUtil database = DbUtil.getInstance();
  private Map<Integer, Room> cave;
  private static CaveInitializer instance;

  /**
   * Returns a reference to the only instance of this class.
   * @return A reference to the only instance of this class.
   */
  public static CaveInitializer getInstance() {
    if (instance == null) {
      instance = new CaveInitializer();
    }
    return instance;
  }
  
  private static class DbRoom{
    private int id;
    private int north;
    private int south;
    private int east;
    private int west;
    private String text;
    private Thing thing;

    public DbRoom(int id, int north, int south, int east, int west, String text, Thing thing) {
      this.id    = id;
      this.north = north;
      this.south = south;
      this.east  = east;
      this.west  = west;
      this.text  = text;
      this.thing = thing;
    }
    
    public int north() {
      return north;
    }
    
    public int south() {
      return south;
    }
    
    public int east() {
      return east;
    }
    
    public int west() {
      return west;
    }
    
    public String text() {
      return text;
    }
    
    public void setThing(Thing thing) {
      this.thing = thing;
    }
    
    public Thing thing() {
      return thing;
    }
    
    @Override
    public String toString() {
      return new StringBuilder("Room ID:")
        .append(id)
        .append(" - north: ")
        .append(north)
        .append(", south: ")
        .append(south)
        .append(", east: ")
        .append(east)
        .append(", west: ")
        .append(west)
        .append(" - ")
        .append(text)
        .append(" - With the thing: ")
        .append(thing)
        .toString();
    }
  }

  private CaveInitializer() {
    this.cave = new TreeMap<>();
  }

  /**
   * Returns a reference to the Starting Room.
   * @return A reference to the Starting Room
   */
  public Room getFirstRoom() {
    return cave.get(1);
  }

  /**
   * Returns a reference to a Room by its db id.
   * Will be useful in later versions of the game.
   * @return A reference to a Room by its db id
   */
  public Room getRoomById(int id) {
    return cave.get(id);
  }

  /**
   * Reads the rooms from the database and creates a cave
   * Using Room objects as a graph.
   */
  public void initAll() {
    Map<Integer, DbRoom> rooms = new TreeMap<>();
    int lastRoom = -1;
    int currentRoom;
    StringBuilder line = new StringBuilder();
    DbRoom room;
    try{
      ResultSet rs = database.query(EVERYTHING_QUERY);
      while (rs.next()) {
        currentRoom = rs.getInt("roomid");
        if(currentRoom == lastRoom) {
          line.append("\n").append(rs.getString("line"));
        } else {
          line = new StringBuilder(rs.getString("line"));
        }
        
        room = new DbRoom(currentRoom,
                          rs.getInt("north"),
                          rs.getInt("south"),
                          rs.getInt("east"),
                          rs.getInt("west"),
                          line.toString(),
                          rs.getString("thing") == null
                          ? null
                          : new Thing(rs.getString("thing")));
        lastRoom = currentRoom;
        rooms.put(currentRoom, room);
      }
    } catch (SQLException sqle) {
      System.err.println("Couldn't set up cave: " + sqle.getMessage());
    }
    buildCave(rooms);
  }
  
  /*
   * Strategy: create a map Integer,Room with the rooms
   * from the map Integer,DbRoom but without any exits.
   *
   * Next, loop through the rooms map again, and check
   * the exits. All Rooms now exist in the cave map.
   * So we can set the exits of the Rooms in cave like this:
   * currentRoom.setDirection(Room.NORTH, cave.get(currentDBRoom.north()));
   * 
   * It can be done in several steps of course, but the idea is
   * that we iterate over all the IDs in the map of DbRoom
   * references, and use the fact that we have the cave map
   * which is a map of actual Room references, in parallell.
   *
   * The DbRoom keeps its exits as int IDs, which we can use
   * to get hold of the actual Room with the same ID.
   * With this information we can actually set the exits
   * of the actual Room to a reference to the correct
   * actual Rooms.
   * 
   * If the rooms map of DbRoom references look like this:
{1=Room ID:1 - north: 5, south: 4, east: 3, west: 2 You are standing at the end of a road. Thing: null,
 2=Room ID:2 - north: 0, south: 5, east: 1, west: 0 - You have walked up a hill. Thing: null,
 3=Room ID:3 - north: 6, south: 6, east: 6, west: 1 - You are inside a building. Thing: Skeleton Key
 .....}
 
 * Then we can set the cave's Room with id 1 to have the Room in
 * the cave with id 5 as the North room, and so on.
 * We translate the DbRoom's ID to an actual Room.
   */
  private void buildCave(Map<Integer, DbRoom> rooms) {
    DbRoom currentDbRoom;
    DbRoom room;
    Room currentRoom;
    List<Thing> things;
    // Create the cave with rooms which have no exits
    for (Integer roomId : rooms.keySet()) {
      currentDbRoom = rooms.get(roomId);
      // An actual Room has a List of Things
      things = new ArrayList<>();
      if (currentDbRoom.thing() != null) {
        // Store this thing in the Things class
        Things.add(currentDbRoom.thing().name(), currentDbRoom.thing());
        // Store this thing in this Room's things list
        things.add(currentDbRoom.thing());
      }
      currentRoom = new Room(currentDbRoom.text(),
                             null, null, null, null,                             
                             things);
      cave.put(roomId, currentRoom);
    }
    // Add exits to the rooms
    for (Integer roomID : rooms.keySet()){
      Room thisRoom = cave.get(roomID);
      room = rooms.get(roomID);
      Room northRoom = cave.get(room.north());
      if (northRoom != null) {
        thisRoom.setConnectingRoom(Room.Direction.NORTH, northRoom);
      }
      Room southRoom = cave.get(room.south());
      if (southRoom != null) {
        thisRoom.setConnectingRoom(Room.Direction.SOUTH, southRoom);
      }
      Room eastRoom = cave.get(room.east());
      if (eastRoom != null) {
        thisRoom.setConnectingRoom(Room.Direction.EAST, eastRoom);
      }
      Room westRoom = cave.get(room.west());
      if (westRoom != null) {
        thisRoom.setConnectingRoom(Room.Direction.WEST, westRoom);
      }    
    }
  }
  
}
