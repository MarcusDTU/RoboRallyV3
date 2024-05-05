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
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.field.Antenna;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {

    @Expose
    public int width;

    @Expose
    private int MAX_NUMBER_OF_CARDS;

    @Expose
    private int currentNumberOfCards;

    @Expose
    private int totalMoves = 0;

    @Expose
    public int height;

    @Expose
    private Integer gameId;

    @Expose
    private Space[][] spaces;

    @Expose
    private final List<Player> players = new ArrayList<>();

    @Expose
    private Player current;

    @Expose
    private Phase phase = INITIALISATION;

    @Expose
    private Space antennaSpace;

    @Expose
    private final List<Space> startSpaces = new ArrayList<>();

    @Expose
    private int step = 0;

    @Expose
    private boolean stepMode;

    // Empty constructor for GSON since it will give errors without it
    public Board() {}

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        this.stepMode = false;
        this.MAX_NUMBER_OF_CARDS =  getPlayersNumber() * 9;
        this.currentNumberOfCards = MAX_NUMBER_OF_CARDS;
    }


    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    public List<Space> getStartSpaces() {
        return startSpaces;
    }

    public void addStartSpace(Space space) {
        if (!startSpaces.contains(space)) {
            startSpaces.add(space);
        }
    }

    /**
     * Returns the space which holds the antenna.
     * @return the space which holds the antenna
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     * @author Nikolaj Schæbel, s220471@dtu.dk
     */
    public Space getAntennaSpace() {
        return antennaSpace;
    }

    /**
     * Sets the space which holds the antenna.
     * @param antennaSpace the space which holds the antenna
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     * @author Nikolaj Schæbel, s220471@dtu.dk
     */
    public void setAntennaSpace(Space antennaSpace) {
        this.antennaSpace = antennaSpace;
    }

    public int getCurrentNumberOfCards() {
        return currentNumberOfCards;
    }
    public void useCard() {
        currentNumberOfCards--;
    }


    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }

    public Player getCurrentPlayer() {
        return current;
    }

    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    public boolean isStepMode() {
        return stepMode;
    }

    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    public void setPlayers(List<Player> players) {
        this.players.clear();
        this.players.addAll(players);
        notifyChange();
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        if (space.getWalls().contains(heading)) {
            return null;
        }
        // TODO needs to be implemented based on the actual spaces
        //      and obstacles and walls placed there. For now it,
        //      just calculates the next space in the respective
        //      direction in a cyclic way.

        // XXX another option (not for now) would be that null represents a hole
        //     or the edge of the board in which the players can fall

        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }
        Heading reverse = Heading.values()[(heading.ordinal() + 2)% Heading.values().length];
        Space result = getSpace(x, y);
        if (result != null) {
            if (result.getWalls().contains(reverse)) {
                return null;
            }
        }
        return result;
    }

    public String getStatusMessage() {
        return "Phase: " + getPhase().name() +
                ", Player = " + getCurrentPlayer().getName() +
                ", Step: " + getStep() +
                ", Total Moves:" + getTotalMoves();
    }
    public int getTotalMoves() {
        return totalMoves;
    }

    public void setTotalMoves(int totalMoves) {
        this.totalMoves = totalMoves;
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[0]);
    }

    public Space[][] getSpaces() {
        return spaces;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
