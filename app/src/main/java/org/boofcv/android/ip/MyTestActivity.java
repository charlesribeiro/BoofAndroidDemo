package org.boofcv.android.ip;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ToggleButton;

import org.boofcv.android.DemoBitmapCamera2Activity;
import org.boofcv.android.DemoProcessingAbstract;
import org.boofcv.android.R;

import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.android.VisualizeImageData;
import boofcv.struct.image.GrayU8;

public class MyTestActivity extends DemoBitmapCamera2Activity
        implements SeekBar.OnSeekBarChangeListener ,
        CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener {

    boolean down;
    double threshold;
    int action;

    public MyTestActivity() {
        super(Resolution.MEDIUM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout controls = (LinearLayout) inflater.inflate(R.layout.binary_ops_controls, null);

        SeekBar seek = controls.findViewById(R.id.slider_threshold);
        seek.setOnSeekBarChangeListener(this);

        ToggleButton toggle = controls.findViewById(R.id.toggle_threshold);
        toggle.setOnCheckedChangeListener(this);

        Spinner spinner = controls.findViewById(R.id.spinner_binary_ops);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.test_binary_filters, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        down = toggle.isChecked();
        threshold = seek.getProgress();
        action = spinner.getSelectedItemPosition();

        setControls(controls);
        activateTouchToShowInput();
    }

    @Override
    public void createNewProcessor() {
        setProcessing(new ThresholdProcessing());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        threshold = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        down = isChecked;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        action = pos;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    protected void convertToOutput(GrayU8 binary) {
        VisualizeImageData.binaryToBitmap(binary, false, bitmap, bitmapTmp);
    }

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
            drawBitmap(canvas, imageToView);
        }

        @Override
        public void process(GrayU8 input) {
            GThresholdImageOps.threshold(input, binary, threshold, down);

            switch (action) {
                case 1:
                    BinaryImageOps.thin(binary, -1, afterOps);
                    break;

                default:
                    afterOps.setTo(binary);
            }

            convertToOutput(afterOps);
        }
    }
}