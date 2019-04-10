package model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileController {

    private static final String LOG_TAG = "Directory not created";

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");

        }
        return file;
    }

    public String readFile(Context context,String fileName){
        String lineData ="";
        try{
            FileInputStream fileInputStream = context.openFileInput(fileName);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

             lineData = bufferedReader.readLine();
        }catch (Exception e){
            Toast.makeText(context,"file error!",Toast.LENGTH_SHORT).show();
        }
        return lineData;
    }

    public void writeFile(Context context, String fileName, String data){
        File file = new File(context.getFilesDir(), fileName);
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(data);
            Toast.makeText(context,"write succesfull",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(context,"file error!",Toast.LENGTH_SHORT).show();
        }
    }


}
