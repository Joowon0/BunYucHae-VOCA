package com.example.user.voca;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class setImage extends Activity{

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private ImageView imgview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_image);
        imgview = (ImageView) findViewById(R.id.imageView1);
        TextView text = (TextView) findViewById(R.id.tv);
        Button buttonCamera = (Button) findViewById(R.id.btn_take_camera);
        Button buttonGallery = (Button) findViewById(R.id.btn_select_gallery);


        text.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        buttonCamera.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        buttonGallery.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // 카메라 호출
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);

                // 이미지 잘라내기 위한 크기
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);

                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                // Gallery 호출
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 잘라내기 셋팅
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        if (requestCode == PICK_FROM_CAMERA) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                imgview.setImageBitmap(photo);
            }
        }
        if (requestCode == PICK_FROM_GALLERY) {
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap photo = extras2.getParcelable("data");
                imgview.setImageBitmap(photo);
            }
        }
    }
}

