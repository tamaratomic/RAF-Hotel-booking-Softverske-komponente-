package repositories;

import models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepo  extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByHotelName(String hotelName);
    Optional<Hotel> findByCity(String city);
    Optional<Hotel> findByManagerId(Long managerId);
    Optional<Hotel> findByHotelNameAndCity(String hotelName, String city);
}
