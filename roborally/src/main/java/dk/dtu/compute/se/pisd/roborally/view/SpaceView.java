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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.field.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.field.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.field.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

/**
 * Handles how a space is displayed on the board.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author Daniel Overballe Lerche, s235095@dtu.dk
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 60; // 75;
    final public static int SPACE_WIDTH = 60; // 75;

    public final Space space;

    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    /**
     * Draws the different elements of the space view based on the space's content.
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     */
    private void updatePlayer() {

        this.getChildren().clear();
        addEmpty();
        addActions();
        addRobot();
        addWalls();
    }

    /**
     * Adds a robot to the space view based on the <code>player.getRobotId()<code/>
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     */
    private void addRobot() {
        Player player = space.getPlayer();
        if(player == null) return;
        ImageView robotImage = new ImageView("robots/r" + player.getRobotId() + ".png");
        robotImage.setFitWidth(SPACE_WIDTH);
        robotImage.setFitHeight(SPACE_HEIGHT);
        robotImage.setRotate(90 * player.getHeading().ordinal());
        this.getChildren().add(robotImage);
    }

    /**
     * Adds actions to the space view, if there is any.
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     */
    private void addActions() {
        if(space.getActions().isEmpty()) return;
        for (FieldAction action : space.getActions()) {
            if(action instanceof ConveyorBelt conveyorBelt) {
                ImageView conveyorBeltImage = new ImageView("conveyor_belts/blue/blue_belt_forward.png");
                conveyorBeltImage.setFitWidth(SPACE_WIDTH);
                conveyorBeltImage.setFitHeight(SPACE_HEIGHT);
                this.getChildren().add(conveyorBeltImage);
                Heading heading = conveyorBelt.getHeading();
                conveyorBeltImage.rotateProperty().set(90 * heading.ordinal() - 180);
            }
            if (action instanceof Checkpoint checkpoint) {
                ImageView checkpointImage = new ImageView("checkpoints/checkpoint" + checkpoint.getOrderNumber() + ".png");
                checkpointImage.setFitWidth(SPACE_WIDTH);
                checkpointImage.setFitHeight(SPACE_HEIGHT);
                this.getChildren().add(checkpointImage);
            }
        }
    }

    /**
     * Adds an empty space to the space view, if there is no actions on the space.
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     */
    private void addEmpty() {
        if(!space.getActions().isEmpty()) return;
        ImageView empty = new ImageView("board_elements/images/empty.png");
        empty.setFitWidth(SPACE_WIDTH);
        empty.setFitHeight(SPACE_HEIGHT);
        this.getChildren().add(empty);
    }

    /**
     * Adds walls to the space view, if there is any.
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     */
    private void addWalls() {
        if(!space.getWalls().isEmpty()) {
            for (Heading wall : space.getWalls()) {
                StackPane group = new StackPane();
                ImageView wallImage = new ImageView("board_elements/images/wall.png");
                group.getChildren().add(wallImage);

                wallImage.setFitWidth((double) SPACE_WIDTH /8);
                wallImage.setFitHeight(SPACE_HEIGHT);
                group.alignmentProperty().set(Pos.CENTER_LEFT);
                group.rotateProperty().set(90 * wall.ordinal() - 90);

                this.getChildren().add(group);
            }
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updatePlayer();
        }
    }

}
