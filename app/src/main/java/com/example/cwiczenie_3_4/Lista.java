package com.example.cwiczenie_3_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.app.Activity;
import android.app.Application;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity implements OpenItem_f1.OpenItemListener, Lista_f1.ListFragmentListener, Lista_f1.ListFragmentInteractionListener {


   // MyAdapter adapter;
    ListView lista;
    private TextView red;
    private TextView green;
    private TextView blue;

    private int ired;
    private int igreen;
    private int iblue;

    private RadioGroup rg;

    private SeekBar seekBarRed;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;
    private View view;

    private RatingBar rb;

    private int color;
    private EditText NameView;
    private EditText NumberView;
    private EditText AgeView;
    private int RecordPosition;
    int gender;
    ArrayList<ListElement> ItemList;
    List<ItemData> itemList_sql;

    static MyRepository myRepository;
    Lista_f1 myListFragment;
    private Lista_f1.ListFragmentInteractionListener mListener;
    private MyRVAdapter mAdapter;


    @Override
    public void onOpenInputSent(Bundle bundle) {
        //ListView lista = (ListView) findViewById(R.id.listView);

        itemList_sql = getRepositoryList();
        int elemposition = bundle.getInt("position", 0);

        //ListElement Element = ItemList.get(elemposition);
        ItemData Element = itemList_sql.get(elemposition);


        Element.name = bundle.getString("name");
        Element.number = bundle.getString("number");
        Element.blueProgress = bundle.getInt("blueprogress", 0);
        Element.redProgress = bundle.getInt("redprogress", 0);
        Element.greenProgress = bundle.getInt("greenprogress", 0);
        Element.rating = bundle.getFloat("rating", 0);

        int test = bundle.getInt("gender",0);
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
        /*
        ItemList.set(elemposition, Element);
        SaveList();
        LoadData();
        adapter.notifyDataSetChanged();
         */
        //itemList_sql.set(elemposition,Element);
        myRepository.updateItem(Element);


    }


    public void toBundle(ItemData item) {

        if (getResources().getConfiguration().orientation == 1) {
            Intent intent = new Intent(this, OpenItem.class);

            intent.putExtra("name", item.name);
            intent.putExtra("number", item.number);
            intent.putExtra("age", item.age);
            intent.putExtra("redprogress", item.redProgress);
            intent.putExtra("blueprogress", item.blueProgress);
            intent.putExtra("greenProgress", item.greenProgress);
            intent.putExtra("gender", item.gender);
            intent.putExtra("rating", item.rating);
            intent.putExtra("position", item.id);

            startActivityForResult(intent, 2);
            Toast.makeText(getApplicationContext(), " F Wybrałeś: " + item.name, Toast.LENGTH_LONG).show();
        }
        else
        {
            Bundle bundle = new Bundle();

            bundle.putString("name", item.name);
            bundle.putString("number", item.number);
            bundle.putString("age", item.age);
            bundle.putInt("redprogress", item.redProgress);
            bundle.putInt("blueprogress", item.blueProgress);
            bundle.putInt("greenProgress", item.greenProgress);
            bundle.putInt("gender", item.gender);
            bundle.putFloat("rating", item.rating);
            bundle.putInt("position", item.id);
            Toast.makeText(getApplicationContext(), "Kliknalles: " + item.name, Toast.LENGTH_LONG).show();

            onListInputSent(bundle);
        }

    }


    @Override
    public void onListInputSent(Bundle bundle) {

        seekBarRed = (SeekBar) findViewById(R.id.SeekBarRed_open);
        seekBarGreen = (SeekBar) findViewById(R.id.SeekBarGreen_open);
        seekBarBlue = (SeekBar) findViewById(R.id.SeekBarBlue_open);


        red = (TextView) findViewById(R.id.textView11);
        green = (TextView) findViewById(R.id.textView12);
        blue = (TextView) findViewById(R.id.textView13);

        view = (View) findViewById(R.id.ColorView_open);

        NameView = (EditText) findViewById(R.id.Name_Open);
        NumberView = (EditText) findViewById(R.id.Number_Open);
        AgeView = (EditText) findViewById(R.id.Age_Open);

        rg = (RadioGroup) findViewById(R.id.radiogroup_open);

        rb = (RatingBar) findViewById(R.id.ratingBar_open);

        NameView.setText(bundle.getString("name"));
        NumberView.setText(bundle.getString ("number"));
        AgeView.setText(bundle.getString ("age"));

        RecordPosition = bundle.getInt ("position", 0);

        ired = bundle.getInt ("redprogress", 0);
        iblue = bundle.getInt ("blueprogress", 0);
        igreen = bundle.getInt ("greenprogress", 0);
        seekBarRed.setProgress(ired);
        seekBarBlue.setProgress(iblue);
        seekBarGreen.setProgress(igreen);

        rb.setRating(bundle.getFloat ("rating", 0));

        color = Color.rgb(ired, igreen, iblue);
        view.setBackgroundColor(color);


        switch (bundle.getInt ("gender", 0)) {
            case 0:
                ((RadioButton) findViewById(R.id.radioButtonWomen_open)).setChecked(true);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.radioButtonMen_open)).setChecked(true);
                break;
            case 2:
                ((RadioButton) findViewById(R.id.radioButtonAgender_open)).setChecked(true);
                break;
            case 3:
                ((RadioButton) findViewById(R.id.radioButtonTrap_open)).setChecked(true);
                break;
            case 4:
                ((RadioButton) findViewById(R.id.radioButtonReverseTrap_open)).setChecked(true);
                break;
            case 5:
                ((RadioButton) findViewById(R.id.radioButtonNonBinary_open)).setChecked(true);
                break;
        }


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);

                Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }


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

        public ListElement()
        {

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.Lista);

        if (savedInstanceState==null)
            myRepository = new MyRepository((Application)getApplicationContext());
        myListFragment =  (Lista_f1) getSupportFragmentManager().findFragmentById(R.id.fragment2);

        LoadData();
        //adapter = new MyAdapter(ItemList);
       // lista = (ListView) findViewById(R.id.listView);
       // lista.setAdapter(adapter);
        mAdapter =new MyRVAdapter(mListener);

        FloatingActionButton Button = findViewById(R.id.floatingActionButton);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uruchomAdd(v);
            }
        });
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
        //ListElement Element = new ListElement();
        ItemData Element = new ItemData();
        itemList_sql = getRepositoryList();
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Element.name = data.getStringExtra("name");
                Element.number = data.getStringExtra("number");
                Element.age = data.getStringExtra("age");
                Element.blueProgress = data.getIntExtra("blueprogress", 0);
                Element.redProgress = data.getIntExtra("redprogress", 0);
                Element.greenProgress = data.getIntExtra("greenprogress", 0);
                Element.rating = data.getFloatExtra("rating", 0);
                Element.id = data.getIntExtra("id",0);

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

                //ItemList.add(Element);
                //itemList_sql.add(Element);
                //SaveList();
                //myRepository.insertItem(Element);
                myRepository.updateItem(Element);

            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                int elemposition = data.getIntExtra("position", 0);
                Element.name = data.getStringExtra("name");
                Element.number = data.getStringExtra("number");
                Element.age = data.getStringExtra("age");
                Element.blueProgress = data.getIntExtra("blueprogress", 0);
                Element.redProgress = data.getIntExtra("redprogress", 0);
                Element.greenProgress = data.getIntExtra("greenprogress", 0);
                Element.rating = data.getFloatExtra("rating", 0);
                Element.id = data.getIntExtra("id",0);
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
                //ItemList.set(elemposition, Element);
                //itemList_sql.set(elemposition, Element);
                myRepository.updateItem(Element);
                myRepository.insertItem(Element);
                //SaveList();

            }
        }

    }

    private void SaveList()
    {
        /*
        SharedPreferences sharedPreferences = getSharedPreferences( "shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ItemList);
        editor.putString("List",json);
        editor.apply();
        */
    }
    private void LoadData()
    {
        /*
        SharedPreferences sharedPreferences = getSharedPreferences( "shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("List" ,null);
        Type type = new TypeToken<ArrayList<ListElement>>() {}.getType();
        ItemList = gson.fromJson(json,type);

         */
        if(ItemList == null)
        {
            ItemList = new ArrayList<>();
        }



    }

    public void onDeleteItem(ItemData item){
        myRepository.deleteItem(item);
        myListFragment.setList(getRepositoryList());
    }


    public void sentToInputSent(ItemData item) {

        seekBarRed = (SeekBar) findViewById(R.id.SeekBarRed_open);
        seekBarGreen = (SeekBar) findViewById(R.id.SeekBarGreen_open);
        seekBarBlue = (SeekBar) findViewById(R.id.SeekBarBlue_open);


        red = (TextView) findViewById(R.id.textView11);
        green = (TextView) findViewById(R.id.textView12);
        blue = (TextView) findViewById(R.id.textView13);

        view = (View) findViewById(R.id.ColorView_open);

        NameView = (EditText) findViewById(R.id.Name_Open);
        NumberView = (EditText) findViewById(R.id.Number_Open);
        AgeView = (EditText) findViewById(R.id.Age_Open);

        rg = (RadioGroup) findViewById(R.id.radiogroup_open);

        rb = (RatingBar) findViewById(R.id.ratingBar_open);

        NameView.setText(item.name);
        NumberView.setText(item.number);
        AgeView.setText(item.age);

        RecordPosition = item.id;

        ired = item.redProgress;
        iblue =item.blueProgress;
        igreen = item.greenProgress;
        seekBarRed.setProgress(ired);
        seekBarBlue.setProgress(iblue);
        seekBarGreen.setProgress(igreen);

        rb.setRating(item.rating);

        color = Color.rgb(ired, igreen, iblue);
        view.setBackgroundColor(color);


        switch (item.gender) {
            case 0:
                ((RadioButton) findViewById(R.id.radioButtonWomen_open)).setChecked(true);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.radioButtonMen_open)).setChecked(true);
                break;
            case 2:
                ((RadioButton) findViewById(R.id.radioButtonAgender_open)).setChecked(true);
                break;
            case 3:
                ((RadioButton) findViewById(R.id.radioButtonTrap_open)).setChecked(true);
                break;
            case 4:
                ((RadioButton) findViewById(R.id.radioButtonReverseTrap_open)).setChecked(true);
                break;
            case 5:
                ((RadioButton) findViewById(R.id.radioButtonNonBinary_open)).setChecked(true);
                break;
        }


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);

                Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });

}

    public void sentToInputSent2(ItemData item){

        Intent intent = new Intent(this, OpenItem.class);

        intent.putExtra("name", item.name);
        intent.putExtra("number", item.number);
        intent.putExtra("age", item.age);
        intent.putExtra("redprogress", item.redProgress);
        intent.putExtra("blueprogress", item.blueProgress);
        intent.putExtra("greenProgress", item.greenProgress);
        intent.putExtra("gender", item.gender);
        intent.putExtra("rating", item.rating);
        intent.putExtra("position", item.id);

        startActivityForResult(intent, 2);
        Toast.makeText(getApplicationContext(), " F Wybrałeś: " + item.name, Toast.LENGTH_LONG).show();
    }


    public List<ItemData> getRepositoryList(){
        return myRepository.getDataList();
    }

    @Override
    public void sentToInputchose(ItemData item) {
        if (getResources().getConfiguration().orientation == 1) {
            sentToInputSent2(item);
        }
        else {
            sentToInputSent(item);
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