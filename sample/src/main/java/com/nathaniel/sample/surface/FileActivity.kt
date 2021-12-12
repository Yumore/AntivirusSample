package com.nathaniel.sample.surface

import android.Manifest
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import com.example.scanfilesutil.utils.ScanFileUtil
import com.hjq.toast.ToastUtils
import com.nathaniel.baseui.surface.BaseActivity
import com.nathaniel.sample.R
import com.nathaniel.sample.databinding.ActivityFileBinding
import com.nathaniel.utility.LoggerUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class FileActivity : BaseActivity<ActivityFileBinding>(), View.OnClickListener {
    /**
     *第一个扫描任务
     */
    private lateinit var scanFileOne: ScanFileUtil

    /**
     *第二个扫描任务
     */
    private lateinit var scanFileTwo: ScanFileUtil

    /**
     * 同时扫描管理类
     */
    var mScanTogetherManager: ScanFileUtil.ScanTogetherManager = ScanFileUtil.ScanTogetherManager()


    private val oneFileList = mutableListOf<File>()
    private val twoFileList = mutableListOf<File>()


    /**
     * 第一个扫描任务
     */
    private fun initOne() {
        scanFileOne = ScanFileUtil(ScanFileUtil.externalStorageDirectory)

        val build = ScanFileUtil.FileFilterBuilder().apply {
            onlyScanFile()
        }.build()

        scanFileOne.setCallBackFilter(build)

        scanFileOne.setScanFileListener(object : ScanFileUtil.ScanFileListener {
            var i = 0
            override fun scanBegin() {
                oneFileList.clear()
                i = 0
            }

            override fun scanComplete(timeConsuming: Long) {
                //   处理你的扫描结果 Process your scan results
                //   Log.d("tow Scan",oneFileList.toString())
                viewBinding.scanInfoTv.text = " 扫描任务1完成 one scan complete ; time:${timeConsuming} size {${oneFileList.size}}"
//            Toast.makeText(this, "one scan end 扫描完成", Toast.LENGTH_SHORT).show()
            }

            override fun scanningCallBack(file: File) {
                oneFileList.add(file)//保存扫描数据 Save scan data
                //以下代码不推荐, 如果有耗时操作和计算操作，会影响扫描速度，Log也不要写在这里
                //The following code is not recommended,
                // if there are time-consuming operations and calculation operations,
                // it will affect the scanning speed，Log also don't write here
                if (i == 20) {
                    i = 0
                    //20次回调一次，减少页面刷新频次
                    GlobalScope.launch(Dispatchers.Main) {
                        //展示过程 Show the process
                        viewBinding.scanInfoTv.text = file.absolutePath
                    }
                } else {
                    i++
                }
                LoggerUtils.logger("one Scan", "${file.path} name ${file.name}")
            }

        })
    }

    /**
     * 第二个扫描任务
     */
    private fun initTwo() {

        scanFileTwo = ScanFileUtil(ScanFileUtil.externalStorageDirectory,
                object : ScanFileUtil.ScanFileListener {
                    var i = 0
                    override fun scanBegin() {
                        i = 0
                        twoFileList.clear()
                    }

                    override fun scanComplete(timeConsuming: Long) {
                        viewBinding.scanTwoInfoTv.text = " 扫描任务2完成 tow scan complete ;time:${timeConsuming} size${twoFileList.size}"
                    }

                    override fun scanningCallBack(file: File) {
                        twoFileList.add(file)
                        if (i == 20) {
                            i = 0
                            GlobalScope.launch(Dispatchers.Main) {
                                viewBinding.scanTwoInfoTv.text = file.absolutePath
                            }
                        } else {
                            i++
                        }
                        LoggerUtils.logger("two Scan", "${file.absolutePath}}")
                    }
                })

        //设置过滤规则
        scanFileTwo.setCallBackFilter(ScanFileUtil.FileFilterBuilder()
                .apply {
                    scanDocumentFiles()
                }
                .build())
    }


    fun stopScan(view: View?) {
        mScanTogetherManager.cancel()
        scanFileOne.stop()
        scanFileTwo.stop()
    }


    override fun onDestroy() {
        super.onDestroy()
        stopScan(null)
    }

    override fun loadData() {
        RxPermissions(activity).request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.GET_PACKAGE_SIZE)
                .subscribe()
    }

    override fun bindView() {
        initOne()
        initTwo()
        viewBinding.startOne.setOnClickListener(this)
        viewBinding.startTwo.setOnClickListener(this)
        viewBinding.scanTogether.setOnClickListener(this)
    }

    override fun initViewBinding(): ActivityFileBinding {
        return ActivityFileBinding.inflate(layoutInflater)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.start_one -> scanFileOne.startAsyncScan()
            R.id.start_two -> scanFileTwo.startAsyncScan()
            R.id.scan_together -> {
                mScanTogetherManager.scan(scanFileOne, scanFileTwo) {
                    LoggerUtils.logger("Scan", "one scan and two scan end,扫描1 和 扫描2 完成")
                    ToastUtils.show("one scan and two scan end,扫描1 和 扫描2 完成")
                }
            }
            R.id.stop_btn -> stopScan(view)
        }
        when (view) {
            is Button -> ToastUtils.show("按钮被点击了")
            is TextView -> ToastUtils.show("文字被点击了")
            is ScrollView -> ToastUtils.show("列表被点击了")
        }
    }
}

