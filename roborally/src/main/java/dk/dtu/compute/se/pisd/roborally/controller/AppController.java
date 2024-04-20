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

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.google.gson.Gson;
/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    final private RoboRally roboRally;

    private GameController gameController;

    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    public void newGame() {
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
            Board board = new Board(8,8);
            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    public void saveGame() { // save the game to a file
        if (gameController != null && gameController.board != null) { // if there is a game running
            String homeFolder = System.getProperty("user.home"); // get the user's home folder

            Gson gson = new GsonBuilder().setPrettyPrinting().create(); // create a Gson object

            List<PlayerData> playersData = new ArrayList<>(); // create a list to store player data
            for (Player player : gameController.board.getPlayers()) { // for each player in the game
                List<String> programmingCardIds = new ArrayList<>(); // create a list to store programming card IDs
                List<String> commandCardIds = new ArrayList<>(); // create a list to store command card IDs
                for (int i = 0; i < Player.NO_REGISTERS; i++) { // for each register
                    CommandCardField field = player.getProgramField(i); // get the program field
                    programmingCardIds.add(field != null && field.getCard() != null ? field.getCard().getName() : null);
                }// add the card name to the list
                for (int j = 0; j < Player.NO_CARDS; j++) { // for each card
                    CommandCardField field = player.getCardField(j); // get the card field
                    commandCardIds.add(field != null && field.getCard() != null ? field.getCard().getName() : null);
                } // add the card name to the list
                playersData.add(new PlayerData( // add the player data to the list
                        player.getName(), player.getSpace().x, player.getSpace().y, player.getColor(), player.getHeading().name(),
                        programmingCardIds, commandCardIds, gameController.board.getPhase().name(), gameController.board.getStep()
                )); // create a new PlayerData object with the player's data
            }
            try (FileWriter writer = new FileWriter(homeFolder + File.separator + "gameData.json")) { // try to write to the file
                gson.toJson(playersData, writer); // write the player data to the file
                System.out.println("Game Saved"); // print that the game was saved
            } catch (IOException e) { // catch an IOException
                e.printStackTrace(); // print the stack trace
            }
        }
    }

    public void loadGame(String path) { // load the game from a file
        Gson gson = new Gson(); // create a Gson object
        Path data = Path.of(path); // create a Path object from the path
        Type playersListType = new TypeToken<ArrayList<PlayerData>>(){}.getType(); // create a Type object for the player data
        try (FileReader reader = new FileReader(String.valueOf(data))) { // try to read from the file
            List<PlayerData> playersData = gson.fromJson(reader, playersListType); // read the player data from the file
            if (!playersData.isEmpty()) { // if the player data is not empty
                if (gameController != null) { // if there is a game running
                    if (!stopGame()) { //   stop the game
                        return; // If stopping the game fails, do not proceed with loading a new game
                    }
                }
                // Create a new board with the required number of players
                Board board = new Board(8, 8); // create a new board
                gameController = new GameController(board); // create a new GameController object with the board

                // Create and place players as per the JSON data
                for (PlayerData playerData : playersData) { // for each player data
                    Space space = board.getSpace(playerData.getX(), playerData.getY()); // get the space
                    Player player = new Player(board, playerData.getColor(), playerData.getName()); // create a new player
                    player.setSpace(space); // set the player's space
                    player.setHeading(Heading.valueOf(playerData.getHeading())); // set the player's heading

                   restorePlayerCards(player, playerData); // restore the player's cards

                    board.addPlayer(player); // add the player to the board
                }

                board.setPhase(Phase.valueOf(playersData.get(0).getPhase())); // Assuming all players have the same phase stored
                board.setStep(playersData.get(0).getStep()); // Assuming all players have the same step stored


                if (!board.getPlayers().isEmpty()) { // if there are players
                    board.setCurrentPlayer(board.getPlayers().get(0)); // set the current player to the first player
                }
                gameController.startLoadingPhase(); // start the loading phase

                roboRally.createBoardView(gameController); // create the board view
            }
        } catch (IOException e) { // catch an IOException
            e.printStackTrace();  // print the stack trace
        }
    }

    private void restorePlayerCards(Player player, PlayerData playerData) { // restore the player's cards
        for (int i = 0; i < playerData.getProgrammingCards().size(); i++) { // for each programming card
            String cardName = playerData.getProgrammingCards().get(i); // get the card name
            CommandCard card = findCommandCardByName(cardName); // find the command card by name
            player.getProgramField(i).setCard(card); // set the card in the program field
        }
        for (int i = 0; i < playerData.getCommandCards().size(); i++) { // for each command card
            String cardName = playerData.getCommandCards().get(i); // get the card name
            CommandCard card = findCommandCardByName(cardName); // find the command card by name
            player.getCardField(i).setCard(card); // set the card in the card field
        }
    }

    private CommandCard findCommandCardByName(String cardName) { // find a command card by name
        if (cardName != null) { //
            Command command = Arrays.stream(Command.values()) // get the command by name
                    .filter(c -> c.displayName.equals(cardName)) // filter the command by name
                    .findFirst() // find the first command
                    .orElse(null); // return null if no command is found
            if (command != null) { // if the command is not null
                return new CommandCard(command); // return a new CommandCard object with the command
            }
        }
        return null; // return null if the card name is null
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
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
          //  saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    public void exit() {
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
