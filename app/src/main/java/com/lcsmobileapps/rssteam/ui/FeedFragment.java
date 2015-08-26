package com.lcsmobileapps.rssteam.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lcsmobileapps.rssteam.R;
import com.lcsmobileapps.rssteam.feed.FeedDownloader;
import com.lcsmobileapps.rssteam.feed.Team;
import com.lcsmobileapps.rssteam.provider.ContentController;
import com.lcsmobileapps.rssteam.util.Utils;

import android.os.Handler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;
    protected static RecyclerView recyclerView;
    public static final int WHAT_REFRESH_CONTENT = 0;
    public static final int WHAT_REFRESH_TEAM = 1;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    //Lets check
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();

        return fragment;
    }

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh : {
                refresh();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feed, container, false);

        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar)v.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager  linearLayout =  new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayout);
        String teamName = Utils.getPrefTeamName(getActivity());
        if (teamName.isEmpty()) {
            TeamDialogFragment dialogFragment = TeamDialogFragment.newInstance();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            dialogFragment.show(fm, "");
            FeedAdapter adapter = new FeedAdapter(ContentController.getInstance().getNews("", getActivity()), getActivity());
            recyclerView.setAdapter(adapter);
        } else {
            FeedAdapter adapter = new FeedAdapter(ContentController.getInstance().getNews(teamName, getActivity()), getActivity());
            recyclerView.setAdapter(adapter);
            refresh();
        }
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);

    }

    private void refresh() {
        FeedDownloader feed = new FeedDownloader(getActivity());
        feed.execute(Utils.getPrefTeamName(getActivity()));
    }

    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_REFRESH_CONTENT: {
                    FeedAdapter feedAdapter = (FeedAdapter)recyclerView.getAdapter();

                    feedAdapter.notifyDataSetChanged();
                }break;
                case WHAT_REFRESH_TEAM: {
                    Bundle bundle = msg.getData();
                    String teamName = bundle.getString("team");

                }
            }

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
