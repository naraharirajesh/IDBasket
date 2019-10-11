package com.IDB.idbasket.fragmentClasses;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.IDB.idbasket.R;
import com.IDB.idbasket.Utils.SaveData;
import com.IDB.idbasket.model.CreationData;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyCreations extends Fragment {
    RecyclerView myCreationsList;
    TextView app_title;
    List<CreationData> urlsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.my_creation_layout,container,false);
        urlsList = new ArrayList<>();
        myCreationsList = layout.findViewById(R.id.myCreationsList);
        app_title = getActivity().findViewById(R.id.app_title);
        app_title.setText("My Creations");
        urlsList = new SaveData(getActivity()).getRecordList();
        if(urlsList != null && urlsList.size() > 0){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            myCreationsList.setLayoutManager(layoutManager);
            myCreationsList.setAdapter(new MyCreationList());
        }else{
            Toast.makeText(getActivity(),"No Records",Toast.LENGTH_SHORT).show();
        }
        return layout;
    }
    class MyCreationList extends RecyclerView.Adapter<MyCreationList.ItemRowHolder> {
        @Override
        public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.my_creation_row_layout, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {
//            File f = new File(String.valueOf(urlsList.get(i).getImageUri()));

//            Picasso.with(getActivity()).load(f).into(itemRowHolder.row_image);
//            Picasso.with(getActivity()).load(Uri.parse(urlsList.get(i).getImageUri())).into(itemRowHolder.row_image);
            File imgFile = new  File(getRealPathFromUri(getActivity(),Uri.parse(urlsList.get(i).getImageUri())));

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                itemRowHolder.row_image.setImageBitmap(myBitmap);

            }
            itemRowHolder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareImage(Uri.parse(urlsList.get(i).getImageUri()));
                }
            });

        }

        @Override
        public int getItemCount() {
            return urlsList.size();
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {

            protected ImageView row_image;
            Button share;
            public ItemRowHolder(View view) {
                super(view);
                row_image = view.findViewById(R.id.row_image);
//                delete = view.findViewById(R.id.delete);
                share = view.findViewById(R.id.share);


            }

        }
    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private void shareImage(Uri imageUri){

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }
}
