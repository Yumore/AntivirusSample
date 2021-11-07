package com.yumore.traction;


import android.content.Context;
import android.media.MediaPlayer;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.nathaniel.baseui.callback.OnFragmentToActivity;
import com.nathaniel.baseui.surface.BaseFragment;
import com.yumore.traction.databinding.FragmentTractionBinding;

import org.jetbrains.annotations.Nullable;

/**
 * @author Nathaniel
 */
public class TractionFragment extends BaseFragment<FragmentTractionBinding> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private int currentPage;
    private boolean paused;
    private OnFragmentToActivity<Integer> onFragmentToActivity;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentToActivity) {
            onFragmentToActivity = (OnFragmentToActivity<Integer>) context;
        }
    }

    @Override
    public void loadData() {
        if (getArguments() == null) {
            return;
        }
        int videoRes = getArguments().getInt("res");
        currentPage = getArguments().getInt("page");
        getBinding().videoView.setVideoPath("android.resource://" + getContext().getPackageName() + "/" + videoRes);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        getBinding().videoView.requestFocus();
        getBinding().videoView.seekTo(0);
        getBinding().videoView.start();
        getBinding().videoView.setOnCompletionListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (paused) {
            getBinding().videoView.seekTo(currentPage);
            getBinding().videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        currentPage = getBinding().videoView.getCurrentPosition();
        paused = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getBinding().videoView.stopPlayback();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (null != onFragmentToActivity) {
            onFragmentToActivity.onCallback(OnFragmentToActivity.ACTION_NEXT_PAGE, currentPage);
        }
    }

    @NonNull
    @Override
    protected FragmentTractionBinding getViewBinding(@Nullable ViewGroup viewGroup) {
        return FragmentTractionBinding.inflate(getLayoutInflater());
    }

    @Override
    public void bindView() {
        getBinding().videoView.setOnPreparedListener(this);
    }
}