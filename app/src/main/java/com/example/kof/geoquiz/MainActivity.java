package com.example.kof.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTrueButton,mFalseBtton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseBtton = (Button) findViewById(R.id.false_button);
        mTrueButton.setOnClickListener(this);
        mFalseBtton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId())
        {
            case R.id.true_button:
                Toast.makeText(this,R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.false_button:
                Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
