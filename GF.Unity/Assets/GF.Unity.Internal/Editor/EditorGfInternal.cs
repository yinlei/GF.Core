using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Xml;

public class EditorGFInternal : EditorWindow
{
    static string mUnityPackagePath = "GF.UnityPackage/";

    //-------------------------------------------------------------------------
    [MenuItem("GF/GF.Native/导出UnityNativePackageAll")]
    static void exportGFUnityNativePackageAll()
    {
        string[] arr_assetpathname = new string[3];
        arr_assetpathname[0] = "Assets/GF.Native";
        arr_assetpathname[1] = "Assets/Plugins";
        arr_assetpathname[2] = "Assets/Test";

        AssetDatabase.ExportPackage(arr_assetpathname, mUnityPackagePath + "UnityNativePackageAll.unitypackage", ExportPackageOptions.Recurse);

        Debug.Log("Export UnityNativePackageAll.unitypackage Finished!");
    }

    //-------------------------------------------------------------------------
    [MenuItem("GF/GF.Native/导出UnityNativeDataEye")]
    static void exportGFUnityNativeDataEye()
    {
        string[] arr_assetpathname = new string[4];
        arr_assetpathname[0] = "Assets/GF.Native";
        arr_assetpathname[1] = "Assets/Plugins/Android";
        arr_assetpathname[2] = "Assets/Plugins/GF.Native/DataEye";
        arr_assetpathname[3] = "Assets/Plugins/GF.Native/SDKReceiveMono";
        AssetDatabase.ExportPackage(arr_assetpathname, mUnityPackagePath + "UnityNativeDataEye.unitypackage", ExportPackageOptions.Recurse);

        Debug.Log("Export UnityNativeDataEye.unitypackage Finished!");
    }

    //-------------------------------------------------------------------------
    [MenuItem("GF/GF.Native/导出UnityNativePay")]
    static void exportUnityNativePay()
    {
        string[] arr_assetpathname = new string[4];
        arr_assetpathname[0] = "Assets/GF.Native";
        arr_assetpathname[1] = "Assets/Plugins/Android";
        arr_assetpathname[2] = "Assets/Plugins/GF.Native/Pay";
        arr_assetpathname[3] = "Assets/Plugins/GF.Native/SDKReceiveMono";
        AssetDatabase.ExportPackage(arr_assetpathname, mUnityPackagePath + "UnityNativePay.unitypackage", ExportPackageOptions.Recurse);

        Debug.Log("Export UnityNativePay.unitypackage Finished!");
    }

    //-------------------------------------------------------------------------
    [MenuItem("GF/GF.Native/导出GFNativeBySelf")]
    static void exportGFNativeBySelf()
    {
        string[] arr_assetpathname = new string[4];
        arr_assetpathname[0] = "Assets/GF.Native";
        arr_assetpathname[1] = "Assets/Plugins/Android";
        arr_assetpathname[2] = "Assets/Plugins/GF.Native/Native";
        arr_assetpathname[3] = "Assets/Plugins/GF.Native/SDKReceiveMono";
        AssetDatabase.ExportPackage(arr_assetpathname, mUnityPackagePath + "GFNativeBySelf.unitypackage", ExportPackageOptions.Recurse);

        Debug.Log("Export GFNativeBySelf.unitypackage Finished!");
    }

    //-------------------------------------------------------------------------
    [MenuItem("GF/GF.Native/导出Speech")]
    static void exportBaiduSpeech()
    {
        string[] arr_assetpathname = new string[4];
        arr_assetpathname[0] = "Assets/GF.Native";
        arr_assetpathname[1] = "Assets/Plugins/Android";
        arr_assetpathname[2] = "Assets/Plugins/GF.Native/Speech";
        arr_assetpathname[3] = "Assets/Plugins/GF.Native/SDKReceiveMono";
        AssetDatabase.ExportPackage(arr_assetpathname, mUnityPackagePath + "Speech.unitypackage", ExportPackageOptions.Recurse);

        Debug.Log("Export BaiduSpeech.unitypackage Finished!");
    }

    //-------------------------------------------------------------------------
    [MenuItem("GF/导出GF.Unity.unitypackage")]
    static void exportGFUnityPackage()
    {
        string[] arr_assetpathname = new string[4];
        arr_assetpathname[0] = "Assets/GF.Unity";
        arr_assetpathname[1] = "Assets/Plugins/GF.Common";
        arr_assetpathname[2] = "Assets/Plugins/GF.Sqlite";
        arr_assetpathname[3] = "Assets/Plugins/GF.UnityPlugins";

        AssetDatabase.ExportPackage(arr_assetpathname, mUnityPackagePath + "GF.Unity.unitypackage", ExportPackageOptions.Recurse);

        Debug.Log("Export GF.Unity.unitypackage Finished!");
    }

    //-------------------------------------------------------------------------
    [MenuItem("GF/导出GF.Json.unitypackage")]
    static void exportGFJsonPackage()
    {
        string[] arr_assetpathname = new string[1];
        arr_assetpathname[0] = "Assets/Plugins/GF.Json";
        AssetDatabase.ExportPackage(arr_assetpathname, mUnityPackagePath + "GF.Json.unitypackage", ExportPackageOptions.Recurse);

        Debug.Log("Export GF.Json.unitypackage Finished!");
    }
}
