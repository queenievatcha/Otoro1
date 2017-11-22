package com.queenievatcha.otoro1;

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

public class CustomAdapter extends ArrayAdapter {
    private String[] food;
    private String[] description;
    private int[] imgID;
    private String[] butPlus;
    private String[] butMinus;
    private int[] amount;
    private Activity context;
    int pos;
    ArrayList<String> foodAL = new ArrayList<String>();
    ArrayList<Integer> imgIDAL = new ArrayList<Integer>();
    ArrayList<Integer> amountAL = new ArrayList<Integer>();


    CustomAdapter(@NonNull Activity context, String[] food, String[] description
            , int[] imgID, String[] butPlus, String[] butMinus, int[] amount) {
        super(context, R.layout.custom_row, food);
        this.context = context;
        this.food = food;
        this.description = description;
        this.imgID = imgID;
        this.butPlus = butPlus;
        this.butMinus = butMinus;
        this.amount = amount;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.custom_row, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.foodImage.setImageResource(imgID[position]);
        viewHolder.foodName.setText(food[position]);
        viewHolder.description.setText(description[position]);
        viewHolder.foodButtonPlus.setText(butPlus[position]);
        viewHolder.foodButtonMinus.setText(butMinus[position]);
        viewHolder.amountDisp.setText(Integer.toString(amount[position]));
        return r;
    }

    class ViewHolder {
        TextView foodName;
        TextView description;
        ImageView foodImage;
        Button foodButtonPlus;
        Button foodButtonMinus;
        TextView amountDisp;
        int count = 0;

        ViewHolder(View v) {
            foodName = v.findViewById(R.id.foodName);
            description = v.findViewById(R.id.price);
            foodImage = v.findViewById(R.id.foodImage);
            amountDisp = v.findViewById(R.id.amountDisp);
            foodButtonPlus = v.findViewById(R.id.foodButtonPlus);
            foodButtonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = amount[pos];
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
                    MenuActivity.setAllText();
                    amountDisp.setText(Integer.toString(count));
                    foodButtonMinus.setEnabled(true);
                }
            });

            foodButtonMinus = v.findViewById(R.id.foodButtonMinus);
            foodButtonMinus.setEnabled(false);
            foodButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = amount[pos];
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
                    MenuActivity.setAllText();
                    amountDisp.setText(Integer.toString(count));
                    if (count == 0) {
                        foodButtonMinus.setEnabled(false);
                    }
                }
            });
        }
    }
}