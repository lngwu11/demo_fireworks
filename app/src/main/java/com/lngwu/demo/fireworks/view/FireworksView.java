package com.lngwu.demo.fireworks.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.lngwu.demo.fireworks.R;

import java.util.Random;
import java.util.Vector;

/**
 * 2017/6/8
 * lngwu11@qq.com
 * Description:
 */

public class FireworksView extends View {
    private static final String TAG = FireworksView.class.getSimpleName();

    private Context mContext;
    private Random random;
    private Vector<FireworksAnim> animVector;
    private boolean isRunning = true;


    public FireworksView(Context context) {
        super(context);
        mContext = context;
        random = new Random();
        animVector = new Vector<>();

        new FireworksThread().start();
    }

    public FireworksView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FireworksView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < animVector.size(); i++) {
            final int index = i;
            animVector.get(i).drawFireworksAnim(canvas, new FireworksAnim.OnBlastFinishedListener() {
                @Override
                public void onBlastFinished() {
                    animVector.remove(index);
                }
            });
        }

        invalidate();
    }

    class FireworksThread extends Thread {

        @Override
        public void run() {

            while (isRunning) {
                if (animVector.size() > 5) {
                    continue;
                }
                int[] blastDrawId;
                SoundPlay soundPlay = new SoundPlay();
                soundPlay.init(mContext);
                soundPlay.load(mContext, R.raw.up, SoundPlay.SOUND_ID_UP);
                switch (random.nextInt(5)) {
                    case 0:
                        blastDrawId = FireworksStyle.blast1;
                        soundPlay.load(mContext, R.raw.blow, SoundPlay.SOUND_ID_BLAST);
                        break;
                    case 1:
                        blastDrawId = FireworksStyle.blast2;
                        soundPlay.load(mContext, R.raw.blow, SoundPlay.SOUND_ID_BLAST);
                        break;
                    case 2:
                        blastDrawId = FireworksStyle.blast3;
                        soundPlay.load(mContext, R.raw.blow, SoundPlay.SOUND_ID_BLAST);
                        break;
                    case 3:
                        blastDrawId = FireworksStyle.blast4;
                        soundPlay.load(mContext, R.raw.multiple, SoundPlay.SOUND_ID_BLAST);
                        break;
                    case 4:
                        blastDrawId = FireworksStyle.blast5;
                        soundPlay.load(mContext, R.raw.multiple, SoundPlay.SOUND_ID_BLAST);
                        break;
                    default:
                        blastDrawId = FireworksStyle.blast1;
                        soundPlay.load(mContext, R.raw.blow, SoundPlay.SOUND_ID_BLAST);
                        break;
                }
                FireworksAnim fireworksAnim = new FireworksAnim(mContext, FireworksStyle.rise1, blastDrawId, soundPlay, "你好世界");
                animVector.add(fireworksAnim);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
