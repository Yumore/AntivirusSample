package com.yumore.traction;


import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.nathaniel.baseui.callback.OnFragmentToActivity;
import com.nathaniel.baseui.surface.BaseFragment;
import com.yumore.traction.databinding.FragmentTractionBinding;

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
    protected FragmentTractionBinding initViewBinding(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean attachToParent) {
        return FragmentTractionBinding.inflate(layoutInflater, viewGroup, false);
    }

    @Override
    public void loadData() {
        if (getArguments() == null) {
            return;
        }
        int videoRes = getArguments().getInt("res");
        currentPage = getArguments().getInt("page");
        viewBinding.videoView.setVideoPath("android.resource://" + getContext().getPackageName() + "/" + videoRes);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        viewBinding.videoView.requestFocus();
        viewBinding.videoView.seekTo(0);
        viewBinding.videoView.start();
        viewBinding.videoView.setOnCompletionListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (paused) {
            viewBinding.videoView.seekTo(currentPage);
            viewBinding.videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        currentPage = viewBinding.videoView.getCurrentPosition();
        paused = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewBinding.videoView.stopPlayback();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (null != onFragmentToActivity) {
            onFragmentToActivity.onCallback(OnFragmentToActivity.ACTION_NEXT_PAGE, currentPage);
        }
    }

    @Override
    public void bindView() {
        viewBinding.videoView.setOnPreparedListener(this);
    }
}