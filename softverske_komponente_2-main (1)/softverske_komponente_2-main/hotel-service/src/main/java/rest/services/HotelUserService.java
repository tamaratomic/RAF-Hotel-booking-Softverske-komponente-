package rest.services;

import dto.entities.ReviewDto;
import dto.entities.TokenDto;
import exceptions.RequestException;
import mappers.EntityMapper;
import mappers.HotelMapper;
import models.Hotel;
import models.Reviews;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repositories.HotelRepo;
import rest.validationh.HotelValidation;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("all")

public class HotelUserService {

    public static ResponseEntity<?> getAll(HotelRepo hotelRepo){
        List<Hotel> hotels = hotelRepo.findAll();
        return new ResponseEntity<>(HotelMapper.fromHotel(hotels), HttpStatus.OK);
    }

    public static ResponseEntity<?> reviewHotel(HotelRepo hotelRepo, TokenDto userInfo, Long hotelId, ReviewDto reviewDto) throws RequestException {
        try {
            Hotel hotel = hotelRepo.findById(hotelId).get();
            Reviews review = EntityMapper.fromReviewDto(userInfo.getId(), userInfo.getUsername(), reviewDto);
            HotelValidation.reviewHotel(hotel, userInfo.getId(), review);
            hotel.addReview(review);
            hotel.adjustRating();
            hotel.setLastModified(Instant.now());
            hotelRepo.save(hotel);
            return new ResponseEntity<>("Sussessfully left a review", HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested hotel not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> deleteReview(HotelRepo hotelRepo, Long hotelId, Long userId) throws RequestException {
        try{
            Hotel hotel = hotelRepo.findById(hotelId).get();
            Reviews reviewToFind = null;
            for(Reviews rev: hotel.getReviews()){
                if(rev.getUserId().equals(userId))
                    reviewToFind = rev;
            }
            if(reviewToFind == null)
                throw new RequestException("You haven't left a review for hotel: " + hotel.getHotelName(), HttpStatus.BAD_REQUEST);
            hotel.deleteReview(reviewToFind);
            hotel.adjustRating();
            hotel.setLastModified(Instant.now());
            hotelRepo.save(hotel);
            return new ResponseEntity<>("Successfully deleted your review for hotel: " + hotel.getHotelName(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested hotel not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> editReview(HotelRepo hotelRepo, Long hotelId, Long userId, ReviewDto dto)  throws RequestException{
        try{
            Hotel hotel = hotelRepo.findById(hotelId).get();
            Reviews toEdit = HotelValidation.editReview(dto, hotel, userId);
            hotel.deleteReview(toEdit);
            toEdit.setComment(  (dto.getComment() == null) ? toEdit.getComment() : dto.getComment() );
            toEdit.setStars(  (dto.getStars() == null) ? toEdit.getStars() : dto.getStars() );
            hotel.addReview(toEdit);
            hotel.adjustRating();
            hotel.setLastModified(Instant.now());
            hotelRepo.save(hotel);
            return new ResponseEntity<>("Successfully edited review for hotel: " + hotel.getHotelName(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested hotel is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

}
