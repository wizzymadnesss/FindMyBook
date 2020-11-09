package com.example.findmybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private List<Books> mData;
    private LayoutInflater mInflater;
    private Context context;

    public BooksAdapter(List<Books> itemList,Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.inflate(R.layout.book_item,null);
        return new BooksAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Books> items){
        mData=items;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView title,author;

        ViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.booktitle);
            author=itemView.findViewById(R.id.bookAuthor);
           // iconImage= itemView.findViewById(R.id.bookImage);
        }

        void bindData(final Books item){
            title.setText(item.getTitle());
            author.setText(item.getAuthor());
        }

    }


}
