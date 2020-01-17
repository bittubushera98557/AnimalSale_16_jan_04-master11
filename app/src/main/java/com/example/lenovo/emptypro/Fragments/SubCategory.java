package com.example.lenovo.emptypro.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lenovo.emptypro.Adapters.MainCatAdapter;
import com.example.lenovo.emptypro.Adapters.SubCatAdapter;
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService;
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance;
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener;
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse.CategoryResponse;
import com.example.lenovo.emptypro.R;
import com.example.lenovo.emptypro.Utils.GlobalData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategory extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String TAG="SubCategory ";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
ImageView iv_back;
    GetDataService service;
    SubCatAdapter subCatAdapter;
    private List<CategoryResponse.CategoryMainListModel> subCatModel= new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public SubCategory() {
        // Required empty public constructor
    }
   // TODO: Rename and change types and number of parameters
    public static SubCategory newInstance(String param1, String param2) {
        SubCategory fragment = new SubCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initalView();
        if (GlobalData.isConnectedToInternet(getActivity())) {
     callSubCateApi();
        } else {
            GlobalData.showSnackbar(getActivity(), getString(R.string.please_check_your_internet_connection));

        }

      //  iv_back.setOnClickListener(this);

    }

    private void callSubCateApi() {
        subCatModel= new ArrayList<>();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<AllApiResponse.CategoryResponse> call = service.allCateListApi();

        call.enqueue(new Callback<AllApiResponse.CategoryResponse>() {
            @Override
            public void onResponse(Call<AllApiResponse.CategoryResponse> call, Response<AllApiResponse.CategoryResponse> response) {
                //   progress_bar.setVisibility(View.GONE);
                //  CharSequence result =new  Gson().toJson(response);
                subCatModel.addAll(response.body().getData());
                Log.e(TAG+"",""+  response.body().getData().size());
                subCatAdapter.addingData(subCatModel);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initalView() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {



        }
    }

}
