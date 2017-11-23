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

    CustomAdapterCart(@NonNull Activity context, ArrayList<String> food, ArrayList<Integer> imgID, ArrayList<String> butPlus, ArrayList<String> butMinus, ArrayList<Integer> amount) {
        super(context, R.layout.custom_row2, food);
        this.context = context;
        this.foodAL = food;
        this.imgIDAL = imgID;
        this.plusAL = butPlus;
        this.minusAL = butMinus;
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
        viewHolder.foodButtonPlus.setText(plusAL.get(position));
        viewHolder.foodButtonMinus.setText(minusAL.get(position));
        viewHolder.amountDisp.setText(Integer.toString(amountAL.get(position)));
        return r;
    }

    class ViewHolder {
        TextView foodName;
        ImageView foodImage;
        Button foodButtonPlus;
        Button foodButtonMinus;
        TextView amountDisp;
        int count;

        ViewHolder(View v) {
            foodName = v.findViewById(R.id.textViewFoodName);
            foodImage = v.findViewById(R.id.imageViewFood);
            amountDisp = v.findViewById(R.id.textViewAmountDisp);
            foodButtonPlus = v.findViewById(R.id.buttonPlus);
            foodButtonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = amountAL.get(pos);
                    count = count + 1;
                    if ((foodName.getText() + "").equalsIgnoreCase("burger"))
                        pos = 0;
                    if ((foodName.getText() + "").equalsIgnoreCase("crab"))
                        pos = 1;
                    if ((foodName.getText() + "").equalsIgnoreCase("fish"))
                        pos = 2;
                    if ((foodName.getText() + "").equalsIgnoreCase("pizza"))
                        pos = 3;
                    if ((foodName.getText() + "").equalsIgnoreCase("shrimp"))
                        pos = 4;
                    MenuActivity.addAmount(pos);
                    CartActivity.addAmount(pos);
                    amountDisp.setText(Integer.toString(count));
                    foodButtonMinus.setEnabled(true);
                }
            });

            foodButtonMinus = v.findViewById(R.id.buttonMinus);
            foodButtonMinus.setEnabled(true);
            foodButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = amountAL.get(pos);
                    count = count - 1;
                    if ((foodName.getText() + "").equalsIgnoreCase("burger"))
                        pos = 0;
                    if ((foodName.getText() + "").equalsIgnoreCase("crab"))
                        pos = 1;
                    if ((foodName.getText() + "").equalsIgnoreCase("fish"))
                        pos = 2;
                    if ((foodName.getText() + "").equalsIgnoreCase("pizza"))
                        pos = 3;
                    if ((foodName.getText() + "").equalsIgnoreCase("shrimp"))
                        pos = 4;
                    MenuActivity.minusAmount(pos);
                    CartActivity.minusAmount(pos);
                    amountDisp.setText(Integer.toString(count));
                    if (count == 0) {
                        foodButtonMinus.setEnabled(false);
                    }
                }
            });
        }
    }
}