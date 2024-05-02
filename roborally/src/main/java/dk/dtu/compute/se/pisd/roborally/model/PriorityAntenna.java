package dk.dtu.compute.se.pisd.roborally.model;
import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.LinkedList;

public class PriorityAntenna extends BoardElement{

    @Expose
    int x;
    @Expose
    int y;

        public PriorityAntenna(Orientation orientation, int x, int y) {
            super(orientation);
            this.x = x;
            this.y = y;
        }

        @Override
        public void activate() {

        }

        /**
         * Determine the priority of the players based on the distance to the antenna
         * @param board The board to determine the priority on
         * @return The players sorted by priority
         */
        public Player[] determinePriority(Board board) {
            Player[] players = board.getPlayers();
            return sortPlayers(players);
        }

        /**
         * Sort the players based on the distance to the antenna
         * @param players The players to sort
         * @return The sorted players
         */
        private Player[] sortPlayers(Player[] players) {
            //sort array by distance to antenna
            Arrays.sort(players, (Player p1, Player p2) -> {
                int distance1 = calculateDistance(p1);
                int distance2 = calculateDistance(p2);
                return Integer.compare(distance1, distance2);
            });
            return sortSameDistance(players);
        }

        /**
         * Sort the players that have the same distance to the antenna
         * @param players The players to sort
         * @return The sorted players
         */
        private Player[] sortSameDistance(Player[] players) {
            for (int i = 0; i < players.length; i++) {
                //find subset of players with same distance
                int subsetEndIndex = 0;
                for (int j = 1; j < players.length; j++) {
                    //break if the distance is not the same
                    if (calculateDistance(players[i]) < calculateDistance(players[j])) {
                        break;
                    }
                    //set end index if the distance is the same
                    if (calculateDistance(players[i]) == calculateDistance(players[j])) {
                        subsetEndIndex = j+1; //non-inclusive end index
                    }
                }
                //If subsetEndIndex is greater than 0, multiple players have the same distance
                if (subsetEndIndex > 0) {
                    //sort subset of players based on their position
                    players = determineOrder(players, i, subsetEndIndex);
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
         */
        private Player[] determineOrder(Player[] players, int start, int end) {
            //Create an array of the subset of players with the same distance
            Player[] subset = Arrays.copyOfRange(players, start, end);
            //track the correct order of the subset
            int[] order = new int[subset.length];
            for (int i = 0; i < subset.length; i++) {
                for (int j = 0; j < subset.length; j++) {
                    //if the player is on the same row as the antenna, they are first
                    if(subset[i].getSpace().y == this.y) {
                        continue;
                    }
                    if(i != j) {
                        //if player j is in the same row as the antenna, increment the order of player i
                        if (subset[j].getSpace().y == this.y) {
                            order[i]++;
                        }
                        //if player i is above the antenna row, by index
                        else if(subset[i].getSpace().y > this.y) {
                            //if player j is above the antenna by index, and the player i.x < player j.x, increment the order of player i
                            if(subset[i].getSpace().y < subset[j].getSpace().y && subset[j].getSpace().y > this.y && subset[i].getSpace().x < subset[j].getSpace().x) {
                                order[i]++;
                            }
                        }
                        //if player i is below the antenna row, by index
                        else {
                            //if player j is above the antenna by index, or if player j is below the antenna by index and player i.x > player j.x, increment the order of player i
                            if(subset[j].getSpace().y > this.y || subset[j].getSpace().y > this.y && subset[i].getSpace().x > subset[j].getSpace().x) {
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
         */
        private int calculateDistance(Player player) {
            return Math.abs(player.getSpace().x - this.x) + Math.abs(player.getSpace().y - this.y);
        }

}
