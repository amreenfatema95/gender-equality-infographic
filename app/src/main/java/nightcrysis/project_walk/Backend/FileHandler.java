/**
 * A class for handling file storage, only contains static methods
 */
package nightcrysis.project_walk.Backend;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {

    /**
     * A method for writing a Serializable object to a file
     *
     * @param dir The path of the file you wish to create and store data too
     * @param object The object which will have its byte stream written to the file
     * @param <T> The type of the object you are storing to a file
     */
    public static <T> void saveObjectToFile(String dir, T object){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir));
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method for loading an object from a file
     *
     * @param dir The path of the file you with to load
     * @param <T> The type of the object the file contains
     * @return The object loaded from the file of the type declared
     */
    public static <T> T loadObjectFromFile(String dir){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir));
            return (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * A method for checking if there is a file at the given path
     *
     * @param dir The path of the file
     * @return True if there is a file there, otherwise false
     */
    public static boolean doesFileExist(String dir){
        File file = new File(dir);
        return file.exists();
    }

    /**
     * A method for activities to easily get the path to a commonly used file countryset.bin
     *
     * @param activity The activity you are calling this from
     * @return The full path to countryset.bin file
     */
    public static String getPathToCountrySet(Activity activity){
        return activity.getFilesDir().getAbsolutePath()
                + "/" + "countryset.bin";
    }
}