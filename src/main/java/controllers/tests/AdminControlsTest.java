package controllers.tests;

import controllers.AdminControls;
import controllers.exceptions.DataAccessException;

public class AdminControlsTest {

    private static AdminControls controls = null;

    public static void main(String[] args){
        controls = AdminControls.getInstance();

        try {
            System.out.println(controls.getAdminNames());

            System.out.println("Is s165202 Admin ? "+controls.isAdmin("s165202"));

            controls.addAdmin("Dummyadmin");

            System.out.println("Dummyadmin added");

            System.out.println(controls.getAdminNames());

            System.out.println("Is Dummyadmin Admin ? "+controls.isAdmin("Dummyadmin"));

            controls.removeAdmin("Dummyadmin");

            System.out.println("Dummyadmin removed\nTry to remove again");

            controls.removeAdmin("Dummyadmin");

            System.out.println("Is Dummyadmin Admin ? "+controls.isAdmin("Dummyadmin"));

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


}
