package com.tdj_sj_webandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.tdj_sj_webandroid.adapter.SearchPopAdapter;
import com.tdj_sj_webandroid.adapter.StorageManagementAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.StockListBean;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.StorageManagementPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.PlayVoice;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StorageManagementActivity extends BaseActivity<StorageManagementPresenter> implements  OnLoadmoreListener {
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.right_text)
    TextView right_text;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.rk_list)
    RecyclerView rk_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_qx)
    TextView tv_qx;
    @BindView(R.id.tv_title1)
    TextView tv_title1;
    @BindView(R.id.iv_clean)
    ImageView iv_clean;
    @BindView(R.id.ll)
    LinearLayout ll;

    public int pageNo = 1;//翻页计数器
    private BroadcastReceiver scanReceiver;
    private boolean b=false;
    private List<StorageManagement> list=new ArrayList();
    private StorageManagementAdapter storageManagementAdapter;
    private int total=0;
    private PopupWindow mPopWindow;

    @OnClick({R.id.tv_qx,R.id.right_text,R.id.btn_back,R.id.iv_clean})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_qx:
                b=true;
                tv_qx.setTextColor(getResources().getColor(R.color.text_));
                break;
            case R.id.right_text:
                if (TextUtils.isEmpty(search_edit.getText().toString())){
                    return;
                }
                if (b){
                    mPresenter.get_scann(search_edit.getText().toString(),"out");
                }else {
                    mPresenter.get_scann(search_edit.getText().toString(),"in");
                }
                break;
            case R.id.btn_back:
                finish();
                break;

            case R.id.iv_clean:
                search_edit.setText("");
                break;
        }
    }

    private boolean isScanner = false;

    @Override
    protected StorageManagementPresenter loadPresenter() {
        return new StorageManagementPresenter();
    }
    protected  void getData(int pn){
       mPresenter.get_scann(pn);

    }
    @Override
    protected void initData() {



        //新大陆
        Intent intent = new Intent("ACTION_BAR_SCANCFG");
        intent.putExtra("EXTRA_SCAN_MODE", 3);
        intent.putExtra("CODE_ID","EAN13");
        intent.putExtra("PROPERTY","TrsmtChkChar");
        intent.putExtra("VALUE","1");
        sendBroadcast(intent);
        scanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String scanResult_1 = intent.getStringExtra("SCAN_BARCODE1");
                final String scanResult_2 = intent.getStringExtra("SCAN_BARCODE2");
                final int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1); // -1:unknown
                final String scanStatus = intent.getStringExtra("SCAN_STATE");
                LogUtils.i(scanStatus);
                LogUtils.i(scanResult_1);
                if ("ok".equals(scanStatus)) {
                    //成功
                    LogUtils.i(scanResult_1);
                    isScanner = true;
                        search_edit.setText("");
                        search_edit.setText(scanResult_1);
                        search_edit.setSelection(search_edit.getText().length());
                    LogUtils.i(b);
                    if (b){
                        mPresenter.get_scann(scanResult_1,"out");
                    }else {
                        mPresenter.get_scann(scanResult_1,"in");
                    }


                } else {
                    //失败如超时等
                    LogUtils.i("scanStatus" + scanStatus);
                }
            }
        };

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (! isScanner) {
                    if (s.length() == 13) {
                        mPresenter.getStockList(s.toString(),"in");
                    }
                    if (s.length() == 0){
                        if (mPopWindow != null && mPopWindow.isShowing()){
                            mPopWindow.dismiss();
                            iv_clean.setClickable(true);
                            right_text.setClickable(true);
                            tv_qx.setClickable(true);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        refreshLayout.setOnLoadmoreListener(this);
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rk_list.setLayoutManager(layout);
        storageManagementAdapter=new StorageManagementAdapter(this,list);
        rk_list.setAdapter(storageManagementAdapter);
        getData(1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.storage_managemet_layout;
    }

    private void registerReceiver() {
        IntentFilter mFilter = new IntentFilter("nlscan.action.SCANNER_RESULT");
        registerReceiver(scanReceiver, mFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    private void unRegisterReceiver() {
        try {
           unregisterReceiver(scanReceiver);
        } catch (Exception e) {
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    public void get_scann_Success(CustomApiResult<StorageManagement> result) {
        isScanner = false;
        tv_qx.setTextColor(getResources().getColor(R.color.text_red));
        if (result.getErr()==0){
        if (b){
            b=false;
                if (list.size()>0){
                    Iterator<StorageManagement> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        StorageManagement item = iterator.next();
                        if (item.getSku().equals(result.getData().getSku())) {
                            iterator.remove();
                        }
                    }
                    showEmpty(list.size() <= 0);
                    tv_num.setText("入库计数："+(--total));
                }else showEmpty(true);
            PlayVoice.playVoice(this,R.raw.quxiaochenggong);
        }else {
            PlayVoice.playVoice(this,R.raw.rukuchenggong);
            if (result.getErr()==0){

                list.add(0,result.getData());
                showEmpty(false);
                tv_num.setText("入库计数："+(++total));

            }



        }
        }else {
            if (b){
                b=false;
            }
            showEmpty(list.size() <= 0);
        }
        if (result.getErr()==1){
            PlayVoice.playVoice(this,R.raw.saomashibai);
        }
        if (result.getErr()==2){
            PlayVoice.playVoice(this,R.raw.saomachaoqu);
        }
        if (result.getErr() == 8){
            PlayVoice.playVoice(this,R.raw.quxiaoshibai);
        }
        if (result.getErr() == 9){
            PlayVoice.playVoice(this,R.raw.yijiruku);

        }
        if (result.getErr()==13){
            PlayVoice.playVoice(this,R.raw.gaidingdanyiquxiao);
        }
        if (result.getErr()==15){
            PlayVoice.playVoice(this,R.raw.feidangtain);
        }
    }

    public Context getContext() {
        return this;
    }


    public void get_scann_onError(ApiException e) {
        isScanner = false;
        if (b) {
            b = false;
            tv_qx.setTextColor(getResources().getColor(R.color.text_red));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (GeneralUtils.isHideInput(view, ev)) {
                GeneralUtils. hideSoftInput(view.getWindowToken(),this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void get_scann__home_Success(CustomApiResult<List<StorageManagement>> response) {
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
        tv_title1.setText(response.getRegionNo());
        if (response.getErr()==0){

            if (response.getData().size()>0){
                list.addAll(response.getData());
                tv_num.setText("入库计数："+list.size());
                showEmpty(false);

            }else {
                showEmpty(true);
                if (response.getPn()!=1){
                    Toast.makeText(this,"数据加载完毕",Toast.LENGTH_LONG).show();
                }
            }
            total=response.getTotal();
            tv_num.setText("入库计数："+response.getTotal());

        }else {
            showEmpty(true);
            if (response.getPn()!=1){
                Toast.makeText(this,"数据加载完毕",Toast.LENGTH_LONG).show();
            }
        }


    }

    public View getStateViewRoot() {
        return ll;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
    }


    public void getStockListSuccess(CustomApiResult<StockListBean> response) {
        if (response.getErr() == 0){
        StockListBean data = response.getData();
        if ("1".equals(data.getIsMultiple())) {
            if (mPopWindow != null && mPopWindow.isShowing()) {
                mPopWindow.dismiss();
                iv_clean.setClickable(true);
                right_text.setClickable(true);
                tv_qx.setClickable(true);
            }
            int qty = data.getQty();
            List<String> inStockData = data.getInStockData();
            String trim = search_edit.getText().toString().trim();
            List<String> searchId = new ArrayList<>();
            if (qty > 0) {
                for (int i = 0; i < qty; i++) {
                    searchId.add(trim + "-" + (i + 1));
                }
            }

            //改版,说是已经入库的也展示在下面
//            List<String> listrem = listrem(searchId, inStockData);
//            if (listrem.size() == 0) return;
            if (searchId.size() == 0) return;
            initPop(searchId, search_edit);
        }else {
            if (mPopWindow != null && mPopWindow.isShowing()){
                mPopWindow.dismiss();
                iv_clean.setClickable(true);
                right_text.setClickable(true);
                tv_qx.setClickable(true);
            }
        }
        }else {
            if (mPopWindow != null && mPopWindow.isShowing()) {
                mPopWindow.dismiss();
                iv_clean.setClickable(true);
                right_text.setClickable(true);
                tv_qx.setClickable(true);
            }
        }
    }

    private void showEmpty(boolean isEmpty){
        if(isEmpty) mStateView.showEmpty();
        else mStateView.showContent();
        storageManagementAdapter.notifyDataSetChanged();

    }

    /**
     * 从listA里删除listB里有的数据
     * @param listA
     * @param listB
     * @return
     */
    public static List<String> listrem(List<String> listA,List<String> listB){
        for (Iterator<String> itA = listA.iterator(); itA.hasNext();)
        {
            String temp = itA.next();
            for (int i = 0; i < listB.size(); i++)
            {
                if (temp.equals(listB.get(i)))
                {
                    itA.remove();
                }
            }
        }
        return listA;
    }
    private void initPop(List<String> listrem,View v){
        View view = LayoutInflater.from(this).inflate(R.layout.search_pop_layout, null, false);
        RecyclerView rvPop = view.findViewById(R.id.rv_pop);
        NestedScrollView nestedscroll = view.findViewById(R.id.nestedscroll);
        LinearLayout ll_layout = view.findViewById(R.id.ll_layout);
        mPopWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setOutsideTouchable(false);
        mPopWindow.setFocusable(false);
        mPopWindow.setTouchable(true);

        mPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        mPopWindow.showAsDropDown(v, 0, 0);

        if(listrem.size() > 7){
            nestedscroll.setScrollbarFadingEnabled(false);
            ViewGroup.LayoutParams params = ll_layout.getLayoutParams();
            params.height = dp2px(40 * 7);
            ll_layout.setLayoutParams(params);
        }

        rvPop.setLayoutManager(new LinearLayoutManager(StorageManagementActivity.this));
        SearchPopAdapter adapter = new SearchPopAdapter(listrem);
        rvPop.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String obj = (String) adapter.getData().get(position);
                search_edit.setText("");
                search_edit.setText(obj);
                search_edit.setSelection(search_edit.getText().length());
                mPopWindow.dismiss();
                iv_clean.setClickable(true);
                right_text.setClickable(true);
                tv_qx.setClickable(true);
            }
        });

        if (mPopWindow.isShowing()){
            iv_clean.setClickable(false);
            right_text.setClickable(false);
            tv_qx.setClickable(false);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());

    }
}
