package dev.haroon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDTO {

    private byte[] imageData;     
    private String fileName;      
    private String imageType;     

  
}

