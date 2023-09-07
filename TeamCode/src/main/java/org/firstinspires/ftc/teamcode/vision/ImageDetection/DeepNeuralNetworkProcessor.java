package org.firstinspires.ftc.teamcode.vision.ImageDetection;

import org.firstinspires.ftc.teamcode.vision.vizTurret.Constants;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

//to put a file to a specific location:
//first: We need to use shell to see file contents.
//adb shell
//inside of shell, do ls to find the directory you want to put the file in.
//NOTE: If we need to make a file,
//adb push myfile.txt /sdcard/myfile.txt

//upon further research, the best bet might be to do adb push folderWithModel /sdcard/FIRST/folderWithModel
//or
//just put it into models: adb push objectDetection.onnx /sdcard/FIRST/models/objectDetection.onnx


//go to Android Sdk then 'platform-tools' path on your Terminal or Console
//
//(on mac, default path is : /Users/USERNAME/Library/Android/sdk/platform-tools)
//
//To check the SDCards(External and Internal) installed on your device fire these commands :
//
//1) ./adb shell (hit return/enter)
//2) cd -(hit return/enter)
//now you will see the list of Directories and files from your android device there you may find /sdcard as well as /storage
//
//3) cd /storage (hit return/enter)
//4) ls (hit return/enter)
//you may see sdcard0 (generally sdcard0 is internal storage) and sdcard1 (if External SDCard is present)
//
//5) exit (hit return/enter)
//to come out of adb shell
//
//6) ./adb push '/Users/SML/Documents/filename.zip' /storage/sdcard0/path_to_store/ (hit return/enter)

//https://stackoverflow.com/questions/20834241/how-to-use-adb-command-to-push-a-file-on-device-without-sd-card

public class DeepNeuralNetworkProcessor {
    private final static Logger LOGGER = LoggerFactory.getLogger(DeepNeuralNetworkProcessor.class);
    private Net net;
    private final String model = "/sdcard/FIRST/models/rubix-ball.onnx";

    private final String[] classNames = {"rubix","ball"};


    public DeepNeuralNetworkProcessor() {
        this.net = Dnn.readNetFromONNX(model);
    }

    public int getObjectCount(Mat frame, String objectName, double threshold) {

        int inWidth = Constants.CAMERA_WIDTH;
        int inHeight = Constants.CAMERA_HEIGHT;
        //0.007843
        double inScaleFactor = 0.07843;
        double thresholdDnn =  threshold;
        double meanVal = 127.5;

        int personObjectCount = 0;
        Mat blob = null;
        Mat detections = null;


        try {
            blob = Dnn.blobFromImage(frame, inScaleFactor,
                    new Size(inWidth, inHeight),
                    new Scalar(meanVal, meanVal, meanVal),
                    false, false);
            net.setInput(blob);
            detections = net.forward();
            detections = detections.reshape(1, (int) detections.total() / 7);
            for (int i = 0; i < detections.rows(); ++i) {
                double confidence = detections.get(i, 2)[0];

                if (confidence < thresholdDnn)
                    continue;

                int classId = (int) detections.get(i, 1)[0];
                if (classNames[classId].toString() != objectName.toLowerCase()) {
                    continue;
                }
                personObjectCount++;
            }
        } catch (Exception ex) {
            LOGGER.error("An error occurred DNN: ", ex);
        }
        return personObjectCount;
    }

    public List<DnnObject> getObjectsInFrame(Mat frame, double threshold) {

        int inWidth = 320;
        int inHeight = 320;
        double inScaleFactor = 0.007843;
        double thresholdDnn =  0.2;
        double meanVal = 127.5;

//        Mat blob = null;
//        Mat detections = null;
        List<DnnObject> objectList = new ArrayList<>();

        int cols = frame.cols();
        int rows = frame.rows();

        try {

            frame = Dnn.blobFromImage(frame, inScaleFactor,
                    new Size(inWidth, inHeight),
                    new Scalar(meanVal, meanVal, meanVal),
                    false, false);

            net.setInput(frame);
            frame = net.forward();
            frame = frame.reshape(1, (int) frame.total() / 7);

            //all detected objects
            for (int i = 0; i < frame.rows(); ++i) {
                double confidence = frame.get(i, 2)[0];

                if (confidence < thresholdDnn)
                    continue;

                int classId = (int) frame.get(i, 1)[0];

                //calculate position
                int xLeftBottom = (int) (frame.get(i, 3)[0] * cols);
                int yLeftBottom = (int) (frame.get(i, 4)[0] * rows);
                Point leftPosition = new Point(xLeftBottom, yLeftBottom);

                int xRightTop = (int) (frame.get(i, 5)[0] * cols);
                int yRightTop = (int) (frame.get(i, 6)[0] * rows);
                Point rightPosition = new Point(xRightTop, yRightTop);

                float centerX = (float) (xLeftBottom + xRightTop) / 2;
                float centerY = (float) (yLeftBottom - yRightTop) / 2;
                Point centerPoint = new Point(centerX, centerY);


                DnnObject dnnObject = new DnnObject(classId, classNames[classId].toString(), leftPosition, rightPosition, centerPoint);
                objectList.add(dnnObject);
            }

        } catch (Exception ex) {
            LOGGER.error("An error occurred DNN: ", ex);
        }
        return objectList;
    }


}