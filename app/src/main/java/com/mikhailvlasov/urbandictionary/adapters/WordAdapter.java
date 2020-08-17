package com.mikhailvlasov.urbandictionary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhailvlasov.urbandictionary.R;
import com.mikhailvlasov.urbandictionary.api.models.Word;


import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<Word> mWordList = new ArrayList<>();


    public WordAdapter(List<Word> wordList) {
        mWordList = wordList;

    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wordListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        WordViewHolder mWordViewHolder = new WordViewHolder(wordListLayout);
        return mWordViewHolder;
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
        TextView term,definition;
        public WordViewHolder(View itemView) {
            super(itemView);
            term = itemView.findViewById(R.id.term_tv);
            definition = itemView.findViewById(R.id.def_tv);

        }
    }
}
