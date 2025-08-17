package com.thinkIndia.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import com.thinkIndia.backend.entities.Images;
import com.thinkIndia.backend.entities.Recommendations;
import com.thinkIndia.backend.entities.TeamMember;
import com.thinkIndia.backend.services.BlogPostService;
import com.thinkIndia.backend.services.EventService;
import com.thinkIndia.backend.services.ImageService;
import com.thinkIndia.backend.services.RecommendService;
import com.thinkIndia.backend.services.TeamMemberService;





@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private BlogPostService blogService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private EventService eventService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private TeamMemberService teamMemberService;

    // @CrossOrigin(origins = {"http://localhost:5173"})
    @PostMapping("/createBlog")
    public ResponseEntity<?> createBlog(@RequestParam("Title") String title , @RequestParam("Excerpt") String excerpt, @RequestParam("Cover_image") MultipartFile imageFile) throws IOException {
        title = title.stripLeading();
        title = title.stripTrailing();

        int savedImageId = uploadImage(imageFile);
        if(savedImageId==-1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        BlogPost blog = new BlogPost(savedImageId, title, excerpt);
        blog = blogService.createBlog(blog);
        if(blog==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/deleteBlog/{heading}")
    public ResponseEntity<?> deleteBlog(@PathVariable(value="heading") String heading){
        blogService.deleteByHeading(heading);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // @CrossOrigin(origins = {"http://localhost:5173"})
    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvent(@RequestParam("Name") String name, @RequestParam("Event_image") MultipartFile imageFile) throws IOException{
        name = name.stripLeading();
        name = name.stripTrailing();

        int savedImageId = uploadImage(imageFile);
        if(savedImageId==-1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Events event = new Events(name, savedImageId);
        event = eventService.createEvent(event);
        if(event == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/deleteEvent/{name}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value="name") String name){
        eventService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // @CrossOrigin(origins = {"http://localhost:5173"})
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
    // @CrossOrigin(origins = {"http://localhost:5173"})
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
    // @CrossOrigin(origins = {"http://localhost:5173"})
    @DeleteMapping("/removeRecommendation/{id}")
    public ResponseEntity<?> removeRecommendation(@PathVariable(value="id") int recommendationId){
        recommendService.deleteById(recommendationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // @CrossOrigin(origins = {"http://localhost:5173"})
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

        int savedImageId = uploadImage(imageFile);
        if(savedImageId==-1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        TeamMember member = new TeamMember(name, savedImageId, committee, position);
        member = teamMemberService.saveMember(member);
        if(member == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    @DeleteMapping("/deleteTeamMember/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable(value="id") int id){
        Optional<TeamMember> memberOptional = teamMemberService.getById(id);
        if(memberOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        TeamMember member = memberOptional.get();
        int imageId = member.getImageId();
        deleteImage(imageId);
        teamMemberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    public int uploadImage(MultipartFile imageFile) throws IOException{
        String imageName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        Images image = new Images(imageName, imageFile.getBytes());
        Images savedImage = imageService.saveImage(image);
        if(savedImage==null) return -1;
        return savedImage.getId();
    }
    public void deleteImage(int id){
        imageService.deleteById(id);
    }

}
