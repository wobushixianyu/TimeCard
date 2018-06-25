package com.david.timecard.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.david.timecard.R;
import com.david.timecard.bean.ScheduleBean;

public class ScheduleAdapter extends BaseQuickAdapter<ScheduleBean, BaseViewHolder> {

    private Drawable readBookBg;
    private Drawable icReadBook;
    private Drawable sportBg;
    private Drawable icSport;
    private Drawable haveRestBg;
    private Drawable icHaveRest;

    public ScheduleAdapter() {
        super(R.layout.item_timecard_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScheduleBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        RelativeLayout rlTypeBg = helper.getView(R.id.rl_type_bg);
        ImageView ivType = helper.getView(R.id.iv_type);
        TextView tvHintMethod = helper.getView(R.id.tv_hint_method);
        helper.setText(R.id.tv_content, item.getTask());
        helper.setText(R.id.tv_time, item.getTime());
        Context context = helper.itemView.getContext();
        /*switch (item.getType()) {
            case 1:
                if (readBookBg == null) {
                    readBookBg = context.getDrawable(R.drawable.read_book_bg);
                }
                if (icReadBook == null) {
                    icReadBook = context.getDrawable(R.drawable.ic_read_book);
                }
                rlTypeBg.setBackground(readBookBg);
                ivType.setImageDrawable(icReadBook);
                break;
            case 2:
                if (sportBg == null) {
                    sportBg = context.getDrawable(R.drawable.sport_bg);
                }
                if (icSport == null) {
                    icSport = context.getDrawable(R.drawable.ic_sport);
                }
                rlTypeBg.setBackground(sportBg);
                ivType.setImageDrawable(icSport);
                break;
            case 3:
                if (haveRestBg == null) {
                    haveRestBg = context.getDrawable(R.drawable.have_a_rest_bg);
                }
                if (icHaveRest == null) {
                    icHaveRest = context.getDrawable(R.drawable.ic_have_rest);
                }
                rlTypeBg.setBackground(haveRestBg);
                ivType.setImageDrawable(icHaveRest);
                break;
            default:
                break;
        }*/

        switch (item.getHintType()) {
            case 0:
                tvHintMethod.setText("提醒方式：无");
                break;
            case 1:
                tvHintMethod.setText("提醒方式：闹铃");
                break;
            case 2:
                tvHintMethod.setText("提醒方式：通知");
                break;
        }
    }
}
