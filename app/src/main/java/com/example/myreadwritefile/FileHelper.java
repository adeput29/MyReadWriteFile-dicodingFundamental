package com.example.myreadwritefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class FileHelper {

    private static final String TAG = FileHelper.class.getName();
    static void writeToFile(FileModel fileModel, Context context) {
        String filename = fileModel.getFilename();
        String fileContents = fileModel.getData();
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Writing file failed :", e);
        }
    }
    static FileModel readFromFile(Context context, String filename) {
        FileModel fileModel = new FileModel();
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            fileModel.setFilename(filename);
            fileModel.setData(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found :", e);
        } catch (IOException e) {
            Log.e(TAG, "Can not read file :", e);
        }
        return fileModel;
    }
}
