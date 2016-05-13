package com.user.tcamera;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.user.tcamera.gallery.GalleryAdapter;
import com.user.tcamera.gallery.ImageModel;
import com.user.tcamera.gallery.RecyclerItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    GalleryAdapter mAdapter;
    RecyclerView mRecyclerView;

    ArrayList<ImageModel> data = new ArrayList<>();
    private String[] IMGS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        IMGS = ReadImages().toArray(new String[0]);

        for (int i = 0; i < IMGS.length; i++) {

            ImageModel imageModel = new ImageModel();
            imageModel.setName("Image " + i);
            imageModel.setUrl(IMGS[i]);
            data.add(imageModel);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);


        mAdapter = new GalleryAdapter(GalleryActivity.this, data);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(GalleryActivity.this, ImageEditorActivity.class);
                        intent.putExtra("imageUri", data.get(position).getUrl());
                        startActivity(intent);
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<String> ReadImages() {
        List<String> s = new ArrayList<String>();
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();

        String targetPath = ExternalStorageDirectoryPath ;

        File targetDirector = new File(targetPath);
        List<File> allFile = getListFiles(targetDirector);
        allFile.addAll(getListFiles(getFilesDir()));
        File[] files = allFile.toArray(new File[0]);

        Arrays.sort( files, new Comparator()
        {
            public int compare(Object o1, Object o2) {
                if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                    return -1;
                } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }
        });

        for (File file : files){
            s.add(file.getAbsolutePath());
        }

        return  s;
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".jpg")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
}
