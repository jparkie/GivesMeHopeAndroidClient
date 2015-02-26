package com.jparkie.givesmehope.presenters;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public interface VotePresenter {
    public void onActivityCreated(Bundle savedInstanceState);

    public void onSaveInstanceState(Bundle outState);

    public void onDestroy();

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    public boolean onOptionsItemSelected(MenuItem item);

    public void onRefresh();

    public void onVoteDown();

    public void onVoteUp();
}
