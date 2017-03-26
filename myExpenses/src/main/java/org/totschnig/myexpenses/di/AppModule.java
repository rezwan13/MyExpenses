package org.totschnig.myexpenses.di;

import android.support.annotation.Nullable;

import org.acra.ReportingInteractionMode;
import org.acra.config.ACRAConfiguration;
import org.acra.config.ACRAConfigurationException;
import org.acra.config.ConfigurationBuilder;
import org.totschnig.myexpenses.MyApplication;
import org.totschnig.myexpenses.R;
import org.totschnig.myexpenses.util.HashLicenceHandler;
import org.totschnig.myexpenses.util.LicenceHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class AppModule {
  private MyApplication application;

  public AppModule(MyApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  LicenceHandler providesLicenceHandler() {
    return new HashLicenceHandler();
  }

  @Provides
  @Singleton
  @Nullable
  ACRAConfiguration providesAcraConfiguration() {
    if (MyApplication.isInstrumentationTest()) return null;
    try {
      return new ConfigurationBuilder(application)
          .setReportingInteractionMode(ReportingInteractionMode.DIALOG)
          .setMailTo("bug-reports@myexpenses.mobi")
          .setResDialogText(R.string.crash_dialog_text)
          .setResDialogTitle(R.string.crash_dialog_title)
          .setResDialogCommentPrompt(R.string.crash_dialog_comment_prompt)
          .build();
    } catch (ACRAConfigurationException e) {
      Timber.e(e, "ACRA not initialized");
      return null;
    }
  }
}
