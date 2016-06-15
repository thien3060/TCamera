package com.user.tcamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_SELECT_PICTURE = 1;

    private String mImageFileLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            String action = bundle.getString("action");
            if (action != null){
                if (action.equals("start_camera"))
                    startCameraIntent();
                if (action.equals("start_gallery"))
                    startGalleryIntent();
            }
        }

        Button btnCamera = (Button) findViewById(R.id.btn_camera);
        Button btnGallery = (Button) findViewById(R.id.btn_gallery);

        btnCamera.setOnClickListener(v -> startCameraIntent());

        btnGallery.setOnClickListener(v -> startGalleryIntent());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTIVITY_START_CAMERA_APP) {
                Intent editImageIntent = new Intent(this, ImageEditorActivity.class);
                editImageIntent.putExtra("imageUri", mImageFileLocation);
                startActivity(editImageIntent);
            }

            if (requestCode == ACTIVITY_SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                mImageFileLocation = selectedImageUri.toString();

                Intent editImageIntent = new Intent(this, ImageEditorActivity.class);
                editImageIntent.putExtra("imageUri", mImageFileLocation);
                startActivity(editImageIntent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startCameraIntent(){
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

    public void startGalleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTIVITY_SELECT_PICTURE);
    }

    File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMAGE"+timeStamp+"_";
        File storageDirectory = Environment.getExternalStorageDirectory();

        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageFileLocation = image.getAbsolutePath();

        return image;
    }
}
