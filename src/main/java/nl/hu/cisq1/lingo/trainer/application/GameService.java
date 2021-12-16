package nl.hu.cisq1.lingo.trainer.application;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.words.application.WordService;

@Service
@Transactional
public class GameService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;

    public GameService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public GameProgress startGame() {
        Game game = Game.start(wordService.provideRandomWord(5));
        gameRepository.save(game);
        return game.giveProgress();
    }
}
