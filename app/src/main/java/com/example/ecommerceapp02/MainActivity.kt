package com.example.ecommerceapp02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var productAdapter: ProductAdapter
    val listofProduct: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rv = findViewById(R.id.rv)
        fab = findViewById(R.id.fab)

        //code to get data from database and upload it
        FirebaseDatabase.getInstance().getReference("products")
            .addValueEventListener(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    listofProduct.clear()
                   for (dataSnapshot in snapshot.children){
                       val product= dataSnapshot.getValue(Product::class.java)
                       listofProduct.add(product!!)
                   }
                    productAdapter = ProductAdapter(listofProduct,this@MainActivity)
                    rv.layoutManager = GridLayoutManager(this@MainActivity,2)
                    rv.adapter = productAdapter

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })




        fab.setOnClickListener(){
            startActivity(
                Intent(this, LoginActivity::class.java)
            )

        }

    }

}