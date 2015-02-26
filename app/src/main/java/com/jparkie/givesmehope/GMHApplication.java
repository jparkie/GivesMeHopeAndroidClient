package com.jparkie.givesmehope;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import dagger.ObjectGraph;

public class GMHApplication extends Application {
    public static final String TAG = GMHApplication.class.getSimpleName();

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeObjectGraph();
        initializeStetho();
    }

    public static GMHApplication getApplication(Context context) {
        return (GMHApplication)context.getApplicationContext();
    }

    public ObjectGraph buildInitialObjectGraph(Object... modules) {
        return ObjectGraph.create(modules);
    }

    public ObjectGraph buildScopedObjectGraph(Object... modules) {
        return mObjectGraph.plus(modules);
    }

    public void initializeObjectGraph() {
        mObjectGraph = buildInitialObjectGraph(Modules.getModules(this).toArray());
    }

    public void setObjectGraph(Object... modules) {
        mObjectGraph = buildScopedObjectGraph(modules);
    }

    private void initializeStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build()
            );
        }
    }
}
