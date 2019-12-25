package com.cainiao.cjni.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao.cjni.R;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private int[] intArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(add(100, 333) + stringFromJNI()
                + ";" + intArraySum(intArray, 10));

//        ImageView iv = findViewById(R.id.sample_iv);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
//        getImageBitmap(bm, 13);
//        iv.setImageBitmap(bm);


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native void getImageBitmap(Bitmap bitmap, int level);

    public native int add(int a, int b);

    private native int intArraySum(int[] intArray, int size);


}
