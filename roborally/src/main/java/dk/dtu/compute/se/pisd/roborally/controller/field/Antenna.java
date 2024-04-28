package dk.dtu.compute.se.pisd.roborally.controller.field;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Antenna extends FieldAction {

    private Heading heading;

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {

        gameController.playersOrder = determinePriority(gameController, space);
        return true;
    }


    //TODO Find out how to get Antenna position
    /**
     * Determine the priority of the players based on the distance to the antenna
     * @param board The board to determine the priority on
     * @return The players sorted by priority
     * @Author Nikolaj Schæbel s220471
     */
    private Player[] determinePriority(GameController gameController, Space space) {
        Player[] players = gameController.board.getPlayers();
        return sortPlayers(players, space);
    }

    /**
     * Sort the players based on the distance to the antenna
     * @param players The players to sort
     * @return The sorted players
     * @Author Nikolaj Schæbel s220471
     */
    private Player[] sortPlayers(Player[] players, Space space) {
        //sort array by distance to antenna
        Arrays.sort(players, (Player p1, Player p2) -> {
            int distance1 = calculateDistance(p1, space);
            int distance2 = calculateDistance(p2, space);
            return Integer.compare(distance1, distance2);
        });
        return sortSameDistance(players, space);
    }

    /**
     * Sort the players that have the same distance to the antenna
     * @param players The players to sort
     * @return The sorted players
     * @Author Nikolaj Schæbel s220471
     */
    private Player[] sortSameDistance(Player[] players, Space space) {
        for (int i = 0; i < players.length; i++) {
            //find subset of players with same distance
            int subsetEndIndex = 0;
            for (int j = 1; j < players.length; j++) {
                //break if the distance is not the same
                if (calculateDistance(players[i], space) < calculateDistance(players[j], space)) {
                    break;
                }
                //set end index if the distance is the same
                if (calculateDistance(players[i]) == calculateDistance(players[j], space)) {
                    subsetEndIndex = j+1; //non-inclusive end index
                }
            }
            //If subsetEndIndex is greater than 0, multiple players have the same distance
            if (subsetEndIndex > 0) {
                //sort subset of players based on their position
                players = determineOrder(players, i, subsetEndIndex, space);
                //skip the subset of players for the following iterations
                i = subsetEndIndex-1;
            }
        }
        return players;
    }

    /**
     * Determine the order of the players based on their position
     * @param players The players to determine the order of
     * @param start The start index of the subset
     * @param end The end index of the subset
     * @return The players sorted by order
     * @Author Nikolaj Schæbel s220471
     */
    private Player[] determineOrder(Player[] players, int start, int end, Space space) {
        //Create an array of the subset of players with the same distance
        Player[] subset = Arrays.copyOfRange(players, start, end);
        //track the correct order of the subset
        int[] order = new int[subset.length];
        for (int i = 0; i < subset.length; i++) {
            for (int j = 0; j < subset.length; j++) {
                //if the player is on the same row as the antenna, they are first
                if(subset[i].getSpace().y == space.y) {
                    continue;
                }
                if(i != j) {
                    //if player j is in the same row as the antenna, increment the order of player i
                    if (subset[j].getSpace().y == space.y) {
                        order[i]++;
                    }
                    //if player i is above the antenna row, by index
                    else if(subset[i].getSpace().y > space.y) {
                        //if player j is above the antenna by index, and the player i.x < player j.x, increment the order of player i
                        if(subset[i].getSpace().y < subset[j].getSpace().y && subset[j].getSpace().y > space.y && subset[i].getSpace().x < subset[j].getSpace().x) {
                            order[i]++;
                        }
                    }
                    //if player i is below the antenna row, by index
                    else {
                        //if player j is above the antenna by index, or if player j is below the antenna by index and player i.x > player j.x, increment the order of player i
                        if(subset[j].getSpace().y > space.y || subset[j].getSpace().y > space.y && subset[i].getSpace().x > subset[j].getSpace().x) {
                            order[i]++;
                        }
                    }
                }
            }
        }
        //reorder the subset based on the order
        for (int i = start; i < end; i++) {
            for (int j = 0; j < subset.length; j++) {
                if (order[j] == i-start) {
                    players[i] = subset[j];
                    break;
                }
            }
        }
        return players;
    }

    /**
     * Calculate the distance between the antenna and a player
     * @param player The player to calculate the distance to
     * @return The distance between the antenna and the player
     * @Author Nikolaj Schæbel s220471
     */
    private int calculateDistance(Player player, Space space) {
        return Math.abs(player.getSpace().x - space.x) + Math.abs(player.getSpace().y - space.y);
    }
}
