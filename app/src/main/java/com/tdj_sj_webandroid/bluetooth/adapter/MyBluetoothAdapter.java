package com.tdj_sj_webandroid.bluetooth.adapter;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.sax.StartElementListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;







import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.bluetooth.prt.HPRTHelper;
import com.example.bluetooth.prt.HPRTHelper.onConnect;
import com.example.bluetooth.prt.HidConncetUtil;
import com.tdj_sj_webandroid.LyManagementActivity;
import com.tdj_sj_webandroid.NuclearGoodsHotelActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.bluetooth.LyGoodsHotelActivity;
import com.tdj_sj_webandroid.utils.Constants;

/**
 * Created by liushen on 2016/5/31.
 */
public class MyBluetoothAdapter extends BaseAdapter {
    private List<BluetoothDevice> mList = new ArrayList<BluetoothDevice>();
    private Context mContext;
    private HidConncetUtil mHidConncetUtil;
	private HPRTHelper mHprt;
	private boolean mf;
    public MyBluetoothAdapter(List<BluetoothDevice> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
            this.mHidConncetUtil = new HidConncetUtil(mContext);
            mHprt = HPRTHelper.getHPRTHelper(mContext);
    }
    public MyBluetoothAdapter(Context mContext,boolean mf) {
        this.mContext = mContext;
        this.mf=mf;
            this.mHidConncetUtil = new HidConncetUtil(mContext);
        	mHprt = HPRTHelper.getHPRTHelper(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData(List<BluetoothDevice> bluetoothDevice){
    	mList.clear();
        mList.addAll(bluetoothDevice);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.blue_list_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        initData(position,viewHolder);
        return convertView;
    }

    private void initData(int position, final ViewHolder viewHolder) {
        final BluetoothDevice bluetoothDevice = mList.get(position);
        viewHolder.bluehandlename.setText(bluetoothDevice.getName());
        viewHolder.bluehandlebond.setText(bluetoothDevice.getAddress());


        viewHolder.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.connect.getText().equals("连接")){
                    mHprt.setDevice(bluetoothDevice);
                    mHprt.buleconnect(mHidConncetUtil,mContext,new onConnect() {
                        @Override
                        public void succeed() {
                            viewHolder.connect.setText("已连接");
                            Toast.makeText(mContext,"蓝牙连接成功",Toast.LENGTH_LONG).show();
                            if (mf){
                                Intent intent=new Intent((Activity)mContext, LyGoodsHotelActivity.class);
                                mContext.startActivity(intent);
                            }else {
                                mContext.startActivity(new Intent((Activity)mContext, LyManagementActivity.class));
                            }

                        }

                        @Override
                        public void failure() {
                            Toast.makeText(mContext,"蓝牙连接失败",Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (mf){
                        Intent intent=new Intent((Activity)mContext, LyGoodsHotelActivity.class);
                        mContext.startActivity(intent);
                    }else {
                        mContext.startActivity(new Intent((Activity)mContext, LyManagementActivity.class));
                    }
                }

            }
        });

    }
    /**
     * 判断是否配对并设置配置状态
     * @param bluetoothDevice
     */
    private void isBonded(BluetoothDevice bluetoothDevice,ViewHolder viewHolder){
        int state =  bluetoothDevice.getBondState();
        switch (state) {
            case BluetoothDevice.BOND_NONE:
                viewHolder.bluehandlebond.setText("未配对");
                break;
            case BluetoothDevice.BOND_BONDING:
                viewHolder.bluehandlebond.setText("配对中...");
                break;
            case BluetoothDevice.BOND_BONDED:
                viewHolder.bluehandlebond.setText("已配对");
                break;
        }
    }


    private  void   isConnected(final BluetoothDevice bluetoothDevice,final ViewHolder viewHolder){
        if (mHidConncetUtil != null) {
            mHidConncetUtil.getHidConncetList(new HidConncetUtil.GetHidConncetListListener() {
                @Override
                public void getSuccess(ArrayList<BluetoothDevice> list) {
                    //判断连接列表中是否有该设备
                    for(BluetoothDevice bluetoothDevice1:list){
                        if(bluetoothDevice.getAddress().equals(bluetoothDevice1.getAddress())){
                            viewHolder.connect.setText("HID已连接");
                            break;
                        }
                    }
                }
            });
        }
    }

    public class ViewHolder {
        public final TextView bluehandlename;
        public final TextView bluehandlebond;
        public final Button connect;
        public final View root;

        public ViewHolder(View root) {
            bluehandlename = (TextView) root.findViewById(R.id.blue_handle_name);
            bluehandlebond = (TextView) root.findViewById(R.id.blue_handle_bond);
            connect = (Button) root.findViewById(R.id.connect);
            this.root = root;
        }
    }
}
