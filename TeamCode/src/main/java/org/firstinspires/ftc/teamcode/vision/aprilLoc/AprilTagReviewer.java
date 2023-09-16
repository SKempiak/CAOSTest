package org.firstinspires.ftc.teamcode.vision.aprilLoc;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AprilTagReviewer {

    public enum TagFamily{
        TAG_1(583),
        TAG_2(584),
        TAG_3(585),
        TAG_4(586);

        public final int tagNum;

        TagFamily(int tagNum)
        {
            this.tagNum = tagNum;
        }

        public static boolean contains(int tagNum) {
            for (TagFamily tag : TagFamily.values()) {
                if (tag.tagNum == tagNum) {
                    return true;
                }
            }
            return false;
        }
    }

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    HardwareMap hardwareMap;

    private int     myExposure  ;
    private int     minExposure ;
    private int     maxExposure ;
    private int     myGain      ;
    private int     minGain ;
    private int     maxGain ;

    List<AprilTagDetection> currentDetections;

    public AprilTagReviewer(HardwareMap hwp, int[] tags) {
        hardwareMap = hwp;
        initAprilTag();

    }

    private void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder().build();

        // Create the vision portal the easy way.
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();

    }
//too lazy to find out why this was erroring ngl
//    private void getCameraSetting() {
//        // Ensure Vision Portal has been setup.
//        if (visionPortal == null) {
//            return;
//        }
//
//        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
//        minExposure = (int)exposureControl.getMinExposure(TimeUnit.MILLISECONDS) + 1;
//        maxExposure = (int)exposureControl.getMaxExposure(TimeUnit.MILLISECONDS);
//
//        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
//        minGain = gainControl.getMinGain();
//        maxGain = gainControl.getMaxGain();
//    }

    public List<AprilTagDetection> getCameraDetections() {
        currentDetections = aprilTag.getDetections();
        return currentDetections;
    }
}
