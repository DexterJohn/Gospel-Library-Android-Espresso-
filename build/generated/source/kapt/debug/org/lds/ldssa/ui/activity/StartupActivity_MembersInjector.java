package org.lds.ldssa.ui.activity;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.task.StartupTask;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StartupActivity_MembersInjector implements MembersInjector<StartupActivity> {
  private final Provider<Bus> busProvider;

  private final Provider<StartupTask> startupTaskProvider;

  public StartupActivity_MembersInjector(
      Provider<Bus> busProvider, Provider<StartupTask> startupTaskProvider) {
    this.busProvider = busProvider;
    this.startupTaskProvider = startupTaskProvider;
  }

  public static MembersInjector<StartupActivity> create(
      Provider<Bus> busProvider, Provider<StartupTask> startupTaskProvider) {
    return new StartupActivity_MembersInjector(busProvider, startupTaskProvider);
  }

  @Override
  public void injectMembers(StartupActivity instance) {
    injectBus(instance, busProvider.get());
    injectStartupTaskProvider(instance, startupTaskProvider);
  }

  public static void injectBus(StartupActivity instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectStartupTaskProvider(
      StartupActivity instance, Provider<StartupTask> startupTaskProvider) {
    instance.startupTaskProvider = startupTaskProvider;
  }
}
