package org.yapp.utils.share.qqapi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;

/**
 * Description: 简述. <br>
 * Date: 2016/8/27 15:31 <br>
 * Author: ysj
 */
public class QQUtils {
    private static final String TAG = "QQUtils";

    public static Bundle genTextMsg(String appName, String title, String description, String tagetUrl, String imgUrl) {
        return genMsg(appName, title, description, tagetUrl, imgUrl);
    }

    public static Bundle genImgMsg(String appName, String title, String description, String tagetUrl, String imgUrl, String path) {
        Bundle params = genMsg(appName, title, description, tagetUrl, imgUrl);
        if (!TextUtils.isEmpty(path)) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        }
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        return params;
    }

    public static Bundle genAudioMsg(String appName, String title, String description, String tagetUrl, String imgUrl, String path) {
        Bundle params = genMsg(appName, title, description, tagetUrl, imgUrl);
        if (!TextUtils.isEmpty(path)) {
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, path);
        }
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        return params;
    }

    public static Bundle genMsg(String appName, String title, String description, String tagetUrl, String imgUrl) {
        Bundle params = new Bundle();
        if (!TextUtils.isEmpty(appName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        }
        if (!TextUtils.isEmpty(title)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        }
        if (!TextUtils.isEmpty(tagetUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tagetUrl);
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        }
        if (!TextUtils.isEmpty(description)) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        }
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        return params;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
//            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
    }
}
