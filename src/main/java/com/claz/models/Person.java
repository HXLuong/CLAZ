package com.claz.models;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Description("first name of a person")
    String firstName;
    String lastName;
    LocalDate birthDate;
    Address address;
}
