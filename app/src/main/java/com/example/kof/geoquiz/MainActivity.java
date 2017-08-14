package com.example.kof.geoquiz;

import android.content.Intent;
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
    private static final String CHEAT_INDEX = "cheat_index";
    private static final String VISIBLE_INDEX = "visible_index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton,mFalseButton,mCheatButton;
    private ImageButton mNextButton,mPreviousButton;
    private TextView mTextView,mPageTextView;
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private boolean mIsVisible;

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
        Log.d(TAG,"onCreat(Bundle) called!");
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mTextView = (TextView) findViewById(R.id.question_text_view);
        mPageTextView = (TextView) findViewById(R.id.page_text_view);
        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        mCheatButton.setOnClickListener(this);
        mTextView.setOnClickListener(this);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(CHEAT_INDEX);
            mIsVisible = savedInstanceState.getBoolean(VISIBLE_INDEX);
        }
        SetEnableVisible(mIsVisible);
        UpdateQuestionTextAtStart();
    }

    private void SetEnableVisible(boolean isVisible) {
        if (isVisible){
            mNextButton.setVisibility(View.VISIBLE);
            mPreviousButton.setVisibility(View.VISIBLE);
        }
        else {
            mNextButton.setVisibility(View.INVISIBLE);
            mPreviousButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState");
        outState.putInt(KEY_INDEX,mCurrentIndex);
        outState.putBoolean(CHEAT_INDEX,mIsCheater);
        outState.putBoolean(VISIBLE_INDEX,mIsVisible);
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
                mIsCheater = false;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                UpdateQuestionText();
                break;
            case R.id.previous_button:
                mIsCheater = false;
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
            case  R.id.cheat_button:
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(MainActivity.this,answerIsTrue);
//                startActivity(i);
                startActivityForResult(i,REQUEST_CODE_CHEAT);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode){
            case REQUEST_CODE_CHEAT:
                if (data == null) return;
                mIsCheater = CheatActivity.wasAnswerShown(data);
                break;
            default:
                break;
        }
    }

    private void CheckAnswer(boolean AnswerValue) {
        int MessageResId;

        if (mIsCheater) {
            MessageResId = R.string.judgement_toast;
        } else {
            if (mQuestionBank[mCurrentIndex].isAnswerTrue() == AnswerValue) {
                MessageResId = R.string.correct_toast;
            } else {
                MessageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, MessageResId, Toast.LENGTH_SHORT).show();
        mIsVisible = true;
        SetEnableVisible(mIsVisible);
    }

    private void UpdateQuestionText() {
        int QuestionId = mQuestionBank[mCurrentIndex].getTextResId();
        mTextView.setText(QuestionId);
        mPageTextView.setText(String.format("%s / %s",mCurrentIndex+1,mQuestionBank.length));

        mIsVisible = false;
        SetEnableVisible(mIsVisible);
    }

    private void UpdateQuestionTextAtStart() {
        int QuestionId = mQuestionBank[mCurrentIndex].getTextResId();
        mTextView.setText(QuestionId);
        mPageTextView.setText(String.format("%s / %s",mCurrentIndex+1,mQuestionBank.length));

        SetEnableVisible(mIsVisible);
    }
}
