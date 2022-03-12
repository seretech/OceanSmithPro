package com.developers.serenity.oceansmith

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class MoreAdapter(private val context: Context,
                  private val arrayList: ArrayList<MoreClass>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.info_adapter, null, true)

            holder.img = convertView!!.findViewById<View>(R.id.img) as ImageView
            holder.title = convertView.findViewById<View>(R.id.name) as TextView
            holder.hb = convertView.findViewById<View>(R.id.hb) as TextView

            holder.hb!!.visibility = View.GONE

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.title!!.text = arrayList[position].getTitles()

        Picasso.get()
            .load(arrayList[position].getImgs())
            .fit()
            .placeholder(R.color.blue_dark)
            .centerInside()
            .into(holder.img)

        return convertView
    }

    private inner class ViewHolder {

        var title: TextView? = null
        var hb: TextView? = null
        var img: ImageView? = null
    }

}