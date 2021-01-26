package com.example.cwiczenie_3_4;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Options_f1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Options_f1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Options_f1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Options_f1.
     */
    // TODO: Rename and change types and number of parameters
    public static Options_f1 newInstance(String param1, String param2) {
        Options_f1 fragment = new Options_f1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options_f1, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.checkable_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        SeekBar bar = view.findViewById(R.id.seekBarChangeSize);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView text = view.findViewById(R.id.textViewChangeSize);
                text.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setTextViewStyle(TextView view, boolean isBold){
        if (view == null)
            return;
        Typeface oldTypeface = view.getTypeface() != null ? view.getTypeface() :
                (isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

        view.setTypeface(
                Typeface.create(oldTypeface, isBold ? Typeface.BOLD : Typeface.NORMAL)
        );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView text = getActivity().findViewById(R.id.textView2);
        Typeface typeface = text.getTypeface() != null ? text.getTypeface() : Typeface.DEFAULT;

        switch (item.getItemId()) {

            case R.id.checkable_item1:
                if(!item.isChecked())
                {
                    if(typeface.isBold())
                    {
                        text.setTypeface(null,Typeface.BOLD_ITALIC);
                    }
                    else
                    {
                        text.setTypeface(null, Typeface.ITALIC);
                    }

                }
                else
                {
                    if(typeface.isBold())
                    {
                        text.setTypeface(null,Typeface.BOLD);
                    }
                    else
                    {
                        text.setTypeface(null, Typeface.NORMAL);
                    }
                }
                item.setChecked(!item.isChecked());
                return true;

            case R.id.checkable_item2:

                if(!item.isChecked())
                {
                    if(typeface.isItalic())
                    {
                        text.setTypeface(typeface,Typeface.BOLD_ITALIC);
                    }
                    else
                    {
                        text.setTypeface(typeface, Typeface.BOLD);
                    }

                }
                else
                {
                    if(typeface.isItalic())
                    {
                        text.setTypeface(typeface,Typeface.ITALIC);
                    }
                    else
                    {
                        text.setTypeface(null, Typeface.NORMAL);
                    }
                }
                item.setChecked(!item.isChecked());
                return true;
            default:
                return false;

        }
    }
}