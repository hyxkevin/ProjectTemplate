package com.luoxiang.weibo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * projectName: 	    WeiBo
 * packageName:	        com.luoxiang.weibo.utils
 * className:	        FileUtil
 * author:	            Luoxiang
 * time:	            2016/12/31	10:13
 * desc:	            删除文件
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2016/12/31
 * upDateDesc:	        TODO
 */

public class FileUtil {
    /**
     * 删除整个目录
     * @param path 路径
     */
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        //如果不删除文件夹目录 注释即可
        path.delete();
    }

    /**
     * 命令行模式删除 需要root
     * @param path 路径
     */
    public static void delDir(String path){
        String cmd = "rm -r " + path;
        Log.d(TAG, cmd);
        execCommand(cmd);
    }

    static void execCommand(String command){
        DataOutputStream outputStream = null;
        try {
            Process su = Runtime.getRuntime()
                                .exec("su");
            outputStream = new DataOutputStream(su.getOutputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.writeBytes(command + "\n");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 挂载文件
     * @param context 上下文
     * @param out 文件
     *            需要权限<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
     */
    public static void mountFile(Context context , File out){
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(out)));
    }
}
