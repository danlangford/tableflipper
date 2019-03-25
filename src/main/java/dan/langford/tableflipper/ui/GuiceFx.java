package dan.langford.tableflipper.ui;

import com.google.inject.Module;
import com.google.inject.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GuiceFx {

    private Injector injector;

    public <A extends Application> Injector init(A app) {
        return this.init(app, Collections.emptyList());
    }

    public  Injector init() {
        return this.init(Collections.emptyList());
    }

    public Injector init(Collection<Module> modules) {
        List<Module> allModules = new ArrayList<>(modules);
        allModules.add(new FXModule());
        injector = Guice.createInjector(allModules);
        return injector;
    }

    public <A extends Application> Injector init(A app, Collection<Module> modules) {
        injector = init(modules);
        injector.injectMembers(app);
        return injector;
    }

    class FXModule extends AbstractModule {

        @Override
        protected void configure() {}

        @Provides
        @Singleton
        FXMLLoader provideFxmlLoader() {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(injector::getInstance);
            return loader;
        }
    }
}
