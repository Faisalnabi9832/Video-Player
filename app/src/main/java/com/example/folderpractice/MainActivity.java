package com.example.folderpractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.folderpractice.Model.MediaFiles;
import com.example.folderpractice.adapters.VideoFoldersAdapter;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1;

    private VideoFoldersAdapter videoFoldersAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MediaFiles> mediaFiles = new ArrayList<>();
    private HashSet<String> allFolderList = new HashSet<>(); // Use a HashSet to avoid duplicates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE_PERMISSION);
        } else {

            fetchFolders();
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch media files
                fetchFolders();
            } else {
                // Permission denied, handle accordingly (e.g., display a message)
            }
        }
    }

    private void fetchFolders() {
        allFolderList.clear();

        String[] projection = {
                MediaStore.Video.Media.DATA
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = MediaStore.Video.Media.DATA + " ASC";

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = getContentResolver().query(
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );

        if (query != null && query.moveToFirst()) {
            int pathColumn = query.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);

            do {
                String path = query.getString(pathColumn);
                String folderPath = path.substring(0, path.lastIndexOf("/"));
                if (!allFolderList.contains(folderPath)) {
                    allFolderList.add(folderPath);
                }
            } while (query.moveToNext());

            query.close();
        }
        Log.d("MainActivity", "allFolderList: " + allFolderList.toString());

        videoFoldersAdapter = new VideoFoldersAdapter(new ArrayList<>(allFolderList), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(videoFoldersAdapter);
    }
}