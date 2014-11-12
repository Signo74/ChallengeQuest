package com.swinginpenguin.vmarinov.challengequest.sections.character.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.swinginpenguin.vmarinov.challengequest.R;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreatureProperties;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreaturesTypes;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryTypes;
import com.swinginpenguin.vmarinov.challengequest.sections.character.activities.CharacterOverview;

public class CharacterCreation extends Activity {

    private EditText nameInput;
    private Button createButton;
    private Button resetButton;
    private RadioGroup genderSelector;
    private RadioGroup classSelector;

    private int _gender;
    private int _heroClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.character_creation, menu);
        return true;
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

    public void onGenderSelectorClicked(View view) {
        switch (view.getId()) {
            case R.id.hero_gender_selector_male:
                _gender = CreatureProperties.MALE.getId();
                break;
            case R.id.hero_gender_selector_female:
                _gender = CreatureProperties.FEMALE.getId();
                break;
            default:
                break;
        }
    }

    public void onClassSelectorClicked(View view) {
        switch (view.getId()) {
            case R.id.hero_class_selector_fighter:
                _heroClass = CreatureProperties.FIGHTER.getId();
                break;
            case R.id.hero_class_selector_wizard:
                _heroClass = CreatureProperties.WIZARD.getId();
                break;
            case R.id.hero_class_selector_rouge:
                _heroClass = CreatureProperties.ROUGE.getId();
                break;
            case R.id.hero_class_selector_monk:
                _heroClass = CreatureProperties.MONK.getId();
                break;
            default:
                break;
        }
    }

    public void resetData(View view){
        genderSelector.clearCheck();
        classSelector.clearCheck();
    }

    public void createHero(View button) {
        Intent createHeroIntent = new Intent(this, CharacterOverview.class);
        startActivity(createHeroIntent);
    }
}
