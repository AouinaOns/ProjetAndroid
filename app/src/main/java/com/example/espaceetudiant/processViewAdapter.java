package com.example.espaceetudiant;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class processViewAdapter  extends RecyclerView.Adapter <processViewAdapter.processViewHolder>{
    private Context mContext;
    private List<Process> mExampleList;

    public processViewAdapter(Context mContext, List<Process> mExampleList) {
        this.mContext = mContext;
        this.mExampleList = mExampleList;
    }

    public class processViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewCreator;
        public processViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewCreator = itemView.findViewById(R.id.proc_name);
        }
    }

    @NonNull
    @Override
    public processViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.process_item, viewGroup, false);
        return new processViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull processViewHolder processViewHolder, int i) {
        Process currentProc = mExampleList.get(i);
        String ProcName = currentProc.getPro_title();
        processViewHolder.mTextViewCreator.setText(ProcName);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}
