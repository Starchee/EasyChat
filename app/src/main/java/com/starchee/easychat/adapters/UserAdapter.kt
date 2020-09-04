package com.starchee.easychat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.starchee.easychat.R
import com.starchee.easychat.models.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var sourceList: ArrayList<User> = ArrayList()
    private var usersList: ArrayList<User> = ArrayList()

    fun setupUsers(userList: List<User>) {
        sourceList.clear()
        sourceList.addAll(userList)
        filter(query = "")
    }

    private fun filter(query: String) {
        usersList.clear()
        sourceList.forEach {
            if (it.name!!.contains(query, ignoreCase = true)) {
                usersList.add(it)
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UsersViewHolder) {
            holder.bind(user = usersList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView = layoutInflater.inflate(R.layout.users_recycler_item, parent, false)
        return UsersViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return usersList.count()
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var userCivPhoto: CircleImageView = itemView.findViewById(R.id.user_item_civ_photo)
        private var userTxtName: TextView = itemView.findViewById(R.id.user_item_txt_name)


        fun bind(user: User) {
            Picasso.get().load(user.photo).into(userCivPhoto)
            userTxtName.text = "${user.name}"
        }
    }

}