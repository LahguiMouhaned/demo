package tn.arabsoft.auth.controler;
 
import java.util.List;
import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import tn.arabsoft.auth.entity.Service;
import tn.arabsoft.auth.entity.user_role;
import tn.arabsoft.auth.entity.Personnel; 
import tn.arabsoft.auth.repository.RoleRepository;
import tn.arabsoft.auth.repository.ServiceRepo;
import tn.arabsoft.auth.repository.UserRepository;
import tn.arabsoft.auth.repository.UserRoleRepo; 
import tn.arabsoft.auth.security.jwt.JwtUtils; 
import tn.arabsoft.auth.security.service.UserDetailsServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class Auth {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsServiceImpl  serv;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;
  @Autowired
  UserRoleRepo userRole;
  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  ServiceRepo serviceRepo;
  @GetMapping("/get/{mat}")
  public UserDetails get(@PathVariable String mat){
  	return this.serv.loadUserByUsername(mat);
  }
  
  @GetMapping("/gett/{mat}")
  public Optional<Personnel> gett(@PathVariable String mat){
  	return this.userRepository.findByMatriculeP(mat);
  }
  
  @GetMapping("/getUsers")
  public List<Personnel> getUsers(){
  	return this.userRepository.findAll();
  }  
 
 @GetMapping("/get")
 public List<Personnel> getPersonnel(){
	 return userRepository.findAll();
 }
 @GetMapping("/getService")
 public List<Service> getService(){
	 return serviceRepo.findAll();
 }

 @GetMapping("/getuseRole/{use}")
 public Optional<user_role> getUse(@PathVariable long use){
	 return userRole.getByUser_id(use);
 }
 @PutMapping("/updatePass")
 
 public ResponseEntity<Personnel> updatePass( @RequestBody Personnel Ag) {
 
    Optional<Personnel> AgData = userRepository.findById(Ag.getIdEmploye());
   if (AgData.isPresent()) {
	   Personnel agg = AgData.get();
  agg.setPassword(encoder.encode(Ag.getPassword()));

    return new ResponseEntity<>(userRepository.save(agg), HttpStatus.OK);
   } else {
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   } }
 
 @PutMapping("/setRole")
 
 public ResponseEntity<user_role> updateRh( @RequestBody user_role Ag) {

	    Optional<user_role> AgData = userRole.getByUser_id(Ag.getUser_id());
		   System.out.println(AgData);

	   if (AgData.isPresent()) {
		   user_role agg = AgData.get();
	  agg.setRole_id(Ag.getRole_id());

	    return new ResponseEntity<>(userRole.save(agg), HttpStatus.OK);
	   } else {
	     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	   } }
	 
 @PostMapping("/add")
 public Personnel addPers(@RequestBody Personnel pers) {
	return userRepository.save(pers);
	 
 }
 }

