package com.example.financeworks

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Transaction>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //signout
        auth = FirebaseAuth.getInstance()
        val signoutBtn: ImageButton = findViewById(R.id.signout_btn)
        signoutBtn.setOnClickListener {
            auth.signOut()
            Intent(this@MainActivity, SignInActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Signing Out...")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        //update
        val updateBtn: ImageButton = findViewById(R.id.update_btn)
        updateBtn.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)
            startActivity(intent)
        }

        //delete
        val deleteBtn: ImageButton = findViewById(R.id.delete_btn)
        deleteBtn.setOnClickListener {
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }


        //RecylerView Read from database (Firebase)
        userRecyclerView = findViewById(R.id.transaction_list)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<Transaction>()
        getTransactionData()


        //add float button
        val addflt_btn = findViewById<FloatingActionButton>(R.id.create_btn)
        addflt_btn.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getTransactionData() {
        dbref = FirebaseDatabase.getInstance().getReference("Transaction")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (transactionSnapshot in snapshot.children) {

                        val transaction = transactionSnapshot.getValue(Transaction::class.java)
                        userArrayList.add(transaction!!)
                    }

                    userRecyclerView.adapter = Adapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}