package vn.cmc.du21.presentation.external.mapper;

import vn.cmc.du21.business.model.Session;
import vn.cmc.du21.business.model.User;
import vn.cmc.du21.common.DateTimeUtil;
import vn.cmc.du21.presentation.external.request.SessionUpsertRequest;
import vn.cmc.du21.presentation.external.request.UserUpsertRequest;
import vn.cmc.du21.presentation.external.response.SessionDetailResponse;
import vn.cmc.du21.presentation.external.response.UserDetailResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SessionMapper {
    private SessionMapper() {
        super();
    }

    public static Session sessionUpsertRequestToSession(SessionUpsertRequest sessionUpsertRequest) {
        LocalDateTime expireTime = sessionUpsertRequest.getExpireTime() == null? null: DateTimeUtil.stringToLocalDateTime(sessionUpsertRequest.getExpireTime());
        return new Session(sessionUpsertRequest.getUserId(), sessionUpsertRequest.getDeviceId(),
                sessionUpsertRequest.getToken(), expireTime, sessionUpsertRequest.getStatus());
    }

    public static SessionDetailResponse sessionToSessionDetailResponse(Session session) {

        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

        String expireTime = session.getExpireTime().format(formatDateTime);
        return new SessionDetailResponse(session.getUserId(), session.getDeviceId(), session.getToken(),
                expireTime, session.getStatus());
    }

    public static SessionUpsertRequest sessionToSessionUpsertRequest(Session session) {
        String expireTime = DateTimeUtil.localDateTimeToString(session.getExpireTime());
        return new SessionUpsertRequest(session.getUserId(), session.getDeviceId(),
                session.getToken(), expireTime, session.getStatus());
    }
}
