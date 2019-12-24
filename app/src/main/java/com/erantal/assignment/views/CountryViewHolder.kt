package com.erantal.assignment.views

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.erantal.assignment.models.RestCountry
import kotlinx.android.synthetic.main.country_item_row.view.*


class CountryViewHolder(v: View, clickAllowed: Boolean) : RecyclerView.ViewHolder(v), View.OnClickListener {

    companion object {
        //5
        const val COUNTRY_KEY = "COUNTRY"
    }
    private var view: View = v
    private var country: RestCountry? = null

    init {
        if (clickAllowed) {
            view.setOnClickListener(this)
        }
    }

    override fun onClick(clickedView: View?) {
        val context = itemView.context
        val countryIntent = Intent(context, CountryActivity::class.java)
        countryIntent.putExtra(COUNTRY_KEY, country)
        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            context as Activity,
            view.country_row,
            "countryName"
        )
        context.startActivity(countryIntent, options.toBundle())
    }

    fun bindCountry(country: RestCountry) {
        this.country = country
        view.native_country_name.text = country.nativeName
        view.english_country_name.text = country.name
    }
}