package com.example.customview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

//SQLite import


//File->setting-> Instant Run 종료하니 실행 됬음
public class thirdActivity extends AppCompatActivity {

    ArrayList<regionData> coffees = new ArrayList<regionData>();
    ListView listView;
    regionDataAdapter adapter;

    String DB_NAME = "Db_PointArea.db";
    byte[] strMean;
    String strWord;
    InputStream is;
    static String x;
    String [] regionlist;
    String region;

    private void copyDatabase(File dbFile){
        try {
            String folderPath = "/data/data/" + getPackageName() + "/databases";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();
            is = getAssets().open(DB_NAME);
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
    private volatile static thirdActivity _instance;
    public  static thirdActivity inst()
    {
        if(_instance ==null)
        {
            synchronized (thirdActivity.class)
            {
                if(_instance == null)
                {
                    _instance = new thirdActivity();
                }
            }
        }
        return _instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent2 = getIntent();
        regionlist = intent2.getStringArrayExtra("regionlist");
        region = intent2.getStringExtra("region");

        File dbFile = getDatabasePath(DB_NAME);
        if(!dbFile.exists()) copyDatabase(dbFile);
        _instance = this;
        //sqlite part start
        x = region;
//        String[] point = {"경복궁","롯데타워","잠실타워","아리랑"};

        int count = 1;
        Log.v("클릭",x + "");
        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(
                "SELECT picture FROM Tb_PointArea WHERE ID = '"+ x +"';",new String[] {});
        if(cursor.getCount()>0){
//                byte[] bytes = new byte[cursor.getCount()];
            while (cursor.moveToNext()){
                strMean = cursor.getBlob(0);    //strMean type -> byte[]
                Drawable draw = new BitmapDrawable(getResources(),getAppImage(strMean));
//                    coffees.add(new regionData(strWord, draw));
                coffees.add(new regionData(regionlist[count], draw));
                count += 2;
            }
            cursor.close();
        }
        else {  }

        //sqlite part end
        listView = (ListView) findViewById(R.id.list_view1);

        adapter = new regionDataAdapter(this, 0, coffees);
        listView.setAdapter(adapter);

        Button nextBtn = (Button) findViewById(R.id.nextbutton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), fourthActivity.class);
                startActivity(intent);
            }
        });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());

    }

    public Bitmap getAppImage(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }
}