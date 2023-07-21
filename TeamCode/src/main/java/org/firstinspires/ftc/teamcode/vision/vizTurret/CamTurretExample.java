package org.firstinspires.ftc.teamcode.vision.vizTurret;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.vision.AIDetectThread;
import org.firstinspires.ftc.teamcode.vision.DnnObject;
import org.firstinspires.ftc.teamcode.vision.OnnxPipeline;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;

//this code was made assuming the turret is on the right side. I was too lazy to make it work
// on the left side.
public class CamTurretExample extends OpMode {
    OpenCvCamera camera;
    Servo servo;
    AIDetectThread aiDetectThread;
    Mat image;
    CameraTurret turretManaager;
    @Override
    public void init() {

        aiDetectThread = new AIDetectThread(hardwareMap,camera);
        aiDetectThread.start();
//        capture = new VideoCapture(0);
        image = new Mat();
//        capture.read(image);
//        telemetry.addData("","" + image);
        telemetry.addData("Current detections", OnnxPipeline.detectObject.toString());
        for(DnnObject obj : OnnxPipeline.detectObject) {
            telemetry.addData("Detection", obj.getCenterCoordinate().x + "," + obj.getCenterCoordinate().y);
            //errored because this is the test project. I do not have the class form caoscontrol.
            turretManaager.findEquation(obj.getCenterCoordinate(), TwoWheelOdometry.getPose());
        }

    }

    @Override
    public void loop() {
        for(DnnObject obj : OnnxPipeline.detectObject) {
            telemetry.addData("Detection", obj.getCenterCoordinate().x + "," + obj.getCenterCoordinate().y);
            //errored because this is the test project. I do not have the class form caoscontrol.
            turretManaager.findEquation(obj.getCenterCoordinate(), TwoWheelOdometry.getPose());
        }
        servo.setPosition(turretManaager.getNextServoVal(TwoWheelOdometry.getPose()));
    }
}
