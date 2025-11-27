package com.example.btapfragmenttablayoutviewpager2viewbinding;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btapfragmenttablayoutviewpager2viewbinding.databinding.FragmentCompletedorderBinding;

public class CompletedOrderFragment extends Fragment {

    private FragmentCompletedorderBinding binding;

    public CompletedOrderFragment() {
        // constructor rỗng
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCompletedorderBinding.inflate(inflater, container, false);

        // Ví dụ xử lý UI
        binding.tvTitleCompleted.setText("Completed Orders");
        binding.tvSubCompleted.setText("Danh sách đơn hàng đã hoàn thành");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
