package com.example.customview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class secondActivity extends AppCompatActivity implements View.OnClickListener {
    private int mNumber = 0;
    private CheckBox mCheckBox;
    private EditText mEtCustomerName;
    private TextView mTvNumber;
    private TextView mTvSummary;
    int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        totalPrice = intent.getIntExtra( "totalPrice", 0 );

        TextView textView = (TextView) findViewById(R.id.second_price_text);
        String text = String.format( "가격: %d", totalPrice );
        textView.setText( text );
        mCheckBox = (CheckBox) findViewById(R.id.cream_check);
        mEtCustomerName = (EditText) findViewById(R.id.customer_name_edit);
        mTvNumber = (TextView) findViewById(R.id.number_text);
        mTvSummary = (TextView) findViewById(R.id.summary_text);
        //버튼리스너

        findViewById(R.id.minus_button).setOnClickListener(this);
        findViewById(R.id.plus_button).setOnClickListener(this);
        findViewById(R.id.order_button).setOnClickListener(this);

        Button nextBtn = (Button) findViewById(R.id.button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), fourthActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.minus_button:
                mNumber--;
                if( mNumber < 0){
                    mNumber = 0;
                }
                mTvNumber.setText( ""+ mNumber);
                break;
            case R.id.plus_button:
                mNumber++;
                mTvNumber.setText(""+ mNumber);
                break;
            case R.id.order_button:
                printSummary();
                break;

        }
    }

    private void printSummary() {
        int total = 500 * mNumber;
        if (mCheckBox.isChecked()) {
            int s = totalPrice;
            total += s;
        }
        else  {
            int s = totalPrice;
            total = s;
        }

        StringBuilder sb = new StringBuilder("주문요약");
        sb.append("\n주문번호 : ").append(mEtCustomerName.getText().toString());
        sb.append("\n샷 추가 수량 : ").append(mNumber);
        sb.append("\n샷 추가 여부 : ").append(mCheckBox.isChecked());
        sb.append("\n합계 : ").append(total);

        mTvSummary.setText(sb);
    }
}
