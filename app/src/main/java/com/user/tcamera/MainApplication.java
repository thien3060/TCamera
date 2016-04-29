package com.user.tcamera;

import android.app.Application;

import com.adobe.creativesdk.aviary.IAviaryClientCredentials;
import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;

/**
 * Created by user on 4/29/2016.
 */
/* 1) Implement IAviaryClientCredentials when using the Image Editor */
public class MainApplication extends Application implements IAviaryClientCredentials {

    /* Be sure to fill in the two strings below. */
    private static final String CREATIVE_SDK_CLIENT_ID = "dc24697c35dc4d3f8a8e012ad2e63484";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "407d9e03-fcb4-43ab-8ed0-af4cd252cd5c";

    @Override
    public void onCreate() {
        super.onCreate();
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    /* 2) Add the getBillingKey() method */
    @Override
    public String getBillingKey() {
        return ""; // Leave this blank
    }
}