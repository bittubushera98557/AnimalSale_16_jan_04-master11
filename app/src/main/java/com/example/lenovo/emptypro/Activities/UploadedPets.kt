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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_uploaded_pets.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.home_searched_item.view.*
import kotlinx.android.synthetic.main.item_top_cate.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadedPets : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.img_back->
            {
                onBackPressed()
            }

        }
    }

    private var allUploadedPetsLst: MutableList<AllApiResponse.UploadedPetsRes.UploadedPetsItem>? = null
//    var ctx: Context?=null
    internal var service: GetDataService?=null
    var  TAG ="UploadedPets "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploaded_pets)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        allUploadedPetsLst = mutableListOf()
        img_back.setOnClickListener(this)
        tv_title.text = "Uploaded Pets"

        rv_uploadedPets.setHasFixedSize(true);
        rv_uploadedPets.layoutManager = GridLayoutManager(this@UploadedPets, 2)

        rv_uploadedPets.setUp(allUploadedPetsLst!!, R.layout.home_searched_item, { it1 ->
            val circularProgressDrawable = CircularProgressDrawable(this@UploadedPets)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Log.e(TAG+"","UploadedPet   name="+it1.petName + "  "+ it1.category)
           //   Picasso.with(context).load(""+it1.images[0].img).placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_mainCatImg)

            tv_searchedItemShortInfo.text = "Rs. "+""+it1.petPrice+"\n"+it1.petName+"\n"+it1.category

            if ( ll_searchedItem!= null) {
                val display = (this@UploadedPets).windowManager.defaultDisplay
                ll_searchedItem.getLayoutParams().width= Math.round((display.width / 2).toFloat())
            }
            ll_searchedItem.setOnClickListener {
                //        Utilities.enterNextReplaceFragment (R.id.ll_homeFragment, SubCategory.newInstance(""+it1.data[0].petID ,""),(ctx as MainActivity).supportFragmentManager)
                Toast.makeText(this@UploadedPets,"", Toast.LENGTH_LONG).show()

            }
        }, { view1: View, i: Int -> })
        rv_uploadedPets.layoutManager = GridLayoutManager(this@UploadedPets, 2)
        if (GlobalData.isConnectedToInternet(this@UploadedPets)) {
            callAllUploadedPetsApi()
        } else {
            GlobalData.showSnackbar(this@UploadedPets, getString(R.string.please_check_your_internet_connection))
        }
    }

    private fun callAllUploadedPetsApi() {


        val call = service!!.getUploadedPetsApi(/*SharedPrefUtil.getUserId(this@UploadedPets)*/"2")

        call.enqueue(object : Callback<AllApiResponse.UploadedPetsRes> {
            override fun onResponse(call: Call<AllApiResponse.UploadedPetsRes>, response: Response<AllApiResponse.UploadedPetsRes>) {
                Log.e(TAG + " UploadedPetsApi", "response   $response")
                if(response.body()!!.status.equals("200")) {
                    allUploadedPetsLst!!.clear()
                    allUploadedPetsLst!!.addAll(response.body()!!.data)
                    Log.e(TAG + " UploadedPetsApi", "size=" + response.body()!!.data.size)

                    rv_uploadedPets.adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<AllApiResponse.UploadedPetsRes>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(this@UploadedPets, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }
    override fun onBackPressed() {
        val intent = Intent(this@UploadedPets, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
