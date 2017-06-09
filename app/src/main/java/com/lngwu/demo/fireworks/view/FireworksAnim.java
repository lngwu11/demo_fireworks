package com.lngwu.demo.fireworks.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import java.util.Random;

/**
 * 2017/6/8
 * lngwu11@qq.com
 * Description:
 */

class FireworksAnim {
    private static final int RISE_INTERVAL_TIME = 16;
    private static final int BLAST_INTERVAL_TIME = 128;
    private static final int TEXT_INTERVAL_TIME = 16;
    private static final int X_MIN = 200;
    private static final int X_RANGE = 600;
    private static final int Y_MAX = 1500;
    private static final int Y_MIN = 300;
    private static final int SPEED = 10; // 上升速度

    private int startX;
    private int startY;

    private Bitmap[] riseBitmaps;
    private Bitmap[] blastBitmaps;
    private int riseCurFrame = 0;
    private int blastCurFrame = 0;
    private int textCurFrame = 0;
    private long riseLastFrameTime = 0;
    private long blastLastFrameTime = 0;
    private long textLastFrameTime = 0;

    private Random random;
    private SoundPlay soundPlay;
    private boolean playUp = false;
    private boolean playBlast = false;

    private String fireworksText = "你好世界";

    interface OnBlastFinishedListener {
        void onBlastFinished();
    }

    FireworksAnim(Context context, int[] riseDrawId, int[] blastDrawId, SoundPlay sound, String text) {
        int riseNum = riseDrawId.length;
        riseBitmaps = new Bitmap[riseNum];
        for (int i = 0; i < riseNum; i++) {
            riseBitmaps[i] = BitmapFactory.decodeResource(context.getResources(), riseDrawId[i]);
        }

        int blastNum = blastDrawId.length;
        blastBitmaps = new Bitmap[blastNum];
        for (int i = 0; i < blastNum; i++) {
            blastBitmaps[i] = BitmapFactory.decodeResource(context.getResources(), blastDrawId[i]);
        }

        random = new Random();
        startX = X_MIN + random.nextInt(X_RANGE);
        startY = Y_MAX;

        //soundPlay = sound;
        fireworksText = text;
    }

    /**
     * 上升动画
     *
     * @param canvas
     * @param x
     * @param y
     */
    private void drawRiseAnim(Canvas canvas, int x, int y) {
        if (!playUp) {
            //soundPlay.play(SoundPlay.SOUND_ID_UP, 0);
            playUp = true;
        }
        canvas.drawBitmap(riseBitmaps[riseCurFrame], x, y, null);
        long curTime = System.currentTimeMillis();
        if (curTime - riseLastFrameTime > RISE_INTERVAL_TIME) {
            riseCurFrame++;
            riseLastFrameTime = curTime;
            if (riseCurFrame >= riseBitmaps.length) {
                riseCurFrame = 0;
            }
        }
    }

    /**
     * 爆炸动画
     *
     * @param canvas
     * @param x
     * @param y
     */
    private void drawBlastAnim(Canvas canvas, int x, int y, OnBlastFinishedListener listener) {
        if (!playBlast) {
            //soundPlay.play(SoundPlay.SOUND_ID_BLAST, 0);
            playBlast = true;
        }
        int x1 = x - blastBitmaps[blastCurFrame].getWidth() / 2;
        int y1 = y - blastBitmaps[blastCurFrame].getHeight() / 2;

        canvas.drawBitmap(blastBitmaps[blastCurFrame], x1, y1, null);
        long curTime = System.currentTimeMillis();
        if (curTime - blastLastFrameTime > BLAST_INTERVAL_TIME) {
            blastCurFrame++;
            blastLastFrameTime = curTime;
            if (blastCurFrame >= blastBitmaps.length) {
                blastCurFrame = 0;
                startX = X_MIN + random.nextInt(X_RANGE);
                startY = Y_MAX;
                listener.onBlastFinished();
            }
        }
    }

    private void drawTextAnim(Canvas canvas, int x, int y) {
        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.RED);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(fireworksText, 0, fireworksText.length(), bounds);
        //Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        //int baseline = (y - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(fireworksText, x - bounds.width() / 2, y - bounds.height() / 2, mPaint);

        long curTime = System.currentTimeMillis();
        if (curTime - textLastFrameTime > TEXT_INTERVAL_TIME) {
            textCurFrame++;
            textLastFrameTime = curTime;
            if (textCurFrame >= 15) {
                textCurFrame = 0;
                startX = X_MIN + random.nextInt(X_RANGE);
                startY = Y_MAX;
                canvas.drawColor(Color.TRANSPARENT);
                Paint p = new Paint();
                p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                canvas.drawPaint(p);
            }
        }
    }

    /**
     * 绘制整个烟花过程动画
     *
     * @param canvas
     */
    void drawFireworksAnim(Canvas canvas, OnBlastFinishedListener listener) {
        if (startY <= Y_MIN) {
            if (!fireworksText.equals("")) {
                drawTextAnim(canvas, startX, startY);
            } else {
                drawBlastAnim(canvas, startX, startY, listener);
            }
        } else {
            drawRiseAnim(canvas, startX, startY);
            startY -= SPEED;
        }
    }
}
