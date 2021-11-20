package com.example.financeworks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var ref : DatabaseReference
    private lateinit var itemName : EditText
    private lateinit var itemDesc : EditText
    private lateinit var itemPrice : EditText
    private lateinit var buttonSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        itemName = findViewById(R.id.item_name4)
        itemName
        itemDesc = findViewById(R.id.item_description2)
        itemPrice = findViewById(R.id.item_price2)
        buttonSave = findViewById(R.id.save_button)

        buttonSave.setOnClickListener{
            val nameItem = itemName.text.toString()
            val descItem = itemDesc.text.toString()
            val priceItem = itemPrice.text.toString()

            if (nameItem.isEmpty()) {
                itemName.error = "Please enter your item name!"
                return@setOnClickListener
            }
            if (descItem.isEmpty()) {
                itemDesc.error = "Please enter your item description!"
                return@setOnClickListener
            }
            if (priceItem.isEmpty()) {
                itemPrice.error = "Please enter your item Price!"
                return@setOnClickListener
            }

            updateData(nameItem,descItem,priceItem)

            val back_btn : ImageButton = findViewById(R.id.back_btn4)
            back_btn.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateData(nameItem: String, descItem: String, priceItem: String) {
        ref = FirebaseDatabase.getInstance().getReference("Transaction")
        val Transaction = mapOf<String, String>(
            "nameItem" to nameItem,
            "descItem" to descItem,
            "priceItem" to priceItem
        )

        ref.child(nameItem).updateChildren(Transaction).addOnSuccessListener {
            Toast.makeText(this, "Transaction successfully edited", Toast.LENGTH_SHORT).show()
            itemName.text.clear()
            itemDesc.text.clear()
            itemPrice.text.clear()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Transaction edit failed", Toast.LENGTH_SHORT).show()
        }
    }
}