package com.wildanfuady.sekolahku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    // membuat adapter baru
    lateinit var adapter : SiswaArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        /*

        var siswaDataSource = SiswaDataSource(this)
        var allSiswa = siswaDataSource.getAll()

        var listNamaLengkap = ArrayList<String>()

        for(index in allSiswa.indices){
            listNamaLengkap.add(allSiswa.get(index).namad + " " + allSiswa.get(index).namab)
        }

        // masukan list data siswa ke dalam adapter list view
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            listNamaLengkap)
        // list_view dari activity_list.xml
        list_view.adapter = adapter

        */

        toFormActivity.setOnClickListener {
            var intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {

        var siswaDataSource = SiswaDataSource(this)
        var allSiswa = siswaDataSource.getAll()

        adapter = SiswaArrayAdapter(this)
        adapter.addAll(allSiswa)
        adapter.notifyDataSetChanged()
        list_view.adapter = adapter

        list_view.setOnItemClickListener(AdapterView.OnItemClickListener{
            parent, view, position, id->
            viewDetailSiswa(position)
        })

        search_view.setOnQueryTextListener(object : androidx.appcompat.widget.
        SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.clear()
                var siswaDataSource = SiswaDataSource(this@ListActivity)
                var siswa = siswaDataSource.search(newText!!)
                adapter.addAll(siswa)
                adapter.notifyDataSetChanged()
                return false
            }
        })

        registerForContextMenu(list_view)

        super.onResume()
    }

    fun viewDetailSiswa(position: Int){

        var intent = Intent(this, DetailActivity::class.java)
        var siswa = adapter.getItem(position)
        // memberikan parameter ke intent
        intent.putExtra("id", siswa?.id)
        startActivity(intent)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add){
            // go to page
            var intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        var position = menuInfo.position
        var siswa = adapter.getItem(position)

        if(item.itemId == R.id.context_edit)
        {
            // edit siswa
            var intent = Intent(this, FormActivity::class.java)
            intent.putExtra("id", siswa?.id)
            startActivity(intent)
        }

        if(item.itemId == R.id.context_delete)
        {
            // delete siswa
            var siswaDataSource = SiswaDataSource(this)
            siswaDataSource.deleteSiswa(siswa?.id)
            adapter.remove(siswa)
            adapter.notifyDataSetChanged()
        }

        return super.onContextItemSelected(item)
    }
}
