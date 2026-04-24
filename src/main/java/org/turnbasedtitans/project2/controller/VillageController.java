package org.turnbasedtitans.project2.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class VillageController {

    // Health bar container
    @FXML
    private HBox healthContainer;

    // Canvas used to render the animated hero sprite
    @FXML
    private Canvas heroSprite;

    // Sprite animation settings
    private static final double FRAME_WIDTH = 100;
    private static final double FRAME_HEIGHT = 55;
    private static final int IDLE_FRAMES = 8;
    private static final double SPRITE_SCALE = 7;

    @FXML
    public void initialize() {

        // Load sprite sheet and start idle animation loop
        if (heroSprite != null) {
            Image spriteSheet = new Image(getClass().getResourceAsStream("/org/turnbasedtitans/project2/characters/HeroKnight.png"));
            heroSprite.setWidth(FRAME_WIDTH * SPRITE_SCALE);
            heroSprite.setHeight(FRAME_HEIGHT * SPRITE_SCALE);

            GraphicsContext gc = heroSprite.getGraphicsContext2D();
            gc.setImageSmoothing(false);
            drawFrame(gc, spriteSheet, 0);

            // Timeline that cycles through idle animation frames
            Timeline idleAnimation = new Timeline(
                    new KeyFrame(Duration.millis(150), event -> {
                        Integer currentFrame = (Integer) heroSprite.getProperties().getOrDefault("frame", 0);
                        int nextFrame = (currentFrame + 1) % IDLE_FRAMES;
                        drawFrame(gc, spriteSheet, nextFrame);
                        heroSprite.getProperties().put("frame", nextFrame);
                    })
            );
            idleAnimation.setCycleCount(Timeline.INDEFINITE);
            idleAnimation.play();
        }

        // Adjust health bar margins based on current window width
        if (healthContainer.getParent() instanceof AnchorPane pane) {
            updateHealthMargins(pane);
            pane.widthProperty().addListener((o, oldW, newW) -> updateHealthMargins(pane));
        }

        // Re-apply margin logic if the parent container changes
        healthContainer.parentProperty().addListener((obs, oldParent, parent) -> {
            if (parent instanceof AnchorPane pane) {
                updateHealthMargins(pane);
                pane.widthProperty().addListener((o, oldW, newW) -> updateHealthMargins(pane));
            }
        });
    }

    // Draws a single frame from the sprite sheet onto the canvas
    private void drawFrame(GraphicsContext gc, Image spriteSheet, int frame) {
        double sx = frame * FRAME_WIDTH;
        gc.clearRect(0, 0, heroSprite.getWidth(), heroSprite.getHeight());
        gc.drawImage(
                spriteSheet,
                sx, 0, FRAME_WIDTH, FRAME_HEIGHT,
                0, 0, heroSprite.getWidth(), heroSprite.getHeight()
        );
    }

    // Updates left/right anchors so the health bar keeps percentage-based margins
    private void updateHealthMargins(AnchorPane pane) {
        double margin = pane.getWidth() * 0.05;
        AnchorPane.setLeftAnchor(healthContainer, margin);
        AnchorPane.setRightAnchor(healthContainer, margin);
    }
}
