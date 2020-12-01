package com.example.applicationinstagram.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationinstagram.R
import com.example.applicationinstagram.model.Post
import com.squareup.picasso.Picasso

class MyImageAdapter (private val mContext: Context, mPost: List<Post>)
    :RecyclerView.Adapter<MyImageAdapter.ViewHolder?>(){

    private var mPost: List<Post>? = null

    init {
        this.mPost = mPost
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var postImage: ImageView = itemView.findViewById(R.id.post_image_grid)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.images_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPost!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: Post = mPost!![position]

        Picasso.get().load(post.getPostImage()).into(holder.postImage)
    }

}