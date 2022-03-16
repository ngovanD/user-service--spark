package vn.cmc.du21.business.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vn.cmc.du21.business.mapper.SessionMapper;
import vn.cmc.du21.business.model.Session;
import vn.cmc.du21.persistence.internal.DaoManager;
import vn.cmc.du21.persistence.internal.entity.SessionData;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.Instant;

public class AuthenticationService {
    private static final String key = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String createJWTToken(String username, String role){
        Instant now = Instant.now();

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
                .signWith( SignatureAlgorithm.HS256, key.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public void addSession(Session session) {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            daoManager.getSessionDAO().save(SessionMapper.sessionToSessionData(session));
            daoManager.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSession(Session session)
    {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            daoManager.getSessionDAO().update(SessionMapper.sessionToSessionData(session));
            daoManager.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session sessionExist(Long userId, Long deviceId)
    {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            Optional<SessionData> sessionData = daoManager.getSessionDAO().getByUserIdDeviceId(userId, deviceId);
            daoManager.commitTransaction();

            return sessionData.map(SessionMapper::sessionDataToSession).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Session checkToken(String token)
    {
        try (DaoManager daoManager = new DaoManager()) {
            daoManager.beginTransaction();
            Optional<SessionData> sessionData = daoManager.getSessionDAO().getActive(token);
            daoManager.commitTransaction();

            return sessionData.map(SessionMapper::sessionDataToSession).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
