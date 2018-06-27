package com.david.timecard.activity;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.RadioButton;

import com.david.timecard.BaseActivity;
import com.david.timecard.R;
import com.david.timecard.widget.FlowRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddScheduleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.add_flag)
    AppCompatTextView mAddFlag;
    @BindView(R.id.flow_rgp)
    FlowRadioGroup mFlowRgp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ButterKnife.bind(this);
        mToolbar.setTitle("新增计划");

        initView();
    }

    private void initView() {
        mFlowRgp.addView(getRadioButton("运动", 1));
        mFlowRgp.addView(getRadioButton("阅读", 2));
        mFlowRgp.addView(getRadioButton("睡觉", 3));
    }

    /**
     * 动态生成radiobutton的一些属性的初始化操作
     *
     * @param name radiobutton的名称
     * @param type   radiobutton的类型
     * @return 一个实例化的radiobutton对象
     */
    @NonNull
    private RadioButton getRadioButton(String name, int type) {
        RadioButton rbt = new RadioButton(this);
        rbt.setButtonDrawable(null);
        rbt.setBackground(getDrawable(R.drawable.radio_button_selector_bg));
        //设置radiobutton在不同的状态下文本的颜色，先获取自定义的颜色状态列表，
        // 在res/color/button_text_color.xml下设置
        ColorStateList stateList = getColorStateList(R.color.button_text_color);
        rbt.setTextColor(stateList);
        rbt.setPadding(20, 10, 20, 10);
        rbt.setGravity(Gravity.CENTER);
        rbt.setText(name);
        rbt.setSingleLine();
        rbt.setTag(type);
        return rbt;
    }
}
