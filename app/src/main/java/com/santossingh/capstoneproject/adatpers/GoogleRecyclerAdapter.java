package com.santossingh.capstoneproject.adatpers;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.fragments.GoogleFragment;
import com.santossingh.capstoneproject.google.models.Item;
import com.santossingh.capstoneproject.utilities.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

public class GoogleRecyclerAdapter extends RecyclerView.Adapter<GoogleRecyclerAdapter.ViewHolder> {

    private final GoogleFragment.OnFragmentInteractionListener mListener;
    private List<Item> itemList = new ArrayList<>();
    private int preposition;

    public GoogleRecyclerAdapter(GoogleFragment.OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mData = itemList.get(position);
        holder.textView.setText(holder.mData.getVolumeInfo().getTitle() == null ? String.valueOf(R.string.Not_Available) : holder.mData.getVolumeInfo().getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentInteraction(holder.mData);
                }
            }
        });

        if (position > preposition) {
            AnimationUtil.animate(holder, true);
        } else {
            AnimationUtil.animate(holder, false);
        }
        preposition = position;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getBooksList() {
        return itemList;
    }

    public void addList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final TextView textView;
        public Item mData;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textView = (TextView) view.findViewById(R.id.book_Title);
        }
    }
}
