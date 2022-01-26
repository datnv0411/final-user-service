//package vn.cmc.du21.userservice.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import vn.cmc.du21.userservice.persistence.internal.entity.Session;
//import vn.cmc.du21.userservice.persistence.internal.entity.User;
//import vn.cmc.du21.userservice.persistence.internal.repository.SessionRepository;
//import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;
//import vn.cmc.du21.userservice.presentation.external.response.SessionResponse;
//
//import java.util.Set;
//
//@Service
//public class AuthenticationService {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    SessionRepository sessionRepository;
//    public Session upsertSession(long userId, long deviceId) {
//        User user = userRepository.findById(userId).orElse(null);
//        Set<Session> sessions = sessionRepository.findByUserId(userId);
//
//        if(!sessions.isEmpty())
//        {
//            for (Session item:
//                 sessions) {
//                if(item.getDeviceId() == deviceId)
//                {
//
//                }
//            }
//        }
//        else
//        {
//
//        }
//    }
//}
