package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shopkeeperhelper.R;

import java.util.ArrayList;

/**
 * Created by PSethi on 28-Dec-16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<String> list;
    public RecyclerAdapter(ArrayList<String> list)
    {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtName;
        public TextView txtBalance;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtBalance = (TextView) itemView.findViewById(R.id.txtBalance);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_template,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String[] listSplit = list.get(position).split(",");
        holder.txtName.setText(listSplit[0]);
        holder.txtBalance.setText(listSplit[3]);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
