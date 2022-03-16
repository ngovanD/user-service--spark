package vn.cmc.du21.presentation.external.controller;

import spark.Spark;
import vn.cmc.du21.business.service.AuthenticationService;
import vn.cmc.du21.business.service.UserService;
import vn.cmc.du21.common.DateTimeUtil;
import vn.cmc.du21.common.Util;
import vn.cmc.du21.common.restful.StandardResponse;
import vn.cmc.du21.common.restful.StatusResponse;
import vn.cmc.du21.presentation.external.mapper.SessionMapper;
import vn.cmc.du21.presentation.external.mapper.UserMapper;
import vn.cmc.du21.presentation.external.request.SessionUpsertRequest;
import vn.cmc.du21.presentation.external.response.UserSessionResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService = new UserService();

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.initHandler();
    }

    private void initHandler(){
        // Login
        Spark.post("api/v1.0/authentication/login", (request, response) -> {
            response.type("application/json");
            // request param username and method
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            // check username and password
            if(userService.checkUsernameAndPassword(username, password)!=null){
                long roleId = userService.getUserRole(userService.checkUsernameAndPassword(username, password).getUserId()).getRoleId();
                String roleName = userService.getRole(roleId).getNameRole();
                // create a token
                String token = authenticationService.createJWTToken(
                        userService.checkUsernameAndPassword(username, password).getUserName(),
                        roleName);
                // create a session upsert request
                SessionUpsertRequest sessionUpsertRequest = new SessionUpsertRequest(
                        userService.checkUsernameAndPassword(username, password).getUserId(),
                        1L, token,
                        DateTimeUtil.localDateTimeToString(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),
                        "Active");
                // check if session exists or not
                if(authenticationService.sessionExist(
                        userService.checkUsernameAndPassword(username, password).getUserId(),
                        1L) == null)
                {
                    authenticationService.addSession(SessionMapper.sessionUpsertRequestToSession(sessionUpsertRequest));
                }
                else
                {
                    sessionUpsertRequest.setToken(token);
                    authenticationService.updateSession(SessionMapper.sessionUpsertRequestToSession(sessionUpsertRequest));
                }
                // create a user session response
                UserSessionResponse userSessionResponse = new UserSessionResponse(
                        UserMapper.userToUserDetailResponse(userService.checkUsernameAndPassword(username, password)),
                        SessionMapper.sessionToSessionDetailResponse(
                                SessionMapper.sessionUpsertRequestToSession(sessionUpsertRequest))
                );
                // thêm token vào header
                response.header("Authorization", "Bearer "+ token);

                return Util.getObjectMapper().toJson(
                        new StandardResponse<>(
                                StatusResponse.SUCCESSFUL.getStatus(),
                                StatusResponse.SUCCESSFUL.getStatusInt(),
                                userSessionResponse)
                );
            } else {
                return Util.getObjectMapper().toJson(new StandardResponse<>(
                        StatusResponse.INTERNALSERVERERROR.getStatus(),
                        StatusResponse.INTERNALSERVERERROR.getStatusInt()));
            }
        });

        // Autologin
        Spark.post("api/v1.0/authentication/auto-login", (request, response) -> {
            response.type("application/json");
            // request a token
            String token = request.queryParams("token");

            //check token có tồn tại và ở trạng thái Active
            if(authenticationService.checkToken(token) != null){
                //check user có tồn tại
                if(userService.getUser(authenticationService.checkToken(token).getUserId()) != null)
                {
                    // Trả về thông tin user
                    return Util.getObjectMapper().toJson(new StandardResponse<>(StatusResponse.SUCCESSFUL.getStatus(),
                            StatusResponse.SUCCESSFUL.getStatusInt(),
                            UserMapper.userToUserDetailResponse(userService.getUser(authenticationService.checkToken(token).getUserId()))));
                }
                else
                {
                    return Util.getObjectMapper().toJson(new StandardResponse<>(StatusResponse.UNAUTHORIZED.getStatus(),
                            StatusResponse.UNAUTHORIZED.getStatusInt()));
                }

            } else {
                return Util.getObjectMapper().toJson(new StandardResponse<>(StatusResponse.UNAUTHORIZED.getStatus(),
                        StatusResponse.UNAUTHORIZED.getStatusInt()));
            }
        });

        //logout
        Spark.get("api/v1.0/logout", (request, response) -> {
            response.type("application/json");

            String authorization = request.headers("Authorization");
            String[] arr = authorization.split(" ");
            String token = arr[1];


            //check token có tồn tại hay k
            if(authenticationService.checkToken(token) != null){
                //cập nhật lại trạng thái token về Logout
                SessionUpsertRequest sessionUpsertRequest = SessionMapper.sessionToSessionUpsertRequest(authenticationService.checkToken(token));
                sessionUpsertRequest.setStatus("Logout");
                authenticationService.updateSession(SessionMapper.sessionUpsertRequestToSession(sessionUpsertRequest));
                return Util.getObjectMapper().toJson(new StandardResponse<>(StatusResponse.SUCCESSFUL.getStatus(),
                        StatusResponse.SUCCESSFUL.getStatusInt()));
            } else {
                return Util.getObjectMapper().toJson(new StandardResponse<>(StatusResponse.UNAUTHORIZED.getStatus(),
                        StatusResponse.UNAUTHORIZED.getStatusInt()));
            }
        });
    }
}
