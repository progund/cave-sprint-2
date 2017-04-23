package se.itu.game.gui;

import static se.itu.game.cave.Room.Direction;

import se.itu.game.cave.Player;
import se.itu.game.cave.Room;
import se.itu.game.cave.Thing;
import se.itu.game.cave.init.CaveInitializer;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;

public class MainFrame {
  private JFrame mainFrame;
  private JButton northButton;
  private JButton southButton;
  private JButton eastButton;
  private JButton westButton;
  private JList<Thing> inventory;
  private JList<Thing> roomThings;
  private JTextArea roomInfo;
  private JPanel top;
  private JPanel navigationPanel;
  private JPanel listPanel;
  private JPanel centerPanel;
  private CaveInitializer caveInit;
  private Player player;
  private DefaultListModel<Thing> inventoryModel;
  private DefaultListModel<Thing> roomThingsModel;
  private ListCellRenderer<Thing> renderer;
  private JLabel messages;
  private JPanel inventoryPanel;
  private JPanel thingsPanel;
  private JLabel inventoryLabel;
  private JLabel thingsLabel;
  private Map<Room.Direction, JButton> buttonMap;
  
  private boolean debug;
  
  private class ThingRenderer<Thing> implements ListCellRenderer<Thing> {
    
    protected DefaultListCellRenderer defaultLCR = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList<? extends Thing> list,
                                                  Thing thing,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
      defaultLCR.setText(thing.toString());
      if (isSelected) {
        defaultLCR.setBackground(list.getSelectionBackground());
        defaultLCR.setForeground(list.getSelectionForeground());
      } else {
        defaultLCR.setBackground(list.getBackground());
        defaultLCR.setForeground(list.getForeground());
      }
      return defaultLCR;
    }
  }
  
  private void debug(Object msg) {
    if (msg != null && debug) {
      System.out.println("DEBUG: " + msg);
    }
  }
  
  private void initComponents() {
    /* Uses the -Ddodebug=true flag */
    debug = System.getProperty("dodebug", "false").equals("false") ? false : true;
    caveInit = CaveInitializer.getInstance();
    caveInit.initAll();
    player = Player.getInstance();
    mainFrame    = new JFrame("Cave game");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    northButton = new JButton("NORTH");
    southButton = new JButton("SOUTH");
    eastButton  = new JButton("EAST");
    westButton  = new JButton("WEST");
    /* A map with directions as keys and the nav buttons as values
     * used for looping through the buttons and enable/disable them
     */
    buttonMap = new HashMap<>();
    buttonMap.put(Direction.NORTH, northButton);
    buttonMap.put(Direction.SOUTH, southButton);
    buttonMap.put(Direction.EAST, eastButton);
    buttonMap.put(Direction.WEST, westButton);
    
    roomInfo    = new JTextArea(20,60);
    messages    = new JLabel();
    top         = new JPanel(new FlowLayout(FlowLayout.LEADING));
    navigationPanel = new JPanel();
    centerPanel = new JPanel();
    thingsPanel = new JPanel();
    inventoryPanel = new JPanel();
    thingsLabel = new JLabel("Things in the room");
    inventoryLabel = new JLabel("Inventory");    
    renderer = new ThingRenderer<Thing>();
    inventoryModel = new DefaultListModel<Thing>();
    roomThingsModel = new DefaultListModel<Thing>();
    updateGui();
    listPanel = new JPanel();
    inventory = new JList<>(inventoryModel);
    inventory.setCellRenderer(renderer);
    inventory.setPrototypeCellValue(new Thing("Pirate ChestXXXXXXXX"));
    roomThings = new JList<>(roomThingsModel);
    roomThings.setCellRenderer(renderer);
    roomThings.setPrototypeCellValue(new Thing("Pirate ChestXXXXXXXX"));
  }

  
  private void updateButtons() {
    
    Room currentRoom = player.currentRoom();
    for (Direction dir : Direction.values()) {
      buttonMap.get(dir).setEnabled(currentRoom.getRoom(dir) != null);
    }
    /*
      // another way to do the same thing, requires much more code:
    if (currentRoom.getRoom(Direction.NORTH) == null) {
      northButton.setEnabled(false);
      debug("Disabling north");
    } else {
      northButton.setEnabled(true);      
    }
    // Etc for all the buttons...
    */
  }
  
  private void updateModels() {
    debug("Updating models");
    // First clear the models for the two lists
    inventoryModel.clear();
    roomThingsModel.clear();
    // Here's a good place to loop through
    // the Room's things and add them to
    // the roomThingsModel
    // for (Thing thing : player.currentRoom().things()) {
    //   ...
    // }

    // The method for adding an element to a model
    // is model.addElement(someElement);
    
    // Next, loop through the player's things
    // and add them to the inventoryModel
    //for (Thing thing : player.inventory()) {
    //  ...
    //}
    
    // Remove the following statement when you're done:
    messages
      .setText(player.currentRoom().things().size() != 0 ? "There are things here!" +
               player.currentRoom().things() : "No things");
  }
  
  private void layoutComponents() {
    navigationPanel.setLayout(new GridLayout(3,3));
    navigationPanel.add(new JPanel());
    navigationPanel.add(northButton);
    navigationPanel.add(new JPanel());
    navigationPanel.add(westButton);
    navigationPanel.add(new JPanel());
    navigationPanel.add(eastButton);
    navigationPanel.add(new JPanel());
    navigationPanel.add(southButton);
    navigationPanel.add(new JPanel());    
    top.add(navigationPanel);
    JScrollPane inventoryScroll = new JScrollPane(inventory);
    JScrollPane roomThingsScroll = new JScrollPane(roomThings);
    inventoryPanel.setLayout(new BorderLayout());
    thingsPanel.setLayout(new BorderLayout());
    inventoryPanel.add(inventoryLabel, BorderLayout.NORTH);
    inventoryPanel.add(inventoryScroll, BorderLayout.CENTER);
    thingsPanel.add(thingsLabel, BorderLayout.NORTH);
    thingsPanel.add(roomThingsScroll, BorderLayout.CENTER);
    listPanel.add(inventoryPanel);
    listPanel.add(thingsPanel);
    top.add(listPanel);
    mainFrame.add(top, BorderLayout.NORTH);
    mainFrame.add(roomInfo, BorderLayout.CENTER);
    mainFrame.add(messages, BorderLayout.SOUTH);
  }

  private void updateGui() {
    updateModels();
    updateButtons();
    roomInfo.setText(player.currentRoom().description());          
  }

  private void addListeners() {
    Room currentRoom = player.currentRoom();
    for (Direction dir : Direction.values()) {
      buttonMap.get(dir).addActionListener( (event) -> {
          try {
            player.go(dir);
            debug(dir + " button pressed");
            updateGui();
          } catch (RuntimeException e) {
            messages.setText("Bad direction - shouldn't happen.");
          }
        });
    }
    /*
    // Alternatively, you could add listeners to
    // each of the buttons like this:
    northButton.addActionListener( (event) -> {
        try {
          player.go(Direction.NORTH);
          debug(player.currentRoom());
          updateGui();
        } catch (RuntimeException e) {
          messages.setText("Bad direction - shouldn't happen");
        }
      });
    // etc for each button...
    */
    RoomThingsListener roomThingsListener = new RoomThingsListener();
    InventoryListener inventoryListener   = new InventoryListener();
    inventory.addMouseListener(inventoryListener);
    roomThings.addMouseListener(roomThingsListener);    
  }
  
  /* Run this method from main() when you want
   * to setup and show this window.
   */
  public void run() {
    initComponents();
    layoutComponents();
    addListeners();
    mainFrame.pack();
    mainFrame.setVisible(true);
  }

  static {
    try {
      // Ignore this - it's a fix for Rikard's computer. Hell Dell!
      UIManager.setLookAndFeel((LookAndFeel)Class
                               .forName("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
                               .newInstance());
    } catch (Exception ignore) {}
  }

  private class RoomThingsListener extends MouseAdapter {
    @SuppressWarnings("unchecked")
    public void mouseClicked(MouseEvent event) {
      if (event.getClickCount() == 2) {
        Thing thing = ((JList<Thing>)event.getSource()).getSelectedValue();
        debug("Click on the room's " + thing);
        try {
          // Make the player take the thing!
          // HERE...
          updateModels();
        } catch (Exception ite) {
          messages.setText("Couldn't take " + thing + ": " + ite.getMessage());
        }
      }
    }
  }
  
  private class InventoryListener extends MouseAdapter {
    @SuppressWarnings("unchecked")    
    public void mouseClicked(MouseEvent event) {
      if (event.getClickCount() == 2) {
        Thing thing = ((JList<Thing>)event.getSource()).getSelectedValue();
        debug("Click on the inventory's " + thing);
        // Make the player drop the thing!
        // HERE...
        updateModels();
      }
    }
  }
  
}
