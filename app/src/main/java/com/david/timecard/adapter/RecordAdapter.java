package com.david.timecard.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.david.timecard.R;
import com.david.timecard.bean.RecordBean;
import com.david.timecard.utils.TCUtils;
import com.david.timecard.widget.CircleImageView;
import java.text.ParseException;

public class RecordAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {

    public RecordAdapter() {
        super(R.layout.item_record_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {
        try {
            CircleImageView mImageView = helper.getView(R.id.img_logo);
            switch (item.getType()){
                case 1:
                    mImageView.setImageResource(R.mipmap.ic_flag_birthday);
                    break;
                case 2:
                    mImageView.setImageResource(R.mipmap.ic_flag_love_together);
                    break;
                case 3:
                    mImageView.setImageResource(R.mipmap.ic_flag_marry);
                    break;
            }
            int dateSpace = TCUtils.getDateSpace(item.getTime());
            helper.setText(R.id.tv_count_down, dateSpace > 0 ? "已经"+dateSpace+"天了" : "还有"+Math.abs(dateSpace)+"天");
            helper.setText(R.id.tv_detail,item.getDetail());
            helper.setText(R.id.tv_time,item.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
