package dk.dtu.compute.se.pisd.roborally.model;

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

        public LinkedList<Player> determinePriority(Board board) {
            LinkedList<Player> players = new LinkedList<>();
            boolean sameDistance = false;
            for (int i = 0; i < board.getPlayersNumber(); i++){
                if (players.isEmpty()) {
                    players.add(board.getPlayer(i));
                }
                else {
                    for (int j = 0; j < players.size(); j++) {
                        if (calculateDistance(board.getPlayer(i)) < calculateDistance(players.get(j))) {
                            players.add(j, board.getPlayer(i));
                            break;
                        }
                        if (calculateDistance(board.getPlayer(i)) < calculateDistance(players.get(j))) {
                            players.add(j, board.getPlayer(i));
                            sameDistance = true;
                            break;
                        }
                        else if (j == players.size() - 1) {
                            players.add(board.getPlayer(i));
                            break;
                        }
                    }
                }
                //How to handle duplicate distance?
            }
            return players;

        }

        private int calculateDistance(Player player) {
            return Math.abs(player.getSpace().x - this.x) + Math.abs(player.getSpace().y - this.y);
        }
}
