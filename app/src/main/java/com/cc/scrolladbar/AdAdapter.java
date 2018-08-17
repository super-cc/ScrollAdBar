package com.cc.scrolladbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guoshichao on 2018/8/16
 */

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.ViewHolder> {

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private LayoutInflater mInflater;
    private List<AdModel> mDataList;

    public AdAdapter(Context context, List<AdModel> datas) {
        this.mContext = context;
        mDataList = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_ad, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int p) {
        if (mDataList == null || mDataList.size() ==0){
            return;
        }
        if (holder != null) {
            final int position = p % mDataList.size();

            holder.mTvTitle.setText(mDataList.get(position).title);
            holder.mTvContent.setText(mDataList.get(position).content);
            holder.mIvIcon.setImageResource(R.drawable.icon);

            holder.viewRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View viewRoot;
        public TextView mTvTitle;
        public TextView mTvContent;
        public ImageView mIvIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            viewRoot = itemView.findViewById(R.id.layout);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
        }
    }

    /**
     * RecyclerView的item点击监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}