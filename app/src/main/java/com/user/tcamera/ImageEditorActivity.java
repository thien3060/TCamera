package com.user.tcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

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

        Bundle imagePath = this.getIntent().getExtras();
        Uri imageUri = Uri.parse(imagePath.getString("imageUri"));

        Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
                .setData(imageUri)
                .build();

        startActivityForResult(imageEditorIntent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent mainPage = new Intent(getApplicationContext(),MainActivity.class);

        if (id == R.id.action_home) {
            startActivity(mainPage);
        }

        if (id == R.id.action_camera) {
            mainPage.putExtra("action", "start_camera");
            startActivity(mainPage);
        }

        if (id == R.id.action_gallery) {
            mainPage.putExtra("action", "start_gallery");
            startActivity(mainPage);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case 1:
                    Uri editedImageUri = data.getData();
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
