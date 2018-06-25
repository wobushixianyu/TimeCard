package com.david.timecard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.david.timecard.fragment.ScheduleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private FragmentTransaction transaction;
    private ScheduleFragment mScheduleFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

        setFragment(PageType.schedule);
    }

    private void setFragment(PageType type) {
        transaction = fragmentManager.beginTransaction();
        if (type == PageType.schedule) {
            if (mScheduleFragment == null) {
                mScheduleFragment = new ScheduleFragment();
                transaction.add(R.id.fragment_container, mScheduleFragment);
            }
            hideFragment();
            transaction.show(mScheduleFragment);
        } else if (type == PageType.add) {
            //
        } else if (type == PageType.look) {

        }
        transaction.commit();
    }

    private void hideFragment() {
        if (mScheduleFragment != null) transaction.hide(mScheduleFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_schedule:
                setFragment(PageType.schedule);
                return true;
            case R.id.navigation_add:
                return true;
            case R.id.navigation_look:
                return true;
        }
        return false;
    }

    enum PageType {
        schedule, add, look
    }
}
