package com.david.timecard.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.david.timecard.R;
import com.david.timecard.bean.DateBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private List<DateBean> dateBeans;
    private int selectPosition = -1;

    public SelectorAdapter() {
        if (dateBeans == null) {
            dateBeans = new ArrayList<>();
        }
    }

    public void addData(List<DateBean> dateBeans) {
        this.dateBeans = dateBeans;
        selectPosition = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selector_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateBean dateBean = dateBeans.get(position);
        holder.mTvOptions.setText(dateBean.getName());
        holder.itemView.setOnClickListener(getOnLickListener(position));

        if (selectPosition == position) {
            holder.mTvOptions.setTextColor(Color.parseColor("#0fb7f7"));
            holder.mTvOptions.setBackgroundColor(Color.WHITE);
        } else {
            holder.mTvOptions.setTextColor(Color.WHITE);
            holder.mTvOptions.setBackgroundColor(Color.parseColor("#0fb7f7"));
        }
    }

    @Override
    public int getItemCount() {
        return dateBeans == null ? 0 : dateBeans.size();
    }

    public void setDefaultOptions(int position){
        if (dateBeans != null && dateBeans.size() > position && position >= 0){
            selectPosition = position;
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_options)
        TextView mTvOptions;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private View.OnClickListener getOnLickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && v != null) {
                    selectPosition = position;
                    notifyDataSetChanged();
                    listener.onItemClick(dateBeans.get(position).getTag());
                }
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(int tag);
    }
}
