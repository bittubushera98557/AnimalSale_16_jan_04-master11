package com.example.lenovo.emptypro.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utils.GlobalData
import com.iww.classifiedolx.recyclerview.setUp
import kotlinx.android.synthetic.main.activity_sold_pets.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.home_searched_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SoldPets : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.img_back->
            {
                onBackPressed()
            }

        }
    }

    private var allSoldPetsLst: MutableList<AllApiResponse.SoldPetsRes.SoldItemModel>? = null
    //var ctx: Context?=null
    internal var service: GetDataService?=null
    var  TAG ="SoldPets "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sold_pets)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        allSoldPetsLst = mutableListOf()
        img_back.setOnClickListener(this)
        tv_title.text = "Sold Pets"

        rv_soldPets.setHasFixedSize(true);
        rv_soldPets.layoutManager = GridLayoutManager(this@SoldPets, 2)

        rv_soldPets.setUp(allSoldPetsLst!!, R.layout.home_searched_item, { it1 ->
            val circularProgressDrawable = CircularProgressDrawable(this@SoldPets)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Log.e(TAG+"","sold  name="+it1.petName+ "  "+ it1.category)
         //   Picasso.with(context).load(""+it1.imgUrl).placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_mainCatImg)

             tv_searchedItemShortInfo.text = "Rs. "+""+it1.petPrice+"\n"+it1.petName+"\n"+it1.category

            if ( ll_searchedItem!= null) {
                val display = (this@SoldPets).windowManager.defaultDisplay
                ll_searchedItem.getLayoutParams().width= Math.round((display.width / 2).toFloat())
            }
            ll_searchedItem.setOnClickListener {
        //        Utilities.enterNextReplaceFragment (R.id.ll_homeFragment, SubCategory.newInstance(""+it1.data[0].petID ,""),(ctx as MainActivity).supportFragmentManager)
          Toast.makeText(this@SoldPets,"",Toast.LENGTH_LONG).show()

            }
        }, { view1: View, i: Int -> })
        rv_soldPets.layoutManager = GridLayoutManager(this@SoldPets, 2)
        if (GlobalData.isConnectedToInternet(this@SoldPets)) {
            callAllSoldPetsApi()
        } else {
            GlobalData.showSnackbar(this@SoldPets, getString(R.string.please_check_your_internet_connection))
        }
    }

    private fun callAllSoldPetsApi() {


        val call = service!!.getSoldPetsApi("2"/*SharedPrefUtil.getUserId(this@SoldPets)*/)

        call.enqueue(object : Callback<AllApiResponse.SoldPetsRes> {
            override fun onResponse(call: Call<AllApiResponse.SoldPetsRes>, response: Response<AllApiResponse.SoldPetsRes>) {
                Log.e(TAG + " SoldPets", "response   $response")
                if(response.body()!!.status.equals("200")) {
                    allSoldPetsLst!!.clear()
                    allSoldPetsLst!!.addAll(response.body()!!.data)
                    Log.e(TAG + " SoldPets", "size=" + response.body()!!.data.size)

                    rv_soldPets.adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<AllApiResponse.SoldPetsRes>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(this@SoldPets, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }
    override fun onBackPressed() {
        val intent = Intent(this@SoldPets, MainActivity::class.java)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent)
        finish()
    }
}
