package com.example.btapfragmenttablayoutviewpager2viewbinding;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btapfragmenttablayoutviewpager2viewbinding.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Gắn toolbar làm ActionBar
        setSupportActionBar(binding.toolbar);

        // ViewPager + TabLayout
        viewPagerAdapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("New");
                            break;
                        case 1:
                            tab.setText("Pending");
                            break;
                        case 2:
                            tab.setText("Completed");
                            break;
                    }
                }).attach();

        // FAB
        binding.fabAction.setOnClickListener(v -> {
            int pos = binding.viewPager.getCurrentItem();
            if (pos == 0) {
                Toast.makeText(this, "Add new order", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Other action", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // debug: xem hàm này có được gọi không
        // (lần đầu vào activity nó sẽ hiện 1 toast nhỏ)
        // bạn có thể xoá sau khi test xong
        Toast.makeText(this, "onCreateOptionsMenu", Toast.LENGTH_SHORT).show();

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miSearch) {
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.miNewGroup) {
            Toast.makeText(this, "New group clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.miBroadcast) {
            Toast.makeText(this, "Broadcast clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.miSetting) {
            Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
