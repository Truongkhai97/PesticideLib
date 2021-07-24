package com.example.pesticidelib.utilities;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

public class CountDown extends CountDownTimer {

    Context context;
    public CountDown(long millisInFuture, long countDownInterval , Context ctx) {
        super(millisInFuture, countDownInterval);

        this.context = ctx;

/*
millisInFuture : The number of millis in the future from the call to start() until the countdown is done and onFinish() is called.
countDownInterval : The interval along the way to receive onTick(long) callbacks.

*/
    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub

        Toast.makeText(context ,"Done", Toast.LENGTH_LONG);

    }

    @Override
    public void onTick(long millisUntilFinished) {
        // TODO Auto-generated method stub

    }

}