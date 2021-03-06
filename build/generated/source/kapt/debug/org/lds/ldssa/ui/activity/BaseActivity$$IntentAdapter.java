package org.lds.ldssa.ui.activity;

import android.content.Intent;
import java.lang.IllegalStateException;
import pocketknife.internal.IntentBinding;

/**
 * Generated by IntentBindingAdapterGenerator on 2017-12-14T09:36-0700 - DO NOT MODIFY
 */
public class BaseActivity$$IntentAdapter<T extends BaseActivity> implements IntentBinding<T> {
  public void bindExtras(T target, Intent intent) {
    if (intent == null) {
      throw new IllegalStateException("intent is null");
    }
    if (intent.hasExtra("EXTRA_SCREEN_ID")) {
      target.setBaseScreenId(intent.getLongExtra("EXTRA_SCREEN_ID", target.getBaseScreenId()));
    } else {
      throw new IllegalStateException("Required Extra with key 'EXTRA_SCREEN_ID' was not found for 'baseScreenId'.If this is not required add '@NotRequired' annotation.");
    }
  }
}
