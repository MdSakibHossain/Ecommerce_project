package com.alfaco_1.testno1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.alfaco_1.testno1.GoMainActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;
    private Button coupenRedeemBtn;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    private FloatingActionButton addToWhislistBtn;

    ////////// Rating layout
    private LinearLayout rateNowContainer;
    ////////// Rating layout

    private Button buyNowBtn;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTablayout;

    ///////////coupenDialog

    public static TextView coupenTitle;
    public static TextView coupenExpireData;
    public static TextView coupenBody;
    private static  RecyclerView coupensRecyclerView;
    private static LinearLayout selectedCoupen;
    ///////////coupenDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coupenRedeemBtn = findViewById(R.id.coupen_redemption_btn);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWhislistBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTablayout = findViewById(R.id.product_details_tab_layout);


        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.mipmap.applogo1);
        productImages.add(R.mipmap.phone1);
        productImages.add(R.mipmap.person_logo);
        productImages.add(R.mipmap.add_photo);

        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);

        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);

        addToWhislistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(ALREADY_ADDED_TO_WISHLIST){
                 ALREADY_ADDED_TO_WISHLIST = false;
                  addToWhislistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F3F3F3")));
             }else{
                 ALREADY_ADDED_TO_WISHLIST = true;
                  addToWhislistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red1));
             }
            }
        });

      productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount()));
      productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
      productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
              productDetailsViewPager.setCurrentItem(tab.getPosition());
          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {

          }

          @Override
          public void onTabReselected(TabLayout.Tab tab) {

          }
      });
/////////////rating layout

        rateNowContainer = findViewById(R.id.rate_now_container);
        for(int x = 0; x < rateNowContainer.getChildCount();x++){
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }

/////////////rating layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });

        ////////coupen dialog

        final Dialog checkCoupenPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDialog.setCancelable(true);
        checkCoupenPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerview =  checkCoupenPriceDialog.findViewById(R.id.toggle_recyclerview);
        coupensRecyclerView = checkCoupenPriceDialog.findViewById(R.id.coupens_recyclerview);
        selectedCoupen = checkCoupenPriceDialog.findViewById(R.id.selected_coupen);

        coupenTitle = checkCoupenPriceDialog.findViewById(R.id.coupen_title);
        coupenExpireData = checkCoupenPriceDialog.findViewById(R.id.coupen_validity);
        coupenBody = checkCoupenPriceDialog.findViewById(R.id.coupen_body);

        TextView originalPrice = checkCoupenPriceDialog.findViewById(R.id.orginal_price);
        TextView discountedPrice = checkCoupenPriceDialog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        coupensRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Buy 1 get 1 free","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Big deal","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Buy 1 get 1 free","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,June 2020","GET 30% CASHBACK on any product above Tk.1000/- and below Tk.3000/-"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList,true);
        coupensRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        toggleRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });
        ////////coupen dialog




        coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCoupenPriceDialog.show();
            }
        });

        ////////coupen dialog

    }

    public  static void showDialogRecyclerView(){
        if(coupensRecyclerView.getVisibility() == View.GONE){
            coupensRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        }else {
            coupensRecyclerView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }
    }

    private void setRating(int starPosition) {
        for(int x=0;  x< rateNowContainer.getChildCount();x++){
            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffff00")));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
             finish();
            return true;
        }else if(id == R.id.main_search_icon)
        {
            return true;
        }
        else if(id == R.id.main_cart_icon)
        {
            Intent cartIntent = new Intent(ProductDetailsActivity.this,GoMainActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}