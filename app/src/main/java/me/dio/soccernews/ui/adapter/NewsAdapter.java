package me.dio.soccernews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.dio.soccernews.R;
import me.dio.soccernews.databinding.NewsItemBinding;
import me.dio.soccernews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> news;
    private final NewsAdapter.favoriteListener favoriteListener;

    public NewsAdapter(List<News> news, NewsAdapter.favoriteListener favoriteListener){
        this.news = news;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Context context = holder.itemView.getContext();

        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvDescription.setText(news.description);

        Picasso.get()
                .load(news.image)
                .fit()
                .into(holder.binding.ivThumbnail);

        // Implementação da funcionalidade de abrir Link
        holder.binding.btOpenLink.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.link));
            context.startActivity(i);

        });
        // Implementação da funcionalidade de Compartilhar
        holder.binding.btShare.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, news.link);
            i.putExtra(Intent.EXTRA_TEXT, news.link);
            context.startActivity(Intent.createChooser(i,"share via"));
        });
        // Implementação da funcionalidade de Favoritar
        holder.binding.btFavorite.setOnClickListener(view -> {
            news.favorite = !news.favorite;
            this.favoriteListener.onFavorite(news );
            notifyItemChanged(position);

        });
        int favoriteCollor = news.favorite ? R.color.favorite_active : R.color.favorite_inactive;
        holder.binding.btFavorite.setColorFilter(context.getColor(favoriteCollor));



    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding){
            super((binding.getRoot()));
            this.binding = binding;
        }


    }

    public interface favoriteListener {
        void onFavorite(News news);
    }
}
