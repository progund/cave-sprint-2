#!/bin/bash

FLAGS=" "
if [[ $1 = "-d" ]]
then
    FLAGS="-Ddodebug=true"
fi

javac se/itu/game/main/MainGui.java && java $FLAGS -cp "sqlite-jdbc-3.16.1.jar:." se.itu.game.main.MainGui
