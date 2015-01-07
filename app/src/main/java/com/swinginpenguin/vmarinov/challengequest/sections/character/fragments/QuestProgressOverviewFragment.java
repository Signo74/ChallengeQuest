package com.swinginpenguin.vmarinov.challengequest.sections.character.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.swinginpenguin.vmarinov.challengequest.R;
import com.swinginpenguin.vmarinov.challengequest.db.dao.CreaturesDAO;
import com.swinginpenguin.vmarinov.challengequest.db.utils.CampaignDBUtils;
import com.swinginpenguin.vmarinov.challengequest.db.utils.QuestDBUtils;
import com.swinginpenguin.vmarinov.challengequest.model.Campaign;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestProgressOverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestProgressOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class QuestProgressOverviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "playerHero";
    private static final String ARG_PARAM2 = "pageTitle";

    // TODO: Rename and change types of parameters
    private int pageIndex;
    private String pageTitle;
    private OnFragmentInteractionListener listener;


    private CampaignDBUtils campaignDbUtils;
    private QuestDBUtils questsDbUtils;
    private CreaturesDAO creaturesDAO;

    private static Creature playerHero;
    private static Boolean skipTutorial = false;

    private ProgressBar xpProgressBar;
    private TextView heroName;
    private TextView currentLevelXPLimit;
    private TextView nextLevelXPLimit;
    private ListView recentActivity;

    public QuestProgressOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hero Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestProgressOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestProgressOverviewFragment newInstance(Creature hero, String param2) {
        QuestProgressOverviewFragment fragment = new QuestProgressOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, hero);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerHero = (Creature) getArguments().getSerializable(ARG_PARAM1);
        }

        // init DB
        campaignDbUtils = new CampaignDBUtils(getActivity());
        questsDbUtils = new QuestDBUtils(getActivity());
        creaturesDAO = CreaturesDAO.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest_progress_overview, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View rootView = getView();
        xpProgressBar = (ProgressBar) rootView.findViewById(R.id.xpProgressBar);
        heroName = (TextView) rootView.findViewById(R.id.hero_name);
        currentLevelXPLimit = (TextView) rootView.findViewById(R.id.current_xp_value);
        nextLevelXPLimit = (TextView) rootView.findViewById(R.id.target_xp_value);
        recentActivity = (ListView) rootView.findViewById(R.id.recent_activity_list);
        initUI();
    }

    private void initUI() {
        heroName.setText(playerHero.getIdentity().getTitle());
        xpProgressBar.setProgress(playerHero.getExperience());

        Resources res = getResources();
        int[] levelLimits = res.getIntArray(R.array.levelLimits);
        currentLevelXPLimit.setText(levelLimits[playerHero.getLevel() -1]);
        nextLevelXPLimit.setText(levelLimits[playerHero.getLevel()]);

        if (playerHero.getLevel() == 1 && playerHero.getCurrentCampaigns() == null &&
                playerHero.getCompletedCampaigns() == null &&
                !skipTutorial) {
            // Initialize the tutorial campaign
            List<Quest> quests = new ArrayList<>();
            String[] questsInfo = res.getStringArray(R.array.tutorial_quests);
            for (int i=0 ; i < questsInfo.length / 2 ; i += 2){
                Quest newQuest = questsDbUtils.add(EntryTypes.QUEST.getId(), questsInfo[i], questsInfo[i+1],
                        null, R.integer.tutorial_quest_reward, 0, 0, 0);
                quests.add(newQuest);
            }
            // Adds the campaign to the db and returns a campaign object
            Campaign tutorial = campaignDbUtils.add(EntryTypes.CAMPAIGN.getId(), getString(R.string.tutorial_title),
                    getString(R.string.tutorial_description), R.integer.tutorial_reward, 0,
                    R.integer.tutorial_max_rank, 0l, 0, quests);
            List<Integer> currentCampaigns = new ArrayList<>();
            currentCampaigns.add(tutorial.getIdentity().getId());
            playerHero.setCurrentCampaigns(currentCampaigns);
            creaturesDAO.updateById(playerHero);
            // TODO add the tutorial to the list of recent activity.
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public Creature getPlayerHero() {
        return playerHero;
    }

    public void setPlayerHero(Creature playerHero) {
        this.playerHero = playerHero;
    }
}
