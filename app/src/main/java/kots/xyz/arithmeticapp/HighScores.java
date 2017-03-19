package kots.xyz.arithmeticapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by kots on 18.03.2017.
 */

public class HighScores extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high);

        TextView scoreView = (TextView)findViewById(R.id.high_scores_list);

        SharedPreferences scorePrefs = getSharedPreferences(PlayGame.GAME_PREFS, 0);

        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");

        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores){
            scoreBuild.append(score+"\n");
        }

        scoreView.setText(scoreBuild.toString());
    }


}
