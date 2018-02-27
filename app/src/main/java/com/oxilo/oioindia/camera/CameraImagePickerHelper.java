package com.oxilo.oioindia.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import java.util.UUID;

/**
 * Created by Ratul Ghosh<tibro4u@gmail.com> on 18/4/16.
 */
public class CameraImagePickerHelper {

    private Uri imageFileUri;

    public Intent buildTakePhotoIntent(Activity activity) {
        String storageState = Environment.getExternalStorageState();

        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, UUID.randomUUID().toString() + ".jpg");

            imageFileUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            return i;
        } else {
            new AlertDialog.Builder(activity).setMessage("External Storage (SD Card) is required.\n\nCurrent state: " + storageState)
                    .setCancelable(true).create().show();
            return null;
        }
    }

    public PhotoDetails processOnActivityResult(Activity activity, Intent data) {

        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(activity, imageFileUri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();

        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        // Rotation is stored in an EXIF tag, and this tag seems to return 0 for URIs.
        // Hence, we retrieve it using  absolute path instead!

        String takePhotoFile = cursor.getString(column_index_data);
        int cameraPhotoRotation = 0;

        if (takePhotoFile != null) {
            cameraPhotoRotation = ImageRotationDetectionHelper.getCameraPhotoOrientation(takePhotoFile);
        }

        if (cameraPhotoRotation == 0) {
            cameraPhotoRotation = ImageRotationDetectionHelper.getRotationFromMediaStore(activity, imageFileUri);
        }
        cursor.close();
        return new PhotoDetails(takePhotoFile, cameraPhotoRotation);
    }

}
