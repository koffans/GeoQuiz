package com.example.kof.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.kof.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.kof.geoquiz.anwer_shown";
    private boolean mAnswerIsTrue;
    private Button mCheatButton;
    private TextView mAnswerTextView;

    public static Intent newIntent(Context packageContext,boolean answerIsTrue) {
        Intent i = new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mCheatButton = (Button) findViewById(R.id.show_answer_button);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mCheatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_answer_button:
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                }
                else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShowResult(true);
                finish();
                break;
            default:
                break;
        }
    }

    private void setAnswerShowResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,true);
        setResult(RESULT_OK,data);
    }
}
