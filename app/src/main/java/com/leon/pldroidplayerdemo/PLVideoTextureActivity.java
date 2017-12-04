package com.leon.pldroidplayerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

public class PLVideoTextureActivity extends AppCompatActivity{

    private PLVideoTextureView mPlVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_texture_view);
        mPlVideoView = findViewById(R.id.pl_video_texture_view);
        String path = "http://hc.yinyuetai.com/uploads/videos/common/2B40015FD4683805AAD2D7D35A80F606.mp4?sc=364e86c8a7f42de3&br=783&rd=Android";
        mPlVideoView.setVideoPath(path);
        mPlVideoView.setMediaController(new MediaController(this));
        mPlVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        mPlVideoView.setBufferingIndicator(findViewById(R.id.progress_bar));

        //旋转180度
//        mPlVideoView.setRotation(180);

        //镜像变换
//        mPlVideoView.setMirror(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlVideoView.stopPlayback();
    }
}
