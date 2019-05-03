package com.example.espaceetudiant;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mListView;
     View view;

    private RecyclerView mRecyclerView;
    private processViewAdapter mExampleAdapter;
    private List<Process> mprocList;
    // private RequestQueue mRequestQueue;

    String API_BASE_URL = "http://process.isiforge.tn/";

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit= builder.build();

    LoginService client = retrofit.create(LoginService.class);


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
       view= inflater.inflate(R.layout.fragment_blank, container, false);



        Call<List<Process>> call= client.getProcess("Bearer 29cab2eb3ddb7ed23aec8520777c51eda09f342d");
        call.enqueue(new Callback<List<Process>>() {
            @Override
            public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                if(response.isSuccessful()){
                    List<Process> mprocList = response.body();
                    //Creating an String array for the ListView

                    Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
                    mRecyclerView= view.findViewById(R.id.Rv_proc);
                    mExampleAdapter = new processViewAdapter(getContext(),mprocList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mExampleAdapter);

                    //check
                    Toast.makeText(getContext(), mprocList.get(0).getPro_title(), Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getContext(), "Token is not correct", Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onFailure(Call<List<Process>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                // TODO: handle error
            }
        });

        // Inflate the layout for this fragment
        return view ;




    }




}
