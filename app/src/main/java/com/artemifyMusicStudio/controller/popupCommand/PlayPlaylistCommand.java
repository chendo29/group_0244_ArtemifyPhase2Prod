package com.artemifyMusicStudio.controller.popupCommand;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.QueueDisplayPage;
import com.artemifyMusicStudio.UserDisplayPage;
import com.entity.Playlist;
import com.presenters.LanguagePresenter;
import com.useCase.PlaylistManager;
import com.useCase.Queue;

import java.util.ArrayList;

/**
 * A PlayPlaylistCommand object to handle the user play all songs in playlist request
 */

public class PlayPlaylistCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;

    private final LanguagePresenter languagePresenter;
    private final String playlistID;

    /**
     * Constructor for PlayPlaylistCommand.
     *
     * @param activityServiceCache            a PageCreator Object
     * @param languagePresenter      a LanguagePresenter Object
     * @param playlistServiceManager a UserAccess object
     * @param playlistID a String represents the playlist's id
     */
    public PlayPlaylistCommand(ActivityServiceCache activityServiceCache, LanguagePresenter languagePresenter,
                               PlaylistManager playlistServiceManager, String playlistID) {
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
        this.playlistID = playlistID;
    }


    /**
     * Execute PlayPlaylistCommand to add all songs in playlist to top of queue.
     * Will add some code to invoke QueueDisplayPage in phase 2.
     */
    @Override
    public void onClick(View view) {
        Queue queueManager = this.activityServiceCache.getQueueManager();
        Playlist currPlaylist = this.activityServiceCache.getPlaylistManager().findPlaylist(Integer.parseInt(this.playlistID));
        ArrayList<Integer> allSongsID = currPlaylist.getSongs();
        int counter = 0;
        for(Integer songID : allSongsID){
            queueManager.addToQueue(songID, counter);
            counter++;
        }
        String addedMsg =  this.languagePresenter.translateString("Added to your playing queue");
        Toast.makeText(this.activityServiceCache.getCurrentPageActivity(),
                addedMsg, Toast.LENGTH_LONG).show();
        // Takes user to QueueDisplayPage
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, QueueDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }

}
