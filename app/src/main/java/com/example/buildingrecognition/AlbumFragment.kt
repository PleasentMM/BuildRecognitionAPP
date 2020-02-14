package com.example.buildingrecognition

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.buildingrecognition.utils.GlideEngine
import com.example.buildingrecognition.utils.PictureUriUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

/**
 * used for 从相册中获取图片,并设置是否压缩，
 * 并回调图片的url
 * style by imurluck
 * style at 2019-08-10
 */
class AlbumFragment : Fragment() {

    var compress = true

    var onPhotoPickResult: ((url: String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startChoosePhoto()
    }

    fun startChoose(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction().add(this, TAG).commit()
    }

    /**
     * 调用知乎的图片选择框架，从相册中选取图片，
     * 知选择jpg、jpeg、png类型的图片
     */
    private fun startChoosePhoto() {
        Matisse.from(this)
            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
            .theme(R.style.AlbumStyle)
            .countable(true)
            .thumbnailScale(0.85F)
            .maxSelectable(PHOTO_MAX_SELECTABLE)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .imageEngine(GlideEngine())
            .forResult(ALBUM_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ALBUM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoUriList = Matisse.obtainResult(data)
            val photoUrlList = PictureUriUtil.handleImageAfterKitKat(requireContext(), photoUriList)
            if (photoUrlList.size > 0) {
                callbackPhoto(photoUrlList[0])
            }
        }
        fragmentManager?.beginTransaction()?.remove(this)
    }

    /**
     * 回调图片Url给调用者，如果设置了压缩，则先压缩，将压缩后的url回调
     */
    private fun callbackPhoto(photoUrl: String) {
        if (!compress) {
            onPhotoPickResult?.invoke(photoUrl)
            return
        }
        Luban.with(activity)
            .load(photoUrl)
            .ignoreBy(100)
            .setTargetDir(activity?.externalMediaDirs?.firstOrNull()?.absolutePath)
            .setFocusAlpha(false)
            .filter {
                !TextUtils.isEmpty(it)
            }.setCompressListener(object : OnCompressListener {
                override fun onSuccess(file: File) {
                    onPhotoPickResult?.invoke(file.absolutePath)
                }

                override fun onError(e: Throwable?) {
                }

                override fun onStart() {
                }

            }).launch()
    }


    companion object {

        private const val TAG = "AlbumFragment"

        private const val PHOTO_MAX_SELECTABLE = 1

        private const val ALBUM_REQUEST_CODE = 0xEF
    }
}