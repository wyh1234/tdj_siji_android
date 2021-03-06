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
import com.tdj_sj_webandroid.adapter.NuclearGoodsAdapter;
import com.tdj_sj_webandroid.adapter.SearchPopAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CheckListBean;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.DDJNuclearGoodsPresenter;
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

public class DDJNuclearGoodsActivity extends BaseActivity<DDJNuclearGoodsPresenter> implements OnLoadmoreListener {
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
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_num_s)
    TextView tv_num_s;
    public int pageNo = 1;//翻页计数器
    private BroadcastReceiver scanReceiver;
    private List<StorageManagement> list = new ArrayList();
    private NuclearGoodsAdapter storageManagementAdapter;
    private int total = 0;
    private boolean b = false;
    private int num;
    @BindView(R.id.iv_clean)
    ImageView iv_clean;
    private PopupWindow mPopWindow;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private String SCANACTION = "com.android.server.scannerservice.broadcast";
    private final String TYPE_BARCODE_BROADCAST_ACTION = "action_barcode_broadcast";
    @OnClick({R.id.tv_qx, R.id.right_text, R.id.btn_back,R.id.iv_clean})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qx:
                b = true;
                tv_qx.setTextColor(getResources().getColor(R.color.text_));
                break;
            case R.id.right_text:
                if (TextUtils.isEmpty(search_edit.getText().toString())) {
                    return;
                }
                if (b) {
                    mPresenter.get_scann(search_edit.getText().toString(), "out", getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                } else {
                    mPresenter.get_scann(search_edit.getText().toString(), "in", getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
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
    protected DDJNuclearGoodsPresenter loadPresenter() {
        return new DDJNuclearGoodsPresenter();
    }

    @Override
    protected void initData() {
        //东大集
        Intent intent = new Intent("com.seuic.scanner.action.PARAM_SETTINGS").putExtra(TYPE_BARCODE_BROADCAST_ACTION, SCANACTION);
        intent.putExtra("number",0x10a);
        intent.putExtra("value",30);
        sendBroadcast(intent);

        scanReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(SCANACTION)) {
                    {
                        //成功
                        LogUtils.i(intent.getStringExtra("scannerdata"));
                        isScanner = true;
                        search_edit.setText("");
                        search_edit.setText(intent.getStringExtra("scannerdata"));
                        search_edit.setSelection(search_edit.getText().length());
                        LogUtils.i(b);
                        if (b){
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"),"out",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                        }else {
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"),"in",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                        }

                    }
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
        storageManagementAdapter = new NuclearGoodsAdapter(this, list);
        rk_list.setAdapter(storageManagementAdapter);
        getData(1);
        setNum(getIntent().getIntExtra("num",0));

    }

    protected void getData(int pn) {
        mPresenter.get_scann(pn, getIntent().getStringExtra("customer_id"));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.nuclear_goods_layout;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
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
    public void get_scann_Success(CustomApiResult<StorageManagement> result) {
        isScanner = false;
        tv_qx.setTextColor(getResources().getColor(R.color.text_gonees));
        if (result.getErr() == 0) {
            if (b) {
                b = false;
                if (list.size() > 0) {

                    Iterator<StorageManagement> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        StorageManagement item = iterator.next();
                        if (item.getSku().equals(result.getData().getSku())) {
                            iterator.remove();
                        }
                    }
                    LogUtils.i(list);
                    storageManagementAdapter.notifyDataSetChanged();
                    LogUtils.i(total);
                    tv_num.setText("已核货：" + (--total));
                }
                PlayVoice.playVoice(this,R.raw.quxiaoshichenggonggai);
            } else {
                PlayVoice.playVoice(this,R.raw.hehuochenggong);
                if (result.getErr() == 0) {
                    list.add(0, result.getData());
                    storageManagementAdapter.notifyDataSetChanged();
                    tv_num.setText("已核货：" + (++total));



                }


            }
        } else {
            if (b) {
                b = false;
            }
        }
        if (result.getErr() == 1) {
            PlayVoice.playVoice(this,R.raw.hehuoshibai);
        }

        if (result.getErr() == 8 || result.getErr() == 2) {
            PlayVoice.playVoice(this,R.raw.quxiaoshibaigai);
        }
        if (result.getErr() == 4) {
            PlayVoice.playVoice(this,R.raw.saomashibaigai);

        }
        if (result.getErr() == 6) {
            PlayVoice.playVoice(this,R.raw.yijinghehuo);
        }
        tv_msg.setVisibility(View.GONE);
        if (result.getErr() == 5) {
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

        tv_num_s.setText("未核货："+(num-total));
    }

    public void get_scann__home_Success(CustomApiResult<List<StorageManagement>> response) {
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
        tv_title1.setText(response.getRegionNo());
        if (response.getErr() == 0) {

            if (response.getData().size()>0){
                list.addAll(response.getData());
                tv_num.setText("已核货：" + list.size());
                tv_num_s.setText("未核货："+(num-list.size()));
                storageManagementAdapter.notifyDataSetChanged();
                mStateView.showContent();
            } else {
                if (pageNo != 1) {
                    Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
                }else {
                    mStateView.showEmpty();
                }
            }
            total = response.getTotal();
            tv_num.setText("已核货：" + response.getTotal());
            tv_num_s.setText("未核货："+(num-response.getTotal()));

        } else {
            if (pageNo != 1) {
                Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void registerReceiver() {
        IntentFilter intentFilter=new IntentFilter(SCANACTION);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(scanReceiver, intentFilter);
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
    public View getStateViewRoot() {
        return ll;

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

        rvPop.setLayoutManager(new LinearLayoutManager(DDJNuclearGoodsActivity.this));
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
