package com.example.foodhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodhub.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper dbHelper = new DBHelper(this);

        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q=  Integer.parseInt(binding.quantity.getText().toString());
                q++;
                binding.quantity.setText(q+"");

            }
        });

       if(getIntent().getIntExtra("type",0)==1) {  // for inserting

          

           binding.minus.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int q=  Integer.parseInt(binding.quantity.getText().toString());

                   if(q==0)
                   {
                       new AlertDialog.Builder(DetailActivity.this)
                               .setTitle("Delete Item")
                               .setMessage("Are you sure to delete this item?")
                               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                       Intent intent=new Intent(DetailActivity.this,OrderActivity.class);
                                       startActivity(intent);
                                       Toast.makeText(DetailActivity.this, "", Toast.LENGTH_SHORT).show();
                                   }
                               })
                               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                       int q=1;
                                       binding.quantity.setText(q+"");
                                       dialogInterface.cancel();
                                   }
                               }).show();
                   }
                   else if(q>0)
                   {
                       q--;
                       binding.quantity.setText(q+"");
                   }
                   else {

                   }
               }
           });
           
           
         final int image = getIntent().getIntExtra("image", 0);
          final int price = Integer.parseInt(getIntent().getStringExtra("price"));
           final String name = getIntent().getStringExtra("name");
           final String description = getIntent().getStringExtra("desc");

           binding.detailImage.setImageResource(image);
           binding.detailPrice.setText(price+"");
           binding.detailName.setText(name);
           binding.detailDescription.setText(description);


           binding.orderButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   boolean isInserted = dbHelper.insertOrder(
                           binding.nameBox.getText().toString(),
                           binding.phoneBox.getText().toString(),
                           price,
                           image,
                           description,
                           name,
                           Integer.parseInt(binding.quantity.getText().toString())
                   );

                   if (isInserted)
                       Toast.makeText(DetailActivity.this, "Data Successful", Toast.LENGTH_SHORT).show();
                   else
                       Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
               }
           });
       }
       else
       {
           // for update
           int id=getIntent().getIntExtra("id",0);


           binding.minus.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int q=  Integer.parseInt(binding.quantity.getText().toString());

                   if(q==1)
                   {
                       new AlertDialog.Builder(DetailActivity.this)
                               .setTitle("Delete Item")
                               .setMessage("Are you sure to delete this item?")
                               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                       if(dbHelper.deleteOrder(id+"")>0)
                                       {

                                           Intent intent=new Intent(DetailActivity.this,OrderActivity.class);
                                           startActivity(intent);
                                           Toast.makeText(DetailActivity.this, "Deleted Item Successfully", Toast.LENGTH_SHORT).show();
                                       }
                                       else
                                       {
                                           Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                       }

                                   }
                               })
                               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                       int q=1;
                                       binding.quantity.setText(q+"");
                                       dialogInterface.cancel();
                                   }
                               }).show();
                   }
                   else if(q>1)
                   {
                       q--;
                       binding.quantity.setText(q+"");
                   }
                   else {
                       Toast.makeText(DetailActivity.this, "Quantity cannot be negative!!!", Toast.LENGTH_SHORT).show();
                   }
               }
           });

           Cursor cursor=dbHelper.getOrderById(id);
           int image=cursor.getInt(4);

           binding.detailImage.setImageResource(image);
           binding.quantity.setText(String.format("%d", cursor.getInt(5)));
           binding.detailPrice.setText(String.format("%d", cursor.getInt(3)));
           binding.detailName.setText(cursor.getString(7));
           binding.detailDescription.setText(cursor.getString(6));
           binding.nameBox.setText(cursor.getString(1));
           binding.phoneBox.setText(cursor.getString(2));
           binding.orderButton.setText("Update Now");

           binding.orderButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   boolean isUpdated = dbHelper.updateOrder(
                           binding.nameBox.getText().toString(),
                           binding.phoneBox.getText().toString(),
                           Integer.parseInt(binding.detailPrice.getText().toString()),
                           image,
                           binding.detailDescription.getText().toString(),
                           binding.detailName.getText().toString(),
                           Integer.parseInt(binding.quantity.getText().toString()),
                           id
                   );

                   if (isUpdated)
                       Toast.makeText(DetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                   else
                       Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
               }
           });
       }
    }
}