package com.swinginpenguin.vmarinov.challengequest.activities.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swinginpenguin.vmarinov.challengequest.R;
import com.swinginpenguin.vmarinov.challengequest.activities.character.CharacterCreation;
import com.swinginpenguin.vmarinov.challengequest.activities.character.CharacterOverview;
import com.swinginpenguin.vmarinov.challengequest.activities.login.LoginActivity;

public class ChallengeQuests extends Activity {

    private Boolean _characterCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_quests);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.challenge_quests, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Button startButton = (Button) findViewById(R.id.button_game_start);
        startButton.setVisibility(View.VISIBLE);

        final TextView loadingLabel = (TextView)findViewById(R.id.label_loading);
        loadingLabel.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startButtonClicked(View button) {
        Intent startButtonIntent = null;
        if (_characterCreated) {
            startButtonIntent = new Intent(this, CharacterOverview.class);
        } else {
            //TODO make it intent with result so that _characterCreated can be set properly
            startButtonIntent =  new Intent(this, CharacterCreation.class);
            _characterCreated = true;
        }
        startActivity(startButtonIntent);
    }

    public void loginButtonClicked(View button) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
