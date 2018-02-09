package org.lds.ldssa.task;

import org.junit.Before;
import org.junit.Test;
import org.lds.ldssa.sync.AnnotationSyncScheduler;

import javax.inject.Inject;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AnnotationFixTaskTest {

    @Inject
    AnnotationFixTask annotationFixTask;
    @Inject
    AnnotationSyncScheduler syncScheduler;

    @Before
    public void setUp() throws Exception {
        AnnotationFixTaskTestComponent component = DaggerAnnotationFixTaskTestComponent.builder().annotationFixTaskTestModule(new AnnotationFixTaskTestModule()).build();
        component.inject(this);
    }

    @Test
    public void testRun() throws Exception {
        annotationFixTask.execute();

        // make sure sync is requested
        verify(syncScheduler, times(1)).scheduleSync();

        // make sure there is nothing more to fix
        assertFalse(annotationFixTask.cleanupInvalidTags());

        // make sure there is not another sync requested (no fixes... no continuous loop of sync) (sync stays at 1)
        verify(syncScheduler, times(1)).scheduleSync();
    }
}