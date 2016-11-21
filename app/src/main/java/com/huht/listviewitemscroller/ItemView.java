package com.huht.listviewitemscroller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class ItemView extends LinearLayout{

	private String TAG = "ITEM_MOVE";
	
	private TextView tv;
	
	private LinearLayout item;
	
	float lastX,lastY,mLastionMotionX;
	
	private Scroller mScroller;
	
	private View buttonView;
	
	private View itemView;
	
	private int buttonWidth;
	
	public ItemView(Context context,int itemLayoutId, int buttonLayoutId) {
		super(context);
		setOrientation(HORIZONTAL);
		itemView = LayoutInflater.from(context).inflate(itemLayoutId, null);
		addView(itemView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		buttonView = LayoutInflater.from(context).inflate(buttonLayoutId, this);
		mScroller = new Scroller(context);
        
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		buttonWidth = 160;
		Log.i("buttonWidth=",""+buttonWidth);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			
			Log.i(TAG, "ACTION_DOWN");
			lastX = event.getX();
			lastY = event.getY();
			mLastionMotionX = lastX;
			if(mScroller != null){  
                if(!mScroller.isFinished()){  
                	mScroller.abortAnimation();   
                }  
            }  
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "ACTION_MOVE");
			float x = event.getX();
			float y = event.getY();
			if (Math.abs(lastX-x)>20) {
				getParent().requestDisallowInterceptTouchEvent(true);
				float deltX =  mLastionMotionX -x ;
				Log.i(TAG, "ACTION_MOVE "+deltX);
				smoothScrollBy((int) deltX, 0);
				mLastionMotionX = event.getX();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i(TAG, "ACTION_UP " +(lastX-event.getX()));
			if (lastX-event.getX()>buttonWidth/2) {
				smoothScrollTo(buttonWidth,0);
			} else {
				smoothScrollTo(0,0);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		    //��ť�����߼�
			getParent().requestDisallowInterceptTouchEvent(false);
			Log.i(TAG, "ACTION_CANCEL " +(lastX-event.getX()));
			if (lastX-event.getX()>buttonWidth/2) {
				smoothScrollTo(buttonWidth, 0);
			} else {
				smoothScrollTo(0, 0);
			}
		    break;
		}
		return true;
	}
	
	//���ô˷���������Ŀ��λ��
	public void smoothScrollTo(int fx, int fy) {
	    int dx = fx - mScroller.getFinalX();
	    int dy = fy - mScroller.getFinalY();
	    smoothScrollBy(dx, dy);
	}
	
	//���ô˷������ù��������ƫ��
	public void smoothScrollBy(int dx, int dy) {
		Log.i("scroll",mScroller.getFinalX() + "," + dx);
		if (mScroller.getFinalX() + dx > buttonWidth || mScroller.getFinalX() + dx<0)
			return;
	    //����mScroller�Ĺ���ƫ����
	    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
	    invalidate();//����������invalidate()���ܱ�֤computeScroll()�ᱻ���ã�����һ����ˢ�½��棬����������Ч��
	}
	
	@Override
	public void computeScroll() {
	
	    //���ж�mScroller�����Ƿ����
	    if (mScroller.computeScrollOffset()) {
	    
	        //�������View��scrollTo()���ʵ�ʵĹ���
	        scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
	        
	        //������ø÷���������һ���ܿ�������Ч��
	        postInvalidate();
	    }
	    super.computeScroll();
	}


}
