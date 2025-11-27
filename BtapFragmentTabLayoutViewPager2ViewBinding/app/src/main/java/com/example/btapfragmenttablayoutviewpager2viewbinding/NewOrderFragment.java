package com.example.btapfragmenttablayoutviewpager2viewbinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.btapfragmenttablayoutviewpager2viewbinding.databinding.FragmentNeworderBinding;

public class NewOrderFragment extends Fragment {

    private  FragmentNeworderBinding binding;


    public NewOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNeworderBinding.inflate(inflater, container, false);

        // TODO: Load dữ liệu vào View ở đây (RecyclerView, Adapter, ...),
        // hiện tại demo chỉ set text cho TextView
        binding.tvTitle.setText("New Order");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
