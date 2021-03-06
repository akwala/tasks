package org.tasks.etesync;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import org.tasks.caldav.BaseCaldavCalendarSettingsActivity;
import org.tasks.data.CaldavAccount;
import org.tasks.data.CaldavCalendar;

@AndroidEntryPoint
public class EteSyncCalendarSettingsActivity extends BaseCaldavCalendarSettingsActivity {

  @Inject EteSyncClient client;
  private CreateCalendarViewModel createCalendarViewModel;
  private DeleteCalendarViewModel deleteCalendarViewModel;
  private UpdateCalendarViewModel updateCalendarViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ViewModelProvider provider = new ViewModelProvider(this);
    createCalendarViewModel = provider.get(CreateCalendarViewModel.class);
    deleteCalendarViewModel = provider.get(DeleteCalendarViewModel.class);
    updateCalendarViewModel = provider.get(UpdateCalendarViewModel.class);

    createCalendarViewModel.observe(this, this::createSuccessful, this::requestFailed);
    deleteCalendarViewModel.observe(this, this::onDeleted, this::requestFailed);
    updateCalendarViewModel.observe(this, uid -> updateCalendar(), this::requestFailed);
  }

  @Override
  protected void createCalendar(CaldavAccount caldavAccount, String name, int color) {
    createCalendarViewModel.createCalendar(client, caldavAccount, name, color);
  }

  @Override
  protected void updateNameAndColor(CaldavAccount account, CaldavCalendar calendar, String name,
      int color) {
    updateCalendarViewModel.updateCalendar(client, account, calendar, name, color);
  }

  @Override
  protected void deleteCalendar(CaldavAccount caldavAccount, CaldavCalendar caldavCalendar) {
    deleteCalendarViewModel.deleteCalendar(client, caldavAccount, caldavCalendar);
  }
}
