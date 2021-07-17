package com.arnomutai.mynet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.arnomutai.mynet.R
import com.arnomutai.mynet.models.user
import com.squareup.picasso.Picasso

class userAdapter  (private var mContext: Context, private var mUser:List<user>) : RecyclerView.Adapter<userAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):userAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_item_layout, parent,false)
        return userAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: userAdapter.ViewHolder, position: Int) {
        val user = mUser[position]
        holder.userNameTextView.text = user.getUserName()
        holder.userFullNameTextView.text = user.getFullName()
        Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into(holder.userProfileImage)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var userNameTextView: TextView = itemView.findViewById(R.id.user_name_search)
        var userFullNameTextView: TextView = itemView.findViewById(R.id.user_full_name_search)
        var userProfileImage: ImageView = itemView.findViewById(R.id.user_profile_image)
        var userFollowBtn: Button = itemView.findViewById(R.id.follow_btn)

    }
}