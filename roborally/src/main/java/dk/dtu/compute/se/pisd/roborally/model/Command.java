/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.model;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the different commands that can be issued to a robot in the game.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author Daniel Overballe Lerche, s235095@dtu.dk (javadoc only)
 */
public enum Command {

    // This is a very simplistic way of realizing different commands.

    FORWARD("Fwd"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Fast Fwd"),
    AGAIN("Again"),
    POWER_UP("Power Up"),
    FAST_FAST_FORWARD("Fast Fast Fwd"),
    U_TURN("U-Turn"),
    BACK_UP("Back Up"),
    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT);

    @Expose
    final public String displayName;

    final private List<Command> options;

    /**
     * The constructor for the Command enum.
     * @param displayName the name that showed in the GUI
     * @param options the options of the command (if any)
     * @author Daniel Overballe Lerche, s235095@dtu.dk (javadoc only)
     */
    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    /**
     * Returns whether this command is interactive, i.e., whether it has options.
     * @return true if the command is interactive, false otherwise
     * @author Daniel Overballe Lerche, s235095@dtu.dk (javadoc only)
     */
    public boolean isInteractive() {
        return !options.isEmpty();
    }

    /**
     * Returns the options of the command. If the command is not interactive, an empty list is returned.
     * @return the options of the command
     * @author Daniel Overballe Lerche, s235095@dtu.dk (javadoc only)
     */
    public List<Command> getOptions() {
        return options;
    }

}
