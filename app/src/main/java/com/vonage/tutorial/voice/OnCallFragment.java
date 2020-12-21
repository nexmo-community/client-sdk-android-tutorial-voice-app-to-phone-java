package com.vonage.tutorial.voice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class OnCallFragment extends Fragment implements BackPressHandler {

    OnCallViewModel viewModel;

    Button endCall;

    public OnCallFragment() {
        super(R.layout.fragment_on_call);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(OnCallViewModel.class);

        endCall = view.findViewById(R.id.endCall);

        viewModel.toast.observe(getViewLifecycleOwner(), it -> Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT));

        endCall.setOnClickListener(view1 -> viewModel.hangup());
    }


    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}
