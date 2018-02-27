package com.oxilo.oioindia.camera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Ratul Ghosh<tibro4u@gmail.com> on 18/4/16.
 */
public class GalleryImagePickerHelper {

    public Intent buildPickFileFromGalleryIntent(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        return intent;
    }

    public PhotoDetails processOnActivityResult(Activity activity, Intent data) {
        if (data == null) {
            return null;
        }

        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = activity.getContentResolver().query(selectedImage, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        final String picturePath = c.getString(columnIndex);
        c.close();

        int cameraPhotoRotation = ImageRotationDetectionHelper.getCameraPhotoOrientation(picturePath);

        if (cameraPhotoRotation == 0) {
            cameraPhotoRotation = ImageRotationDetectionHelper.getRotationFromMediaStore(activity, selectedImage);
        }
        c.close();
        return new PhotoDetails(picturePath, cameraPhotoRotation);

    }
}
