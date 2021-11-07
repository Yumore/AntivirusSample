package com.yumore.introduce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yumore.introduce.databinding.FragmentIntroduceBinding;


/**
 * @author nathaniel
 */
public class IntroduceFragment extends Fragment {
    private final int imageResource;
    private FragmentIntroduceBinding introduceBinding;

    private IntroduceFragment(int imageResource) {
        this.imageResource = imageResource;
    }

    public static IntroduceFragment newInstance(int imageResource) {
        return new IntroduceFragment(imageResource);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        introduceBinding = FragmentIntroduceBinding.inflate(inflater);
        introduceBinding.tractionImageIv.setImageResource(imageResource);
        return introduceBinding.getRoot();
    }
}
