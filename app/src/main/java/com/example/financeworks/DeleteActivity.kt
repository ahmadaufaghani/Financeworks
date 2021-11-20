package com.example.financeworks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var itemName: EditText
    private lateinit var deleteButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        itemName= findViewById(R.id.item_name2)
        deleteButton = findViewById(R.id.delete_button)

        val back_btn: ImageButton = findViewById(R.id.back_btn3)
        back_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        deleteButton.setOnClickListener{

            val nameItem = itemName.text.toString()
            if (nameItem.isNotEmpty()) {
                deleteTransaction(nameItem)
            }
            else {
                Toast.makeText(this, "Please enter your item name!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun deleteTransaction(nameItem: String) {

        dbref = FirebaseDatabase.getInstance().getReference("Transaction")
        dbref.child(nameItem).removeValue().addOnSuccessListener {
            itemName.text.clear()
            Toast.makeText(this, "Transaction successfully deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Transaction deletion fail", Toast.LENGTH_SHORT).show()
        }

    }
}