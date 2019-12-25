#include <jni.h>
#include <string>
#include <android/log.h>
#include <tgmath.h>
#include "android/bitmap.h"

#define PI 3.14

void gaussBlur(int *pInt, uint32_t width, uint32_t height, int i);


void gaussBlur(int *pix, uint32_t w, uint32_t h, int radius) {

    float sigma = (float) (1.0 * radius / 2.57);
    float deno = (float) (1.0 / (sigma * sqrt(2.0 * PI)));
    float nume = (float) (-1.0 / (2.0 * sigma * sigma));
    float *gaussMatrix = (float *) malloc(sizeof(float) * (radius + radius + 1));
    float gaussSum = 0.0;
    for (int i = 0, x = -radius; x <= radius; ++x, ++i) {
        float g = (float) (deno * exp(1.0 * nume * x * x));
        gaussMatrix[i] = g;
        gaussSum += g;
    }
    int len = radius + radius + 1;
    for (int i = 0; i < len; ++i)
        gaussMatrix[i] /= gaussSum;
    int *rowData = (int *) malloc(w * sizeof(int));
    int *listData = (int *) malloc(h * sizeof(int));
    for (int y = 0; y < h; ++y) {
        memcpy(rowData, pix + y * w, sizeof(int) * w);
        for (int x = 0; x < w; ++x) {
            float r = 0, g = 0, b = 0;
            gaussSum = 0;
            for (int i = -radius; i <= radius; ++i) {
                int k = x + i;
                if (0 <= k && k <= w) {
                    //得到像素点的rgb值
                    int color = rowData[k];
                    int cr = (color & 0x00ff0000) >> 16;
                    int cg = (color & 0x0000ff00) >> 8;
                    int cb = (color & 0x000000ff);
                    r += cr * gaussMatrix[i + radius];
                    g += cg * gaussMatrix[i + radius];
                    b += cb * gaussMatrix[i + radius];
                    gaussSum += gaussMatrix[i + radius];
                }
            }
            int cr = (int) (r / gaussSum);
            int cg = (int) (g / gaussSum);
            int cb = (int) (b / gaussSum);
            pix[y * w + x] = cr << 16 | cg << 8 | cb | 0xff000000;
        }
    }
    for (int x = 0; x < w; ++x) {
        for (int y = 0; y < h; ++y)
            listData[y] = pix[y * w + x];
        for (int y = 0; y < h; ++y) {
            float r = 0, g = 0, b = 0;
            gaussSum = 0;
            for (int j = -radius; j <= radius; ++j) {
                int k = y + j;
                if (0 <= k && k <= h) {
                    int color = listData[k];
                    int cr = (color & 0x00ff0000) >> 16;
                    int cg = (color & 0x0000ff00) >> 8;
                    int cb = (color & 0x000000ff);
                    r += cr * gaussMatrix[j + radius];
                    g += cg * gaussMatrix[j + radius];
                    b += cb * gaussMatrix[j + radius];
                    gaussSum += gaussMatrix[j + radius];
                }
            }
            int cr = (int) (r / gaussSum);
            int cg = (int) (g / gaussSum);
            int cb = (int) (b / gaussSum);
            pix[y * w + x] = cr << 16 | cg << 8 | cb | 0xff000000;
        }
    }
    free(gaussMatrix);
    free(rowData);
    free(listData);

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_cainiao_cjni_activity_MainActivity_add(JNIEnv *env, jobject thiz, jint a, jint b) {
    // TODO: implement add()
    jint sum = a + b;
    return sum;
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_cainiao_cjni_activity_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    // TODO: implement stringFromJNI()
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT void JNICALL
Java_com_cainiao_cjni_activity_MainActivity_getImageBitmap(JNIEnv *env, jobject thiz,
                                                           jobject bitmap, jint level) {
    AndroidBitmapInfo info = {0};//初始化BitmapInfo结构体

    int *data = NULL;

    AndroidBitmap_getInfo(env, bitmap, &info);

    AndroidBitmap_lockPixels(env, bitmap, (void **) &data);//锁定Bitmap，并且获得指针

    gaussBlur(data, info.width, info.height, 80);

    AndroidBitmap_unlockPixels(env, bitmap);//解锁
}extern "C"
JNIEXPORT jint JNICALL
Java_com_cainiao_cjni_activity_MainActivity_intArraySum(JNIEnv *env, jobject instance,
                                                        jintArray intArray_, jint num) {
    // TODO: implement intArraySum()
    jint *intArray;
    int sum = 0;
    jint buf[num];
    // 通过 GetIntArrayRegion 方法来获取数组内容
    env->GetIntArrayRegion(intArray_, 0, num, buf);
    sum = 0;
    for (int i = 0; i < num; ++i) {
        sum += buf[i];
    }
    // 使用完了别忘了释放内存
    env->ReleaseIntArrayElements(intArray_, intArray, 0);
    return sum;
}