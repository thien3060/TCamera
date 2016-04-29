package com.user.tcamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageFileLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent cammereAppIntent = new Intent();
        cammereAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photo = null;
        try {
            photo = createImageFile();
            mImageFileLocation = Uri.fromFile(photo).toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        cammereAppIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(cammereAppIntent, ACTIVITY_START_CAMERA_APP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            Intent editImageIntent = new Intent(this, ImageEditorActivity.class);
            editImageIntent.putExtra("imageUri", mImageFileLocation);
            startActivity(editImageIntent);
        }
    }

    File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE"+timeStamp+"_";
        File storageDirectory = Environment.getExternalStorageDirectory();

        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageFileLocation = image.getAbsolutePath();

        return image;
    }
}
