package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.common.JwtTokenProvider;
import vn.cmc.du21.userservice.persistence.internal.entity.Otp;
import vn.cmc.du21.userservice.persistence.internal.entity.Session;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.OtpRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.SessionRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;

import javax.transaction.Transactional;
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

    private static final int MAX_TRY_OTP = 5;
    private static final int TIME_WAIT = 5; // MINUTES
    private static final int TIMESTAMP_OTP = 5; // MINUTES
    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_VERIFYING = "Verifying";

    @Transactional
    public Session upsertSession(long userId, long deviceId) throws Throwable{

        User user = userRepository.findById(userId)
                .orElse(null);

        Set<Session> sessions = sessionRepository.findByUserId(userId);

        for (Session item:sessions) {
            if(item.getDeviceId() == deviceId)
            {
                String token = JwtTokenProvider.generateToken(item.getSessionId());
                item.setToken(token);
                item.setStatus(STATUS_ACTIVE);
                return sessionRepository.save(item);
            }
        }

        Session newSession = new Session(deviceId, user);
        newSession  = sessionRepository.save(newSession);
        String token = JwtTokenProvider.generateToken(newSession.getSessionId());
        newSession.setToken(token);
        newSession.setStatus(STATUS_ACTIVE);
        return sessionRepository.save(newSession);
    }

    @Transactional
    public void disableToken(String token) throws Throwable{
        Session foundSession = sessionRepository.findByToken(token)
                .orElseThrow(() -> {
                            throw new RuntimeException("Token does not exist !!!");
                        }
                );
        foundSession.setStatus("Logout");
        sessionRepository.save(foundSession);
    }

    @Transactional
    public void addOtp(String otp, String cellphone) {
        Optional<Otp> foundOtp = otpRepository.findByCellphone(cellphone);
        if(foundOtp.isPresent())
        {
            if(foundOtp.get().getOtpTry() > MAX_TRY_OTP)
            {
                if(foundOtp.get().getOtpTimeStamp()
                        .after(Timestamp.valueOf(LocalDateTime.now().minus(TIME_WAIT, ChronoUnit.MINUTES))))
                {
                    throw new SecurityException("Exceeded the allowed number of times, please wait "+TIME_WAIT+" minutes !!!");
                }
                else
                {
                    foundOtp.get().setOtpTry(0);
                    otpRepository.save(foundOtp.get());
                }
            }
            foundOtp.get().setOtpPass(otp);
            foundOtp.get().setStatus(STATUS_VERIFYING);
            foundOtp.get().setOtpTimeStamp(Timestamp.valueOf(LocalDateTime.now().plus(TIMESTAMP_OTP, ChronoUnit.MINUTES)));
            otpRepository.save(foundOtp.get());
        }
        else
        {
            Otp newOtp = new Otp();
            newOtp.setCellphone(cellphone);
            newOtp.setOtpPass(otp);
            newOtp.setStatus(STATUS_VERIFYING);
            newOtp.setOtpTry(0);
            newOtp.setOtpTimeStamp(Timestamp.valueOf(LocalDateTime.now().plus(TIMESTAMP_OTP, ChronoUnit.MINUTES)));
            otpRepository.save(newOtp);
        }
    }

    public void checkOtp(String otp, String cellphone) {
        Optional<Otp> foundOtp = otpRepository.findByCellphone(cellphone);
        if(foundOtp.isPresent())
        {
            foundOtp.get().setOtpTry(foundOtp.get().getOtpTry() + 1);
            if(foundOtp.get().getOtpPass().equals(otp))
            {
                if(foundOtp.get().getStatus().equals(STATUS_VERIFYING)
                        && foundOtp.get().getOtpTry() <= MAX_TRY_OTP
                        && foundOtp.get().getOtpTimeStamp().after(Timestamp.valueOf(LocalDateTime.now()))){
                    foundOtp.get().setStatus("Verified");
                    foundOtp.get().setOtpTry(0);
                    otpRepository.save(foundOtp.get());
                    return;
                }
            }
            otpRepository.save(foundOtp.get());
        }

        throw new RuntimeException("Incorrect. Please try again !!!");
    }

    @Transactional
    private boolean checkTokenActive(String token) {
        Optional<Session> foundSession = sessionRepository.findByToken(token);
        if(foundSession.isPresent() && foundSession.get().getStatus().equals(STATUS_ACTIVE))
        {
            Date expireTime = JwtTokenProvider.getExpireTimeFromJWT(token);
            return expireTime.after(Date.from(Instant.now()));
        }
        return false;
    }

    @Transactional
    public User getUserByToken(String token) throws Throwable{

        if(this.checkTokenActive(token))
        {
            long sessionId = JwtTokenProvider.getSessionIdFromJWT(token);
            Session foundSession = sessionRepository.findById(sessionId).orElseThrow(
                    () -> {
                        throw new RuntimeException("Token does not exist !!!");
                    }
            );
            return userRepository.findById(foundSession.getUser().getUserId()).orElseThrow(
                    () -> {
                        throw new RuntimeException("User does not exist  !!!");
                    }
            );
        }

        throw new RuntimeException("Error token !!!");
    }
}