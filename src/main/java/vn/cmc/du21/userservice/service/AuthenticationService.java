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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
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

    public void disableToken(String token) throws Throwable {
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

    public boolean checkOtp(String otp, String cellphone) {
        Optional<Otp> foundOtp = otpRepository.findByCellphone(cellphone);
        if(foundOtp.isPresent())
        {
            if(foundOtp.get().getOtpPass().equals(otp))
            {
                if(foundOtp.get().getStatus().equals("Verifying")
                        && foundOtp.get().getOtpTry() <= 5
                        && foundOtp.get().getOtpTimeStamp().before(Timestamp.valueOf(LocalDateTime.now()))){
                    foundOtp.get().setStatus("Verified");
                    otpRepository.save(foundOtp.get());
                    return true;
                }
                foundOtp.get().setStatus("Failed");
                otpRepository.save(foundOtp.get());
                return false;
            }
        }
        else
        {
            return false;
        }

        return false;
    }

    public User getUserByToken(String token) {
        if(checkTokenActive(token))
        {
            long sessionId = JwtTokenProvider.getSessionIdFromJWT(token);
            Session foundSession = sessionRepository.findById(sessionId).orElse(null);
            return userRepository.findById(foundSession.getUser().getUserId()).orElse(null);
        }
        return null;
    }

    private boolean checkTokenActive(String token) {
         Optional<Session> foundSession = sessionRepository.findByToken(token);
         if(foundSession.isPresent())
         {
             if(foundSession.get().getStatus().equals("Active"))
             {
                 Date expireTime = JwtTokenProvider.getExpireTimeFromJWT(token);
                 if(expireTime.after(Date.from(Instant.now())))
                 {
                     return true;
                 }
             }
         }

         return false;
    }
}
