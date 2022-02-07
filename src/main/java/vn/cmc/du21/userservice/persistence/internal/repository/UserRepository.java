package vn.cmc.du21.userservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.userservice.persistence.internal.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCellphone(String cellphone);

    @Query(value = "SELECT * FROM USER WHERE email = :email AND userId != :userId", nativeQuery = true)
    Optional<User> findByEmailMinusItself(@Param(value = "email") String email, @Param(value = "userId") long userId);

    @Query(value = "SELECT * FROM USER WHERE cellphone = :cellphone AND userId != :userId", nativeQuery = true)
    Optional<User> findBCellphoneMinusItself(@Param(value = "cellphone") String cellphone, @Param(value = "userId") long userId);
}
