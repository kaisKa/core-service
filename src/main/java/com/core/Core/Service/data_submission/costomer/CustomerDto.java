package com.core.Core.Service.data_submission.costomer;

import com.core.Core.Service.data_submission.submission.Submission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {


    private String firstName;
    private String lastName;
    private String email;
    private String password;


//    @Enumerated(EnumType.STRING)
//    private Role role; // for the sake of simplicity, I make it one role


}
