package com.softdesign.urbandictionary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softdesign.urbandictionary.activities.MainActivity;
import com.softdesign.urbandictionary.R;
import com.softdesign.urbandictionary.api.models.Word;


import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<Word> mWordList = new ArrayList<>();
    MainActivity mActivity;

    public WordAdapter(List<Word> wordList, MainActivity activity) {
        mWordList = wordList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wordListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wordListLayout.setLayoutParams(lp);
        WordViewHolder wordViewHolder = new WordViewHolder(wordListLayout);
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.term.setText(mWordList.get(position).getTerm());
        holder.definition.setText(mWordList.get(position).getDefinition());
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder{
        TextView term,definition,example;
        public WordViewHolder(View itemView) {
            super(itemView);
            term = itemView.findViewById(R.id.term_tv);
            definition = itemView.findViewById(R.id.def_tv);

        }
    }
}
