package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.vizTurret.Constants;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.List;

public class AIDetectThread {
    HardwareMap hwmap;
    OpenCvCamera camera;
    int cameraMonitorViewId;
    DeepNeuralNetworkProcessor dnnp = new DeepNeuralNetworkProcessor();
    public static List<DnnObject> detectObject = new ArrayList<>();


    public AIDetectThread(HardwareMap hwmap, OpenCvCamera camera) {
        this.hwmap = hwmap;
        this.camera = camera;
        cameraMonitorViewId = hwmap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwmap.appContext.getPackageName());

    }

    public void start() {
        camera = OpenCvCameraFactory.getInstance().createWebcam(hwmap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        camera.setPipeline(new OnnxPipeline());
        camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        camera.setViewportRenderer(OpenCvCamera.ViewportRenderer.NATIVE_VIEW);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()

            {
                camera.startStreaming(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
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
    public List<DnnObject> newRecognitions(Telemetry telemetry) {
        telemetry.addData("attempting to add data", "test");
        telemetry.update();
        detectObject = dnnp.getObjectsInFrame(0.0);
        return detectObject;
    }

}
