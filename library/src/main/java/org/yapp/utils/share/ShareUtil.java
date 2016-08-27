package org.yapp.utils.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import org.yapp.utils.share.qqapi.QQCallbackListener;
import org.yapp.utils.share.qqapi.QQUtils;
import org.yapp.utils.share.wxapi.WxUtils;

/**
 * ClassName: ShareUtil <br>
 * Description: 友盟工具包. <br>
 * Date: 2016-1-16 下午8:03:16 <br>
 *
 * @author ysj
 * @since JDK 1.7
 */
public class ShareUtil {
    private static final String TAG = "ShareUtil";
    private final static Object lock = new Object();
    private static final int THUMB_SIZE = 150;

    private static ShareUtil instance;
    private static ShareConfig configInstance;

    private Context mContext;
    private ShareApiEnum api;
    private ShareTypeEnum type;

    // 标题
    private String title;
    // 文字内容
    private String text;
    // 描述
    private String description;

    // 一般用于网络路径
    private String wxTagetUrl;
    private String wxMusicUrl;
    private String wxVideoUrl;
    private Bitmap wxImgBmp;
    private int wxImgResId;

    private String qqTagetUrl;
    private String qqImgUrl;
    private String qqAudioUrl;
    private String qqFilePath;

    public static void setConfig(ShareConfig config) {
        synchronized (lock) {
            configInstance = config;
        }
    }

    public static ShareUtil with(Context context) {
        synchronized (lock) {
            if (null == configInstance) {
                throw new RuntimeException("Please set config first.");
            }
            if (null == instance) {
                instance = new ShareUtil(context);
            }
            configInstance.setContext(context);
        }
        return instance;
    }

    public ShareUtil type(ShareTypeEnum type) {
        instance.type = type;
        return instance;
    }

    public ShareUtil api(ShareApiEnum api) {
        instance.api = api;
        return instance;
    }

    public ShareUtil setTitle(String title) {
        instance.title = title;
        return instance;
    }

    public ShareUtil setText(String text) {
        instance.text = text;
        return instance;
    }

    public ShareUtil setDescription(String description) {
        instance.description = description;
        return instance;
    }

    public ShareUtil setUrl(String url) {
        if (null == instance.api) {
            throw new RuntimeException("Please call api first.");
        }
        switch (instance.api) {
            case QQ:
                instance.qqTagetUrl = url;
                break;
            case WeChat:
                instance.wxTagetUrl = url;
                break;
            case SinaWeiBo:
                break;
        }
        return instance;
    }

    public ShareUtil setBitmap(Bitmap bmp){
        if (null == instance.api) {
            throw new RuntimeException("Please call api first.");
        }
        switch (instance.api) {
            case QQ:
                throw new RuntimeException("This interface is not supported.");
            case WeChat:
                instance.wxImgBmp = bmp;
                break;
            case SinaWeiBo:
                break;
        }
        return instance;
    }

    public ShareUtil setImgUrl(String url) {
        if (null == instance.api) {
            throw new RuntimeException("Please call api first.");
        }
        switch (instance.api) {
            case QQ:
                instance.qqImgUrl = url;
                break;
            case WeChat:
                throw new RuntimeException("This interface is not supported.");
            case SinaWeiBo:
                break;
        }
        return instance;
    }

    public ShareUtil setAudioUrl(String url) {
        if (null == instance.api) {
            throw new RuntimeException("Please call api first.");
        }
        switch (instance.api) {
            case QQ:
                instance.qqAudioUrl = url;
                break;
            case WeChat:
                instance.wxMusicUrl = url;
                instance.wxVideoUrl = url;
                break;
            case SinaWeiBo:
                break;
        }
        return instance;
    }

    public ShareUtil setResId(int resId) {
        if (null == instance.api) {
            throw new RuntimeException("Please call api first.");
        }
        switch (instance.api) {
            case QQ:
                throw new RuntimeException("This interface is not supported.");
            case WeChat:
                instance.wxImgResId = resId;
                break;
            case SinaWeiBo:
                break;
        }
        return instance;
    }

    public ShareUtil setFilePath(String path) {
        if (null == instance.api) {
            throw new RuntimeException("Please call api first.");
        }
        switch (instance.api) {
            case QQ:
                instance.qqFilePath = path;
                break;
            case WeChat:
                throw new RuntimeException("This interface is not supported.");
            case SinaWeiBo:
                break;
        }
        return instance;
    }

    public void send() {
        if (null == instance.type
                || null == instance.api) {
            throw new RuntimeException("Please call type and api first.");
        }
        switch (instance.type) {
            case TEXT:
                sendText();
                return;
            case MUSIC:
                sendMusic();
                return;
            case VIDEO:
                sendVideo();
                return;
            case IMAGE:
                sendImage();
                return;
            case WEB:
                sendWeb();
                return;
        }

    }

    private void sendText() {
        switch (instance.api) {
            case QQ:
                String appName = mContext.getString(R.string.app_name);
                Bundle params = QQUtils.genTextMsg(appName, title, description, qqTagetUrl, qqImgUrl);
                configInstance.getTencentApi().shareToQQ((Activity) mContext, params, new QQCallbackListener());
                return;
            case WeChat:
                // 初始化一个WXTextObject对象
                WXTextObject textObj = new WXTextObject();
                textObj.text = text;

                // 用WXTextObject对象初始化一个WXMediaMessage对象
                WXMediaMessage msg = WxUtils.genMsg(textObj, title, description);

                // 调用api接口发送数据到微信
                configInstance.getWxApi().sendReq(WxUtils.genRequest(msg, instance.type));
                return;
            case SinaWeiBo:
                return;
        }
    }

    private void sendMusic() {
        switch (instance.api) {
            case QQ:
                String appName = mContext.getString(R.string.app_name);
                Bundle params = QQUtils.genAudioMsg(appName, title, description, qqTagetUrl, qqImgUrl, qqAudioUrl);
                configInstance.getTencentApi().shareToQQ((Activity) mContext, params, new QQCallbackListener());
                return;
            case WeChat:
                WXMusicObject music = new WXMusicObject();
                if (!TextUtils.isEmpty(instance.wxMusicUrl)) {
                    music.musicUrl = instance.wxMusicUrl;
                    music.musicLowBandUrl = instance.wxMusicUrl;
                }

                Bitmap thumbBmp = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_send);
                if (instance.wxImgResId != 0) {
                    thumbBmp = BitmapFactory.decodeResource(mContext.getResources(), instance.wxImgResId);
                }

                WXMediaMessage msg = WxUtils.genMsg(music, title, description, thumbBmp);

                // 调用api接口发送数据到微信
                configInstance.getWxApi().sendReq(WxUtils.genRequest(msg, instance.type));
                return;
            case SinaWeiBo:
                return;
        }
    }

    private void sendVideo() {
        switch (instance.api) {
            case QQ:
                String appName = mContext.getString(R.string.app_name);
                Bundle params = QQUtils.genAudioMsg(appName, title, description, qqImgUrl, qqImgUrl, qqFilePath);
                configInstance.getTencentApi().shareToQQ((Activity) mContext, params, new QQCallbackListener());
                return;
            case WeChat:
                WXVideoObject video = new WXVideoObject();
                if (!TextUtils.isEmpty(instance.wxVideoUrl)) {
                    video.videoUrl = instance.wxVideoUrl;
                    video.videoLowBandUrl = instance.wxVideoUrl;
                }

                Bitmap thumbBmp = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_send);
                if (instance.wxImgResId != 0) {
                    thumbBmp = BitmapFactory.decodeResource(mContext.getResources(), instance.wxImgResId);
                }

                WXMediaMessage msg = WxUtils.genMsg(video, title, description, thumbBmp);

                // 调用api接口发送数据到微信
                configInstance.getWxApi().sendReq(WxUtils.genRequest(msg, instance.type));
                return;
            case SinaWeiBo:
                return;
        }
    }

    private void sendImage() {
        switch (instance.api) {
            case QQ:
                String appName = mContext.getString(R.string.app_name);
                Bundle params = QQUtils.genImgMsg(appName, title, description, qqTagetUrl, qqImgUrl, qqFilePath);
                configInstance.getTencentApi().shareToQQ((Activity) mContext, params, new QQCallbackListener());
                return;
            case WeChat:
                WXImageObject imgObj = new WXImageObject();
                Bitmap thumbBmp = Bitmap.createScaledBitmap(wxImgBmp, THUMB_SIZE, THUMB_SIZE, true);
                wxImgBmp.recycle();
                WXMediaMessage msg = WxUtils.genMsg(imgObj, title, description, thumbBmp);

                // 调用api接口发送数据到微信
                configInstance.getWxApi().sendReq(WxUtils.genRequest(msg, instance.type));
                return;
            case SinaWeiBo:
                return;
        }
    }

    private void sendWeb() {
        switch (instance.api) {
            case QQ:
                String appName = mContext.getString(R.string.app_name);
                Bundle params = QQUtils.genTextMsg(appName, title, description, qqTagetUrl, qqImgUrl);
                configInstance.getTencentApi().shareToQQ((Activity) mContext, params, new QQCallbackListener());
                return;
            case WeChat:
                WXWebpageObject webpage = new WXWebpageObject();
                if (!TextUtils.isEmpty(instance.wxTagetUrl)) {
                    webpage.webpageUrl = instance.wxTagetUrl;
                }

                Bitmap thumbBmp = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_send);
                if (instance.wxImgResId != 0) {
                    thumbBmp = BitmapFactory.decodeResource(mContext.getResources(), instance.wxImgResId);
                }

                WXMediaMessage msg = WxUtils.genMsg(webpage, title, description, thumbBmp);

                // 调用api接口发送数据到微信
                configInstance.getWxApi().sendReq(WxUtils.genRequest(msg, instance.type));
                return;
            case SinaWeiBo:
                return;
        }
    }


    private ShareUtil(Context context) {
        mContext = context;
    }
}
