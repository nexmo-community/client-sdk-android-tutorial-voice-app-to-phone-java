package com.vonage.tutorial.voice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainFragment extends Fragment implements BackPressHandler {

    MainViewModel viewModel;

    Button startAppToPhoneCallButton;

    ProgressBar progressBar;


    private Observer<String> toastObserver = it -> Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show();

    private Observer<Boolean> loadingObserver = this::setDataLoading;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startAppToPhoneCallButton = view.findViewById(R.id.startAppToPhoneCallButton);
        progressBar = view.findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        viewModel.toast.observe(getViewLifecycleOwner(), toastObserver);
        viewModel.loading.observe(getViewLifecycleOwner(), loadingObserver);

        startAppToPhoneCallButton.setOnClickListener(it -> viewModel.startAppToPhoneCall());
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    private void setDataLoading(Boolean dataLoading) {
        startAppToPhoneCallButton.setEnabled(!dataLoading);

        int visibility;

        if (dataLoading) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.GONE;
        }

        progressBar.setVisibility(visibility);
    }
}
