package com.example.cwiczenie_3_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

    public class Lista extends AppCompatActivity {


        MyAdapter adapter;
        ListView lista;


        private class LVitem{
            TextView tv1;
            TextView tv2;
            ImageView img;
            View Color;
        }

        protected static class ListElement //implements Comparable
        {
            String name;
            String number;
            String age;
            int RedProgress;
            int GreenProgress;
            int BlueProgress;
            int gender;
            float rating;

        /*
        @Override
        public int compareTo(ListElement o) {
            String comparename =((ListElement)o).name;
            int byname = comparename.compareTo(this.name);
            if(byname!=0)
                return 0;
            String compareage =((ListElement)o).name;
            int byage = compareage.compareTo(this.age);
            return byage;
        }
         */
        }

        ArrayList<ListElement> ItemList;


        private int color;

        public class MyAdapter extends BaseAdapter {

            private LayoutInflater inflater = null;
            boolean[] zazn_pozycje;
            LVitem myLVitem;

            MyAdapter(ArrayList<ListElement> lista) {
                super();
                zazn_pozycje = new boolean[lista.size()];
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }


            @Override
            public int getCount() {
                return ItemList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View listitemView, ViewGroup parent) {
                if (listitemView==null) {
                    listitemView = inflater.inflate(R.layout.list_row, null);
                    myLVitem = new LVitem();
                    myLVitem.tv1 = (TextView) listitemView.findViewById(R.id.row_tv1);
                    myLVitem.tv2 = (TextView) listitemView.findViewById(R.id.row_tv2);
                    myLVitem.img = (ImageView) listitemView.findViewById(R.id.row_image);
                    myLVitem.Color = listitemView.findViewById(R.id.view_Row);

                    listitemView.setTag(myLVitem);
                }
                else
                    myLVitem = (LVitem) listitemView.getTag();

                myLVitem.tv1.setText(ItemList.get(position).name);
                myLVitem.tv2.setText("Wiek: "+ ItemList.get(position).age);

                switch (ItemList.get(position).gender){
                    case 0:
                        myLVitem.img.setImageResource(R.drawable.female);
                        break;
                    case 1:
                        myLVitem.img.setImageResource(R.drawable.male);
                        break;
                    case 2:
                        myLVitem.img.setImageResource(R.drawable.agender);
                        break;
                    case 3:
                        myLVitem.img.setImageResource(R.drawable.trap);
                        break;
                    case 4:
                        myLVitem.img.setImageResource(R.drawable.trapreverse);
                        break;
                    case 5:
                        myLVitem.img.setImageResource(R.drawable.nonbinary);
                        break;
                }


                color = Color.rgb(ItemList.get(position).RedProgress,ItemList.get(position).GreenProgress,ItemList.get(position).BlueProgress);
                myLVitem.Color.setBackgroundColor(color);


                listitemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getApplicationContext(),"Usunąłeś: "+ ItemList.get(position).name,Toast.LENGTH_LONG).show();
                        ItemList.remove(position);
                        SaveList();
                        notifyDataSetChanged();
                        return true;

                    }
                });

                listitemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Lista.this, OpenItem.class);

                        intent.putExtra("name", ItemList.get(position).name);
                        intent.putExtra("number", ItemList.get(position).number);
                        intent.putExtra("age", ItemList.get(position).age);
                        intent.putExtra("redprogress", ItemList.get(position).RedProgress);
                        intent.putExtra("blueprogress", ItemList.get(position).BlueProgress);
                        intent.putExtra("greenProgress", ItemList.get(position).GreenProgress);
                        intent.putExtra("gender", ItemList.get(position).gender);
                        intent.putExtra("rating",ItemList.get(position).rating);
                        intent.putExtra("position",position);

                        startActivityForResult(intent, 2);
                        Toast.makeText(getApplicationContext(),"Wybrałeś: "+ ItemList.get(position).name,Toast.LENGTH_LONG).show();

                    }
                });

                return listitemView;
            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lista);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.Lista);

            LoadData();
            adapter = new MyAdapter(ItemList);
            lista = (ListView) findViewById(R.id.listView);
            lista.setAdapter(adapter);

            FloatingActionButton Button = findViewById(R.id.floatingActionButton);
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uruchomAdd(v);
                }
            });
        }

        protected void onStart(){
            super.onStart();
            adapter.notifyDataSetChanged();
        }


        public void powrotDoMain(View view){
            onBackPressed();
            SaveList();
        }

        public void uruchomAdd(View view){
            final Intent intencja = new Intent(this, Add.class);
            startActivityForResult(intencja,1);

        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            ListElement Element = new ListElement();
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    Element.name = data.getStringExtra("name");
                    Element.number = data.getStringExtra("number");
                    Element.age = data.getStringExtra("age");
                    Element.BlueProgress = data.getIntExtra("blueprogress", 0);
                    Element.RedProgress = data.getIntExtra("redprogress", 0);
                    Element.GreenProgress = data.getIntExtra("greenprogress", 0);
                    Element.rating = data.getFloatExtra("rating", 0);

                    switch (data.getIntExtra("gender", 0)) {
                        case R.id.radioButtonWomen:
                            Element.gender =0;
                            break;
                        case R.id.radioButtonMen:
                            Element.gender =1;
                            break;
                        case R.id.radioButtonAgender:
                            Element.gender =2;
                            break;
                        case R.id.radioButtonTrap:
                            Element.gender =3;
                            break;
                        case R.id.radioButtonReverseTrap:
                            Element.gender =4;
                            break;
                        case R.id.radioButtonNonBinary:
                            Element.gender =5;
                            break;
                    }

                    ItemList.add(Element);
                    SaveList();
                }
            }
            if (requestCode == 2) {
                if (resultCode == RESULT_OK) {
                    int elemposition = data.getIntExtra("position", 0);
                    Element.name = data.getStringExtra("name");
                    Element.number = data.getStringExtra("number");
                    Element.age = data.getStringExtra("age");
                    Element.BlueProgress = data.getIntExtra("blueprogress", 0);
                    Element.RedProgress = data.getIntExtra("redprogress", 0);
                    Element.GreenProgress = data.getIntExtra("greenprogress", 0);
                    Element.rating = data.getFloatExtra("rating", 0);
                    int test = data.getIntExtra("gender",0);
                    switch (test) {
                        case R.id.radioButtonWomen_open:
                            Element.gender =0;
                            break;
                        case R.id.radioButtonMen_open:
                            Element.gender =1;
                            break;
                        case R.id.radioButtonAgender_open:
                            Element.gender =2;
                            break;
                        case R.id.radioButtonTrap_open:
                            Element.gender =3;
                            break;
                        case R.id.radioButtonReverseTrap_open:
                            Element.gender =4;
                            break;
                        case R.id.radioButtonNonBinary_open:
                            Element.gender =5;
                            break;
                    }
                    ItemList.set(elemposition, Element);
                    SaveList();

                }
            }

        }

        private void SaveList()
        {
            SharedPreferences sharedPreferences = getSharedPreferences( "shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(ItemList);
            editor.putString("List",json);
            editor.apply();
        }
        private void LoadData()
        {
            SharedPreferences sharedPreferences = getSharedPreferences( "shared preferences", MODE_PRIVATE);

            Gson gson = new Gson();
            String json = sharedPreferences.getString("List" ,null);
            Type type = new TypeToken<ArrayList<ListElement>>() {}.getType();
            ItemList = gson.fromJson(json,type);
            if(ItemList == null)
            {
                ItemList = new ArrayList<>();
            }

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case android.R.id.home:
                    Intent homeIntent = new Intent(this, MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
            }
            return (super.onOptionsItemSelected(menuItem));
        }

    }