package net.antoniy.gidder.beta.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import net.antoniy.gidder.beta.R;
import net.antoniy.gidder.beta.ui.util.C;
import net.antoniy.gidder.beta.ui.util.GidderCommons;

public class ToggleAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(C.action.TOGGLE_SSH_SERVER), PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.toggle_widget);
		remoteViews.setOnClickPendingIntent(R.id.toggleWidgetButton, pendingIntent);

		if (GidderCommons.isSshServiceRunning(context)) {
			remoteViews.setImageViewResource(R.id.toggleWidgetButton, R.drawable.ic_widget_active);
		} else {
			remoteViews.setImageViewResource(R.id.toggleWidgetButton, R.drawable.ic_widget_inactive);
		}

		ComponentName componentName = new ComponentName(context, ToggleAppWidgetProvider.class);
		appWidgetManager.updateAppWidget(componentName, remoteViews);
	}
}
