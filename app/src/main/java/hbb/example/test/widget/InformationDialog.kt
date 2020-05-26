package com.yunkahui.datacubeper.common.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.view.View
import android.view.Window
import android.widget.TextView

import hbb.example.test.R

/**
 * @author HuangJiaHeng
 * @date 2019/12/19.
 */
class InformationDialog:Dialog{

    private var mCancleListener:CancleListener ?=null
    private var mPositionListener:PositionClickListener ?=null
    private var view: View ?=null
    private var mTvDecristion:TextView?=null
    private var mTvTitle:TextView?=null
    private var mTvCancle:TextView?=null
    private var mTvGo:TextView?=null

    constructor(context: Context,id: Int):super(context){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        view = layoutInflater.inflate(if(id ==0) R.layout.dialog_home_certification else id!!,null)
        init()
    }

    companion object{
        private var mInformationDialog:InformationDialog?=null
        @Synchronized
        fun getInstance(context: Context?):InformationDialog{
            this.mInformationDialog = InformationDialog(context!!,0)
            return mInformationDialog as InformationDialog
        }

        @Synchronized
        fun getInstance(context: Context?,id:Int):InformationDialog{
            this.mInformationDialog = InformationDialog(context!!,id)
            return mInformationDialog as InformationDialog
        }
    }

    fun setDescrition(message:String):InformationDialog{
        mTvDecristion?.text = message
        return this
    }

    fun setTitle(title:String):InformationDialog{
        mTvTitle?.text = title
        return this
    }

    fun setCancleText(text:String):InformationDialog{
        mTvCancle?.text = text
        return this
    }

    fun setPositionText(text:String):InformationDialog {
        mTvGo?.text = text
        return this
    }

    fun setPositionGone():InformationDialog{
        mTvGo?.visibility = View.GONE
        return this
    }

    fun setCancleListener(listener:CancleListener):InformationDialog{
        mCancleListener = listener
        return  this
    }

    fun setCancle(b: Boolean):InformationDialog{
        this.setCancelable(b)
        return  this
    }

    fun setPositionListener(listener:PositionClickListener):InformationDialog{
        mPositionListener = listener
        return  this
    }

    fun setDescrition(message:String,gravity:Int):InformationDialog{
        mTvDecristion?.text = message
        mTvDecristion?.gravity = gravity
        return this
    }

    fun init() {
//        mTvDecristion = view?.findViewById(R.id.tv_description)
//        mTvTitle = view?.findViewById(R.id.tv_title)
//        mTvCancle = view?.findViewById(R.id.tv_cancle)
//        mTvGo = view?.findViewById(R.id.tv_go)
        mTvCancle?.setOnClickListener {
            if (mCancleListener==null){
                dismiss()
                return@setOnClickListener
            }
            mCancleListener?.onCancle(this)
        }
        mTvGo?.setOnClickListener {
            if (mPositionListener==null){
                dismiss()
                return@setOnClickListener
            }
            mPositionListener?.onClick(this)
        }
//        setContentView(view)
    }
    interface CancleListener{
        fun onCancle(dialog: Dialog)
    }
    interface PositionClickListener{
        fun onClick(dialog: Dialog)
    }
}