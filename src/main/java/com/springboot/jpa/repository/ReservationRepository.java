package com.springboot.jpa.repository;

import com.springboot.jpa.domain.Reservation;
import com.springboot.jpa.domain.Store;
import com.springboot.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

     Store findByUser(User user);

     User findByEmail(String email);

     List<Reservation> findByStoreAndReservedTime(Store store, LocalDateTime reservedTime);
}
