package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.common.restful.JwtTokenProvider;
import vn.cmc.du21.userservice.persistence.internal.entity.Otp;
import vn.cmc.du21.userservice.persistence.internal.entity.Session;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.OtpRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.SessionRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    OtpRepository otpRepository;
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

    public void addOtp(String otp, String cellphone) {
        Otp foundOtp = otpRepository.findByCellphone(cellphone).orElse(null);
        if(foundOtp != null)
        {
            foundOtp.setOtpPass(otp);
            foundOtp.setStatus("Verifying");
            foundOtp.setOtpTry(foundOtp.getOtpTry()+1);
            foundOtp.setOtpTimeStamp(Timestamp.valueOf(LocalDateTime.now().plus(1, ChronoUnit.MINUTES)));
        }
        else
        {
            Otp newOtp = new Otp();
            newOtp.setCellphone(cellphone);
            newOtp.setOtpPass(otp);
            newOtp.setStatus("Verifying");
            newOtp.setOtpTry(1);
            newOtp.setOtpTimeStamp(Timestamp.valueOf(LocalDateTime.now().plus(1, ChronoUnit.MINUTES)));
        }
    }
}
