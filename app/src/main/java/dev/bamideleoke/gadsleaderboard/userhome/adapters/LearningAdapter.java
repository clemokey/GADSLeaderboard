package dev.bamideleoke.gadsleaderboard.userhome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import dev.bamideleoke.gadsleaderboard.R;
import dev.bamideleoke.gadsleaderboard.userhome.objects.LearningEntry;

public class LearningAdapter extends RecyclerView.Adapter<LearningAdapter.CustomViewHolder> {

    private List<LearningEntry> dataList;
    private Context context;

    public LearningAdapter(Context context, List<LearningEntry> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView personName, details;
        private ImageView badge;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            personName = mView.findViewById(R.id.person_name);
            details = mView.findViewById(R.id.score_details);
            badge = mView.findViewById(R.id.itemImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.personName.setText(dataList.get(position).getName());
        holder.details.setText(context.getResources().getString(
                R.string.details,
                dataList.get(position).getHours(),
                dataList.get(position).getCountry()
        ));

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getBadgeUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.badge);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}