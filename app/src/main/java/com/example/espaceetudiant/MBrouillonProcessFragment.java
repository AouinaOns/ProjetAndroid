package com.example.espaceetudiant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.espaceetudiant.dummy.DummyContent;
import com.example.espaceetudiant.dummy.DummyContent.DummyItem;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MBrouillonProcessFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    String token;
    private List<DraftProcessItem> list = new ArrayList<>();

    String refresh_token;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://process.isiforge.tn/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit= builder.build();
    LoginService loginService= retrofit.create(LoginService.class);
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MBrouillonProcessFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MBrouillonProcessFragment newInstance(int columnCount) {
        MBrouillonProcessFragment fragment = new MBrouillonProcessFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        SharedPreferences pref = getContext().getSharedPreferences("TokenPref", 0); // 0 - for private mode
        token = pref.getString("access_token", "");
        refresh_token = pref.getString("refresh_token", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mbrouillonprocess_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            final MyMBrouillonProcessRecyclerViewAdapter adapter = new MyMBrouillonProcessRecyclerViewAdapter(new ArrayList<DraftProcessItem>(), mListener);
            recyclerView.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    new LinearLayoutManager(context).getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            Call<List<DraftProcessItem>> cal = loginService.getDraft("Bearer "+token);
            cal.enqueue(new Callback<List<DraftProcessItem>>() {
                @Override
                public void onResponse(Call<List<DraftProcessItem>> call, Response<List<DraftProcessItem>> response) {
                    Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                    if( response.body() != null) {
                        for (int i = 0; i < response.body().size(); i++)
                            list.add(response.body().get(i));

                        System.out.println(list.get(0).app_pro_title);
                        adapter.updateItems(list);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<DraftProcessItem>> call, Throwable t) {
                    Toast.makeText(getContext(), "scss", Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));

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
        void onListFragmentInteraction(DraftProcessItem item);
    }
}
