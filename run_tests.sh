#!/bin/bash

javac se/itu/game/test/ThingTests.java && java -cp .:sqlite-jdbc-3.16.1.jar -ea se.itu.game.test.ThingTests
javac se/itu/game/test/ThingRuleTest.java && java -cp .:sqlite-jdbc-3.16.1.jar -ea se.itu.game.test.ThingRuleTest
javac se/itu/game/test/TestPlayer.java && java -cp .:sqlite-jdbc-3.16.1.jar -ea se.itu.game.test.TestPlayer
javac se/itu/game/test/TestRoom.java && java -cp .:sqlite-jdbc-3.16.1.jar -ea se.itu.game.test.TestRoom
javac se/itu/game/test/InitTest.java && java -cp .:sqlite-jdbc-3.16.1.jar -ea se.itu.game.test.InitTest
javac se/itu/game/test/NavigationTests.java && java -cp .:sqlite-jdbc-3.16.1.jar -ea se.itu.game.test.NavigationTests
