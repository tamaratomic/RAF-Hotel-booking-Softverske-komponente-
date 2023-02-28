package initializers;

import dto.user.ManagerInformationDto;
import dto.user.UserInformationDto;
import enums.UserTypes;
import mappers.HotelMapper;
import mappers.UserMapper;
import models.Hotel;
import models.User;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    public static List<Hotel> initialHotels(){
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(HotelMapper.fromScratch("The Plaza", "New York", 2L,
                "Opened in 1907 and designated an official landmark in 1969, The Plaza is arguably the most famous hotel in New York."
        ));
        hotels.add(HotelMapper.fromScratch("Ritz", "Paris", 3L,
                "Famously the headquarters of Coco Chanel, Ernest Hemingway and Ingrid Bergman," +
                        " the legendary Hotel Ritz Paris has set the standard for luxury hotels since its 1898 opening."));
        hotels.add(HotelMapper.fromScratch("Raffles", "Singapore", 4L,
                "Named after Stamford Raffles, the founder of Singapore, this opulent, " +
                        "colonial-style hotel had surprisingly humble beginnings: It was originally built as a small 10-room bungalow."));
        hotels.add(HotelMapper.fromScratch("Beverly Hills Hotel", "Los Angeles", 5L,
                "The Beverly Hills Hotel, built in 1912, is as glamorous as its A-list clientele." +
                        "Guests have included Marilyn Monroe, John Wayne, Richard Burton and Elizabeth Taylor"));
        hotels.add(HotelMapper.fromScratch("Grand Hotel Europe", "St. Petersburg", 6L,
                "Opened in 1875, its restaurant was the city's first to boast electric light bulbs"));
        return hotels;
    }

    public static List<User> initialUsers(){
        List<User> users = new ArrayList<>();
        UserInformationDto adminInfo = UserMapper.fromScratch("aleksa", "admin", "" +
                "Password123", "admin@gmail.com", "12312312", "234211124", "22/12/2000");
        User admin = UserMapper.fromUserInformation(adminInfo);
        admin.setUserType(UserTypes.ADMINISTRATOR);
        users.add(admin);
        ManagerInformationDto manager1info = UserMapper.fromScratch("John Smith", "manager1", "Password123",
                "manager1@gmail.com", "2134151", "124412421", "21/02/1979",
                "The Plaza", "11/05/2001");
        users.add(UserMapper.fromManagerInformation(manager1info));
        ManagerInformationDto manager2info = UserMapper.fromScratch("Chloe Monet", "manager2", "Password123",
                "manager2@gmail.com", "12324226", "222211133", "21/04/1969",
                "Ritz", "16/07/1995");
        users.add(UserMapper.fromManagerInformation(manager2info));
        ManagerInformationDto manager3info = UserMapper.fromScratch("Adrian Chang", "manager3", "Password123",
                "manager3@gmail.com", "2317951", "992110689", "02/04/1974",
                "Raffles", "12/07/2005");
        users.add(UserMapper.fromManagerInformation(manager3info));
        ManagerInformationDto manager4info = UserMapper.fromScratch("Cleveland Brown", "manager4", "Password123",
                "manager4@gmail.com", "2331217951", "992115621", "18/11/1991",
                "Beverly Hills Hotel", "05/09/2011");
        users.add(UserMapper.fromManagerInformation(manager4info));
        ManagerInformationDto manager5info = UserMapper.fromScratch("Milana Smirnoff", "manager5", "Password123",
                "manager5@gmail.com", "12315851", "121416891", "11/12/1998",
                "Beverly Hills Hotel", "11/07/2021");
        users.add(UserMapper.fromManagerInformation(manager5info));
        UserInformationDto standardInfo = UserMapper.fromScratch("Aleksa Pekovic", "standard1",
                "Password123", "standard1@gmail.com", "12312312", "234211124", "22/12/2000");
        User standard = UserMapper.fromUserInformation(standardInfo);
        users.add(standard);
        return users;
    }


}
