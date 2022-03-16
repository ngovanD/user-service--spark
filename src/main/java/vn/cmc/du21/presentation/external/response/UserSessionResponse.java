package vn.cmc.du21.presentation.external.response;

public class UserSessionResponse {
    UserDetailResponse profile;
    SessionDetailResponse authentication;

    public UserDetailResponse getProfile() {
        return profile;
    }

    public void setProfile(UserDetailResponse profile) {
        this.profile = profile;
    }

    public SessionDetailResponse getAuthentication() {
        return authentication;
    }

    public void setAuthentication(SessionDetailResponse authentication) {
        this.authentication = authentication;
    }

    public UserSessionResponse(UserDetailResponse userDetailResponse, SessionDetailResponse sessionDetailResponse) {
        this.profile = userDetailResponse;
        this.authentication = sessionDetailResponse;
    }
}
