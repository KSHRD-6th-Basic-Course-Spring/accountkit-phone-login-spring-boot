package com.chhaileng.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.chhaileng.app.model.AccountKitPostRequest;
import com.chhaileng.app.model.Role;
import com.chhaileng.app.model.User;

@Controller
public class SMSLoginController {

    private String  APP_ID = "1529574253759244";
    private String  APP_SECRET = "25fd5f666f7c7928a42cac044bf2c6a6";
    private static final String  ME_ENDPOINT_BASE_URL = "https://graph.accountkit.com/v1.1/me";
    private static final String  TOKEN_EXCHANGE_BASE_URL = "https://graph.accountkit.com/v1.1/access_token";

    private RestTemplate restTemplate = new RestTemplate();
    
	@GetMapping("/login")
	public String login(ModelMap m) {
		m.addAttribute("accKitReq", new AccountKitPostRequest());
		return "login";
	}
	
	@PostMapping("/login/redirect_success")
	public String loginSuccess(@ModelAttribute AccountKitPostRequest accKitReq) {
//		String uri = TOKEN_EXCHANGE_BASE_URL + "?grant_type=authorization_code&code=" +
//						accKitReq.getCode() + "&access_token=AA|" + APP_ID + "|" + APP_SECRET;
//		System.out.println(accKitReq);
//		System.out.println(uri);
//        Object obj = restTemplate.getForObject(uri, Object.class);
        
        String uri = TOKEN_EXCHANGE_BASE_URL + "?grant_type=authorization_code" + "&code=" +
                accKitReq.getCode()+"&access_token=AA|"+APP_ID+"|"+APP_SECRET;
        Object obj = restTemplate.getForObject(uri, Object.class);

        @SuppressWarnings("unchecked")
		Map<String, Object> info = (HashMap<String,Object>) obj;
        String user_id = (String) info.get("id");
        String user_access_token = (String) info.get("access_token");

        String me_endpoint_uri = ME_ENDPOINT_BASE_URL + "?access_token=" + user_access_token;
        Object me = restTemplate.getForObject(me_endpoint_uri,Object.class);

        //Set Phone User Object
        @SuppressWarnings("unchecked")
		Map<String, Object> map = (HashMap<String,Object>) me;
        @SuppressWarnings("unchecked")
		Map<String, Object> phone = (HashMap<String,Object>) map.get("phone");
        String number = (String) phone.get("number");

        // Sign up user and login
        User user = new User();
        user.setId(user_id);
        user.setPhone(number);
        user.setToken(user_access_token);
        user.setName("Chhaileng Peng");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        System.out.println(user);
        
//        // Create login session
//        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
//		SecurityContextHolder.getContext().setAuthentication(auth);
        
        
		return "redirect:/index";
	}
	
	@GetMapping(value= {"/index"})
	public String index() {
		return "index";
	}
}
