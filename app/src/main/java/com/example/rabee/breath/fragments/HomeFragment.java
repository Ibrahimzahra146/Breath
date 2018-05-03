
package com.example.rabee.breath.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.rabee.breath.Activities.AddPostActivity;
import com.example.rabee.breath.Activities.RecentCommentsActivity;
import com.example.rabee.breath.Adapters.HomePostAdapter;
import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.ReactsRecyclerViewModel;
import com.example.rabee.breath.RequestInterface.PostInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    public static List<PostCommentResponseModel> postResponseModelsList;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout noFriendsLayout;

    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ReactsRecyclerViewModel reactSingleModel = new ReactsRecyclerViewModel(1, "Ibrahim zahra", "75782539973_288842465026282085_n.jpg");
        PostInterface postInterface;
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        noFriendsLayout = (LinearLayout) view.findViewById(R.id.no_friends_Layout);

        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        postInterface = retrofit.create(PostInterface.class);
        final Call<List<PostCommentResponseModel>> postResponse = postInterface.getUserHomePost(GeneralInfo.getUserID());
        postResponse.enqueue(new Callback<List<PostCommentResponseModel>>() {

            @Override
            public void onResponse(Call<List<PostCommentResponseModel>> call, Response<List<PostCommentResponseModel>> response) {
                postResponseModelsList = response.body();
                //ensure that still in homefragment
                if (getActivity() != null) {


                    recyclerView.setAdapter(new HomePostAdapter(getActivity().getApplicationContext(), postResponseModelsList));
                    if (postResponseModelsList.size() == 0) {
                        noFriendsLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                    } else {
                        recyclerView.setAdapter(new HomePostAdapter(getActivity(), postResponseModelsList));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<PostCommentResponseModel>> call, Throwable t) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton RecentActivityFab = (FloatingActionButton) view.findViewById(R.id.RecentActivityFab);
        FloatingActionButton AddPostActivityfab = (FloatingActionButton) view.findViewById(R.id.AddPostActivityfab);
        RecentActivityFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), RecentCommentsActivity.class);
                startActivity(i);
            }
        });
        AddPostActivityfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddPostActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
