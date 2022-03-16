package vn.cmc.du21.business.mapper;

import vn.cmc.du21.business.model.Session;
import vn.cmc.du21.business.model.User;
import vn.cmc.du21.common.DateTimeUtil;
import vn.cmc.du21.persistence.internal.entity.SessionData;
import vn.cmc.du21.persistence.internal.entity.UserData;

import java.sql.Timestamp;

public class SessionMapper {
    private SessionMapper() {
        super();
    }

    public static SessionData sessionToSessionData(Session session) {
        Timestamp expireTime = session.getExpireTime() == null ? null : DateTimeUtil.localDateTimeToSqlTimestamp(session.getExpireTime());
        return new SessionData(session.getUserId(), session.getDeviceId(), session.getToken(), expireTime, session.getStatus());
    }

    public static Session sessionDataToSession(SessionData sessionData) {
        return new Session(sessionData.getUserId(), sessionData.getDeviceId(), sessionData.getToken(),
                DateTimeUtil.sqlTimestampToLocalDateTime(sessionData.getExpireTime()), sessionData.getStatus());
    }
}
