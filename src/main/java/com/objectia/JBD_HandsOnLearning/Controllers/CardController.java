package com.objectia.JBD_HandsOnLearning.Controllers;

import com.objectia.JBD_HandsOnLearning.DTO.CardResponseDTO;
import com.objectia.JBD_HandsOnLearning.DTO.FindByCvvDTO;
import com.objectia.JBD_HandsOnLearning.models.Card;
import com.objectia.JBD_HandsOnLearning.services.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("card-controller")
public class CardController {
    @Autowired
    CardService cardService;

    @Autowired
    ModelMapper modelMapper;

   @PostMapping("/create-card")
    public ResponseEntity<Card> createCard() throws Exception {
           Card createdCard = cardService.createCard();
           return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("/find-card-by-cvv")
    public ResponseEntity<CardResponseDTO> findByCvv(@RequestBody FindByCvvDTO cvvDTO){
      Card card =  cardService.findCardByCvv(cvvDTO.getCvv());
      CardResponseDTO cardResponseDTO = modelMapper.map(card,CardResponseDTO.class);
      return new ResponseEntity<>(cardResponseDTO,HttpStatus.FOUND);
    }
    
}
