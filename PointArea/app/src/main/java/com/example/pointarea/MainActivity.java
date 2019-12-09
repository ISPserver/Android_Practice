package com.example.pointarea;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;




public class MainActivity extends AppCompatActivity {
    String DB_NAME = "Db_PointArea.db";
    static String[] words;
    static String[] sum_alpha;

    private void copyDatabase(File dbFile){
        try {
            String folderPath = "/data/data" + getPackageName() + "/databases";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();
            InputStream is = getAssets().open(DB_NAME);
            OutputStream os = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) os.write(buffer);
            os.flush();
            is.close();
            os.close();
        }
        catch (Exception e)
        {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File dbFile = getDatabasePath(DB_NAME);
        if(!dbFile.exists()) copyDatabase(dbFile);
    }

    public void on7Random(final View view){
        Random random = new Random();
        int x = 1;
        Log.v("클릭",x + "");

        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(
                "SELECT name,picture FROM Tb_PointArea WHERE ID ='" + x + "';",null);


        cursor.moveToNext();
        final String strWord = cursor.getString(0);
        final String strMean = cursor.getString(1);
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        TextView tv2 = (TextView)findViewById(R.id.textView2);
        cursor.close();
        tv1.setText(strWord);
//        tv2.setText(strMean);


    }
}
