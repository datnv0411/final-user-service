package vn.cmc.du21.userservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.userservice.persistence.internal.entity.Session;

import java.util.Set;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = "SELECT * FROM SESSION WHERE UserId = :userId", nativeQuery = true)
    Set<Session> findByUserId(@Param(value = "userId") long userId);
}
