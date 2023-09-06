package com.example.firebaseapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_view)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)


        // Real Time Database
        database = Firebase.database.reference
        // Write Custom data to Firebase

        val user1 = User("Uzair Jamal","uzairjamal123")
        database.child("Users").setValue(user1)

        // Get user data
        val pe = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val u1 = snapshot.getValue<User>()
                textView2.text = u1.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.child("Users").addValueEventListener(pe)


        // Write data to Firebase
        database.child("price").setValue("302022 $")

        // Read Data from Firebase
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val goldPrice = snapshot.value
                textView.text = goldPrice.toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.child("price").addValueEventListener(postListener)




        // Firebase Firestore initialization
        val db = Firebase.firestore

        val users_collection = db.collection("Users")

        // Creating documents
        val user2 = hashMapOf(
            "name" to "Uzair",
            "lname" to "Jamal",
            "born" to 1999
        )
        val user3 = hashMapOf(
            "name" to "Jhon",
            "lname" to "Wick",
            "born" to 1990
        )
        val user4 = hashMapOf(
            "name" to "James",
            "lname" to "Bond",
            "born" to "1786"


        )

        // Adding documents to collection
        users_collection.document("user2").set(user2)
        users_collection.document("user3").set(user3)
        users_collection.document("user4").set(user4)

        db.collection("Users")
            .document("user2")
            .delete()

        // Receive Documents from Firestore
//       val docRef = db.collection("Users").document("user3")

        // Getting specific documents from Firestore
//        docRef.get().addOnSuccessListener { document ->
//            if(document != null){
//                textView3.text = "${document.data?.get("name")}"
//            }
//        }

        // Getting all documents from Firestore collection

    //        var allDocuments : String = ""
//        db.collection("Users").get().addOnSuccessListener { result ->
//            for (document in result) {
//                //Log.i("TAGY","${document.data}")
//                allDocuments += "${document.data} \n"
//            }
//            textView3.text = ""+allDocuments
//        }

    }

}