package com.bupt.red5lib;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bupt.red5prolib.publish.PublishWithPreview;
import com.bupt.sensordriver.led;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.view.R5VideoView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    R5Stream subscribe;

    R5VideoView mR5VideoView;
    TextView mTextView;
//    Camera mCamera;

    boolean mButtonFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mR5VideoView = (R5VideoView) findViewById(R.id.r5videoview);
        mTextView = (TextView) findViewById(R.id.textView);

//        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
//        mCamera.cancelAutoFocus();
//        Camera.Parameters parameters =  mCamera.getParameters();
//        parameters.setPreviewSize(176, 144);
//        mCamera.setParameters(parameters);
//        List<Camera.Size> sizes = parameters.getSupportedVideoSizes();
//        if (sizes != null){
//            for (Camera.Size size : sizes) {
//                Log.d(TAG, "Video Size: " + size.height + "  " + size.width );
//            }
//        }
//        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
//        Log.d(TAG, "Video Info: " + "\n" +
//                "height: " + profile.videoFrameHeight + "\n" +
//                "width: " + profile.videoFrameWidth + "\n" +
//                "Codec: " + profile.videoCodec + "\n" +
//                "BitRate: " + profile.videoBitRate + "\n");
//
//        R5Configuration config = new R5Configuration(R5StreamProtocol.RTSP, "10.210.12.237", 8554, "live", 0.5f);
//        R5Connection connection = new R5Connection(config);
//        //setup a new stream using the connection
//        subscribe = new R5Stream(connection);
//        //show all logging
//        subscribe.setLogLevel(R5Stream.LOG_LEVEL_DEBUG);
//        mR5VideoView.attachStream(subscribe);
//        mR5VideoView.showDebugView(true);
//        subscribe.play("stream");

        final led light = new led();
        light.Open();

        mTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mButtonFlag) light.Ioctl(0, 1);
                else light.Ioctl(0, 0);

                mButtonFlag = !mButtonFlag;
            }
        });

//        new PublishWithPreview(getApplicationContext(), "10.210.12.237", 8554, mCamera, mR5VideoView);
    }
}
