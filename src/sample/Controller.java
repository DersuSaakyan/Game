package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import play.Main1;

import java.io.File;

public class Controller {
    public static boolean f=false;
    public static boolean t=false;
    public static MediaPlayer mediaPlayer;
    @FXML
    Button exit;
    @FXML
    Button play;

    @FXML
    RadioMenuItem music;
    @FXML
    RadioMenuItem sound;

    @FXML
    public void click() throws Exception {
        if (exit.isArmed()) {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        } else if (play.isArmed()) {
            Main1 main1 = new Main1();
            Stage stage = new Stage();
            main1.start(stage);
        }
    }

    @FXML
    public void startMusic() {
        if (music.isSelected()) {
            String file = "m1.mp3";
            Media sound = new Media(new File(file).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            f=true;
        }
          if (!music.isSelected() && f) {
            mediaPlayer.stop();
            f=false;
        }
    }
    @FXML
    public void eatSound() {
        if (sound.isSelected()) {
            t = true;
            System.out.println(t);

        }
        if(!sound.isSelected()){
            t=false;
            System.out.println(t);
        }
    }



}
