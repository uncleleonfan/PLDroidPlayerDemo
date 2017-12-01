package com.leon.pldroidplayerdemo;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;


public class VideoView extends FrameLayout implements View.OnClickListener{

    private static final String TAG = "VideoView";

    private static final int DELAY = 200;
    private static final int HIDE_DELAY = 5000;

    private PLVideoTextureView mTextureView;
    private ImageView mPlay;
    private TextView mCurrentTime;
    private TextView mDuration;
    private SeekBar mSeekBar;
    private LinearLayout mBottomLayout;
    private ProgressBar mProgressBar;

    private Handler mHandler = new Handler();

    public VideoView(@NonNull Context context) {
        this(context, null);
    }

    public VideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_video, this);
        mTextureView = findViewById(R.id.pl_video_texture_view);
        mCurrentTime = findViewById(R.id.current);
        mDuration = findViewById(R.id.duration);
        mBottomLayout = findViewById(R.id.bottom_layout);
        mProgressBar = findViewById(R.id.progress_bar);
        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        mTextureView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_FIT_PARENT);
        mTextureView.setOnPreparedListener(mOnPreparedListener);
        mTextureView.setOnCompletionListener(mOnCompletionListener);
        mPlay = findViewById(R.id.play);
        mPlay.setOnClickListener(this);

        mTextureView.start();
    }



    private PLMediaPlayer.OnPreparedListener mOnPreparedListener = new PLMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(PLMediaPlayer plMediaPlayer, int i) {
            long duration = mTextureView.getDuration();
            mDuration.setText(TimeUtils.stringForTime(duration));
            mSeekBar.setMax((int) duration);
            mProgressBar.setVisibility(View.GONE);
            mHandler.postDelayed(mTicker, DELAY);

            mPlay.setVisibility(View.VISIBLE);
            mBottomLayout.setVisibility(View.VISIBLE);
            mHandler.postDelayed(mHider, HIDE_DELAY);
        }
    };

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            mPlay.setImageResource(R.drawable.play_selector);
        }
    };

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mTextureView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mTextureView.pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mTextureView.start();
        }
    };

    public void setVideoPath(String path) {
        mTextureView.setVideoPath(path);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                togglePlay();
                break;
        }
    }

    private void togglePlay() {
        if (mTextureView.isPlaying()) {
            mTextureView.pause();
            mPlay.setImageResource(R.drawable.play_selector);
            mHandler.removeCallbacks(mHider);

        } else {
            mTextureView.start();
            mPlay.setVisibility(View.GONE);
            mBottomLayout.setVisibility(View.GONE);
            mPlay.setImageResource(R.drawable.pause_selector);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mProgressBar.getVisibility() == View.GONE) {
                    mPlay.setVisibility(VISIBLE);
                    mBottomLayout.setVisibility(VISIBLE);
                    mHandler.postDelayed(mHider, HIDE_DELAY);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private Runnable mTicker = new Runnable() {

        @Override
        public void run() {
            Log.d(TAG, "run: ");
            mCurrentTime.setText(TimeUtils.stringForTime(mTextureView.getCurrentPosition()));
            mSeekBar.setProgress((int) mTextureView.getCurrentPosition());
            mSeekBar.postDelayed(mTicker, DELAY);
        }
    };

    private Runnable mHider = new Runnable() {
        @Override
        public void run() {
            mPlay.setVisibility(View.GONE);
            mBottomLayout.setVisibility(View.GONE);
        }
    };
}
