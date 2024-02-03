package ui.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyboardKey {
    ENTER("Enter"),
    ESCAPE("Escape"),
    DOWN_ARROW("ArrowDown")
    ;

    private final String name;
}
