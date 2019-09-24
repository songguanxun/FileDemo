package com.song.fileiodemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Assets读取文件工具类
 */
public class AssetsUtil {

    /**
     * 遍历读取assets文件夹下的文件。
     *
     * @param path assets中的文件或文件夹路径
     */
    public static void travelAssetsFile(Context context, String path) {
        if (context == null) {
            Log.i("sgx", "context为空");
            return;
        }

        if (path == null || path.equals("")) {
            Log.i("sgx", "传入地址为空");
            return;
        }

        AssetManager assetManager = context.getAssets();
        try {
            String[] fileList = assetManager.list(path); // path文件夹下的子文件列表
            if (fileList != null) {
                // 步骤1：遍历Assets中的文件夹path
                for (String item : fileList) {
                    String subPath = path + '/' + item; // path文件夹下的子文件名称
                    String[] grandChildren = assetManager.list(subPath);
                    if (grandChildren != null) {
                        if (grandChildren.length > 0) { // 是文件夹
                            travelAssetsFile(context, subPath);
                        } else { // 是文件
                            Log.i("sgx", "assets文件: " + subPath);
                            Log.i("sgx", "assets文内容: " + getTextFromAssetsFile(context, subPath));
                            Log.i("sgx", "--------------------------------------------");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取获取assets目录下的单个文件的文本数据。
     *
     * @param path 文件在assets文件夹中的相对地址。
     * @return 文件的文本数据，字符串格式。
     */
    public static String getTextFromAssetsFile(Context context, String path) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(path));
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 拷贝assets文件到files文件夹
     * <p>
     * 注：文件夹在files中的目录结构与assets中的相同。
     *
     * @param context context
     * @param path    assets文件地址
     */
    public static void copyAssetsFileToFiles(Context context, String path) {
        copyAssetsFileToFiles(context, path, path);
    }

    /**
     * 拷贝assets文件到files文件夹
     * <p>
     * 注：文件夹在files中的目录结构可以与assets中的不同。
     *
     * @param context    Context，请使用getApplicationContext()获取
     * @param sourcePath 文件原路径，如："superhero/dc/profile1.json"
     * @param targetPath 文件目标路径，如："collection/superhero/my_profile1.json"
     */
    public static void copyAssetsFileToFiles(Context context, String sourcePath, String targetPath) {
        if (context == null) {
            Log.i("sgx", "context为空");
            return;
        }

        if (sourcePath == null || sourcePath.isEmpty() || targetPath == null || targetPath.isEmpty()) {
            Log.e("sgx", "原路径或目标路径为空或空字符串");
            return;
        }

        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        try {

            // 输入流
            inputStream = new BufferedInputStream(context.getAssets().open(sourcePath));

            // 输出文件
            File targetFile = new File(context.getFilesDir() + "/" + targetPath);

            // 上级目录不存在，创建多级目录
            File targetDir = targetFile.getParentFile();
            if (targetDir != null && !targetDir.exists()) {
                targetDir.mkdirs();
            }

            boolean created = targetFile.createNewFile();
            if (created) {
                Log.i("sgx", "本文件不存在，且已成功创建: " + targetPath);
            } else {
                Log.i("sgx", "本文件已存在: " + targetPath);
            }
            // 输出流
            outputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
            int bufferNumber = inputStream.available();
            byte[] buf = new byte[bufferNumber];
            int i;
            // 写入
            while ((i = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inputStream);
            close(outputStream);
        }

    }


    private static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝assets文件夹到files文件夹
     * <p>
     * 注：文件夹在files中的目录结构与assets中的相同。
     *
     * @param context context
     * @param path    assets文件夹地址
     */
    public static void copyAssetsDirToFiles(Context context, String path) {
        copyAssetsDirToFiles(context, path, path);
    }

    /**
     * 拷贝assets文件夹到files文件夹
     * <p>
     * 注：文件夹在files中的目录结构与assets中的可以相同。
     *
     * @param context       Context，请使用getApplicationContext()获取
     * @param sourceDirPath 源文件夹地址（assets中），如："superhero"
     * @param targetDirPath 目标文件夹地址（files中），如："my_superhero"
     */
    public static void copyAssetsDirToFiles(Context context, String sourceDirPath, String targetDirPath) {
        if (context == null) {
            Log.i("sgx", "context为空");
            return;
        }

        if (sourceDirPath == null || sourceDirPath.isEmpty()
                || targetDirPath == null || targetDirPath.isEmpty()) {
            Log.i("sgx", "源文件或目标文件夹地址为空或空字符串");
            return;
        }

        AssetManager assetManager = context.getAssets();
        try {
            String[] fileList = assetManager.list(sourceDirPath); // sourceDirPath文件夹下的子文件列表
            if (fileList != null) {
                // 步骤1：遍历Assets中的文件夹path
                for (String item : fileList) {
                    String sourceSubPath = sourceDirPath + '/' + item; // sourceDirPath文件夹下的子文件名称
                    String targetSubPath = targetDirPath + "/" + item; // targetDirPath文件夹下的子文件名称

                    String[] grandChildren = assetManager.list(sourceSubPath);
                    if (grandChildren != null) {
                        if (grandChildren.length > 0) { // 是文件夹
                            copyAssetsDirToFiles(context, sourceSubPath, targetSubPath);
                        } else { // 是文件
                            copyAssetsFileToFiles(context, sourceSubPath, targetSubPath);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
