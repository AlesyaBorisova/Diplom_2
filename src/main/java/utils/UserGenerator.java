package utils;

import model.User;

public class UserGenerator {

    public static User createUser() {
        return new User("test" + System.currentTimeMillis() + "@mail.com", "123456", "name");

         }


         public static User createUserWithoutPassword() {
        return new User("test" + System.currentTimeMillis() + "@mail.com", null, "name");
         }

         public static User createUserWithoutEmail() {
        return new User(null, "123456", "name");
         }

         public static  User createUserWithoutName() {
        return new User("test" + System.currentTimeMillis() + "@mail.com", "123456", null);
         }

         public static User createExistingUser() {
        return new User("test_existing@mail.com", "123456", "name");
         }
}
