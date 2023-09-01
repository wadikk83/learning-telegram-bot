package by.wadikk.telegrambot.checker;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import static by.wadikk.telegrambot.checker.CallbackDataCheckerEnum.*;

@Component
public class CheckerContainer {

    private final ImmutableMap<String, Checker> checkerMap;
    private final UnknownChecker unknownChecker;
    private final MathChecker mathChecker;
    private final RussianChecker russianChecker;
    private final EnglishChecker englishChecker;


    public CheckerContainer(UnknownChecker unknownChecker,
                            MathChecker mathChecker,
                            RussianChecker russianChecker,
                            EnglishChecker englishChecker) {
        this.unknownChecker = unknownChecker;
        this.mathChecker = mathChecker;
        this.russianChecker = russianChecker;
        this.englishChecker = englishChecker;
        checkerMap = ImmutableMap.<String, Checker>builder()
                .put(MATH.name(), mathChecker)
                .put(ENGLISH.name(), englishChecker)
                .put(RUSSIAN.name(), russianChecker)
                .build();
    }

    public Checker findChecker(String checkerIdentifier) {
        return checkerMap.getOrDefault(checkerIdentifier, unknownChecker);
    }
}
