package com.tdj_sj_webandroid.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tdj_sj_webandroid.MainTabActivity;

import static android.provider.Telephony.Sms.Intents.SECRET_CODE_ACTION;

public class SecretCodeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && SECRET_CODE_ACTION.equals(intent.getAction())) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setClass(context, MainTabActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
