package org.firstinspires.ftc.teamcode.vision.vizTurret;

import org.opencv.core.Point;

import java.util.ArrayList;

//avoid divide by zero!
public class CameraTurret {
    private final int M = 0;
    private final int B = 1;

    private final int X = 0;
    private final int Y = 1;
    //OPTIONAL: make a data handling class for the equations.
    //custom "var" to hold 2 y = mx+b equations.
    double[] currentEquation = new double[] {0,0};
    double[] previousEquation = new double[] {0, 0};


    ArrayList<double[]> intersects = new ArrayList<>();

    private double correctCamera(Point point, Pose2d robotPos) {
        double cameraDeg = (Constants.FOV-(point.x * Constants.PIXEL_TO_DEG));
        double servoCamDeg = ((cameraDeg + 135) - (Constants.FOV/2.0)) - Constants.CAMERA_ROTATION_OFFSETS[2];

        return (robotPos.getHeading() <= 180 ? 180 - robotPos.getHeading() :
                robotPos.getHeading() >= 360 ? robotPos.getHeading() % 360 : robotPos.getHeading() - 90)
                + servoCamDeg;
    }

    public void findEquation(Point point, Pose2d robotPos) {
        double currentAngleDeg = correctCamera(point, robotPos);
        boolean secondSector = currentAngleDeg > 90;
        double correctedAng = (currentAngleDeg > 90 ? -90 : 0) + robotPos.getHeading();
        double m = Math.tan(correctedAng);
        double b = robotPos.getX() - (m * robotPos.getY());

        if(secondSector) {
            addEquation(new double[]{-m, b});
        } else {
            addEquation(new double[]{m, b});
        }
    }


    private void addEquation(double[] newEquation) {
        previousEquation = currentEquation;
        currentEquation = newEquation;
        //avoid divide by zero exception
        if(previousEquation[M] != 0 && currentEquation[M] != 0) {
            calculateIntersect();
        }
    }

    private void calculateIntersect() {
        double m = currentEquation[M] - previousEquation[M];
        double b = currentEquation[B] - previousEquation[B];
        //this sets the equation to 0 = -1x + b. So we just take b instead of making that negative.
        double multiplier = -1/m;
        double x = b*multiplier;
        double y = (currentEquation[M]*x) + currentEquation[B];
        intersects.add(new double[]{x, y});
    }

    public double[] getObjectCoord() {
        ArrayList<Double> allX = new ArrayList<>();
        ArrayList<Double> allY = new ArrayList<>();

        for(double[] intersect : intersects) {
            allX.add(intersect[X]);
            allY.add(intersect[Y]);
        }
        return new double[] {
                allX.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0),
                allY.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0)};
    }

    public double getNextServoVal(Pose2d roPo) {
        double width = roPo.getX() - getObjectCoord()[X];
        double height = roPo.getY() - getObjectCoord()[Y];
        double posAsDeg = (Math.toDegrees(Math.tan(height/width))) - 180;
        if(posAsDeg > 135 || posAsDeg < -135) {
            return 0.0;
        } else {
            return posAsDeg/135;
        }
    }

}
