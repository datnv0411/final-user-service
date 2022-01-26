package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.common.restful.JwtTokenProvider;
import vn.cmc.du21.userservice.persistence.internal.entity.Session;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.SessionRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;

import java.util.Set;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;
    public Session upsertSession(long userId, long deviceId) {
        User user = userRepository.findById(userId).orElse(null);
        Set<Session> sessions = sessionRepository.findByUserId(userId);

        for (Session item:sessions) {
            if(item.getDeviceId() == deviceId)
            {
                String token = JwtTokenProvider.generateToken(item.getSessionId());
                item.setToken(token);
                item.setStatus("Active");
                return sessionRepository.save(item);
            }
        }

        Session newSession = new Session(deviceId, user);
        newSession  = sessionRepository.save(newSession);
        String token = JwtTokenProvider.generateToken(newSession.getSessionId());
        newSession.setToken(token);
        newSession.setStatus("Active");
        return sessionRepository.save(newSession);
    }

    public void disableToken(String token) throws IndexOutOfBoundsException {
        Session foundSession = sessionRepository.findByToken(token)
                .orElseThrow(() -> {
                        throw new IndexOutOfBoundsException("Token is not available");
                    }
                );
        foundSession.setStatus("Logout");
        sessionRepository.save(foundSession);
    }
}
