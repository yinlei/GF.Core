using UnityEngine;
using System.Collections;

public class NativeTakePhoto
{
    //-------------------------------------------------------------------------
    static INativeTakePhoto mINativeTakePhoto;

    //-------------------------------------------------------------------------
    static NativeTakePhoto()
    {
#if UNITY_ANDROID
        mINativeTakePhoto = new AndroidTakePhoto();
        Debug.Log("AndroidTakePhoto::");
#elif UNITY_IOS
        mINativeTakePhoto = new IOSTakePhoto();
        Debug.Log("IOSTakePhoto::");
#else
        Debug.LogError("Do not supported on this platform. ");
#endif
    }

    //-------------------------------------------------------------------------
    public static void takeNewPhoto(int photo_width, int photo_height, string photo_name)
    {
        mINativeTakePhoto.takeNewPhoto(photo_width, photo_height, photo_name);
    }

    //-------------------------------------------------------------------------
    public static void takeExistPhoto(int photo_width, int photo_height, string photo_name)
    {
        mINativeTakePhoto.takeExistPhoto(photo_width, photo_height, photo_name);
    }
}
