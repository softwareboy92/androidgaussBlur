package com.cainiao.cjni.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * 作者：created by albert on 2019-12-20 10:47
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class OpenGlView extends GLSurfaceView {

    public OpenGlView(Context context) {
        super(context);
        init();
    }



    public OpenGlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        setRenderer(new OpenGlRenderer(getContext()));
    }

}
