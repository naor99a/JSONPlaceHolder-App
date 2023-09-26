public class MainTest {
    public static void main(String[] args) {

        System.out.println("For user 1:\t" + Main.getUncompletedByUser(1));
        System.out.println("For user 5:\t" + Main.getUncompletedByUser(5));
        System.out.println("For user 99:\t" + Main.getUncompletedByUser(99));


        for (int threshold = 40; threshold < 60; threshold+=10) {
            for (int user = 0; user < 12; user++) {
                System.out.println("ResourceClasses.User " + user + " albums with more than " + threshold + " photos: " + Main.getExceedingAlbumsByUser(user, threshold));
            }
        }
    }
}
