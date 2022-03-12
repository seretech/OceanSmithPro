package com.developers.serenity.oceansmith

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), DataAdapter.OnClickListener {

    private val reInstance : ReInstance = ReInstance()

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        fetchData()
    }

    private fun fetchData() {
        val fetchDataAll = Job()

        val errorHandler = CoroutineExceptionHandler { _, _ ->
            fetchData0()
        }

        val scope = CoroutineScope(fetchDataAll + Dispatchers.Main)

        scope.launch (errorHandler){
            val allData = reInstance.getData()
            renderAllData(allData)
        }
    }

    private fun fetchData0() {
        val fetchDataAll = Job()

        val errorHandler = CoroutineExceptionHandler { _, _ ->
            showErr()
        }

        val scope = CoroutineScope(fetchDataAll + Dispatchers.Main)

        scope.launch (errorHandler){
            val allData = reInstance.getData()
            renderAllData(allData)
        }
    }

    private fun renderAllData(allData: List<DataClass>) {
        recyclerView.adapter = DataAdapter(allData, this)
        prog.visibility = View.GONE
    }

    private fun initRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(position: Int, name: String) {
       val intent = Intent(this@MainActivity, InfoMore::class.java)
        intent.putExtra("pos", position)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder
            .setMessage(getString(R.string.sure_to_exit))
            .setCancelable(true)
            .setPositiveButton(
                getString(R.string.no)
            ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            .setNegativeButton(
                getString(R.string.yes_exit)
            ) { _: DialogInterface?, _: Int -> finishAffinity() }
        dialog = alertDialogBuilder.create()
        dialog!!.show()
    }

    private fun showErr(){
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
            initRecycler()
            fetchData()
        }
    }
}
