package com.ucontrol.flowerpot.widgets;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class CommonOnTouchListener implements View.OnTouchListener {

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			v.setBackgroundColor(Color.parseColor("#6481b940"));
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			v.setBackgroundColor(0);
		}
		return false;
		
	}
}
