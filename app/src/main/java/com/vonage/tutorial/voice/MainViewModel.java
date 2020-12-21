package com.vonage.tutorial.voice;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;
import com.nexmo.client.NexmoCall;
import com.nexmo.client.NexmoCallHandler;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.request_listener.NexmoApiError;
import com.nexmo.client.request_listener.NexmoRequestListener;

public class MainViewModel extends ViewModel {

    private NexmoClient client = NexmoClient.get();
    private CallManager callManager = CallManager.getInstance();
    private NavManager navManager = NavManager.getInstance();

    private MutableLiveData<String> _toast = new MutableLiveData<>();
    public LiveData<String> toast = _toast;

    private MutableLiveData<Boolean> _loading = new MutableLiveData<>();
    public LiveData<Boolean> loading = _loading;

    private NexmoRequestListener<NexmoCall> callListener = new NexmoRequestListener<NexmoCall>() {
        @Override
        public void onSuccess(@Nullable NexmoCall call) {
            callManager.setOnGoingCall(call);

            _loading.postValue(false);

            NavDirections navDirections = MainFragmentDirections.actionMainFragmentToOnCallFragment();
            navManager.navigate(navDirections);
        }

        @Override
        public void onError(@NonNull NexmoApiError apiError) {
            _toast.postValue(apiError.getMessage());
            _loading.postValue(false);
        }
    };

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @SuppressLint("MissingPermission")
    public void startAppToPhoneCall() {
        // Callee number is ignored because it is specified in NCCO config
        client.call("IGNORED_NUMBER", NexmoCallHandler.SERVER, callListener);

        _loading.postValue(true);
    }

    public void onBackPressed() {
        client.logout();
    }
}

