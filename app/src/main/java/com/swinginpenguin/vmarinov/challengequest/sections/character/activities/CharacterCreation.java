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
import android.widget.Toast;

import com.swinginpenguin.vmarinov.challengequest.R;
import com.swinginpenguin.vmarinov.challengequest.model.AttributeSet;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreatureProperties;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreaturesTypes;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.db.utils.CreatureDBUtils;

import java.util.List;

public class CharacterCreation extends Activity {

    private final String NAME_INPUT_DEFAULT_VALUE = getString(R.string.editable_empty);

    private EditText nameInput;
    private Button createButton;
    private Button resetButton;
    private RadioGroup genderSelector;
    private RadioGroup classSelector;

    private String _title;
    private String _description;
    private int _gender;
    private int _race;
    private int _heroClass = CreaturesTypes.PLAYER.getId();
    private int _subClass = CreatureProperties.BRAND_NEW_HERO.getId();
    private CreatureDBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbUtils = new CreatureDBUtils(this);

        nameInput = (EditText) findViewById(R.id.hero_name_input);
        genderSelector = (RadioGroup) findViewById(R.id.hero_gender_selector);
        classSelector = (RadioGroup) findViewById(R.id.hero_race_selector);

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

    public void onRaceSelectorItemClicked(View view) {
        switch (view.getId()) {
            case R.id.hero_race_selector_human:
                _race = CreatureProperties.HUMAN.getId();
                _description = getString(R.string.race_human_description);
                break;
            case R.id.hero_race_selector_elf:
                _race = CreatureProperties.ELF.getId();
                _description = getString(R.string.race_elf_description);
                break;
            case R.id.hero_race_selector_dwarf:
                _race = CreatureProperties.DWARF.getId();
                _description = getString(R.string.race_dwarf_description);
                break;
            case R.id.hero_race_selector_gnome:
                _race = CreatureProperties.GNOME.getId();
                _description = getString(R.string.race_gnome_description);
                break;
            case R.id.hero_race_selector_giant:
                _race = CreatureProperties.GIANT.getId();
                _description = getString(R.string.race_giant_description);
                break;
            default:
                break;
        }
    }

    public void resetData(View view){
        genderSelector.clearCheck();
        classSelector.clearCheck();
        nameInput.setText(NAME_INPUT_DEFAULT_VALUE);
    }

    public void createHero(View button) {
        //TODO populate all parameters correctly - Use ClassBaseUtils
        _title = nameInput.getText().toString();
        List<AttributeSet> attributes = null;
        List<Float> stats = null;
        List<Integer> abilities = null;
        List<Integer> items = null;
        List<Integer> loot = null;
        Creature playerHero = dbUtils.add(CreaturesTypes.PLAYER.getId(), _title, _description, 0, 1, _gender, _race,
                _heroClass, _subClass, attributes, stats, abilities, items, loot);
        if (playerHero.getIdentity().getType() != ErrorCodes.DB_ERROR.getErrorCode()) {
            Intent createHeroIntent = new Intent(this, CharacterOverview.class);
            createHeroIntent.putExtra("playerHero", playerHero);
            startActivity(createHeroIntent);
        } else {
            //TODO show error message and prompt user for different input.
            Toast.makeText(this, "Error creating character!", Toast.LENGTH_LONG);
        }
    }
}
