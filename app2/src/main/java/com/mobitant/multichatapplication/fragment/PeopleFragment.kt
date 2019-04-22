package com.mobitant.multichatapplication.fragment


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobitant.multichatapplication.R
import com.mobitant.multichatapplication.message.MessageActivity
import com.mobitant.multichatapplication.model.UserModel
import java.util.*

//recyclerView 변수를 만들어서 layoutManager와 ViewAdapter를 구현해야 한다.
class PeopleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_people, container, false)
        val recyclerView = view.findViewById(R.id.peoplefragment_recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)
        recyclerView.adapter = PeopleFragmentRecyclerViewAdapter()
        return view
    }

    internal inner class PeopleFragmentRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var userModels: MutableList<UserModel> = ArrayList()

        //초기화 작업!
        //FirebaseDatabase에 있는 정보들을 가져온다. 가져온 정보들을 userModel 배열에 저장한다.
        init {
            FirebaseDatabase.getInstance().reference.child("users").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    userModels.clear()
                    for (snapshot in dataSnapshot.children) {
                        (userModels as ArrayList<UserModel>).add(snapshot.getValue(UserModel::class.java)!!)
                    }
                    notifyDataSetChanged()

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }

        //재활용(반복)되는 layout을 가져온다.
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
            return CustomViewHolder(view)
        }

        //이미지를 Glide라이브러리를 이용하여 holder에 넣어준다.
        //userModel배열에 저장된 이름을 holder에 넣어준다.
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            Glide.with(holder.itemView.context)
                .load(userModels[position].profileImageUrl)
                .apply(RequestOptions().circleCrop())
                .into((holder as CustomViewHolder).imageView)
            holder.textView.text = userModels[position].userName

            //하나의 item을 선택하였을 경우에 다음 액티비티로 애니메이션을 지정한다.
            holder.itemView.setOnClickListener {
                var activityOptions : ActivityOptions = ActivityOptions.makeCustomAnimation(view!!.context,R.anim.fromright,R.anim.toleft)

                var intent = Intent(context, MessageActivity::class.java)
                intent.putExtra("destinationUid", userModels[position].uid)
                startActivity(intent,activityOptions.toBundle())
            }


        }

        override fun getItemCount(): Int {
            return userModels.size
        }

        //Holder는 xml의 View들을 캡슐화해주는 class이다.
        private inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var imageView: ImageView = view.findViewById(R.id.frienditem_imageview) as ImageView
            var textView: TextView = view.findViewById(R.id.frienditem_textview) as TextView

        }
    }

}
