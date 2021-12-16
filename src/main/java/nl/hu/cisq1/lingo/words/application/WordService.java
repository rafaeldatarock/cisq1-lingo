package nl.hu.cisq1.lingo.words.application;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;

@Service
@Transactional
public class WordService {
    private final SpringWordRepository wordRepository;

    public WordService(SpringWordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public String provideRandomWord(Integer length) {
        return this.wordRepository
                .findRandomWordByLength(length)
                .orElseThrow(() -> new WordLengthNotSupportedException(length))
                .getValue();
    }
}
