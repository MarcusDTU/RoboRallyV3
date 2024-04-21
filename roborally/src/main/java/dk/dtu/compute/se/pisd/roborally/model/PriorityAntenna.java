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
        /**
         * Determine the priority of the players based on their distance to the antenna
         * @param board The board to determine the priority on
         * @return A list of players sorted by their distance to the antenna
         */
        public LinkedList<Player> determinePriority(Board board) {
            LinkedList<Player> players = new LinkedList<>();
            boolean sameDistance = false; //Used to track if multiple players are at the same distance
            int[][] distancePlayers = new int[board.getPlayersNumber()][board.getPlayersNumber()+1]; //Used to track which players are at the same distance. Distance is stored at index 0, players are stored at index 1 and onwards
            for (int i = 0; i < board.getPlayersNumber(); i++){
                if (players.isEmpty()) { //If the list is empty, add the first player
                    players.add(board.getPlayer(i));
                    distancePlayers = updateDistancePlayers(distancePlayers, i, calculateDistance(board.getPlayer(i))); //Update the distancePlayers array
                }
                else {
                    for (int j = 0; j < players.size(); j++) {
                        if (calculateDistance(board.getPlayer(i)) < calculateDistance(players.get(j))) { //If player i is closer than player j, add player i at index j
                            players.add(j, board.getPlayer(i));
                            distancePlayers = updateDistancePlayers(distancePlayers, i, calculateDistance(board.getPlayer(i))); //Update the distancePlayers array
                            break;
                        }
                        if (calculateDistance(board.getPlayer(i)) == calculateDistance(players.get(j))) { //If player i is at the same distance as player j, add player i at index j
                            sameDistance = true; //Set sameDistance to true
                            players.add(j, board.getPlayer(i));
                            distancePlayers = updateDistancePlayers(distancePlayers, i, calculateDistance(board.getPlayer(i))); //Update the distancePlayers array
                            break;
                        }
                        else if (j == players.size() - 1) { //If player i is further than all other players, add player i at the end
                            players.add(board.getPlayer(i));
                            distancePlayers = updateDistancePlayers(distancePlayers, i, calculateDistance(board.getPlayer(i))); //Update the distancePlayers array
                            break;
                        }
                    }
                }
            }
            //TODO: Implement a way to handle multiple players at the same distance
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
