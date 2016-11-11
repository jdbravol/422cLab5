package assignment5;

import java.util.TimerTask;

import javafx.application.Platform;

public class AnimThread extends TimerTask {
	public void run(){
        Platform.runLater(new Runnable() {
            public void run() {
    			for(int i = 0; i < CritterController.animationFPS; i++){
    				Critter.worldTimeStep();
    			}
    			
    			// update the world view
    			WorldView.updateWorldView();
    			
    			CritterController.doRunStats();
            }
        });
	}
}
