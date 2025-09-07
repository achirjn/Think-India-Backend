package com.thinkIndia.backend.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.thinkIndia.backend.dto.UserEventDto;
import com.thinkIndia.backend.dto.UserInternshipsDto;
import com.thinkIndia.backend.entities.EventRegistration;
import com.thinkIndia.backend.entities.Events;
import com.thinkIndia.backend.entities.InternApplication;
import com.thinkIndia.backend.entities.Internship;
import com.thinkIndia.backend.entities.ResumeCV;
import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.services.EventRegistrationService;
import com.thinkIndia.backend.services.EventsService;
import com.thinkIndia.backend.services.InternApplicationService;
import com.thinkIndia.backend.services.InternshipService;
import com.thinkIndia.backend.services.ResumeCVService;
import com.thinkIndia.backend.services.S3Service;
import com.thinkIndia.backend.services.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ResumeCVService resumeCVService;
    @Autowired
    private InternshipService internshipService;
    @Autowired
    private InternApplicationService internApplicationService;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private EventRegistrationService eventRegistrationService;

    @GetMapping("/getUserData/{email}")
    public ResponseEntity<?> getUserData( @PathVariable("email") String email) {
        User user = (User) userService.loadUserByUsername(email);
        if(user==null) return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    

    @PostMapping("/editProfile/{email}")
    public ResponseEntity<?> editProfile(@RequestParam(value="Profile_pic", required=false) MultipartFile imageFile,
    @RequestParam(value="UserName") String name, @PathVariable("email") String email) throws IOException {
        
        User user = (User) userService.loadUserByUsername(email);
        if(imageFile!=null){
            if(user.getImageUrl()!=null){
                try {
                    String imageUrl = user.getImageUrl();
                    URI uri = new URI(imageUrl);
                    String key = uri.getPath().substring(1);
                    s3Service.deleteFile(key);
                } catch (URISyntaxException ex) {
                }
            }
            String imageUrl = s3Service.uploadFile(imageFile);
            user.setImageUrl(imageUrl);
        }
        user.setName(name);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<?> changePassword(@RequestParam(value="Old_password") String oldPassword,@RequestParam(value="New_password") String newPassword, @PathVariable("email") String email) {
        User user = (User) userService.loadUserByUsername(email);
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            return new ResponseEntity<>("Wrong old password.", HttpStatus.BAD_REQUEST);
        }
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/uploadResume/{email}")
    public ResponseEntity<?> uploadResume(@PathVariable("email") String email, @RequestParam(value="Resume_name") String name, @RequestParam(value="Resume_file") MultipartFile resumeFile) {
        User user = (User) userService.loadUserByUsername(email);
        ResumeCV oldResume = user.getResumeCV();
        ResumeCV resume;
        try {
            resume = new ResumeCV(name, resumeFile.getBytes());
            user.setResumeCV(resume);
            userService.saveUser(user);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(oldResume!=null){
            int resumeId = oldResume.getId();
            resumeCVService.deleteById(resumeId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //resume is downloaded from frontend itsel cause getUserData doesnt return resume id without which download api wont work

    // @GetMapping("/downloadResumeCV/{id}")
    // public void downloadResumeCV(@PathVariable("id") int resumeId, HttpServletResponse response) throws IOException {
    //     try {
    //         Optional<ResumeCV> resumeOptional = resumeCVService.findById(resumeId);
    //         if (resumeOptional.isEmpty()) {
    //             response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resume not found");
    //             return;
    //         }

    //         ResumeCV resume = resumeOptional.get();

    //         // Set response headers
    //         response.setContentType("application/pdf");
    //         response.setContentLength(resume.getData().length);

    //         String headerKey = "Content-Disposition";
    //         String headerValue = "attachment; filename=\"" + resume.getName() + ".pdf\"";
    //         response.setHeader(headerKey, headerValue);

    //         // Write the file data to response
    //         ServletOutputStream outputStream = response.getOutputStream();
    //         outputStream.write(resume.getData());
    //         outputStream.flush();

    //     } catch (IOException ex) {
    //         // Log the error and send error response
    //         System.err.println("Error downloading resume: " + ex.getMessage());
    //         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading file");
    //         throw ex;
    //     }
    // }

    @GetMapping("/getUpcommingInternships")
    public ResponseEntity<?> getUpcommingInternships() {
        List<Internship> upcommingInternships = internshipService.getUpcommingInternships();
        return new ResponseEntity<>(upcommingInternships, HttpStatus.OK);
    }
    
    
    @PostMapping("/applyInternship/{userEmail}/{internshipId}")
    public ResponseEntity<?> applyInternship(@PathVariable("userEmail") String userEmail, @PathVariable("internshipId") int internshipId, @RequestParam(value="Resume", required=false) MultipartFile resumeFile) {
        User user = (User) userService.loadUserByUsername(userEmail);
        Optional<Internship> internshipOptional = internshipService.findById(internshipId);
        if(internshipOptional.isEmpty() || user==null) return new ResponseEntity<>("invalid application.", HttpStatus.BAD_REQUEST);
        ResumeCV resume;
        if(resumeFile == null){
            resume = user.getResumeCV();
        }
        else{
            try {
                resume = new ResumeCV(resumeFile.getOriginalFilename(), resumeFile.getBytes());
            } catch (IOException ex) {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }
        InternApplication application = new InternApplication(user, internshipOptional.get(), resume);
        internApplicationService.saveApplication(application);
        
        return new ResponseEntity<>(OK);
    }
    @GetMapping("/myApplications/{userEmail}")
    public ResponseEntity<?> getMyInternApplications(@PathVariable("userEmail") String userEmail) {
        User user = (User) userService.loadUserByUsername(userEmail);
        List<InternApplication> applications = internApplicationService.getAppliedInternships(user);
        List<UserInternshipsDto> userInternshipsDtoList = new ArrayList<>();
        for(InternApplication ia: applications){
            UserInternshipsDto tempDto = new UserInternshipsDto(ia.getInternship(), ia.getStatus(), ia.getApplicationDateTime(), ia.getResumeCV());
            userInternshipsDtoList.add(tempDto);
        }
        return new ResponseEntity<>(userInternshipsDtoList, HttpStatus.OK);
    }
    
    @PostMapping("/registerEvent/{userEmail}/{eventId}")
    public ResponseEntity<?> registerEvent(@RequestParam(value="Team_id", required=false) String teamId,@RequestParam(value="Team_name", required=false) String teamName, @PathVariable("userEmail") String email, @PathVariable("eventId") int eventId) {
        User user = (User) userService.loadUserByUsername(email);
        Optional<Events> eventOptional = eventsService.findById(eventId);
        if(eventOptional.isEmpty() || user==null) return new ResponseEntity<>("invalid application.", HttpStatus.BAD_REQUEST);
        EventRegistration eventRegistration;
        if(teamId==null){
            eventRegistration = new EventRegistration(eventOptional.get(), user, teamName);
        }
        else{
            eventRegistration = new EventRegistration(user, eventOptional.get(), teamId);
        }
        eventRegistrationService.saveRegistration(eventRegistration);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/myRegistrations/{userEmail}")
    public ResponseEntity<?> getRegistrationsForUser(@PathVariable("userEmail") String email) {
        User user = (User) userService.loadUserByUsername(email);
        List<EventRegistration> userRegistrationList = eventRegistrationService.getRegisteredEvents(user);
        List<UserEventDto> userEventDtoList = new ArrayList<>();
        for(EventRegistration er: userRegistrationList){
            UserEventDto userEventDto = new UserEventDto(er.getEvent(), er.getTeamName());
            userEventDtoList.add(userEventDto);
        }
        return new ResponseEntity<>(userEventDtoList, HttpStatus.OK);
    }
    
}
