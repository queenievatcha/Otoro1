package com.queenievatcha.otoro1;

/**
 * Created by inkz on 21/11/2017 AD.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapterCart extends ArrayAdapter{
    private Activity context;
    int pos;
    ArrayList<String> foodAL = new ArrayList<String>();
    ArrayList<String> minusAL = new ArrayList<String>();
    ArrayList<String> plusAL = new ArrayList<String>();
    ArrayList<Integer> imgIDAL = new ArrayList<Integer>();
    ArrayList<Integer> amountAL = new ArrayList<Integer>();

    CustomAdapterCart(@NonNull Activity context, ArrayList<String> food, ArrayList<Integer> imgID, ArrayList<Integer> amount) {
        super(context, R.layout.custom_row2, food);
        this.context = context;
        this.foodAL = food;
        this.imgIDAL = imgID;
        this.amountAL = amount;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.custom_row2, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.foodImage.setImageResource(imgIDAL.get(position));
        viewHolder.foodName.setText(foodAL.get(position));
        viewHolder.amountDisp.setText(Integer.toString(amountAL.get(position)));
        return r;
    }

    class ViewHolder {
        TextView foodName;
        ImageView foodImage;
        TextView amountDisp;

        ViewHolder(View v) {
            foodName = v.findViewById(R.id.textViewFoodName);
            foodImage = v.findViewById(R.id.imageViewFood);
            amountDisp = v.findViewById(R.id.textViewAmountDisp);

        }
    }
}