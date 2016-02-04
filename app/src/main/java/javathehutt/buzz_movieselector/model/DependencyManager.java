package javathehutt.buzz_movieselector.model;

import javathehutt.buzz_movieselector.model.UserManager;

/**
 * Created by Mohammed on 2/3/2016.
 * trying to do dependency injections?
 */
public class DependencyManager {
    public static UserManager um;
    public static UserManager getUserManager() {
        if (um == null) {
            um = new UserMapManager();
        }
        return um;
    }
}
