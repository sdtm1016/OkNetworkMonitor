package com.linkaipeng.oknetworkmonitor.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.R;
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl;
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linkaipeng on 2018/5/20.
 */

public class NetworkFeedAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<NetworkFeedModel> mNetworkFeedList;

    public NetworkFeedAdapter(Context context) {
        mContext = context;
        mNetworkFeedList = new ArrayList(DataPoolImpl.getInstance().getNetworkFeedMap().values());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_network_feed, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (!(viewHolder instanceof ItemViewHolder)) {
            return;
        }
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        final NetworkFeedModel networkFeedModel = mNetworkFeedList.get(i);
        itemViewHolder.mUrlTextView.setText("Url:" + networkFeedModel.getUrl());

        if (networkFeedModel.getStatus() >= 400 && networkFeedModel.getStatus() <= 600) {
            itemViewHolder.mStatusView.setBackgroundResource(R.drawable.red_rect);
            itemViewHolder.mStatusCodeTextView.setTextColor(mContext.getResources().getColor(R.color.red));
        } else {
            itemViewHolder.mStatusView.setBackgroundResource(R.drawable.green_rect);
            itemViewHolder.mStatusCodeTextView.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        itemViewHolder.mStatusCodeTextView.setText("StatusCode: "+networkFeedModel.getStatus());

        Format format = new DecimalFormat("#.00");
        String dataSize = "Size: " + format.format(networkFeedModel.getSize() * 0.001) + " KB";
        itemViewHolder.mSizeTextView.setText(dataSize);
        itemViewHolder.mCostTimeTextView.setText("CostTime: " + networkFeedModel.getCostTime() + " ms");
        itemViewHolder.mRootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkFeedDetailActivity.start(mContext, networkFeedModel.getRequestId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNetworkFeedList.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mRootLayout;
        private TextView mUrlTextView;
        private TextView mStatusCodeTextView;
        private TextView mSizeTextView;
        private TextView mCostTimeTextView;
        private View mStatusView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mRootLayout = itemView.findViewById(R.id.item_network_feed_root_layout);
            mUrlTextView = itemView.findViewById(R.id.item_network_feed_url_textView);
            mStatusCodeTextView = itemView.findViewById(R.id.item_network_feed_status_textView);
            mSizeTextView = itemView.findViewById(R.id.item_network_feed_size_textView);
            mCostTimeTextView = itemView.findViewById(R.id.item_network_feed_cost_time_textView);
            mStatusView = itemView.findViewById(R.id.item_network_feed_status_view);
        }
    }
}
