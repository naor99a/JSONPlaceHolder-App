import ResourceClasses.Todo;

public class MainTest {
    public static void main(String[] args) {
        // UserTodos.getAllUsersTodos();
        // System.out.println("All users:\t " + RESTHelper.getResponse(Utils.Consts.JSONPlaceholder_ADDRESS + "/users"));

        System.out.println("For user 1:\t" + Queries.getUncompletedByUser(1));
        System.out.println("For user 5:\t" + Queries.getUncompletedByUser(5));
        System.out.println("For user 99:\t" + Queries.getUncompletedByUser(99));


        for (int threshold = 40; threshold < 60; threshold+=10) {
            for (int user = 0; user < 12; user++) {
                System.out.println("ResourceClasses.User " + user + " albums with more than " + threshold + " photos: " + Queries.getExceedingAlbumsByUser(user, threshold));
            }
        }
    }
}
