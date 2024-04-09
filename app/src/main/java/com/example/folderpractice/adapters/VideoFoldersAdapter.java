package com.example.folderpractice.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.folderpractice.R;
import com.example.folderpractice.VideoFilesActivity;
import java.util.List;


public class VideoFoldersAdapter extends RecyclerView.Adapter<VideoFoldersAdapter.ViewHolder> {
    private List<String> folderPaths;
    private Context context;

    public VideoFoldersAdapter(List<String> folderPaths, Context context) {
        this.folderPaths = folderPaths;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String folderPath = folderPaths.get(position);
        String folderName = folderPath.substring(folderPath.lastIndexOf("/") + 1);

        holder.folderName.setText(folderName);
        holder.folderPath.setText(folderPath);
        holder.noOfFiles.setText(noOfFiles(folderPath) + " Videos");
        Log.d("VideoFoldersAdapter", "onBindViewHolder: "+noOfFiles(folderPath));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoFilesActivity.class);
                intent.putExtra("folderName", folderName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView folderName, folderPath, noOfFiles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.folderName);
            folderPath = itemView.findViewById(R.id.folderPath);
            noOfFiles = itemView.findViewById(R.id.noOfFiles);
        }
    }

    private int noOfFiles(String folderPath) {
        int fileCount = 0;

        String[] projection = {
                MediaStore.Video.Media.DATA
        };

        String selection = MediaStore.Video.Media.DATA + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + folderPath + "/%"};

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = context.getContentResolver().query(
                uri,
                projection,
                selection,
                selectionArgs,
                null
        );

        if (query != null) {
            fileCount = query.getCount();
            query.close();
        }

        return fileCount;
    }
}
