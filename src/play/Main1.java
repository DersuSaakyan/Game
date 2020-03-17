package play;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controller;
import sample.Main;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main1 extends Application{
    FileWriter w;
    FileWriter w1;
    FileReader r;
    Controller c = new Controller();
    MediaPlayer mediaPlayerSound;
    MediaPlayer mediaPlayerGameOver;
    int x = 0;
    int y = 0;
    int angle = 45;
    int speed = 10;
    int i = 0;
    Group root = new Group();
    Scene scene = new Scene(root, 600, 325);
    Label score;
    static Arc wolf;
    public static   Circle circle = new Circle();
    List<Matrix> list = new ArrayList<>();
    public static Label labelRecord;
    static int l = 1;


    @Override
    public void start(Stage primaryStage) throws Exception {

        circle.setTranslateX(Math.random() * 300);
        circle.setTranslateY(Math.random() * 300);
        circle.setRadius(7);
        circle.setFill(Color.RED);

        primaryStage.setTitle("GAME");
        wolf = new Arc(16, 16, 16, 16, angle, 270);
        wolf.setType(ArcType.ROUND);

        Label count = new Label("Count");
        count.setPrefSize(50, 20);
        count.setLayoutX(500);
        count.setLayoutY(0);
        count.setFont(new Font("Cambria", 15));



        FileReader reader = new FileReader("rec.txt");
        int h = 0;
        String m = "";
        while ((h = reader.read()) != -1) {
            m += (char) h;
        }
        labelRecord = new Label("RECORD " + m);
        labelRecord.setPrefSize(100, 20);
        labelRecord.setLayoutX(290);
        labelRecord.setLayoutY(0);
        labelRecord.setFont(new Font("Cambira", 15));

        score = new Label();
        score.setText("0");
        score.setPrefSize(50, 20);
        score.setLayoutX(555);
        score.setLayoutY(0);
        scene.setFill(Color.valueOf("#ccccb3"));

        String file = "gameOver.mp3";
        Media sound = new Media(new File(file).toURI().toString());
        mediaPlayerGameOver = new MediaPlayer(sound);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {



                if (change()) {
                    i++;
                    score.setText(String.valueOf(i));
                    circle.setTranslateX(Math.random() * (scene.getWidth() - 7));
                    circle.setTranslateY(Math.random() * (scene.getHeight() - 7));
                    add();
                    if (eat()) {
                        String file = "x.mp3";
                        Media sound = new Media(new File(file).toURI().toString());
                        mediaPlayerSound = new MediaPlayer(sound);
                        mediaPlayerSound.play();
                    }

                }
                if (gameOver()) {
                    JOptionPane.showMessageDialog(null, "Game Over" + " " + "Your score is " + score.getText());
                    primaryStage.close();

                }


                if (event.getCode() == KeyCode.RIGHT) {
                    wolf.setTranslateX(x += speed);
                    wolf.setStartAngle(45);
                    if (x >= scene.getWidth()) {
                        x = -32;
                    }
                }
                if (event.getCode() == KeyCode.LEFT) {
                    wolf.setTranslateX(x -= speed);
                    wolf.setStartAngle(225);
                    if (x <= -32) {
                        x = (int) scene.getWidth();
                    }

                }
                if (event.getCode() == KeyCode.UP) {
                    wolf.setTranslateY(y -= speed);
                    wolf.setStartAngle(135);
                    if (y <= -32) {
                        y = (int) scene.getHeight();
                    }

                }
                if (event.getCode() == KeyCode.DOWN) {
                    wolf.setStartAngle(-40);
                    wolf.setTranslateY(y += speed);
                    if (y >= scene.getHeight()) {
                        y = -32;
                    }
                }
            }
        });
        Mythread mythread=new Mythread();
        mythread.start();
        root.getChildren().addAll(wolf, circle, count, score, labelRecord);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();

    }

    private void add() {
        boolean f = true;
        int a = (int) (Math.random() * (scene.getWidth() - 7));
        int b = (int) (Math.random() * (scene.getHeight() - 7));
        while (f) {
            Rectangle r = new Rectangle(a, b, 7, 7);
            r.setFill(Color.LIGHTGREEN);
            r.setStroke(Color.GREEN);
            r.setStrokeWidth(2);
            if ((r.getX() + 4.9 != wolf.getCenterX() && r.getY() + 4.9 != wolf.getCenterY()) && r.getX() + 4.9 != circle.getTranslateX() && r.getY() + 4.9 != circle.getTranslateY()) {
                Matrix matrix = new Matrix(r.getX() + 4.9, r.getY() + 4.9);
                list.add(matrix);
                root.getChildren().addAll(r);
                f = false;
            }
        }
    }

    public boolean change() {

        if (Math.abs(wolf.getTranslateX() + 16 - circle.getTranslateX()) < 17 && Math.abs(wolf.getTranslateY() + 16 - circle.getTranslateY()) < 17) {
            return true;
        }
        return false;
    }

    public boolean gameOver() {
        for (Matrix matrix : list) {
            if (Math.abs(wolf.getTranslateX() - matrix.getX() + 16) <= 16 && Math.abs(wolf.getTranslateY() - matrix.getY() + 16) <= 16) {
                if (eat()) {
                    mediaPlayerGameOver.play();
                }
                if (Controller.f) {
                    Controller.mediaPlayer.stop();
                }
                saveScore();
                record();
                return true;
            }
        }
        return false;

    }

    public boolean eat() {
        if (c.t) {
            return true;
        }
        return false;
    }


    public void saveScore() {
        try {
            w = new FileWriter("score.txt");
            w.write(score.getText());
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void record() {
        try {


            r = new FileReader("score.txt");
            String str = "";
            int j = 0;

            while ((j = r.read()) != -1) {
                str += (char) j;
            }
            r.close();


            FileReader r1 = new FileReader("rec.txt");
            String str1 = "";
            int k = 0;
            while ((k = r1.read()) != -1) {
                str1 += (char) k;
            }
            r1.close();
            int c = Integer.parseInt(str);
            int t = Integer.parseInt(str1);

            if (c > t) {
                FileWriter fileWriter = new FileWriter("rec.txt");
                fileWriter.write(str);
                fileWriter.flush();

            }
        } catch (IOException e) {

        }
    }

}




