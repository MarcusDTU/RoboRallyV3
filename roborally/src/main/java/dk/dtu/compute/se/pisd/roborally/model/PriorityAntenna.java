package dk.dtu.compute.se.pisd.roborally.model;
import java.util.Arrays;
import java.util.LinkedList;

public class PriorityAntenna extends BoardElement{

        int x;
        int y;

        public PriorityAntenna(Orientation orientation, int x, int y) {
            super(orientation);
            this.x = x;
            this.y = y;
        }

        @Override
        public void activate() {

        }
        
        public Player[] determinePriority(Board board) {
            Player[] players = board.getPlayers();
            return sortPlayers(players);
        }

    private Player[] sortSameDistance(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            int subsetEndIndex = 0;
            for (int j = 1; j < players.length; j++) {
                if (calculateDistance(players[i]) < calculateDistance(players[j])) {
                    break;
                }
                if (calculateDistance(players[i]) == calculateDistance(players[j])) {
                    subsetEndIndex = j;
                }
            }
            if (subsetEndIndex > 0) {
                players = determineOrder(players, i, subsetEndIndex);
            }
        }
        return players;
    }

    private Player[] determineOrder(Player[] players, int start, int end) {
        return players;
    }

    private Player[] sortPlayers(Player[] players) {
        Arrays.sort(players, (Player p1, Player p2) -> {
            int distance1 = calculateDistance(p1);
            int distance2 = calculateDistance(p2);
            return Integer.compare(distance1, distance2);
        });
        return sortSameDistance(players);
    }

        /**
         * Calculate the distance between the antenna and a player
         * @param player The player to calculate the distance to
         * @return The distance between the antenna and the player
         */
        private int calculateDistance(Player player) {
            return Math.abs(player.getSpace().x - this.x) + Math.abs(player.getSpace().y - this.y);
        }

        /**
         * Update the distancePlayers array
         * @param distancePlayers The array to update
         * @param playerIndex The index of the player
         * @param distance The distance to the player
         * @return The updated distancePlayers array
         */
        private int[][] updateDistancePlayers(int[][] distancePlayers, int playerIndex, int distance) {
            for (int i = 0; i < distancePlayers.length; i++) {
                if (distancePlayers[i][0] == 0) {
                    distancePlayers[i][0] = distance;
                    distancePlayers[i][playerIndex+1] = 1;
                    break;
                }
                else if (distancePlayers[i][0] == distance) {
                    distancePlayers[i][playerIndex+1] = 1;
                    break;
                }
            }
            return distancePlayers;
        }
}
