package com.dream.share;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.yapp.utils.share.ShareApiEnum;
import org.yapp.utils.share.ShareConfig;
import org.yapp.utils.share.ShareTypeEnum;
import org.yapp.utils.share.ShareUtil;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mQQDefaultButton;
    private Button mQQAudioButton;

    private Button mWxTextButton;
    private Button mWxImgButton;
    private Button mWxMusicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShareConfig config = new ShareConfig
                .Builder()
                .setWeixin("wxd30478e950b3d816", "08bf822aeef141dfb2ab26ab72613a3a")
                .setQQ("1105616184", "3wiHgfrKzzxeue2I")
                .build();
        ShareUtil.setConfig(config);

        mQQDefaultButton = (Button) findViewById(R.id.main_btn_qq_default);
        mQQDefaultButton.setOnClickListener(this);
        mQQAudioButton = (Button) findViewById(R.id.main_btn_qq_audio);
        mQQAudioButton.setOnClickListener(this);

        mWxTextButton = (Button) findViewById(R.id.main_btn_wx_txt);
        mWxTextButton.setOnClickListener(this);
        mWxImgButton = (Button) findViewById(R.id.main_btn_wx_img);
        mWxImgButton.setOnClickListener(this);
        mWxMusicButton = (Button) findViewById(R.id.main_btn_wx_music);
        mWxMusicButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_qq_default:
                ShareUtil.with(MainActivity.this)
                        .type(ShareTypeEnum.TEXT)
                        .api(ShareApiEnum.QQ)
                        .setTitle("Share")
                        .setUrl("http://www.safehy.com/index.html")
                        .setImgUrl("http://www.safehy.com/skin/images/logo12.png")
                        .setText("hello world")
                        .setDescription("hello world")
                        .send();
                break;
            case R.id.main_btn_qq_audio:
                ShareUtil.with(MainActivity.this)
                        .type(ShareTypeEnum.MUSIC)
                        .api(ShareApiEnum.QQ)
                        .setTitle("Share")
                        .setUrl("http://www.safehy.com/index.html")
                        .setAudioUrl("http://stream14.qqmusic.qq.com/30432451.mp3?key=ABD30A88B30BA76C1435598BC67F69EA741DE4082BF8E089&qqmusic_fromtag=15")
                        .setImgUrl("http://www.safehy.com/skin/images/logo12.png")
                        .setText("hello world")
                        .setDescription("hello world")
                        .send();
                break;
            case R.id.main_btn_wx_txt:
                ShareUtil.with(MainActivity.this)
                        .type(ShareTypeEnum.TEXT)
                        .api(ShareApiEnum.WeChat)
                        .setTitle("Share")
                        .setText("hello world")
                        .setDescription("hello world")
                        .send();
                break;
            case R.id.main_btn_wx_img:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ShareUtil.with(MainActivity.this)
                                    .type(ShareTypeEnum.IMAGE)
                                    .api(ShareApiEnum.WeChat)
                                    .setTitle("Share")
                                    .setBitmap(BitmapFactory.decodeStream(new URL("http://www.safehy.com/skin/images/logo12.png").openStream()))
                                    .setText("hello world")
                                    .setDescription("hello world")
                                    .send();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.main_btn_wx_music:
                ShareUtil.with(MainActivity.this)
                        .type(ShareTypeEnum.MUSIC)
                        .api(ShareApiEnum.WeChat)
                        .setTitle("Share")
                        .setAudioUrl("http://stream14.qqmusic.qq.com/30432451.mp3?key=ABD30A88B30BA76C1435598BC67F69EA741DE4082BF8E089&qqmusic_fromtag=15")
                        .setText("hello world")
                        .setDescription("hello world")
                        .send();
                break;
        }
    }
}
