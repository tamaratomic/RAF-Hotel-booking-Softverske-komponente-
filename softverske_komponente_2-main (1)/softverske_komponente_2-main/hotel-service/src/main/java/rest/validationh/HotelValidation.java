package rest.validationh;

import dto.entities.ReviewDto;
import dto.entities.RoomChangeDto;
import dto.hotels.HotelUpdateDto;
import exceptions.RequestException;
import mappers.HotelMapper;
import models.Hotel;
import models.Reviews;
import org.springframework.http.HttpStatus;
import repositories.HotelRepo;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class HotelValidation {

    public static void editMyHotel(HotelUpdateDto updateDto, Hotel hotel, HotelRepo hotelRepo) throws RequestException{
        int count = 0;
        boolean citySet = false, hotelNameSet = false;
        String exCity = hotel.getCity(), exHotelName = hotel.getHotelName();

        if(!(updateDto.getCity() == null)){
            hotel.setCity(updateDto.getCity());
            citySet = true;
            count++;
        }
        if(!(updateDto.getHotelName() == null)){
            hotel.setHotelName(updateDto.getHotelName());
            hotelNameSet = true;
            count++;
        }
        if(updateDto.getDescription() != null){
            hotel.setDescription(updateDto.getDescription());
            count++;
        }
        if(updateDto.getRoomTypes() != null){
            if(updateDto.getRoomTypes().isEmpty())
                throw new RequestException("Must have at least one room type", HttpStatus.UNPROCESSABLE_ENTITY);
            hotel.setRoomTypes(updateDto.getRoomTypes());
            count++;
        }
        if(count == 0)
            throw new RequestException("Nothing to update", HttpStatus.BAD_REQUEST);
        if(hotelNameSet || citySet)
            ParameterValidation.checkExistingHotel(hotel.getCity(), hotel.getHotelName(), hotelRepo);
    }

    public static Hotel addMyHotel(Long managerId, HotelUpdateDto hotelInfo, HotelRepo hotelRepo) throws RequestException{
        ParameterValidation.checkExistingHotel(hotelInfo.getCity(), hotelInfo.getHotelName(), hotelRepo);
        ParameterValidation.addMyHotel(hotelInfo);
        return HotelMapper.fromHotelUpdate(hotelInfo, managerId);
    }

    public static void updateHotelAdmin(Hotel hotel, HotelRepo hotelRepo) throws RequestException{
        if(hotel.getRoomTypes() == null)
            throw new RequestException("Room types cannot be null", HttpStatus.UNPROCESSABLE_ENTITY);
        if(hotel.getHotelName() == null)
            throw new RequestException("Hotel name cannot be null", HttpStatus.UNPROCESSABLE_ENTITY);
        if(hotel.getCity() == null)
            throw new RequestException("City cannot be null", HttpStatus.UNPROCESSABLE_ENTITY);
        if(hotel.getRoomTypes().isEmpty())
            throw new RequestException("Must have at least one room type", HttpStatus.UNPROCESSABLE_ENTITY);
        ParameterValidation.checkExistingHotel(hotel.getCity(), hotel.getHotelName(), hotelRepo);
    }

    public static void editHotelRooms(List<RoomChangeDto> rooms) throws RequestException, IndexOutOfBoundsException, NumberFormatException{
        if(rooms.isEmpty())
            throw new RequestException("Hotel must have at least one room-type", HttpStatus.UNPROCESSABLE_ENTITY);
        Interval arr[] = new Interval[rooms.size()];
        int i = 0;
        for(RoomChangeDto r: rooms){
            if(r.getLower() > r.getUpper())
                throw new RequestException("Invalid room layout, (x - y), where x >= y", HttpStatus.BAD_REQUEST);
            arr[i++] = new Interval(r.getLower(), r.getUpper());
        }
        if(ParameterValidation.overlappingIntervals(arr))
            throw new RequestException("Invalid room layout, overlapping room numbers", HttpStatus.BAD_REQUEST);
    }

    public static void reviewHotel(Hotel hotel, Long userId, Reviews potentialReview) throws RequestException {
        ParameterValidation.review(potentialReview.getComment(), potentialReview.getStars());
        if(userId.equals(hotel.getManagerId()))
            throw new RequestException("Manager's cannot review their own hotel", HttpStatus.FORBIDDEN);
        List<Reviews> reviews = hotel.getReviews();
        for(Reviews rev: reviews){
            if(rev.equals_review(potentialReview))
                throw new RequestException("You already left a review for hotel " + hotel.getHotelName(), HttpStatus.BAD_REQUEST);
        }
    }

    public static Reviews editReview(ReviewDto dto, Hotel hotel, Long userId) throws RequestException{
        Reviews toEdit = null;
        for(Reviews rev: hotel.getReviews()){
            if(rev.getUserId().equals(userId))
                toEdit = rev;
        }
        if(toEdit == null)
            throw new RequestException("You haven't reviewed hotel " + hotel.getHotelName(), HttpStatus.BAD_REQUEST);
        int count = 0;
        if(dto.getStars() != null){
            count++;
            if(dto.getStars() > 10 || dto.getStars() < 1)
                throw new RequestException("Invalid number of stars, value must be between [1..10]", HttpStatus.BAD_REQUEST);
        }
        if(dto.getComment() !=null){
            count++;
            if(dto.getComment().length() > 255)
                throw new RequestException("Comment too long, plase leave a brief review", HttpStatus.BAD_REQUEST);
        }
        if(count == 0)
            throw new RequestException("Nothing to update", HttpStatus.BAD_REQUEST);

        return toEdit;
    }


    private static class ParameterValidation{

       public static void checkExistingHotel(String city, String hotelName, HotelRepo hotelRepo) throws RequestException{
          try {
              Hotel hotel = hotelRepo.findByHotelNameAndCity(hotelName,city).get();
              throw new RequestException("Failed to update, a hotel in the same city and the same name already exists", HttpStatus.BAD_REQUEST);
          }catch (NoSuchElementException ignored){}
       }

       public static void addMyHotel(HotelUpdateDto dto) throws RequestException{
           if(dto.getHotelName() == null)
               throw new RequestException("Hotel name cannot be empty", HttpStatus.UNPROCESSABLE_ENTITY);
           if(dto.getCity() == null)
               throw new RequestException("Hotel city cannot be empty", HttpStatus.UNPROCESSABLE_ENTITY);
           if(dto.getRoomTypes() == null)
               throw new RequestException("Hotel city cannot be empty", HttpStatus.UNPROCESSABLE_ENTITY);
           if(dto.getRoomTypes().isEmpty())
               throw new RequestException("Must have at least one room type", HttpStatus.UNPROCESSABLE_ENTITY);
       }

       public static boolean overlappingIntervals(Interval[] arr){
           int n = arr.length;
           Arrays.sort(arr, (i1, i2) -> {
               return i1.start - i2.start;
           });
           for(int i = 1; i < n; i++)
               if (arr[i - 1].end >= arr[i].start)
                   return true;
           return false;
       }

       public static void review(String comment, Integer stars) throws RequestException{
           if(comment == null)
               throw new RequestException("Review comment is missing", HttpStatus.BAD_REQUEST);
           if(stars == null)
               throw new RequestException("Review stars are missing", HttpStatus.BAD_REQUEST);
           if(comment.length() > 255)
               throw new RequestException("Comment too long, please leave a brief review", HttpStatus.BAD_REQUEST);
           if(stars > 10 || stars < 1)
               throw new RequestException("Star value can only be between [1-10]", HttpStatus.BAD_REQUEST);
       }

    }

    private static class Interval{
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
