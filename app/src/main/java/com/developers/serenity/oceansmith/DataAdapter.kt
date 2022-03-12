package com.developers.serenity.oceansmith

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_adapter.view.*

class DataAdapter(
    private val dataResponse: List<DataClass>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        @SuppressLint("SetTextI18n")
        fun bind(dataClass: DataClass) {
            itemView.name.text = dataClass.name
            itemView.hb.text = "Habitat\n"+dataClass.hb

            Picasso.get()
                .load(dataClass.img.src)
                .fit()
                .placeholder(R.color.blue_dark)
                .centerInside()
                .into(itemView.img)

        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                onClickListener.onItemClick(position, dataResponse[position].name)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.info_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataClass = dataResponse[position])
    }

    interface OnClickListener {
        fun onItemClick(position: Int, name: String)
    }


    override fun getItemCount(): Int = dataResponse.size
}