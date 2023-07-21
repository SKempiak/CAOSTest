package org.firstinspires.ftc.teamcode.vision.vizTurret;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import com.arcrobotics.ftclib.geometry.Pose2d;

import java.util.ArrayList;

public class CameraInit {
    Servo rotator;
    Camera camera;

    public final int fov = 180;
    public final int cameraWidth = 640;
    public final int cameraHeight = 480;

    //camera offset from zero degrees(left of robot?)
    public final int cameraOffset = 0;
    public final double pointMultiplier = (double) cameraWidth/fov;

    double[][] equation = new double[][]{{0,0},{0,0}};

    double[] intersect = new double[]{0,0};
    int equationToChange = 0;
    ArrayList<double[]> everyIntersection = new ArrayList<>();
    public CameraInit(Servo servo, Camera cam) {
        rotator = servo;
        camera = cam;

    }

    //this command is used to start up the camera turret. The object should be in frame.
    public void start(Point point, Pose2d roPo) {
        equationToChange = 0;
        findEquation(point,roPo);
    }

    //this is called by the user to update the position of the intersection.
    public void update(Point point, Pose2d roPo) {
        findEquation(point,roPo);
        updateIntersect();
    }

    //takes where the object is in the camera frame, accounts based on the cameras fov
    //then finds the equation of the line from the point to your current robot position
    private void findEquation(Point point, Pose2d robotPos) {
        double currentAngleDeg = (fov-(point.x * pointMultiplier));
        boolean secondSector = currentAngleDeg > 90;
        double correctedAng = (currentAngleDeg > 90 ? -90 : 0) + robotPos.getHeading();
        double m = Math.tan(correctedAng);
        double b = robotPos.getX() - (m * robotPos.getY());

        if(equationToChange == 0) {
            if(secondSector) {
                equation[equationToChange] = new double[]{-m, b};
                everyIntersection.add(new double[]{-m, b});
            } else {
                equation[equationToChange] = new double[]{m, b};
                everyIntersection.add(new double[]{m,b});
            }
            equationToChange++;
        } else {
            if(secondSector) {
                equation[equationToChange] = new double[]{-m, b};
                everyIntersection.add(new double[]{-m,b});
            } else {
                equation[equationToChange] = new double[]{m, b};
                everyIntersection.add(new double[]{m,b});
            }
            equationToChange--;
        }
    }

    //subtracts equation 1 from equation 2. Then it finds the multiplier to make x = 1 and applies
    // that to b. We set it as negative to account for the change from 0 = x + ? to x = ?
    private void updateIntersect() {
        double combinedM = equation[0][0] - equation[1][0];
        double combinedB = equation[0][1] - equation[1][1];
        double multiplier = 1/combinedM;
        double x = combinedB*multiplier;
        if(equationToChange == 0) {
            intersect = new double[]{x, -(equation[1][0]*x) + equation[1][1]};
        } else {
            intersect = new double[]{x, -(equation[0][0]*x) + equation[0][1]};
        }

    }

    //uses trigonometry to find the angle and corrects as the range of atan is -90 to 90
    public void trackObject(Pose2d roPos) {

        double width = Math.abs(roPos.getX()-intersect[0]);
        double height = Math.abs(roPos.getY()-intersect[1]);
        double degree = (Math.atan(height/width)+90.0)*(1+1/3);
        //270 because the default servo range is -135 tp 135
        rotator.setPosition((degree/270)-135);
    }
}