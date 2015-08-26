package com.lcsmobileapps.rssteam.ui;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcsmobileapps.rssteam.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamDialogFragment newInstance(String param1, String param2) {
        TeamDialogFragment fragment = new TeamDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static TeamDialogFragment newInstance() {
        TeamDialogFragment fragment = new TeamDialogFragment();

        return fragment;
    }
    public TeamDialogFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_team_dialog, container, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_dialog);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TeamAdapter(getActivity(),this));
        
        return v;
    }


}
