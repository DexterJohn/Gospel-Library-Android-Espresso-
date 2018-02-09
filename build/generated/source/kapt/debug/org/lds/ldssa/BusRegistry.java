package org.lds.ldssa;

import java.lang.Override;
import org.lds.ldssa.download.AllAudioDownloader;
import org.lds.ldssa.download.AllAudioDownloaderSubscriptionRegistration;
import org.lds.ldssa.download.ContentDownloader;
import org.lds.ldssa.download.ContentDownloaderSubscriptionRegistration;
import org.lds.ldssa.ui.activity.BaseActivity;
import org.lds.ldssa.ui.activity.BaseActivitySubscriptionRegistration;
import org.lds.ldssa.ui.activity.StartupActivity;
import org.lds.ldssa.ui.activity.StartupActivitySubscriptionRegistration;
import org.lds.ldssa.ui.activity.VideoPlayerActivity;
import org.lds.ldssa.ui.activity.VideoPlayerActivitySubscriptionRegistration;
import org.lds.ldssa.ui.fragment.SettingsFragment;
import org.lds.ldssa.ui.fragment.SettingsFragmentSubscriptionRegistration;
import org.lds.ldssa.ux.annotations.AnnotationsFragment;
import org.lds.ldssa.ux.annotations.AnnotationsFragmentSubscriptionRegistration;
import org.lds.ldssa.ux.catalog.CatalogDirectoryActivity;
import org.lds.ldssa.ux.catalog.CatalogDirectoryActivitySubscriptionRegistration;
import org.lds.ldssa.ux.content.directory.ContentDirectoryActivity;
import org.lds.ldssa.ux.content.directory.ContentDirectoryActivitySubscriptionRegistration;
import org.lds.ldssa.ux.content.item.ContentItemFragment;
import org.lds.ldssa.ux.content.item.ContentItemFragmentSubscriptionRegistration;
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryActivity;
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryActivitySubscriptionRegistration;
import org.lds.ldssa.ux.search.SearchPresenter;
import org.lds.ldssa.ux.search.SearchPresenterSubscriptionRegistration;
import pocketbus.SubscriptionRegistration;
import pocketbus.internal.Registry;

public class BusRegistry implements Registry {
  @Override
  public <T> SubscriptionRegistration getRegistration(T target) {
    if (target instanceof StartupActivity) {
      return new StartupActivitySubscriptionRegistration((StartupActivity)target);
    } else if (target instanceof SettingsFragment) {
      return new SettingsFragmentSubscriptionRegistration((SettingsFragment)target);
    } else if (target instanceof CustomCollectionDirectoryActivity) {
      return new CustomCollectionDirectoryActivitySubscriptionRegistration((CustomCollectionDirectoryActivity)target);
    } else if (target instanceof CatalogDirectoryActivity) {
      return new CatalogDirectoryActivitySubscriptionRegistration((CatalogDirectoryActivity)target);
    } else if (target instanceof VideoPlayerActivity) {
      return new VideoPlayerActivitySubscriptionRegistration((VideoPlayerActivity)target);
    } else if (target instanceof ContentDirectoryActivity) {
      return new ContentDirectoryActivitySubscriptionRegistration((ContentDirectoryActivity)target);
    } else if (target instanceof BaseActivity) {
      return new BaseActivitySubscriptionRegistration((BaseActivity)target);
    } else if (target instanceof SearchPresenter) {
      return new SearchPresenterSubscriptionRegistration((SearchPresenter)target);
    } else if (target instanceof AnnotationsFragment) {
      return new AnnotationsFragmentSubscriptionRegistration((AnnotationsFragment)target);
    } else if (target instanceof AllAudioDownloader) {
      return new AllAudioDownloaderSubscriptionRegistration((AllAudioDownloader)target);
    } else if (target instanceof ContentItemFragment) {
      return new ContentItemFragmentSubscriptionRegistration((ContentItemFragment)target);
    } else if (target instanceof ContentDownloader) {
      return new ContentDownloaderSubscriptionRegistration((ContentDownloader)target);
    }
    return null;
  }
}
