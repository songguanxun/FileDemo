package com.song.fileiodemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 文件读写示例。
 * <p>
 * 1. assets文件夹的文件读取；
 * 2. 沙盒内files文件夹内文件读写。
 *
 * @author songguanxun
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 读取assets文件夹点击监听
     */
    public void onClicked(View view) {
        Log.i("sgx", "读取assets文件夹点击监听");

        // 1.读取assets文件夹下json文件
        String name1 = AssetsUtil.getTextFromAssetsFile(this, "name1.json"); // 成功
        Log.i("sgx", "name1.json: " + name1);

        // 2.读取assets文件夹下txt文件
        String text = AssetsUtil.getTextFromAssetsFile(this, "text.txt"); // 成功
        Log.i("sgx", "text.txt  : " + text);

        // 3.读取assets文件夹下txt文件
        String aaa = AssetsUtil.getTextFromAssetsFile(this, "superhero/aaa.json"); //
        Log.i("sgx", "aaa.json  : " + aaa);

        Log.i("sgx", "--------------------------------------------");

        // 4.遍历assets文件夹下的某个子文件夹
        AssetsUtil.travelAssetsFile(this, "superhero"); // 成功

    }

    /**
     * 读取files文件夹点击监听
     */
    public void onClicked2(View view) {
        Log.i("sgx", "读取files文件夹点击监听");
        FileUtil.travelDirectoryFromFiles(getApplicationContext(), "superhero"); // 成功

        Log.i("sgx", "--------------------------------------------");

        String name1 = FileUtil.readTextFromFiles(getApplicationContext(), "name1.json"); // 成功
        Log.i("sgx", "name1.json : " + name1);

        String profile1 = FileUtil.readTextFromFiles(getApplicationContext(),
                "superhero/dc/profile1.json"); // 成功
        Log.i("sgx", "superhero/dc/profile1 : " + profile1);

    }

    /**
     * 写入files文件夹点击监听
     */
    public void onClicked3(View view) {
        Log.i("sgx", "写入files文件夹点击监听");
        EditText editText = findViewById(R.id.editText);
        String content = editText.getText().toString();
        Log.i("sgx", "content : " + content);

        FileUtil.writeTextToFiles(getApplicationContext(), "my_data.txt", content); // 成功
        FileUtil.writeTextToFiles(getApplicationContext(), "data/my_data2.txt", content); // 成功

    }

    /**
     * 拷贝assets文件到files文件夹点击监听，地址目录相同的情况。
     */
    public void onClicked4(View view) {
        Log.i("sgx", "拷贝assets文件到files文件夹点击监听，地址目录相同的情况");

        // 测试，assets文件到files文件夹，地址目录相同的情况。

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(), "name1.json"); // 成功

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(), "superhero/aaa.json"); // 成功


    }

    /**
     * 拷贝assets文件到files文件夹点击监听，地址目录不同的情况。
     */
    public void onClicked5(View view) {
        Log.i("sgx", "拷贝assets文件到files文件夹点击监听，地址目录不同的情况");

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(), "name1.json", ""); // 成功

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(), "name1.json", "name1.json"); // 成功

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(), "name1.json", "my_name.json"); // 成功

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(),
                "name1.json", "collection/superhero/my_name.json"); // 成功

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(),
                "superhero/dc/profile1.json", "superhero/dc/profile1.json"); // 成功

        AssetsUtil.copyAssetsFileToFiles(getApplicationContext(),
                "superhero/dc/profile1.json", "collection/superhero/profile1.json"); // 成功
    }

    /**
     * 拷贝assets文件夹到files文件夹点击监听，地址目录相同的情况。
     */
    public void onClicked6(View view) {
        Log.i("sgx", "拷贝assets文件夹到files文件夹点击监听，地址目录相同的情况");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(), "superhero"); // 成功

        Log.i("sgx", "--------------------------------------------");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(), "superhero/dc"); // 成功

        Log.i("sgx", "--------------------------------------------");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(), "superhero/marvel"); // 成功
    }

    /**
     * 拷贝assets文件夹到files文件夹点击监听，地址目录不同的情况。
     */
    public void onClicked7(View view) {
        Log.i("sgx", "拷贝assets文件夹到files文件夹点击监听，地址目录不同的情况");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(), "superhero", "my_superhero"); // 成功

        Log.i("sgx", "--------------------------------------------");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(), "superhero", "superhero"); // 成功

        Log.i("sgx", "--------------------------------------------");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(),
                "superhero/dc", "superhero/dc"); // 成功

        Log.i("sgx", "--------------------------------------------");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(),
                "superhero/marvel", "my_superhero/marvel"); // 成功

        Log.i("sgx", "--------------------------------------------");

        AssetsUtil.copyAssetsDirToFiles(getApplicationContext(),
                "superhero/marvel", "hero/superhero/my_marvel"); //
    }

}
