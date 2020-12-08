package com.yuaihen.viewmodel.room

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.item_room_card.view.*

/**
 * Created by Yuaihen.
 * on 2020/12/4
 */
class RoomAdapter(
    private var useCardView: Boolean,
    private var viewModel: WordViewModel,
    diffCallback: DiffUtil.ItemCallback<Word> = object : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return (oldItem.englishWord == newItem.englishWord) &&
                    (oldItem.chineseInvisible == newItem.chineseInvisible) &&
                    (oldItem.chineseMeaning == newItem.chineseMeaning)
        }
    }
) : ListAdapter<Word, RoomAdapter.MyViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                if (useCardView) R.layout.item_room_card else R.layout.item_room_normal,
                parent,
                false
            )
        )

        holder.itemView.chineseSwitch.setOnCheckedChangeListener { _, isChecked ->
            val word = holder.itemView.getTag(R.id.word_for_view_holder) as Word
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
            val uri =
                Uri.parse("http://m.youdao.com/dict?le=eng&q=${holder.itemView.englishText.text}")
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = uri
            }
            holder.itemView.context.startActivity(intent)

        }

        return holder
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.indexText.text = holder.adapterPosition.plus(1).toString()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val word = getItem(position)
        holder.itemView.setTag(R.id.word_for_view_holder, word)
        holder.itemView.indexText.text = position.plus(1).toString()
        holder.itemView.englishText.text = word.englishWord
        holder.itemView.chineseText.text = word.chineseMeaning

        if (word.chineseInvisible) {
            holder.itemView.chineseText.visibility = View.GONE
            holder.itemView.chineseSwitch.isChecked = true
        } else {
            holder.itemView.chineseText.visibility = View.VISIBLE
            holder.itemView.chineseSwitch.isChecked = false
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}