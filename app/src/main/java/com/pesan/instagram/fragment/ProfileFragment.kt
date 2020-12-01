package com.pesan.instagram

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pesan.instagram.activity.AccountSettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pesan.instagram.model.user
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*

//
//class ProfileFragment : Fragment() {
//
//    private lateinit var profileId: String
//    private lateinit var firebaseUser: FirebaseUser
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //Inflate the layout for this fragment
//        val viewProfile = inflater.inflate(R.layout.fragment_profile, container, false)
//
//        firebaseUser = FirebaseAuth.getInstance().currentUser!!
//
//        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
//        if (pref !=null) {
//
//            this.profileId = pref.getString("profileId", "none")!!
//
//        }
//
//        if (profileId == firebaseUser.uid){
//            view?.btn_edit_account?.text = "Edit Profile"
//        } else if (profileId != firebaseUser.uid){
//
//            cekFollowAndFollowingButtonStatus()
//        }
//
//        viewProfile.btn_edit_account.setOnClickListener {
//            val getButtonText = view?.btn_edit_account?.text.toString()
//
//            when{
//                getButtonText == "Edit Profile" -> startActivity(Intent(context, AccountSettingActivity::class.java))
//
//                getButtonText == "Follow" ->{
//                    firebaseUser?.uid.let {
//                        FirebaseDatabase.getInstance().reference
//                            .child("Follow").child(it.toString())
//                            .child("Following").child(profileId).setValue(true)
//                    }
//
//                    firebaseUser?.uid.let {
//                        FirebaseDatabase.getInstance().reference
//                            .child("Follow").child(profileId)
//                            .child("Followers").child(it.toString()).setValue(true)
//                    }
//                }
//
//                getButtonText == "Following" ->{
//                    firebaseUser?.uid.let {
//                        FirebaseDatabase.getInstance().reference
//                            .child("Follow").child(it.toString())
//                            .child("Following").child(profileId).removeValue()
//                    }
//
//                    firebaseUser?.uid.let {
//                        FirebaseDatabase.getInstance().reference
//                            .child("Follow").child(profileId)
//                            .child("Followers").child(it.toString()).removeValue()
//                    }
//                }
//            }
//        }
//
//        getFollowers()
//        getFollowing()
//        userInfo()
//        return viewProfile
//    }
//
//    private fun userInfo() {
//        val userRef = FirebaseDatabase.getInstance().reference
//            .child("Users").child(profileId)
//
//        userRef.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//               if (p0.exists()){
//                   val user = p0.getValue<user>(user::class.java)
//
//                   Picasso.get().load(user?.getImage()).placeholder(R.drawable.profile)
//                       .into(view!!.profile_image)
//                   view?.profile_fragment_usernmae?.text = user?.getUsername()
//                   view?.txt_full_namaProfile?.text = user?.getFullname()
//                   view?.txt_bio_profile?.text = user?.getBio()
//               }
//            }
//        })
//    }
//
//    private fun getFollowing() {
//        val followingRef = FirebaseDatabase.getInstance().reference
//            .child("Follow").child(profileId)
//            .child("Following")
//
//        followingRef.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if (p0.exists()){
//                    view?.txt_totalFollowing?.text = p0.childrenCount.toString()
//                }
//            }
//        })
//    }
//
//    private fun getFollowers() {
//        val followersRef = FirebaseDatabase.getInstance().reference
//            .child("Follow").child(profileId)
//            .child("Followers")
//
//        followersRef.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if (p0.exists()){
//                    view?.txt_totalFollowers?.text = p0.childrenCount.toString()
//                }
//            }
//        })
//    }
//
//    private fun cekFollowAndFollowingButtonStatus() {
//        val followingRef = firebaseUser?.uid.let {
//            FirebaseDatabase.getInstance().reference
//                .child("Follow").child(it.toString())
//                .child("Following")
//        }
//
//        if (followingRef !=null){
//            followingRef.addValueEventListener(object : ValueEventListener{
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    if (p0.child(profileId).exists()){
//                        view?.btn_edit_account?.text = "Following"
//                    } else {
//                        view?.btn_edit_account?.text = "Follow"
//                    }
//                }
//            })
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId", firebaseUser.uid)
//        pref?.apply()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId", firebaseUser.uid)
//        pref?.apply()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId", firebaseUser.uid)
//        pref?.apply()
//    }
//}

class ProfileFragment : Fragment() {

    private lateinit var profileId: String
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        val viewProfile = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if (pref !=null) {

            this.profileId = pref.getString("profileId", "none")!!

        }

        if (profileId == firebaseUser.uid){
            view?.btn_edit_account?.text = "Edit Profile"
        } else if (profileId != firebaseUser.uid){

            cekFollowAndFollowingButtonStatus()
        }

        viewProfile.btn_edit_account.setOnClickListener {
            val getButtonText = view?.btn_edit_account?.text.toString()

            when{
                getButtonText == "Edit Profile" -> startActivity(Intent(context, AccountSettingActivity::class.java))

                getButtonText == "Follow" ->{
                    firebaseUser?.uid.let {
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(it.toString())
                            .child("Following").child(profileId).setValue(true)
                    }

                    firebaseUser?.uid.let {
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileId)
                            .child("Followers").child(it.toString()).setValue(true)
                    }
                }

                getButtonText == "Following" ->{
                    firebaseUser?.uid.let {
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(it.toString())
                            .child("Following").child(profileId).removeValue()
                    }

                    firebaseUser?.uid.let {
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileId)
                            .child("Followers").child(it.toString()).removeValue()
                    }
                }
            }
        }

        getFollowers()
        getFollowing()
        userInfo()
        return viewProfile
    }

    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance().reference
            .child("Users").child(profileId)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user = p0.getValue<user>(user::class.java)

                    Picasso.get().load(user?.getImage()).placeholder(R.drawable.profile)
                        .into(view!!.profile_image)
                    view?.profile_fragment_usernmae?.text = user?.getUsername()
                    view?.txt_full_namaProfile?.text = user?.getFullname()
                    view?.txt_bio_profile?.text = user?.getBio()
                }
            }
        })
    }

    private fun getFollowing() {
        val followingRef = FirebaseDatabase.getInstance().reference
            .child("Follow").child(profileId)
            .child("Following")

        followingRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    view?.txt_totalFollowing?.text = p0.childrenCount.toString()
                }
            }
        })
    }

    private fun getFollowers() {
        val followersRef = FirebaseDatabase.getInstance().reference
            .child("Follow").child(profileId)
            .child("Followers")

        followersRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    view?.txt_totalFollowers?.text = p0.childrenCount.toString()
                }
            }
        })
    }

    private fun cekFollowAndFollowingButtonStatus() {
        val followingRef = firebaseUser?.uid.let {
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(it.toString())
                .child("Following")
        }

        if (followingRef !=null){
            followingRef.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.child(profileId).exists()){
                        view?.btn_edit_account?.text = "Following"
                    } else {
                        view?.btn_edit_account?.text = "Follow"
                    }
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }
}

