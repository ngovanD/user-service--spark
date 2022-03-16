package vn.cmc.du21.business.service;

import vn.cmc.du21.business.mapper.RoleMapper;
import vn.cmc.du21.business.mapper.UserMapper;
import vn.cmc.du21.business.mapper.UserRoleMapper;
import vn.cmc.du21.business.model.Role;
import vn.cmc.du21.business.model.User;
import vn.cmc.du21.business.model.UserRole;
import vn.cmc.du21.common.EncodePasswordUtil;
import vn.cmc.du21.common.RandomStringUtil;
import vn.cmc.du21.persistence.internal.DaoManager;
import vn.cmc.du21.persistence.internal.entity.RoleData;
import vn.cmc.du21.persistence.internal.entity.UserData;
import vn.cmc.du21.persistence.internal.entity.UserRoleData;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    public void addUser(User user) {
        try (DaoManager daoManager = new DaoManager()) {
            user.setPasswordSalt(RandomStringUtil.createString());
            user.setPasswordHashAlgorithm("MD5");
            user.setPassword(EncodePasswordUtil.getEncodePassword(user.getPassword()+user.getPasswordSalt(), user.getPasswordHashAlgorithm()));
            user.setCreateTime(LocalDateTime.now());
            user.setCreateBy("User");
            user.setUpdateTime(LocalDateTime.now());
            user.setUpdateBy("User");

            daoManager.beginTransaction();

            daoManager.getUserDAO().save(UserMapper.userToUserData(user));
            daoManager.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            List<UserData> result = daoManager.getUserDAO().getAll();
            daoManager.commitTransaction();

            return result.stream().map(UserMapper::userDataToUser).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public User getUser(Long id) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            Optional<UserData> result = daoManager.getUserDAO().get(id);
            daoManager.commitTransaction();

            return result.map(UserMapper::userDataToUser).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUser(String username) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            Optional<UserData> result = daoManager.getUserDAO().get(username);
            daoManager.commitTransaction();

            return result.map(UserMapper::userDataToUser).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUser(User user) throws Exception {
        try (DaoManager daoManager = new DaoManager()) {
            User userUpdate = getUser(user.getUserId());
            String newPassword = EncodePasswordUtil.getEncodePassword(user.getPassword()+userUpdate.getPasswordSalt(), userUpdate.getPasswordHashAlgorithm());
            user.setPassword(newPassword);
            user.setUpdateTime(LocalDateTime.now());
            user.setUpdateBy("User");

            daoManager.beginTransaction();
            daoManager.getUserDAO().update(UserMapper.userToUserData(user));
            daoManager.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Long id) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            daoManager.getUserDAO().deleteById(id);
            daoManager.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean userExist(Long id) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            Optional<UserData> result = daoManager.getUserDAO().get(id);
            daoManager.commitTransaction();

            return result.isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User checkUsernameAndPassword(String username, String password) throws NoSuchAlgorithmException {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();

            Optional<UserData> resultPwd = daoManager.getUserDAO().get(username);
            User userData = resultPwd.map(UserMapper::userDataToUser).orElse(null);
            if(userData==null){
                return null;
            }
            String pwdSalt = userData.getPasswordSalt();
            String pwdHash = userData.getPasswordHashAlgorithm();

            String newPassword = EncodePasswordUtil.getEncodePassword(password+pwdSalt, pwdHash);

            Optional<UserData> result = daoManager.getUserDAO().checkUsernameAndPassword(username, newPassword);
            daoManager.commitTransaction();

           return result.map(UserMapper::userDataToUser).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<User> getListUsers( String page, String size, String sort) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            List<UserData> result = daoManager.getUserDAO().getList(page,size,sort);
            daoManager.commitTransaction();

            return result.stream().map(UserMapper::userDataToUser).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public String totalPage (String size){
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            String result = daoManager.getUserDAO().getTotalPage(size);
            daoManager.commitTransaction();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String totalRecord (){
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            String result = daoManager.getUserDAO().getTotalRecord();
            daoManager.commitTransaction();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String checkUserExist(String username, String email, String phone){
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();

            String result = daoManager.getUserDAO().checkUserExisted(username, email, phone);
            daoManager.commitTransaction();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String checkEmailAndPhoneExist(Long userId, String email, String phone){
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();

            String result = daoManager.getUserDAO().checkEmailAndPhoneExisted(userId, email, phone);
            daoManager.commitTransaction();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addUserRole(long userId) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();

            Optional<RoleData> role = daoManager.getRoleDAO().get("User");

            UserRole userRole = new UserRole(userId,(role.map(RoleMapper::roleDataToRole).orElse(null)).getRoleId());
            daoManager.getUserRoleDAO().save(UserRoleMapper.userRoleToUserRoleData(userRole));

            daoManager.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserRole getUserRole(long userId){
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            Optional<UserRoleData> result = daoManager.getUserRoleDAO().get(userId);
            daoManager.commitTransaction();

            return result.map(UserRoleMapper::userRoleDataToUserRole).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Role getRole(long roleId) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            Optional<RoleData> result = daoManager.getRoleDAO().get(roleId);
            daoManager.commitTransaction();

            return result.map(RoleMapper::roleDataToRole).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
