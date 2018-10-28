package sewacart.com.sewacart.finalpackage.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.ControllerPixels;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_main_activity)
    Toolbar toolbar;
    @BindView(R.id.table)
    TableLayout table;
    @BindView(R.id.checkOut)
    Button checkOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        table = findViewById(R.id.table);
        setTitle("Cart List");

        toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        TableRow topTableRow = new TableRow(CartActivity.this);
        int dp = (int) ControllerPixels.convertDpToPixel(16, CartActivity.this);
        int smallmargin = (int) ControllerPixels.convertDpToPixel(5, CartActivity.this);


        TextView topproduct = new TextView(CartActivity.this);
        topproduct.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
        topproduct.setText("Product");
        topproduct.setPadding(smallmargin, dp, smallmargin, dp);
        topproduct.setTextSize(15f);
        topproduct.setGravity(Gravity.CENTER);
        topproduct.setTypeface(null, Typeface.BOLD);
        topproduct.setMaxLines(1);
        topproduct.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, CartActivity.this));
        topproduct.setBackgroundResource(R.drawable.border);




        TextView topqty = new TextView(CartActivity.this);
        topqty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        topqty.setText("Price");
        topqty.setGravity(Gravity.CENTER);
        topqty.setTypeface(null, Typeface.BOLD);
        topqty.setTextSize(15f);
        topqty.setPadding(smallmargin, dp, smallmargin, dp);
        topqty.setBackgroundResource(R.drawable.border);


        TextView topempty = new TextView(CartActivity.this);
        topempty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        topempty.setText("Action");
        topempty.setTextSize(15f);
        topempty.setGravity(Gravity.CENTER);
        topempty.setTypeface(null, Typeface.BOLD);
        topempty.setPadding(smallmargin, dp, smallmargin, dp);
        topempty.setBackgroundResource(R.drawable.border);


        topTableRow.addView(topproduct);
        topTableRow.addView(topqty);
        topTableRow.addView(topempty);

        if (topTableRow.getParent() != null)
            ((ViewGroup) topTableRow.getParent()).removeView(topTableRow);

        table.addView(topTableRow);
    }



    @Override
    protected void onStop() {
        super.onStop();
        table.removeAllViews();
    }
}
