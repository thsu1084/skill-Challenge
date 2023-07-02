package com.springboot.jpa.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.jpa.domain.Reservation;
import com.springboot.jpa.domain.Store;
import com.springboot.jpa.domain.User;
import com.springboot.jpa.exception.UserNotFoundException;
import com.springboot.jpa.repository.ReservationRepository;
import com.springboot.jpa.repository.StoreRepository;
import com.springboot.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    /*
    점주(사용자)의 이메일, 가게 이름, 주소를 입력받아 새로운 가게를 등록하는 기능을 수행합니다.
     */
    @PostMapping("/{email}/register")
    public Store registerStore(
            @PathVariable String email,
            @RequestParam String name,
            @RequestParam String address
           ) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            Store store = Store.builder()
                    .name(name)
                    .address(address)
                    .email(email)
                    .user(user)
                    .build();
            Store registeredStore = storeRepository.save(store);
            return registeredStore;
        } else {
            throw new UserNotFoundException("User is not registered");
        }
    }

    /*
     사용자의 이메일, 가게 ID, 선택한 시간을 입력받아 예약을 생성하는 기능을 수행합니다.
    */
    @PostMapping("/{email}/reserve")
    public ResponseEntity<?> reserveStore(@PathVariable String email, @RequestParam Long id, @RequestParam String selectedTime) throws UnsupportedEncodingException, DateTimeParseException {


        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("사용자가 등록되어 있지 않습니다.");
        }

        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            Store selectedStore = store.get();

            LocalDateTime reservationTime;

            reservationTime = LocalDateTime.parse(selectedTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            List<Reservation> existReservations = reservationRepository.findByStoreAndReservedTime(selectedStore, reservationTime);

            Reservation reservation = Reservation.builder()
                    .user(user)
                    .store(selectedStore)
                    .email(email)
                    .reservedTime(reservationTime)
                    .build();

            if (!Reservation.ReservationAvailable(reservation, existReservations)) {
                return ResponseEntity.badRequest().body("예약 가능한 시간이 아닙니다.");
            }



            reservationRepository.save(reservation);

            return ResponseEntity.ok("예약이 성공적으로 생성되었습니다.");
        }

        return ResponseEntity.badRequest().body("예약할 수 있는 가게가 없습니다.");
    }



    /*
      예약 ID와 사용자의 이메일을 입력받아 예약을 거절하는 기능을 수행합니다.
     */
    @PutMapping("/reservation/{id}/reject")
    public ResponseEntity<?> rejectReservation(@PathVariable Long id, @RequestParam String email) {
        User user = userRepository.findByEmail(email);

        Optional<Reservation> reservationOp = reservationRepository.findById(id);

        if (reservationOp.isPresent()) {


            Reservation reservation = reservationOp.get();


            if ("OWNER".equals(user.getRole()) && reservation.getStore().getUser().equals(user)) {
                reservation.setRejected(true);
                reservationRepository.save(reservation);
                return ResponseEntity.ok("예약이 거절되었습니다.");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 예약을 찾을 수 없습니다.");
    }
}