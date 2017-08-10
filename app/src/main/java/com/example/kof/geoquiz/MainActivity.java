package com.example.kof.geoquiz;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton,mFalseButton;
    private ImageButton mNextButton,mPreviousButton;
    private TextView mTextView,mPageTextView;
    private int mCurrentIndex = 0;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }
        Log.d(TAG,"onCreat(Bundle) called!");
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mTextView = (TextView) findViewById(R.id.question_text_view);
        mPageTextView = (TextView) findViewById(R.id.page_text_view);
        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        mTextView.setOnClickListener(this);

        UpdateQuestionText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState");
        outState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called!");
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId())
        {
            case R.id.true_button:
                CheckAnswer(true);
                break;
            case R.id.false_button:
                CheckAnswer(false);
                break;
            case R.id.next_button:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                UpdateQuestionText();
                break;
            case R.id.previous_button:
                mCurrentIndex = mCurrentIndex - 1;
                if (mCurrentIndex == -1) {
                    mCurrentIndex = mQuestionBank.length - 1;
                }

                UpdateQuestionText();
                break;
            case R.id.question_text_view:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                UpdateQuestionText();
                break;
            default:
                break;
        }
    }

    private void CheckAnswer(boolean AnswerValue)
    {
        int MessageResId;
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == AnswerValue){
            MessageResId = R.string.correct_toast;
        }
        else {
            MessageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this,MessageResId, Toast.LENGTH_SHORT).show();
    }

    private void UpdateQuestionText() {
        int QuestionId = mQuestionBank[mCurrentIndex].getTextResId();
        mTextView.setText(QuestionId);
        mPageTextView.setText(String.format("%s / %s",mCurrentIndex+1,mQuestionBank.length));
    }
}
