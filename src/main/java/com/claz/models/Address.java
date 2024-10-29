package com.claz.models;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Description("an address")
class Address {
    String street;
    Integer streetNumber;
    String city;
}
