package com.example.espaceetudiant;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MProcessForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MProcessForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MProcessForm extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "formJsonString";
    private static final String ARG_PARAM2 = "pro_uid";
    private static final String ARG_PARAM3 = "tas_uid";
    private static final String ARG_PARAM4 = "token";
    private static final String ARG_PARAM5 = "pro_title";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String pro_uid;
    private String tas_uid;
    private String pro_title;

    private String token;
    private JSONArray dynaformObject;
    private JSONObject apiPostFormObject;
    private OnFragmentInteractionListener mListener;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://process.isiforge.tn/")
            .addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit= builder.build();
    LoginService userClient= retrofit.create(LoginService.class);


    public MProcessForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MProcessForm.
     */
    // TODO: Rename and change types and number of parameters
    public static MProcessForm newInstance(String param1, String param2) {
        MProcessForm fragment = new MProcessForm();
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
            pro_uid = getArguments().getString(ARG_PARAM2);
            tas_uid = getArguments().getString(ARG_PARAM3);
            token = getArguments().getString(ARG_PARAM4);
            pro_title=getArguments().getString(ARG_PARAM5);
            System.out.println(token);

            try {
                JSONObject paramObject = new JSONObject(mParam1);
                JSONObject dyn_content = new JSONObject(paramObject.getString("dyn_content"));
                dynaformObject = dyn_content.getJSONArray("items").getJSONObject(0).getJSONArray("items");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final LinearLayout myLayout = new LinearLayout(getActivity());
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.setPadding(48, 32, 48, 0);
        apiPostFormObject = new JSONObject();
        for (int i = 0; i < dynaformObject.length(); i++) {
            try {
                JSONArray item = dynaformObject.getJSONArray(i);
                for (int j = 0; j < item.length(); j++) {
                    JSONObject formItem = item.getJSONObject(j);

                    try {
                        String type = formItem.getString("type");
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 32, 0, 0);
                        switch (type) {
                            case "title":
                                TextView title = new TextView(getActivity());
                                System.out.println(formItem.getString("label"));
                                String test = formItem.getString("label");
                                String t = "Etudiant";

                                if (test.equals(t)){
                                    System.out.println("Im heree");
                                    TextView title1 = new TextView(getActivity());
                                    title1.setText("Deposer une demande de double correction");
                                    TextViewCompat.setTextAppearance(title1,R.style.TextAppearance_MaterialComponents_Headline4);
                                    title1.setPadding(0, 40, 0, 32);
                                    myLayout.addView(title1);
                                }
                                title.setText(formItem.getString("label"));
                                TextViewCompat.setTextAppearance(title,R.style.TextAppearance_MaterialComponents_Headline4);

                              //  title.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline5);
                                title.setPadding(0, 32, 0, 32);
                                myLayout.addView(title);
                                break;

                            case "text":

                                TextInputLayout inputLayout = new TextInputLayout(getActivity(), null, R.attr.myInputStyle);
                                inputLayout.setLayoutParams(params);
                                TextInputEditText editText = new TextInputEditText(getActivity());
                                editText.setHint(formItem.getString("label"));
                                editText.setTag(formItem.getString("variable"));
                                apiPostFormObject.put(formItem.getString("variable"), "");
                                inputLayout.addView(editText);
                                myLayout.addView(inputLayout);
                                break;

                            case "radio" :
                                TextView txtRadio = new  TextView(getActivity());
                                txtRadio.setText(formItem.getString("label"));
                                txtRadio.setTextColor(Color.parseColor("#000000"));
                                txtRadio.setTextSize(20);

                                RadioGroup radioGroup = new RadioGroup(getActivity(), null);
                                JSONArray options = new JSONArray(formItem.getString("options"));
                                for(int c =0;c<options.length();c++){
                                    RadioButton radioBtn = new RadioButton(getActivity(), null);
                                    //System.out.println("Label "+i+ ": "options.getJSONObject(i).optString("label"));
                                    radioBtn.setText(options.getJSONObject(c).optString("label"));
                                    radioBtn.setTextSize(20);
                                    radioGroup.addView(radioBtn);
                                }
                                myLayout.addView(txtRadio);
                                myLayout.addView(radioGroup);
                                break;

                            case "subtitle" :
                                TextView subtitle = new  TextView(getActivity(), null);
                                subtitle.setText(formItem.getString("label"));
                                subtitle.setTextColor(Color.parseColor("#1313D2"));
                                subtitle.setTextSize(20);

                                myLayout.addView(subtitle);
                                break;

                            case "submit":
                                params.setMargins(0, 32, 0, 0);
                                MaterialButton button = new MaterialButton(getActivity());
                                button.setText(formItem.getString("label"));
                                button.setLayoutParams(params);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Iterator<String> temp = apiPostFormObject.keys();
                                        while (temp.hasNext()) {
                                            String key = temp.next();
                                            try {
                                                TextInputEditText edit = (TextInputEditText) myLayout.findViewWithTag(key);
                                                apiPostFormObject.put(key, edit.getText().toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        JSONArray variables = new JSONArray();
                                        variables.put(apiPostFormObject);


                                        NewProcess body = null;
                                        try {
                                            body = new NewProcess(pro_uid, tas_uid, variables.getJSONObject(0));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                         System.out.println(body.getVariables());
                                        Call<NewProcessRep> call = userClient.postCase("Bearer "+token, body);
                                        call.enqueue(new Callback<NewProcessRep>() {
                                            @Override
                                            public void onResponse(Call<NewProcessRep> call, Response<NewProcessRep> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Votre demande est evoyée avec succée", Toast.LENGTH_LONG).show();

                                                    MfragmentlistprocessFragment fragment =new MfragmentlistprocessFragment();

                                                    FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
                                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
                                                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                                                    fragmentTransaction.commit();
                                                    System.out.println("okayyyyyyyyyyyyyyyyyyyyyyyyyy"+response.toString());

                                                } else {
                                                    System.out.println(" not okayyyyyyyyyyyyyyyyyyyyyyyyyy"+response.toString());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<NewProcessRep> call, Throwable t) {

                                                System.out.println(t.toString());
                                            }
                                        });
                                    }
                                });



                                myLayout.addView(button);
                                break;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ScrollView scrollView = new ScrollView(getContext() );
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myLayout.setLayoutParams(layoutParams);
        scrollView.addView(myLayout);
        return scrollView;
        /*final LinearLayout myLayout = new LinearLayout(getActivity());
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.setPadding(48, 32, 48, 0);
        apiPostFormObject = new JSONObject();
        // Inflate the layout for this fragment
        for(int i=0; i<dynaformObject.length(); i++){
            try {
                JSONArray item = dynaformObject.getJSONArray(i);
                for(int j=0; j<item.length(); j++){
                    JSONObject formItem = item.getJSONObject(j);
                    try {
                        String type = formItem.getString("type");
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 32, 0,0);
                        switch (type) {
                            case "title":
                                TextView title = new TextView(getActivity());
                                title.setText(formItem.getString("label"));
                                title.setPadding(0, 32, 0, 32);
                                myLayout.addView(title);
                                break;
                            case "text":
                                TextInputLayout inputLayout = new TextInputLayout(getActivity(), null);
                                inputLayout.setLayoutParams(params);
                                TextInputEditText editText = new TextInputEditText(getActivity());
                                editText.setHint(formItem.getString("label"));
                                editText.setTag(formItem.getString("variable"));
                                apiPostFormObject.put(formItem.getString("variable"), "");
                                inputLayout.addView(editText);
                                myLayout.addView(inputLayout);
                                break;
                            case "submit":
                                params.setMargins(0, 32, 0,0);
                                Button button = new Button(getActivity());
                                button.setText(formItem.getString("label"));
                                button.setLayoutParams(params);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Iterator<String> temp = apiPostFormObject.keys();
                                        while (temp.hasNext()) {
                                            String key = temp.next();
                                            try {
                                                TextInputEditText edit = (TextInputEditText) myLayout.findViewWithTag(key);
                                                apiPostFormObject.put(key, edit.getText().toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        AsyncHttpClient client = new AsyncHttpClient();
                                        client.addHeader("Authorization", "Bearer "+token);
                                        String api_endpoit = "/cases";
                                        JSONArray variables = new JSONArray();
                                        variables.put(apiPostFormObject);
                                        String body = "{\"pro_uid\":\""+pro_uid+"\",\n" +
                                                "\"tas_uid\":\""+tas_uid+"\",\n"+
                                                "\"variables\":"+variables.toString()+"\n"+
                                                "}";

                                        Log.d("body", body);


                                    }
                                });
                                myLayout.addView(button);
                                break;
                        }
                    }catch(JSONException e){
                        System.out.println("No type field");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ScrollView scrollView = new ScrollView(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myLayout.setLayoutParams(layoutParams);
        scrollView.addView(myLayout);
        return scrollView;
            //return inflater.inflate(R.layout.fragment_mprocess_form, container, false);*/
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
