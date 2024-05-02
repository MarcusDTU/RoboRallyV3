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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.model.*;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    final private RoboRally roboRally;

    private GameController gameController;

    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    public void newGame() throws IOException {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            Board board = new Board(8, 8);
            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, i+1, "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }


    /**
     * Stops the current game, providing the user with an option to save their progress. The user may also cancel the
     * operation to stop the game. The method returns {true} if the game was successfully stopped, which can include
     * saving the game. If the user chooses to cancel the operation or if there is no game currently being played, the method
     * will return {false}.
     * @author ...
     */

    public void saveGame() throws IOException {
        Board board = gameController.board; // get the board from the game controller
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); // create a Gson object
        String homeFolder = System.getProperty("user.home");
        FileWriter fileWriter = new FileWriter(homeFolder + File.separator + "gameData.json");
        if (board != null) {
            fileWriter.append(gson.toJson(board)); // write the board to the file
        }
        fileWriter.close(); // close the file writer
    }

    /**
     * Load a game state from a file, given the file path. The method will throw an {IOException} if an I/O error occurs
     * while reading from the file or if a malformed or unmappable byte sequence is read from the file. The method assumes
     * a specific file structure and that all necessary classes (such as {Board}, {Player}, {Space}, and {CommandCardField})
     * are present and properly structured. It iterates through the board and player components, linking them together and
     * setting up the game state. It also determines the current phase of the game and initiates the appropriate game phase
     * through {GameController}. The {path} parameter should be a valid file path to a JSON file that correctly represents
     * the game state with the expected schema. This method directly affects the state of the game and should be called in a
     * context where such changes are appropriate.
     * @param path The file path of the game state to be loaded.
     */
    public void loadGame(String path) throws IOException {
        Gson gson = new Gson();
        Path data = Path.of(path);

        Board board = gson.fromJson(Files.readString(data), Board.class);

        for (Player player : board.getPlayers()) { // for each player in the board
            player.getDiscardedPile().player = player; // set the player's discarded pile player to the player
            for (Space[] space: board.getSpaces()) { // for each space in the board
                for (Space s : space) { // for each space in the space
                    if (player.getSpace() != null && player.getSpace().x == s.x && player.getSpace().y == s.y){ // if the player's space is not null and the player's space x and y are equal to the space's x and y
                        player.setSpace(s); // set the player's space to the space
                    }
                }
            }
            Player currentPlayer = board.getCurrentPlayer(); // get the current player
            for (CommandCardField card : player.getProgram()) { // for each card in the player's program
                card.player = player;
                if (currentPlayer.getName().equals(player.getName())) { // if the current player's name is equal to the player's name
                    for (CommandCardField card2 : currentPlayer.getProgram()) { // for each card in the current player's program
                        card2.player = currentPlayer;
                        card2.setCard(card.getCard());
                    }
                }
            }

            for (CommandCardField card: player.getCards()){ // for each card in the player's cards
                card.player = player;
                if(currentPlayer.getName().equals(player.getName())){ // if the current player's name is equal to the player's name
                    for (CommandCardField card2: currentPlayer.getCards()){ // for each card in the current player's cards
                        card2.player = currentPlayer;
                    }
                }
            }
            player.board = board;
        }


        for (Space[] space : board.getSpaces()) { // for each space in the board's spaces
             for (Space s : space) { // for each space in the space
                 s.board = board;
            }
        }
        Player currentPlayer = board.getCurrentPlayer();
        currentPlayer.board = board;

        gameController = new GameController(board);
        //add if statements here later when other phases are added
        if(board.getPhase().name().equals("ACTIVATION")){ // if the board's phase is equal to "ACTIVATION"
            gameController.startActivationPhase(board.getStep()); // start the activation phase
        } else {
            gameController.startProgrammingPhase(); // start the programming phase
        }
        roboRally.createBoardView(gameController); // create the board view
    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() throws IOException {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    public void exit() throws IOException {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}
