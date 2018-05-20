package com.custu.project.walktogether.util.lib;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collections;

public class OpenCVUtil {

    private static OpenCVUtil instance;

    public static OpenCVUtil getInstance() {
        if (instance == null)
            instance = new OpenCVUtil();
        return instance;
    }

    public void compare_image(Bitmap img_1, Bitmap img_2)
    {
        Mat mat_1 = conv_Mat(img_1);
        Mat mat_2 = conv_Mat(img_2);

        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        MatOfFloat ranges = new MatOfFloat(0f,256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Collections.singletonList(mat_1), new MatOfInt(0),
                new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Collections.singletonList(mat_2), new MatOfInt(0),
                new Mat(), hist_2, histSize, ranges);

        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        Double d = res * 100;
        Log.d("compare_image: ", "compare_image: "+d.intValue());
    }
    private Mat conv_Mat(Bitmap img){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Mat mat = new Mat(img.getHeight(),img.getWidth(), CvType.CV_8UC3);
        mat.put(0,0,byteArray);

        Mat mat1 = new Mat(img.getHeight(),img.getWidth(),CvType.CV_8UC3);
        Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2HSV);

        return mat1;
    }
}
