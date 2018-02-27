package com.oxilo.oioindia.camera;

/**
 * Created by Ratul Ghosh<tibro4u@gmail.com> on 18/4/16.
 */
public class PhotoDetails {
    private final String absolutePath;
    private final int photoRotation;

    public PhotoDetails(String absolutePath, int photoRotation) {
        this.absolutePath = absolutePath;
        this.photoRotation = photoRotation;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public int getPhotoRotation() {
        return photoRotation;
    }
}
