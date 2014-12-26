package com.swinginpenguin.vmarinov.challengequest.sections.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swinginpenguin.vmarinov.challengequest.R;
import com.swinginpenguin.vmarinov.challengequest.db.dao.CreaturesDAO;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreaturesTypes;
import com.swinginpenguin.vmarinov.challengequest.sections.character.activities.CharacterCreation;
import com.swinginpenguin.vmarinov.challengequest.sections.character.activities.CharacterOverview;
import com.swinginpenguin.vmarinov.challengequest.sections.login.LoginActivity;
import com.swinginpenguin.vmarinov.challengequest.sections.settings.SettingsActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChallengeQuests extends Activity {
    private final int MAX_NUMBER_OF_HEROES = 3;
    private CreaturesDAO dao;
    private Button startButton;
    RelativeLayout heroGroup;

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
        String selection = "type=" + CreaturesTypes.PLAYER.getId();
        dao = CreaturesDAO.getInstance(this);

        startButton = (Button) findViewById(R.id.button_game_start);
        TextView loadingLabel = (TextView) findViewById(R.id.label_loading);
        heroGroup = (RelativeLayout) findViewById(R.id.hero_buttons_group);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                             ViewGroup.LayoutParams.WRAP_CONTENT);

        loadingLabel.setVisibility(View.INVISIBLE);

        try {
            List<Creature> heroes =  dao.getList(selection);
            if (heroes.size() > 0) {
                int i = 0;
                for (; i < MAX_NUMBER_OF_HEROES && i < heroes.size() ; i++) {
                    Button heroButtonN = (Button) heroGroup.getChildAt(i);
                    if (heroes.get(i) != null) {
                        heroButtonN.setText(heroes.get(i).getIdentity().getTitle());
                        heroButtonN.setVisibility(View.VISIBLE);
                        heroButtonN.setTag(heroes.get(i));
                    } else {
                        break;
                    }
                }
                // TODO see how to add Below for the child component.
                params.addRule(RelativeLayout.BELOW, heroGroup.getId());
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            } else if (heroes.size() >= MAX_NUMBER_OF_HEROES) {
                return;
            } else {
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
            }
        } catch (ExecutionException | InterruptedException ex) {
            Log.e("ChallengeQuests.onStart","Exception: " + ex + " was thrown while starting main activity");
        }
        startButton.setLayoutParams(params);
        startButton.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.action_delete_db) {
            dao.deleteAll();
            for (int i = 0 ; i < heroGroup.getChildCount() ; i++) {
                heroGroup.getChildAt(i).setVisibility(View.INVISIBLE);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                                 ViewGroup.LayoutParams.WRAP_CONTENT);
            // TODO see how to remove a rule for API below 17
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            startButton.setLayoutParams(params);

        }
        return super.onOptionsItemSelected(item);
    }

    public void startButtonClicked(View button) {
        Intent startButtonIntent;
        startButtonIntent =  new Intent(this, CharacterCreation.class);
        startActivity(startButtonIntent);
    }

    public void heroButtonClicked(View button) {
        Intent heroIntent = new Intent(this, CharacterOverview.class);
        Creature hero = (Creature) button.getTag();
        heroIntent.putExtra("hero", hero);
        startActivity(heroIntent);
    }

    public void loginButtonClicked(View button) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
