package org.tasks.injection;

import android.app.Activity;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import org.tasks.R;
import org.tasks.billing.Inventory;
import org.tasks.location.Geocoder;
import org.tasks.location.MapboxGeocoder;
import org.tasks.preferences.Preferences;
import org.tasks.themes.ColorProvider;
import org.tasks.themes.ThemeAccent;
import org.tasks.themes.ThemeBase;
import org.tasks.themes.ThemeColor;

@Module
public class ActivityModule {

  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides
  public Activity getActivity() {
    return activity;
  }

  @Provides
  @ForActivity
  public Context getActivityContext() {
    return activity;
  }

  @Provides
  @ActivityScope
  public ThemeBase getThemeBase(Preferences preferences, Inventory inventory) {
    return ThemeBase.getThemeBase(preferences, inventory, activity.getIntent());
  }

  @Provides
  @ActivityScope
  public ThemeColor getThemeColor(ColorProvider colorProvider, Preferences preferences) {
    return colorProvider.getThemeColor(preferences.getDefaultThemeColor(), true);
  }

  @Provides
  @ActivityScope
  public ThemeAccent getThemeAccent(ColorProvider colorProvider, Preferences preferences) {
    return colorProvider.getThemeAccent(preferences.getInt(R.string.p_theme_accent, 1));
  }

  @Provides
  @ActivityScope
  public Geocoder getGeocoder(@ForApplication Context context) {
    return new MapboxGeocoder(context);
  }
}
