package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private EntityManager entityManager;

    @RequestMapping("*")
    public String defaultMapping(Authentication authentication) {
        if (authentication.getName() != null && authentication.getName().equals("admin")) {
            return "redirect:/admin";
        }
        return "redirect:/signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }
    
    @RequestMapping(value = "/signups", method = RequestMethod.GET)
    public String loadSignup(Authentication authentication, Model model, @RequestParam(required = false) String name) {
        
        if (name == null) {
            String loggedInUsername = authentication.getName();
            return "redirect:/signups?name=" + loggedInUsername;
        }
        
        List<Signup> signups = entityManager.createNativeQuery("SELECT s.id, s.name, s.address FROM Signup s WHERE s.name='" + name + "'", Signup.class).getResultList();
        
        model.addAttribute("signups", signups);
        return "signups";
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String loadAdminPage() {
        return "admin";
    }
    
    @RequestMapping(value = "/admin/signups", method = RequestMethod.GET)
    public String loadSignups(Model model) {
        List<Signup> signups = signupRepository.findAll();
        model.addAttribute("signups", signups);
        return "signups_admin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }
    
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "error";
    }

}
