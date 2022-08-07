package com.artemifyMusicStudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PopupCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.TransitionCommandCreator;
import com.useCase.PlaylistManager;
import com.useCase.SongManager;

import java.util.ArrayList;
import java.util.List;

public class PlaylistSongsDisplayPage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_songs_display_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateExitPageMenuItems();
        populateIdMenuMap();
        populateButtons();
        setUpPlaylistSongsInformation();
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        switch (creatorType) {
            case "TransitionCommandCreator":
                return new TransitionCommandCreator(this.activityServiceCache);
            default:
                return null;
        }
    }


    @Override
    protected void populateIdMenuMap() {
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList = new ArrayList<>(
                List.of(CommandItemType.EXIT_PAGE)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList);
    }

    @Override
    protected void populateExitPageMenuItems() {
        this.exitPageMenuItems.add(CommandItemType.EXIT_PAGE);
    }


    private void setUpPlaylistSongsInformation() {

        // primary setup
        int playlistID = Integer.parseInt(activityServiceCache.getTargetPlaylistID());

        // display playlist name
        TextView tv = findViewById(R.id.playlist_name_display);
        tv.setText(this.activityServiceCache.getPlaylistManager().getPlaylistName(playlistID));

        // show lists of songs
        showListSongs();
    }

    private void showListSongs() {
        // find the LinearLayout part that will display all songs in the playlist
        LinearLayout songLists = findViewById(R.id.list_songs_display);
        // remove all existing songs in the lists to prevent redundancy
        songLists.removeAllViews();

        SongManager songManager = activityServiceCache.getSongManager();
        PlaylistManager playlistManager = activityServiceCache.getPlaylistManager();
        int playlistID = Integer.parseInt(activityServiceCache.getTargetPlaylistID());
        ArrayList<Integer> allSongIDs = playlistManager.findPlaylist(playlistID).getSongs();
        for (Integer songID: allSongIDs) {
            String songName = songManager.getSongName(songID);
            String artistName = songManager.getSongArtist(songID);
            View oneSong = LayoutInflater.from(this).inflate(R.layout.one_song_display, null);
            // set song name for this song
            TextView songNameDisplay = oneSong.findViewById(R.id.display_song_name);
            songNameDisplay.setText(songName);
            // set artist name for this song
            TextView artistNameDisplay = oneSong.findViewById(R.id.display_artist_name);
            artistNameDisplay.setText(artistName);
            // put this song in PlaylistSongsDisplay
            songLists.addView(oneSong);
        }
        
    }

}