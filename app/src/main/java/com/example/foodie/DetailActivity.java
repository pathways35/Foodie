package com.example.foodie;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodie.model.DataItem;
import com.example.foodie.sample.SampleDataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    private TextView tvName, tvDescription, tvPrice;
    private ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        String item_id = getIntent().getExtras().getString(DataItemAdapter.ITEM_ID_KEY);
//        DataItem item = SampleDataProvider.dataItemMap.get(item_id);

        DataItem item = getIntent().getExtras().getParcelable(DataItemAdapter.ITEM_KEY);
        if (item == null) {
            Log.i("DetailActivity", "Did not receive any data");
            throw new AssertionError("Null data received");
        }

        tvName = (TextView) findViewById(R.id.tvItemName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        itemImage = (ImageView) findViewById(R.id.itemImage);

        tvName.setText(item.getItemName());
        tvDescription.setText(item.getDescription());

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        tvPrice.setText(nf.format(item.getPrice()));

        InputStream stream = null;
        try {
            stream = getAssets().open(item.getImage());
            Drawable d = Drawable.createFromStream(stream, null);
            itemImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
