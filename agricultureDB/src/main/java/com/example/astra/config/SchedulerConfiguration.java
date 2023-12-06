package com.example.astra.config;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.astra.model.Agri;
import com.example.astra.model.UserResponse;
import com.example.astra.model.fruitables;
import com.example.astra.repository.FruitablesRepository;

@EnableScheduling
@Configuration
public class SchedulerConfiguration {

	@Autowired
	SimpMessagingTemplate template;
	
	
	@Autowired
	private FruitablesRepository repo;
	
	@Scheduled(fixedDelay = 10000)
	public void sendLatestData() {
		
		List<fruitables> latestRecords =null;
//		Instant timestamp = Instant.parse("2023-11-20T12:52:00Z");
		Instant timestamp = Instant.now();
		
		Duration threeMinutes = Duration.ofSeconds(10);
		
		Instant threeMinutesAgo = timestamp.minus(threeMinutes);
		
		ZonedDateTime currentZonedDateTime = ZonedDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        ZonedDateTime threeMinutesAgoZonedDateTime = ZonedDateTime.ofInstant(threeMinutesAgo, ZoneId.systemDefault());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        
        
        
        Instant temp = Instant.parse(threeMinutesAgoZonedDateTime.format(formatter));
        
        System.out.println(threeMinutesAgoZonedDateTime.format(formatter) instanceof String);
        
        System.out.println(temp);

//        System.out.println("current time: " + currentZonedDateTime.format(formatter) + " three minutes: " + threeMinutes
//                + " three minutes ago: " + threeMinutesAgoZonedDateTime.format(formatter));
		try {
			latestRecords = this.repo.findAllByTimeGreaterThan(temp);
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		if(!latestRecords.isEmpty()) {
			for (fruitables record : latestRecords) {
				Map<String, Object> message = new HashMap<>();
		        message.put("name", record.getPrimaryKey().getName());
		        message.put("time", record.getPrimaryKey().getTime().toString());
		        message.put("color", record.getColor());
		        message.put("current_price", record.getCurrentPrice());
		        message.put("prev_price", record.getPrevPrice());
		        message.put("price_change", record.getPriceChange());
		        System.out.println(message);
				template.convertAndSend("/topic/user",message);
			}
		}
		else {
			template.convertAndSend("/topic/user", new UserResponse("No update yet"));
		}
	}
}
