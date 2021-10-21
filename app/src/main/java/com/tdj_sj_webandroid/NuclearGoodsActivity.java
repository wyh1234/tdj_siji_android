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

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.adapter.NuclearGoodsAdapter;
import com.tdj_sj_webandroid.adapter.SearchPopAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CheckListBean;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.model.StorageManage;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.NuclearGoodsPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.PlayVoice;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuclearGoodsActivity extends BaseActivity<NuclearGoodsPresenter>{
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
    @BindView(R.id.tv_qx)
    TextView tv_qx;
    @BindView(R.id.tv_title1)
    TextView tv_title1;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_num_s)
    TextView tv_num_s;
    @BindView(R.id.iv_clean)
    ImageView iv_clean;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_unStock)
    TextView tv_unStock;
    @BindView(R.id.tv_inStock)
    TextView tv_inStock;
    @BindView(R.id.tv_isD)
    TextView tv_isD;
    @BindView(R.id.tv_isFirst)
    TextView tv_isFirst;
    @BindView(R.id.tv_customerName)
    TextView tv_customerName;
    @BindView(R.id.tv_customerId)
    TextView tv_customerId;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.ll_inStock)
    LinearLayout ll_inStock;
    @BindView(R.id.ll_unStock)
    LinearLayout ll_unStock;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.line3)
    View line3;

    private ArrayList<View> mViewsList;
    public int pageNo = 1;//翻页计数器
    private BroadcastReceiver scanReceiver;
    private List<StorageManage.ResInStockCheckedListBean> list = new ArrayList();
    private NuclearGoodsAdapter storageManagementAdapter;
    private int total = 0;
    private boolean b=false;
    private int num;
    private PopupWindow mPopWindow;

    private int mType = 0;
    private StorageManage mData;

    public StorageManage getData() {
        return mData;
    }

    public void setData(StorageManage data) {
        mData = data;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @OnClick({R.id.tv_qx,R.id.right_text,R.id.btn_back,R.id.iv_clean})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_qx:
                b=true;
                tv_qx.setTextColor(getResources().getColor(R.color.text_));
                break;
            case R.id.right_text:
                tv_msg.setVisibility(View.GONE);
                if (TextUtils.isEmpty(search_edit.getText().toString())){
                    return;
                }
                if (b){
                    mPresenter.get_scann(search_edit.getText().toString(),"out",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                }else {
                    mPresenter.get_scann(search_edit.getText().toString(),"in",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                }
                break;
            case R.id.btn_back:
                EventBus.getDefault().post(new Resume());
                finish();
                break;
            case R.id.iv_clean:
                search_edit.setText("");
                break;
        }
    }
    private boolean isScanner = false;
    @Override
    protected NuclearGoodsPresenter loadPresenter() {
        return new NuclearGoodsPresenter();
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
                        mPresenter.get_scann(scanResult_1,"out",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                    }else {
                        mPresenter.get_scann(scanResult_1,"in",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
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
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rk_list.setLayoutManager(layout);
        storageManagementAdapter=new NuclearGoodsAdapter(this,list);
        rk_list.setAdapter(storageManagementAdapter);
        getData(1);
    }
    protected  void getData(int pn){
        mPresenter.get_scann(pn,getIntent().getStringExtra("customer_id"));

    }
    @Override
    protected int getLayoutId() {
        return R.layout.nuclear_goods_layout;
    }


    public Context getContext() {
        return this;
    }


    public void get_scann_onError(ApiException e) {
        isScanner = false;
        if (b) {
            b = false;
            tv_qx.setTextColor(getResources().getColor(R.color.text_gonees));
        }
    }
    public void get_scann_Success(CustomApiResult<StorageManagement> result) {
        isScanner = false;
        tv_qx.setTextColor(getResources().getColor(R.color.text_gonees));
        if (result.getErr()==0){
            getData(1);
            if (b){
                b=false;
                if (list.size()>0){

                    Iterator<StorageManage.ResInStockCheckedListBean> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        StorageManage.ResInStockCheckedListBean item = iterator.next();
                        if (item.getSku().equals(result.getData().getSku())) {
                            iterator.remove();
                        }
                    }

                    showEmpty(list.size() <= 0);

                }
                PlayVoice.playVoice(this,R.raw.quxiaoshichenggonggai);

            }else {
                PlayVoice.playVoice(this,R.raw.hehuochenggong);
                refreshViewByData(result);
                showEmpty(list.size() <= 0);
            }
        }else {
            if (b){
                b=false;
            }
        }
        if (result.getErr()==1){
            PlayVoice.playVoice(this,R.raw.hehuoshibai);
        }
        if (result.getErr() == 8||result.getErr() == 2){
            PlayVoice.playVoice(this,R.raw.quxiaoshibaigai);
        }
        if (result.getErr() == 4){
            PlayVoice.playVoice(this,R.raw.saomashibaigai);
        }
        if (result.getErr() == 6) {
            PlayVoice.playVoice(this,R.raw.yijinghehuo);
        }
        if (result.getErr()==5){
            PlayVoice.playVoice(this,R.raw.chuanxian);
        }

        if (result.getErr()==13){
            PlayVoice.playVoice(this,R.raw.gaidingdanyiquxiao);
        }

        if (!GeneralUtils.isNullOrZeroLenght(result.getMsg())){
            tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(result.getMsg());
        }
        if (list.size()>0){
            mStateView.showContent();
        }else {
            mStateView.showEmpty();
        }
    }

    private void refreshViewByData(CustomApiResult<StorageManagement> result){
        boolean isCheck = false;
        boolean isEquals = false;
        StorageManagement resultData = result.getData();

        StorageManage data = getData();
        if (list.size() > 0){
            for (StorageManage.ResInStockCheckedListBean item : list) {
                if (item.getSku().equals(result.getData().getSku())) {
                    isEquals = true;
                    break;
                }
            }
            if (!isEquals){
                list.add(0, new StorageManage.ResInStockCheckedListBean(resultData.getQty(), resultData.getSpecification()
                        , resultData.getSku(), resultData.getProductName()));
            }
        }else {
            list.add(0,new StorageManage.ResInStockCheckedListBean(resultData.getQty(),resultData.getSpecification()
                    ,resultData.getSku(),resultData.getProductName()));
        }

        String sku = resultData.getSku().substring(0, 13);

        List<StorageManage.ResInStockCheckedListBean> resCheckedList = data.getResCheckedList();
        for (StorageManage.ResInStockCheckedListBean res : resCheckedList) {
            if (res.getSku().contains(sku)) {
                isCheck = true;
                break;
            }
        }

        if (isCheck){
            tv_unStock.setText(data.getUncheckedOrderNum() + " (" + (data.getUncheckedNum() -1) +")");
            tv_inStock.setText(data.getCheckedOrderNum() + " (" + (data.getCheckedNum() + 1) +")");
        }else {
            tv_unStock.setText((data.getUncheckedOrderNum() -1) + " (" + (data.getUncheckedNum() -1) +")");
            tv_inStock.setText((data.getCheckedOrderNum() + 1) + " (" + (data.getCheckedNum() + 1) +")");
            data.setUncheckedOrderNum(data.getUncheckedOrderNum() -1);
            data.setCheckedOrderNum(data.getCheckedOrderNum() + 1);
        }
        data.setUncheckedNum(data.getUncheckedNum() - 1);
        data.setCheckedNum(data.getCheckedNum() + 1);
        resCheckedList.add(new StorageManage.ResInStockCheckedListBean(resultData.getQty(),resultData.getSpecification()
                ,resultData.getSku(),resultData.getProductName()));
        setData(data);

    }

    public void get_scann__home_Success(CustomApiResult<StorageManage> response) {
        tv_title1.setText(response.getRegionNo());

        if (response.getErr()==0){
            mData = response.getData();

            if (mData != null) {
                if (mData.getIsC() == 2) {
                    tv_isD.setText("SV");
                } else if (mData.getIsC() == 0) {
                    switch (mData.getIsD()) {
                        case 0:
                            tv_isD.setVisibility(View.GONE);
                            break;
                        case 1:
                            tv_isD.setText("V");
                            break;
                    }
                } else {
                    tv_isD.setVisibility(View.GONE);
                }

                if (this.mData.getIsFirst() == 1) tv_isFirst.setVisibility(View.VISIBLE);
                else tv_isFirst.setVisibility(View.GONE);

                tv_customerId.setText(mData.getCustomerId() + "");
                tv_customerName.setText(mData.getCustomerName());
                tv_all.setText(mData.getOrderNum() + " (" + mData.getNum() + ")");
                tv_unStock.setText(mData.getUncheckedOrderNum() + " (" + mData.getUncheckedNum() + ")");
                tv_inStock.setText(mData.getCheckedOrderNum() + " (" + mData.getCheckedNum() + ")");
                list.clear();

                if (mType == 0) {
                    list.addAll(this.mData.getRes_list());
                }
            }
            showEmpty(list.size() <= 0);

        }
    }

    private void showEmpty(boolean isEmpty){
        if(isEmpty) mStateView.showEmpty();
        else mStateView.showContent();
        storageManagementAdapter.notifyDataSetChanged();

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
    public View getStateViewRoot() {
        return ll;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new Resume());
    }


    public void getStockListSuccess(CustomApiResult<CheckListBean> response) {
        if (response.getErr() == 0) {
            CheckListBean data = response.getData();
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
            } else {
                if (mPopWindow != null && mPopWindow.isShowing()) {
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
            // itA.next() 只能在外层循环里面调用1次
            for (int i = 0; i < listB.size(); i++)
            {
                if (temp.equals(listB.get(i)))
                // 你不该在这里多次调用itA.next()的
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

        rvPop.setLayoutManager(new LinearLayoutManager(NuclearGoodsActivity.this));
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
