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
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import java.util.List;

/**
 *
 * Controls the game logic.
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;

    //private DiscardPile discardPile = new DiscardPile();

    public GameController(Board board) {
        this.board = board;
    }


    /**
     * Moves the given player forward one space on the board, if possible.
     *
     * @param player The player to move forward.
     * @throws NullPointerException if {@code player} is null.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void moveForward(@NotNull Player player) {
        if (player.board == board) {
            Space space = player.getSpace();
            Heading heading = player.getHeading();

            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {
                    // we don't do anything here  for now; we just catch the
                    // exception so that we do no pass it on to the caller
                    // (which would be very bad style).
                }
            }
        }
    }
    /**
     * Moves the given player forward two spaces on the board, if possible.
     *
     * @param player The player to move forward.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    /**
     * Moves the given player backward one space on the board, if possible.
     *
     * @param player The player to move backward.
     * @throws NullPointerException if {@code player} is null.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
   public void backup(@NotNull Player player) {
        Space space = player.getSpace();
        Heading heading = player.getHeading();
        Space target = board.getNeighbour(space, heading.opposite());
        if (target != null) {
            try {
                moveToSpace(player, target, heading.opposite());
            } catch (ImpossibleMoveException e) {

            }
        }
    }
    /**
     * Turns the given player's heading to the right.
     *
     * @param player The player whose heading will be turned.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void turnRight(@NotNull Player player) {
        player.setHeading(player.getHeading().next());
    }

    /**
     * Turns the given player's heading to the left.
     *
     * @param player The player whose heading will be turned.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
    }
    /**
     * Moves the given player to the specified space with the provided heading.
     *
     * @param player The player to move.
     * @param space The space to move the player to.
     * @param heading The heading in which the player will move.
     * @throws ImpossibleMoveException if the move cannot be completed.
     * @throws NullPointerException if {@code player}, {@code space}, or {@code heading} is null.
     */
    void moveToSpace(@NotNull Player player, @NotNull Space space, @NotNull Heading heading) throws ImpossibleMoveException {
        assert board.getNeighbour(player.getSpace(), heading) == space; // make sure the move to here is possible in principle
        Player other = space.getPlayer();
        if (other != null){
            Space target = board.getNeighbour(space, heading);
            if (target != null ) {
                moveToSpace(other, target, heading);
                assert target.getPlayer() == null : target; // make sure target is free now
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);
    }

    /**
     * Increases the power-up count of the given player by one.
     *
     * @param player The player whose power-up count will be increased.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void powerUp(Player player){
        player.oneUpPowerUpCnt();
    }

    /**
     * Moves the current player to the specified space if it's unoccupied.
     *
     * @param space The space to move the current player to.
     */
    public void moveCurrentPlayerToSpace(Space space) {
        Player currentPlayer = board.getCurrentPlayer();
        if(space.getPlayer() == null){
            currentPlayer.setSpace(space);
            space.setPlayer(currentPlayer);
            board.setTotalMoves(board.getTotalMoves() + 1);

            int nextPlayerNum;

            if(board.getPlayersNumber() != board.getPlayerNumber(currentPlayer) + 1){
                nextPlayerNum = board.getPlayerNumber(currentPlayer) + 1;
            } else {
                nextPlayerNum = (board.getPlayerNumber(currentPlayer) + 1) - board.getPlayersNumber();
            }
            Player nextPlayer = board.getPlayer(nextPlayerNum);
            board.setCurrentPlayer(nextPlayer);
        }

    }
    /**
     * Makes the program fields at the specified register visible for all players on the board.
     *
     * @param register The register number of the program fields to make visible.
     */
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * Makes all program fields invisible for all players on the board.
     */
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }
    /**
     * Finishes the programming phase by making program fields
     * invisible, making the first program field visible,
     * and setting the phase, current player, and step accordingly.
     */
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }


    /**
     * Executes the programs of all players on the board.
     */

    public void startActivationPhase(int steps) { // start the activation phase
        makeProgramFieldsInvisible(); // make the program fields invisible
        for (int i = 0; i <= steps; i++) { // for each step
            makeProgramFieldsVisible(i); // make the program fields visible
        }
        board.setPhase(Phase.ACTIVATION); // set the board's phase to "ACTIVATION"
    }


    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * Executes a single step of the programs of all players on the board.
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * Continues executing the programs of all players on the board
     * until the activation phase ends or step mode is enabled.
     */
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    /**
     * Executes the next step of the program of the current player during the activation phase.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    executeCommand(currentPlayer, command);
                    if(command == Command.OPTION_LEFT_RIGHT) return;
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }


    /**
     * Executes the given command for the specified player.
     *
     * @param player The player for whom the command will be executed.
     * @param command The command to execute.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            player.setCurrentCommand(command);
            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                case FAST_FAST_FORWARD:
                    this.fastForward(player);
                    this.moveForward(player);
                    break;
                case U_TURN:
                    this.turnRight(player);
                    this.turnRight(player);
                    break;
                case BACK_UP:
                    this.backup(player);
                    break;
                case AGAIN:
                    executeCommand(player, player.getLastCommand());
                    break;
                case POWER_UP:
                    this.powerUp(player);
                    break;
                case OPTION_LEFT_RIGHT:
                    board.setPhase(Phase.PLAYER_INTERACTION);
                    break;
                default:
                    // DO NOTHING (for now)
            }
            //player.getDiscardedPile().getPile().pile.add(command);
            player.setLastCommand(command);
            board.useCard();
        }
    }


    /**
     * Moves a command card from the source field to the target field.
     *
     * @param source The source field from which to move the command card.
     * @param target The target field to which to move the command card.
     * @return {@code true} if the move is successful, {@code false} otherwise.
     */
    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Starts the programming phase by initializing player decks,
     * setting up program and card fields, and shuffling decks if needed.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        if(board.getCurrentNumberOfCards() <= 0){
            for(int i = 0; i < board.getPlayersNumber(); i++){
                Player player = board.getPlayer(i);
                Deck currentDeck = player.getDeck();
/*


                if(currentDeck.initDeck.size() < 9){
                    for(int j = 0; j < player.getDiscardedPile().getPile().pile.size(); j++){
                        currentDeck.initDeck.add(player.getDiscardedPile().getPile().pile.get(j));
                    }
                    player.getDiscardedPile().getPile().pile.clear();
                }
                */

            }
            //board.resetCards();
        }

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            Deck currentDeck = player.getDeck();
            //ArrayList<Command> twentySeven = init.initDeck;
            if(currentDeck.initDeck.size() < 9){
                currentDeck.initDeck.addAll(player.getDiscardedPile().getPile().pile);
                player.getDiscardedPile().getPile().pile.clear();
                currentDeck.shuffleDeck();
            }
            // when discard pile is added currentDeck.shuffleDeck();

            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(new CommandCard(currentDeck.initDeck.get(0)));
                    currentDeck.initDeck.remove(0);
                    field.setVisible(true);
                }
                for(int j = 0; j <= player.getDiscardedPile().getPile().pile.size(); j++){
                    DiscardPileField pile = player.getDiscardedPile();
                    pile.setPile(player.getDiscardedPile().getPile());
                    pile.setVisible(true);
                }
            }

        }



        for(int i = 0; i < board.getPlayersNumber(); i++){
            for(int j =0; j < board.getPlayer(0).NO_CARDS; j++){
                if(board.getPlayer(i).getCardField(j) != null){
                    board.getPlayer(i).getDiscardedPile().getPile().pile.add(board.getPlayer(i).getCardField(j).getCard().command);
                }
            }
        }

    }
    /**
     * Generates a random command card.
     *
     * @return A randomly generated command card.
     */
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    /**
     * Execute the command option and continue with the next player.
     * @param command the command option to be executed
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void executeCommandOptionAndContinue(Command command) {
        board.setPhase(Phase.ACTIVATION);
        executeCommand(board.getCurrentPlayer(), command);
        // Switch to the next player
        int nextPlayerNumber = board.getPlayerNumber(board.getCurrentPlayer()) + 1;
        // if the player before next player was the last one then
        // switch to programming phase
        if (nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
        } else {
            int step = board.getStep() + 1;
            if (step < Player.NO_REGISTERS) {
                makeProgramFieldsVisible(step);
                board.setStep(step);
                board.setCurrentPlayer(board.getPlayer(0));
            } else {
                startProgrammingPhase();
            }
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }


    /**
     * Custom exception indicating that a move is impossible.
     */
    class ImpossibleMoveException extends Exception {

        private Player player;
        private Space space;
        private Heading heading;

        /**
         * Constructs a new ImpossibleMoveException with the specified player, space, and heading.
         *
         * @param player The player attempting the impossible move.
         * @param space The space where the move was attempted.
         * @param heading The heading in which the move was attempted.
         */
        public ImpossibleMoveException(Player player, Space space, Heading heading) {
            super("Move impossible");
            this.player = player;
            this.space = space;
            this.heading = heading;
        }
    }

}
