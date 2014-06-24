package com.example.livecards6;

import java.util.Calendar;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

public class Card6Services extends Service 
{

	private static final String LIVE_CARD_TAG = "LiveCard";

	private LiveCard currentLiveCard;
	// Control LiveCard's components
	private RemoteViews liveCardRemote;

	private final Handler handler = new Handler();
	// Runnable to refresh LiveCard
	private final LiveCardUpdater liveCardUpdater = new LiveCardUpdater();
	// Delay to refresh LiveCard
	private static final long REFRESH_CARD_DELAY = 1000; 
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		if (currentLiveCard == null) 
		{
			// new instance of LiveCard
			currentLiveCard = new LiveCard(this, LIVE_CARD_TAG);

			// start a RemoteView to control LiveCard components
			liveCardRemote = new RemoteViews(getPackageName(), R.layout.activity_main); 

			Intent menuIntent = new Intent(this, MainActivity.class);
			menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			currentLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));

			currentLiveCard.publish(PublishMode.REVEAL);

			handler.post(liveCardUpdater);
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() 
	{
		if (currentLiveCard != null && currentLiveCard.isPublished()) 
		{
			 // Stop the live card updater
			liveCardUpdater.setStop(true);

			// Remove live card from user's timeline
			currentLiveCard.unpublish(); 
			
			currentLiveCard = null;
		}
		super.onDestroy();
	}

	public class LiveCardUpdater implements Runnable 
	{

		private boolean mIsStopped = false;

		public void run() 
		{
			if (!isStopped()) 
			{
				if(MainActivity.value==1)
				{
					liveCardRemote.setTextViewText(R.id.main_text, "1");
				}
				else if(MainActivity.value==2)
				{
					liveCardRemote.setTextViewText(R.id.main_text, "2");
				}
				else 
				{
					liveCardRemote.setTextViewText(R.id.main_text, "Live Card");
				}
				currentLiveCard.setViews(liveCardRemote); 

				handler.postDelayed(liveCardUpdater, REFRESH_CARD_DELAY); 
			}
		}

		public boolean isStopped()
		{
			return mIsStopped;
		}

		public void setStop(boolean isStopped)
		{
			this.mIsStopped = isStopped;
		}
	}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

}
