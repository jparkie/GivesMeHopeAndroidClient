package com.jparkie.givesmehope.views.activties;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.views.MainView;
import com.jparkie.givesmehope.views.fragments.HotFragment;
import com.jparkie.givesmehope.views.fragments.TrendingFragment;
import com.jparkie.givesmehope.views.fragments.VoteFragment;
import com.jparkie.givesmehope.views.widgets.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity implements MainView {
    public static final String TAG = MainActivity.class.getSimpleName();

    private MainPagerAdapter mMainPagerAdapter;

    @InjectView(R.id.mainToolbar)
    Toolbar mMainToolbar;
    @InjectView(R.id.mainSlidingTabs)
    SlidingTabLayout mMainSlidingTabLayout;
    @InjectView(R.id.mainViewPager)
    ViewPager mMainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initializeMainViewPager();
        initializeMainSlidingTabLayout();
        initializeMainToolbar();
    }

    private void actionSetTitle(int position) {
        switch (position) {
            case MainPagerAdapter.HOT_POSITION:
                getSupportActionBar().setTitle(R.string.fragment_title_hot);
                break;
            case MainPagerAdapter.TRENDING_POSITION:
                getSupportActionBar().setTitle(R.string.fragment_title_trending);
                break;
            case MainPagerAdapter.VOTE_POSITION:
                getSupportActionBar().setTitle(R.string.fragment_title_vote);
                break;
        }
    }

    // MainView:

    @Override
    public void initializeMainViewPager() {
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        mMainViewPager.setAdapter(mMainPagerAdapter);
        mMainViewPager.setOffscreenPageLimit(mMainPagerAdapter.getCount() - 1);
    }

    @Override
    public void initializeMainSlidingTabLayout() {
        mMainSlidingTabLayout.setCustomTabView(R.layout.tab_layout_main, android.R.id.text1);
        mMainSlidingTabLayout.setDistributeEvenly(true);
        mMainSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));
        mMainSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Do Nothing.
            }

            @Override
            public void onPageSelected(int position) {
                actionSetTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Do Nothing.
            }
        });

        mMainSlidingTabLayout.setViewPager(mMainViewPager);
    }

    @Override
    public void initializeMainToolbar() {
        mMainToolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance);
        mMainToolbar.setLogo(R.mipmap.ic_logo);

        setSupportActionBar(mMainToolbar);

        actionSetTitle(mMainViewPager.getCurrentItem());
    }

    // MainPagerAdapter:

    private class MainPagerAdapter extends FragmentPagerAdapter {
        public final static int HOT_POSITION = 0;
        public final static int TRENDING_POSITION = 1;
        public final static int VOTE_POSITION = 2;

        public MainPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case HOT_POSITION:
                    return new HotFragment();
                case TRENDING_POSITION:
                    return new TrendingFragment();
                case VOTE_POSITION:
                    return new VoteFragment();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable iconDrawable = null;

            switch (position) {
                case HOT_POSITION:
                    iconDrawable = getResources().getDrawable(R.mipmap.ic_whatshot_white_24dp);
                    break;
                case TRENDING_POSITION:
                    iconDrawable = getResources().getDrawable(R.mipmap.ic_trending_up_white_24dp);
                    break;
                case VOTE_POSITION:
                    iconDrawable = getResources().getDrawable(R.mipmap.ic_thumbs_up_down_white_24dp);
                    break;
            }

            if (iconDrawable == null) {
                return null;
            } else {
                iconDrawable.setBounds(0, 0, iconDrawable.getIntrinsicWidth(), iconDrawable.getIntrinsicHeight());

                SpannableString spannableString = new SpannableString(" ");
                ImageSpan imageSpan = new ImageSpan(iconDrawable, ImageSpan.ALIGN_BOTTOM);

                spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                return spannableString;
            }
        }
    }
}
