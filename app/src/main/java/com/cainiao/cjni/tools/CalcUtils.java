package com.cainiao.cjni.tools;
 
import android.content.Context;

/**
 * description: 单位换算工具.
 *
 * @author 刘明昆.
 * @date 2018/8/30.
 */
public class CalcUtils {
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
 
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

//    public static int[] findMax(List<SigninBean> steps) {
//        int[] value = new int[2];
//        int[] position = new int[2];
//        int temValue;
//        int temPosition;
//        for (int i = 0; i < steps.size(); i++) {
//            if (steps.get(i).getNumber() > value[1]) {
//                //比较出大的放到value[0]中
//                value[1] = steps.get(i).getNumber();
//                position[1] = i;
//            }
//            if (value[1] > value[0]) {
//                //把最大的放到value[0]中,交换位置
//                temValue = value[0];
//                value[0] = value[1];
//                value[1] = temValue;
//
//                temPosition = position[0];
//                position[0] = position[1];
//                position[1] = temPosition;
//            }
//        }
//        return position;
//    }
}