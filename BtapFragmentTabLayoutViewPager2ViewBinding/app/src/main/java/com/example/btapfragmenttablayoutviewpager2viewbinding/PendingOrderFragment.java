package com.example.btapfragmenttablayoutviewpager2viewbinding;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btapfragmenttablayoutviewpager2viewbinding.databinding.FragmentPendingorderBinding;


public class PendingOrderFragment extends Fragment {

    private FragmentPendingorderBinding binding;

    public PendingOrderFragment() {
        // bắt buộc phải có constructor rỗng
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPendingorderBinding.inflate(inflater, container, false);

        // Ví dụ xử lý UI ở đây
        binding.tvTitlePending.setText("Pending Orders");
        binding.tvSubPending.setText("Danh sách đơn hàng đang chờ xử lý");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh leak
    }
}
