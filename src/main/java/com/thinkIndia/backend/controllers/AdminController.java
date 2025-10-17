package com.thinkIndia.backend.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thinkIndia.backend.dto.RecommendationDto;
import com.thinkIndia.backend.entities.BlogPost;
import com.thinkIndia.backend.entities.Events;
import com.thinkIndia.backend.entities.Glimpses;
import com.thinkIndia.backend.entities.InternPlacements;
import com.thinkIndia.backend.entities.Internship;
import com.thinkIndia.backend.entities.Recommendations;
import com.thinkIndia.backend.entities.TeamMember;
import com.thinkIndia.backend.services.BlogPostService;
import com.thinkIndia.backend.services.EventsService;
import com.thinkIndia.backend.services.GlimpsesService;
import com.thinkIndia.backend.services.InternPlacementsService;
import com.thinkIndia.backend.services.InternshipService;
import com.thinkIndia.backend.services.RecommendService;
import com.thinkIndia.backend.services.S3Service;
import com.thinkIndia.backend.services.TeamMemberService;





@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private BlogPostService blogService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private GlimpsesService glimpsesService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private InternPlacementsService internPlacementsService;
    @Autowired
    private InternshipService internshipService;

    @PostMapping("/createBlog")
    public ResponseEntity<?> createBlog(@RequestParam("Title") String title , @RequestParam("Excerpt") String excerpt, @RequestParam("Cover_image") MultipartFile imageFile) throws IOException {
        title = title.stripLeading();
        title = title.stripTrailing();

        String imageUrl = s3Service.uploadFile(imageFile);
        BlogPost blog = new BlogPost(imageUrl, title, excerpt);
        blog = blogService.createBlog(blog);
        if(blog==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/deleteBlog/{heading}")
    public ResponseEntity<?> deleteBlog(@PathVariable(value="heading") String heading){
        BlogPost blog = blogService.findByHeading(heading);
        try {
            String imageUrl = blog.getImageUrl();
            URI uri = new URI(imageUrl);
            String key = uri.getPath().substring(1);
            s3Service.deleteFile(key);
        } catch (URISyntaxException ex) {
        }
        blogService.deleteByHeading(heading);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/addGlimpse")
    public ResponseEntity<?> addGlimpse(@RequestParam("Name") String name, @RequestParam("Glimpse_image") MultipartFile imageFile) throws IOException{
        name = name.stripLeading();
        name = name.stripTrailing();

        try{
            String imageUrl = s3Service.uploadFile(imageFile);
            Glimpses event = new Glimpses(name, imageUrl);
            event = glimpsesService.createEvent(event);
            if(event == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException e) {
            System.err.println("An IOException occurred during file upload: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("File upload to S3 failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("An internal server error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteGlimpse/{name}")
    public ResponseEntity<?> deleteGlimpse(@PathVariable(value="name") String name){
        Glimpses glimpse = glimpsesService.findByName(name);
        try {
            String imageUrl = glimpse.getImageUrl();
            URI uri = new URI(imageUrl);
            String key = uri.getPath().substring(1);
            s3Service.deleteFile(key);
        } catch (URISyntaxException ex) {
        }
        glimpsesService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/showUnresolvedRecommendations")
    public ResponseEntity<?> showUnresolvedRecommendations() {
        List<Recommendations> recommendList = recommendService.showUnresolvedRecommendations();
        List<RecommendationDto> recommendDtoList = new ArrayList<>();
        for(Recommendations recom: recommendList){
            RecommendationDto tempDto = new RecommendationDto(recom.getId() ,recom.getName(), recom.getEmail(), recom.getMessage(), recom.getPostTime());
            recommendDtoList.add(tempDto);
        }
        return new ResponseEntity<>(recommendDtoList, HttpStatus.OK);
    }
    @GetMapping("/showResolvedRecommendations")
    public ResponseEntity<?> showResolvedRecommendations() {
        List<Recommendations> recommendList = recommendService.showResolvedRecommendations();
        List<RecommendationDto> recommendDtoList = new ArrayList<>();
        for(Recommendations recom: recommendList){
            RecommendationDto tempDto = new RecommendationDto(recom.getId() ,recom.getName(), recom.getEmail(), recom.getMessage(), recom.getPostTime());
            recommendDtoList.add(tempDto);
        }
        return new ResponseEntity<>(recommendDtoList, HttpStatus.OK);
    }
    @DeleteMapping("/removeRecommendation/{id}")
    public ResponseEntity<?> removeRecommendation(@PathVariable(value="id") int recommendationId){
        recommendService.deleteById(recommendationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/resolveRecommendation/{id}")
    public ResponseEntity<?> resolveRecommendation(@PathVariable(value="id") int recommendationId) {
        Recommendations recommendation = recommendService.getById(recommendationId).get();
        // handle optional here
        recommendation.setResolved(true);
        recommendService.saveRecommendation(recommendation);        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/addTeamMember")
    public ResponseEntity<?> addTeamMember(@RequestParam("Name") String name, @RequestParam("Member_image") MultipartFile imageFile, @RequestParam("Committee") String committee, @RequestParam("Position") String position) throws IOException {
        name = name.stripLeading();
        name = name.stripTrailing();
        committee = committee.stripLeading();
        committee = committee.stripTrailing();
        position = position.stripLeading();
        position = position.stripTrailing();

        String imageUrl = s3Service.uploadFile(imageFile);
        TeamMember member = new TeamMember(name, imageUrl, committee, position);
        member = teamMemberService.saveMember(member);
        if(member == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    @DeleteMapping("/deleteTeamMember/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable(value="id") int id){
        Optional<TeamMember> memberOptional = teamMemberService.getById(id);
        if(memberOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        TeamMember member = memberOptional.get();
        try {
            String imageUrl = member.getImageUrl();
            URI uri = new URI(imageUrl);
            String key = uri.getPath().substring(1);
            s3Service.deleteFile(key);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        teamMemberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/addEvent")
    public ResponseEntity<?> addevents(@RequestParam("Name") String name,@RequestParam(value="Images", required=false) List<MultipartFile> imageList, @RequestParam(value="Details") String details, @RequestParam(value="Message",required=false) String message, @RequestParam(value="DateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime, @RequestParam(value="IsActive") int isActive, @RequestParam(value="ShowEvent") int showEvent) {
        Events events;
        if(imageList != null && !imageList.isEmpty()){
            List<String> imageUrlList = new ArrayList<>();
            for(MultipartFile image: imageList){
                try {
                    String imageUrl = s3Service.uploadFile(image);
                    imageUrlList.add(imageUrl);
                } catch (IOException e) {
                    System.err.println("Error creating event: " + e.getMessage());
                    e.printStackTrace();
                    return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
            }
            events = new Events(dateTime, details, imageUrlList,message, name, isActive, showEvent);
        }
        else{
            events = new Events(name, details, message, dateTime, isActive, showEvent);
        }
        eventsService.saveEvent(events);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/activateEvent/{id}")
    public ResponseEntity<?> activateEvent(@PathVariable("id") int id){
        Optional<Events> eventOptional = eventsService.findById(id);
        if(eventOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Events event = eventOptional.get();
        event.setIsActive(1);
        eventsService.saveEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/deActivateEvent/{id}")
    public ResponseEntity<?> deActivateEvent(@PathVariable("id") int id){
        Optional<Events> eventOptional = eventsService.findById(id);
        if(eventOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Events event = eventOptional.get();
        event.setIsActive(0);
        eventsService.saveEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/showEvent/{id}")
    public ResponseEntity<?> showEvent(@PathVariable("id") int id){
        Optional<Events> eventOptional = eventsService.findById(id);
        if(eventOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Events event = eventOptional.get();
        event.setShowEvent(1);
        eventsService.saveEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/hideEvent/{id}")
    public ResponseEntity<?> hideEvent(@PathVariable("id") int id){
        Optional<Events> eventOptional = eventsService.findById(id);
        if(eventOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Events event = eventOptional.get();
        event.setShowEvent(0);
        eventsService.saveEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/getHiddenEvents")
    public ResponseEntity<?> getHiddenEvents() {
        List<Events> hiddenEventsList = eventsService.getHiddenEvents();
        return new ResponseEntity<>(hiddenEventsList, HttpStatus.OK);
    }
    
    @GetMapping("/unHideEvent/{id}")
    public ResponseEntity<?> unHideEvent(@PathVariable("id") int id){
        Optional<Events> eventOptional = eventsService.findById(id);
        if(eventOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Events event = eventOptional.get();
        event.setShowEvent(1);
        eventsService.saveEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/deleteEvent/{id}")
    public void deleteEvent(@PathVariable("id") int id){
        Optional<Events> eventOptional = eventsService.findById(id);
        if(eventOptional.isEmpty()) return;
        List<String> imageUrlList = eventOptional.get().getImageUrlList();
        for(String imageUrl : imageUrlList){
            try {
                URI uri = new URI(imageUrl);
                String key = uri.getPath().substring(1);
                s3Service.deleteFile(key);
            } catch (URISyntaxException ex) {
            }
        }
        eventsService.deleteById(id);
    }

    @PostMapping("/addUpcommingInternship")
    public ResponseEntity<?> addUpcommingInternship(@RequestParam(value="Role", required=false) String role, @RequestParam(value="Description") String discription, @RequestParam(value="Institute", required=false) String institute, @RequestParam(value="eligibility") String eligibility, @RequestParam(value="Start Date", required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,@RequestParam(value="duration", required=false) Integer duration,@RequestParam(value="Internship_image", required=false) MultipartFile image, @RequestParam(value="IsActive", defaultValue = "0") int isActive) {
        String imageUrl = null;
        if(image != null && !image.isEmpty()){
            try {
                imageUrl = s3Service.uploadFile(image);
            } catch (IOException e) {
                System.err.println("Error creating event: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Error: " + e.getMessage());
            }
        }
        Internship internship = new Internship(role, discription, institute, startDate, duration, eligibility,imageUrl, isActive);
        internshipService.saveInternship(internship);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/removeInternship/{id}")
    public ResponseEntity<?> removeInternship(@PathVariable("id") int id){
        internshipService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

    @PostMapping("/addInternPlacements")
    public ResponseEntity<?> addInternPlacements(@RequestParam(value="Name") String studentName,@RequestParam(value="Designation") String designation,@RequestParam(value="Role") String role, @RequestParam(value="Institute") String instituteName, @RequestParam(value="Image") MultipartFile imageFile, @RequestParam(value="Message") String message) {
        try {
            String imageUrl = s3Service.uploadFile(imageFile);
            InternPlacements internPlacement = new InternPlacements(designation, imageUrl, instituteName, message, role, studentName);
            internPlacementsService.saveInternPlacedData(internPlacement);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/removeInternPlacedData/{id}")
    public ResponseEntity<?> deleteInternPlacements(@PathVariable("id") int id){
        Optional<InternPlacements> internPlacedOptional = internPlacementsService.findById(id);
        if(internPlacedOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            String imageUrl = internPlacedOptional.get().getImageUrl();
            URI uri = new URI(imageUrl);
            String key = uri.getPath().substring(1);
            s3Service.deleteFile(key);
        } catch (URISyntaxException ex) {
        }
        internPlacementsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

}
