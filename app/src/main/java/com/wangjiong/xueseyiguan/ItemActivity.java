package com.wangjiong.xueseyiguan;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import data.Wujiang;

public class ItemActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    String TAG = "ItemActivity==";

    private ViewPager viewPager;
    private View[] mImageViews;

    List<Wujiang> mWujiangs = new ArrayList<>();

    TextView positionTv;

    private void initData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Log.d(TAG, "name:" + name);
        String place = "";
        for (Wujiang wujiang : HomeActivity.mWujiangs) {
            if (wujiang.name.equals(name)) {
                place = wujiang.place;
                mWujiangs.add(wujiang);
                break;
            }
        }
        for (Wujiang wujiang : HomeActivity.mWujiangs) {
            if (wujiang.place.equals(place) && !mWujiangs.contains(wujiang)) {
                mWujiangs.add(wujiang);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        initData();

        int num = mWujiangs.size();
        if (num == 0) {
            return;
        } else {
            TextView numText = (TextView) findViewById(R.id.num);
            numText.setText("总数 ： " + num + "");

            positionTv = (TextView) findViewById(R.id.position);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        mImageViews = new View[mWujiangs.size()];
        for (int i = 0; i < mWujiangs.size(); i++) {
            Wujiang wujiang = mWujiangs.get(i);
            View rootView = LayoutInflater.from(this).inflate(R.layout.item_viewpager, null);
            // 图片
            ImageView imageView = (ImageView) rootView.findViewById(R.id.avatar);
            setImage(imageView, "img/img" + wujiang.id + ".jpg");
            // 名字
            TextView name = (TextView) rootView.findViewById(R.id.name);
            name.setText(wujiang.name);

            //
            TextView tongshuai = (TextView) rootView.findViewById(R.id.tongshuai);
            tongshuai.setText(wujiang.tongshuai + "");

            TextView wuli = (TextView) rootView.findViewById(R.id.wuli);
            wuli.setText(wujiang.wuli + "");

            TextView zhili = (TextView) rootView.findViewById(R.id.zhili);
            zhili.setText(wujiang.zhili + "");

            TextView zhengzhi = (TextView) rootView.findViewById(R.id.zhengzhi);
            zhengzhi.setText(wujiang.zhengzhi + "");

            TextView meili = (TextView) rootView.findViewById(R.id.meili);
            meili.setText(wujiang.meili + "");

            //
            TextView qiang = (TextView) rootView.findViewById(R.id.qiang);
            qiang.setText("枪兵：" + getBingType(wujiang.qiangbin));

            TextView ji = (TextView) rootView.findViewById(R.id.ji);
            ji.setText("戟兵：" + getBingType(wujiang.jibin));

            TextView nu = (TextView) rootView.findViewById(R.id.nu);
            nu.setText("弩兵：" + getBingType(wujiang.nubin));

            TextView qi = (TextView) rootView.findViewById(R.id.qi);
            qi.setText("骑兵：" + getBingType(wujiang.qibin));

            TextView bing = (TextView) rootView.findViewById(R.id.bing);
            bing.setText("兵器：" + getBingType(wujiang.binqi));

            TextView shui = (TextView) rootView.findViewById(R.id.shui);
            shui.setText("水军：" + getBingType(wujiang.shuijun));


            TextView teji = (TextView) rootView.findViewById(R.id.teji);
            teji.setText("特技：" + wujiang.teji);

            TextView liezhuang = (TextView) rootView.findViewById(R.id.liezhuang);
            liezhuang.setText(wujiang.liezhuang.replace("\n", "\n[史]"));


            mImageViews[i] = rootView;
        }
        viewPager.setAdapter(new MyAdapter());
        viewPager.setCurrentItem((mImageViews.length) * 100);
    }


    private void setImage(ImageView imageView, String fileName) {
        Bitmap image;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            imageView.setImageBitmap(image);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getBingType(String type) {
        String result = "'";
        switch (type) {
            case "Ｓ+20%":
                result = "圣";
                break;
            case "Ｓ+10%":
                result = "神";
                break;
            case "Ｓ":
                result = "极";
                break;
            case "Ａ":
                result = "精";
                break;
            case "Ｂ":
                result = "熟";
                break;
            case "Ｃ":
                result = "初";
                break;
        }
        return result;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        positionTv.setText("当前 ：" + (position % mImageViews.length + 1 )+ "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * @author xiaanming
     */
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            //((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            try {
                ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            }catch(Exception e){
                //handler something
            }
            return mImageViews[position % mImageViews.length];
        }


    }
}
