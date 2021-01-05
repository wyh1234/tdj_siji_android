package com.tdj_sj_webandroid.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;















import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.example.bluetooth.prt.HPRTHelper;
import com.example.bluetooth.prt.HidConncetUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.bluetooth.adapter.MyBluetoothAdapter;


public class MainActivity extends Activity {
    private BluetoothAdapter mBluetoothAdapter;
    private HidConncetUtil mHidConncetUtil;
    private BlueBroadcastReceiver mBlueBroadcastReceiver;
    private MyBluetoothAdapter mMyBluetoothAdapter;
    private ListView mListView;
	private HPRTHelper mHelper;
	private Context mContext;
	private TextView tv_nodevice;
	private ImageView btn_back;
	private List<BluetoothDevice> mList = new ArrayList<BluetoothDevice>();
	private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blue_activity_main);
        mContext = getApplicationContext();
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();
        initview();

        initBlue();
        
    }

    private void initview() {
        mListView = (ListView) findViewById(R.id.listview);
        tv_nodevice = (TextView) findViewById(R.id.tv_nodevice);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.startDiscovery();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getStringExtra("lyhh")!=null){
            flag=true;
        }
    }

    private void initPair(){
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"无可用蓝牙",Toast.LENGTH_LONG).show();
            return;
        }
        mList.clear();
        Set<BluetoothDevice> mSet = mBluetoothAdapter.getBondedDevices();
        final Iterator mIterator = mSet.iterator();
        while (mIterator.hasNext()) {
            BluetoothDevice mBluetoothDevice = (BluetoothDevice) mIterator
                    .next();
            if(mMyBluetoothAdapter==null){
                mMyBluetoothAdapter = new MyBluetoothAdapter(MainActivity.this,flag);
                mListView.setAdapter(mMyBluetoothAdapter);
            }
            mList.add(mBluetoothDevice);
        }
        if (mList.size()==0) {
        	tv_nodevice.setVisibility(View.VISIBLE);
		}else {
			mMyBluetoothAdapter.addData(mList);
	        mMyBluetoothAdapter.notifyDataSetChanged();
			tv_nodevice.setVisibility(View.GONE);
		}
    }

    private void initBlue() {
        mBlueBroadcastReceiver = new BlueBroadcastReceiver();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"无可用蓝牙",Toast.LENGTH_LONG).show();
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
            this.mHidConncetUtil = new HidConncetUtil(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initPair();
        mHelper = HPRTHelper.getHPRTHelper(MainActivity.this);
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBlueBroadcastReceiver);
    }

    public void registerReceiver() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        localIntentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        localIntentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        localIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        localIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        this.registerReceiver(mBlueBroadcastReceiver, localIntentFilter);
    }

    private class BlueBroadcastReceiver  extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(str)) {
            	Log.i("liushen", "onReceive" + str);
            	final BluetoothDevice localBluetoothDevice = (BluetoothDevice) intent
            			.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            	if (localBluetoothDevice != null) {
            		if(mMyBluetoothAdapter==null){
            			mMyBluetoothAdapter = new MyBluetoothAdapter(MainActivity.this,flag);
            			mListView.setAdapter(mMyBluetoothAdapter);
            		}
            	}
			}
        }
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
//    	if (mHelper!=null) {
//    		mHelper.disconnect(new onDisconnect() {
//
//				@Override
//				public void succeed() {
//					// TODO Auto-generated method stub
//
//				}
//			});
//		}
    }
}
