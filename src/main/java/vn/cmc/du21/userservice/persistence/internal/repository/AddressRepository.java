package vn.cmc.du21.userservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.userservice.persistence.internal.entity.Address;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = "SELECT * FROM ADDRESS WHERE userId = :userId", nativeQuery = true)
    List<Address> findByUserId(@Param(value = "userId") long userId);

    @Query(value = "SELECT * FROM ADDRESS WHERE addressId = :addressId AND userId = :userId", nativeQuery = true)
    Optional<Address> findByAddressIdAndUserId(@Param(value = "userId") long userId, @Param(value = "addressId") long addressId);
}
