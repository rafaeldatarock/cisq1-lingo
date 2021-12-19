package nl.hu.cisq1.lingo.trainer.application;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFound;
import nl.hu.cisq1.lingo.words.application.WordService;

@Service
@Transactional
public class TrainerService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;

    public TrainerService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public GameProgress startGame() {
        Game game = Game.start(this.wordService.provideRandomWord(5));

        this.gameRepository.save(game);
        return game.giveProgress();
    }

    public GameProgress guessWord(Long id, String guess) {
        Game game = this.gameRepository
            .findById(id)
            .orElseThrow(() -> new GameNotFound(id));

        game.attemptGuess(guess);

        this.gameRepository.save(game);
        return game.giveProgress();
    }

    public GameProgress newRound(Long id) {
        Game game = this.gameRepository
            .findById(id)
            .orElseThrow(() -> new GameNotFound(id));

        game.startNewRound(this.wordService.provideRandomWord(6));

        this.gameRepository.save(game);
        return game.giveProgress();
    }

    public GameProgress showProgress(Long id) {
        return this.gameRepository
            .findById(id)
            .orElseThrow(() -> new GameNotFound(id))
            .giveProgress();
    }

    public GameProgress stopGame(Long id) {
        Game game = this.gameRepository
            .findById(id)
            .orElseThrow(() -> new GameNotFound(id));

        game.stop();

        this.gameRepository.save(game);
        return game.giveProgress();
    }
}
