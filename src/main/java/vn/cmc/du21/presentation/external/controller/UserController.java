package vn.cmc.du21.presentation.external.controller;

import spark.Spark;
import vn.cmc.du21.business.service.UserService;
import vn.cmc.du21.common.Util;
import vn.cmc.du21.common.restful.StandardResponse;
import vn.cmc.du21.common.restful.StatusResponse;
import vn.cmc.du21.presentation.external.mapper.UserMapper;
import vn.cmc.du21.presentation.external.request.UserUpsertRequest;
import vn.cmc.du21.presentation.external.request.validator.UserRequestValidator;

import java.util.stream.Collectors;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        this.initHandler();
    }

    private void initHandler() {
        // sign up
        Spark.post("api/v1.0/user", (request, response) -> {
            response.type("application/json");

            UserUpsertRequest userUpsertRequest = Util.getObjectMapper().fromJson(request.body(), UserUpsertRequest.class);

            try {
                UserRequestValidator.upsertRequestValidate(userUpsertRequest);
            }catch (Exception e)
            {
                return Util.getObjectMapper().toJson(new StandardResponse<>(
                        StatusResponse.BADREQUEST.getStatus(),
                        StatusResponse.BADREQUEST.getStatusInt(),
                        e.getMessage()));
            }


            String result = userService.checkUserExist(
                    userUpsertRequest.getUserName(),
                    userUpsertRequest.getEmail(),
                    userUpsertRequest.getCellphone());

            if(result!=null){
                return Util.getObjectMapper().toJson(new StandardResponse<>(
                        StatusResponse.BADREQUEST.getStatus(),
                        StatusResponse.BADREQUEST.getStatusInt(),
                        result));
            }

            userService.addUser(UserMapper.userUpsertRequestToUser(userUpsertRequest));
            userService.addUserRole(userService.getUser(userUpsertRequest.getUserName()).getUserId());

            return Util.getObjectMapper().toJson(new StandardResponse<>(
                    StatusResponse.SUCCESSFUL.getStatus(),
                    StatusResponse.SUCCESSFUL.getStatusInt(),
                    UserMapper.userToUserDetailResponse(
                            userService.getUser(userUpsertRequest.getUserName())
                    )));
        });

        // get users
        Spark.get("api/v1.0/users", (request, response) -> {
            response.type("application/json");
            String page = request.queryParams("page") == "" ? "1" : request.queryParams("page");
            String size = request.queryParams("size") == "" ? "10" : request.queryParams("size");
            String sort = request.queryParams("sort") == "" ? "userId" : request.queryParams("sort");

            if(Integer.parseInt(userService.totalPage(size)) >= Integer.parseInt(page)){
                return Util.getObjectMapper().toJson(new StandardResponse<>(StatusResponse.SUCCESSFUL.getStatus(),
                        StatusResponse.SUCCESSFUL.getStatusInt(),
                        Util.getObjectMapper().toJsonTree(
                                userService.getListUsers(page,size,sort).stream()
                                        .map(UserMapper::userDetailDataResponse).collect(Collectors.toList())),
                        page,
                        userService.totalPage(size),
                        userService.totalRecord()));
            } else {
                return Util.getObjectMapper().toJson((new StandardResponse<>(
                        StatusResponse.NOCONTENT.getStatus(),
                        StatusResponse.NOCONTENT.getStatusInt())));
            }

        });

        // get user detail
        Spark.get("api/v1.0/user/:id", (request, response) -> {
            response.type("application/json");
            Long userId = Long.valueOf(request.params(":id"));

            if(userService.getUser(userId)==null){
                return Util.getObjectMapper().toJson((new StandardResponse<>(
                        StatusResponse.NOCONTENT.getStatus(),
                        StatusResponse.NOCONTENT.getStatusInt())));
            }

            return Util.getObjectMapper().toJson(new StandardResponse<>(
                    StatusResponse.SUCCESSFUL.getStatus(),
                    StatusResponse.SUCCESSFUL.getStatusInt(),
                    UserMapper.userToUserDetailResponse(userService.getUser(userId))
                    ));
        });

        // update a user
        Spark.put("api/v1.0/user/:id", (request, response) -> {
            response.type("application/json");
            Long userId = Long.valueOf(request.params(":id"));

            UserUpsertRequest userUpsertRequest = Util.getObjectMapper().fromJson(request.body(), UserUpsertRequest.class);
            userUpsertRequest.setUserId(userId);

            String result = userService.checkEmailAndPhoneExist(
                    userId,
                    userUpsertRequest.getEmail(),
                    userUpsertRequest.getCellphone());

            if(result!=null){
                return Util.getObjectMapper().toJson(new StandardResponse<>(
                        StatusResponse.BADREQUEST.getStatus(),
                        StatusResponse.BADREQUEST.getStatusInt(),
                        result));
            }

            userService.updateUser(UserMapper.userUpsertRequestToUser(userUpsertRequest));

            return Util.getObjectMapper().toJson(new StandardResponse<>(
                    StatusResponse.SUCCESSFUL.getStatus(),
                    StatusResponse.SUCCESSFUL.getStatusInt(),
                    UserMapper.userToUserDetailResponse(userService.getUser(userId))
                    ));
        });

        // delete a user
        Spark.delete("api/v1.0/user/:id", (request, response) -> {
            response.type("application/json");
            Long userID = Long.valueOf(request.params(":id"));

            if(!userService.userExist(userID)){
                return Util.getObjectMapper().toJson(new StandardResponse<>(
                        StatusResponse.NOCONTENT.getStatus(),
                        StatusResponse.NOCONTENT.getStatusInt(),
                        "User doesn't exist"));
            }

            userService.deleteUser(userID);

            return Util.getObjectMapper().toJson(new StandardResponse<>(
                    StatusResponse.SUCCESSFUL.getStatus(),
                    StatusResponse.SUCCESSFUL.getStatusInt(),
                    "User deleted"));
        });

        // check if user exists or not
        Spark.options("api/v1.0/user/:id", (request, response) -> {
            response.type("application/json");
            Long userID = Long.valueOf(request.params(":id"));
            return Util.getObjectMapper().toJson(new StandardResponse<>(
                    StatusResponse.SUCCESSFUL.getStatus(),
                    StatusResponse.SUCCESSFUL.getStatusInt(),
                    (userService.userExist(userID)) ? "User exists" : "User does not exists"));
        });
    }


}
