package com.artemifyMusicStudio.controller.pageNavigationCommand;

import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageType;

/**
 * A ProfileAndSetting command to handle user's request for Profile&Setting
 */
public class ProfileAndSettingCommand extends PageNavigationCommand {

    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor of ProfileAndSettingCommand
     * @param activityServiceCache a PageCreator object
     */
    public ProfileAndSettingCommand(ActivityServiceCache activityServiceCache){
        super();
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * Execute the ProfileAndSettingCommand by invoking the MyAccountPage
     */
    public void execute(){
        PageActivity profileAndSettingPage = activityServiceCache.creat(PageType.PROFILE_AND_SETTING_PAGE);
        profileAndSettingPage.invokes();
    }
}