package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.AIDetectThread;
import org.firstinspires.ftc.teamcode.vision.DnnObject;
import org.firstinspires.ftc.teamcode.vision.OnnxPipeline;
import org.firstinspires.ftc.teamcode.vision.vizTurret.Constants;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;
//q: This code should be running the camera and the AI, but it doesn't seem to be working. What's wrong?

@TeleOp()
public class test extends LinearOpMode {
    OpenCvCamera camera;
    Servo servo;
    Mat image;
    int cameraMonitorViewId;
    AIDetectThread aiDetectThread;
    List<DnnObject> objects;

    @Override
    public void runOpMode() throws InterruptedException {
        aiDetectThread = new AIDetectThread(hardwareMap, camera);
        aiDetectThread.start();
        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        capture = new VideoCapture(0);
        image = new Mat();
//        capture.read(image);
//        telemetry.addData("","" + image);
        telemetry.addData("Current detections", OnnxPipeline.detectObject.toString());
        for (DnnObject obj : OnnxPipeline.detectObject) {
            telemetry.addData("Detection", obj.getCenterCoordinate().x + "," + obj.getCenterCoordinate().y);
        }
        telemetry.update();
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        camera.setPipeline(new OnnxPipeline());
        camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        camera.setViewportRenderer(OpenCvCamera.ViewportRenderer.NATIVE_VIEW);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                telemetry.addData("Camera opened", "successful");
                camera.startStreaming(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        telemetry.addData("waiting for start", "");
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("looping", "testing");
            objects = aiDetectThread.newRecognitions(telemetry);
            telemetry.update();
            for (DnnObject obj : objects) {
                telemetry.addData("Detection " + obj.getObjectName(), obj.getCenterCoordinate().x + "," + obj.getCenterCoordinate().y);
            }

        }
    }


}
