package vn.cmc.du21.loader;

import spark.Spark;
import vn.cmc.du21.business.service.AuthenticationService;
import vn.cmc.du21.business.service.UserService;
import vn.cmc.du21.common.Setting;
import vn.cmc.du21.presentation.external.controller.AuthenticationController;
import vn.cmc.du21.presentation.external.controller.UserController;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Spark.port(Setting.EXTERNAL_PORT);

        //dependency injection
        UserService userService = new UserService();
        new UserController(userService);
        AuthenticationService authenticationController = new AuthenticationService();
        new AuthenticationController(authenticationController);
    }
}
