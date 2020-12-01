package com.pesan.instagram.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.pesan.instagram.activity.CommentsActivity
import com.pesan.instagram.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pesan.instagram.R
import com.pesan.instagram.model.Post
import com.pesan.instagram.model.user
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.post_layout.view.*

class PostAdapter(private val mContext: Context, private val mPost: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = null

    class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var profileImage: CircleImageView = itemView.findViewById(R.id.user_profile_image_post)
        var postImage: ImageView = itemView.findViewById(R.id.post_image_home)
        var likeButton: ImageView = itemView.findViewById(R.id.post_image_like_btn)
        var commentButton: ImageView = itemView.findViewById(R.id.post_image_comment_btn)
        var saveButton: ImageView = itemView.findViewById(R.id.post_save_btn)
        var userName: TextView = itemView.findViewById(R.id.post_user_name)
        var likes: TextView = itemView.findViewById(R.id.post_likes)
        var publisher: TextView = itemView.findViewById(R.id.post_publisher)
        var description: TextView = itemView.findViewById(R.id.post_description)
        var comments: TextView = itemView.findViewById(R.id.post_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.post_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser

        val post = mPost[position]
        Picasso.get().load(post.getPostImage()).into(holder.postImage)
        if (post.getDescription().equals("")){
            holder.description.visibility = View.GONE
        } else {
            holder.description.visibility = View.VISIBLE
            holder.description.setText(post.getDescription())
        }

        //method publish postingan
        publisherInfo(holder.profileImage, holder.userName, holder.publisher, post.getPublisher())
        //method setting icon
        isLikes(post.getPostid(), holder.likeButton)
        //method untuk melihat berapa user yang like
        numberOfLikes(holder.likes, post.getPostid())
        //method untuk menampilkan total user comment
        getTotalComment(holder.comments, post.getPostid())

        holder.likeButton.setOnClickListener {
            if (holder.likeButton.tag == "Like"){
                FirebaseDatabase.getInstance().reference
                        .child("Likes").child(post.getPostid()).child(firebaseUser!!.uid)
                        .setValue(true)
            }
            else
            {
                FirebaseDatabase.getInstance().reference
                        .child("Likes").child(post.getPostid()).child(firebaseUser!!.uid)
                        .removeValue()

                val intent = Intent(mContext, MainActivity::class.java)
                mContext.startActivity(intent)
            }
        }

        holder.commentButton.setOnClickListener {
            val intenComment = Intent(mContext, CommentsActivity::class.java)
            intenComment.putExtra("postId", post.getPostid())
            intenComment.putExtra("publisherId", post.getPublisher())
            mContext.startActivity(intenComment)
        }

        holder.comments.setOnClickListener {
            val intenComment = Intent(mContext, CommentsActivity::class.java)
            intenComment.putExtra("postId", post.getPostid())
            intenComment.putExtra("publisherId", post.getPublisher())
            mContext.startActivity(intenComment)
        }

    }

    private fun getTotalComment(comments: TextView, postid: String) {
        val commentRef = FirebaseDatabase.getInstance().reference
            .child("Comments").child(postid)

        commentRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    comments.text = "view all" + p0.childrenCount.toString() + "comments"
                }
            }
        })
    }

    private fun numberOfLikes(likes: TextView, postid: String) {
        val likeRef = FirebaseDatabase.getInstance().reference
                .child("Likes").child(postid)

        likeRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    likes.text = p0.childrenCount.toString() + "likes"
                }
            }
        })
    }

    private fun isLikes(postid: String, likeButton: ImageView) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val likesRef = FirebaseDatabase.getInstance().reference
                .child("Likes").child(postid)


        likesRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.child(firebaseUser!!.uid).exists()){
                    likeButton.setImageResource(R.drawable.heart_clicked)
                    likeButton.tag = "Liked"
                }
                else
                {
                    likeButton.setImageResource(R.drawable.heart_not_clicked)
                    likeButton.tag = "Like"
                }
            }
        })
    }

    private fun publisherInfo(
        profileImage: CircleImageView,
        userName: TextView,
        publisher: TextView,
        publisherID: String
    ) {
        val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(publisherID)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user = p0.getValue<user>(user::class.java)

                    Picasso.get().load(user?.getImage()).placeholder(R.drawable.profile)
                        .into(profileImage)
                    userName.text = user?.getUsername()
                    publisher.text = user?.getFullname()
                }
            }
        })
    }
}