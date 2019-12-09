package com.example.busstation;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    TextView tv;
    String strHtml ="";
    String strHtml2 ="";
    String strLine2;
    BufferedReader in2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=(TextView)findViewById(R.id.textView2);
        tv.setText("확인 결과");


    }

    public void onClick(View view){
    Handler h = new Handler(){
        public void handleMessage(Message msg){
            HTMLParsing();
        }
    };
        new WorkerThread(h).start();
    }

    class WorkerThread extends Thread{
        Handler h; String strLine;
        WorkerThread(Handler h) {this.h = h;}
        public void run(){
            try{
                URL bURL = new URL("http://its.wonju.go.kr/map/RoutePosition.do?route_id=251000154");
                in2 = new BufferedReader(new InputStreamReader(bURL.openStream() ));
                while((strLine2 = in2.readLine()) != null){
                    if(strLine2.contains("LOCAL_X"))
                        strHtml2 += strLine2;
                }
                in2.close();
                h.sendMessage(new Message());
            }catch (Exception e){
                tv.setText("네트워크 에러: "+ e.toString() );
            }
        }
    }
    void HTMLParsing(){
        try {
            String strContent = "";
            String arr2 ="";
            String arr3 ="";
            int vrf_start, vrf_end = 0;  String verifiy = "";
            int vrf_start2, vrf_end2 = 0;  String verifiy2 = "";


            String[] num = strHtml2.split("\\}");

            for(int k=0; k<num.length-1; k++){
                vrf_start = strHtml2.indexOf("LOCAL_X", vrf_end);
                vrf_end = strHtml2.indexOf("LOCAL_Y",vrf_start);
                verifiy += strHtml2.substring(vrf_start+9,vrf_end-2)+" ";
            }

            String[] arr = verifiy.split(" ");
            String[] arr_copy = new String[arr.length];
            for(int j=0; j<arr.length; j++){
                    arr_copy[j] = arr[j];
                    arr2 += arr_copy[j]+"";
            }

            // local_x는 위 , local_y 아래
            for(int k=0; k<num.length-1; k++){
                vrf_start2 = strHtml2.indexOf("LOCAL_Y", vrf_end2);
                vrf_end2 = strHtml2.indexOf("PLATE_NO",vrf_start2);
                verifiy2 += strHtml2.substring((vrf_start2)+9,(vrf_end2)-2)+" ";
            }

            String[] arr_y = verifiy2.split(" ");
            String[] arr_copy2 = new String[arr.length];
            for(int j=0; j<arr.length; j++){
                arr_copy2[j] = arr_y[j];
                arr3 += arr_copy2[j]+"";
            }



            tv.setText(arr2+"  "+arr3);
        }catch (Exception e){
            tv.setText("파싱 에러: "+e.toString());
        }
    }
}

//
//package com.example.busstation;
//
//
//        import android.os.Handler;
//        import android.os.Message;
//        import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.TextView;
//        import java.io.BufferedReader;
//        import java.io.InputStreamReader;
//        import java.net.URL;
//        import java.util.Arrays;
//
//
//public class MainActivity extends AppCompatActivity {
//    TextView tv;
//    String strHtml ="";
//    String strHtml2 ="";
//    String strLine2;
//    BufferedReader in2;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        tv=(TextView)findViewById(R.id.textView2);
//        tv.setText("확인 결과");
//
//
//    }
//
//    public void onClick(View view){
//        Handler h = new Handler(){
//            public void handleMessage(Message msg){
//                HTMLParsing();
//            }
//        };
//        new WorkerThread(h).start();
//    }
//
//    class WorkerThread extends Thread{
//        Handler h; String strLine;
//        WorkerThread(Handler h) {this.h = h;}
//        public void run(){
//            try{
//                URL aURL = new URL("http://its.wonju.go.kr/map/StopListByRoute.do?route_id=251000154");
//                BufferedReader in = new BufferedReader(new InputStreamReader(aURL.openStream() ));
//                while((strLine = in.readLine()) != null)
//                    if(strLine.contains("STATION_NM"))
//                        strHtml += strLine;
//                in.close();
//
//                URL bURL = new URL("http://its.wonju.go.kr/map/RoutePosition.do?route_id=251000154");
//                in2 = new BufferedReader(new InputStreamReader(bURL.openStream() ));
//                while((strLine2 = in2.readLine()) != null){
//                    if(strLine2.contains("STATION_ORD"))
//                        strHtml2 += strLine2;
//                }
//                in2.close();
//                h.sendMessage(new Message());
//            }catch (Exception e){
//                tv.setText("네트워크 에러: "+ e.toString() );
//            }
//        }
//    }
//    void HTMLParsing(){
//        try {
//            String strContent = "";
//            int nm_start, nm_end = 0;
//            String arr2 ="";
//            int vrf_start, vrf_end = 0;  String verifiy = "";
//            int count =0;
//
//            String[] num = strHtml2.split("\\}");
//
//            for(int k=0; k<num.length-1; k++){
//                vrf_start = strHtml2.indexOf("STATION_ORD", vrf_end);
//                vrf_end = strHtml2.indexOf("TURN_NODE_ORD",vrf_start);
//                verifiy += strHtml2.substring(vrf_start+13,vrf_end-2)+" ";
//            }
//
//            String[] arr = verifiy.split(" ");
//            String[] arr_copy = new String[arr.length];
//            for(int j=0; j<arr.length; j++){
//                arr_copy[j] = arr[j];
//                arr2 += arr_copy[j]+"";
//            }
//
//            for(int i=1; i<=103; i++){
//                nm_start = strHtml.indexOf("STATION_NM", nm_end);
//                nm_end = strHtml.indexOf("STATION_ORD",nm_start);
//
//                if(Arrays.asList(arr_copy).contains(Integer.toString(i))){
//                    strContent += "("+i+")"+strHtml.substring(nm_start+13,nm_end-3)+"<ㅡㅡ현재 버스 위치"+"\n";
//                }
//                else  strContent += "("+i+")"+strHtml.substring(nm_start+13,nm_end-3)+"\n";
//
//
//            }
//            tv.setText(strContent);
//        }catch (Exception e){
//            tv.setText("파싱 에러: "+e.toString());
//        }
//    }
//}
//
//
