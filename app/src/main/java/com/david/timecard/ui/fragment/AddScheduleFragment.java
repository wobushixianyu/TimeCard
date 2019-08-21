package com.david.timecard.ui.fragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.david.timecard.ui.fragment.base.BaseFragment;
import com.david.timecard.R;
import com.david.timecard.utils.db.RecordDBHelper;
import com.david.timecard.widget.FlowRadioGroup;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

public class AddScheduleFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.category_rgp)
    FlowRadioGroup mCategoryRgp;
    @BindView(R.id.ll_container)
    LinearLayout mContainer;
    @BindView(R.id.commit_btn)
    Button mCommitBtn;
    private TextView mTvTime;
    private int mCategory;
    private int mFlag;
    private int mYear;
    private int mMonth;
    private int mDay;
    private EditText mEtDetail;
    public static final String BROADCAST_ACTION_ADD_RECORD = "broadcast_action_add_record";
    public static final String BROADCAST_ACTION_ADD_SCHEDULE = "broadcast_action_add_schedule";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_schedule, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mToolbar.setTitle("添加");
        mCategoryRgp.addView(getRadioButton("计划", 1, 1));
        mCategoryRgp.addView(getRadioButton("纪念日", 2, 2));
        initListener();
    }

    private void initListener() {
        mCategoryRgp.setOnCheckedChangeListener((radioGroup, checkId) -> {
            mCategory = checkId;
            mContainer.removeAllViews();
            switch (checkId) {
                case 1:
                    View mPlanView = LayoutInflater.from(getContext()).inflate(R.layout.edit_plan_layout, mContainer, false);
                    mContainer.addView(mPlanView);
                    mCommitBtn.setVisibility(View.GONE);
                    break;
                case 2:
                    View mRecordView = LayoutInflater.from(getContext()).inflate(R.layout.edit_record_layout, mContainer, false);
                    mContainer.addView(mRecordView);
                    mRecordViewConfig(mRecordView);
                    mCommitBtn.setVisibility(View.VISIBLE);
                    break;
            }
        });
        
        mCommitBtn.setOnClickListener(view -> {
            switch (mCategory){
                case 1:
                    break;
                case 2:
                    if (mFlag == 0 || TextUtils.isEmpty(mTvTime.getText()) || TextUtils.isEmpty(mEtDetail.getText())){
                        Toasty.warning(Objects.requireNonNull(getContext()),"请填写完所有内容").show();
                    }else {
                        doSubmitRecord();
                    }
                    break;
            }
        });
    }

    private void doSubmitRecord() {
        RecordDBHelper mRecordDBHelper = RecordDBHelper.getInstance();
        ContentValues recordValues = new ContentValues();
        recordValues.put("type",mFlag);
        recordValues.put("detail",mEtDetail.getText().toString());
        recordValues.put("time",mTvTime.getText().toString());
        mRecordDBHelper.insert(recordValues);
        mTvTime.setText("");
        mEtDetail.setText("");
        Toasty.success(Objects.requireNonNull(getContext()),"添加成功，请到[纪念日]中查看").show();

        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION_ADD_RECORD);
        getContext().sendBroadcast(intent);
    }

    private void mRecordViewConfig(View mRecordView) {
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        FlowRadioGroup mFlagGrp = mRecordView.findViewById(R.id.flag_grp);
        mFlagGrp.addView(getRadioButton("生日", 1, 1));
        mFlagGrp.addView(getRadioButton("恋爱纪念日", 2, 2));
        mFlagGrp.addView(getRadioButton("结婚纪念日", 3, 3));
        mFlagGrp.setOnCheckedChangeListener((radioGroup, checkId) -> mFlag = checkId);
        mTvTime = mRecordView.findViewById(R.id.tv_time);
        mEtDetail = mRecordView.findViewById(R.id.et_detail);
        mRecordView.findViewById(R.id.ll_time).setOnClickListener(view -> new DatePickerDialog(Objects.requireNonNull(getContext()),onDateSetListener,mYear,mMonth,mDay).show());
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            mTvTime.setText(days);
        }
    };

    /**
     * 动态生成radiobutton的一些属性的初始化操作
     * @param name radiobutton的名称
     * @param type radiobutton的类型
     * @return 一个实例化的radiobutton对象
     */
    @NonNull
    private RadioButton getRadioButton(String name, int type, int id) {
        RadioButton rbt = new RadioButton(getContext());
        rbt.setButtonDrawable(null);
        rbt.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.radio_button_selector_bg));
        //设置radiobutton在不同的状态下文本的颜色，先获取自定义的颜色状态列表，
        // 在res/color/button_text_color.xml下设置
        ColorStateList stateList = getContext().getColorStateList(R.color.button_text_color);
        rbt.setTextColor(stateList);
        rbt.setPadding(20, 10, 20, 10);
        rbt.setGravity(Gravity.CENTER);
        rbt.setText(name);
        rbt.setSingleLine();
        rbt.setTag(type);
        rbt.setId(id);
        return rbt;
    }
}
