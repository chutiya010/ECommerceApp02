package com.example.ecommerceapp02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.core.view.View
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadProductActivity : AppCompatActivity() {
    private lateinit var edtproductName: EditText
    private lateinit var edtproductPrice: EditText
    private lateinit var edtproductDes: EditText
    private lateinit var previewImage: ImageView
    private lateinit var btnselectProduct: Button
    private lateinit var btnuploadProduct: Button
    private lateinit var progressBar: ProgressBar




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)

        edtproductName = findViewById(R.id.edt_product_name)
        edtproductPrice = findViewById(R.id.edt_product_price)
        edtproductDes = findViewById(R.id.edt_product_des)
        previewImage = findViewById(R.id.product_preview_image)
        btnselectProduct = findViewById(R.id.btn_Select_Product)
        btnuploadProduct = findViewById(R.id.btn_Upload_Product)
        progressBar = findViewById(R.id.progress_Bar)



        btnselectProduct.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101 && resultCode  == RESULT_OK) {
            val uri = data?.data
            previewImage.setImageURI(uri)

            btnuploadProduct.setOnClickListener {
                //image code to upload image
                //to get image link
                progressBar.visibility = android.view.View.VISIBLE
                val productName = edtproductName.text.toString()
                val productPrice = edtproductPrice.text.toString()
                val productDes = edtproductDes.text.toString()

                val fileName = UUID.randomUUID().toString()+".jpg"
                val storageRef = FirebaseStorage.getInstance().reference.child("productImages/$fileName")
                storageRef.putFile(uri!!).addOnSuccessListener {
                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener {
                        Log.i("abcd",it.toString())

                        uploadProduct(
                            productName,
                            productPrice,
                            productDes,
                            it.toString()
                        )

                    }
                }
            }
        }



    }
    private fun uploadProduct(name:String, price: String, des: String, link: String){
        Firebase.database.getReference("products").child(name).setValue(
            Product(
                productName = name,
                productDes = des,
                productPrice = price,
                productImage = link
            )
        ).addOnSuccessListener {
            progressBar.visibility = android.view.View.GONE
            Toast.makeText(this, "Product is successfully uploaded", Toast.LENGTH_SHORT).show()
        }

    }



}