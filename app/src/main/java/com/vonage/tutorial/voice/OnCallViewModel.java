package com.vonage.tutorial.voice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.nexmo.client.*;
import com.nexmo.client.request_listener.NexmoApiError;
import com.nexmo.client.request_listener.NexmoRequestListener;

public class OnCallViewModel extends ViewModel {

    private CallManager callManager = CallManager.getInstance();
    private NavManager navManager = NavManager.getInstance();

    private MutableLiveData<String> _toast = new MutableLiveData<>();
    public LiveData<String> toast = _toast;

    private NexmoCallEventListener callEventListener = new NexmoCallEventListener() {
        @Override
        public void onMemberStatusUpdated(NexmoCallMemberStatus callMemberStatus, NexmoCallMember callMember) {
            if (callMemberStatus == NexmoCallMemberStatus.COMPLETED || callMemberStatus == NexmoCallMemberStatus.CANCELLED) {
                callManager.setOnGoingCall(null);
                navManager.popBackStack(R.id.mainFragment, false);
            }
        }

        @Override
        public void onMuteChanged(NexmoMediaActionState mediaActionState, NexmoCallMember callMember) { }

        @Override
        public void onEarmuffChanged(NexmoMediaActionState mediaActionState, NexmoCallMember callMember) { }

        @Override
        public void onDTMF(String dtmf, NexmoCallMember callMember) { }
    };

    public OnCallViewModel() {
        NexmoCall onGoingCall;

        if (callManager.getOnGoingCall() == null) {
            throw new RuntimeException("Call is null");
        } else {
            onGoingCall = callManager.getOnGoingCall();
        }

        onGoingCall.addCallEventListener(callEventListener);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        NexmoCall ongoingCall = callManager.getOnGoingCall();

        if (ongoingCall != null) {
            ongoingCall.removeCallEventListener(callEventListener);
        }
    }

    public void onBackPressed() {
        hangupInternal();
    }

    public void hangup() {
        hangupInternal();
    }

    private void hangupInternal() {
        NexmoCall ongoingCall = callManager.getOnGoingCall();

        if (ongoingCall != null) {
            ongoingCall.hangup(new NexmoRequestListener<NexmoCall>() {
                @Override
                public void onSuccess(@Nullable NexmoCall call) {
                    callManager.setOnGoingCall(null);
                }

                @Override
                public void onError(@NonNull NexmoApiError apiError) {
                    _toast.postValue(apiError.getMessage());
                }
            });
        }
    }
}