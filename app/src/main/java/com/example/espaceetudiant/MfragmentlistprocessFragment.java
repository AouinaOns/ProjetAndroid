package com.example.espaceetudiant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MfragmentlistprocessFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private String token;




    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MfragmentlistprocessFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MfragmentlistprocessFragment newInstance(int columnCount) {
        MfragmentlistprocessFragment fragment = new MfragmentlistprocessFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences Tpref =getContext() .getSharedPreferences("TokenPref", 0); // 0 - for private mode
        token = Tpref.getString("access_token", "");

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mfragmentlistprocess_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            final MyMfragmentlistprocessRecyclerViewAdapter adapter = new MyMfragmentlistprocessRecyclerViewAdapter(new ArrayList<Process>(), mListener);
            recyclerView.setAdapter(adapter);
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(getString(R.string.API_BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit= builder.build();
            LoginService client = retrofit.create(LoginService.class);
            Call<List<Process>> call= client.getProcess("Bearer "+token);
            call.enqueue(new Callback<List<Process>>() {
                @Override
                public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                    if(response.isSuccessful()){
                        List<Process> mprocList = response.body();
                        //Creating an String array for the ListView
                        Toast.makeText(getActivity(), "Im here", Toast.LENGTH_LONG).show();
                        adapter.updateList(mprocList);
                        adapter.notifyDataSetChanged();

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
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Process item);
    }
}
