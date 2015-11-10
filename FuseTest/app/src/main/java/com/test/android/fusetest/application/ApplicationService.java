package com.test.android.fusetest.application;

import android.app.Application;
import android.util.Log;


import com.test.android.fusetest.bll.communications.ConnectionHelper;

import ro.cvu.connection.mvc.system.request.RequestOptions;
import ro.cvu.connection.mvc.system.request.RequestService;

public class ApplicationService extends Application {

    public static final String APP_NAME = "[FuseTest]::";

    private static final String LOG_TAG = APP_NAME
            + ApplicationService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        RequestService.createInstance(getApplicationContext());

        ConnectionHelper
                .getInstance();

        RequestService.getInstance().setIsInputStreamData(false);

        initRequestOptions();
    }

    private void initRequestOptions() {
        RequestOptions mReqOptions = new RequestOptions() {
            @Override
            public Object InterceptResponseString(int requestType,
                                                  Object responseData) {
                Log.e(LOG_TAG, "Received response - " + responseData);

                return responseData;
            }
        };

        mReqOptions.SetConnectionTimeout(ConnectionHelper.CONNECTION_TIMEOUT);
        mReqOptions.SetDataTimeout(ConnectionHelper.DATA_TIMEOUT);
    }
}
