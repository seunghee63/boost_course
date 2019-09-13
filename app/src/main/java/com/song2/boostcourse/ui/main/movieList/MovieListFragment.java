package com.song2.boostcourse.ui.main.movieList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieRank;
import com.song2.boostcourse.data.MovieRankList;
import com.song2.boostcourse.databinding.FragmentMovieListBinding;
import com.song2.boostcourse.util.adapter.MoviePagerAdapter;
import com.song2.boostcourse.util.network.AppHelper;

import java.util.ArrayList;

public class MovieListFragment extends Fragment {

    FragmentMovieListBinding binding;

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);
        binding.setMovieList(this);

        sendRequest("/movie/readMovieList");

        return binding.getRoot();
    }

    public void settingViewPager(int count, ArrayList<MovieRank> dataList) {

        //뷰페이저
        ViewPager pager;

        pager = binding.pager;
        pager.setOffscreenPageLimit(count+1);

        MoviePagerAdapter adapter = new MoviePagerAdapter(getFragmentManager());

        //instance 생성
        for (int i =0; i< count; i++){
            adapter.addItem(MovieItemFragment.newInstance(i+1, dataList.get(i)));
        }

        pager.setAdapter(adapter);

        //좌우 미리보기 padding
        float d = getResources().getDisplayMetrics().density;
        int margin = (int)(30 * d);
        int marginRight = (int)(1 * d);

        pager.setClipToPadding(false);
        pager.setPadding(margin, 0, marginRight, 0);

        pager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);
    }

    // 통신
    public void sendRequest(final String route) {

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route;

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }

        //get,post :
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("응답 : ", "<"+ route + "> ::" + response);
                        movieListProcessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("에러 : ", error.toString());
                    }
                }
        );

        //아래 두 줄은 일반적으로 AppHelper에 넣어서 관리. 메소드 호출해서 여기서 씀..ㅎ
        request.setShouldCache(true);
        AppHelper.requestQueue.add(request);

        Log.e("sendRequest", "/movie/readMovieList - 요청보냄");
    }

    public void movieListProcessResponse(String response){
        Gson gson = new Gson();
        MovieRankList movieRankList = gson.fromJson(response, MovieRankList.class);

        if (movieRankList != null){
            int count = movieRankList.result.size();

            Log.e("데이터 길이 : ", String.valueOf(count));

            settingViewPager(count, movieRankList.result);

        }else{
            Log.e("데이터 길이 : ", "null");
        }
    }
}
