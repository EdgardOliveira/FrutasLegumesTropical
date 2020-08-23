package br.com.technologies.venom.frutastropicais;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> implements Filterable {

    private List<Result> resultList;
    private List<Result> resultListFiltered;
    private Context context;

    public void setFrutaList(Context context, final List<Result> resultList) {
        this.context = context;
        if (this.resultList == null) {
            this.resultList = resultList;
            this.resultListFiltered = resultList;
            notifyItemChanged(0, resultListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ResultAdapter.this.resultList.size();
                }

                @Override
                public int getNewListSize() {
                    return resultList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ResultAdapter.this.resultList.get(oldItemPosition).getTfvname() == resultList.get(newItemPosition).getTfvname();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Result newResult = ResultAdapter.this.resultList.get(oldItemPosition);

                    Result oldResult = resultList.get(newItemPosition);

                    return newResult.getTfvname() == oldResult.getTfvname();
                }
            });
            this.resultList = resultList;
            this.resultListFiltered = resultList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_frutas_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultAdapter.MyViewHolder holder, int position) {
        holder.tvNome.setText(resultListFiltered.get(position).getTfvname());
        Glide.with(context).load(resultList.get(position).getImageurl()).apply(RequestOptions.centerCropTransform()).into(holder.ivFruta);
    }

    @Override
    public int getItemCount() {

        if (resultList != null) {
            return resultListFiltered.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    resultListFiltered = resultList;
                } else {
                    List<Result> filteredList = new ArrayList<>();
                    for (Result result : resultList) {
                        if (result.getTfvname().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(result);
                        }
                    }
                    resultListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = resultListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                resultListFiltered = (ArrayList<Result>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Result> getFilteredList(){
        return resultListFiltered;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNome;
        ImageView ivFruta;

        public MyViewHolder(View view) {
            super(view);
            tvNome = view.findViewById(R.id.tvCVFrutaNome);
            ivFruta = view.findViewById(R.id.ivCVFruta);
        }
    }
}