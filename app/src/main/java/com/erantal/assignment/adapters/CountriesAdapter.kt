package com.erantal.assignment.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erantal.assignment.R
import com.erantal.assignment.inflate
import com.erantal.assignment.models.RestCountry
import com.erantal.assignment.views.CountryViewHolder

class CountriesAdapter(private val countries: ArrayList<RestCountry>, private val clickAllowed: Boolean) : RecyclerView.Adapter<CountryViewHolder>() {

    enum class SortType {
        NAME_ASC, NAME_DESC, AREA_ASC, AREA_DESC
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val inflatedView = parent.inflate(R.layout.country_item_row, false)
        return CountryViewHolder(inflatedView, clickAllowed)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bindCountry(country)
    }

    fun sort(sortType: SortType) {
        when (sortType) {
            SortType.NAME_ASC -> countries.sortBy { it.name }
            SortType.NAME_DESC -> countries.sortByDescending { it.name }
            SortType.AREA_ASC -> countries.sortBy { it.area }
            SortType.AREA_DESC -> countries.sortByDescending { it.area }
        }
        notifyDataSetChanged()

    }
}