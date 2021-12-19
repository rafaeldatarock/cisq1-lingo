package nl.hu.cisq1.lingo.trainer.presentation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFound;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;
    
    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public GameProgress startGame() {
        try {
            return this.service.startGame();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/game/{id}/guess")
    public GameProgress guessWord(@PathVariable Long id, @RequestBody @Valid GuessRequest attempt) {
        try {
            return this.service.guessWord(id, attempt.getGuess());
        } catch (GameNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/game/{id}/round")
    public GameProgress newRound(@PathVariable Long id) {
        try {
            return this.service.newRound(id);
        } catch (GameNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/game/{id}")
    public GameProgress showProgress(@PathVariable Long id) {
        try {
            return this.service.showProgress(id);
        } catch (GameNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/game/{id}")
    public GameProgress stopGame(@PathVariable Long id) {
        try {
            return this.service.stopGame(id);
        } catch (GameNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
