package com.example.w22comp1011w2;

import java.sql.*;

public class DBUtility {

    private static String user = "Parth200458024";
    private static String pass = "xqyNUKdfmY";
    private static String url = "jdbc:mysql://172.31.22.43:3306/Parth200458024";

    /**
     * This method will send a Camera object into the DB and return the cameraID
     */

    public static int insertCameraIntoDB(Camera camera) throws SQLException {
        int cameraID = -1;
        ResultSet resultSet = null;

        String sql = "INSERT INTO cameras(make, model, resolution, slr, price) Values (?,?,?,?,?);";

        //this is called a try with resources block. It will auto close anything in the ()
        try(
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps= connection.prepareStatement(sql, new String[] {"cameraID"})
        )
        {
            //configure the prepared statement to prevent SQL injection attacks
            ps.setString(1,camera.getMake());
            ps.setString(2,camera.getModel());
            ps.setInt(3,camera.getResolution());
            ps.setBoolean(4, camera.isSlr());
            ps.setDouble(5,camera.getPrice());

            //run the command into DB

            ps.executeUpdate();

            //get the cameraID
            resultSet = ps.getGeneratedKeys();
            while(resultSet.next())
                cameraID= resultSet.getInt(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(resultSet != null)
                resultSet.close();
        }
        return cameraID;

    }

}
