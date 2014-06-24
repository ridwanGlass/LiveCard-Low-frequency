package com.example.livecards6;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity 
{

	public final Handler mHandler = new Handler();
	public static int value;
	
	@Override
	public void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		openOptionsMenu();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_one, menu);
		
		if(value==1)
		{
			MenuItem item = menu.findItem(R.id.item1);
			item.setVisible(false);
		}
		else if(value==2)
		{
			MenuItem item = menu.findItem(R.id.item2);
			item.setVisible(false);
		}
		else
		{
			
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item1:
			post(new Runnable() 
			{
				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					value=1;
					startService(new Intent(MainActivity.this, Card6Services.class));
				}
			});
			return true;
			
		case R.id.item2:
			
			post(new Runnable() 
			{
				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					value=2;
					startService(new Intent(MainActivity.this, Card6Services.class));
				}
			});
			return true;
			
		case R.id.Exit:
			
			post(new Runnable() 
			{
				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					value=0;
					stopService(new Intent(MainActivity.this, Card6Services.class));
				}
			});
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onOptionsMenuClosed(Menu menu)
	{
		//super.onOptionsMenuClosed(menu);
		value=0;
		finish();
	}
	
	private void post(Runnable runnable)
	{
		// TODO Auto-generated method stub
		mHandler.post(runnable);
	}
	

}
