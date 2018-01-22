package com.example.rabee.recyclerviewspeedtest.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.rabee.recyclerviewspeedtest.Activities.HomeActivity;
import com.example.rabee.recyclerviewspeedtest.R;
import com.example.rabee.recyclerviewspeedtest.ReactsRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ReactsRecyclerViewModel> reactsList = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent= new Intent(this,HomeActivity.class);
        startActivity(intent);

    }
}
