package controllers.tests;

import controllers.AppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.NotFoundException;

public class AppResourcesTest {

    private static AppResources controls = null;

    public static void main(String[] args) {
        controls = AppResources.getInstance();

        try {
            System.out.println(controls.search("m"));

            System.out.println(controls.search("adwkdwakdwdlkawdklawd"));

        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        try{

            System.out.println(controls.search("V2.01"));

            System.out.println(controls.search("Sune"));

            System.out.println(controls.search("M"));

            System.out.println(controls.search(" "));

            System.out.println(controls.search("(test)"));

            System.out.println(controls.search("No U"));

        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        try{

            System.out.println(controls.search("}"));
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
