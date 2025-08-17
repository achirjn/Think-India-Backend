package com.thinkIndia.backend.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thinkIndia.backend.dto.BlogDto;
import com.thinkIndia.backend.dto.EventDto;
import com.thinkIndia.backend.dto.ImageDto;
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
public class HomeController {
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TeamMemberService teamMemberService;

    // @CrossOrigin(origins = {"http://localhost:5173"})
    @PostMapping("/recommend")
    public ResponseEntity<?> saveRecommendation(@RequestParam("Name") String name, @RequestParam("Email") String email, @RequestParam("Message") String message) {
        Recommendations recommendation = new Recommendations(name, email, message);
        recommendation = recommendService.saveRecommendation(recommendation);
        if(recommendation == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // @CrossOrigin(origins = {"http://localhost:5173"})
    @GetMapping("/blogPageGetAllBlogs")
    public ResponseEntity<?> allBlogs() {
        List<BlogPost> blogsList = blogPostService.getAllBlogs();
        List<BlogDto> blogDtoList = new ArrayList<>();
        for(BlogPost it: blogsList){
            BlogDto tempDto = new BlogDto(it.getImageId(), it.getHeading(), it.getContent(),it.getPostTime());
            blogDtoList.add(tempDto);
        }
        return new ResponseEntity<>(blogDtoList, HttpStatus.OK);
    }
    // @CrossOrigin(origins = {"http://localhost:5173"})
    @GetMapping("/blog/{id}")
    public ResponseEntity<?> getBlog(@PathVariable(value="id") int blogId) {
        Optional<BlogPost> blogPostOptional = blogPostService.getBlogById(blogId);
        if(blogPostOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        BlogPost blog = blogPostOptional.get();
        
        BlogDto blogDto = new BlogDto(blog.getImageId(), blog.getHeading(), blog.getContent(),blog.getPostTime());
        return new ResponseEntity<>(blogDto, HttpStatus.OK);
    }
    
    // @CrossOrigin(origins = {"http://localhost:5173"})
    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable(value="id") int imageId) {
        Optional<Images> imageOptional = imageService.getImageById(imageId);
        if(imageOptional.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        byte[] imageByteArray = imageOptional.get().getData();
        String base64Image = Base64.getEncoder().encodeToString(imageByteArray);
        ImageDto imageDto = new ImageDto(base64Image);
        return new ResponseEntity<>(imageDto, HttpStatus.OK);
    }
    
    // @CrossOrigin(origins = {"http://localhost:5173"})
    @GetMapping("/events")
    public ResponseEntity<?> getEvents() {
        List<Events> eventsList = eventService.findAll();
        List<EventDto> eventDtoList = new ArrayList<>();
        for(Events ev: eventsList){
            EventDto tempDto = new EventDto(ev.getImageId(), ev.getName());
            eventDtoList.add(tempDto);
        }
        return new ResponseEntity<>(eventDtoList, HttpStatus.OK);
    }
    
    @GetMapping("/getMemberByPosition/{position}")
    public ResponseEntity<?> getMemberByPosition(@PathVariable String position) {
        position = position.replace('_',' ');
        List<TeamMember> memberList = teamMemberService.getMemberByPosition(position);
        if(memberList.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }
    @GetMapping("/getTeamMember")
    public ResponseEntity<?> getTeamMember() {
        List<TeamMember> memberList = teamMemberService.getMembers();
        return new ResponseEntity<>(memberList,HttpStatus.OK);
    }
    
    
    
}
