package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.commandCreator.TransitionCommandCreator;

import java.util.ArrayList;
import java.util.List;

public class RegularUserHomePage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_user_home_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateExitPageMenuItems();
        populateIdMenuMap();
        populateButtons();
        // set user name
        TextView tv = findViewById(R.id.user_name);
        tv.setText(activityServiceCache.getUserID());
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList = new ArrayList<>(
                List.of(
                        CommandItemType.INVOKE_SEARCH, CommandItemType.ENABLE_ADMIN_MODE,
                        CommandItemType.INVOKE_SONG_UPLOAD,
                        CommandItemType.INVOKE_CREATE_NEW_PLAYLIST,
                        CommandItemType.PROFILE_AND_SETTING,
                        CommandItemType.LOG_OUT)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList);
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        switch (creatorType){
            case "TransitionCommandCreator":
                return new TransitionCommandCreator(this.activityServiceCache);
            default:
                return null;
        }
    }

    @Override
    protected void populateExitPageMenuItems() {
        this.exitPageMenuItems.add(CommandItemType.LOG_OUT);
        this.exitPageMenuItems.add(CommandItemType.ENABLE_ADMIN_MODE);
    }

    @Override
    protected void populateIdMenuMap(){
        idMenuItemMap.put(CommandItemType.INVOKE_SEARCH, R.id.search);
        idMenuItemMap.put(CommandItemType.PROFILE_AND_SETTING, R.id.profile_setting);
        idMenuItemMap.put(CommandItemType.INVOKE_CREATE_NEW_PLAYLIST, R.id.create_playlist);
        idMenuItemMap.put(CommandItemType.INVOKE_SONG_UPLOAD, R.id.upload_song);
        idMenuItemMap.put(CommandItemType.ENABLE_ADMIN_MODE, R.id.enable_admin);
        idMenuItemMap.put(CommandItemType.LOG_OUT, R.id.log_out);
    }
}