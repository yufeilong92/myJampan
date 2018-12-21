package com.example.myjampan;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;
import com.etiennelawlor.discreteslider.library.utilities.DisplayUtility;

public class MainActivity extends AppCompatActivity {

    private DiscreteSlider mDiscreteSlider;
    private RelativeLayout mTickMarkLabelsRl;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mDiscreteSlider.setOnDiscreteSliderChangeListener(new DiscreteSlider.OnDiscreteSliderChangeListener() {
            @Override
            public void onPositionChanged(int position) {
                int childCount = mTickMarkLabelsRl.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    TextView tv = (TextView) mTickMarkLabelsRl.getChildAt(i);
                    if (i == position)
                        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    else
                        tv.setTextColor(getResources().getColor(R.color.grey_400));
                }
            }
        });
   /*     mTickMarkLabelsRl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTickMarkLabelsRl.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                addTickMarkTextLabels();
            }
        });*/
    }

    private void initView() {
        mContext = this;
        mDiscreteSlider = (DiscreteSlider) findViewById(R.id.discrete_slider);
        mTickMarkLabelsRl = (RelativeLayout) findViewById(R.id.tick_mark_labels_rl);
    }

    private void addTickMarkTextLabels() {
        int tickMarkCount = mDiscreteSlider.getTickMarkCount();
        float tickMarkRadius = mDiscreteSlider.getTickMarkRadius();
        int width = mTickMarkLabelsRl.getMeasuredWidth();

        int discreteSliderBackdropLeftMargin = DisplayUtility.dp2px(mContext, 32);
        int discreteSliderBackdropRightMargin = DisplayUtility.dp2px(mContext, 32);
        float firstTickMarkRadius = tickMarkRadius;
        float lastTickMarkRadius = tickMarkRadius;
        int interval = (width - (discreteSliderBackdropLeftMargin + discreteSliderBackdropRightMargin) - ((int) (firstTickMarkRadius + lastTickMarkRadius)))
                / (tickMarkCount - 1);

        String[] tickMarkLabels = {"$", "$$", "$$$", "$$$$", "$$$$$"};
        int tickMarkLabelWidth = DisplayUtility.dp2px(mContext, 40);

        for (int i = 0; i < tickMarkCount; i++) {
            TextView tv = new TextView(mContext);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    tickMarkLabelWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);

//            tv.setText(tickMarkLabels[i]);
            tv.setGravity(Gravity.CENTER);
            if (i == mDiscreteSlider.getPosition())
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            else
                tv.setTextColor(getResources().getColor(R.color.grey_400));
//                    tv.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            int left = discreteSliderBackdropLeftMargin + (int) firstTickMarkRadius + (i * interval) - (tickMarkLabelWidth / 2);

            layoutParams.setMargins(left,
                    0,
                    0,
                    0);
            tv.setLayoutParams(layoutParams);

            mTickMarkLabelsRl.addView(tv);
        }
    }
}
