package com.developers.serenity.oceansmith

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class InfoMore : AppCompatActivity() {

    private var pos = 0

    private lateinit var arrayList: ArrayList<MoreClass>
    private var adapter: MoreAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle: Bundle? = intent.extras
        pos = bundle?.getInt("pos", 0)!!
        val name = bundle.getString("name", "")

        toolBar.title = "Image Gallery"
        toolBar.subtitle = name

        listView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        setSupportActionBar(toolBar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        loadData()

    }

    private fun loadData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.fishwatch.gov/api/species"
        val req = StringRequest(Request.Method.GET, url,
            { response ->
                val data = response.toString()
                val jArray = JSONArray(data)
                val dataA = jArray.get(pos).toString()

                val jsonObj = JSONObject(dataA)
                arrayList = ArrayList()


                if (jsonObj.optJSONArray("Image Gallery") == null) {
                    noImgs()
                } else {
                    val dataArray = jsonObj.getJSONArray("Image Gallery")
                    for (i in 0 until dataArray.length()) {

                        val mc = MoreClass()
                        val obj = dataArray.getJSONObject(i)

                        mc.setTitles(obj.getString("title"))
                        mc.setImgs(obj.getString("src"))

                        arrayList.add(mc)
                    }
                    adapter = MoreAdapter(this, arrayList)
                    listView!!.adapter = adapter
                    prog.visibility = View.GONE
                }

            }, {
                (
                        loadData0()
                        )
            })
        queue.add(req)
    }

    private fun loadData0() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.fishwatch.gov/api/species"
        val req = StringRequest(Request.Method.GET, url,
            { response ->
                val data = response.toString()
                val jArray = JSONArray(data)
                val dataA = jArray.get(pos).toString()

                val jsonObj = JSONObject(dataA)
                arrayList = ArrayList()


                if (jsonObj.optJSONArray("Image Gallery") == null) {
                    noImgs()
                } else {
                    val dataArray = jsonObj.getJSONArray("Image Gallery")
                    for (i in 0 until dataArray.length()) {

                        val mc = MoreClass()
                        val obj = dataArray.getJSONObject(i)

                        mc.setTitles(obj.getString("title"))
                        mc.setImgs(obj.getString("src"))

                        arrayList.add(mc)
                    }
                    adapter = MoreAdapter(this, arrayList)
                    listView!!.adapter = adapter
                    prog.visibility = View.GONE
                }

            }, {
                (
                        showErr()

                        )
            })
        queue.add(req)
    }

    private fun noImgs() {
        prog.visibility = View.GONE
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        @SuppressLint("InflateParams") val view: View =
            layoutInflater.inflate(R.layout.bottom_sheet_single, null)
        bottomSheetDialog.setContentView(view)
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)
                .toInt()
        bottomSheetDialog.show()

        val txt1 = view.findViewById<TextView>(R.id.txt)
        txt1.text = getString(R.string.empty_gallery)

        txt1.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showErr() {
        prog.visibility = View.GONE
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        @SuppressLint("InflateParams") val view: View =
            layoutInflater.inflate(R.layout.bottom_sheet_single, null)
        bottomSheetDialog.setContentView(view)
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)
                .toInt()
        bottomSheetDialog.show()

        val txt1 = view.findViewById<TextView>(R.id.txt)
        txt1.text = getString(R.string.click_to_retry)

        txt1.setOnClickListener {
            prog.visibility = View.VISIBLE
            bottomSheetDialog.dismiss()
            loadData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}