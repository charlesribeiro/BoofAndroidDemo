package org.boofcv.android.ip;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.boofcv.android.DemoProcessingAbstract;
import org.boofcv.android.R;

import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.struct.image.GrayU8;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

//    @Override
//    public void createNewProcessor() {
//        setProcessing(new ThresholdProcessing());
//    }

    protected class ThresholdProcessing extends DemoProcessingAbstract<GrayU8> {
        GrayU8 binary;
        GrayU8 afterOps;

        public ThresholdProcessing() {
            super(GrayU8.class);
        }

        @Override
        public void initialize(int imageWidth, int imageHeight, int sensorOrientation) {
            binary = new GrayU8(imageWidth, imageHeight);
            afterOps = new GrayU8(imageWidth, imageHeight);
        }

        @Override
        public void onDraw(Canvas canvas, Matrix imageToView) {
            //drawBitmap(canvas, imageToView);
        }

        @Override
        public void process(GrayU8 input) {
//            GThresholdImageOps.threshold(input, binary, threshold, down);
//
//            switch (action) {
//                case 1:
//                    BinaryImageOps.thin(binary, -1, afterOps);
//                    break;
//
//                default:
//                    afterOps.setTo(binary);
//            }
//
//            convertToOutput(afterOps);
        }
    }

}
