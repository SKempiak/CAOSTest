package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;

@TeleOp()
public class imageMaker extends OpMode {
    OpenCvCamera camera;

    // Store image as 2D matrix
    private Mat image;


    AIDetectThread aiDetectThread;

    @Override
    public void init() {
        aiDetectThread = new AIDetectThread(hardwareMap,camera);
        aiDetectThread.start();
//        capture = new VideoCapture(0);
        image = new Mat();
//        capture.read(image);
//        telemetry.addData("","" + image);
        telemetry.addData("Current detections", OnnxPipeline.detectObject.toString());
        for(DnnObject obj : OnnxPipeline.detectObject)
            telemetry.addData("Detection" ,obj.getCenterCoordinate().x + "," + obj.getCenterCoordinate().y );
    }

    @Override
    public void loop() {

    }
}
