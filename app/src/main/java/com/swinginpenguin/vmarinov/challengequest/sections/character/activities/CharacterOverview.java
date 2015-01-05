package com.swinginpenguin.vmarinov.challengequest.sections.character.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.swinginpenguin.vmarinov.challengequest.R;
import com.swinginpenguin.vmarinov.challengequest.db.utils.CampaignDBUtils;
import com.swinginpenguin.vmarinov.challengequest.db.utils.QuestDBUtils;
import com.swinginpenguin.vmarinov.challengequest.model.Campaign;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryTypes;
import com.swinginpenguin.vmarinov.challengequest.sections.IntentExtraKeys;
import com.swinginpenguin.vmarinov.challengequest.sections.character.fragments.CharacterFragment;
import com.swinginpenguin.vmarinov.challengequest.sections.character.fragments.QuestsListFragment;
import com.swinginpenguin.vmarinov.challengequest.sections.character.fragments.QuestProgressOverviewFragment;
import com.swinginpenguin.vmarinov.challengequest.sections.questdetails.QuestOverviewActivity;

public class CharacterOverview
    extends Activity
    implements QuestsListFragment.OnFragmentInteractionListener,
               CharacterFragment.OnFragmentInteractionListener,
               QuestProgressOverviewFragment.OnFragmentInteractionListener{


    // Defines the index of the page in which we'll show a character overview.
    private final static int CHARACTER_OVERVIEW_PAGE = 0;
    // Defines the index of the starting page in the ViewPager
    private final static int PROGRESS_OVERVIEW = 1;
    // Defines the index of the page in which we'll show the quest list
    private final static int LIST_PAGE = 3;

    private int pageCount;
    private CampaignDBUtils campaignDbUtils;
    private QuestDBUtils questsDbUtils;


    private Creature playerHero;
    private ProgressBar xpProgressBar;
    private TextView heroName;
    private TextView currentLevelXPLimit;
    private TextView nextLevelXPLimit;
    private ListView recentActivity;
    private Boolean skipTutorial = false;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_overview);
        Intent invokerIntent = getIntent();
        playerHero = (Creature) invokerIntent.getSerializableExtra(IntentExtraKeys.PLAYER_HERO_EXTRA.getKey());
        skipTutorial = invokerIntent.getBooleanArrayExtra(IntentExtraKeys.SKIP_TUTORIAL.getKey())[0];

        // Init view elements
        xpProgressBar = (ProgressBar) findViewById(R.id.xpProgressBar);
        heroName = (TextView) findViewById(R.id.hero_name);
        // TODO refactor the current xp value to the previous level limit
        currentLevelXPLimit = (TextView) findViewById(R.id.current_xp_value);
        nextLevelXPLimit = (TextView) findViewById(R.id.target_xp_value);
        recentActivity = (ListView) findViewById(R.id.campaignList);

        // init DB
        campaignDbUtils = new CampaignDBUtils(this);
        questsDbUtils = new QuestDBUtils(this);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(PROGRESS_OVERVIEW);
        pageCount = R.integer.character_overview_pages_count;

        initUI(playerHero);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.character_overview, menu);
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

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO change Character and Progress Fragments to send different parameters and therefore
        // different handlers!
    }

    @Override
    public void onFragmentInteraction(String id) {
        Intent listItemClickedIntent = new Intent(this, QuestOverviewActivity.class);
        listItemClickedIntent.putExtra("targetId", id);
        startActivity(listItemClickedIntent);
    }

    public void onHeroButtonClicked(View button) {
        mViewPager.setCurrentItem(CHARACTER_OVERVIEW_PAGE);
    }

    private void initUI(Creature hero) {
        heroName.setText(hero.getIdentity().getTitle());
        xpProgressBar.setProgress(hero.getExperience());

        Resources res = getResources();
        int[] levelLimits = res.getIntArray(R.array.levelLimits);
        currentLevelXPLimit.setText(levelLimits[hero.getLevel() -1]);
        nextLevelXPLimit.setText(levelLimits[hero.getLevel()]);

        if (hero.getLevel() == 1 && hero.getCurrentCampaigns() == null &&
                                    hero.getCompletedCampaigns() == null &&
                                    !skipTutorial) {
            // Initialize the tutorial campaign
            List<Quest> quests = new ArrayList<>();
            String[] questsInfo = res.getStringArray(R.array.tutorial_quests);
            for (int i=0 ; i < questsInfo.length / 2 ; i += 2){
                Quest newQuest = questsDbUtils.add(EntryTypes.QUEST.getId(), questsInfo[i], questsInfo[i+1],
                        null, R.integer.tutorial_quest_reward, 0, 0, 0);
                quests.add(newQuest);
            }
            Campaign tutorial = campaignDbUtils.add(EntryTypes.CAMPAIGN.getId(), getString(R.string.tutorial_title),
                                getString(R.string.tutorial_description), R.integer.tutorial_reward, 0,
                                R.integer.tutorial_max_rank, 0l, 0, quests);
            List<Integer> currentCampaigns = new ArrayList<>();
            currentCampaigns.add(tutorial.getIdentity().getId());
            hero.setCurrentCampaigns(currentCampaigns);
            // TODO add the tutorial to the list of recent activity.
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment pageFragment = null;
            String str1 = "";
            String str2 = "";
            switch (position) {
                case CHARACTER_OVERVIEW_PAGE:
                    pageFragment = CharacterFragment.newInstance(str1, str2);
                    break;
                case PROGRESS_OVERVIEW:
                    pageFragment = QuestProgressOverviewFragment.newInstance(str1, str2);
                    break;
                case LIST_PAGE:
                    pageFragment = QuestsListFragment.newInstance(str1, str2);
                    break;
                default:
                    pageFragment = QuestsListFragment.newInstance(str1, str2);
                    break;
            }
            return pageFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case CHARACTER_OVERVIEW_PAGE:
                    return getString(R.string.common_label_character_overview).toUpperCase(l);
                case PROGRESS_OVERVIEW:
                    return getString(R.string.progress_title).toUpperCase(l);
                case LIST_PAGE:
                    return getString(R.string.quest_list_title).toUpperCase(l);
            }
            return null;
        }
    }
}
