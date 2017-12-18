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

import static com.queenievatcha.otoro1.MenuActivity.priceForEach;


public class CustomAdapter extends ArrayAdapter {
    private String[] food;
    private String[] description;
    private int[] imgID;
    private String[] butPlus;
    private String[] butMinus;
    private int[] amount;
    private Activity context;
    int pos;

    CustomAdapter(@NonNull Activity context, String[] food, String[] description, int[] imgID, String[] butPlus, String[] butMinus, int[] amount) {
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
                    if ((foodName.getText().toString()).equalsIgnoreCase("Takoyaki")) {
                        pos = 0;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Tonkatsu Curry")) {
                        pos = 1;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("3 Pieces Tuna")) {
                        pos = 2;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("5 Pieces Sushi")) {
                        pos = 3;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Hamburg Rice")) {
                        pos = 4;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Sukiyaki")) {
                        pos = 5;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Shoyu Ramen")) {
                        pos = 6;
                    }

                    count = amount[pos];
                    count++;
                    amountDisp.setText(Integer.toString(count));

                    MenuActivity.addAmount(pos);
                    MenuActivity.addPrice(pos);
                    foodButtonMinus.setEnabled(true);

                    //reset counting
                    count = 0;
                }
            });

            foodButtonMinus = v.findViewById(R.id.buttonMinus);

            foodButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ((foodName.getText().toString()).equalsIgnoreCase("Takoyaki")) {
                        pos = 0;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Tonkatsu Curry")) {
                        pos = 1;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("3 Pieces Tuna")) {
                        pos = 2;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("5 Pieces Sushi")) {
                        pos = 3;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Hamburg Rice")) {
                        pos = 4;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Sukiyaki")) {
                        pos = 5;
                    } else if ((foodName.getText().toString()).equalsIgnoreCase("Shoyu Ramen")) {
                        pos = 6;
                    }

                    count = amount[pos];
                    count--;

                    //prevent skipping
                    if (count < 1) {
                        count = 0;
                        amountDisp.setText("0");
                        amount[pos] = 0;
                        foodButtonMinus.setEnabled(false);
                    }
                    amountDisp.setText(Integer.toString(count));

                    MenuActivity.minusAmount(pos);
                    MenuActivity.minusPrice(pos);

                    //reset counting
                    count = 0;
                }
            });
        }
    }
}