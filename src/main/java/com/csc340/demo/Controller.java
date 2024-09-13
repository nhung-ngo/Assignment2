package com.csc340.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class Controller {
     Map<String, Cat> catDatabase = new HashMap<>();

    @GetMapping("cat/all")
    public Collection<Cat> getAllCat(){
        if(catDatabase.isEmpty()){
            catDatabase.put("0xyv", new Cat("0xyv", "Egypt", "Woodro"));
        }
        return catDatabase.values();
    }

    @GetMapping("cat/{id}")
    public Cat getCatById(@PathVariable String id){
        return catDatabase.get(id);
    }

    @PostMapping("cat/create")
    public ResponseEntity<Object> createCat(@RequestBody List<Cat> cats){
        for(Cat cat : cats){
            catDatabase.put(cat.getId(), cat);
        }

        return ResponseEntity.ok(catDatabase.values());

    }

    @DeleteMapping("cat/delete/{id}")
    public Object deleteCat(@PathVariable String id){
        if(catDatabase.containsKey(id)) {
            catDatabase.remove(id);
            return catDatabase.values();
        } else {
            return "Cat with id " + id + " dose not exist";
        }
    }

    @GetMapping("/catInf")
    public Object getCatInf() {
        try {
            String url = "https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng&api_key=REPLACE_ME";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonListResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            if(root.isArray()) {
                List<Cat> catList = new ArrayList<>();
                for (JsonNode rt : root) {
                    String id = rt.get("id").asText();
                    String origin = rt.get("country").asText();
                    String weight = rt.get("weight").asText();
                    System.out.println(id + ": " + origin);

                    Cat cat = new Cat(id, origin, weight);
                    catList.add(cat);
                }
                return catList;
            }else{
                return "Unexpected";
            }


        } catch (JsonProcessingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /catInf";
        } catch (Exception ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /catInf";
        }

    }

}
