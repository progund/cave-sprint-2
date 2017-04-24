# cave-sprint-2
The source code and design documents for the Cave game according to Sprint 2

# Proposed workflow for implementing the new design changes
This is the proposed work flow (work in progress, check here often as this will be added to continously).
## Start with the new responsibilities for Player - then change the GUI
Add the following methods to the Player class:

```
+describeCurrentRoom() : String
+thingsInCurrentRoom() : Thing [*]
+canSeeDoorIn(direction : Room.Direction) : boolean
```

It is pretty straight forward what they should do. Remember that the rationale for these changes,
is that the GUI/UI shouldn't have to deal with the Player's current Room, but rather directly with the Player.

In each of the methods, just query the ```currentRoom``` variable for the thing to return.

When the Player compiles, implement these changes in the GUI. What places? The place in the GUI
where before, you used Player.getInstance().currentRoom() .

These are the places to look for:
Bash```
$ grep -n 'currentRoom()' se/itu/game/gui/MainFrame.java
118:    Room currentRoom = player.currentRoom();
140:    for (Thing thing : player.currentRoom().things()) {
180:    roomInfo.setText(player.currentRoom().description());
184:    Room currentRoom = player.currentRoom();
```

## Add the new Exception classes
Implement the classes ```se.itu.game.cave.IllegalMoveException``` and ```game.itu.game.cave.RuleViolationException``` .

It's pretty straight forward - Let them extend Exception and add a constructor accepting a String message and in the constructor body,
simply call ```super(message);``` . See the lecture slides for the code examples.

## Change the Player's instance method takeThing(Thing thing)
Now, change the Player's instance method takeThing(Thing thing) so that it throws IllegalMoveException in its declaration. Also reflect this
in the javadoc for the method.

When Player compiles, reflect this change in the GUI. You need to add try-catch(IllegalMoveException e) in the places where you call "player.go(direction)".

bash```
$ grep -n 'go(' se/itu/game/gui/MainFrame.java
188:            player.go(dir);
```
## Implement the ThingRule interface
See the UML and the lecture slides for hints. The interface should declare one public abstract method:

```
/+apply() : boolean {exceptions=RuleViolationException}/
```

The UML syntax above means abstract and that it throws RuleViolationException

## Implement the RuleBook class
Write the class ```se.itu.game.cave.RuleBook``` which should have the following variables and methods:

```
_-rules: Map<Thing, ThingRule>_
--
_+addThingRule(thing : Thing, rule : ThingRule) : void_
_+getRuleFor(thing : Thing) : ThingRule_
```

That is, a private static Map<Thing, ThingRule> (you need to import java.util.Map and java.util.HashMap), and a public
static void addThingRule method which accepts a Thing and a ThingRule and adds them to the map using the method put(thing, rule), and finally a public static getRuleFor method which accepts a Thing and returns it using the method get(thing) on the map. See lecture slides for hints.

Don't worry about the adding of ThingRules to the map using the addThingRule method. It is done from the new version of CaveInitializer.java in this repository. And, yeah, now that you have both a RuleBook and the ThingRule interface, you should be able to compile both the CaveInitializer.java and the test class ThingRuleTest.java !

## Implement the new version of Player's takeThing so that it gets the ThingRule and applies it
Next, change the Player's instance method ```takeThing(Thing)``` so that it uses an if-statement which gets the corresponding ThingRule from the RuleBook and calls ```apply()``` inside the if-test.

In the if-block, do as before. See the lecture slides for hints.

To be continued and/or updated. Stay tuned!
