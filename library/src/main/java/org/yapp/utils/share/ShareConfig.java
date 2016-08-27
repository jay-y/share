package org.yapp.utils.share;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

/**
 * Description: 分享配置. <br>
 * Date: 2016/8/27 0:00 <br>
 * Author: ysj
 */
public class ShareConfig {
    private Context context;
    private String qqAppId;
    private String qqAppKey;
    private String wxAppId;
    private String wxAppKey;
    private String wbAppId;
    private String wbAppKey;

    public IWXAPI wxApi;
    public Tencent tencentApi;

    public void setContext(Context context) {
        this.context = context;
    }

    public IWXAPI getWxApi() {
        synchronized (ShareConfig.class){
            if(null == wxApi){
                wxApi = WXAPIFactory.createWXAPI(context,wbAppId);
            }
        }
        return wxApi;
    }

    public Tencent getTencentApi(){
        synchronized (ShareConfig.class){
            if(null == tencentApi){
                context.getString(R.string.qq_app_id,qqAppId);
                tencentApi = Tencent.createInstance(qqAppId,context);
            }
        }
        return tencentApi;
    }

    public static class Builder {
        private String qqAppId;
        private String qqAppKey;
        private String wxAppId;
        private String wxAppKey;
        private String wbAppId;
        private String wbAppKey;

        public Builder setQQ(String appId, String appKey) {
            this.qqAppId = appId;
            this.qqAppKey = appKey;
            return this;
        }

        public Builder setWeixin(String appId, String appKey) {
            this.wxAppId = appId;
            this.wxAppKey = appKey;
            return this;
        }

        public Builder setSinaWeibo(String appId, String appKey) {
            this.wbAppId = appId;
            this.wbAppKey = appKey;
            return this;
        }

        public ShareConfig build() {
            return new ShareConfig(this);
        }
    }

    private ShareConfig(Builder builder) {
        this.qqAppId = builder.qqAppId;
        this.qqAppKey = builder.qqAppKey;
        this.wxAppId = builder.wxAppId;
        this.wxAppKey = builder.wxAppKey;
        this.wbAppKey = builder.wbAppKey;
    }
}
