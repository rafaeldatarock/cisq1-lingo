Feature: Training for Lingo
    As a Player,
    I want to practice guessing 5, 6, and 7 letter words
    In order to practice for my appearence in the real game

Feature: Starting a game
    As a Player,
    I want to start a game,
    In order to practice Lingo

    Scenario: Start new game
        When I start a game
        Then the score is 0
        And the first letter of a random 5-letter word is shown

Feature: Game sequence
    As a Player,
    I want to be given a 5 letter word in the first round
    And a 6 letter word in the next round 
    And a 7 letter word in the round after that
    And then the sequence should start again in the round after that
    In order to follow the sequence of the game

    Scenario Outline: Start a new round
        Given I am playing a game
        And the round was won
        And the last word had "<previous length>" letters
        When I start a new round
        Then the next word to guess has "<next length>" letters

    Examples:
        | previous length | next length |
        | 5               | 6           |
        | 6               | 7           |
        | 7               | 5           |

    # Failure path
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

Feature: See word length
    As a Player,
    I want to see the length of the word I need to guess,
    In order to be able to think of correct guesses

Feature: Guess limit
    As a Player,
    I want to have to guess a word within 5 tries,
    In order to train for the Lingo contest

Feature: Resuming a game
    As a Player,
    I want to resume a game where I left of,
    So I can keep my progress in a game

Feature: Guessing a word
    As a Player,
    I want to guess a word,
    In order to get closer to the correct answer

    Scenario Outline: Guessing a word
        Given I am playing a game
        And the word to guess is "<word>"
        When my guess is "<guess>"
        Then the feedback I get should be "<feedback>"

    Examples:
    # c = correct, p = present, a = absent, i = invalid
        | word  | guess   | feedback      |
        | braam | braam   | c,c,c,c,c     |
        | braam | vroom   | a,c,a,a,c     |
        | braam | baard   | c,p,p,p,a     |
        | baard | bedde   | c,a,p,a,a     |
        | braam | bramen  | i,i,i,i,i,i   |
        | braam | badgast | i,i,i,i,i,i,i |

Feature: Getting feedback
    As a Player,
    I want to get feedback after I guessed a word,
    In order to know which letters are present in the word and/or in the correct place or if my guess was invalid

Feature: See first hint
    As a Player,
    I want to see a hint when the game round starts with a new word,
    In order to see with which letter the word starts

Feature: See hint
    As a Player,
    I want to see a new hint after I guessed a letter place correctly,
    In order to easily see the letters I guessed in their correct place

Feature: See score after round
    As a Player,
    I want to see my score after I guessed a word correctly or failed to do so within 5 tries,
    In order to see how well I performed


# We weten dat één speler meerdere spellen kan hebben. Daar hebben we in onze specificaties geen
# rekening mee gehouden. Denk na hoe je dit in de code wel zou doen. Dit mag je uitwerken in je
# specificaties maar hoeft niet.

# Door middel van Tokens bijhouden? Token uitgeven bij game start die correspondeert met een bepaalde gamesessie
# Elke stap wordt de token mee gezonden
# Zo kun je ook altijd later verder gaan mits de token maar is opgeslagen