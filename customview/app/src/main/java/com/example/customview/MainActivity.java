package com.example.customview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String region;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //regionText
        final TextView regionText = (TextView)findViewById(R.id.regiontext);

        //Spinner
        final Spinner spinner = findViewById(R.id.spinner1);
        String[] str = getResources().getStringArray(R.array.region);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, str);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(spinner.getSelectedItemPosition() > 0)
                {
                    region = spinner.getSelectedItem().toString();
                    regionText.setText(region);
                }
                else if(spinner.getSelectedItemPosition() == 0){
                    regionText.setText("도시를 선택하세요!");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //searchBt 검색버튼
        Button searchbt = (Button)findViewById(R.id.searchbt);
        final Intent intent = new Intent(getApplicationContext(), SearchActivity.class);

        searchbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regionText.getText().toString()==null || regionText.getText().toString() == "도시를 선택하세요!"){
                    Toast.makeText(getApplicationContext(), "도시를 선택하세요!", Toast.LENGTH_SHORT).show();
                }
                else {
                    intent.putExtra("region", regionText.getText().toString());
                    startActivity(intent);
                }
            }
        });


    }

}