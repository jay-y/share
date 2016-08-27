package org.yapp.utils.share.qqapi;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class QQCallbackListener implements IUiListener {

    @Override
    public void onCancel() {
    }

    @Override
    public void onComplete(Object response) {
        // TODO Auto-generated method stub
//            Util.toastMessage(QQShareActivity.this, "onComplete: " + response.toString());
    }

    @Override
    public void onError(UiError e) {
        // TODO Auto-generated method stub
//            Util.toastMessage(QQShareActivity.this, "onError: " + e.errorMessage, "e");
    }
}