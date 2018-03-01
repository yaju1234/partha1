package com.oxilo.oioindia.event;


import com.oxilo.oioindia.camera.PhotoDetails;

/**
 * Created by root on 3/6/16.
 */
public class EventPhotoChosenFromGallery {

    private final PhotoDetails photoDetails;


    public EventPhotoChosenFromGallery(PhotoDetails photoDetails) {
        this.photoDetails = photoDetails;
    }

    public PhotoDetails getPhotoDetails() {
        return photoDetails;
    }
}
