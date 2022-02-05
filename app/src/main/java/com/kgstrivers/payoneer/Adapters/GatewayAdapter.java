package com.kgstrivers.payoneer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class GatewayAdapter extends RecyclerView.Adapter<GatewayAdapter.MyViewHolder>{

    Context context;
    List<Applicable> listapplicable;
    public GatewayAdapter(Context contxt,List<Applicable> applicables)
    {
        context = contxt;
        this.listapplicable = applicables;
    }
    public void Search(String value)
    {
        for(int i=0;i<listapplicable.size();i++)
        {
            if(listapplicable.get(i).getLabel().toLowerCase(Locale.ROOT) == value)
            {
                listapplicable.add(listapplicable.get(i));
            }
        }
    }
    @NonNull
    @Override
    public GatewayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.singlegateway,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GatewayAdapter.MyViewHolder holder, int position) {


        holder.gatewayname.setText(listapplicable.get(position).getLabel());
        Picasso.get().load(listapplicable.get(position).getLinks().getLogo()).into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return listapplicable.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView gatewayname;
        ImageView imageview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gatewayname = itemView.findViewById(R.id.gatewayname);
            imageview = itemView.findViewById(R.id.gatewayimage);
        }


    }
}
