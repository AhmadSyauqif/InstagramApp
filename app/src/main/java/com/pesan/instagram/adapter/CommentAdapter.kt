package com.pesan.instagram.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pesan.instagram.R
import com.pesan.instagram.model.user
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

import org.w3c.dom.Comment

class CommentAdapter(private val mContext: Context, private val mComment: MutableList<com.pesan.instagram.model.Comment>)
    :RecyclerView.Adapter<CommentAdapter.ViewHolder>(), Parcelable {

    private var firebaseUser: FirebaseUser? = null

    constructor(parcel: Parcel) : this(
        TODO("mContext"),
        TODO("mComment")
    ) {
        firebaseUser = parcel.readParcelable(FirebaseUser::class.java.classLoader)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //inisialisasi id comment layout
        var imageProfile: CircleImageView = itemView.findViewById(R.id.user_profile_image_comment)
        var userNameCommentTV: TextView = itemView.findViewById(R.id.user_name_comment)
        var commentTv: TextView = itemView.findViewById(R.id.comment_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.comment_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mComment.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val comment = mComment[position]

        holder.commentTv.text = comment.getComment()

        //create method untuk ngambil datauser
        getUserInfo(holder.imageProfile, holder.userNameCommentTV, comment.getPublisher())
    }

    private fun getUserInfo(
        imageProfile: CircleImageView,
        userNameCommentTV: TextView,
        publisher: String
    ) {
        val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(publisher)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user = p0.getValue(user::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile)
                        .into(imageProfile)
                    userNameCommentTV.text = user!!.getUsername()
                }
            }
        })
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(firebaseUser, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentAdapter> {
        override fun createFromParcel(parcel: Parcel): CommentAdapter {
            return CommentAdapter(parcel)
        }

        override fun newArray(size: Int): Array<CommentAdapter?> {
            return arrayOfNulls(size)
        }
    }

}