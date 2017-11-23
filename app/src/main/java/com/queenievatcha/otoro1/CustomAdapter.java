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

import static com.queenievatcha.otoro1.MenuActivity.priceForEach;

public class CustomAdapter extends ArrayAdapter {
    private String[] food;
    private String[] description;
    private int[] price;
    private int[] imgID;
    private String[] butPlus;
    private String[] butMinus;
    private int[] amount;
    private Activity context;
    int pos;

    ArrayList<String> foodAL = new ArrayList<String>();
    ArrayList<Integer> imgIDAL = new ArrayList<Integer>();
    ArrayList<Integer> amountAL = new ArrayList<Integer>();

    CustomAdapter(@NonNull Activity context, String[] food, String[] description, int[] price
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
        viewHolder.textViewFoodPrice.setText(Integer.toString(priceForEach[position]));
        return r;
    }

    class ViewHolder {
        TextView foodName;
        TextView description;
        ImageView foodImage;
        Button foodButtonPlus;
        Button foodButtonMinus;
        TextView amountDisp;
        TextView textViewFoodPrice;
        int count = 0;

        ViewHolder(View v) {
            foodName = v.findViewById(R.id.textViewFoodName);
            description = v.findViewById(R.id.textViewDescription);
            foodImage = v.findViewById(R.id.imageViewFood);
            amountDisp = v.findViewById(R.id.textViewAmountDisp);
            foodButtonPlus = v.findViewById(R.id.buttonPlus);
            textViewFoodPrice = v.findViewById(R.id.textViewFoodPrice);

            foodButtonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((foodName.getText().toString()).equalsIgnoreCase("burger")) {
                        pos = 0;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("crab")) {
                        pos = 1;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("fish")) {
                        pos = 2;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("pizza")) {
                        pos = 3;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("shrimp")) {
                        pos = 4;
                    }

                    count = amount[pos];
                    count++;
                    amountDisp.setText(Integer.toString(count));

                    MenuActivity.addAmount(pos);
                    MenuActivity.addPrice(pos);
                    MenuActivity.setAllText();

                    foodButtonMinus.setEnabled(true);
                    //reset counting
                    count = 0;
                }
            });

            foodButtonMinus = v.findViewById(R.id.buttonMinus);

            //if user backs to home activity and comes back again
            int totalTest=0;
            for (int i = 0; i < amount.length; i++) {
                totalTest+=amount[i];
            }
            if (totalTest<1) foodButtonMinus.setEnabled(false);

            ///////

            foodButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ((foodName.getText().toString()).equalsIgnoreCase("burger")) {
                        pos = 0;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("crab")) {
                        pos = 1;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("fish")) {
                        pos = 2;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("pizza")) {
                        pos = 3;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("shrimp")) {
                        pos = 4;
                    }

                    count = amount[pos];
                    count--;

                    //prevent skipping
                    if (count < 1) {
                        count = 0;
                        amountDisp.setText("0");
                        amount[pos]=0;
                    }
                    amountDisp.setText(Integer.toString(count));

                    MenuActivity.minusAmount(pos);
                    MenuActivity.minusPrice(pos);
                    MenuActivity.setAllText();

                    //reset counting
                    count = 0;
                }
            });
        }
    }
}