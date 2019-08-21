package com.david.timecard.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.timecard.bean.ScheduleBean;
import com.david.timecard.ui.fragment.base.BaseFragment;
import com.david.timecard.R;
import com.david.timecard.adapter.ScheduleAdapter;
import com.david.timecard.adapter.SelectorAdapter;
import com.david.timecard.bean.DateBean;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends BaseFragment implements SelectorAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_selector)
    RecyclerView mSelector;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        mToolbar.setTitle(getString(R.string.app_name));
        initSelector();
        return view;
    }

    private void initSelector() {
        DateBean monday = new DateBean("周一", 0);
        DateBean tuesday = new DateBean("周二", 1);
        DateBean wednesday = new DateBean("周三", 2);
        DateBean thursday = new DateBean("周四", 3);
        DateBean friday = new DateBean("周五", 4);
        DateBean saturday = new DateBean("周六", 5);
        DateBean sunday = new DateBean("周日", 6);
        List<DateBean> dateBeans = Arrays.asList(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
        SelectorAdapter selectorAdapter = new SelectorAdapter();
        selectorAdapter.addData(dateBeans);
        selectorAdapter.setOnItemClickListener(this);
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);//weekday=1，当天是周日；weekday=2，当天是周一；...;weekday=7，当天是周六
        position = weekday == 1 ? 6 : weekday - 2;
        selectorAdapter.setDefaultOptions(position);//0~6 对应周一到周日
        getDataSchedule(position);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        mSelector.setLayoutManager(layoutManager);
        mSelector.setAdapter(selectorAdapter);
    }

    private void getDataSchedule(int position) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        ScheduleAdapter mAdapter = new ScheduleAdapter();
        mRecyclerView.setAdapter(mAdapter);
        ScheduleBean bean01 = new ScheduleBean(1, 1, 1, getString(R.string.text_read_title), "读20页书，背20个英语单词", 1, "20:30");
        ScheduleBean bean02 = new ScheduleBean(2, 1, 2, getString(R.string.text_sport_title), "跑步1000米", 2, "19:30");
        ScheduleBean bean03 = new ScheduleBean(3, 1, 3, getString(R.string.text_have_a_rest_title), "晚安", 0, "23:00");
        List<ScheduleBean> scheduleBeans = Arrays.asList(bean01, bean02, bean03);
        mAdapter.addData(scheduleBeans);
    }

    @Override
    public void onItemClick(int tag) {
        position = tag;
        getDataSchedule(tag);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
