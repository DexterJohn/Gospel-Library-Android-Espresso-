package org.lds.ldssa.ux.tips.lists;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipListPagerViewModel_Factory implements Factory<TipListPagerViewModel> {
  private static final TipListPagerViewModel_Factory INSTANCE = new TipListPagerViewModel_Factory();

  @Override
  public TipListPagerViewModel get() {
    return new TipListPagerViewModel();
  }

  public static Factory<TipListPagerViewModel> create() {
    return INSTANCE;
  }
}
