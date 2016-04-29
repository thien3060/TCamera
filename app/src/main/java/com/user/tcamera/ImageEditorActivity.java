package com.user.tcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.adobe.creativesdk.aviary.AdobeImageIntent;

public class ImageEditorActivity extends AppCompatActivity {
    private ImageView mEditedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditedImageView = (ImageView) findViewById(R.id.editedImageView);

    /* 1) Make a new Uri object (Replace this with a real image on your device) */
        Bundle imagePath = this.getIntent().getExtras();
        Uri imageUri = Uri.parse(imagePath.getString("imageUri"));

    /* 2) Create a new Intent */
        Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
                .setData(imageUri)
                .build();

    /* 3) Start the Image Editor with request code 1 */
        startActivityForResult(imageEditorIntent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent mainPage = new Intent(this,MainActivity.class);
            startActivity(mainPage);
        }

        if (id == R.id.action_camera) {
            Intent camera = new Intent(this, CameraActivity.class);
            startActivity(camera);
        }

        if (id == R.id.action_gallery) {
            Intent gallery = new Intent(this, GalleryActivity.class);
            startActivity(gallery);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                /* 4) Make a case for the request code we passed to startActivityForResult() */
                case 1:
                    /* 5) Show the image! */
                    Uri editedImageUri = data.getData();
                    //mEditedImageView.setImageURI(editedImageUri);
                    scaleImage(editedImageUri);
                    break;
            }
        }
    }

    public void scaleImage(Uri imageUri){
        Bitmap d = BitmapFactory.decodeFile(imageUri.getPath());
        int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
        mEditedImageView.setImageBitmap(scaled);
    }

}
