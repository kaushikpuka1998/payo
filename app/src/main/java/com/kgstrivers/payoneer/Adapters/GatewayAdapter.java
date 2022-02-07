package com.kgstrivers.payoneer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kgstrivers.payoneer.Activities.SecondPageActivity;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GatewayAdapter extends RecyclerView.Adapter<GatewayAdapter.MyViewHolder> {

    Context context;
    List<Applicable> listapplicable;
    public GatewayAdapter(Context contxt,List<Applicable> applicables)
    {
        context = contxt;
        this.listapplicable = applicables;
    }
    @NonNull
    @Override
    public GatewayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.singlegateway,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GatewayAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.gatewayname.setText(listapplicable.get(position).getLabel());
        Picasso.get().load(listapplicable.get(position).getLinks().getLogo()).into(holder.imageview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,SecondPageActivity.class);
                i.putExtra("Applicable",listapplicable.get(position));
                i.putExtra("id",position);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
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
