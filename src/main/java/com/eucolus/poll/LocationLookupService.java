package com.eucolus.poll;

import com.eucolus.poll.repositories.RequestLocationRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

@Service
public class LocationLookupService implements Runnable {

    @Autowired
    RequestLocationRepository requestLocationRepository;
    @Autowired
    TaskExecutor taskExecutor;

    private String ip;

    public void updateLocation(String ip) throws IOException {
        this.ip = ip;
        taskExecutor.execute(this);
    }

    @Override
    public void run() {
        JSONObject jsonObject = new JSONObject();
        try {
            URL url = new URL("http://freegeoip.net/json/" + ip);
            Scanner scanner = new Scanner(url.openStream());

            jsonObject = new JSONObject(scanner.nextLine());
            String country = jsonObject.getString("country_name");
            String city = jsonObject.getString("country_name");
            Double longitude = jsonObject.getDouble("longitude");
            Double latitude = jsonObject.getDouble("latitude");

            requestLocationRepository.update(ip, country, city, longitude, latitude);
        } catch (UnknownHostException e) {
            System.out.println("No internet connection, cannot find location");
        }
        catch (JSONException e) {
            e.printStackTrace();
            System.err.println(ip + " " + jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
