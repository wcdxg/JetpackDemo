package com.yuaihen.viewmodel.room

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.item_room_card.view.*

/**
 * Created by Yuaihen.
 * on 2020/12/4
 */
class RoomAdapter(private val useCardView: Boolean, val viewModel: WordViewModel) :
    RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {
    private var data: List<Word> = arrayListOf()

    fun setWordsData(word: List<Word>) {
        this.data = word
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                if (useCardView) R.layout.item_room_card else R.layout.item_room_normal,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val word = data[position]
        holder.itemView.indexText.text = position.plus(1).toString()
        holder.itemView.englishText.text = word.englishWord
        holder.itemView.chineseText.text = word.chineseMeaning

        //需要先设置为null 避免item复用导致显示状态错误
        holder.itemView.chineseSwitch.setOnCheckedChangeListener(null)

        if (word.chineseInvisible) {
            holder.itemView.chineseText.visibility = View.GONE
            holder.itemView.chineseSwitch.isChecked = true
        } else {
            holder.itemView.chineseText.visibility = View.VISIBLE
            holder.itemView.chineseSwitch.isChecked = false
        }

        holder.itemView.chineseSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.itemView.chineseText.visibility = View.GONE
                word.chineseInvisible = true
                viewModel.wordRepository.updateWords(word)
            } else {
                holder.itemView.chineseText.visibility = View.VISIBLE
                word.chineseInvisible = false
                viewModel.wordRepository.updateWords(word)
            }
        }

        holder.itemView.rootView.setOnClickListener {
            val uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=${data[position].englishWord}")
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