package com.song.fileiodemo;

import android.content.Context;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * files目录下文件读写服务
 */
public class FileUtil {

    /**
     * 遍历files文件夹下的子文件夹。
     *
     * @param context context
     * @param path    files文件夹下的文件夹子地址
     */
    public static void travelDirectoryFromFiles(Context context, String path) {
        String completePath = context.getFilesDir().getAbsolutePath() + "/" + path; // 文件夹全量地址
        File file = new File(completePath);
        if (file.isDirectory()) { // 判断是否为文件夹
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) { // 判断文件夹是否为空
                for (File file1 : listFiles) { // 遍历文件夹
                    String name = file1.getName(); // 文件名称
                    Log.i("sgx", "name: " + name);
                }
            }
        }
    }

    /**
     * 读files文件夹下子文件文本数据。
     *
     * @param filePathName 文件路径及文件名
     * @return 读出的字符串
     */
    public static String readTextFromFiles(Context context, String filePathName) {
        try {
            String path = context.getFilesDir().getAbsolutePath() + "/" + filePathName;
            Log.i("sgx", "path: " + path);

            StringBuilder stringBuffer = new StringBuilder();

            // 打开文件输入流
            FileInputStream fileInputStream = new FileInputStream(path);
            int available = fileInputStream.available(); // 获取本地文件长度

            byte[] buffer = new byte[available];
            int len = fileInputStream.read(buffer);
            // 读取文件内容
            while (len > 0) {
                stringBuffer.append(new String(buffer, 0, len));
                // 继续将数据放到buffer中
                len = fileInputStream.read(buffer);
            }
            // 关闭输入流
            fileInputStream.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 写文件
     *
     * @param pathName - 带子目录的文件名
     * @param data     -- 写入的内容
     */
    public static void writeTextToFiles(Context context, String pathName, String data) {

        if (data == null || data.isEmpty()) {
            return;
        }
        String path = context.getFilesDir().getAbsolutePath() + "/" + pathName;
        Log.i("sgx", "写文件: " + path);

        try {
            File file = new File(path);
            // 如果有未创建多级目录，创建
            File dir = file.getParentFile();
            if (dir != null && !dir.exists()) {
                boolean created = dir.mkdirs();
                if (created) {
                    Log.i("sgx", "创建文件成功: " + path);
                } else {
                    Log.i("sgx", "创建文件失败: " + path);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            byte[] buffer = data.getBytes();

            if (buffer.length > 0) {
                dataOutputStream.write(data.getBytes());
            }

            dataOutputStream.flush();
            dataOutputStream.close();
            // 关闭流
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("sgx", "文件异常: FileNotFoundException");
        } catch (IOException e) {
            Log.e("sgx", "输入输出异常");
        }
    }

}
