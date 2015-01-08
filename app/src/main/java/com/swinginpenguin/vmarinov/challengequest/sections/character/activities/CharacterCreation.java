package com.swinginpenguin.vmarinov.challengequest.sections.character.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.swinginpenguin.vmarinov.challengequest.R;
import com.swinginpenguin.vmarinov.challengequest.model.Ability;
import com.swinginpenguin.vmarinov.challengequest.model.Attribute;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreatureProperties;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreaturesTypes;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.db.utils.CreatureDBUtils;
import com.swinginpenguin.vmarinov.challengequest.sections.IntentExtraKeys;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterCreation extends Activity {

    //TODO getResource getString(R.string.editable_empty)
    private final String NAME_INPUT_DEFAULT_VALUE = "";

    private EditText nameInput;
    private Button createButton;
    private Button resetButton;
    private RadioGroup genderSelector;
    private RadioGroup classSelector;
    private CheckBox skipTutorial;

    private String _title = NAME_INPUT_DEFAULT_VALUE;
    private String _description;
    private int _gender = CreatureProperties.UNIDENTIFIED.getId();
    private int _race = CreatureProperties.UNIDENTIFIED.getId();
    private int _heroClass = CreaturesTypes.PLAYER.getId();
    private int _subClass = CreatureProperties.BRAND_NEW_HERO.getId();
    private List<String> attributeNames = new ArrayList<>();
    private CreatureDBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        dbUtils = new CreatureDBUtils(this);
        nameInput = (EditText) findViewById(R.id.hero_name_input);
        genderSelector = (RadioGroup) findViewById(R.id.hero_gender_selector);
        classSelector = (RadioGroup) findViewById(R.id.hero_race_selector);
        skipTutorial = (CheckBox) findViewById(R.id.skip_tutorial);

        attributeNames.add("Strength");
        attributeNames.add("Dexterity");
        attributeNames.add("Stamina");
        attributeNames.add("Wisdom");
        attributeNames.add("Intelligence");
        attributeNames.add("Charisma");
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
        skipTutorial.setChecked(false);
    }

    public void createHero(View button) {
        List<Creature> allCreatures = dbUtils.getAll();
        Log.d("CharacterCreation.createHero","Creatures: " + allCreatures.toString());

        if (_gender == CreatureProperties.UNIDENTIFIED.getId() ||
                _race == CreatureProperties.UNIDENTIFIED.getId() ||
                _title == NAME_INPUT_DEFAULT_VALUE) {
            //TODO first create check whether all inputs are filled/selected
        }
        //TODO populate all parameters correctly - Use ClassBaseUtils
        _title = nameInput.getText().toString();
        List<Attribute> attributes = new ArrayList<>();
        for (int i = CreatureProperties.STRENGTH.ordinal(); i <= CreatureProperties.CHARISMA.ordinal(); i++) {
            Map<Integer, Float> affectedStats = new HashMap<>();
            Attribute attribute = new Attribute(CreatureProperties.getNameById(i), 10, affectedStats);
            attributes.add(attribute);
        }
        Map<Integer, Float> stats = new HashMap<>();
        for (int i = CreatureProperties.HEALTH.getId(); i <= CreatureProperties.CRITICAL_HIT_DAMAGE.getId(); i++) {
            Float value = 1.5f;
            stats.put(i, value);
        }
        // TODO make it real!
        Ability first = new Ability("DoStuff", 1, "", 0f, 0, 0, 0, 0, true, true);
        List<Ability> abilities = new ArrayList<>();
        abilities.add(first);
        List<Integer> items = new ArrayList<>();
        items.add(0);
        List<Integer> loot = new ArrayList<>();
        loot.add(0);
        Creature playerHero = dbUtils.add(CreaturesTypes.PLAYER.getId(), _title, _description, 0, 1, _gender, _race,
                _heroClass, _subClass, attributes, stats, abilities, items, loot);
        Log.d("##@##", playerHero.toString());
        if (playerHero.getIdentity().getType() != ErrorCodes.DB_ERROR.getErrorCode()) {
            Intent createHeroIntent = new Intent(this, CharacterOverview.class);
            createHeroIntent.putExtra(IntentExtraKeys.PLAYER_HERO_EXTRA.getKey(), playerHero);
            createHeroIntent.putExtra(IntentExtraKeys.SKIP_TUTORIAL.getKey(), skipTutorial.isChecked());
            startActivity(createHeroIntent);
        } else {
            //TODO show error message and prompt user for different input.
            Log.e("CharacterCreation.createHero","Error creating hero. Reset Form and rty again!");
            Toast.makeText(this, "Error creating character!", Toast.LENGTH_LONG);
        }
    }

    public void clear(View view) {
        dbUtils.deleteAll();
    }
}
