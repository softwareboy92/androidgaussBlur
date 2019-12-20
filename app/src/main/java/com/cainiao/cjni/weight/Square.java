package com.cainiao.cjni.weight;

import android.content.Context;
import android.opengl.GLES20;

import com.cainiao.cjni.R;
import com.cainiao.cjni.tools.BufferUtils;
import com.cainiao.cjni.tools.ShaderProgram;
import com.cainiao.cjni.tools.ShaderUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;


/**
 * 作者：created by albert on 2019-12-20 11:02
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class Square {

    private FloatBuffer vertexBuffer;
    private ShaderProgram shader;
    private int vertexBufferID;
    private int vertexCount;
    private int vertexStride;
    static final int COORDS_PER_VERTEX = 3;
    static final int COLORS_PER_VERTEX = 4;
    static final int SIZE_OF_FLOAT = 4;
    static final float squareCoords[] = {
            -0.5f, 0.5f, 0, 1f, 0, 0, 1f,
            -0.5f, -0.5f, 0, 0, 1f, 0, 1f,
            0.5f, -0.5f, 0, 0, 0, 1f, 1f,
            -0.5f, 0.5f, 0, 1f, 0, 0, 1f,
            0.5f, -0.5f, 0, 0, 0, 1f, 1f,
            0.5f, 0.5f, 0, 0, 1f, 0, 1f
    };

    public Square(Context c) {
        setupShader(c);
        setupVertexBuffer();
    }

    private void setupShader(Context c) {
        shader = new ShaderProgram(
                ShaderUtils.readShaderFileFromRawResource(c, R.raw.simple_fragment_shader),
                ShaderUtils.readShaderFileFromRawResource(c, R.raw.simple_fragment_shader)
        );
    }

    private void setupVertexBuffer() {
        vertexBuffer = BufferUtils.newFloatBuffer(squareCoords.length);
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        vertexBufferID = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferID);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, squareCoords.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);
        vertexCount = squareCoords.length / (COORDS_PER_VERTEX + COLORS_PER_VERTEX);
        vertexStride = (COORDS_PER_VERTEX + COLORS_PER_VERTEX) * 4;
    }

    public void draw() {
        shader.begin();
        shader.disableVertexAttribute("a_Position");
        shader.setVertexAttribute("a_Position", COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);
        shader.enableVertexAttribute("a_Color");
        shader.setVertexAttribute("a_Color", COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, COORDS_PER_VERTEX * SIZE_OF_FLOAT);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferID);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        shader.disableVertexAttribute("a_Position");
        shader.disableVertexAttribute("a_Color");

        shader.end();
    }
}
