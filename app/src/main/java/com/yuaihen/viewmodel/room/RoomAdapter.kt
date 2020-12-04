package com.yuaihen.viewmodel.room

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.item_room.view.*

/**
 * Created by Yuaihen.
 * on 2020/12/4
 */
class RoomAdapter(private val useCardView: Boolean) :
    RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {
    private val data = mutableListOf<Word>()

    fun setWordsData(word: List<Word>) {
        if (word.isEmpty()) {
            data.clear()
        } else {
            data.addAll(word)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                if (useCardView) R.layout.item_room_card else R.layout.item_room,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.indexText.text = position.plus(1).toString()
        holder.itemView.engText.text = data[position].word
        holder.itemView.chnText.text = data[position].chineseMeaning

        holder.itemView.setOnClickListener {
            val uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=${data[position].word}")
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = uri
            }
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}