package play;

import static play.Main1.circle;

public class Mythread extends Thread {
    @Override
    public void run() {
        try {
            while (true) {
                Main1 main1 = new Main1();
                Thread.sleep(60000);
                circle.setTranslateX(Math.random() * (main1.scene.getWidth() - 7));
                circle.setTranslateY(Math.random() * (main1.scene.getHeight() - 7));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
