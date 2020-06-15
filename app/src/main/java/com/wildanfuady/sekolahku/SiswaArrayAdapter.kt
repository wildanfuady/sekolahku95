package com.wildanfuady.sekolahku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.wildanfuady.sekolahku.model.Siswa

class SiswaArrayAdapter(context: Context): ArrayAdapter<Siswa>(
    context, R.layout.siswa_array_adapter) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView

        if(view == null){
            view = LayoutInflater.from(context).inflate(
                R.layout.siswa_array_adapter, null
            )
        }

        var textNama = view!!.findViewById<TextView>(R.id.rowNama)
        var textGender = view!!.findViewById<TextView>(R.id.rowGender)
        var textJenjang = view!!.findViewById<TextView>(R.id.rowJenjang)
        var textNoHp = view!!.findViewById<TextView>(R.id.rowNoHp)

        var siswa = getItem(position)

        textNama.setText(siswa?.namad + " " + siswa?.namab)
        textGender.setText(siswa?.gender)
        textJenjang.setText(siswa?.jenjang)
        textNoHp.setText(siswa?.no_hp)

        return view

    }

}