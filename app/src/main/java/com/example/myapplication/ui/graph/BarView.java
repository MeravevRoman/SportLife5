package com.example.myapplication.ui.graph;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;


public class BarView extends View {

    private TypedArray attrs;

    private List<Float> percentList;
    private List<Float> targetPercentList;
    private Paint textPaint;
    private Paint bgPaint;
    private Paint fgPaint;
    private Rect rect;
    private int barWidth;
    private int bottomTextDescent;
    private boolean autoSetWidth = true;
    private int topMargin;
    private int bottomTextHeight;
    private List<String> bottomTextList = new ArrayList<>();
    private final int MINI_BAR_WIDTH;
    private final int BAR_SIDE_MARGIN;
    private final int TEXT_TOP_MARGIN;


    private int maxValue;

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            for (int i = 0; i < targetPercentList.size(); i++) {
                if (percentList.get(i) < targetPercentList.get(i)) {
                    percentList.set(i, percentList.get(i) + 0.02f);
                    needNewFrame = true;
                } else if (percentList.get(i) > targetPercentList.get(i)) {
                    percentList.set(i, percentList.get(i) - 0.02f);
                    needNewFrame = true;
                }
                if (Math.abs(targetPercentList.get(i) - percentList.get(i)) < 0.02f) {
                    percentList.set(i, targetPercentList.get(i));
                }
            }
            if (needNewFrame) {
                postDelayed(this, 20);
            }
            invalidate();
        }
    };

    public BarView(Context context){
        this(context,null);
    }

    public BarView(Context context, AttributeSet attr){

        super(context, attr);
        attrs = context.obtainStyledAttributes(attr, R.styleable.GraphView, 0, 0);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.parseColor(attrs.getString(R.styleable.GraphView_gBackgroundColor)));

        fgPaint = new Paint(bgPaint);
        fgPaint.setColor(Color.parseColor(attrs.getString(R.styleable.GraphView_gBarColor)));

        rect = new Rect();
        topMargin = GraphUtils.dip2px(context, 5);
        int textSize = GraphUtils.sp2px(context, attrs.getInteger(R.styleable.GraphView_gTextSize, 10));
        barWidth = GraphUtils.dip2px(context, attrs.getInteger(R.styleable.GraphView_gBarWidth, 10));
        MINI_BAR_WIDTH = GraphUtils.dip2px(context, attrs.getInteger(R.styleable.GraphView_gBarWidth, 10));
        BAR_SIDE_MARGIN  = GraphUtils.dip2px(context,attrs.getInteger(R.styleable.GraphView_gBarMargin, 10));
        TEXT_TOP_MARGIN = GraphUtils.dip2px(context, 5);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor(attrs.getString(R.styleable.GraphView_gTextColor)));
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        percentList = new ArrayList<>();
    }

    /**
     * Настраиваем строку значений по горизонтали (шкала времени)
     * @param bottomStringList список значений (годы, месяцы, дни, часы, минуты)
     */
    public void setKeyList(List<String> bottomStringList) {

        this.bottomTextList = bottomStringList;

        Rect r = new Rect();
        bottomTextDescent = 0;
        barWidth = MINI_BAR_WIDTH;
        for (String s : bottomTextList) {
            textPaint.getTextBounds(s,0, s.length(), r);
            if (bottomTextHeight < r.height()) {
                bottomTextHeight = r.height();
            }
            // если ширина текста больше ширины столбца, меняем ширину столбца
            if (autoSetWidth && (barWidth < r.width())) {
                barWidth = r.width();
            }
            if (bottomTextDescent < (Math.abs(r.bottom))) {
                bottomTextDescent = Math.abs(r.bottom);
            }
        }
        setMinimumWidth(2);
        postInvalidate();
    }

    /**
     * Настраиваем значения по вертикали (в столбцах)
     * @param list список значений каждого столбца
     * @param max максимальное значение списка для определения верхней границы
     *            чтобы было удобно считать значения в % и строить график в относительных величинах
     */
    public void setValueList(List<Integer> list, int max) {
        // список с относительными значениями (например, 580 в абсолютных значениях
        // будет равно 0,88 или 88% в относительных значениях)
        targetPercentList = new ArrayList<>();
        if (max == 0) max = 1;
        maxValue = max;

        for (Integer integer : list) {
            // вычисляем относительные значения
            targetPercentList.add(1 - (float) integer / (float) max);
        }

        if (percentList.isEmpty() || percentList.size() < targetPercentList.size()) {
            // если данные есть не для всех столбиков, то пустые столбики заполняем единицами
            int temp = targetPercentList.size() - percentList.size();
            for (int i = 0; i < temp; i++) {
                percentList.add(1f);
            }
        } else if (percentList.size() > targetPercentList.size()) {
            int temp = percentList.size() - targetPercentList.size();
            for (int i = 0; i < temp; i++) {
                percentList.remove(percentList.size() - 1);
            }
        }
        setMinimumWidth(2);
        removeCallbacks(animator);
        post(animator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int i = 1;
        float[] corners = new float[] {
                30, 30,
                30, 30,
                0, 0,
                0, 0
        };
        final Path path = new Path();
        if (percentList != null && !percentList.isEmpty()) {
            for (Float f : percentList) {
                rect.set(30 + BAR_SIDE_MARGIN * i + barWidth * (i - 1),
                        topMargin + (int) ((getHeight() - topMargin - bottomTextHeight - TEXT_TOP_MARGIN) * percentList.get(i - 1)),
                        20 + (BAR_SIDE_MARGIN + barWidth) * i,
                        getHeight() - bottomTextHeight - TEXT_TOP_MARGIN);
                path.addRoundRect(new RectF(rect), corners, Path.Direction.CW);
                canvas.drawPath(path, fgPaint);
                i++;
            }
        }

        if (bottomTextList != null && !bottomTextList.isEmpty()) {
            i = 1;
            for (String s : bottomTextList) {
                canvas.drawText(s,25 + BAR_SIDE_MARGIN * i + barWidth * (i - 1) + barWidth / 2,
                        getHeight() - bottomTextDescent, textPaint);
                i++;
            }

            int nDigits = String.valueOf(maxValue).length() - 1;
            int counter = (int) (maxValue / Math.pow(10, nDigits));
            for (int j = 1; j <= counter; j++) {
                String scaleText = String.valueOf((int) (j * Math.pow(10, nDigits)));
                canvas.drawText(scaleText, BAR_SIDE_MARGIN, getHeight() - (bottomTextDescent + j * 60), textPaint);
                i++;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mViewWidth = measureWidth(widthMeasureSpec);
        int mViewHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
        int preferred = 0;
        if (bottomTextList != null) {
            preferred = bottomTextList.size() * (barWidth + BAR_SIDE_MARGIN);
        }
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        int preferred = 222;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }

}