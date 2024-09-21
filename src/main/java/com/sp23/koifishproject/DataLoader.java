package com.sp23.koifishproject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp23.koifishproject.model.Pond;
import com.sp23.koifishproject.model.User;
import com.sp23.koifishproject.repository.mongo.PondRepository;
import com.sp23.koifishproject.repository.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PondRepository pondRepository;


    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //Load UserData
        TypeReference<List<User>> userReference = new TypeReference<List<User>>() {};
        InputStream inputStream = new ClassPathResource("data/UserData.json").getInputStream();
        List<User> users = mapper.readValue(inputStream, userReference);

        for (User user : users) {
            Optional<User> existingStage = userRepository.findById(user.getId());
            if (existingStage.isEmpty()) {
                userRepository.save(user);
            }
        }

        //Load PondData
        TypeReference<List<Pond>> pondReference = new TypeReference<List<Pond>>() {};
        InputStream pondInputStream = new ClassPathResource("data/PondData.json").getInputStream();
        List<Pond> ponds = mapper.readValue(pondInputStream, pondReference);
        for (Pond pond : ponds) {
            Optional<Pond> existingPond = pondRepository.findById(pond.getId());
            if (existingPond.isEmpty()) {
                pondRepository.save(pond);
            }
        }

        //
        System.out.println("Data loaded successfully");
    }
}
