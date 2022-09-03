package com.example.whooby

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
internal class
personAdapter(
    options: FirebaseRecyclerOptions<User>
) : FirebaseRecyclerAdapter<User, personAdapter.personsViewholder>(options) {
    // Function to bind the view in Card view with data in
    // model class(here "person.class")
    override fun onBindViewHolder(
        holder: personsViewholder,
        position: Int, model: User
    ) {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.statement.text = model.getStatement()

    }

    // Function to tell the class about the Card view in
    // which the data will be shown
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): personsViewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return personsViewholder(view)
    }

    internal inner class personsViewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var statement: TextView


        init {
            statement = itemView.findViewById(R.id.msgcontent)

        }
    }

}