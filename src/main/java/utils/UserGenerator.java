package utils;

import model.User;

public class UserGenerator {

    public static User createUser() {
        return new User("test" + System.currentTimeMillis() + "@mail.com", "123456", "name");

         }

         public static User createWrongUser() {
        return new User("wrong@mail.com", "wrong", "wrong");

         }

         public static User createUserWithoutPassword() {
        return new User("test" + System.currentTimeMillis() + "@mail.com", null, "name");
         }

         public static User createExistingUser() {
        return new User("test_existing@mail.com", "123456", "name");
         }
}
