package com.bupt.red5prolib.publish;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bupt.red5prolib.Config;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.event.R5ConnectionEvent;
import com.red5pro.streaming.event.R5ConnectionListener;
import com.red5pro.streaming.source.R5AdaptiveBitrateController;
import com.red5pro.streaming.source.R5Camera;
import com.red5pro.streaming.source.R5Microphone;
import com.red5pro.streaming.view.R5VideoView;


/**
 * Created by hadoop on 16-9-11.
 */
public class PublishWithPreview {

    private static final String TAG = "PublishWithPreview";
    private static final boolean DEBUG = Config.DEBUG;

    private R5Configuration mR5Configuration;
    private R5Connection mR5Connection;
    private R5Stream mR5Stream;
    private R5Camera mR5Camera;

    public PublishWithPreview(Context context, String hostName, int port, Camera camera, R5VideoView preview){
        mR5Configuration = new R5Configuration(R5StreamProtocol.RTSP, hostName, port, "live", 1.0f);
        mR5Connection = new R5Connection(mR5Configuration);
        mR5Stream = new R5Stream(mR5Connection);


        //show all logging
        mR5Stream.setLogLevel(R5Stream.LOG_LEVEL_DEBUG);

        mR5Stream.connection.addListener(new R5ConnectionListener() {
            @Override
            public void onConnectionEvent(R5ConnectionEvent r5ConnectionEvent) {
                Log.d(TAG, "connection event code "+r5ConnectionEvent.value()+"\n");
            }
        });

        mR5Stream.setListener(new R5ConnectionListener() {
            @Override
            public void onConnectionEvent(R5ConnectionEvent r5ConnectionEvent) {
                Log.d(TAG, "Stream event code "+r5ConnectionEvent.value()+"\n");
            }
        });

        mR5Camera  = new R5Camera(camera, 720, 480);
        // 300 kbps is good enough
        mR5Camera.setBitrate(300);
        /* for test */
        int bitrate = mR5Camera.getBitrate();
        if (DEBUG) Log.d(TAG, "Camera bitrate:" + bitrate);

        mR5Stream.attachCamera(mR5Camera);
//        mR5Stream.attachMic(new R5Microphone());
//        R5AdaptiveBitrateController bitrateController = new R5AdaptiveBitrateController();
//        bitrateController.AttachStream(mR5Stream);
        if (preview != null) {
            preview.attachStream(mR5Stream);
            preview.showDebugView(true);
        } else {
        }

        mR5Stream.publish("myStream", R5Stream.RecordType.Live);
        camera.startPreview();
    }
}
