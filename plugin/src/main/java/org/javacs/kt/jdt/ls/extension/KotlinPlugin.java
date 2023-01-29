package org.javacs.kt.jdt.ls.extension;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.ILog;
import org.osgi.framework.BundleContext;

public class KotlinPlugin extends Plugin {
    private static KotlinPlugin plugin;
    
    public static final String PLUGIN_ID = "org.javacs.kt.jdt.ls.extension";

    public KotlinPlugin() {
        plugin = this;
    }
    
    public static KotlinPlugin getDefault() {
        return plugin;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        super.start(bundleContext);

        KotlinLogger.logInfo("Start org.javacs.kt.jdt.ls.extension");
        
        // ResourcesPlugin.getWorkspace().addResourceChangeListener(KotlinAnalysisProjectCache.INSTANCE,
        //         IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.PRE_BUILD);
        // ResourcesPlugin.getWorkspace().addResourceChangeListener(KotlinRefreshProjectListener.INSTANCE,
        //         IResourceChangeEvent.PRE_REFRESH);
        
        // KotlinProperties.init();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        // ResourcesPlugin.getWorkspace().removeResourceChangeListener(KotlinAnalysisProjectCache.INSTANCE);
        // ResourcesPlugin.getWorkspace().removeResourceChangeListener(KotlinRefreshProjectListener.INSTANCE);
        
        plugin = null;
    }
}
