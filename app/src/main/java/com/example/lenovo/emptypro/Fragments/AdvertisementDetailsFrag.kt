package com.example.lenovo.emptypro.Fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.glide.slider.library.SliderTypes.TextSliderView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_advertisement_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdvertisementDetailsFrag : Fragment(), View.OnClickListener, OnMapReadyCallback {
    override fun onMapReady(googleMap: GoogleMap?) {

        googleMap!!.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        gMap=googleMap
    }

    override fun onClick(v: View?) {

    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var adsDataModel: AllApiResponse.AllTypePetsRes.AllTypePetsItem? = null
    var ctx: Context? = null
    var lati="0"
    var longi="0"

    var gMap:GoogleMap?=null
    var TAG="AdvertisementDetailsFrag "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_call.setOnClickListener(this)
        fl_AdvertisementDetailsFrag.setOnClickListener(this)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

      /*  val url_maps = HashMap<String, String>()
        if ( adsDataModel!!.images.equals("")) {
            url_maps.put("Hannibal1", IMAGE_Ad_Images + fullDetailsModel.imageUpload1)
        }
        if (!fullDetailsModel.imageUpload2.equals("")) {
            url_maps.put("Hannibal2", IMAGE_Ad_Images + fullDetailsModel.imageUpload2)
        }
        if (!fullDetailsModel.imageUpload3.equals("")) {
            url_maps.put("Hannibal3", IMAGE_Ad_Images + fullDetailsModel.imageUpload3)
        }
        if (!fullDetailsModel.imageUpload4.equals("")) {
            url_maps.put("Hannibal4", IMAGE_Ad_Images + fullDetailsModel.imageUpload4)
        }*/

        for (item in  adsDataModel!!.images) {
            val sliderView = TextSliderView(ctx as Activity)
            sliderView.image(item.img).setProgressBarVisible(true)
            slider.addSlider(sliderView)
        }
        tv_advertiseLoc.text = "pets Location "//adsDataModel!!.petTitle+ ", " + adsDataModel.cityName
        tv_advertiseDate.text = "12-06-19" // fullDetailsModel.date
        tv_title.text = adsDataModel!!.petTitle
        tv_advertiseDesc.text = adsDataModel!!.petDescription


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advertisement_details, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AllApiResponse.AllTypePetsRes.AllTypePetsItem, param2: String) =
                AdvertisementDetailsFrag().apply {
                    arguments = Bundle().apply {
                        adsDataModel=param1
                        //                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
