package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class AIDetectThread extends Thread {
    HardwareMap hwmap;
    OpenCvCamera camera;
    int cameraMonitorViewId;

    public AIDetectThread(HardwareMap hwmap, OpenCvCamera camera) {
        this.hwmap = hwmap;
        this.camera = camera;
        cameraMonitorViewId = hwmap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwmap.appContext.getPackageName());

    }
    @Override
    public void start() {
        camera = OpenCvCameraFactory.getInstance().createWebcam(hwmap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        camera.setPipeline(new OnnxPipeline());
        camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        camera.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(640, 480, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

    }


}
