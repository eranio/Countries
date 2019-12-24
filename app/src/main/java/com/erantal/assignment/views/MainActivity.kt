package com.erantal.assignment.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erantal.assignment.CountriesApplication
import com.erantal.assignment.R
import com.erantal.assignment.RestService
import com.erantal.assignment.adapters.CountriesAdapter
import com.erantal.assignment.models.RestCountry
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(RestService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RestService::class.java)
        val call = service.getAllCountries()
        handleResponse(call)
        initRecyclerView()
    }

    private fun initSortOptions() {
        var sortOptions = resources.getStringArray(R.array.sort_options)
        val sortAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortOptions)
        sortAdapter.setDropDownViewResource(R.layout.dropdown_item_layout)
        sort_spinner.adapter = sortAdapter
        sort_spinner.setSelection(0)
        sort_spinner.onItemSelectedListener = this
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(baseContext, R.drawable.horizontal_divider)!!)
        recyclerView.addItemDecoration(divider)
    }

    private fun handleResponse(call: Call<ArrayList<RestCountry>>) {
        call.enqueue(object : Callback<ArrayList<RestCountry>> {
            override fun onResponse(call: Call<ArrayList<RestCountry>>, response: Response<ArrayList<RestCountry>>) {
                if (response.code() == 200) {
                    val countriesList = response.body() ?: ArrayList()
                    countriesList.forEach{ country ->
                        country.alpha3Code?.let {
                            val countryPair = Pair(country.name ?: "", country.nativeName ?: "")

                            (application as CountriesApplication).countriesMap.put(it, countryPair)
                        }
                    }
                    adapter = CountriesAdapter(countriesList, true)
                    initSortOptions()
                    recyclerView.adapter = adapter
                }
            }
            override fun onFailure(call: Call<ArrayList<RestCountry>>, t: Throwable) {
                Log.d("FAIL","Failed")
            }
        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, option: Int, id: Long) {
        var sortType = when (option) {
            0 -> CountriesAdapter.SortType.NAME_ASC
            1 -> CountriesAdapter.SortType.NAME_DESC
            2 -> CountriesAdapter.SortType.AREA_ASC
            3 -> CountriesAdapter.SortType.AREA_DESC
            else -> CountriesAdapter.SortType.NAME_ASC
        }

        adapter.sort(sortType)
    }
}
