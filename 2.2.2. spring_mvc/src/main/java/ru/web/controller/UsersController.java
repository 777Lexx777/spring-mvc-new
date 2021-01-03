package ru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.web.model.User;
import ru.web.service.UserServiceImp;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping()
    public String printUsers(@RequestParam(value = "count", required = false) String count, Model model){
        if (count == null) { count = "0"; }
        model.addAttribute("messages", userServiceImp.getCountUser(count));
        return "user";
    }
    @GetMapping("/{id}")
    public String printUsersId(@PathVariable("id") long id, Model model){
        model.addAttribute("messagesID", userServiceImp.getUserId(id));
        return "userId";
    }
    @GetMapping("/new")
    public String newUsers( @ModelAttribute("newUser") User user){
       // model.addAttribute("newUser", new User());//
        return "new";
    }
    @PostMapping()
    public String createNewUsers(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {return "new";}
        userServiceImp.saveUser(user);
        return "redirect:/user";
    }
    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id){
        model.addAttribute("editUsers", userServiceImp.getUserId(id));
        return "/editUser";
    }
    @PatchMapping("/{id}")
    public String updateUsers(@ModelAttribute("editUsers") @Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {return "editUser";}
        userServiceImp.updateUser(user);
        return "redirect:/user";
    }
    @DeleteMapping("/{id}")
    public String deleteUsers( @ModelAttribute("messagesID") User user){
        userServiceImp.deleteUser(user);
        return "redirect:/user";
    }
}
