package com.IDB.idbasket.fragmentClasses;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.IDB.idbasket.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class HomeScreen extends Fragment {
CardView cv3;
RelativeLayout cardOne,cardTwo,cardThree;
TextView cardThreeShare,cardTwoShare,cardOneShare,app_title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_screen,container,false);
        cv3 = layout.findViewById(R.id.cv3);
        cardOne = layout.findViewById(R.id.cardOne);
        cardTwo = layout.findViewById(R.id.cardTwo);
        cardThree = layout.findViewById(R.id.cardThree);
        cardThreeShare  = layout.findViewById(R.id.cardThreeShare);
        cardTwoShare = layout.findViewById(R.id.cardTwoShare);
        cardOneShare = layout.findViewById(R.id.cardOneShare);
        app_title = getActivity().findViewById(R.id.app_title);
        app_title.setText("Create Card");
        cardThreeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap temp = getScreenShot(cardThree);
                File directory = store(temp,"CardThree",getActivity());
                Toast.makeText(getActivity(),directory.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                shareImage(temp);
            }
        });
        cardTwoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap temp = getScreenShot(cardTwo);
                File directory = store(temp,"CardTwo",getActivity());
                Toast.makeText(getActivity(),directory.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                shareImage(temp);
            }
        });
        cardOneShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap temp = getScreenShot(cardOne);
                File directory = store(temp,"CardOne",getActivity());
                Toast.makeText(getActivity(),directory.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                shareImage(temp);
            }
        });
        return layout;
    }
    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas snap = new Canvas(bitmap);
        view.draw(snap);

        return bitmap;
    }
    public static File store(Bitmap bm, String fileName,Context ctx){
          String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() ;
        File dir = new File(ctx.getFilesDir(),fileName);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(ctx.getFilesDir(), fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }
    private void shareImage(Bitmap file){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        file.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                file, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }
}
