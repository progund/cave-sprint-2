#!/bin/bash

declare -a DIRS=(
    se/itu/game/main/
    se/itu/game/gui/
    se/itu/game/cave/
    se/itu/game/cave/init/
    se/itu/game/test/
)

for DIR in ${DIRS[@]}
do
    javac ${DIR}*.java
done
