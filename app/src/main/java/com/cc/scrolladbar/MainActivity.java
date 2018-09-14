package com.cc.scrolladbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshichao on 2018/8/16
 * QQ:1169380200
 */

public class MainActivity extends AppCompatActivity {

    private static final int SCROLL_AD = 0;//会员轮播广告
    public static final int DEFAULT_SCROLL_INTERVAL = 3000;//会员轮播广告间隔时间
    public static final float DEFAULT_SCROLL_ANIMATION_TIME = 500;//会员轮播广告动画时长

    private RecyclerView mRvAd;
    private AdAdapter mAdapter;
    private List<AdModel> mAdList;
    private AdHandler mHandler;
    private int nowScrollPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        scrollVipAdOnce(nowScrollPosition);//防止滑动一半切到别的页面使滑动完成
        if (mHandler != null) {
            sendScrollMessage(DEFAULT_SCROLL_INTERVAL);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mHandler != null) {
            mHandler.removeMessages(SCROLL_AD);
        }
    }

    private void initList() {
        mAdList = new ArrayList<>();
        mAdList.add(new AdModel("第一条广告标题", "我是第一条广告的内容哦~"));
        mAdList.add(new AdModel("第二条广告标题", "我是第二条广告的内容哦~"));
        mAdList.add(new AdModel("第三条广告标题", "我是第三条广告的内容哦~"));

        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
                    // 为了平滑滑动返回：滑过1px时经历的时间(ms)。
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        //这里必须返回一个正数，否则会出错
                        float duration = DEFAULT_SCROLL_ANIMATION_TIME / displayMetrics.densityDpi;
                        return duration > 0 ? duration : 1;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };

        mRvAd = (RecyclerView) findViewById(R.id.rv_ad);
        mRvAd.setLayoutManager(manager);
        mAdapter = new AdAdapter(this, mAdList);
        mRvAd.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //点击跳转到指定广告页
            }
        });
        mHandler = new AdHandler();
        sendScrollMessage(DEFAULT_SCROLL_INTERVAL);
    }

    private void scrollVipAdOnce(int position) {
        if (mAdList != null && mAdList.size() > 1) {
            //平滑滑动到指定位置
            mRvAd.smoothScrollToPosition(position);
        }
    }

    private void sendScrollMessage(long delayMillis) {
        mHandler.removeMessages(SCROLL_AD);
        mHandler.sendEmptyMessageDelayed(SCROLL_AD, delayMillis);
    }

    private class AdHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_AD:
                    nowScrollPosition++;
                    scrollVipAdOnce(nowScrollPosition);
                    sendScrollMessage(DEFAULT_SCROLL_INTERVAL);
                    break;
                default:
                    break;
            }
        }
    }

}
