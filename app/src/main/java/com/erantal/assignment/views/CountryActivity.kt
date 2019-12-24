package com.erantal.assignment.views

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erantal.assignment.CountriesApplication
import com.erantal.assignment.R
import com.erantal.assignment.adapters.CountriesAdapter
import com.erantal.assignment.models.RestCountry
import com.erantal.assignment.views.CountryViewHolder.Companion.COUNTRY_KEY
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.country_page.*


class CountryActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_page)
        val country : RestCountry = intent.getSerializableExtra(COUNTRY_KEY) as RestCountry
        val countries = ArrayList<RestCountry>()
        country_name.text = country.name
        loadFlag(country.flag)

        country.borders?.forEach { border ->
            val countryName = (application as CountriesApplication).countriesMap[border]?.first
            val countryNativeName = (application as CountriesApplication).countriesMap[border]?.second
            val borderCountry = RestCountry()
            borderCountry.name = countryName
            borderCountry.nativeName = countryNativeName
            countries.add(borderCountry)

        }
        adapter = CountriesAdapter(countries, false)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(baseContext, R.drawable.horizontal_divider)!!)
        recyclerView.addItemDecoration(divider)

    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }

    private fun loadFlag(url: String?) {
        GlideToVectorYou
            .init()
            .with(this)
            .load(Uri.parse(url), countryFlag)
    }

}