package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DayCareMoreItems extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Profile","Messages","Photos","Promotions","Update center location","Your Rating","Parents Reviews","Language","Report Problem","LOG OUT"};
    int mImages[] = {R.drawable.ic_profile,R.drawable.ic_nav_messages,R.drawable.ic_photo,R.drawable.ic_promotion,R.drawable.ic_edit_location,R.drawable.ic_rating,R.drawable.ic_review,R.drawable.ic_language,R.drawable.ic_report_problem,R.drawable.ic_logout};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_care_more_items);

        listView = findViewById(R.id.moreItemsList);

        MyAdapter adapter = new MyAdapter(this,mTitle,mImages);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    startActivity(new Intent(getApplicationContext(), DayCareProfile.class));
                }
                if(position==2){
                    startActivity(new Intent(getApplicationContext(), DCMIimages.class));
                }
                if(position==9){
                    Intent itttt = new Intent(DayCareMoreItems.this,UserType.class);
                    itttt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(itttt);
                }
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        int rImages[];

        MyAdapter (Context c,String title[],int images[]){
            super(c,R.layout.row,R.id.rowText,title);
            this.context=c;
            this.rTitle=title;
            this.rImages=images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView myImages = row.findViewById(R.id.imageID);
            TextView  myTitle = row.findViewById(R.id.rowText);

            myImages.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);


            return row;
        }
    }


}
