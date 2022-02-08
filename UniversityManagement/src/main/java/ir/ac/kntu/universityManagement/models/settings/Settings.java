package ir.ac.kntu.universityManagement.models.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    private Theme theme;
    private Theme previousTheme;
    private Language language;
    private FontSize previousFontSizeName;
    private FontSize fontSizeName;
}
