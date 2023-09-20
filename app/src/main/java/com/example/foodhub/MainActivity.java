package com.example.foodhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.foodhub.Adapters.MainAdapter;
import com.example.foodhub.Models.MainModel;
import com.example.foodhub.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<MainModel> list=new ArrayList<>();
        list.add(new MainModel((R.drawable.biryani),"Biryani","5","Hey Bro Accha Hai"));
        list.add(new MainModel((R.drawable.burger),"Burger","26","Kaise Ho Tum"));
        list.add(new MainModel((R.drawable.chicken),"Chicken","17","Khoob Khaao Chicken"));
        list.add(new MainModel((R.drawable.dosa),"Dosa","8","Itli Dosa Khaao Aur Khoob Maza Me Raho"));
        list.add(new MainModel((R.drawable.kabab),"Kabab","90","Kabaab Khaana Hai Toh Khaao Fir Tum"));
        list.add(new MainModel((R.drawable.noodles),"Noodles","10","Chinese Ka Noodles Khaao Tum"));
//        list.add(new MainModel((R.drawable.pasta),"Pasta","12","Aaj toh Kuch Naya Banate Hai"));
//        list.add(new MainModel((R.drawable.pizza),"Pizza","13","Aaj Toh Bhai Pizzza Khaayenge"));

        MainAdapter adapter=new MainAdapter(list,this);
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
           int menuId=item.getItemId();

            if(menuId==R.id.orders)
                startActivity(new Intent(MainActivity.this,OrderActivity.class));

        return super.onOptionsItemSelected(item);
    }
}