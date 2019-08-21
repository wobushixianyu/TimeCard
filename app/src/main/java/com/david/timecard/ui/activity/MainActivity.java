package com.david.timecard.ui.activity;

import android.os.Bundle;
import android.os.Process;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.david.timecard.ui.activity.base.BaseActivity;
import com.david.timecard.R;
import com.david.timecard.ui.fragment.AddScheduleFragment;
import com.david.timecard.ui.fragment.RecordFragment;
import com.david.timecard.ui.fragment.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private FragmentTransaction transaction;
    private ScheduleFragment mScheduleFragment;
    private AddScheduleFragment mAddScheduleFragment;
    private RecordFragment mRecordFragment;
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
            if (mAddScheduleFragment == null) {
                mAddScheduleFragment = new AddScheduleFragment();
                transaction.add(R.id.fragment_container, mAddScheduleFragment);
            }
            hideFragment();
            transaction.show(mAddScheduleFragment);
        } else if (type == PageType.record) {
            if (mRecordFragment == null) {
                mRecordFragment = new RecordFragment();
                transaction.add(R.id.fragment_container, mRecordFragment);
            }
            hideFragment();
            transaction.show(mRecordFragment);
        }
        transaction.commit();
    }

    private void hideFragment() {
        if (mScheduleFragment != null) transaction.hide(mScheduleFragment);
        if (mAddScheduleFragment != null) transaction.hide(mAddScheduleFragment);
        if (mRecordFragment != null) transaction.hide(mRecordFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_schedule:
                setFragment(PageType.schedule);
                return true;
            case R.id.navigation_add:
                setFragment(PageType.add);
                return true;
            case R.id.navigation_record:
                setFragment(PageType.record);
                return true;
        }
        return false;
    }

    enum PageType {
        schedule, add, record
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            ActivityCompat.finishAffinity(this);
            Process.killProcess(Process.myPid());
            return;
        } else {
            Snackbar.make(navigation, "再次点击[返回]键退出程序", Snackbar.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}
