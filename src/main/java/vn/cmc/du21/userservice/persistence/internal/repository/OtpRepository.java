package vn.cmc.du21.userservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.cmc.du21.userservice.persistence.internal.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {
}
