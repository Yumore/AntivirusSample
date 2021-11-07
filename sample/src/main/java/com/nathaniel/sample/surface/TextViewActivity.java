package com.nathaniel.sample.surface;

import com.nathaniel.baseui.surface.BaseActivity;
import com.nathaniel.sample.databinding.ActivityTextBinding;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/5/18 - 21:32
 */
public class TextViewActivity extends BaseActivity<ActivityTextBinding> {

    @Override
    public void loadData() {

    }

    @Override
    public void bindView() {

    }

    @Override
    protected ActivityTextBinding initViewBinding() {
        return ActivityTextBinding.inflate(getLayoutInflater());
    }
}
