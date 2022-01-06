package nl.hu.cisq1.lingo.trainer.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;

class GameMapper {

    public static FullGameResponse toDto(Game game) {
        Long id = game.getId();
        int score = game.getScore();
        String status = game.getStatus().toString();
        List<Rounds> rounds = new ArrayList<>();

        for (Round r : game.getRounds()) {

            String lastHint = r.giveHint();
            List<Attempts> attempts = new ArrayList<>();

            for (Attempt a : r.getAttempts()) {

                String guess = a.getGuess();
                List<String> feedback = a.getFeedback().stream()
                    .map(Enum::toString)
                    .collect(Collectors.toList());
                
                attempts.add(new Attempts(guess, feedback));
            }

            rounds.add(new Rounds(lastHint, attempts));
        }

        return new FullGameResponse(id, score, status, rounds);
    }
}
