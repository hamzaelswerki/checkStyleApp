package com.example.dalyelaltaleb.Adabter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dalyelaltaleb.Model.News;
import com.example.dalyelaltaleb.Model.TimeAgo2;
import com.example.dalyelaltaleb.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NewsAdapter extends FirestoreRecyclerAdapter<News, NewsAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private Context context;

   public interface OnItemClickListener {
      void onItemClicked(String filesUrl);
    }

   public  void setOnItemClickListener (OnItemClickListener onItemClickListener){
       this.onItemClickListener=onItemClickListener;
   }
    public NewsAdapter(@NonNull FirestoreRecyclerOptions<News> options,Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull final News model) {

       holder.bind(model);



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

          ImageView logoImg;
        SimpleDraweeView imgNews;
         TextView tvTitle;
         TextView tvTextNews;
         TextView tvDate;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            logoImg = itemView.findViewById(R.id.image_view_user);
            imgNews = itemView.findViewById(R.id.imageView5);
            tvTitle = itemView.findViewById(R.id.textView10);
            tvTextNews = itemView.findViewById(R.id.textView11);
            tvDate = itemView.findViewById(R.id.textView12);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim);
            itemView.startAnimation(animation);
        }
        void  bind(final News news){
            if (news.getTextNews()!=null &&!news.getTextNews().equals("")){
                tvTextNews.setVisibility(View.VISIBLE);
                tvTextNews.setText(news.getTextNews());
            }else
                tvTextNews.setVisibility(View.GONE);

            if (news.getImgUrl()!=null &&!news.getImgUrl().equals("")){
                imgNews.setVisibility(View.VISIBLE);
                imgNews.setImageURI(news.getImgUrl());
                imgNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        final AlertDialog dialog = builder.create();
                        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.item_dialog, null,false);

                        SimpleDraweeView imgNews=dialogLayout.findViewById(R.id.imageView5);
                        imgNews.setImageURI(news.getImgUrl());

                    //    ImageView logoImg= dialogLayout.findViewById(R.id.image_view_user);

                        TextView tvTextNew=dialogLayout.findViewById(R.id.textView11);
                        if (news.getTextNews()!=null &&!news.getTextNews().equals("")){
                            tvTextNew.setVisibility(View.VISIBLE);
                            tvTextNew.setText(news.getTextNews());
                        }else
                            tvTextNew.setVisibility(View.GONE);

                        TextView tvDates=dialogLayout.findViewById(R.id.textView12);
                        TimeAgo2 timeAgo2 = new TimeAgo2();
                        String covertTimeToText = timeAgo2.covertTimeToText(news.getDatePost());
                        tvDates.setText(covertTimeToText );

                        TextView  tvTitles = dialogLayout.findViewById(R.id.textView10);

                        if (news.getTitle()!=null &&!news.getTitle().equals("")){
                            tvTitles.setText(news.getTitle());
                        }else
                            tvTitles.setText("دليل الطالب");

                        dialog.setView(dialogLayout);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                      if (!dialog.isShowing())
                          dialog.show();
                    }
                });
            }else
                imgNews.setVisibility(View.GONE);


            if (news.getTitle()!=null &&!news.getTitle().equals("")){
                tvTitle.setText(news.getTitle());
            }else
                tvTitle.setText("دليل الطالب");

            TimeAgo2 timeAgo2 = new TimeAgo2();
            String covertTimeToText = timeAgo2.covertTimeToText(news.getDatePost());
            tvDate.setText(covertTimeToText );

        }
    }
}
