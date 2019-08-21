package com.david.timecard.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.david.timecard.R;
import com.david.timecard.adapter.RecordAdapter;
import com.david.timecard.bean.RecordBean;
import com.david.timecard.ui.fragment.base.BaseFragment;
import com.david.timecard.utils.db.RecordDBHelper;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecordFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private RecordAdapter mAdapter;
    private AddRecordMessageReceiver messageReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_layout, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @SuppressLint("SimpleDateFormat")
    private void initView() {
        mToolbar.setTitle(getString(R.string.title_record));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecordAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.postDelayed(this::getData, 500);

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            new AlertDialog.Builder(getContext()).setCancelable(false)
                    .setMessage("是否删除该条数据?")
                    .setPositiveButton("确定", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        RecordBean item = (RecordBean) adapter.getItem(position);
                        if (item != null) {
                            RecordDBHelper.getInstance().delete(item.getId());
                            adapter.remove(position);
                        }
                    }).setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            return false;
        });

        messageReceiver = new AddRecordMessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AddScheduleFragment.BROADCAST_ACTION_ADD_RECORD);
        Objects.requireNonNull(getContext()).registerReceiver(messageReceiver, intentFilter);
    }

    private void getData() {
        mSwipeRefreshLayout.setRefreshing(false);
        RecordDBHelper mDbHelper = RecordDBHelper.getInstance();
        List<RecordBean> recordBeans = mDbHelper.rawQuery(1);
        if (recordBeans != null && recordBeans.size() != 0) {
            mAdapter.setNewData(recordBeans);
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(this::getData, 500);
    }

    private class AddRecordMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) return;
            if (intent.getAction().equals(AddScheduleFragment.BROADCAST_ACTION_ADD_RECORD)) {
                getData();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getContext()).unregisterReceiver(messageReceiver);
    }
}
