package com.liaobb.evernote.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.TextUtils;

import com.jenzz.materialpreference.CheckBoxPreference;
import com.jenzz.materialpreference.Preference;
import com.jenzz.materialpreference.SwitchPreference;
import com.liaobb.evernote.R;
import com.liaobb.evernote.bean.Note;
import com.liaobb.evernote.bean.NoteType;
import com.liaobb.evernote.common.NoteUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liaobb on 2015/7/8.
 */
public class SettingFragment extends PreferenceFragment {
    private Context context;

    private SwitchPreference rightHandModeSwitch;
    private CheckBoxPreference cardLayoutPreference;
    private Preference feedbackPreference;
    private Preference giveFavorPreference;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean rightHandMode;
    private boolean cardLayout;

    public SettingFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        getPreferenceManager().setSharedPreferencesName(SettingActivity.PREFERENCE_FILE_NAME);

        context = getActivity();
        sharedPreferences = context.getSharedPreferences(SettingActivity.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        rightHandMode = sharedPreferences.getBoolean(getString(R.string.right_hand_mode_key), false);
        rightHandModeSwitch = (SwitchPreference) findPreference(getString(R.string.right_hand_mode_key));
        rightHandModeSwitch.setChecked(rightHandMode);

        cardLayout = sharedPreferences.getBoolean(getString(R.string.card_note_item_layout_key), false);
        cardLayoutPreference = (CheckBoxPreference) findPreference(getString(R.string.card_note_item_layout_key));
        cardLayoutPreference.setChecked(cardLayout);

        feedbackPreference = (Preference) findPreference(getString(R.string.advice_feedback_key));
        giveFavorPreference = (Preference) findPreference(getString(R.string.give_favor_key));

        initFeedbackPreference();

    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, android.preference.Preference preference) {
        if (preference == null)
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        String key = preference.getKey();
        if (TextUtils.equals(key, getString(R.string.right_hand_mode_key))) {
            rightHandMode = !rightHandMode;
            editor.putBoolean(getString(R.string.right_hand_mode_key), rightHandMode).commit();
        } else if (TextUtils.equals(key, getString(R.string.card_note_item_layout_key))) {
            cardLayout = !cardLayout;
            editor.putBoolean(getString(R.string.card_note_item_layout_key), cardLayout).commit();
        } else if (TextUtils.equals(key, getString(R.string.clear_database_key))) {
            DataSupport.deleteAll(Note.class);
            EventBus.getDefault().post(NoteUtils.NOTE_CLEARALL_EVENT);
        } else if (TextUtils.equals(key, getString(R.string.restore_defaultSettings_key))) {
            DataSupport.deleteAll(NoteType.class);
            EventBus.getDefault().post(NoteUtils.NOTE_RESTORY_DEFAULT_EVENT);
            editor.putBoolean(getString(R.string.first_init_app_key), true).commit();
        } else if (TextUtils.equals(key, getString(R.string.give_favor_key))){
            giveFavor();
        } else if(TextUtils.equals(key, getString(R.string.sync_note_key))){

        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void initFeedbackPreference() {
        Uri uri = Uri.parse("mailto:1250440341@qq.com");
        final Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (infos == null || infos.size() <= 0) {
            feedbackPreference.setSummary(getString(R.string.no_email_app_tip));
            return;
        }
        feedbackPreference.setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.preference.Preference preference) {
                startActivity(intent);
                return true;
            }
        });
    }

    private void giveFavor(){
        try{
            Uri uri = Uri.parse("market://details?id="+ getActivity().getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View listView = view.findViewById(android.R.id.list);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setVerticalScrollBarEnabled(false);
    }*/
}
