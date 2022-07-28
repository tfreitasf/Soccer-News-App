import android.app.Application;


/**
 * FIXME fazer ineção de dependencia
 * @see <a href="https://stackoverflow.com/a/14057777/3072570">Android Singleton with Global Context</a>
 */

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
