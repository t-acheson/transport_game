package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TimerUI extends Label {
	private float time;

	public TimerUI(CharSequence time, Skin skin) {
		super("", skin);
		this.time = Integer.parseInt(time.toString());
		CharSequence text = makeText();
		
		setText(text);
		// TODO Auto-generated constructor stub
		
	}
	
	CharSequence makeText()
	{
		return "" + Math.round(Math.floor(this.time/60)) + ":" + Math.round(Math.floor(this.time % 60));
	}
	
	public void updateTimer(float delta) {
		time -= delta;
		CharSequence text = makeText();
		setText(text);
	}
	
	public float getTime() {
		return time;
	}

}
