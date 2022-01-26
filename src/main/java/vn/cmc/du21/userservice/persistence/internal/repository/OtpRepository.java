package vn.cmc.du21.userservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.userservice.persistence.internal.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
}
