package com.example.ecommerceapp02

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
     private val listofProducts: List<Product>,
     private val context: Context
 ) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

   class ProductViewHolder(

       itemView : View
   ): RecyclerView.ViewHolder(itemView){

       val productImg : ImageView = itemView.findViewById(R.id.product_img)
       val productName : TextView = itemView.findViewById(R.id.product_name)
       val productPrice : TextView = itemView.findViewById(R.id.product_price)
       val productDes : TextView = itemView.findViewById(R.id.product_des)

   }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.productlayout, parent, false)
         return ProductViewHolder(view)
     }

     override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
         // Bind your data to the views here
         val currentProduct = listofProducts[position]
         holder.productName.text = currentProduct.productName
         holder.productPrice.text = currentProduct.productPrice
         holder.productDes.text = currentProduct.productDes
         //image code is left for that we use glide
         Glide.with(context)
             .load(currentProduct.productImage)
             .into(holder.productImg)
     }

     override fun getItemCount(): Int {
         // Return the number of items in your data set
         return listofProducts.size
     }




 }


