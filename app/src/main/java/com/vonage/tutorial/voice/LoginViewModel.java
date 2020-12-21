package com.vonage.tutorial.voice;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.request_listener.NexmoConnectionListener;
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus;

public class LoginViewModel extends ViewModel {

    private NexmoClient client = NexmoClient.get();

    NavManager navManager = NavManager.getInstance();
    private MutableLiveData<ConnectionStatus> _connectionStatusMutableLiveData = new MutableLiveData<>();
    public LiveData<ConnectionStatus> connectionStatusLiveData = _connectionStatusMutableLiveData;

    public LoginViewModel() {
        client.setConnectionListener(new NexmoConnectionListener() {
            @Override
            public void onConnectionStatusChange(@NonNull ConnectionStatus connectionStatus, @NonNull ConnectionStatusReason connectionStatusReason) {
                if (connectionStatus == ConnectionStatus.CONNECTED) {
                    NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToMainFragment();
                    navManager.navigate(navDirections);
                    return;
                }

                _connectionStatusMutableLiveData.postValue(connectionStatus);
            }
        });
    }

    void onLoginUser(User user) {
        if (!user.jwt.trim().isEmpty()) {
            client.login(user.jwt);
        }
    }
}

