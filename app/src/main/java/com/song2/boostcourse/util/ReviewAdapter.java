package com.song2.boostcourse.util;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.databinding.ItemReviewBinding;

import java.util.ArrayList;


public class ReviewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ReviewData> ReviewList;
    private int nListCnt = 0;

    public ReviewAdapter(ArrayList<ReviewData> _ReviewList){
        ReviewList =_ReviewList;
        nListCnt = _ReviewList.size();
    }

    @Override
    public int getCount() {
        return ReviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return ReviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ItemReviewBinding binding;

        //item lyout을 객체로 받아
        inflater = LayoutInflater.from(viewGroup.getContext());
        binding = DataBindingUtil.getBinding(view);

        if(binding == null){
            binding = DataBindingUtil.inflate(inflater, R.layout.item_review, viewGroup, false);
        }

/*      //non using dataBinding
        Context context = viewGroup.getContext();

        if (view == null) {

            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

        }

        view = inflater.inflate(R.layout.item_review, viewGroup, false);


        TextView user_id =  view.findViewById(R.id.tv_review_item_user_id);
        TextView review_date = view.findViewById(R.id.tv_review_item_date);
        TextView comment = view.findViewById(R.id.tv_review_item_contents);
        TextView like_cnt = view.findViewById(R.id.tv_review_item_like_cnt);
        RatingBar rating = view.findViewById(R.id.rating_bar_review_item_review_rate);
        CircleImageView profile_img = view.findViewById(R.id.cv_review_item_profile_img);

        rating.setRating(ReviewList.get(position).rate);
        user_id.setText(ReviewList.get(position).user_id);
        review_date.setText(ReviewList.get(position).date);
        comment.setText(ReviewList.get(position).comment);
        like_cnt.setText(ReviewList.get(position).like);
        //Glide.with(view).load(ReviewList.get(position).profile_img).into(profile_img);*/

        //return  view;

        binding.setReviewData((ReviewData)getItem(position));
        binding.executePendingBindings();

        return binding.getRoot();
    }
}