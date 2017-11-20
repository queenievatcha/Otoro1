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

public class CustomAdapter extends ArrayAdapter<String> {
    private String[] food;
    private String[] description;
    private Integer[] imgID;
    private String[] butPlus;
    private String[] butMinus;
    private Integer[] amount;
    private Activity context;

    CustomAdapter(@NonNull Activity context, String[] food, String[] description
            , Integer[] imgID, String[] butPlus, String[] butMinus, Integer[] amount) {
        super(context, R.layout.custom_row, food);
        this.context = context;
        this.food = food;
        this.description = description;
        this.imgID = imgID;
        this.butPlus = butPlus;
        this.butMinus=butMinus;
        this.amount=amount;
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
        int count=0;

        ViewHolder(View v) {
            foodName = v.findViewById(R.id.foodName);
            description = v.findViewById(R.id.description);
            foodImage = v.findViewById(R.id.foodImage);
            foodButtonPlus=v.findViewById(R.id.foodButtonPlus);
            foodButtonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count=count+1;
                    amountDisp.setText(Integer.toString(count));
                    foodButtonMinus.setEnabled(true);
                }
            });

            foodButtonMinus=v.findViewById(R.id.foodButtonMinus);
            foodButtonMinus.setEnabled(false);
            foodButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count=count-1;
                    amountDisp.setText(Integer.toString(count));
                    if (count==0){
                        foodButtonMinus.setEnabled(false);
                    }
                }
            });
            amountDisp=v.findViewById(R.id.amountDisp);
        }
    }
}