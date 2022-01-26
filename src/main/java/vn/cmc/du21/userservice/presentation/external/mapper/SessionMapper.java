//package vn.cmc.du21.userservice.presentation.external.mapper;
//
//import vn.cmc.du21.userservice.common.DateTimeUtil;
//import vn.cmc.du21.userservice.persistence.internal.entity.Session;
//import vn.cmc.du21.userservice.presentation.external.response.SessionResponse;
//
//public class SessionMapper {
//
//    public static SessionResponse convertSessionToSessionResponse(Session session) {
//        return new SessionResponse(session.getSessionId(), session.getToken(),
//                DateTimeUtil.timestampToString(session.getExpireTime()), session.getStatus(),
//                session.getUser().getUserId());
//    }
//}
