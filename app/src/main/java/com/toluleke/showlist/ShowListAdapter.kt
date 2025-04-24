package com.toluleke.showlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toluleke.common.response.CountryDetails


/**
 * A RecyclerView.Adapter to populate the screen with a store feed.
 */
class ShowListAdapter: RecyclerView.Adapter<CountriesViewHolder>() {
    var items: List<CountryDetails> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        return CountriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val (capital, code, _, _, _, name, region) = items[position]

        with(holder.itemView) {
            findViewById<TextView>(R.id.name).text =  "$name, $region"
            findViewById<TextView>(R.id.code).text = code
            findViewById<TextView>(R.id.capital).text = capital
//            findViewById<TextView>(R.id.region).text = region
        }
    }

    override fun getItemCount() =  items.size
}

/**
 * Holds the view for the Store item.
 */
class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)