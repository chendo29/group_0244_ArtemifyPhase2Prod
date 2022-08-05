package com.artemifyMusicStudio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.TransitionCommandCreator;
import com.artemifyMusicStudio.controller.searchCommand.SearchResultContainer;
import com.presenters.LanguagePresenter;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultPage extends PageActivity {
    private SearchResultContainer searchResultContainer;
    private String searchType;
    private CommandItemType invokeSearchResultType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);

        // parse service cache, searchResult and searchType
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);
        this.searchResultContainer = (SearchResultContainer) getIntent().getSerializableExtra("searchResults");
        this.searchType = (String) getIntent().getSerializableExtra("searchType");
        String userInputSearchString = (String) getIntent().getSerializableExtra("userInputSearchString");
        populateInvokeSearchResultType();

        // get language presenter
        LanguagePresenter languagePresenter = this.activityServiceCache.getLanguagePresenter();

        // populate head message
        String headMsgEng = "These are all " + this.searchType + " with the name " +
                userInputSearchString + ":";
        String headMsg = languagePresenter.translateString(headMsgEng);
        TextView tv = findViewById(R.id.search_result_head_msg);
        tv.setText(headMsg);

        //populate search result buttons
        populateButtons();
    }

    @Override
    protected void populateButtons(){
        LinearLayout searchResultDisplay = findViewById(R.id.search_result_display_layout);
        HashMap<String, String> searchResultInfoMap = this.searchResultContainer.getSearchResultMap();
        ArrayList<String> resultTargetID = new ArrayList<>(searchResultInfoMap.keySet());
        int count = 0;
        TransitionCommandCreator transitionCommandCreator = new TransitionCommandCreator(this.activityServiceCache);
        for (String targetID : resultTargetID){
            String buttonDescription = searchResultInfoMap.get(targetID);
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setId(count);
            button.setText(buttonDescription);
            transitionCommandCreator.setTargetID(targetID);
            View.OnClickListener onClickListener = transitionCommandCreator.create(this.invokeSearchResultType);
            button.setOnClickListener(onClickListener);

            // populate the button to the layout
            searchResultDisplay.addView(button);
            count += 1;
        }

    }

    /**
     * The getSimpleOnClickCommandCreator will not be used in SearchResultPage. Therefore,
     * no implementation in here
     *
     */
    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        return null;
    }

    /**
     * The idMenuMap will not be used in SearchResultPage. No implementation here
     */
    @Override
    protected void populateIdMenuMap() {
    }

    /**
     * The menuCommandCreatorMap will not be used in SearchResultPage, no population in here
     */
    @Override
    protected void populateMenuCommandCreatorMap() {}

    @Override
    protected void populateExitPageMenuItems() {}

    private void populateInvokeSearchResultType(){
        switch (this.searchType){
            case "Song":
                this.invokeSearchResultType = CommandItemType.INVOKE_SONG_DISPLAY;
                break;
            case "Playlist":
                this.invokeSearchResultType = CommandItemType.INVOKE_PLAYLIST_DISPLAY;
                break;
            case "User":
                this.invokeSearchResultType = CommandItemType.INVOKE_USER_DISPLAY;
                break;
            default:
                this.invokeSearchResultType = null;
        }
    }

}