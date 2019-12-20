package com.cainiao.cjni.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.cainiao.cjni.weight.Square;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 作者：created by albert on 2019-12-20 10:49
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class OpenGlRenderer implements GLSurfaceView.Renderer {

    private Square square;
    private Context context;

    public OpenGlRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        square = new Square(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        GLES20.glViewport(0, 0, w, h);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(1f,0,0,1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        square.draw();
    }
}
