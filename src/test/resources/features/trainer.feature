Feature: Training for Lingo
    As a Player,
    I want to practice guessing 5, 6, and 7 letter words
    In order to practice for my appearence in the real game

    Scenario: Start new game
        When I start a game
        Then the score is 0
        And the first letter of a random 5-letter word is shown

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

    # Failure path for 'Start a new round'
    Scenario: Cannot start new round
        Given I am playing a game
        And the round was lost
        Then I cannot start a new round

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

    Scenario Outline: Score calculation
        Given my total score was "<prevtotal>"
        And I finished a round
        When this round took "<amount>" of guesses
        Then the total score should be "<newtotal>"

        Examples:
            | prevtotal | amount | newtotal |
            | 25        | 1      | 50       |
            | 50        | 1      | 75       |
            | 75        | 1      | 100      |
            | 100       | 5      | 105      |
            | 0         | 1      | 25       |
            | 0         | 2      | 20       |
            | 0         | 3      | 15       |
            | 0         | 4      | 10       |
            | 0         | 5      | 5        |
            | 0         | 6      | 0        |

    Scenario: Guess limit
        Given I am playing a game
        And I have already used 4 guesses
        When guess for the 5th time
        And my guess is incorrect
        Then I am game over

    Scenario Outline: Initial hint
        Given I started a game
        When the word I need to guess is "<word>"
        Then I should get this "<hint>"

        Examples:
        # dots are used to substitute for empty hint
            | word    | hint    |
            | bapao   | b....   |
            | koekje  | k.....  |
            | brownie | b...... |

    Scenario Outline: Updating hint
        Given I am playing a game
        And the word I need to guess is "<word>"
        And my hint is "<hint>"
        When the feedback on my guess is "<feedback>"
        Then my next hint should be "<nexthint>"

        Examples:
        # dots are used to substitute for empty hint
            | word   | hint   | feedback    | nexthint |
            | koekje | k..... | a,a,a,a,p,a | k.....   |
            | koekje | k..... | c,c,c,a,p,a | koe...   |
            | koekje | koe... | a,a,a,c,c,c | koekje   |
            | bapao  | b....  | c,c,p,a,a   | ba...    |
            | babbel | b..... | a,a,a,c,c,c | b..bel   |

    # We weten dat één speler meerdere spellen kan hebben. Daar hebben we in onze specificaties geen
    # rekening mee gehouden. Denk na hoe je dit in de code wel zou doen. Dit mag je uitwerken in je
    # specificaties maar hoeft niet.

    # Door middel van Tokens bijhouden? Token uitgeven bij game start die correspondeert met een bepaalde gamesessie
    # Elke stap wordt de token mee gezonden
    # Zo kun je ook altijd later verder gaan mits de token maar is opgeslagen