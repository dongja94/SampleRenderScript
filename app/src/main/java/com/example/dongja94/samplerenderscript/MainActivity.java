package com.example.dongja94.samplerenderscript;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView blurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blurView = (ImageView) findViewById(R.id.image_blur);
        RenderScript rs = RenderScript.create(this);
        Bitmap bitmap = ((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.data)).getBitmap();
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Allocation inAllocation = Allocation.createFromBitmap(rs, bitmap);
        Allocation outAllocation = Allocation.createTyped(rs, inAllocation.getType());

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        blur.setRadius(25.0f);
        blur.setInput(inAllocation);
        blur.forEach(outAllocation);

        outAllocation.copyTo(outBitmap);
        blurView.setImageBitmap(outBitmap);

    }

}
