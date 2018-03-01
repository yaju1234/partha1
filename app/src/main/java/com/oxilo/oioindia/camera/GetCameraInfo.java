package com.oxilo.oioindia.camera;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 20/5/16.
 */
public class GetCameraInfo {


    public Bitmap getOrantationBitmap(String picturePath) {
        int orientation = 0;
        try {
            ExifInterface ei = new ExifInterface(picturePath);
            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        if ((bitmap.getHeight() + bitmap.getWidth()) > 3500) {
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    (bitmap.getWidth() / 3) ,
                    (bitmap.getHeight() / 3), true);

        }
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                bitmap = rotate(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmap = rotate(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmap = rotate(bitmap, 270);
                break;
        }

        return bitmap;
    }


    public Bitmap rotate(Bitmap src, float degree) {
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            src = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
                    matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return src;
    }

    public String SaveBitmapToInternal(Bitmap finalBitmap) {
        String FOLDER_NAME = "GroupShout";
        String FILE_NAME = "GroupShout" + System.currentTimeMillis();
        String root = Environment.getExternalStorageDirectory().getPath();

        String fname = FILE_NAME + ".jpg";
        File imageDirectory = new File(root + File.separator + FOLDER_NAME
                + File.separator);
        imageDirectory.mkdirs();
        File file = new File(imageDirectory, fname);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("=========FILEPATH " + file.getPath());
        return file.getPath();
    }


    public String getPath(Uri uri, Context mContext) {

        String picturePath = null;

        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = mContext.getContentResolver().query(uri,
                    projection, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            picturePath = cursor.getString(column_index);
            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            return uri.getPath();
        }
        return picturePath;
    }
}
