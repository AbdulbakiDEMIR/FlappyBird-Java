package com.example.flappybirddeneme;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.IOException;

import static javafx.scene.input.KeyCode.SPACE;

public class HelloApplication extends Application {
    boolean play = false;
    boolean button = false;
    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        pane.setId("pane");
        Scene scene = new Scene(pane, 500, 500);





        FileInputStream backgroundInput = new FileInputStream("background.png");
        Image backgroundImage = new Image(backgroundInput);
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(500);
        background.setLayoutY(-250);
        pane.getChildren().add(background);

        FileInputStream birdInput = new FileInputStream("bird.png");
        Image imageBird = new Image(birdInput);
        ImageView bird = new ImageView(imageBird);

        FileInputStream pipe1Input = new FileInputStream("pipe.png");
        Image pipe1Image = new Image(pipe1Input);
        ImageView pipe1 = new ImageView(pipe1Image);
        pipe1.setFitHeight(400);
        pipe1.setFitWidth(70);
        pipe1.setLayoutX(400);
        pipe1.setLayoutY(450);
        pane.getChildren().add(pipe1);

        ImageView pipe1_1 = new ImageView(pipe1Image);
        pipe1_1.setFitHeight(400);
        pipe1_1.setFitWidth(70);
        pipe1_1.setLayoutX(670);
        pipe1_1.setLayoutY(450);
        pane.getChildren().add(pipe1_1);

        FileInputStream pipe2Input = new FileInputStream("pipe2.png");
        Image pipe2Image = new Image(pipe2Input);
        ImageView pipe2 = new ImageView(pipe2Image);
        pipe2.setFitHeight(400);
        pipe2.setFitWidth(70);
        pipe2.setLayoutX(400);
        pipe2.setLayoutY(-100);
        pane.getChildren().add(pipe2);

        ImageView pipe2_1 = new ImageView(pipe2Image);
        pipe2_1.setFitHeight(400);
        pipe2_1.setFitWidth(70);
        pipe2_1.setLayoutX(670);
        pipe2_1.setLayoutY(-100);
        pane.getChildren().add(pipe2_1);


        bird.setFitWidth(50);
        bird.setFitHeight(33);
        bird.setLayoutX(150);
        bird.setLayoutY(250);
        pane.getChildren().add(bird);


        Label score = new Label("0");
        score.setLayoutX(230);
        score.setLayoutY(80);
        score.setFont(new Font("Arial", 30));
        score.setStyle("-fx-font-weight: bold");
        score.setTextFill(Color.rgb(255,255,255));
        pane.getChildren().add(score);


        EventHandler birdAnimationEvent = event ->{
                bird.setLayoutY(bird.getLayoutY()-5);
                if(bird.getRotate()>-45)
                     bird.setRotate(bird.getRotate()-3);
                else
                    bird.setRotate(-45);
        };


        Timeline birdAnimation = new Timeline(new KeyFrame(new Duration(6),birdAnimationEvent));
        birdAnimation.setCycleCount(15);
        pane.setOnMousePressed(e->{
            if(play){
                birdAnimation.play();
                bird.setRotate(0);
            }

        });

        Button start = new Button("Start");
        start.setLayoutX(250);
        start.setLayoutY(250);
        pane.getChildren().add(start);

        EventHandler eventHandler = event -> {
            int ust = -100;
            int alt = 450;
            int rand = 0;
            int scoreint;

            if(bird.getLayoutY()>450 || bird.getLayoutY()<25){
                play = false;
            }
            if(pipe1.getBoundsInParent().intersects(bird.getBoundsInParent())){
                play = false;
            }
            else if(pipe2.getBoundsInParent().intersects(bird.getBoundsInParent())){
                play = false;
            }
            else if(pipe1_1.getBoundsInParent().intersects(bird.getBoundsInParent())){
                play = false;
            }
            else if(pipe2_1.getBoundsInParent().intersects(bird.getBoundsInParent())){
                play = false;
            }
            if(play){
                bird.setLayoutY(bird.getLayoutY()+(bird.getRotate()/12)+1);
                if(pipe1.getLayoutX()+50 == bird.getLayoutX() || pipe1_1.getLayoutX()+70 == bird.getLayoutX()){
                    scoreint=Integer.parseInt(score.getText());
                    scoreint++;
                    score.setText(String.valueOf(scoreint));
                }

                if(pipe1.getLayoutX()<=-70){
                    rand = (int)(Math.random()*250);
                    pipe1.setLayoutX(540);
                    pipe2.setLayoutX(540);
                    pipe1.setLayoutY(alt-rand);
                    pipe2.setLayoutY(ust-rand);
                }
                if(pipe1_1.getLayoutX()<=-70){
                    pipe1_1.setLayoutX(540);
                    pipe2_1.setLayoutX(540);
                    pipe1_1.setLayoutY(alt-rand);
                    pipe2_1.setLayoutY(ust-rand);
                }
                if(bird.getRotate()<90){
                    bird.setRotate(bird.getRotate()+1.75    );
                }
                pipe1.setLayoutX(pipe1.getLayoutX()-2);
                pipe1_1.setLayoutX(pipe1_1.getLayoutX()-2);
                pipe2.setLayoutX(pipe2.getLayoutX()-2);
                pipe2_1.setLayoutX(pipe2_1.getLayoutX()-2);
                button=true;
            }
            else{
                if(button)
                    pane.getChildren().add(start);
                    button= false;
            }
        };

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(15),eventHandler));
        timeline.setCycleCount(Timeline.INDEFINITE);
        start.setOnAction(e->{
            score.setText("0");
            pipe1.setLayoutX(400);
            pipe1_1.setLayoutX(670);
            pipe2.setLayoutX(400);
            pipe2_1.setLayoutX(670);
            bird.setRotate(0);
            bird.setLayoutY(250);
            play=true;
            timeline.play();
            pane.getChildren().remove(start);
        });


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}