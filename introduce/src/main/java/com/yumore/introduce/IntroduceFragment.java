package com.yumore.introduce;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nathaniel.baseui.surface.BaseFragment;
import com.yumore.introduce.databinding.FragmentIntroduceBinding;


/**
 * @author nathaniel
 */
public class IntroduceFragment extends BaseFragment<FragmentIntroduceBinding> {
    private final int imageResource;

    private IntroduceFragment(int imageResource) {
        this.imageResource = imageResource;
    }

    public static IntroduceFragment newInstance(int imageResource) {
        return new IntroduceFragment(imageResource);
    }


    @Override
    protected FragmentIntroduceBinding initViewBinding(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean attachToParent) {
        return FragmentIntroduceBinding.inflate(layoutInflater, viewGroup, isAttachToRoot());
    }

    @Override
    public void loadData() {

    }

    @Override
    public void bindView() {
        viewBinding.tractionImageIv.setImageResource(imageResource);
    }
}
